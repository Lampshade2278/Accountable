package com.accountable.gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private String currentUsername;

    public MainWindow(String username) {
        this.currentUsername = username;

        setTitle("Accountable - Main Window");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Budget", new BudgetPanel());
        tabbedPane.addTab("Expenses", new ExpensePanel());
        tabbedPane.addTab("Income", new IncomePanel());
        tabbedPane.addTab("Reports", new ReportPanel());

        // Pass 'this' as the second argument to refer to the current instance of MainWindow
        SettingsPanel settingsPanel = new SettingsPanel(currentUsername, this);
        tabbedPane.addTab("Settings", settingsPanel);

        // Set the layout of the content pane to BorderLayout
        getContentPane().setLayout(new BorderLayout());

        // Add the JTabbedPane to the CENTER of the content pane
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // Apply the theme based on the user's settings
        settingsPanel.loadAndApplyTheme();
    }
}
