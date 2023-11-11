package com.accountable.gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Accountable - Main Window");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tabs for different functionalities
        tabbedPane.addTab("Budget", new BudgetPanel());
        tabbedPane.addTab("Expenses", new ExpensePanel());
        tabbedPane.addTab("Income", new IncomePanel());
        tabbedPane.addTab("Reports", new ReportPanel());
        tabbedPane.addTab("Settings", new SettingsPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}
