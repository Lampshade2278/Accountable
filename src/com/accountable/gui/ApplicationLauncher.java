package com.accountable.gui;

import javax.swing.*;
import java.awt.*;

public class ApplicationLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            setupApplication();

            JFrame launchWindow = new JFrame("Accountable - Launch");
            launchWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            launchWindow.setSize(810, 800);
            launchWindow.setLayout(null); // We're using null layout for absolute positioning

            BackgroundPanel backgroundPanel = new BackgroundPanel();
            backgroundPanel.setLayout(null); // Set null layout for the background panel as well
            backgroundPanel.setBounds(0, 0, 810, 800);
            launchWindow.add(backgroundPanel);

            // Define the button properties
            int buttonWidth = 150;
            int buttonHeight = 75;
            int buttonY = 590;
            int buttonSpacing = 20;

            // Calculate the x positions for the buttons
            int centerX = launchWindow.getWidth() / 2;
            int loginButtonX = (centerX + 70) - (buttonWidth + buttonSpacing / 2) - (buttonWidth / 2); // Centered
            int registerButtonX = centerX + (buttonSpacing / 2); // Centered

            // Create and add the login button
            JButton loginButton = new JButton("Login");
            loginButton.setBounds(loginButtonX, buttonY, buttonWidth, buttonHeight);
            loginButton.addActionListener(e -> {
                launchWindow.dispose();
                new LoginWindow().setVisible(true);
            });
            backgroundPanel.add(loginButton); // Add the button to the background panel

            // Create and add the register button
            JButton registerButton = new JButton("Register");
            registerButton.setBounds(registerButtonX, buttonY, buttonWidth, buttonHeight);
            registerButton.addActionListener(e -> {
                launchWindow.dispose();
                new RegistrationWindow().setVisible(true);
            });
            backgroundPanel.add(registerButton); // Add the button to the background panel

            launchWindow.setLocationRelativeTo(null);
            launchWindow.setVisible(true);
        });
    }

    private static void setupApplication() {
        // Example to set the look and feel to the system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            // Load the background image
            backgroundImage = new ImageIcon(getClass().getResource("/splash_background.png")).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the background image
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    // Assume LoginWindow and RegistrationWindow classes are defined elsewhere
}




/* UNUSED CODE

package com.accountable.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            setupApplication();

            JFrame launchWindow = new JFrame("Accountable - Launch");
            launchWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            launchWindow.setSize(810, 800);
            launchWindow.setLayout(null);

            BackgroundPanel backgroundPanel = new BackgroundPanel();
            backgroundPanel.setBounds(0, 0, 810, 800);
            backgroundPanel.setOpaque(true);
            launchWindow.add(backgroundPanel);

            // Assume logoLabel is added here with the correct size and at the correct position

            int buttonWidth = 150;
            int buttonHeight = 50;
            int centerX = launchWindow.getWidth() / 2;
            int buttonY = 575 + 50;
            int buttonSpacing = 20;

            int loginButtonX = centerX - (buttonWidth + buttonSpacing / 2);
            int registerButtonX = centerX + buttonSpacing / 2;

            JButton loginButton = new JButton("Login");
            loginButton.setBounds(loginButtonX, buttonY, buttonWidth, buttonHeight);
            loginButton.addActionListener(e -> {
                launchWindow.dispose();
                new LoginWindow().setVisible(true);
            });
            launchWindow.add(loginButton);

            JButton registerButton = new JButton("Register");
            registerButton.setBounds(registerButtonX, buttonY, buttonWidth, buttonHeight);
            registerButton.addActionListener(e -> {
                launchWindow.dispose();
                new RegistrationWindow().setVisible(true);
            });
            launchWindow.add(registerButton);


            launchWindow.setLocationRelativeTo(null);
            launchWindow.setVisible(true);
            launchWindow.revalidate();
            launchWindow.repaint();
        });
    }

    private static void setupApplication() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                // Ensures that the image is loaded from the resources folder in a way that works inside a JAR
                backgroundImage = new ImageIcon(getClass().getResource("/splash_background.png")).getImage();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to load background image.");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Debug: Check if method is called
            System.out.println("paintComponent called");

            //If there's an image,
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            } else {
                System.err.println("Background image is null");
            }
        }
    }
}






    /*
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            backgroundImage = new ImageIcon("Accountable/bin/resources/splash_background.png").getImage();
        }



        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}

     */

