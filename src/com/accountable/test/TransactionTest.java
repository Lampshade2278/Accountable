package com.accountable.test;

import com.accountable.core.Transaction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class TransactionTest {

    @Test
    public void testTransactionCreation() {
        Transaction transaction = new Transaction("Grocery", 50.0, new Date(), true);
        assertNotNull(transaction, "Transaction should not be null");
        assertEquals("Grocery", transaction.getDescription(), "Description should match");
        assertEquals(50.0, transaction.getAmount(), "Amount should match");
        assertTrue(transaction.isExpense(), "Should be marked as an expense");
    }

    @Test
    public void testTransactionModification() {
        Transaction transaction = new Transaction("Grocery", 50.0, new Date(), true);
        transaction.setDescription("Utilities");
        transaction.setAmount(75.0);
        transaction.setExpense(false);

        assertEquals("Utilities", transaction.getDescription(), "Description should be updated");
        assertEquals(75.0, transaction.getAmount(), "Amount should be updated");
        assertFalse(transaction.isExpense(), "Should be marked as not an expense");
    }

    // Additional test cases can be added to cover more aspects of the Transaction model.
}
