package com.accountable.gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Map;


import com.accountable.core.Authentication;
import com.accountable.core.DataManager;
import com.accountable.core.User;

public class SettingsPanel extends JPanel {

    private String currentUsername;
    private JFrame parentFrame;

    public SettingsPanel(String username, JFrame parentFrame) {
        this.currentUsername = username;
        this.parentFrame = parentFrame;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Theme settings panel
        JPanel themePanel = createThemePanel();
        add(themePanel);

        // Password management panel
        JPanel passwordPanel = createPasswordPanel();
        add(passwordPanel);

        // Account deletion panel
        JPanel deleteAccountPanel = createDeleteAccountPanel();
        add(deleteAccountPanel);

        // Data management and bank link panel
        JPanel dataManagementPanel = createDataManagementAndBankLinkPanel();
        add(dataManagementPanel);

        // Notification settings panel
        JPanel notificationSettingsPanel = createNotificationSettingsPanel();
        add(notificationSettingsPanel);

        // Feedback panel
        JPanel feedbackPanel = createFeedbackPanel();
        add(feedbackPanel);
    }

    private JPanel createThemePanel() {
        JPanel themePanel = new JPanel();
        themePanel.setBorder(BorderFactory.createTitledBorder("Theme Settings"));

        JRadioButton lightThemeButton = new JRadioButton("Light Theme");
        JRadioButton darkThemeButton = new JRadioButton("Dark Theme");
        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(lightThemeButton);
        themeGroup.add(darkThemeButton);

        if (ThemeManager.getCurrentTheme() == ThemeManager.Theme.LIGHT) {
            lightThemeButton.setSelected(true);
        } else {
            darkThemeButton.setSelected(true);
        }

        lightThemeButton.addActionListener(e -> ThemeManager.applyTheme(parentFrame, ThemeManager.Theme.LIGHT));
        darkThemeButton.addActionListener(e -> ThemeManager.applyTheme(parentFrame, ThemeManager.Theme.DARK));

        themePanel.add(lightThemeButton);
        themePanel.add(darkThemeButton);

        return themePanel;
    }

    private JPanel createPasswordPanel() {
        JPanel passwordPanel = new JPanel(new GridLayout(4, 2));
        passwordPanel.setBorder(BorderFactory.createTitledBorder("Password Management"));

        JPasswordField currentPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        JButton changePasswordButton = new JButton("Change Password");

        passwordPanel.add(new JLabel("Current Password:"));
        passwordPanel.add(currentPasswordField);
        passwordPanel.add(new JLabel("New Password:"));
        passwordPanel.add(newPasswordField);
        passwordPanel.add(new JLabel("Confirm New Password:"));
        passwordPanel.add(confirmPasswordField);
        passwordPanel.add(changePasswordButton);

        changePasswordButton.addActionListener(e -> changePassword(
                new String(currentPasswordField.getPassword()),
                new String(newPasswordField.getPassword()),
                new String(confirmPasswordField.getPassword())
        ));

        return passwordPanel;
    }

    private JPanel createDeleteAccountPanel() {
        JPanel deleteAccountPanel = new JPanel();
        deleteAccountPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        deleteAccountPanel.setBorder(BorderFactory.createTitledBorder("Delete Account"));

        JLabel warningLabel = new JLabel("<html><span style='color: red;'>Warning:</span> Deleting your account is irreversible!</html>");
        deleteAccountPanel.add(warningLabel);

        JTextField usernameField = new JTextField(20);
        JButton deleteAccountButton = new JButton("Delete Account");

        deleteAccountPanel.add(new JLabel("Enter Username:"));
        deleteAccountPanel.add(usernameField);
        deleteAccountPanel.add(deleteAccountButton);

        deleteAccountButton.addActionListener(e -> deleteAccount(usernameField.getText()));

        return deleteAccountPanel;
    }

    private JPanel createDataManagementAndBankLinkPanel() {
        JPanel dataManagementPanel = new JPanel();
        dataManagementPanel.setBorder(BorderFactory.createTitledBorder("Data Management & Bank Integration"));

        JButton importDataButton = new JButton("Import Data");
        JButton exportDataButton = new JButton("Export Data");
        JButton linkBankButton = new JButton("Link to Bank");

        importDataButton.setEnabled(false); // Disabled for future implementation
        exportDataButton.setEnabled(false); // Disabled for future implementation
        linkBankButton.setEnabled(false); // Disabled for future implementation

        dataManagementPanel.add(importDataButton);
        dataManagementPanel.add(exportDataButton);
        dataManagementPanel.add(linkBankButton);

        return dataManagementPanel;
    }

    private JPanel createNotificationSettingsPanel() {
        JPanel notificationSettingsPanel = new JPanel();
        notificationSettingsPanel.setBorder(BorderFactory.createTitledBorder("Notification Settings"));

        JCheckBox emailNotificationsCheckBox = new JCheckBox("Email Notifications");
        JCheckBox appNotificationsCheckBox = new JCheckBox("App Notifications");
        emailNotificationsCheckBox.setEnabled(false); // Disabled for future implementation
        appNotificationsCheckBox.setEnabled(false); // Disabled for future implementation

        notificationSettingsPanel.add(emailNotificationsCheckBox);
        notificationSettingsPanel.add(appNotificationsCheckBox);
        return notificationSettingsPanel;
    }

    private JPanel createFeedbackPanel() {
        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setBorder(BorderFactory.createTitledBorder("Send Feedback"));

        JButton sendFeedbackButton = new JButton("Send Feedback");
        sendFeedbackButton.setEnabled(false); // Disabled for future implementation

        feedbackPanel.add(sendFeedbackButton);
        return feedbackPanel;
    }

    private void deleteAccount(String enteredUsername) {
        if (!enteredUsername.equals(currentUsername)) {
            JOptionPane.showMessageDialog(this, "Username entered does not match. Account not deleted.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (DataManager.deleteUserAccount(currentUsername)) {
            JOptionPane.showMessageDialog(this, "Account and associated data deleted successfully.");
            logoutUser();
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting account or associated data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (DataManager.changeUserPassword(currentUsername, currentPassword, newPassword)) {
            JOptionPane.showMessageDialog(this, "Password successfully changed.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error updating password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logoutUser() {
        parentFrame.dispose();
        new LoginWindow().setVisible(true);
    }

    public void loadAndApplyTheme() {
        ThemeManager.applyTheme(parentFrame, ThemeManager.getCurrentTheme());
    }
}
