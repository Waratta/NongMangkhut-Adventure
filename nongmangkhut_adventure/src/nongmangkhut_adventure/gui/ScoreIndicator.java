package nongmangkhut_adventure.gui;

import nongmangkhut_adventure.character_all.Character;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class ScoreIndicator {

    private BufferedImage coinImage; // Image for the coin
    private Character character; // Reference to the Character instance
    private int coinSize = 32; // Size of coin images to display (scaled up)
    private Font customFont; // Variable to hold the custom font

    // Constructor to initialize the score
    public ScoreIndicator(Character character) {
        this.character = character; // Assign the Character instance
        loadCoinImage(); // Load coin image
        customFont = loadCustomFont(); // Load custom font
    }

    // Method to load the coin image from the spritesheet
    private void loadCoinImage() {
        try {
            // Load spritesheet image
            BufferedImage spritesheet = ImageIO.read(getClass().getResource("../item/Coin (16 x 16).png"));
            // Extract frame 0 (top-left corner)
            coinImage = spritesheet.getSubimage(0, 0, 16, 16); // Specify start position (0,0) and size (16x16)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load custom font
    private Font loadCustomFont() {
        try {
            // Load the font file from the fonts directory
            return Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("../fonts/PixeloidSansBold-PKnYd.ttf")).deriveFont(24f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Pixeloid Sans", Font.BOLD, 24); // Fallback to default font if loading fails
        }
    }

    // Method to draw the score indicator on the screen
    public void draw(Graphics g) {
        g.drawImage(coinImage, 34, 75, coinSize, coinSize, null); // Draw the coin image
        g.setColor(Color.WHITE); // Set text color to white
        g.setFont(customFont); // Use the custom font loaded in the constructor
        g.drawString("Score: " + character.getScore(), 74, 100); // Draw the score next to the coin image
    }

    // Method to add points to the score
    public void addScore(int points) {
        character.addScore(points); // Update character's score
    }

    // Getter for current score
    public int getScore() {
        return character.getScore(); // Return current score
    }
}
