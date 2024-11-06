package nongmangkhut_adventure.character_all;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Enemy8 implements Runnable, Damageable {

    private int health; // Health points of the enemy
    private float x, y; // Position of the enemy
    private int width, height; // Size of the enemy
    private BufferedImage[] frames; // Animation frames for walking
    private BufferedImage[] attackFrames; // Animation frames for attacking
    private int currentFrame; // Current frame for animation
    private int attackFrame; // Current frame for attack animation
    private int frameCount; // Total number of frames for walking
    private int attackFrameCount; // Total number of frames for attacking
    private boolean alive; // Status of the enemy's life
    private boolean attacking; // Status of attack animation
    private float movementDirection = 1; // 1 = right, -1 = left
    private Random random; // Random instance
    private Character player; // Reference to the player character
    private ScheduledExecutorService animationScheduler; // Scheduled executor for animation
    private Thread movementThread; // Thread for managing movement
    private volatile boolean running; // Control for the movement thread
    private final float speed; // Movement speed
    private float detectionRange; // Detection range for the player
    private float attackRange;
    private boolean collected;

    public Enemy8(int startX, int startY, Character player) {
        this.x = startX;
        this.y = startY;
        this.width = 120; // Set width of the enemy
        this.height = 120; // Set height of the enemy
        this.health = 1000; // Initial health points
        this.collected = false;
        this.speed = 3; // Movement speed
        this.detectionRange = 120; // Detection range for the player
        this.attackRange = 10;
        loadAnimationFrames();
        loadAttackAnimationFrames();
        currentFrame = 0; // Initialize the current frame
        alive = true; // Set the enemy as alive
        this.player = player; // Set the player reference
        random = new Random(); // Initialize random instance
        startAnimationScheduler(); // Start the animation scheduler
        startMovementThread(); // Start the movement thread
    }

    public int getPoints() {
        
        return 2000;

    }

    public boolean isCollected() {
        return collected;
    }

    private void loadAnimationFrames() {
        frames = new BufferedImage[4]; // Assuming there are 12 frames for walking
        try {
            BufferedImage crawlSpritesheet = ImageIO.read(getClass().getResource("../character/Running (16 x 16).png"));
            for (int i = 0; i < frames.length; i++) {
                frames[i] = crawlSpritesheet.getSubimage(i * 16, 0, 16, 16);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        frameCount = frames.length; // Set the frame count
    }

    private void loadAttackAnimationFrames() {
        attackFrames = new BufferedImage[1]; // 6 frames for attacking animation
        try {
            BufferedImage attackSpritesheet = ImageIO.read(getClass().getResource("../character/Hurt (16 x 16).png"));
            for (int i = 0; i < attackFrames.length; i++) {
                attackFrames[i] = attackSpritesheet.getSubimage(i * 16, 0, 16, 16);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        attackFrameCount = attackFrames.length; // Set the attack frame count
    }

    private void startAnimationScheduler() {
        animationScheduler = Executors.newScheduledThreadPool(1);
        animationScheduler.scheduleAtFixedRate(this::updateAnimation, 0, 150, TimeUnit.MILLISECONDS);
    }

    private void startMovementThread() {
        running = true; // Control for the movement thread
        movementThread = new Thread(this);
        movementThread.start(); // Start the movement thread
    }

    @Override
    public void run() {
        while (running) {
            if (!alive) {
                stopMovementThread();
                return; // Stop updating if the enemy is dead
            }
            updateMovement(); // Update enemy movement
            try {
                Thread.sleep(16); // Sleep to control movement speed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
            }
        }
    }

    private void updateAnimation() {
        if (attacking) {
            attackFrame = (attackFrame + 1) % attackFrameCount; // Loop attack frame
        } else {
            currentFrame = (currentFrame + 1) % frameCount; // Loop walking frame
        }
    }

    private void patrol() {
        // Randomly change direction every few seconds
        if (random.nextInt(100) < 5) {
            movementDirection *= -1; // Change direction randomly
        }
        x += speed * movementDirection; // Move in the current direction
    }

    private void updateMovement() {
        // Log enemy and player positions
        //    System.out.println("Enemy position: " + x + ", " + y);
        //   System.out.println("Current Player position: " + player.getX() + ", " + player.getY());
        // Check if the enemy is triggered by the sensor
        if (isTriggeredBySensor()) {
            //  System.out.println("Enemy detected the player via sensor. Moving towards player.");
            moveTowardsPlayer(); // Move towards the player
            // Check if the enemy is close enough to attack
            if (Math.abs(player.getX()) > attackRange) {
                attack(); // Attack the player
            }
        } else {
            //    System.out.println("Player is out of sensor range. Patrolling.");
            patrol(); // Call the patrol method when not triggered
        }
        // Log the updated positions after the update
        //  System.out.println("Updated Enemy position: " + x + ", Player position: " + player.getX());
    }

    private boolean isTriggeredBySensor() {
        // Check if the player is within the detection range
        float distanceX = Math.abs(player.getX() - x);
        return distanceX < detectionRange; // Trigger if within sensor range
    }

    private void moveTowardsPlayer() {
        // Move towards the player based on their position
        if (x < player.getX()) {
            x += speed; // Move right
            movementDirection = 1; // Set direction to right
        } else if (x > player.getX()) {
            x -= speed; // Move left
            movementDirection = -1; // Set direction to left
        }
        // Log the player's position during movement
        //  System.out.println("Enemy moving towards Player. Current Player position: " + player.getX());
    }

   

    private void attack() {
        if (Math.abs(player.getX() - x) < attackRange && !attacking) { // Ensure the enemy is not already attacking
            attacking = true; // Set attacking to true
         
            player.takeDamage(1); // Reduce player's health by 1
            // Log the attack
            //  System.out.println("Enemy attacked the player. Player health reduced by 1.");
            // After finishing the attack animation, reset attacking
            new Thread(() -> {
                try {
                    Thread.sleep(attackFrameCount * 150); // Wait for the animation to finish
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore the interrupted status
                }
                attacking = false; // Reset attacking status
                attackFrame = 0; // Reset attack frame
            }).start();
        }
    }

    public void draw(Graphics g) {
        if (alive) { // Only draw if the enemy is alive
            Graphics2D g2d = (Graphics2D) g;
            // Draw the enemy based on whether it is attacking
            if (attacking) {
                g2d.drawImage(attackFrames[attackFrame], (int) x, (int) y, width, height, null); // Draw attack animation
            } else {
                // Draw the enemy facing the direction it is moving
                if (movementDirection > 0) {
                    g2d.drawImage(frames[currentFrame], (int) x + width, (int) y, -width, height, null); // Move left (flipped)
                } else {
                    g2d.drawImage(frames[currentFrame], (int) x, (int) y, width, height, null); // Move right
                }
            }
        }
    }

    public void takeDamage(int damage) {
        health -= damage; // Reduce health
       
        if (health <= 0) {
            player.addScore(getPoints());

            alive = false; // Set to dead if health is zero or less
            System.out.println("Enemy has died.");
        }
    }

    public interface Damageable {

        void takeDamage(int amount);
    }

    public void stopMovementThread() {
        running = false; // Stop the movement thread
        movementThread.interrupt(); // Interrupt the thread
        animationScheduler.shutdown(); // Shutdown the animation scheduler
    }

    public boolean isAlive() {
        return alive; // Check if the enemy is alive
    }

    public int getX() {
        return (int) x; // Return x position as integer
    }

    public int getY() {
        return (int) y; // Return y position as integer
    }

    public int getWidth() {
        return width; // Return width
    }

    public int getHeight() {
        return height; // Return height
    }
}
