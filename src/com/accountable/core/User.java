package com.accountable.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.util.Base64;

public class User {
    private String username;
    private String password; // Note: This should store the hashed password

    public User(String username, String password) {
        this.username = username;
        this.password = PasswordUtil.hashPassword(password); // Hash the password
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
        this.password = PasswordUtil.hashPassword(password); // Hash the password
    }

    public static boolean createUser(String username, String password) {
        // Delegate the user creation to the Authentication class
        return Authentication.signUp(username, password);
    }
}
