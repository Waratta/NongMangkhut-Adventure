package nongmangkhut_adventure.L_map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import nongmangkhut_adventure.NongMangkhut_Adventure;

public class MapSelectionScreen extends JFrame implements ActionListener {
    private JButton level1Button;
    private JButton level2Button;
    private JButton level3Button; 

    public MapSelectionScreen() {
        setTitle("Select Level");

       
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            setUndecorated(true); 
            gd.setFullScreenWindow(this); 
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        level1Button = new JButton("Level 1");
        level2Button = new JButton("Level 2");
        level3Button = new JButton("Level 3");

        // Add action listeners to buttons
        level1Button.addActionListener(this);
        level2Button.addActionListener(this);
        level3Button.addActionListener(this);

        // Set layout and add buttons
        setLayout(new GridLayout(3, 1));
        add(level1Button);
        add(level2Button);
        add(level3Button);

        setResizable(false); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedLevel = -1;

        // Determine which level was selected
        if (e.getSource() == level1Button) {
            selectedLevel = 0;
        } else if (e.getSource() == level2Button) {
            selectedLevel = 1;
        } else if (e.getSource() == level3Button) {
            selectedLevel = 2;
        }

        // Start the game with the selected level
        if (selectedLevel != -1) {
            startGame(selectedLevel);
            this.dispose(); // Close the map selection screen
        }
    }

    // Method to start the game
    private void startGame(int selectedLevel) {
        SwingUtilities.invokeLater(() -> {
            JFrame gameFrame = new JFrame("Nong Mangkhut Adventure");

            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            if (gd.isFullScreenSupported()) {
                gameFrame.setUndecorated(true); 
                gd.setFullScreenWindow(gameFrame); 
            }

            NongMangkhut_Adventure game = new NongMangkhut_Adventure(selectedLevel);
            gameFrame.add(game);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true); // Make the window visible
            gameFrame.setResizable(false); 
        });
    }
}
