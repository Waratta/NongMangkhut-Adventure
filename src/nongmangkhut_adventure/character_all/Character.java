package nongmangkhut_adventure.character_all;
import nongmangkhut_adventure.sound.SoundManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.awt.geom.AffineTransform;

import java.util.ArrayList;

import nongmangkhut_adventure.gui.HealthIndicator;

public class Character implements Runnable, Damageable {
 private SoundManager soundManager;
 
    private int x, y;
    private int frameIndex;
    private BufferedImage[] runFrames;
    private BufferedImage[] attackFrames;
    private Image jumpFrame;
    private boolean movingLeft, movingRight, jumping, attacking;
    private int jumpSpeed, gravity, fallSpeed;
    private boolean facingRight;
    private int walkSpeed = 5;
    private boolean running;
    private int characterWidth = 75;
    private int characterHeight = 75;
    private BufferedImage spritesheet;
    private ArrayList<Fireball> fireballs; // Store fireballs
    private int health; // Health points
    private boolean dead; // Death status
    private int attackPower;
    private BufferedImage deathFrame; // Death animation frame
    private HealthIndicator healthIndicator;

    private int score;

    public Character() {
        Rectangle characterRect = new Rectangle(x, y, characterWidth, characterHeight);
        x = 100; // Starting horizontal position
        y = 700; // Initial y position (ground level)
        frameIndex = 0;
        facingRight = true;
        running = true;
        attacking = false;
            this.soundManager = new SoundManager(); 
        dead = false; // Start alive
        health = 3; // Initial health
        healthIndicator = new HealthIndicator(health);
        fireballs = new ArrayList<>(); // Create ArrayList for fireballs
        this.score = 0;
        try {
            // Load spritesheet
            spritesheet = ImageIO.read(getClass().getResource("/nongmangkhut_adventure/character/Running_(32 x 32).png"));
            runFrames = new BufferedImage[6];
            for (int i = 0; i < 6; i++) {
                runFrames[i] = spritesheet.getSubimage(i * 32, 0, 32, 32);
            }
            jumpFrame = new ImageIcon(getClass().getResource("/nongmangkhut_adventure/character/Jumping_1.png")).getImage();
            attackFrames = new BufferedImage[4];
            BufferedImage attackSheet = ImageIO.read(getClass().getResource("/nongmangkhut_adventure/character/Casting_Spell_Aerial_(32 x 32).png"));
            for (int i = 0; i < 4; i++) {
                attackFrames[i] = attackSheet.getSubimage(i * 32, 0, 32, 32);
            }
            // Load death frame
            deathFrame = ImageIO.read(getClass().getResource("/nongmangkhut_adventure/character/Ducking_(32 x 32).png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        gravity = 1;
        fallSpeed = 9;
        jumping = false;
        // Start the animation thread
        new Thread(this).start();
    }
    
private void checkCollision(List<Rectangle> hitboxes) {
    Rectangle characterRect = new Rectangle(x, y, characterWidth, characterHeight);
    for (Rectangle hitbox : hitboxes) {
        if (characterRect.intersects(hitbox)) {
            // จัดการการชนที่เกิดขึ้น เช่น ปรับตำแหน่งกลับ
            if (movingLeft) {
                x += walkSpeed; // ย้อนกลับเมื่อเคลื่อนที่ซ้าย
            } else if (movingRight) {
                x -= walkSpeed; // ย้อนกลับเมื่อเคลื่อนที่ขวา
            }
            // ถ้าตกลงพื้น ให้ปรับตำแหน่งให้พอดีกับ hitbox
            if (y + characterHeight > hitbox.y) {
                y = hitbox.y - characterHeight; // ปรับ y ให้อยู่บน hitbox
                fallSpeed = 0; // รีเซ็ตความเร็วในการตก
                jumping = false; // ตั้งค่า jumping เป็น false
            }
        }
    }
}
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform transform = new AffineTransform();
        if (facingRight) {
            transform.translate(x, y);
        } else {
            transform.translate(x + characterWidth, y);
            transform.scale(-1, 1);
        }
        transform.scale((double) characterWidth / runFrames[frameIndex].getWidth(null),
                (double) characterHeight / runFrames[frameIndex].getHeight(null));
        // Check if character is dead
        if (dead) {
            g2d.drawImage(deathFrame, transform, null);
            return; // Do not draw other animations if dead
        } else if (jumping) {
            g2d.drawImage(jumpFrame, transform, null);
        } else if (attacking) {
            g2d.drawImage(attackFrames[frameIndex % attackFrames.length], transform, null);
        } else if (movingLeft || movingRight) {
            g2d.drawImage(runFrames[frameIndex], transform, null);
        } else {
            g2d.drawImage(runFrames[0], transform, null);
        }
        // Draw fireballs
        for (Fireball fireball : fireballs) {
            fireball.draw(g); // Draw fireballs
        }
        healthIndicator.draw(g);
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;

    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;

    }

    public void jump() {
        if (!jumping && !dead) {
            jumping = true;
           soundManager.playSound("cartoon-jump-6462.wav");
        }
    }

  
    public void attack() {
        if (!attacking && !dead) {
            attacking = true;
            frameIndex = 0;
            new Thread(() -> {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                attacking = false;
            }).start();
            shootFireball(); // Create fireball when attacking
        }
    }

    private void shootFireball() {
        if (!dead) { // Create fireball only if alive
            int fireballX = x + (facingRight ? characterWidth : 0); // Adjust X position based on direction
            int fireballY = y + characterHeight / 2 - 10; // Adjust Y position to center character
            fireballs.add(new Fireball(fireballX, fireballY, facingRight, this)); // Create fireball
        }
    }

    public void takeDamage(int par) {
        if (!dead) {
            health--; // Reduce health
            healthIndicator.takeDamage();
            soundManager.playSound("retro-hurt-1-236672.wav");
            if (health <= 0) {

               soundManager.playSound("mixkit-player-losing-or-failing-2042.wav");
                die(); // Trigger death

            }
        }
    }

    public void increaseAttackPower(int amount) {
        this.attackPower += amount; // Increase attack power
        System.out.println(attackPower);
    }

    private void die() {
        dead = true; // Mark as dead
        // Optionally, you can also handle post-death logic here if needed
    }

    public int getAttackPower() {

        return attackPower; // Get current attack power
    }

    private void handleJumping(List<Rectangle> hitboxes, int screenHeight) {
        y -= jumpSpeed;
        jumpSpeed -= gravity;
        Rectangle characterFeet = new Rectangle(x, y + characterHeight, characterWidth, 1);
        for (Rectangle hitbox : hitboxes) {
            if (hitbox.intersects(characterFeet) && jumpSpeed < 0) {
                y = hitbox.y - characterHeight;
                jumping = false;
                // Reset jump speed
                return;
            }
        }
        if (y > screenHeight - characterHeight) {
            jumping = false;
            y = screenHeight - characterHeight; // Reset position
        }
    }

    public void update(List<Rectangle> hitboxes, List<Enemy> enemies, List<Enemy1> enemies1,
            List<Enemy2> enemies2, List<Enemy3> enemies3, List<Enemy4> enemies4, List<Enemy5> enemies5, List<Enemy6> enemies6, List<Enemy7> enemies7, List<Enemy8> enemies8, int screenWidth, int screenHeight) {
        if (dead) {
            return; // If dead, skip any updates
        }        // Handle jumping
        if (jumping) {
            handleJumping(hitboxes, screenHeight);
        } else {
            handleFalling(hitboxes, screenHeight);
        }
        // Handle horizontal movement
        if (movingLeft) {
            moveLeft(hitboxes);
        }
        if (movingRight) {
            moveRight(hitboxes);
        }

        // Update fireballs
        for (int i = fireballs.size() - 1; i >= 0; i--) {
            Fireball fireball = fireballs.get(i);
            fireball.update(); // Update fireball position

            boolean collided = false; // Flag to check if fireball collided
            // Check for collision with regular enemies
            for (Enemy enemy : enemies) {
                if (fireball.checkCollision(enemy)) {
                    enemy.takeDamage(fireball.getDamage()); // Handle enemy hit
                    collided = true; // Set the flag
                    break; // Exit the loop after hit
                }
            }

            // Check for collision with Enemy1
            for (Enemy1 enemy1 : enemies1) {
                if (fireball.checkCollision(enemy1)) {
                    enemy1.takeDamage(fireball.getDamage()); // Handle Enemy1 hit

                    collided = true; // Set the flag

                    break; // Exit the loop after hit
                }
            }
            for (Enemy2 enemy2 : enemies2) {
                if (fireball.checkCollision(enemy2)) {
                    enemy2.takeDamage(fireball.getDamage()); // Handle Enemy1 hit

                    collided = true; // Set the flag

                    break; // Exit the loop after hit
                }
            }
            for (Enemy3 enemy3 : enemies3) {
                if (fireball.checkCollision(enemy3)) {
                    enemy3.takeDamage(fireball.getDamage()); // Handle Enemy1 hit

                    collided = true; // Set the flag

                    break; // Exit the loop after hit
                }
            }
            for (Enemy4 enemy4 : enemies4) {
                if (fireball.checkCollision(enemy4)) {
                    enemy4.takeDamage(fireball.getDamage()); // Handle Enemy1 hit

                    collided = true; // Set the flag

                    break; // Exit the loop after hit
                }

            }
            for (Enemy5 enemy5 : enemies5) {
                if (fireball.checkCollision(enemy5)) {
                    enemy5.takeDamage(fireball.getDamage()); // Handle Enemy1 hit

                    collided = true; // Set the flag

                    break; // Exit the loop after hit
                }

            }
            for (Enemy6 enemy6 : enemies6) {
                if (fireball.checkCollision(enemy6)) {
                    enemy6.takeDamage(fireball.getDamage()); // Handle Enemy1 hit

                    collided = true; // Set the flag

                    break; // Exit the loop after hit
                }

            }
            for (Enemy7 enemy7 : enemies7) {
                if (fireball.checkCollision(enemy7)) {
                    enemy7.takeDamage(fireball.getDamage()); // Handle Enemy1 hit

                    collided = true; // Set the flag

                    break; // Exit the loop after hit
                }

            }
            for (Enemy8 enemy8 : enemies8) {
                if (fireball.checkCollision(enemy8)) {
                    enemy8.takeDamage(fireball.getDamage()); // Handle Enemy1 hit

                    collided = true; // Set the flag

                    break; // Exit the loop after hit
                }

            }
            // Remove fireball if it collided or it's not visible
            if (collided || !fireball.isVisible()) {
                fireballs.remove(i); // Remove fireball from the list
            }
        }

    }

    private void handleFalling(List<Rectangle> hitboxes, int screenHeight) {
    boolean onGround = false; // สถานะการยืนอยู่บนพื้น
    Rectangle characterFeet = new Rectangle(x, y + characterHeight, characterWidth, 1);

    for (Rectangle hitbox : hitboxes) {
        if (hitbox.intersects(characterFeet)) {
            onGround = true; 
            y = hitbox.y - characterHeight; 
            jumpSpeed = 16; 
            break; 
        }
    }

   
if (!onGround) {
    if (jumpSpeed < 0) {
       
        y += jumpSpeed; 
        jumpSpeed += gravity; 
    } else {
       
        y += fallSpeed; 
    }
}

    if (onGround) {
    fallSpeed = 0; 
} else {
    fallSpeed += gravity; 
}


   
    if (y > screenHeight - characterHeight) {
        y = screenHeight - characterHeight; 
    }
}


    private void moveLeft(List<Rectangle> hitboxes) {
        Rectangle nextPosition = new Rectangle(x - walkSpeed, y, characterWidth, characterHeight);
        for (Rectangle hitbox : hitboxes) {
            if (nextPosition.intersects(hitbox)) {
                x = hitbox.x + hitbox.width ;
                return;
            }
        }
        x -= walkSpeed;checkCollision(hitboxes);
        facingRight = false; // Adjust direction
    }

    private void moveRight(List<Rectangle> hitboxes) {
        Rectangle nextPosition = new Rectangle(x + walkSpeed, y, characterWidth, characterHeight);
        for (Rectangle hitbox : hitboxes) {
            if (nextPosition.intersects(hitbox)) {
                x = hitbox.x - characterWidth;
                return;
            }
        }
        x += walkSpeed; checkCollision(hitboxes); 
        facingRight = true; // Adjust direction
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return characterWidth;
    }

    public int getHeight() {
        return characterHeight;
    }

    public int getHealth() {
        return health; // Return the current health points
    }

    private void updateAnimation() {
        if (attacking) {
            frameIndex = (frameIndex + 1) % attackFrames.length; // Cycle through attack frames
        } else {
            frameIndex = (frameIndex + 1) % runFrames.length; // Cycle through run frames
        }
    }

    public HealthIndicator getHealthIndicator() {
        return healthIndicator;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(100); // Control frame rate
                updateAnimation(); // Update animation frame
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateHealth(int amount) {
        // Assuming you have a health variable to track the character's health
        this.health += amount; // Increase health by the specified amount
        // Ensure health does not exceed maximum health if you have a maximum limit
        if (this.health > health) {
            this.health = health;
        }
        // Optionally, you can add code to update the health indicator UI here
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, characterWidth, characterHeight);
    }

    public void addScore(int points) {
         soundManager.playSound("90s-game-ui-7-185100.wav");
        this.score += points; // Increase score by given points
        System.out.println("coin: " + score);
    }

    // Getter for score
    public int getScore() {

        return score;
    }

    public Fireball getLastFireball() {
        if (!fireballs.isEmpty()) {
            return fireballs.get(fireballs.size() - 1); // Get the last fireball
        }
        return null; // Return null if no fireballs exist
    }

   
    public void setScore(int i) {

   this.score = 0;
    }

    public void reset() {
          this.health = 3; 
        this.score = 0;
    }

}
