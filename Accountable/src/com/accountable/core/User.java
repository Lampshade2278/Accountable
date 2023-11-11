package com.accountable.core;

public class User {
    private String username;
    private String password; // Note: Storing passwords in plain text is not secure in real applications.
    private String email;
    // Other user-related attributes can be added here.

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getter and setter methods for username, password, and email.
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Other methods related to user functionality can be added here.
    // For example, method to update user details, validate user data, etc.
}
