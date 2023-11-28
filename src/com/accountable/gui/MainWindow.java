package com.accountable.gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private IncomePanel incomePanel;
    private BudgetPanel budgetPanel;

    private String currentUsername;

    public MainWindow(String username) {
        this.currentUsername = username;

        incomePanel = new IncomePanel();
        budgetPanel = new BudgetPanel();

        // Set the BudgetPanel instance to the IncomePanel
        incomePanel.setBudgetPanel(budgetPanel);

        setTitle("Accountable - Main Window");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create instances of BudgetPanel and ExpensePanel
        BudgetPanel budgetPanel = new BudgetPanel();
        ExpensePanel expensePanel = new ExpensePanel();

        // Add ExpensePanel as a listener to BudgetPanel
        budgetPanel.addCategoryUpdateListener(expensePanel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Budget", budgetPanel);
        tabbedPane.addTab("Expenses", expensePanel);
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

/* Extra main function for testing without login feature
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
    }
*/
}