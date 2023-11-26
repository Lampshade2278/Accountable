package com.accountable.gui;
import com.accountable.CategoryUpdateListener;
import com.accountable.core.Transaction;
import com.accountable.util.NonEditableTableModel;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExpensePanel extends JPanel implements CategoryUpdateListener {

    private JTextField expenseNameField;
    private JTextField expenseAmountField;
    private JButton addExpenseButton;
    private JButton deleteExpenseButton; // Button to delete selected expense
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
        expenseModel = new NonEditableTableModel(columnNames, 0); // Use NonEditableTableModel
        expenseTable = new JTable(expenseModel);
        //expenseTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        add(new JScrollPane(expenseTable), BorderLayout.CENTER);

        // Add a delete button
        deleteExpenseButton = new JButton("Delete Expense");
        deleteExpenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteExpense();
            }
        });
        expenseManagementPanel.add(deleteExpenseButton);
        add(expenseManagementPanel, BorderLayout.SOUTH);


    }

    // Call this method to update the categories from the Budget tab
    public void updateCategoryComboBox(List<String> categories) {
        expenseCategoryComboBox.setModel(new DefaultComboBoxModel<>(categories.toArray(new String[0])));
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
    private void deleteExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow != -1) {
            // Remove the selected row from the table model
            expenseModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(ExpensePanel.this,
                    "Expense Deleted",
                    "Expense Deleted", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(ExpensePanel.this,
                    "Please select an expense to delete",
                    "No Expense Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Implementation of the CategoryUpdateListener interface
    @Override
    public void updateCategories(List<String> categories) {
        updateCategoryComboBox(categories);
    }
}