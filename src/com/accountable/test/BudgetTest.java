package com.accountable.test;

import com.accountable.core.Budget;
import com.accountable.core.Transaction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class BudgetTest {

    @Test
    public void testBudgetCreationAndTransactionAddition() {
        Budget budget = new Budget("Monthly Budget", 1000.0);
        assertNotNull(budget, "Budget should not be null");
        assertEquals("Monthly Budget", budget.getName(), "Budget name should match");
        assertEquals(1000.0, budget.getTotalAmount(), "Budget total amount should match");

        Transaction expense = new Transaction("Groceries", 100.0, new Date(), true);
        budget.addTransaction(expense);

        assertEquals(1, budget.getTransactions().size(), "Budget should have one transaction");
        assertEquals(expense, budget.getTransactions().get(0), "Transaction should match the added one");
    }

    @Test
    public void testBudgetCalculation() {
        Budget budget = new Budget("Monthly Budget", 1000.0);
        budget.addTransaction(new Transaction("Groceries", 100.0, new Date(), true));
        budget.addTransaction(new Transaction("Salary", 2000.0, new Date(), false));

        double total = budget.calculateTotal();
        assertEquals(1900.0, total, "Total should account for both income and expenses");
    }

    @Test
    public void testBudgetExceeding() {
        Budget budget = new Budget("Small Budget", 50.0);
        budget.addTransaction(new Transaction("Expensive Item", 100.0, new Date(), true));

        assertTrue(budget.isExceeded(), "Budget limit should be exceeded");
    }

    // Additional tests can be added to cover more aspects of the Budget model.
}
