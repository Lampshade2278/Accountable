package com.accountable.test;

import com.accountable.core.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreation() {
        User user = new User("testUser", "password123", "test@example.com");
        assertNotNull(user, "User should not be null");
        assertEquals("testUser", user.getUsername(), "Username should match");
        assertEquals("password123", user.getPassword(), "Password should match");
        assertEquals("test@example.com", user.getEmail(), "Email should match");
    }

    @Test
    public void testUserModification() {
        User user = new User("testUser", "password123", "test@example.com");
        user.setUsername("newUsername");
        user.setPassword("newPassword");
        user.setEmail("new@example.com");

        assertEquals("newUsername", user.getUsername(), "Username should be updated");
        assertEquals("newPassword", user.getPassword(), "Password should be updated");
        assertEquals("new@example.com", user.getEmail(), "Email should be updated");
    }

    // Additional test cases can be added here, such as validation logic, etc.
}
