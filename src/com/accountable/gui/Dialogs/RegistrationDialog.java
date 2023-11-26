/*
package com.accountable.gui.Dialogs;

import com.accountable.core.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegistrationDialog extends JDialog {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton cancelButton;
    private boolean registrationSuccessful = false;
    private String registeredUsername;

    public RegistrationDialog(JFrame parent) {
        super(parent, "Register", true);
        setSize(300, 200);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField(20);
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        add(passwordField);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this::registerUser);
        add(registerButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void registerUser(ActionEvent e) {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean userCreated = User.createUser(username, new String(password));

        if (userCreated) {
            registrationSuccessful = true;
            registeredUsername = username;
            JOptionPane.showMessageDialog(this, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed or username already exists", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isRegistrationSuccessful() {
        return registrationSuccessful;
    }

    public String getRegisteredUsername() {
        return registeredUsername;
    }
}
*/
