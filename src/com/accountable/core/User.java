package com.accountable.core;

public class User {
    private String username;
    private String password; // Stores the hashed password

    public User(String username, String password) {
        this.username = username;
        this.password = password; // Assuming password is already hashed
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password; // Assuming password is already hashed
    }
}
