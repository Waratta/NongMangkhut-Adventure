package nongmangkhut_adventure.character_all;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.List;

public class Pet implements Runnable {

    private float x, y; // Position of the pet
    private float yVelocity; // Vertical velocity for falling
    private boolean onGround; // Check if the pet is on the ground
    private BufferedImage[] walkFrames; // Pet walking frames
    private int frameIndex;
    private boolean facingRight;
    private float walkSpeed = 4; // Pet speed
    private float gravity = 1; // Gravity value
    private float jumpForce = -15; // Jump force
    private int petWidth = 58; // Width of the pet
    private int petHeight = 42; // Height of the pet
    private volatile boolean running = true; // Control the thread's running state

    public Pet(int startX, int startY) {
        x = startX;
        y = startY;
        yVelocity = 0;
        frameIndex = 0;
        facingRight = true;

        // Load spritesheet for the pet
        try {
            BufferedImage petSpritesheet = ImageIO.read(getClass().getResource("/nongmangkhut_adventure/character/Walking_(48 x 32).png"));
            walkFrames = new BufferedImage[4]; // Number of walking frames
            for (int i = 0; i < 4; i++) {
                walkFrames[i] = petSpritesheet.getSubimage(i * 48, 0, 48, 32); // Set walking frames
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update pet position and state based on character position and hitboxes.
     *
     * @param characterX X position of the character.
     * @param characterY Y position of the character.
     * @param hitboxes List of hitboxes to check for collisions.
     */
    public void update(int characterX, int characterY, List<Rectangle> hitboxes) {
        // Apply gravity
        if (!onGround) {
            yVelocity += gravity; // Increase downward velocity
        } else {
            yVelocity = 0; // Reset vertical velocity when on the ground
        }

        // Update vertical position based on velocity
        y += yVelocity;

        // Check for collisions with hitboxes
        checkCollisions(hitboxes);

        // Move towards the character if far away
        float deltaX = characterX - x;
        if (Math.abs(deltaX) > 1) {
            // Adjust speed
            if (Math.abs(deltaX) > walkSpeed) {
                x += Math.signum(deltaX) * walkSpeed; // Move towards the character
            } else {
                x = characterX; // Snap to character position
            }

            facingRight = deltaX < 0; // Determine facing direction
            frameIndex = (frameIndex + 1) % walkFrames.length; // Update walking frame
        } else {
            frameIndex = 0; // Reset to the first frame when stopped
        }
    }

    private void checkCollisions(List<Rectangle> hitboxes) {
        for (Rectangle hitbox : hitboxes) {
            if (getBounds().intersects(hitbox)) {
                y = hitbox.y - petHeight; // Set pet's Y position above the hitbox
                yVelocity = 0; // Reset velocity
                onGround = true; // Set onGround to true
                return; // Exit after collision
            }
        }
        onGround = false; // Set onGround to false if no collision
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // Adjust pet image based on direction
        if (facingRight) {
            g2d.drawImage(walkFrames[frameIndex], (int) x, (int) y, petWidth, petHeight, null);
        } else {
            g2d.drawImage(walkFrames[frameIndex], (int) x + petWidth, (int) y, -petWidth, petHeight, null);
        }
    }

    @Override
    public void run() {
        while (running) {
            // Update logic can be placed here if needed
            // Adjust the update rate here or call update directly if needed
            try {
                Thread.sleep(100); // Control the update frequency
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false; // Stop the thread
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, petWidth, petHeight); // Return pet's bounding rectangle
    }

    public void jump() {
        if (onGround) {
            yVelocity = jumpForce; // Apply jump force if on the ground
            onGround = false; // Set onGround to false after jumping
        }
    }
}
