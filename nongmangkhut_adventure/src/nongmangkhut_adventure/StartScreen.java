package nongmangkhut_adventure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import nongmangkhut_adventure.L_map.MapSelectionScreen;

public class StartScreen extends JFrame implements ActionListener {

    private JButton startButton;
    private JButton exitButton; // New exit button
    private Image backgroundImage;
    private Font customFont;

    public StartScreen() {
        setTitle("Nong Mangkhut Adventure");

        // Load custom font
        customFont = loadCustomFont();

        // Load background image
        backgroundImage = new ImageIcon(getClass().getResource("images/Asset 4.png")).getImage();

        // Set full screen
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            setUndecorated(true); // Hide window decorations
            gd.setFullScreenWindow(this); // Set to full-screen
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Start button
        startButton = createStyledButton("Start");
        startButton.addActionListener(this);

        // Exit button
        exitButton = createStyledButton("Exit");
        exitButton.addActionListener(e -> System.exit(0)); // Exit the application

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make panel transparent
        buttonPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between buttons
        gbc.gridx = 0; // Column
        gbc.gridy = 0; // Row
        buttonPanel.add(startButton, gbc);

        gbc.gridy = 1; // Move to the next row for the exit button
        buttonPanel.add(exitButton, gbc);

        // Center panel
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.CENTER);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Add a custom panel for painting
        JPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);
        setContentPane(backgroundPanel);
    }

    // Method to load the custom font
    private Font loadCustomFont() {
        try {
            // Load the font file from the fonts directory
            return Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("./fonts/PixeloidSansBold-PKnYd.ttf")).deriveFont(24f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Pixeloid Sans", Font.BOLD, 24); // Fallback to default font if loading fails
        }
    }

    // Create a styled button with the custom font
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 60)); // Set button size
        button.setFont(customFont); // Set custom font
        button.setForeground(Color.WHITE); // Set text color
        button.setBackground(new Color(0, 128, 255)); // Set background color (bright blue)
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // 8-bit style border
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor on hover

        // Add mouse hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 153, 255)); // Change to a lighter blue on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 128, 255)); // Reset to original color
            }
        });

        return button;
    }

    // Custom panel for background image
    private class BackgroundPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw background image to fill the panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Open map selection screen
        SwingUtilities.invokeLater(() -> {
            MapSelectionScreen mapSelectionScreen = new MapSelectionScreen();
            mapSelectionScreen.setVisible(true);
        });
        this.dispose(); // Close start screen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartScreen startScreen = new StartScreen();
            startScreen.setVisible(true); // Make the start screen visible
        });
    }
}
