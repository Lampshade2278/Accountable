package com.accountable.core;

public class Progress {
    private double budget;
    private double remaining;

    public Progress(double budget, double remaining) {
        this.budget = budget;
        this.remaining = remaining;
    }

    public double getBudget() {
        return budget;
    }

    public double getRemaining() {
        return remaining;
    }

    public String getBudgetAsString() {
        return String.format("$%.2f", budget);
    }

    public String getRemainingAsString() {
        return String.format("$%.2f", remaining);
    }
}

