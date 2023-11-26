package com.accountable.core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.accountable.core.Authentication.authenticate;
import static com.accountable.core.Authentication.hashPassword;

public class DataManager {
    private static final String USER_DATA_FOLDER = "user_data/";
    private static final String CREDENTIALS_SUFFIX = "_credentials.dat";
    private static final String FINANCIALS_SUFFIX = "_financials.dat";
    private static final String SETTINGS_SUFFIX = "_settings.dat";

    public DataManager() {
        new File(USER_DATA_FOLDER).mkdirs();
    }

    public static User loadUser(String username) {
        File userFile = new File(USER_DATA_FOLDER + username + CREDENTIALS_SUFFIX);
        if (!userFile.exists()) return null;
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line = reader.readLine();
            if (line != null) {
                String decryptedData = new String(Base64.getDecoder().decode(line), StandardCharsets.UTF_8);
                String[] userData = decryptedData.split(":");
                if (userData.length >= 2) {
                    return new User(userData[0], userData[1]);
                } else {
                    System.err.println("Invalid user data format for: " + username);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveUser(User user) {
        File userFile = new File(USER_DATA_FOLDER + user.getUsername() + CREDENTIALS_SUFFIX);
        File financeFile = new File(USER_DATA_FOLDER + user.getUsername() + FINANCIALS_SUFFIX);
        File settingsFile = new File(USER_DATA_FOLDER + user.getUsername() + SETTINGS_SUFFIX);

        // Ensure directory exists
        if (!userFile.getParentFile().exists()) {
            userFile.getParentFile().mkdirs();
        }

        // Write the encrypted user credentials
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, false))) {
            String encryptedData = Base64.getEncoder().encodeToString((user.getUsername() + ":" + user.getPassword()).getBytes(StandardCharsets.UTF_8));
            writer.write(encryptedData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create the financials file if it doesn't exist
        if (!financeFile.exists()) {
            try {
                financeFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Create the settings file if it doesn't exist
        if (!settingsFile.exists()) {
            try {
                settingsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void saveCredentials(User user) {
        File userFile = new File(USER_DATA_FOLDER + user.getUsername() + CREDENTIALS_SUFFIX);
        if (!userFile.getParentFile().exists()) {
            userFile.getParentFile().mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, false))) {
            String encryptedData = Base64.getEncoder().encodeToString((user.getUsername() + ":" + user.getPassword()).getBytes(StandardCharsets.UTF_8));
            writer.write(encryptedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createEmptyFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + filePath);
            e.printStackTrace();
        }
    }

    public static boolean changeUserPassword(String username, String oldPassword, String newPassword) {
        // Authenticate the user with the old password
        if (!authenticate(username, oldPassword)) {
            return false;
        }
        String hashedNewPassword = hashPassword(newPassword);
        return updateUserPassword(username, hashedNewPassword);
    }

    public static boolean updateUserPassword(String username, String newHashedPassword) {
        File userFile = new File(USER_DATA_FOLDER + username + CREDENTIALS_SUFFIX);
        if (!userFile.exists()) {
            return false;
        }
        try {
            String updatedCredentials = username + ":" + newHashedPassword;
            String encryptedData = Base64.getEncoder().encodeToString(updatedCredentials.getBytes(StandardCharsets.UTF_8));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, false))) {
                writer.write(encryptedData);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteUserAccount(String username) {
        String[] filesToDelete = {
                USER_DATA_FOLDER + username + CREDENTIALS_SUFFIX,
                USER_DATA_FOLDER + username + FINANCIALS_SUFFIX,
                USER_DATA_FOLDER + username + SETTINGS_SUFFIX
        };
        boolean allFilesDeleted = true;
        for (String filename : filesToDelete) {
            File file = new File(filename);
            if (file.exists() && !file.delete()) {
                allFilesDeleted = false;
            }
        }
        return allFilesDeleted;
    }

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

    public static List<Transaction> getAllTransactions() {
        List<Transaction> allTransactions = new ArrayList<>();
        List<User> users = getAllUsers();
        for (User user : users) {
            allTransactions.addAll(getUserFinances(user.getUsername()));
        }
        return allTransactions;
    }

    private static List<Transaction> getUserFinances(String username) {
        File financeFile = new File(USER_DATA_FOLDER + username + FINANCIALS_SUFFIX);
        List<Transaction> transactions = new ArrayList<>();
        // Add logic to read transactions from the financeFile
        return transactions;
    }

    public static Settings loadUserSettings(String username) {
        File settingsFile = new File(USER_DATA_FOLDER + username + SETTINGS_SUFFIX);
        if (!settingsFile.exists()) return new Settings(); // Assuming Settings is a class that can be instantiated

        Map<String, String> settingsMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(settingsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] keyValue = line.split("=");
                if (keyValue.length == 2) {
                    settingsMap.put(keyValue[0], keyValue[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Settings(settingsMap); // Assuming Settings has a constructor that takes a Map
    }

    public static void saveUserSettings(String username, Settings settings) {
        File settingsFile = new File(USER_DATA_FOLDER + username + SETTINGS_SUFFIX);
        // Add logic to write settings to the settingsFile
    }

    // Additional methods and logic as necessary...
}
