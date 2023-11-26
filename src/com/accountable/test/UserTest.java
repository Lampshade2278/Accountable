package com.accountable.test;

import com.accountable.core.Authentication;
import com.accountable.core.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreation() {
        User user = new User("testUser", "password123");
        assertNotNull(user, "User should not be null");
        assertEquals("testUser", user.getUsername(), "Username should match");
        // Updated to check the hashed password instead of plain text
        assertTrue(Authentication.hashPassword("password123").equals(user.getPassword()), "Hashed password should match");
    }

    @Test
    public void testUserModification() {
        User user = new User("testUser", "password123");
        user.setUsername("newUsername");
        user.setPassword("newPassword");

        assertEquals("newUsername", user.getUsername(), "Username should be updated");
        assertEquals("newPassword", user.getPassword(), "Password should be updated");
    }

    // Additional test cases can be added here, such as validation logic, etc.
}
