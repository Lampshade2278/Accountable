package com.accountable.core;

public class Authentication {

    // Temporary method for demonstration. In a real application, this should check against a secure data source.
    public static boolean authenticate(String username, String password) {
        // Hardcoded credentials for demonstration purposes. Replace with secure credential checking.
        return username.equals("admin") && password.equals("password");
    }
}

