
package com.accountable.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class Authentication {

    private static final String USER_CREDENTIALS_FILE = "user_credentials.dat";
    private static final String CHARSET_NAME = "UTF-8";
    private static Map<String, String> userCredentials = new HashMap<>();

    static {
        try {
            loadUserCredentials();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean authenticate(String username, String password) {
        String hashedPassword = PasswordUtil.hashPassword(password);
        return hashedPassword.equals(userCredentials.get(username));
    }

    public static boolean signUp(String username, String password) {
        if (userCredentials.containsKey(username)) {
            return false; // User already exists
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        userCredentials.put(username, hashedPassword);
        saveUserCredentials(username, hashedPassword);

        return true;
    }

    private static void saveUserCredentials(String username, String hashedPassword) {
        try (PrintWriter out = new PrintWriter(new FileWriter(USER_CREDENTIALS_FILE, true))) {
            String encryptedData = Base64.getEncoder().encodeToString((username + ":" + hashedPassword).getBytes(StandardCharsets.UTF_8));
            out.println(encryptedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadUserCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_CREDENTIALS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String decryptedData = line;
                try {
                    decryptedData = new String(Base64.getDecoder().decode(line), CHARSET_NAME);
                } catch (IllegalArgumentException e) {
                    // Handle potential Base64 decoding issues
                }
                String[] userData = decryptedData.split(":");
                if (userData.length == 2) { // username:hashedPassword
                    userCredentials.put(userData[0], userData[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String hashPassword(String password) {
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
