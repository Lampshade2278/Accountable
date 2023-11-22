package com.accountable.gui;

import com.accountable.CategoryUpdateListener;
import com.accountable.core.Transaction;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BudgetPanel extends JPanel {

    private JTextField categoryNameField;
    private JTextField categoryAmountField;
    private JButton addCategoryButton;
    private JTable categoryTable;
    private DefaultTableModel categoryModel;

    private JComboBox<String> expenseCategoryComboBox; // ComboBox for spending categories

    public BudgetPanel() {
        setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Budget", JLabel.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        // Panel to add new expenses
        JPanel budgetManagementPanel = new JPanel();
        budgetManagementPanel.setLayout(new GridLayout(0, 2)); // 0 rows, 2 columns

        budgetManagementPanel.add(new JLabel("Category Name:"));
        categoryNameField = new JTextField(20);
        budgetManagementPanel.add(categoryNameField);

        budgetManagementPanel.add(new JLabel("Amount Allocated:"));
        categoryAmountField = new JTextField(20);
        budgetManagementPanel.add(categoryAmountField);

        addCategoryButton = new JButton("Add Category");
        addCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBudget();
            }
        });
        budgetManagementPanel.add(addCategoryButton);

        // Table to display expenses with the new "Spending Category" column
        String[] columnNames = {"Category Name", "Amount Allocated"};
        categoryModel = new DefaultTableModel(columnNames, 0);
        categoryTable = new JTable(categoryModel);
        add(new JScrollPane(categoryTable), BorderLayout.CENTER);

        add(budgetManagementPanel, BorderLayout.SOUTH);
    }

    /*
    // Call this method to update the categories from the Budget tab
    public void updateCategoryComboBox(String[] categories) {
        expenseCategoryComboBox.setModel(new DefaultComboBoxModel<>(categories));
    }
    */

    private void addBudget() {
        String categoryName = categoryNameField.getText();
        String categoryAmount = categoryAmountField.getText();

        // Assuming you have validation in place for categoryAmount and spendingCategory
        Transaction newExpense = new Transaction(categoryName, Double.parseDouble(categoryAmount), new Date(), true);

        // Add new category to the table model including the spending category
        categoryModel.addRow(new Object[]{
                newExpense.getDescription(),
                String.format("$%.2f", newExpense.getAmount()),
        });

        // Clear the input fields after adding the expense
        categoryNameField.setText("");
        categoryAmountField.setText("");

        JOptionPane.showMessageDialog(BudgetPanel.this,
                "Category Added: " + categoryName);
                //"Expense Added", JOptionPane.INFORMATION_MESSAGE);
    }

    // Implementation of the CategoryUpdateListener interface
    // Need to send the most updated Category list to the Expense Panel
    //@Override

}
