package com.accountable.core;

import java.util.Date;

public class Transaction {
    private String description;
    private double amount;
    private Date date;
    private boolean isExpense; // True for expense, false for income

    public Transaction(String description, double amount, Date date, boolean isExpense) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.isExpense = isExpense;
    }

    // Getter and setter methods
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public void setExpense(boolean isExpense) {
        this.isExpense = isExpense;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", isExpense=" + isExpense +
                '}';
    }

    // Additional methods for transaction management can be added here.
}
