/*

The SpendingCategoriesSetupDialog needs
to collect a set of spending categories
and the amount of money allocated to each,
ensuring that the total does not exceed
the totalIncome specified. You'll likely
want to provide an interface where the
user can add categories and specify
amounts for each, with the ability to
add or remove categories dynamically.

We're dynamically adding rows of text fields to a JPanel with a BoxLayout,
allowing the user to enter category names and allocation amounts.
We're using a LinkedHashMap to maintain the order of categories as they're added.
A "Total Allocated" label keeps track of the sum of allocations, updating whenever
the user changes any of the amounts. The OK button triggers a validation
check to ensure allocations do not exceed total income, then saves the data
and closes the dialog.
The getCategoryAllocations method allows retrieval of the
allocation map after the dialog is closed.
 */

package com.accountable.gui.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

// Dialog for setting up spending categories and allocating the budget.
public class SpendingCategoriesSetupDialog extends JDialog {
    private Double totalIncome;                         // Total income to be allocated
    private Map<String, JTextField> categoryFields;    // Fields for entering category names and allocation amounts
    private JPanel categoriesPanel;                    // Panel to contain category input fields
    private JLabel totalLabel;                         // Label to show total allocated amount

    private Map<String, Double> categoryAllocations;

    // Constructor for SpendingCategoriesSetupDialog
    public SpendingCategoriesSetupDialog(Frame owner, boolean modal, Double totalIncome) {
        super(owner, modal);
        this.totalIncome = totalIncome;
        this.categoryFields = new LinkedHashMap<>();
        categoryAllocations = new HashMap<>();
        initComponents();
    }

    // Initializes the dialog components
    private void initComponents() {
        setTitle("Set Up Spending Categories");
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Create a scrolling panel for category fields
        categoriesPanel = new JPanel();
        categoriesPanel.setLayout(new BoxLayout(categoriesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(categoriesPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize the total label to show allocated amount
        totalLabel = new JLabel("Total Allocated: $0.00 / $" + String.format("%.2f", totalIncome));
        add(totalLabel, BorderLayout.NORTH);

        // Button to add new category fields
        JButton addCategoryButton = new JButton("Add Category");
        addCategoryButton.addActionListener(e -> addCategoryField("", ""));
        add(addCategoryButton, BorderLayout.SOUTH);

        // Add initial category field for user input
        addCategoryField("Category Name", "0.00");

        // OK button to confirm the allocation
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> onOk());
        add(okButton, BorderLayout.SOUTH);

        // Ensure the dialog is centered on the parent window
        setLocationRelativeTo(null);
    }

    // Adds a new category input field to the panel
    private void addCategoryField(String name, String amount) {
        // Panel to hold the name and amount fields
        JPanel panel = new JPanel(new GridLayout(1, 2));
        JTextField nameField = new JTextField(name);
        JTextField amountField = new JTextField(amount);

        // Add action listener to recalculate the total when the amount changes
        amountField.addActionListener(e -> recalculateTotal());
        amountField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                recalculateTotal();
            }
        });

        // Add fields to the panel and the panel to the main panel
        panel.add(nameField);
        panel.add(amountField);
        categoriesPanel.add(panel);
        categoryFields.put(name, amountField);

        // Update UI and recalculate total allocation
        categoriesPanel.revalidate();
        recalculateTotal();
    }

    // Recalculates the total allocated amount when amount fields change
    private void recalculateTotal() {
        // Sum all allocation amounts entered by the user
        double totalAllocated = categoryFields.values().stream()
                .mapToDouble(field -> {
                    try {
                        return Double.parseDouble(field.getText());
                    } catch (NumberFormatException e) {
                        return 0.0;
                    }
                }).sum();

        // Update the total label with the new sum
        totalLabel.setText("Total Allocated: $" + String.format("%.2f", totalAllocated) + " / $" + String.format("%.2f", totalIncome));
    }

    // Confirms the allocations and closes the dialog
    private void onOk() {

        categoryFields.forEach((name, field) -> {
            double value = Double.parseDouble(field.getText());
            categoryAllocations.put(name, value);
        });

        setVisible(false);
        dispose();

        // Calculate total allocated and check against total income
        double totalAllocated = categoryFields.values().stream()
                .mapToDouble(field -> {
                    try {
                        return Double.parseDouble(field.getText());
                    } catch (NumberFormatException e) {
                        return 0.0;
                    }
                }).sum();

        // Validate that total allocated does not exceed total income
        if (totalAllocated > totalIncome) {
            JOptionPane.showMessageDialog(this, "The total allocated exceeds your total income.", "Allocation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Save the allocations and close the dialog
        categoryAllocations = new HashMap<>();
        categoryFields.forEach((name, field) -> {
            double value = Double.parseDouble(field.getText());
            categoryAllocations.put(name, value);
        });

        setVisible(false);
        dispose();
    }

    // Returns the map of category allocations
    public Map<String, Double> getCategoryAllocations() {
        return categoryAllocations;
    }
}
