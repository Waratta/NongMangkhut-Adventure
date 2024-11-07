package nongmangkhut_adventure.item_all;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.IOException;

import nongmangkhut_adventure.character_all.Character;

public class Tree2  {

    private int x;
    private int y;
    private int heartSize = 124;
    private BufferedImage frames;
    private int currentFrame;
    private boolean collected;

    private static final int FRAME_TIME = 180; // Frame change time in milliseconds
    private Thread animationThread;

    public Tree2(int x, int y, Character mainCharacter) {
        this.x = x;
        this.y = y;
        this.currentFrame = 0;
        this.collected = false;
        loadFrames();

        
      
    }

    private void loadFrames() {
    
        try {
               frames =ImageIO.read(getClass().getResource("/nongmangkhut_adventure/map/Grassland_entities (16 x 16).png"));
          
           
                frames = frames.getSubimage( 128, 16, 64, 64);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        if (!collected) {
            g.drawImage(frames, x, y, heartSize, heartSize, null);
        }
    }

  

    

  

    private Rectangle getBounds() {
        return new Rectangle(x, y, 16, 16);
    }

    

 

    

}
