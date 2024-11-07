package nongmangkhut_adventure.item_all;

import nongmangkhut_adventure.character_all.Character;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class HealthItem {

    private int x, y; // Position of the item
    private BufferedImage sprite; // Image for the item
    private int heartSize = 16; // Size of the heart image
    private boolean collected; // State to check if the item has been collected

    public HealthItem(int x, int y) {
        this.x = x;
        this.y = y;
        loadSprite(); // Load the sprite image
        this.collected = false; // Initialize as not collected
    }

    private void loadSprite() {
        try {
            sprite = ImageIO.read(getClass().getResource("/nongmangkhut_adventure/item/Pancake_Stack (16 x 16).png"));
            // Load your image here
            // Only use the first frame (0, 0)
            sprite = sprite.getSubimage(0, 0, 16, 16); // Get the single heart image
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        if (!collected) { // ตรวจสอบว่าไอเท็มยังไม่ถูกเก็บ
            g.drawImage(sprite, x, y, heartSize * 2, heartSize * 2, null); // วาดภาพ
        }
    }

    public int getX() {
        return (int) x; // Get x position
    }

    public int getY() {
        return (int) y; // Get y position
    }

    public BufferedImage getSprite() {
        return sprite; // Get the sprite image
    }

    // Method to check collision with the player
    public boolean checkCollision(int playerX, int playerY, int playerWidth, int playerHeight) {
        return (x < playerX + playerWidth && x + heartSize > playerX
                && y < playerY + playerHeight && y + heartSize > playerY);
    }

    public void collect(Character character) {
        collected = true; // Set the item as collected
        character.getHealthIndicator().collectHealth(1);
        // Add logic to increase player health or update health indicator here
    }

    public boolean isCollected() {
        return collected; // Return the collected state
    }
}
