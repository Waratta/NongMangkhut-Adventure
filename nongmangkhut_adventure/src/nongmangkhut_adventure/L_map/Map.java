package nongmangkhut_adventure.L_map;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Map {

    private BufferedImage tileImage; // Image tile
    private int tileSize; // tile
    private int[][] mapLayout; 
    private List<Rectangle> hitboxes; //  hitboxes
    private int screenWidth; 
    private int screenHeight; 

    public Map(int[][] layout, int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.mapLayout = layout; 

        try {
            tileImage = ImageIO.read(getClass().getResource("../map/Terrain1.png")); 
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.tileSize = 64; 
        this.hitboxes = new ArrayList<>(); 
        createHitboxes(); 
    }

  
    public int getWidth() {
        return mapLayout[0].length;
    }

    
    public int getHeight() {
        return mapLayout.length; 
    }

   
    private void createHitboxes() {
        for (int row = 0; row < mapLayout.length; row++) {
            for (int col = 0; col < mapLayout[row].length; col++) {
                if (mapLayout[row][col] == 1) { 
                    Rectangle hitbox = new Rectangle(col * tileSize, row * tileSize + 5, tileSize, tileSize);
                    hitboxes.add(hitbox);
                }
            }
        }
    }


    public void drawMap(Graphics g, int cameraX, int cameraY) {
      
        int cols = screenWidth / tileSize + 1; 
        int rows = screenHeight / tileSize + 1; 

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                
                int tileX = col * tileSize - cameraX;
                int tileY = row * tileSize - cameraY;

               
                if (row < mapLayout.length && col < mapLayout[row].length && mapLayout[row][col] != 0) {
                    g.drawImage(tileImage, tileX, tileY, tileSize + 12, tileSize + 18, null);
                }
            }
        }
    }

    public List<Rectangle> getHitboxes() {
        return hitboxes; 
    }

    public int getTileSize() {
        return tileSize; 
    }

  

}
