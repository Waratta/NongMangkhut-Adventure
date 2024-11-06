package nongmangkhut_adventure.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HealthIndicator {

    private BufferedImage[] heartFrames; // Array for heart frames
    private int maxHealth; // Maximum health points
    private int currentHealth; // Current health points
    private int heartSize = 36; // Size of heart images to display (scaled up)

    // Constructor now takes a reference to the character's health instead of a fixed maxHealth
    public HealthIndicator(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth; // Initialize current health to max
        loadHeartImages(); // Load heart images
    }

    // Method to load heart images from spritesheet
    private void loadHeartImages() {
        heartFrames = new BufferedImage[2]; // 2 frames for healthy and damaged hearts
        try {
            BufferedImage heartSpriteSheet = ImageIO.read(getClass().getResource("../item/Health_Indicator_Black_Outline (8 x 8).png"));
            // Subimage for healthy heart (8x8)
            heartFrames[0] = heartSpriteSheet.getSubimage(0, 0, 8, 8); // Keep original size for subimage
            // Subimage for damaged heart (8x8)
            heartFrames[1] = heartSpriteSheet.getSubimage(8, 0, 8, 8); // Keep original size for subimage
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to draw the health indicator on the screen
    public void draw(Graphics g) {
        for (int i = 0; i < maxHealth; i++) {
            if (i < currentHealth) {
                // Draw healthy heart, scale it up to heartSize
                g.drawImage(heartFrames[0], 30 + (i * heartSize), 30, heartSize, heartSize, null);
            } else {
                // Draw damaged heart, scale it up to heartSize
                g.drawImage(heartFrames[1], 30 + (i * heartSize), 30, heartSize, heartSize, null);
            }
        }
    }

    // Method to reduce health when damage is taken
    public void takeDamage() {
        if (currentHealth > 0) {
            currentHealth--; // Reduce health
        }
    }

    // Method to set the current health directly (useful for when healing is implemented)
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = Math.max(0, Math.min(currentHealth, maxHealth)); // Ensure health stays within bounds
    }

    // Getter for current health
    public int getCurrentHealth() {
        return currentHealth; // Return current health
    }

    // Getter for maximum health
    public int getMaxHealth() {
        return maxHealth; // Return maximum health
    }

    public void collectHealth(int amount) {
        setCurrentHealth(currentHealth + amount);
    }
}
