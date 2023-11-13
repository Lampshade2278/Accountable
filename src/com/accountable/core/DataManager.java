
package com.accountable.core;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DataManager {
    private static final String USER_DATA_FILE = "user_credentials.dat";

    public DataManager() {
        // Initialization for data storage
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String decryptedData = new String(Base64.getDecoder().decode(line), StandardCharsets.UTF_8);
                String[] userData = decryptedData.split(":");
                if (userData.length == 2) { // username:hashedPassword
                    users.add(new User(userData[0], userData[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<Transaction> getAllTransactions() {
        // Placeholder implementation, as the specific details of transactions are not provided
        return new ArrayList<>(); // Return an empty list for now
    }
}
