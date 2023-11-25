package com.accountable.core;

import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Authentication {

    public static boolean authenticate(String username, String password) {
        // Load the hashed password from the user's specific credentials file
        String hashedPassword = loadHashedPasswordFromFile(username);
        if (hashedPassword == null) {
            return false; // User does not exist or error reading the file
        }
        return hashedPassword.equals(hashPassword(password));
    }

    public static boolean signUp(String username, String password) {
        // Check if the user's credentials file already exists
        File userCredentialsFile = new File(username + "_credentials.dat");
        if (userCredentialsFile.exists()) {
            return false; // User already exists
        }

        // Hash the password
        String hashedPassword = hashPassword(password);
        // Save the user's hashed password to their credentials file
        saveUserCredentials(username, hashedPassword);
        // Create additional user-specific files (settings and financials)
        createUserFiles(username);

        return true;
    }

    private static void createUserFiles(String username) {
        createFile(username + "_settings.dat");
        createFile(username + "_financials.dat");
    }

    private static void createFile(String filename) {
        try {
            File file = new File(filename);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
    }

    private static void saveUserCredentials(String username, String hashedPassword) {
        String filename = username + "_credentials.dat";
        try (PrintWriter out = new PrintWriter(new FileWriter(filename, false))) {
            String encryptedData = Base64.getEncoder().encodeToString((username + ":" + hashedPassword).getBytes(StandardCharsets.UTF_8));
            out.println(encryptedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String loadHashedPasswordFromFile(String username) {
        String filename = username + "_credentials.dat";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line != null) {
                String decryptedData = new String(Base64.getDecoder().decode(line), StandardCharsets.UTF_8);
                String[] userData = decryptedData.split(":");
                if (userData.length == 2 && userData[0].equals(username)) {
                    return userData[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String hashPassword(String password) {
        // Hashing logic
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
