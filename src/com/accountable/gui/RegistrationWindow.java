package com.accountable.gui;

import com.accountable.core.Authentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
        registerButton.addActionListener(this::registerUser);
        add(registerButton);

        // Position the window in the center of the screen
        setLocationRelativeTo(null);

        // Disable the register button initially
        registerButton.setEnabled(false);

        // Add document listeners to the username and password fields
        usernameField.getDocument().addDocumentListener(new SimpleDocumentListener());
        passwordField.getDocument().addDocumentListener(new SimpleDocumentListener());
    }

    private void registerUser(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

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

    private class SimpleDocumentListener implements javax.swing.event.DocumentListener {
        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            checkFields();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            checkFields();
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            checkFields();
        }
    }

    private void checkFields() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        boolean isUsernameFilled = !username.trim().isEmpty();
        boolean isPasswordValid = isPasswordValid(password);

        // Enable/disable the register button based on field conditions
        registerButton.setEnabled(isUsernameFilled && isPasswordValid);
    }

    private boolean isPasswordValid(String password) {
        // Check password conditions (1 number, 1 special character, 1 uppercase, 8 characters in length)
        return password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*()-_=+\\[\\]{}|;:'\",.<>/?].*")
                && password.matches(".*[A-Z].*") && password.length() >= 8;
    }
}
