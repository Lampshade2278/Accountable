package com.accountable.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationLauncher {
    public static void main(String[] args) {
        // Set up the look and feel or any initial configurations if necessary
        setupApplication();

        // Create the launch window
        JFrame launchWindow = new JFrame("Accountable - Launch");
        launchWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        launchWindow.setLayout(new FlowLayout());
        launchWindow.setSize(300, 200);

        // Logo label (replace with actual logo path)
        JLabel logoLabel = new JLabel(new ImageIcon("path/to/logo.png"));
        launchWindow.add(logoLabel);
        // ...[previous code]...

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchWindow.dispose();
                SwingUtilities.invokeLater(() -> {
                    LoginWindow loginWindow = new LoginWindow();
                    loginWindow.setVisible(true);
                });
            }
        });
        launchWindow.add(loginButton);

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the launch window
                launchWindow.dispose();

                // Open the registration window
                SwingUtilities.invokeLater(() -> {
                    RegistrationWindow registrationWindow = new RegistrationWindow();
                    registrationWindow.setVisible(true);
                });
            }
        });
        launchWindow.add(registerButton);

        // Display the launch window
        launchWindow.setLocationRelativeTo(null); // Center on screen
        launchWindow.setVisible(true);
    }

    private static void setupApplication() {
        // Initial setup code here, if any
        // Example: UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    }
}
