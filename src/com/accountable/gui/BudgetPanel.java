package com.accountable.gui;// BudgetPanel.java
import com.accountable.CategoryUpdateListener;
import com.accountable.core.Transaction;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BudgetPanel extends JPanel implements CategoryUpdateListener {

    private JTextField categoryNameField;
    private JTextField categoryAmountField;
    private JButton addCategoryButton;
    private JButton deleteCategoryButton;
    private JTable categoryTable;
    private DefaultTableModel categoryModel;

    private JComboBox<String> expenseCategoryComboBox;

    private List<CategoryUpdateListener> categoryUpdateListeners = new ArrayList<>();

    public BudgetPanel() {
        setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Budget", JLabel.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        JPanel budgetManagementPanel = new JPanel();
        budgetManagementPanel.setLayout(new GridLayout(0, 2));

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

        String[] columnNames = {"Category Name", "Amount Allocated"};
        categoryModel = new DefaultTableModel(columnNames, 0);
        categoryTable = new JTable(categoryModel);
        add(new JScrollPane(categoryTable), BorderLayout.CENTER);

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

        categoryModel.addRow(new Object[]{
                newExpense.getDescription(),
                String.format("$%.2f", newExpense.getAmount()),
        });

        notifyListeners();
        notifyBudgetListeners();

        categoryNameField.setText("");
        categoryAmountField.setText("");

        JOptionPane.showMessageDialog(BudgetPanel.this,
                "Category Added: " + categoryName);
    }

    private void deleteCategory() {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow != -1) {
            categoryModel.removeRow(selectedRow);
            notifyListeners();
            notifyBudgetListeners();
            JOptionPane.showMessageDialog(BudgetPanel.this,
                    "Category Deleted",
                    "Category Deleted", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(BudgetPanel.this,
                    "Please select a category to delete",
                    "No Category Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void addCategoryUpdateListener(CategoryUpdateListener listener) {
        categoryUpdateListeners.add(listener);
    }

    private void notifyListeners() {
        for (CategoryUpdateListener listener : categoryUpdateListeners) {
            listener.updateCategories(List.of(getCategoryNames()));
        }
    }

    // Update this method to return the category budgets as a list
    private List<Double> getCategoryBudgets() {
        int rowCount = categoryModel.getRowCount();
        List<Double> categoryBudgets = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            String amountString = (String) categoryModel.getValueAt(i, 1);
            double amount = Double.parseDouble(amountString.substring(1));
            categoryBudgets.add(amount);
        }
        return categoryBudgets;
    }
    // Method to notify all listeners about budget updates
    private void notifyBudgetListeners() {
        List<String> categoryNames = List.of(getCategoryNames());
        List<Double> budgets = getCategoryBudgets();
        for (CategoryUpdateListener listener : categoryUpdateListeners) {
            listener.updateBudgetCategories(categoryNames, budgets);
        }
    }

    private String[] getCategoryNames() {
        int rowCount = categoryModel.getRowCount();
        String[] categoryNames = new String[rowCount];
        for (int i = 0; i < rowCount; i++) {
            categoryNames[i] = (String) categoryModel.getValueAt(i, 0);
        }
        return categoryNames;
    }

    @Override
    public void updateCategories(List<String> updatedCategories) {
        // Implement this method if needed
    }

    @Override
    public void updateBudgetCategories(List<String> updatedCategories, List<Double> updatedBudgets) {
        // Implement this method to handle budget updates
    }
}

