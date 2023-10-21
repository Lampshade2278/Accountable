package model;

import java.time.LocalDate;

public class Transaction {

    // Attributes
    private int transactionID;
    private double amount;
    private LocalDate date;
    private String category;
    private String description;

    // Constructors
    public Transaction(int transactionID, double amount, LocalDate date, String category, String description) {
        this.transactionID = transactionID;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.description = description;
    }

    // Operations

    // Getters and Setters
    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // To String for debugging purposes
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID=" + transactionID +
                ", amount=" + amount +
                ", date=" + date +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
