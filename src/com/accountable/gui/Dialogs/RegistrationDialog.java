
package com.accountable.gui.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.accountable.core.User; // Assuming User class exists in the core package for user creation

public class RegistrationDialog extends JDialog {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton cancelButton;

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
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        add(registerButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    boolean registrationSuccessful = false;

    private void registerUser() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        // Basic validation (more comprehensive validation can be implemented)
        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Assuming User class has a method to create a new user
        boolean userCreated = User.createUser(username, new String(password));

        if (userCreated) {
            registrationSuccessful = true;
            JOptionPane.showMessageDialog(this, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            User.createUser(username, new String(password));
            dispose();
        } else {
            registrationSuccessful = false;
            JOptionPane.showMessageDialog(this, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public boolean isRegistrationSuccessful() {
        return registrationSuccessful;
    }
}
