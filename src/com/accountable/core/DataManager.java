package com.accountable.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DataManager {
    private static final String USER_DATA_FOLDER = "user_data/";
    private static final String CREDENTIALS_SUFFIX = "_credentials.dat";
    private static final String CATEGORIES_SUFFIX = "_categories.json";
    private static final String EXPENSES_SUFFIX = "_expenses.json";

    private static final String TRANSACTIONS_SUFFIX = "_transactions.json";

    // Method to retrieve all transactions for all users
    public static List<Transaction> getAllTransactions() {
        List<Transaction> allTransactions = new ArrayList<>();
        List<User> users = getAllUsers();

        for (User user : users) {
            List<Transaction> userTransactions = loadUserTransactions(user.getUsername());
            allTransactions.addAll(userTransactions);
        }

        return allTransactions;
    }

    // Method to load transactions for a specific user
    private static List<Transaction> loadUserTransactions(String username) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(USER_DATA_FOLDER + username + TRANSACTIONS_SUFFIX)));
            Type listType = new TypeToken<List<Transaction>>(){}.getType();
            return new Gson().fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Method to save user credentials
    public static void saveUser(User user) {
        try {
            String json = new Gson().toJson(user);
            Path path = Paths.get(USER_DATA_FOLDER + user.getUsername() + CREDENTIALS_SUFFIX);
            Files.createDirectories(path.getParent()); // Ensure the directory exists
            Files.write(path, json.getBytes(StandardCharsets.UTF_8));
            System.out.println("User saved to: " + path.toString()); // Debug log
        } catch (IOException e) {
            System.err.println("Failed to save user: " + e.getMessage()); // More detailed error logging
            JOptionPane.showMessageDialog(null, "Failed to save user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    // Method to load a user
    public static User loadUser(String username) {
        try {
            Path path = Paths.get(USER_DATA_FOLDER + username + CREDENTIALS_SUFFIX);
            String json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            System.out.println("User loaded from: " + path.toString()); // Debug log
            return new Gson().fromJson(json, User.class);
        } catch (IOException e) {
            System.err.println("Failed to load user: " + e.getMessage()); // More detailed error logging
            return null;
        }
    }

    public static boolean changeUserPassword(String username, String oldPassword, String newPassword) {
        // Fetch the user
        User user = loadUser(username);
        if (user == null) {
            return false; // User not found
        }

        // Check if the old password matches
        if (!user.getPassword().equals(Authentication.hashPassword(oldPassword))) {
            return false; // Old password does not match
        }

        // Update the user's password
        user.setPassword(Authentication.hashPassword(newPassword));
        saveUser(user); // Save the updated user
        return true;
    }


    // Method to get all users
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        File folder = new File(USER_DATA_FOLDER);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(CREDENTIALS_SUFFIX)) {
                    String username = file.getName().replace(CREDENTIALS_SUFFIX, "");
                    User user = loadUser(username);
                    if (user != null) {
                        users.add(user);
                    }
                }
            }
        }
        return users;
    }

    // Method to save categories
    public static void saveCategories(String username, List<Category> categories) {
        Gson gson = new Gson();
        String json = gson.toJson(categories);
        try {
            Files.write(Paths.get(USER_DATA_FOLDER + username + CATEGORIES_SUFFIX), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load categories
    public static List<Category> loadCategories(String username) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(USER_DATA_FOLDER + username + CATEGORIES_SUFFIX)));
            Type listType = new TypeToken<List<Category>>(){}.getType();
            return new Gson().fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Method to save expenses
    public static void saveExpenses(String username, List<Expense> expenses) {
        Gson gson = new Gson();
        String json = gson.toJson(expenses);
        try {
            Files.write(Paths.get(USER_DATA_FOLDER + username + EXPENSES_SUFFIX), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load expenses
    public static List<Expense> loadExpenses(String username) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(USER_DATA_FOLDER + username + EXPENSES_SUFFIX)));
            Type listType = new TypeToken<List<Expense>>(){}.getType();
            return new Gson().fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Method to delete a user account and associated data
    public static boolean deleteUserAccount(String username) {
        File userFile = new File(USER_DATA_FOLDER + username + CREDENTIALS_SUFFIX);
        File categoriesFile = new File(USER_DATA_FOLDER + username + CATEGORIES_SUFFIX);
        File expensesFile = new File(USER_DATA_FOLDER + username + EXPENSES_SUFFIX);
        boolean deletedUser = userFile.delete();
        boolean deletedCategories = categoriesFile.delete();
        boolean deletedExpenses = expensesFile.delete();
        return deletedUser && deletedCategories && deletedExpenses;
    }

    // Add any additional methods needed for your application
}

