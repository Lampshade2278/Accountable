package com.accountable.gui;

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
        // Hash the password for security
        String hashedPassword = hashPassword(password);

        // File to store user credentials
        Path file = Paths.get("users.txt");

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[0].equals(username)) {
                    JOptionPane.showMessageDialog(this, "User already exists.");
                    return;
                }
            }
        } catch (IOException e) {
            // For a real application, use logging instead of printStackTrace
            e.printStackTrace();
        }

        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(username + "," + hashedPassword + System.lineSeparator());
            JOptionPane.showMessageDialog(this, "Registration successful!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering user.");
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
