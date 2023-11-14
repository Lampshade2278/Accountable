package com.accountable.gui.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

// Dialog for entering a test expense.
public class ExpenseEntryDialog extends JDialog {
    private JComboBox<String> categoryComboBox; // Drop-down of spending categories
    private JTextField amountField;            // Field to enter the expense amount
    private JButton okButton;                  // Button to confirm the expense
    private String selectedCategory;           // The selected category for the expense
    private Double expenseAmount;              // The entered expense amount

    // Constructor for ExpenseEntryDialog
    public ExpenseEntryDialog(Frame owner, boolean modal, List<String> categories) {
        super(owner, modal);
        initComponents(categories);
    }

    // Initializes the dialog components
    private void initComponents(List<String> categories) {
        setTitle("Enter Test Expense");
        setLayout(new BorderLayout());
        setSize(300, 120);
        setLocationRelativeTo(getOwner());

        // Create a panel with a GridLayout to arrange labels and fields
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Category:"));
        categoryComboBox = new JComboBox<>(categories.toArray(new String[0]));
        panel.add(categoryComboBox);

        panel.add(new JLabel("Amount:"));
        amountField = new JTextField(10);
        panel.add(amountField);

        // Add the panel to the center of the dialog
        add(panel, BorderLayout.CENTER);

        // Initialize the OK button and add its action listener
        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOk();
            }
        });
        // Create a panel for the button to align it properly
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);
        // Add the button panel to the bottom of the dialog
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Action performed when the OK button is pressed
    private void onOk() {
        try {
            // Parse the expense amount and get the selected category
            expenseAmount = Double.parseDouble(amountField.getText());
            selectedCategory = (String) categoryComboBox.getSelectedItem();

            // Check if the amount is valid (not negative)
            if (expenseAmount < 0) {
                throw new NumberFormatException();
            }

            // Close the dialog if the amount is positive
            setVisible(false);
            dispose();
        } catch (NumberFormatException e) {
            // Handle the case where the amount is not a valid number
            JOptionPane.showMessageDialog(this, "Please enter a valid expense amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Getter for the selected category
    public String getSelectedCategory() {
        return selectedCategory;
    }

    // Getter for the expense amount
    public Double getExpenseAmount() {
        return expenseAmount;
    }
}
