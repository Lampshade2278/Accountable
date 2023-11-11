package com.accountable.test;

import com.accountable.core.BusinessLogic;
import com.accountable.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BusinessLogicTest {

    private BusinessLogic businessLogic;
    private DataManager dataManager; // Mocked or real instance

    @BeforeEach
    public void setup() {
        dataManager = new DataManager(); // Assume DataManager is initialized
        businessLogic = new BusinessLogic(dataManager);
    }

    @Test
    public void testCalculateTotalExpenses() {
        double totalExpenses = businessLogic.calculateTotalExpenses();
        assertTrue(totalExpenses >= 0, "Total expenses should be non-negative");
        // More assertions can be added based on expected behavior
    }

    @Test
    public void testCalculateTotalIncome() {
        double totalIncome = businessLogic.calculateTotalIncome();
        assertTrue(totalIncome >= 0, "Total income should be non-negative");
        // More assertions can be added based on expected behavior
    }

    // Additional tests for other BusinessLogic methods can be added here.
}
