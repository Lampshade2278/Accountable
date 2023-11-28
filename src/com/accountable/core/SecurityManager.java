
package com.accountable.core;

public class SecurityManager {
    private DataManager dataManager;

    public SecurityManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public boolean authenticateUser(String username, String password) {
        for (User user : dataManager.getAllUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(Authentication.hashPassword(password))) {
                return true;
            }
        }
        return false;
    }
}
