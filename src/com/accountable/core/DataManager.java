package com.accountable.core;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManager {
    private static final String USER_DATA_FOLDER = "user_data/";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Constructor to ensure the user data directory exists
    public DataManager() {
        new File(USER_DATA_FOLDER).mkdirs();
    }

    // Load a user by username
    public static User loadUser(String username) {
        File userFile = new File(USER_DATA_FOLDER + username + "_credentials.dat");
        if (!userFile.exists()) return null;
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line = reader.readLine();
            if (line != null) {
                String[] userData = line.split(":");
                return new User(userData[0], userData[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Save a user's data
    public static void saveUser(User user) {
        File userFile = new File(USER_DATA_FOLDER + user.getUsername() + "_credentials.dat");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            writer.write(user.getUsername() + ":" + user.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a list of all users
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        File folder = new File(USER_DATA_FOLDER);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith("_credentials.dat")) {
                    String username = file.getName().replace("_credentials.dat", "");
                    User user = loadUser(username);
                    if (user != null) users.add(user);
                }
            }
        }
        return users;
    }

    // Delete a user's data
    public static boolean deleteUserFile(String username) {
        File userFile = new File(USER_DATA_FOLDER + username + "_credentials.dat");
        return userFile.exists() && userFile.delete();
    }

    // Save a transaction for a user
    public static void saveUserFinance(String username, Transaction transaction) {
        File financeFile = new File(USER_DATA_FOLDER + username + "_finances.dat");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(financeFile, true))) {
            writer.write(formatTransaction(transaction) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all financial transactions for a user
    public static List<Transaction> getUserFinances(String username) {
        List<Transaction> transactions = new ArrayList<>();
        File financeFile = new File(USER_DATA_FOLDER + username + "_finances.dat");
        if (!financeFile.exists()) return transactions;
        try (BufferedReader reader = new BufferedReader(new FileReader(financeFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transactions.add(parseTransaction(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // Format a transaction to a string for file storage
    private static String formatTransaction(Transaction transaction) {
        return transaction.getDescription() + "," +
                transaction.getAmount() + "," +
                DATE_FORMAT.format(transaction.getDate()) + "," +
                transaction.isExpense();
    }

    // Parse a transaction from a string
    private static Transaction parseTransaction(String line) {
        try {
            String[] parts = line.split(",");
            String description = parts[0];
            double amount = Double.parseDouble(parts[1]);
            Date date = DATE_FORMAT.parse(parts[2]);
            boolean isExpense = Boolean.parseBoolean(parts[3]);
            return new Transaction(description, amount, date, isExpense);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Retrieve all transactions across all users (aggregation)
    public List<Transaction> getAllTransactions() {
        List<Transaction> allTransactions = new ArrayList<>();
        for (User user : getAllUsers()) {
            allTransactions.addAll(getUserFinances(user.getUsername()));
        }
        return allTransactions;
    }
}
