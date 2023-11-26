package com.accountable.gui;

import com.accountable.core.Authentication;

import javax.swing.*;
import java.awt.*;

public class RegistrationWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public RegistrationWindow() {
        setTitle("Register");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Adjust the grid and padding to match the LoginWindow
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50)); // Padding adjusted for symmetry

        // Use a consistent font for labels and text fields
        Font labelFont = new Font("Arial", Font.BOLD, 16);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        inputPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(labelFont); // Set the text field font to be consistent
        usernameField.setPreferredSize(new Dimension(200, 30)); // Set the preferred size to make it longer
        inputPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        inputPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(labelFont); // Set the text field font to be consistent
        passwordField.setPreferredSize(new Dimension(200, 30)); // Set the preferred size to make it longer
        inputPanel.add(passwordField);

        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> registerUser(usernameField.getText(), new String(passwordField.getPassword())));
        registerButton.setFont(labelFont); // Use the same font as the labels for consistency

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(registerButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }


    private void registerUser(String username, String password) {
        // Registration logic remains the same
        boolean isSignUpSuccessful = Authentication.signUp(username, password);

        // Custom font to match the other windows
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 16));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 12));

        if (isSignUpSuccessful) {
            // Customized JOptionPane to match the RegistrationWindow style
            JOptionPane.showMessageDialog(this,
                    "Registration successful!",
                    "Message",
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                MainWindow mainWindow = new MainWindow(username);
                mainWindow.setVisible(true);
            });
        } else {
            // Customized JOptionPane to match the RegistrationWindow style
            JOptionPane.showMessageDialog(this,
                    "Registration failed. User may already exist.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}

