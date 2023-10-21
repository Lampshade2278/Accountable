package model;

import java.time.LocalDate;

public class Budget {

    // Attributes
    private static int uniqueID = 1; // Static counter for generating unique budgetIDs
    private int budgetID;
    private int userID;
    private double monthlyLimit;
    private LocalDate startDate;
    private LocalDate endDate;

    // Constructors
    public Budget(int userID, double monthlyLimit, LocalDate startDate, LocalDate endDate) {
        this.budgetID = uniqueID++;
        this.userID = userID;
        this.monthlyLimit = monthlyLimit;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getter methods
    public int getBudgetID() {
        return budgetID;
    }

    public int getUserID() {
        return userID;
    }

    public double getMonthlyLimit() {
        return monthlyLimit;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    // Setter methods
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setMonthlyLimit(double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    // Operations
    public void createBudget(double monthlyLimit, LocalDate startDate, LocalDate endDate) {
        // This method can now be used to set or modify the budget details after instantiation
        this.monthlyLimit = monthlyLimit;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Other possible operations
    public void modifyBudget(double newLimit) {
        this.monthlyLimit = newLimit;
    }

    // You can add other utility methods or operations as required.

    @Override
    public String toString() {
        return "Budget [budgetID=" + budgetID + ", userID=" + userID + ", monthlyLimit=" + monthlyLimit + ", startDate="
                + startDate + ", endDate=" + endDate + "]";
    }
}
