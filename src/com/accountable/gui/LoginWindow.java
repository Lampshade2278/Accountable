package com.accountable.gui;

import com.accountable.core.Authentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginWindow() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        setSize(400, 200);
        setLocationRelativeTo(null); // Center the window

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 5, 5));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (Authentication.authenticate(username, password)) {
                    // Assuming success, dispose the login window and open the main window
                    dispose();
                    MainWindow mainWindow = new MainWindow(); // Replace with your main window class
                    mainWindow.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginWindow.this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Registration logic
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Example registration logic, replace with your own logic as necessary
                if (Authentication.signUp(username, password)) {
                    JOptionPane.showMessageDialog(LoginWindow.this, "Registration successful.", "Registration", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(LoginWindow.this, "Registration failed.", "Registration", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginWindow().setVisible(true);
            }
        });
    }
}
