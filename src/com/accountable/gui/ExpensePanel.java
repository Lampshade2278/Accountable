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

public class ExpensePanel extends JPanel implements CategoryUpdateListener {

    private JTextField expenseNameField;
    private JTextField expenseAmountField;
    private JButton addExpenseButton;
    private JTable expenseTable;
    private DefaultTableModel expenseModel;

    private JComboBox<String> expenseCategoryComboBox; // ComboBox for spending categories

    public ExpensePanel() {
        setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Expense Tracking", JLabel.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        // Panel to add new expenses
        JPanel expenseManagementPanel = new JPanel();
        expenseManagementPanel.setLayout(new GridLayout(0, 2)); // 0 rows, 2 columns

        expenseManagementPanel.add(new JLabel("Expense Name:"));
        expenseNameField = new JTextField(20);
        expenseManagementPanel.add(expenseNameField);

        expenseManagementPanel.add(new JLabel("Expense Amount:"));
        expenseAmountField = new JTextField(20);
        expenseManagementPanel.add(expenseAmountField);

        // Label and ComboBox for the spending category
        expenseManagementPanel.add(new JLabel("Spending Category:"));
        expenseCategoryComboBox = new JComboBox<>(new DefaultComboBoxModel<>());
        expenseManagementPanel.add(expenseCategoryComboBox);

        addExpenseButton = new JButton("Add Expense");
        addExpenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });
        expenseManagementPanel.add(addExpenseButton);

        // Table to display expenses with the new "Spending Category" column
        String[] columnNames = {"Expense Name", "Amount", "Spending Category", "Date"};
        expenseModel = new DefaultTableModel(columnNames, 0);
        expenseTable = new JTable(expenseModel);
        add(new JScrollPane(expenseTable), BorderLayout.CENTER);

        add(expenseManagementPanel, BorderLayout.SOUTH);
    }

    // Call this method to update the categories from the Budget tab
    public void updateCategoryComboBox(String[] categories) {
        expenseCategoryComboBox.setModel(new DefaultComboBoxModel<>(categories));
    }

    private void addExpense() {
        String expenseName = expenseNameField.getText();
        String expenseAmount = expenseAmountField.getText();
        String spendingCategory = (String) expenseCategoryComboBox.getSelectedItem(); // Get the selected spending category
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Assuming you have validation in place for expenseAmount and spendingCategory
        Transaction newExpense = new Transaction(expenseName, Double.parseDouble(expenseAmount), new Date(), true);

        // Add new expense to the table model including the spending category
        expenseModel.addRow(new Object[]{
                newExpense.getDescription(),
                String.format("$%.2f", newExpense.getAmount()),
                spendingCategory, // Add the spending category here
                dateFormat.format(newExpense.getDate())
        });

        // Clear the input fields after adding the expense
        expenseNameField.setText("");
        expenseAmountField.setText("");

        JOptionPane.showMessageDialog(ExpensePanel.this,
                "Expense Added: " + expenseName,
                "Expense Added", JOptionPane.INFORMATION_MESSAGE);
    }

    // Implementation of the CategoryUpdateListener interface
    //@Override
    public void updateCategories(String[] categories) {
        // Update the combo box with the new categories
        expenseCategoryComboBox.setModel(new DefaultComboBoxModel<>(categories));
        // Optionally select the first item by default
        if (categories.length > 0) {
            expenseCategoryComboBox.setSelectedIndex(0);
        }
    }
}
