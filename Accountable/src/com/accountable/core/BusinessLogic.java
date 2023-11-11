package com.accountable.core;

import java.util.List;

public class BusinessLogic {

    private DataManager dataManager;

    public BusinessLogic(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    // Example method: Calculate the total expenses
    public double calculateTotalExpenses() {
        List<Transaction> transactions = dataManager.getAllTransactions();
        return transactions.stream()
                           .filter(Transaction::isExpense)
                           .mapToDouble(Transaction::getAmount)
                           .sum();
    }

    // Example method: Calculate the total income
    public double calculateTotalIncome() {
        List<Transaction> transactions = dataManager.getAllTransactions();
        return transactions.stream()
                           .filter(t -> !t.isExpense())
                           .mapToDouble(Transaction::getAmount)
                           .sum();
    }

    // Additional business logic methods can be added here.
    // For example, methods for budget analysis, financial forecasting, etc.
}
