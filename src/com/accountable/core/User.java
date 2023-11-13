package com.accountable.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.util.Base64;

public class User {
    private String username;
    private String password; // Note: This should store the hashed password
    // private String email; // Email is not used in registration

    // File to store user credentials
    private static final String USER_DATA_FILE = "user_credentials.dat";

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

    // public String getEmail() { // Email getter commented out
    //     return email;
    // }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = PasswordUtil.hashPassword(password); // Hash the password
    }

    // public void setEmail(String email) { // Email setter commented out
    //     this.email = email;
    // }

    // Hash password method
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (int i = 0; i < encodedhash.length; i++) {
                String hex = Integer.toHexString(0xff & encodedhash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean createUser(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        if (hashedPassword == null) {
            return false; // Hashing failed
        }

        if (usernameExists(username)) {
            return false; // Username already exists
        }

        return writeUserDataToFile(username, hashedPassword);
    }

    private static boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.split(",")[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean writeUserDataToFile(String username, String hashedPassword) {
        try (FileWriter fw = new FileWriter(USER_DATA_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            String encryptedData = Base64.getEncoder().encodeToString((username + ":" + hashedPassword).getBytes(StandardCharsets.UTF_8));
            out.println(encryptedData);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

