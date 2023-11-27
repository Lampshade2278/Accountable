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

    // Class variable to keep track of the total allocated amount.
    private static double totalAllocated = 0.0;
    private static double predictedIncome = 0.0;
    // You need a JLabel to show the allocation message, let's call it 'allocationMessageLabel'
    private static JLabel allocationMessageLabel;
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

        initializeAllocationMessage(); // Call this method to initialize the label

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
        recalculateAndDisplayAllocation(); // Add this line after the category is successfully added
    }

    private void deleteCategory() {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow != -1) {
            categoryModel.removeRow(selectedRow);
            notifyListeners();
            notifyBudgetListeners();
            recalculateAndDisplayAllocation(); // Add this line after the category is successfully deleted
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


    // Remove the static keyword to make this an instance method
    public void initializeAllocationMessage() {
        allocationMessageLabel = new JLabel("$0.00 / $0.00 of this month's income allocated");
        this.add(allocationMessageLabel, BorderLayout.NORTH); // Add the label to the panel
        // Make sure this does not conflict with other components added to the BorderLayout.NORTH
    }

    // Update this method to include the new label text with the remaining amount.
    private void updateAllocationMessage() {
        double amountLeft = predictedIncome - totalAllocated;
        allocationMessageLabel.setText(String.format("$%.2f / $%.2f of this month's income allocated, $%.2f left",
                totalAllocated, predictedIncome, amountLeft));
    }

    // Change this to an instance method as well
    public void setPredictedIncome(double income) {
        predictedIncome = income;
        totalAllocated = calculateTotalAllocated(); // Calculate the total allocated based on the table data
        updateAllocationMessage();
    }

    // Call this method whenever a new category is added or the income is updated.
    public void recalculateAndDisplayAllocation() {
        totalAllocated = calculateTotalAllocated(); // Sum up all the amounts allocated in the categories
        updateAllocationMessage(); // Update the label with the new total
    }

    private double calculateTotalAllocated() {
        double total = 0.0;
        for (int i = 0; i < categoryModel.getRowCount(); i++) {
            String amountStr = (String) categoryModel.getValueAt(i, 1);
            total += Double.parseDouble(amountStr.replace("$", ""));
        }
        return total;
    }


    // Method to be called when the user allocates money to a category
    public void onCategoryAllocation(double allocation) {
        // Add the allocation to the totalAllocated
        totalAllocated += allocation;
        // Update the allocation message
        updateAllocationMessage();
    }



}

