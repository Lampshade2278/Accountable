package com.accountable.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class IncomePanel extends JPanel {
    private BudgetPanel budgetPanel;
    private JTextField incomeAmountField;
    private double predictedIncome = 0.0;
    private JLabel incomeAmountDisplay;
    private JLabel incomeDateDisplay;
    private YearMonth currentMonth;
    private Map<YearMonth, String> monthlyIncomes = new HashMap<>();
    private final Path incomeFilePath = Paths.get(System.getProperty("user.home"), "accountable_income.dat");

    public IncomePanel() {
        setLayout(new BorderLayout());
        currentMonth = YearMonth.now();
        loadMonthlyIncomes();

        // Title and amount panel with GridBagLayout
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        GridBagConstraints gbc = new GridBagConstraints();

        incomeDateDisplay = new JLabel();
        incomeDateDisplay.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        titlePanel.add(incomeDateDisplay, gbc);

        incomeAmountDisplay = new JLabel("$0.00");
        incomeAmountDisplay.setFont(new Font("Arial", Font.PLAIN, 48));
        gbc.gridy = 1;
        titlePanel.add(incomeAmountDisplay, gbc);

        // Input field and button
        incomeAmountField = new JTextField(10);
        JButton updateIncomeButton = new JButton("Update Income");
        styleButton(updateIncomeButton);
        updateIncomeButton.addActionListener(e -> updateIncome());

        JPanel updatePanel = new JPanel();
        updatePanel.add(incomeAmountField);
        updatePanel.add(updateIncomeButton);

        // Navigation buttons
        JButton previousButton = new JButton("Previous Month");
        styleButton(previousButton);
        previousButton.addActionListener(e -> changeMonth(-1));

        JButton nextButton = new JButton("Next Month");
        styleButton(nextButton);
        nextButton.addActionListener(e -> changeMonth(1));

        JPanel navigationPanel = new JPanel(new BorderLayout());
        navigationPanel.add(previousButton, BorderLayout.WEST);
        navigationPanel.add(nextButton, BorderLayout.EAST);

        // Adding sub-panels to main panel
        add(titlePanel, BorderLayout.NORTH);
        add(updatePanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);

        // Initial display
        displayIncomeForMonth(currentMonth);
    }

    private void updateIncome() {
        String income = incomeAmountField.getText();
        monthlyIncomes.put(currentMonth, income);
        saveMonthlyIncomes();
        displayIncomeForMonth(currentMonth);
    }

    private void changeMonth(int monthsToAdd) {
        currentMonth = currentMonth.plusMonths(monthsToAdd);
        displayIncomeForMonth(currentMonth);
    }

    private void displayIncomeForMonth(YearMonth month) {
        String income = monthlyIncomes.getOrDefault(month, "0");
        incomeAmountField.setText(income);
        incomeAmountDisplay.setText("$" + income);
        incomeDateDisplay.setText(month.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
    }

    private void saveMonthlyIncomes() {
        try (BufferedWriter writer = Files.newBufferedWriter(incomeFilePath)) {
            for (Map.Entry<YearMonth, String> entry : monthlyIncomes.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMonthlyIncomes() {
        if (!Files.exists(incomeFilePath)) return;

        try (BufferedReader reader = Files.newBufferedReader(incomeFilePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    YearMonth month = YearMonth.parse(parts[0]);
                    String income = parts[1];
                    monthlyIncomes.put(month, income);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Utility method to style buttons
    private void styleButton(JButton button) {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    // Method to be called when the user submits their income (e.g., pressing an 'Enter' button)
    public void onIncomeSubmit() {
        // Parse the income from the incomeField and store it in the predictedIncome variable
        try {
            predictedIncome = Double.parseDouble(incomeAmountField.getText());
            // Validate that the income is a positive number
            if (predictedIncome < 0) {
                throw new NumberFormatException("Income cannot be negative.");
            }
            // Ensure budgetPanel is not null and then pass the predictedIncome to it
            if (budgetPanel != null) {
                budgetPanel.setPredictedIncome(predictedIncome);
            } else {
                // Handle the case where budgetPanel has not been initialized or set
                JOptionPane.showMessageDialog(this, "Budget panel is not initialized.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            // Handle invalid input (e.g., not a number)
            JOptionPane.showMessageDialog(this, "Please enter a valid income amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }

        if (budgetPanel != null) {
            budgetPanel.setPredictedIncome(predictedIncome);
            budgetPanel.recalculateAndDisplayAllocation(); // Add this line to update the budget panel
        }

    }

    // Setter for budgetPanel to be called from outside, such as from the main application frame
    public void setBudgetPanel(BudgetPanel budgetPanel) {
        this.budgetPanel = budgetPanel;
    }

    // Getter method for predictedIncome
    public double getPredictedIncome() {
        return predictedIncome;
    }


    // Main method for testing
    public static void main(String[] args) {
        JFrame frame = new JFrame("Income Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new IncomePanel());
        frame.pack();
        frame.setSize(400, 300); // Set the window size or pack for automatic sizing
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(true);
    }
}

