package com.accountable.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class SettingsPanel extends JPanel {

    private JCheckBox darkModeCheckBox;
    private JButton saveSettingsButton;
    private String username;

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
        saveSettingsButton.addActionListener(this::saveSettings);

        settingsControlPanel.add(saveSettingsButton);
        add(settingsControlPanel, BorderLayout.CENTER);

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
