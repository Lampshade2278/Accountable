package com.accountable.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Authentication {

    public static boolean authenticate(String username, String password) {
        User user = DataManager.loadUser(username);
        if (user == null) {
            return false; // User does not exist
        }
        return hashPassword(password).equals(user.getPassword());
    }

    public static boolean signUp(String username, String password) {
        if (DataManager.loadUser(username) != null) {
            return false; // User already exists
        }

        String hashedPassword = hashPassword(password);
        User newUser = new User(username, hashedPassword);
        DataManager.saveUser(newUser);
        return true;
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
