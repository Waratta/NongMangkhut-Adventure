package nongmangkhut_adventure.L_map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import nongmangkhut_adventure.NongMangkhut_Adventure;

public class MapSelectionScreen extends JFrame implements ActionListener {
    private JButton level1Button;
    private JButton level2Button;
    private JButton level3Button;
    private Image backgroundImage;

    public MapSelectionScreen() {
        setTitle("Select Level");

        // Load background image
        backgroundImage = new ImageIcon(getClass().getResource("/nongmangkhut_adventure/map/Asset 4.png")).getImage();

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            setUndecorated(true); 
            gd.setFullScreenWindow(this); 
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create a JPanel with background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Scale and draw the background image to fit the panel's size
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        backgroundPanel.setPreferredSize(new Dimension(1920, 1080)); // Set fixed size for background panel

        // Load images for buttons without resizing
        ImageIcon level1Icon = new ImageIcon(getClass().getResource("/nongmangkhut_adventure/map/Background (1) (3).png"));
        ImageIcon level2Icon = new ImageIcon(getClass().getResource("/nongmangkhut_adventure/map/Background (1) (2).png"));
        ImageIcon level3Icon = new ImageIcon(getClass().getResource("/nongmangkhut_adventure/map/Background (1) (1).png"));

        // Scale the button icons to fit the button size (500x200)
        level1Icon = scaleImageIcon(level1Icon, 500, 200);
        level2Icon = scaleImageIcon(level2Icon, 500, 200);
        level3Icon = scaleImageIcon(level3Icon, 500, 200);

        // Create buttons with resized image icons
        level1Button = new JButton(level1Icon);
        level2Button = new JButton(level2Icon);
        level3Button = new JButton(level3Icon);

        // Configure buttons for a cleaner look
        configureButton(level1Button);
        configureButton(level2Button);
        configureButton(level3Button);

        // Add action listeners to buttons
        level1Button.addActionListener(this);
        level2Button.addActionListener(this);
        level3Button.addActionListener(this);

        // Add buttons to background panel with the same layout and position
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(-150, 20,150, 400); // Add space between buttons

        // Start from the top and center buttons horizontally
        gbc.gridx = 0; // Center horizontally
        gbc.gridy = 0; // Start from the top
        gbc.anchor = GridBagConstraints.NORTH; // Align to the top
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make buttons fill horizontally

        // Add the first button at the top
        backgroundPanel.add(level1Button, gbc);

        // Move the second button below the first one
        gbc.gridy = 1;  
        backgroundPanel.add(level2Button, gbc);

        // Move the third button below the second one
        gbc.gridy = 2;  
        backgroundPanel.add(level3Button, gbc);

        // Add background panel to JFrame
        add(backgroundPanel);
        pack(); // Adjust size based on background panel
        setResizable(false);
    }

    private void configureButton(JButton button) {
        button.setText(null);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
    }

    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedLevel = -1;

        if (e.getSource() == level1Button) {
            selectedLevel = 0;
        } else if (e.getSource() == level2Button) {
            selectedLevel = 1;
        } else if (e.getSource() == level3Button) {
            selectedLevel = 2;
        }

        if (selectedLevel != -1) {
            startGame(selectedLevel);
            this.dispose();
        }
    }

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
            gameFrame.setVisible(true); 
            gameFrame.setResizable(false); 
        });
    }
}
