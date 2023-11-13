package com.accountable.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    // Components and layout setup

    public MainWindow() {
        // Set frame properties
        setTitle("Accountable - Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Set your preferred window size
        setLayout(new GridBagLayout());

        // Create sections
        JPanel budgetPanel = createSection("Budget", "budget_icon.png");
        JPanel incomeExpensesPanel = createSection("Income vs. Expenses", "income_expenses_icon.png");
        JPanel savingsPanel = createSection("Savings", "savings_icon.png");
        JPanel monthlyReportPanel = createSection("Monthly Report", "monthly_report_icon.png");
        JPanel settingsPanel = createSection("Settings", "settings_icon.png");

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the main window
                MainWindow.this.dispose();

                // Open the login window
                SwingUtilities.invokeLater(() -> {
                    LoginWindow loginWindow = new LoginWindow();
                    loginWindow.setVisible(true);
                });
            }
        });

        // Add components to the main window
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(budgetPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(incomeExpensesPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        add(savingsPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(monthlyReportPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(settingsPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHEAST; // Align the button to the top right
        add(logoutButton, gbc);

        // Set the frame to be visible
        setVisible(true);
    }

    private JPanel createSection(String title, String iconFilename) {
        // Create a section panel with an icon and label
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical layout

        ImageIcon icon = new ImageIcon(iconFilename);
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(iconLabel);
        panel.add(titleLabel);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow();
            }
        });
    }
}
