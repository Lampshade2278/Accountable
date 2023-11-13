package com.accountable.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BudgetPanel extends JPanel {

    private JTextField budgetNameField;
    private JTextField budgetAmountField;
    private JButton addBudgetButton;
    private JList<String> budgetList; // Assuming the use of a String for demonstration

    public BudgetPanel() {
        setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Budget Management", JLabel.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        JPanel budgetManagementPanel = new JPanel();
        budgetManagementPanel.setLayout(new GridLayout(0, 2)); // 0 rows, 2 columns

        budgetManagementPanel.add(new JLabel("Budget Name:"));
        budgetNameField = new JTextField(20);
        budgetManagementPanel.add(budgetNameField);

        budgetManagementPanel.add(new JLabel("Budget Amount:"));
        budgetAmountField = new JTextField(20);
        budgetManagementPanel.add(budgetAmountField);

        addBudgetButton = new JButton("Add Budget");
        addBudgetButton.addActionListener(new AddBudgetActionListener());
        budgetManagementPanel.add(addBudgetButton);

        budgetList = new JList<>(); // Placeholder for budget items
        add(new JScrollPane(budgetList), BorderLayout.CENTER);

        add(budgetManagementPanel, BorderLayout.SOUTH);
    }

    private class AddBudgetActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Placeholder for adding budget logic
            String budgetName = budgetNameField.getText();
            String budgetAmount = budgetAmountField.getText();
            // TODO: Implement budget adding logic

            JOptionPane.showMessageDialog(BudgetPanel.this,
                    "Budget Added: " + budgetName + " - " + budgetAmount,
                    "Budget Added", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
