package com.accountable.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ApplicationLauncher {
    public static void main(String[] args) {
        // Set up the look and feel or any initial configurations if necessary
        setupApplication();

        // Create the launch window
        JFrame launchWindow = new JFrame("Accountable - Launch");
        launchWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        launchWindow.setLayout(new BorderLayout(10, 10)); // Add some spacing between components
        launchWindow.setSize(910, 900); // Increase the height a bit to accommodate larger buttons

        // Logo label (replace with actual logo path)
        ImageIcon logo = new ImageIcon(ApplicationLauncher.class.getResource("/splash_background.png"));
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setHorizontalAlignment(JLabel.CENTER); // Center the logo
        launchWindow.add(logoLabel, BorderLayout.NORTH); // Add logo at the top

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // Use GridLayout for equal sizing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 50, 20)); // Reduced bottom padding to move buttons up

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(loginButton.getWidth(), 40)); // Increase button height
        loginButton.setFont(new Font("Arial", Font.BOLD, 16)); // Make the font bigger and bold
        loginButton.addActionListener(e -> {
            launchWindow.dispose();
            SwingUtilities.invokeLater(() -> {
                LoginWindow loginWindow = new LoginWindow();
                loginWindow.setVisible(true);
            });
        });
        buttonPanel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(registerButton.getWidth(), 40)); // Increase button height
        registerButton.setFont(new Font("Arial", Font.BOLD, 16)); // Make the font bigger and bold
        registerButton.addActionListener(e -> {
            launchWindow.dispose();
            SwingUtilities.invokeLater(() -> {
                RegistrationWindow registrationWindow = new RegistrationWindow();
                registrationWindow.setVisible(true);
            });
        });
        buttonPanel.add(registerButton);

        // Create a wrapper panel to move the buttons up
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(buttonPanel, BorderLayout.NORTH); // Aligns the button panel to the top

        // Add wrapper panel instead of button panel directly
        launchWindow.add(wrapperPanel, BorderLayout.CENTER);

        // Display the launch window
        launchWindow.setLocationRelativeTo(null); // Center on screen
        launchWindow.setVisible(true);
    }

    private static void setupApplication() {
        try {
            // Set the look and feel to the system default
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
