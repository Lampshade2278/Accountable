package com.accountable.gui;

import com.accountable.core.Transaction;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import java.util.Date;

public class IncomePanel extends JPanel {

    private JTextField incomeSourceField;
    private JTextField incomeAmountField;
    private JButton addIncomeButton;
    private JList<Transaction> incomeList;
    private DefaultListModel<Transaction> incomeModel;

    public IncomePanel() {
        setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Income Tracking", JLabel.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        JPanel incomeManagementPanel = new JPanel();
        incomeManagementPanel.setLayout(new GridLayout(0, 2)); // 0 rows, 2 columns

        incomeManagementPanel.add(new JLabel("Income Source:"));
        incomeSourceField = new JTextField(20);
        incomeManagementPanel.add(incomeSourceField);

        incomeManagementPanel.add(new JLabel("Income Amount:"));
        incomeAmountField = new JTextField(20);
        incomeManagementPanel.add(incomeAmountField);

        addIncomeButton = new JButton("Add Income");
        addIncomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String incomeSource = incomeSourceField.getText();
                String incomeAmount = incomeAmountField.getText();

                // Create a new Transaction object with the current date and false for isExpense
                Transaction newIncome = new Transaction(incomeSource, Double.parseDouble(incomeAmount), new Date(), false);
                incomeModel.addElement(newIncome); // Add new income to the model

                JOptionPane.showMessageDialog(IncomePanel.this,
                        "Income Added: " + newIncome,
                        "Income Added", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        incomeManagementPanel.add(addIncomeButton);

        incomeModel = new DefaultListModel<>();
        incomeList = new JList<>(incomeModel);
        add(new JScrollPane(incomeList), BorderLayout.CENTER);

        add(incomeManagementPanel, BorderLayout.SOUTH);
    }
}
