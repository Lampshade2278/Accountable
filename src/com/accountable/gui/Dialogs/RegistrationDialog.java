
package com.accountable.gui.Dialogs;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.accountable.core.User;

public class RegistrationDialog extends JDialog {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton registerButton;

    private boolean registrationSuccessful = false;

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

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancelClicked();
            }
        });
        add(cancelButton);

        registerButton.setEnabled(false);

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkInputValidity();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkInputValidity();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkInputValidity();
            }
        };

        usernameField.getDocument().addDocumentListener(documentListener);
        passwordField.getDocument().addDocumentListener(documentListener);
    }

    private void checkInputValidity() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        boolean isValid = !username.isEmpty() && password.length >= 8 && containsNumber(password) && containsSpecialChar(password) && containsUpperCase(password);

        registerButton.setEnabled(isValid);
    }

    private boolean containsNumber(char[] password) {
        for (char c : password) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsSpecialChar(char[] password) {
        String specialChars = "!@#$%^&*()-_=+[]{}|;:'\",.<>/?";
        for (char c : password) {
            if (specialChars.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }

    private boolean containsUpperCase(char[] password) {
        for (char c : password) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private void registerUser() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        boolean userCreated = User.createUser(username, new String(password));

        if (userCreated) {
            registrationSuccessful = true;
            JOptionPane.showMessageDialog(this, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            registrationSuccessful = false;
            JOptionPane.showMessageDialog(this, "Registration failed. Please choose a different username.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancelClicked() {
        int confirmResult = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel registration?",
                "Confirm Cancel",
                JOptionPane.YES_NO_OPTION);

        if (confirmResult == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public boolean isRegistrationSuccessful() {
        return registrationSuccessful;
    }
}
