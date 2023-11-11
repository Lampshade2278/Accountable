package com.accountable.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {

    private JCheckBox darkModeCheckBox;
    private JButton saveSettingsButton;

    public SettingsPanel() {
        setLayout(new BorderLayout());
        
        JLabel headerLabel = new JLabel("Application Settings", JLabel.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        JPanel settingsControlPanel = new JPanel();
        settingsControlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        darkModeCheckBox = new JCheckBox("Enable Dark Mode");
        settingsControlPanel.add(darkModeCheckBox);

        saveSettingsButton = new JButton("Save Settings");
        saveSettingsButton.addActionListener(new SaveSettingsActionListener());
        settingsControlPanel.add(saveSettingsButton);

        add(settingsControlPanel, BorderLayout.CENTER);
    }

    private class SaveSettingsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isDarkModeEnabled = darkModeCheckBox.isSelected();
            applyDarkMode(isDarkModeEnabled);

            // Save the setting preference, potentially to a file or database
            saveSettingPreference("darkMode", isDarkModeEnabled);

            JOptionPane.showMessageDialog(SettingsPanel.this, 
                    "Settings Saved", 
                    "Settings", JOptionPane.INFORMATION_MESSAGE);
        }

        private void applyDarkMode(boolean enable) {
            // Example logic to apply dark mode
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
            // Placeholder for saving the setting preference
            // This could involve writing to a file or database
            System.out.println("Setting \"" + key + "\" saved with value: " + value);
        }
    }
}
