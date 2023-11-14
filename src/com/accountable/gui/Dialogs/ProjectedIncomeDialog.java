package com.accountable.gui.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Dialog for entering the projected monthly income.
public class ProjectedIncomeDialog extends JDialog {
    private JTextField incomeField; // Field to enter income amount
    private JTextField titleField;  // Field to enter the title for the income
    private JButton okButton;       // Button to confirm the data
    private Double projectedIncome; // The entered projected monthly income
    private String incomeTitle;     // The entered title for the income

    // Constructor for ProjectedIncomeDialog
    public ProjectedIncomeDialog(Frame owner, boolean modal) {
        super(owner, modal);
        initComponents();
    }

    // Initializes the dialog components
    private void initComponents() {
        setTitle("Set Projected Monthly Income");
        setLayout(new BorderLayout());
        setSize(300, 120);
        setLocationRelativeTo(getOwner());

        // Create a panel with a GridLayout to arrange labels and text fields
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Monthly Income:"));
        incomeField = new JTextField(10);
        panel.add(incomeField);

        panel.add(new JLabel("Income Title:"));
        titleField = new JTextField(10);
        panel.add(titleField);

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
            // Parse the income and check the title
            projectedIncome = Double.parseDouble(incomeField.getText());
            incomeTitle = titleField.getText();
            if (incomeTitle.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a title for your income.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // If successful, close the dialog
            setVisible(false);
            dispose();
        } catch (NumberFormatException e) {
            // Handle the case where the income is not a valid number
            JOptionPane.showMessageDialog(this, "Please enter a valid income amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Getter for the projected income
    public Double getProjectedIncome() {
        return projectedIncome;
    }

    // Getter for the income title
    public String getIncomeTitle() {
        return incomeTitle;
    }
}
