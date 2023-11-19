package com.accountable.gui.Dialogs;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

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
        setSize(600, 180); // Increased the height to accommodate the subtitle
        setLocationRelativeTo(getOwner());

        // Create a panel with a GridBagLayout to arrange labels and text fields
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add Income Title label and text field
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Income Title (i.e. Your Occupation):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        titleField = new JTextField(10);

        panel.add(titleField, gbc);

        // Add Monthly Income label and text field
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Amount:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        incomeField = new JTextField(10);
        incomeField.setText("$"); // Set initial text to "$"
        panel.add(incomeField, gbc);

        // Create a titled border with a longer subtitle using HTML for line break
        String subtitle = "This is an estimate of how much income you expect to earn by the end of each month";
        TitledBorder titledBorder = BorderFactory.createTitledBorder(subtitle);
        titledBorder.setTitleFont(new Font("Arial", Font.PLAIN, 11));
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        titledBorder.setTitlePosition(TitledBorder.TOP);

        // Add the panel to the center of the dialog with the titled border
        panel.setBorder(titledBorder);
        add(panel, BorderLayout.CENTER);

        // Initialize the OK button and add its action listener using lambda expression
        okButton = new JButton("OK");
        okButton.addActionListener(e -> onOk());

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
            projectedIncome = Double.parseDouble(incomeField.getText().substring(1)); // Remove "$" before parsing
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
