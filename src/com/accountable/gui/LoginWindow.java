package com.accountable.gui;

import com.accountable.gui.Dialogs.RegistrationDialog;
import com.accountable.core.Authentication;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    //private JButton registerButton;

    public LoginWindow() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        inputPanel.add(new JLabel("   Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("   Password:"));
        inputPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        loginButton = new JButton("Login");
        loginButton.addActionListener(this::login);

        /*
        registerButton = new JButton("Register");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRegisterClicked();
            }
        });
        */



        buttonPanel.add(loginButton);
        //buttonPanel.add(registerButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void login(ActionEvent e) {
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


    /*
    // Method to handle registration logic
    private void onRegisterClicked() {

        RegistrationDialog registrationDialog = new RegistrationDialog(this);
        registrationDialog.setVisible(true);

        if (registrationDialog.isRegistrationSuccessful()) {
            dispose();
            MainWindow mainWindow = new MainWindow(registrationDialog.getRegisteredUsername());
            mainWindow.setVisible(true);
        }
    }
    */


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
    }
}
