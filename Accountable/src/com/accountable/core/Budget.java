package com.accountable.core;

import java.util.ArrayList;
import java.util.List;

public class Budget {
    private String name;
    private double totalAmount;
    private List<Transaction> transactions;

    public Budget(String name, double totalAmount) {
        this.name = name;
        this.totalAmount = totalAmount;
        this.transactions = new ArrayList<>();
    }

    // Add a transaction to the budget
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    // Calculate the total spent or received
    public double calculateTotal() {
        double total = 0.0;
        for (Transaction t : transactions) {
            if (t.isExpense()) {
                total -= t.getAmount();
            } else {
                total += t.getAmount();
            }
        }
        return total;
    }

    // Check if the budget is exceeded
    public boolean isExceeded() {
        return calculateTotal() > totalAmount;
    }

    // Getter and setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions); // Return a copy for encapsulation
    }

    @Override
    public String toString() {
        return "Budget{" +
                "name='" + name + '\'' +
                ", totalAmount=" + totalAmount +
                ", transactions=" + transactions +
                '}';
    }

    // Additional methods related to budget management can be added here.
}
