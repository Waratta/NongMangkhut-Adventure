package nongmangkhut_adventure.item_all;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.IOException;

import nongmangkhut_adventure.character_all.Character;

public class TreasureChest implements Runnable {

    private int x;
    private int y;
    private int chestSize = 64; // Size of the treasure chest
    private BufferedImage[] frames;
    private int currentFrame;
    private boolean collected;

    private static final int FRAME_TIME = 180; // Time to change frames in milliseconds
    private Thread animationThread;

    public TreasureChest(int x, int y) {
        this.x = x;
        this.y = y;
        this.currentFrame = 0;
        this.collected = false;
        loadFrames();

        animationThread = new Thread(this);
        animationThread.start(); // Start the animation thread
    }

    private void loadFrames() {
        frames = new BufferedImage[3]; // Number of frames for the treasure chest
        try {
            // Load the sprite sheet containing the treasure chest frames
            BufferedImage chestSheet = ImageIO.read(getClass().getResource("/nongmangkhut_adventure/item/Treasure_Chest (32 x 16).png"));

            // Loop through each frame and extract it from the sprite sheet
            for (int i = 0; i < frames.length; i++) {
                frames[i] = chestSheet.getSubimage(i * 32, 0, 32, 16); // Get each frame based on its index
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        if (!collected) {
            g.drawImage(frames[currentFrame], x, y, chestSize, chestSize, null);
        }
    }

    public int getPoints() {
     
        return 1000; // Points received from the treasure chest
    }

    public boolean isCollected() {
        return collected;
    }

    private Rectangle getBounds() {
        return new Rectangle(x, y, 32, 16); // Bounds for collision detection
    }

    public boolean isCollectedBy(Character mainCharacter) {
        Rectangle characterBounds = mainCharacter.getBounds(); // Check for collision
        if (getBounds().intersects(characterBounds) && !collected) {
            collected = true; // Mark the chest as collected
            mainCharacter.addScore(getPoints()); // Add points to the character
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        while (!collected) {
            try {
                Thread.sleep(FRAME_TIME);
                // Update the current frame for animation
                currentFrame = (currentFrame + 1) % frames.length; // Cycle through frames
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

   
}
