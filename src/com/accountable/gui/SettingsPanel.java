package com.accountable.gui;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class SettingsPanel extends JPanel {

    private JCheckBox darkModeCheckBox;
    private JButton saveSettingsButton;
    private JButton deleteAccountButton;
    private JButton changePasswordButton;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmNewPasswordField;
    private String username;

    public SettingsPanel(String username) {
        this.username = username;

        setLayout(new BorderLayout());

        // Create a panel for the theme settings
        JPanel themeSettingsPanel = new JPanel(new BorderLayout());
        JLabel themeLabel = new JLabel("Theme Settings", JLabel.CENTER);
        themeSettingsPanel.add(themeLabel, BorderLayout.NORTH);

        darkModeCheckBox = new JCheckBox("Enable Dark Mode");
        themeSettingsPanel.add(darkModeCheckBox, BorderLayout.CENTER);

        // Create a panel for the account management with GridBagLayout
        JPanel accountManagementPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel accountLabel = new JLabel("Account Management", JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        accountManagementPanel.add(accountLabel, gbc);

        // Modify the "Delete Account" button size
        deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.setPreferredSize(new Dimension(160, 30)); // Set a larger button size
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the available horizontal space
        gbc.insets = new Insets(10, 0, 0, 0); // Add top margin
        accountManagementPanel.add(deleteAccountButton, gbc);

        // Create a panel for "Change Password" with fields
        JPanel changePasswordPanel = new JPanel(new BorderLayout());
        JLabel changePasswordLabel = new JLabel("Change Password", JLabel.CENTER);
        changePasswordPanel.add(changePasswordLabel, BorderLayout.NORTH);

        currentPasswordField = new JPasswordField(20); // Adjust the field size as needed
        newPasswordField = new JPasswordField(20);
        confirmNewPasswordField = new JPasswordField(20);

        JPanel passwordFieldsPanel = new JPanel(new GridLayout(3, 2, 0, 10)); // Add vertical spacing between fields
        passwordFieldsPanel.add(new JLabel("Current Password:"));
        passwordFieldsPanel.add(currentPasswordField);
        passwordFieldsPanel.add(new JLabel("New Password:"));
        passwordFieldsPanel.add(newPasswordField);
        passwordFieldsPanel.add(new JLabel("Confirm New Password:"));
        passwordFieldsPanel.add(confirmNewPasswordField);

        changePasswordPanel.add(passwordFieldsPanel, BorderLayout.CENTER);

        changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(this::changePassword);
        changePasswordPanel.add(changePasswordButton, BorderLayout.SOUTH);

        // Create a panel for future features (placeholders)
        JPanel futureFeaturesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints futureGbc = new GridBagConstraints();

        JLabel futureLabel = new JLabel("Future Features (Coming Soon)", JLabel.CENTER);
        futureGbc.gridx = 0;
        futureGbc.gridy = 0;
        futureGbc.gridwidth = 2;
        futureFeaturesPanel.add(futureLabel, futureGbc);

        // Placeholder buttons for future features (grayed out)
        JButton sendNotificationsButton = new JButton("Send Notifications");
        sendNotificationsButton.setEnabled(false); // Grayed out
        futureGbc.gridx = 0;
        futureGbc.gridy = 1;
        futureGbc.gridwidth = 1;
        futureFeaturesPanel.add(sendNotificationsButton, futureGbc);

        JButton importExportButton = new JButton("Import/Export Bank Docs");
        importExportButton.setEnabled(false); // Grayed out
        futureGbc.gridx = 1;
        futureGbc.gridy = 1;
        futureGbc.gridwidth = 1;
        futureFeaturesPanel.add(importExportButton, futureGbc);

        // Add the sections to the main panel
        add(themeSettingsPanel, BorderLayout.NORTH);
        add(accountManagementPanel, BorderLayout.CENTER);
        add(changePasswordPanel, BorderLayout.SOUTH); // Add "Change Password" panel
        add(futureFeaturesPanel, BorderLayout.SOUTH); // Placeholder for future features

        // Create a panel for the save button
        JPanel saveButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveSettingsButton = new JButton("Save Settings");
        saveSettingsButton.addActionListener(this::saveSettings);
        saveButtonPanel.add(saveSettingsButton);
        add(saveButtonPanel, BorderLayout.SOUTH);

        // Customize the disabled button appearance
        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        defaults.put("Button.disabledText", new ColorUIResource(Color.GRAY));

        loadAndApplyTheme();
    }

    private void saveSettings(ActionEvent e) {
        ThemeManager.Theme theme = darkModeCheckBox.isSelected() ? ThemeManager.Theme.DARK : ThemeManager.Theme.LIGHT;
        saveSettingPreference("theme", theme.toString());

        JFrame mainWindow = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (mainWindow != null) {
            ThemeManager.applyTheme(mainWindow, theme);
        }
    }

    private void deleteAccount(ActionEvent e) {
        int confirmDialogResult = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete your account?",
                "Confirm Account Deletion", JOptionPane.YES_NO_OPTION);

        if (confirmDialogResult == JOptionPane.YES_OPTION) {
            // Perform account deletion logic here
            // You can add code to delete user data, preferences, etc.
            // You might want to show a confirmation message after successful deletion.
            // Note: Implement proper user authentication and data handling for security.
        }
    }

    private void changePassword(ActionEvent e) {
        // You can add code to open a dialog for password change
        // Collect old password, new password, and confirm new password
        // Perform password change logic here, and show appropriate messages.
        // Note: Implement proper user authentication and password handling for security.
    }

    void loadAndApplyTheme() {
        String themeSetting = loadSettingPreference("theme");
        ThemeManager.Theme theme = "DARK".equals(themeSetting) ? ThemeManager.Theme.DARK : ThemeManager.Theme.LIGHT;
        darkModeCheckBox.setSelected(ThemeManager.Theme.DARK.equals(theme));

        JFrame mainWindow = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (mainWindow != null) {
            ThemeManager.applyTheme(mainWindow, theme);
        }
    }

    private void saveSettingPreference(String key, String value) {
        String filename = username + "_settings.dat";
        try (PrintWriter out = new PrintWriter(new FileWriter(filename, false))) {
            out.println(key + "=" + value);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving settings",
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private String loadSettingPreference(String key) {
        String filename = username + "_settings.dat";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(key + "=")) {
                    return line.split("=")[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
