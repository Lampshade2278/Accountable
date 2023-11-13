package com.accountable.gui;

import com.accountable.core.Transaction;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import java.util.Date;

public class ExpensePanel extends JPanel {

    private JTextField expenseNameField;
    private JTextField expenseAmountField;
    private JButton addExpenseButton;
    private JList<Transaction> expenseList;
    private DefaultListModel<Transaction> expenseModel;

    public ExpensePanel() {
        setLayout(new BorderLayout());
        
        JLabel headerLabel = new JLabel("Expense Tracking", JLabel.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        JPanel expenseManagementPanel = new JPanel();
        expenseManagementPanel.setLayout(new GridLayout(0, 2)); // 0 rows, 2 columns

        expenseManagementPanel.add(new JLabel("Expense Name:"));
        expenseNameField = new JTextField(20);
        expenseManagementPanel.add(expenseNameField);

        expenseManagementPanel.add(new JLabel("Expense Amount:"));
        expenseAmountField = new JTextField(20);
        expenseManagementPanel.add(expenseAmountField);

        addExpenseButton = new JButton("Add Expense");
        addExpenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expenseName = expenseNameField.getText();
                String expenseAmount = expenseAmountField.getText();

                // Create a new Transaction object with the current date and true for isExpense
                Transaction newExpense = new Transaction(expenseName, Double.parseDouble(expenseAmount), new Date(), true);
                expenseModel.addElement(newExpense); // Add new expense to the model

                JOptionPane.showMessageDialog(ExpensePanel.this, 
                        "Expense Added: " + newExpense, 
                        "Expense Added", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        expenseManagementPanel.add(addExpenseButton);

        expenseModel = new DefaultListModel<>();
        expenseList = new JList<>(expenseModel);
        add(new JScrollPane(expenseList), BorderLayout.CENTER);

        add(expenseManagementPanel, BorderLayout.SOUTH);
    }
}
