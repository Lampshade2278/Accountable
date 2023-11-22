package com.accountable.gui;
//This is a comment
import javax.swing.*;

public class ApplicationLauncher {
    public static void main(String[] args) {
        // Set up the look and feel or any initial configurations if necessary
        setupApplication();

        // Now start with the Login Window
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
    }

    private static void setupApplication() {
        // Perform setup tasks, like configuring the look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Any other global setup can be done here
    }
}
