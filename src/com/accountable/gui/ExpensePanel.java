
package com.accountable.gui;
import com.accountable.CategoryUpdateListener;
import com.accountable.core.Transaction;
import com.accountable.util.NonEditableTableModel;
import com.accountable.core.Progress;
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
    private JButton deleteExpenseButton;
    private JTable expenseTable;
    private DefaultTableModel expenseModel;

    private JComboBox<String> expenseCategoryComboBox;

    public ExpensePanel() {
        setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Expense Tracking", JLabel.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        JPanel expenseManagementPanel = new JPanel();
        expenseManagementPanel.setLayout(new GridLayout(0, 2));

        expenseManagementPanel.add(new JLabel("Expense Name:"));
        expenseNameField = new JTextField(20);
        expenseManagementPanel.add(expenseNameField);

        expenseManagementPanel.add(new JLabel("Expense Amount:"));
        expenseAmountField = new JTextField(20);
        expenseManagementPanel.add(expenseAmountField);

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

        String[] columnNames = {"Expense Name", "Amount", "Spending Category", "Date", "Budget", "Remaining"};
        expenseModel = new NonEditableTableModel(columnNames, 0);
        expenseTable = new JTable(expenseModel);
        add(new JScrollPane(expenseTable), BorderLayout.CENTER);

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

    public void updateCategoryComboBox(List<String> categories) {
        expenseCategoryComboBox.setModel(new DefaultComboBoxModel<>(categories.toArray(new String[0])));
    }

    private void addExpense() {
        String expenseName = expenseNameField.getText();
        String expenseAmount = expenseAmountField.getText();
        String spendingCategory = (String) expenseCategoryComboBox.getSelectedItem();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Transaction newExpense = new Transaction(expenseName, Double.parseDouble(expenseAmount), new Date(), true);
        double budget = 100.00; // Example budget value (you should get it from somewhere)

        // Add new expense to the table model including the spending category and progress information
        Progress progress = new Progress(budget, calculateRemainingBudget(newExpense, budget));
        expenseModel.addRow(new Object[]{
                newExpense.getDescription(),
                String.format("$%.2f", newExpense.getAmount()),
                spendingCategory,
                dateFormat.format(newExpense.getDate()),
                progress.getBudgetAsString(),
                progress.getRemainingAsString()
        });
        expenseNameField.setText("");
        expenseAmountField.setText("");

        JOptionPane.showMessageDialog(ExpensePanel.this,
                "Expense Added: " + expenseName,
                "Expense Added", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow != -1) {
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

    @Override
    public void updateCategories(List<String> categories) {
        updateCategoryComboBox(categories);
    }
    @Override
    public void updateBudgetCategories(List<String> updatedCategories, List<Double> updatedBudgets) {
        updateCategoryComboBox(updatedCategories);

        // TODO: Use the updated budget information in your ExpensePanel
        // For example, you can access the budget for the first category as follows:
        Double budgetForFirstCategory = updatedBudgets.get(0);
        System.out.println("Budget for the first category: " + budgetForFirstCategory);
    }
    private double calculateRemainingBudget(Transaction newExpense, double budget) {
        return budget - newExpense.getAmount();
    }
}

