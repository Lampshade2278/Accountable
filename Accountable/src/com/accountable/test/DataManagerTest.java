package com.accountable.test;

import com.accountable.core.DataManager;
import com.accountable.core.Transaction;
import com.accountable.core.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DataManagerTest {

    private DataManager dataManager;

    @BeforeEach
    public void setup() {
        dataManager = new DataManager();
        // Assuming DataManager is set up with mock data or connected to a test database
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = dataManager.getAllUsers();
        assertNotNull(users, "User list should not be null");
        // More assertions based on expected mock data
    }

    @Test
    public void testGetAllTransactions() {
        List<Transaction> transactions = dataManager.getAllTransactions();
        assertNotNull(transactions, "Transaction list should not be null");
        // More assertions based on expected mock data
    }

    // Additional tests for other DataManager functionalities can be added here.
}
