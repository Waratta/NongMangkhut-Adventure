package nongmangkhut_adventure.item_all;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.IOException;

import nongmangkhut_adventure.character_all.Character;

public class Coin implements Runnable {

    private int x;
    private int y;
    private int heartSize = 32;
    private BufferedImage[] frames;
    private int currentFrame;
    private boolean collected;

    private static final int FRAME_TIME = 180; // Frame change time in milliseconds
    private Thread animationThread;

    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
        this.currentFrame = 0;
        this.collected = false;
        loadFrames();

        animationThread = new Thread(this);
        animationThread.start(); // Start the animation thread
    }

    private void loadFrames() {
        frames = new BufferedImage[4];
        try {
            BufferedImage coinSheet = ImageIO.read(getClass().getResource("../item/Coin (16 x 16).png"));
            for (int i = 0; i < frames.length; i++) {
                frames[i] = coinSheet.getSubimage(i * 16, 0, 16, 16);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        if (!collected) {
            g.drawImage(frames[currentFrame], x, y, heartSize, heartSize, null);
        }
    }

    public int getPoints() {
       
        return 100;

    }

    

    public boolean isCollected() {
        return collected;
    }

    private Rectangle getBounds() {
        return new Rectangle(x, y, 16, 16);
    }

    public boolean isCollectedBy(Character mainCharacter) {
        Rectangle characterBounds = mainCharacter.getBounds(); // Ensure getBounds() is implemented in Character
        if (getBounds().intersects(characterBounds) && !collected) {
            collected = true; // Mark the coin as collected
            mainCharacter.addScore(getPoints()); // Add points to the character's score
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        while (!collected) {
            try {
                Thread.sleep(FRAME_TIME);
                currentFrame = (currentFrame + 1) % frames.length;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
