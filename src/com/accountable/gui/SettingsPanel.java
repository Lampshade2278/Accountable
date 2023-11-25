package com.accountable.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SettingsPanel extends JPanel {

    private JCheckBox darkModeCheckBox;
    private JButton saveSettingsButton;
    private String username; // The username should be passed to the SettingsPanel when it's instantiated

    public SettingsPanel(String username) {
        this.username = username;

        setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Application Settings", JLabel.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        JPanel settingsControlPanel = new JPanel();
        settingsControlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        darkModeCheckBox = new JCheckBox("Enable Dark Mode");
        settingsControlPanel.add(darkModeCheckBox);

        saveSettingsButton = new JButton("Save Settings");
        saveSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isDarkModeEnabled = darkModeCheckBox.isSelected();
                applyDarkMode(isDarkModeEnabled);
                saveSettingPreference("darkMode", isDarkModeEnabled);
            }
        });

        settingsControlPanel.add(saveSettingsButton);

        add(settingsControlPanel, BorderLayout.CENTER);
    }

    private void applyDarkMode(boolean enable) {
        if (enable) {
            setBackground(Color.DARK_GRAY);
            for (Component comp : getComponents()) {
                comp.setForeground(Color.WHITE);
            }
        } else {
            setBackground(Color.WHITE);
            for (Component comp : getComponents()) {
                comp.setForeground(Color.BLACK);
            }
        }
    }

    private void saveSettingPreference(String key, boolean value) {
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
}
