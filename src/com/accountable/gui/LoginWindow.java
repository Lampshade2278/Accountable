package com.accountable.gui;

// Importing Dialogs
import com.accountable.gui.Dialogs.ProjectedIncomeDialog;
import com.accountable.gui.Dialogs.RegistrationDialog;
import com.accountable.gui.Dialogs.SpendingCategoriesSetupDialog;
import com.accountable.gui.Dialogs.ExpenseEntryDialog;

import com.accountable.core.Authentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginWindow extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton registerButton;


// Import statements remain unchanged


        public LoginWindow() {
            setTitle("Login");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout(5, 5));
            setSize(400, 200);
            setLocationRelativeTo(null); // Center the window

            JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
            usernameField = new JTextField();
            passwordField = new JPasswordField();
            inputPanel.add(new JLabel("   Username:"));
            inputPanel.add(usernameField);
            inputPanel.add(new JLabel("   Password:"));
            inputPanel.add(passwordField);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            loginButton = new JButton("Login");
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    login();
                }
            });

            registerButton = new JButton("Register");
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onRegisterClicked();
                }
            });

            buttonPanel.add(loginButton);
            buttonPanel.add(registerButton);

            add(inputPanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        private void login() {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                if (Authentication.authenticate(username, password)) {
                    dispose();
                    MainWindow mainWindow = new MainWindow();
                    mainWindow.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void onRegisterClicked() {
            RegistrationDialog registrationDialog = new RegistrationDialog(this);

            try {
                registrationDialog.setLocationRelativeTo(this);
                registrationDialog.setVisible(true);

                if (registrationDialog.isRegistrationSuccessful()) {
                    MainWindow mainWindow = new MainWindow();
                    mainWindow.setVisible(true);
                    startNewUserWorkflow(mainWindow);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void startNewUserWorkflow(MainWindow mainWindow) {
            ProjectedIncomeDialog projectedIncomeDialog = new ProjectedIncomeDialog(mainWindow, true);
            projectedIncomeDialog.setVisible(true);

            // Continue with category setup only if the income was entered
            if (projectedIncomeDialog.getProjectedIncome() != null) {
                SpendingCategoriesSetupDialog categoriesDialog = new SpendingCategoriesSetupDialog(this, true, projectedIncomeDialog.getProjectedIncome());
                categoriesDialog.setVisible(true);

            /*
            // Continue with expense entry only if the categories were set up
            if (categoriesDialog.getCategoryAllocations() != null) {
                ExpenseEntryDialog expenseDialog = new ExpenseEntryDialog(this, true, new ArrayList<>(categoriesDialog.getCategoryAllocations().keySet()));
                expenseDialog.setVisible(true);
            */
                // Handle the expense entry and update the budget as needed
            }

//setupDialog.isCategoriesSet(){

            //CHECK


            //}
            //ADD THE BELOW TO THE .isCategoriesSet() method
            JOptionPane.showMessageDialog(this, "Spending Categories configured.", "Configuration Complete", JOptionPane.INFORMATION_MESSAGE);

        }
    }

