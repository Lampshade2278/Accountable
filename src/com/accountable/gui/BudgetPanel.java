package com.accountable.gui;

import com.accountable.CategoryUpdateListener;
import com.accountable.core.Transaction;

import com.accountable.util.NonEditableTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BudgetPanel extends JPanel {

    private JTextField categoryNameField;
    private JTextField categoryAmountField;
    private JButton addCategoryButton;

    private JButton deleteCategoryButton; // Button to delete selected category

    private JTable categoryTable;
    private DefaultTableModel categoryModel;

    private JComboBox<String> expenseCategoryComboBox; // ComboBox for spending categories

    // List to hold category update listeners
    private List<CategoryUpdateListener> categoryUpdateListeners = new ArrayList<>();

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

        // Table to display budget categories
        String[] columnNames = {"Category Name", "Amount Allocated"};
        categoryModel = new NonEditableTableModel(columnNames, 0);
        categoryTable = new JTable(categoryModel);
        add(new JScrollPane(categoryTable), BorderLayout.CENTER);

        // Add a delete button
        deleteCategoryButton = new JButton("Delete Category");
        deleteCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCategory();
            }
        });
        budgetManagementPanel.add(deleteCategoryButton);

        add(budgetManagementPanel, BorderLayout.SOUTH);
    }

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

        // Notify listeners about the updated categories
        notifyListeners();

        // Clear the input fields after adding the category
        categoryNameField.setText("");
        categoryAmountField.setText("");

        JOptionPane.showMessageDialog(BudgetPanel.this,
                "Category Added: " + categoryName);
    }

    private void deleteCategory() {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow != -1) {
            // Remove the selected row from the table model
            categoryModel.removeRow(selectedRow);
            notifyListeners(); // Notify listeners about the updated categories after deletion
            JOptionPane.showMessageDialog(BudgetPanel.this,
                    "Category Deleted",
                    "Category Deleted", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(BudgetPanel.this,
                    "Please select a category to delete",
                    "No Category Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Method to add a listener to the list
    public void addCategoryUpdateListener(CategoryUpdateListener listener) {
        categoryUpdateListeners.add(listener);
    }

    // Method to notify all listeners about category updates
    private void notifyListeners() {
        for (CategoryUpdateListener listener : categoryUpdateListeners) {
            listener.updateCategories(List.of(getCategoryNames()));
        }
    }

    // Method to get category names from the table model
    private String[] getCategoryNames() {
        int rowCount = categoryModel.getRowCount();
        String[] categoryNames = new String[rowCount];
        for (int i = 0; i < rowCount; i++) {
            categoryNames[i] = (String) categoryModel.getValueAt(i, 0);
        }
        return categoryNames;
    }
}