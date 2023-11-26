package com.accountable.gui;

import com.accountable.core.Authentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class RegistrationWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public RegistrationWindow() {
        setTitle("Register");
        setSize(300, 200);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Username field
        add(new JLabel("Username:"));
        usernameField = new JTextField(15);
        add(usernameField);

        // Password field
        add(new JLabel("Password:"));
        passwordField = new JPasswordField(15);
        add(passwordField);

        // Register button
        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> registerUser(usernameField.getText(), new String(passwordField.getPassword())));
        add(registerButton);

        // Position the window in the center of the screen
        setLocationRelativeTo(null);
    }
    private void registerUser(String username, String password) {
        // Use the signUp method from the Authentication class to register the user
        boolean isSignUpSuccessful = Authentication.signUp(username, password);

        if (isSignUpSuccessful) {
            JOptionPane.showMessageDialog(this, "Registration successful!");

            // Close the registration window
            this.dispose();

            // Open the MainWindow and pass the username
            SwingUtilities.invokeLater(() -> {
                MainWindow mainWindow = new MainWindow(username);
                mainWindow.setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. User may already exist.");
        }
    }

    }

