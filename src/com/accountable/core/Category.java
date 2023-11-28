// Category.java

package com.accountable.core;

public class Category {
    private String name;
    private double amountAllocated;

    public Category(String name, double amountAllocated) {
        this.name = name;
        this.amountAllocated = amountAllocated;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmountAllocated() {
        return amountAllocated;
    }

    public void setAmountAllocated(double amountAllocated) {
        this.amountAllocated = amountAllocated;
    }

}
