package com.accountable.gui;

import com.accountable.core.Authentication;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginWindow() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50)); // Adjust for longer fields

        // Increase the font size for labels and set them to bold
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

        loginButton = new JButton("Login");
        loginButton.addActionListener(this::login);
        loginButton.setFont(labelFont); // Use the same font as the labels

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void login(ActionEvent e) {
        // Login logic remains the same
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (Authentication.authenticate(username, password)) {
            dispose();
            MainWindow mainWindow = new MainWindow(username);
            mainWindow.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
    }
}
