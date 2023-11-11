package com.accountable.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class SecurityManager {
    private DataManager dataManager;

    public SecurityManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    // Method for user authentication
    public boolean authenticateUser(String username, String password) {
        List<User> users = dataManager.getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(hashPassword(password))) {
                return true;
            }
        }
        return false;
    }

    // Password hashing method
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // In a real application, proper error handling should be implemented here.
            e.printStackTrace();
            return null;
        }
    }

    // Additional security methods can be added here, like token generation, encryption/decryption of data, etc.
}
