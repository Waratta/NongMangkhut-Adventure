package nongmangkhut_adventure.item_all;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.IOException;

import nongmangkhut_adventure.character_all.Character;

public class SilverCoin implements Runnable {

    private int x;
    private int y;
    private int coinSize = 36; 
    private BufferedImage[] frames;
    private int currentFrame;
    private boolean collected;

    private static final int FRAME_TIME = 180;
    private Thread animationThread;

    public SilverCoin(int x, int y) {
        this.x = x;
        this.y = y;
        this.currentFrame = 0;
        this.collected = false;
        loadFrames();

        animationThread = new Thread(this);
        animationThread.start(); 
    }

    private void loadFrames() {
        frames = new BufferedImage[4]; // Adjust the number of frames as needed
        try {
            // Load the sprite sheet containing the coin frames
            BufferedImage coinSheet = ImageIO.read(getClass().getResource("../item/Small_Coin (16 x 16).png"));

            // Loop through each frame and extract it from the sprite sheet
            for (int i = 0; i < frames.length; i++) {
                frames[i] = coinSheet.getSubimage(i * 16, 0, 16, 16); // Get each frame based on its index
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        if (!collected) {
            g.drawImage(frames[currentFrame], x, y, coinSize, coinSize, null);
        }
    }

    public int getPoints() {
       
        return 50; 
    }

    public boolean isCollected() {
        return collected;
    }

    private Rectangle getBounds() {
        return new Rectangle(x, y, 16, 16);
    }

    public boolean isCollectedBy(Character mainCharacter) {
        Rectangle characterBounds = mainCharacter.getBounds(); 
        if (getBounds().intersects(characterBounds) && !collected) {
            collected = true; 
            mainCharacter.addScore(getPoints());
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

   public void setCollected(boolean collected) {
    this.collected = collected;
}

}
