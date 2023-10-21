package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import controller.PasswordCreationValidation;

public class Main extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showInitialScreen();
    }

    private void showInitialScreen() {
    	// Create and configure a GridPane.
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        Button loginButton = new Button("Login");
        Button createAccountButton = new Button("Create New Account");

        loginButton.setOnAction(e -> showLoginScreen());
        createAccountButton.setOnAction(e -> showAccountCreationScreen());

        gridPane.add(loginButton, 0, 0);
        gridPane.add(createAccountButton, 0, 1);
        
        // Create an initial screen. 
        Scene initialScene = new Scene(gridPane, 400, 250);
        primaryStage.setTitle("Accountable");
        primaryStage.setScene(initialScene);
        primaryStage.show();
    }

    private void showLoginScreen() {
    	// Setup for login screen.
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Login UI Elements.
        Text loginLabel = new Text("Login");
        TextField loginUsernameField = new TextField();
        PasswordField loginPasswordField = new PasswordField();
        Button loginButton = new Button("Login");
        Text loginStatusText = new Text();

        // Account Creation UI Elements.
        Text createLabel = new Text("Create New Account");
        TextField createUsernameField = new TextField();
        PasswordField createPasswordField = new PasswordField();
        Button createAccountButton = new Button("Create Account");
        Text createStatusText = new Text();

        // Login Logic (WIP)
        loginButton.setOnAction(e -> {
            String loginUsername = loginUsernameField.getText();
            String loginPassword = loginPasswordField.getText();
            // Placeholder login verification logic.
            if (true) { // If login was successful...
                loginStatusText.setText("Successfully logged in!");
                showMainAppView();
            } else {
                loginStatusText.setText("Incorrect username or password.");
            }
        });

        // Account Creation Logic.
        createAccountButton.setOnAction(e -> {
            if (PasswordCreationValidation.isStrongPassword(createPasswordField.getText())) {
                createStatusText.setText("Account successfully created!");
                // Placeholder to store user account info...
            } else {
                createStatusText.setText("Password not strong enough. Try again.");
            }
        });

        // Adding Login UI.
        gridPane.add(loginLabel, 0, 0);
        gridPane.add(new Text("Username:"), 0, 1);
        gridPane.add(loginUsernameField, 1, 1);
        gridPane.add(new Text("Password:"), 0, 2);
        gridPane.add(loginPasswordField, 1, 2);
        gridPane.add(loginButton, 1, 3);
        gridPane.add(loginStatusText, 1, 4);

        // Adding Account Creation UI.
        gridPane.add(createLabel, 0, 5);
        gridPane.add(new Text("Username:"), 0, 6);
        gridPane.add(createUsernameField, 1, 6);
        gridPane.add(new Text("Password:"), 0, 7);
        gridPane.add(createPasswordField, 1, 7);
        gridPane.add(createAccountButton, 1, 8);
        gridPane.add(createStatusText, 1, 9);

        Scene loginScene = new Scene(gridPane, 500, 400);
        primaryStage.setTitle("Accountable - Login");
        primaryStage.setScene(loginScene);
    }

    private void showAccountCreationScreen() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Account Creation UI.
        Text createAccountLabel = new Text("Create New Account");
        TextField createUsernameField = new TextField();
        PasswordField createPasswordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();
        Button createAccountButton = new Button("Create Account");
        Text createStatusText = new Text();
        Button continueToDashboardBtn = new Button("Continue to Dashboard");
        continueToDashboardBtn.setVisible(false);  // Initially, this button should be hidden.

        // Account Creation Logic.
        createAccountButton.setOnAction(e -> {
            String password = createPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            
            if (!password.equals(confirmPassword)) {
                createStatusText.setText("Passwords do not match. Try again.");
            } else if (PasswordCreationValidation.isStrongPassword(password)) {
                createStatusText.setText("Account successfully created!");

                // In order to make the "Continue to Dash board" button visible after successful account creation.
                continueToDashboardBtn.setVisible(true);

            } else {
                createStatusText.setText("Password not strong enough. Must be at least eight characters with one special character. Try again.");
            }
        });

        // In order to continue to Dashboard button.
        continueToDashboardBtn.setOnAction(e -> showMainAppView());

        // Adding Account Creation UI.
        gridPane.add(createAccountLabel, 0, 0, 2, 1);
        gridPane.add(new Text("Username:"), 0, 1);
        gridPane.add(createUsernameField, 1, 1);
        gridPane.add(new Text("Password:"), 0, 2);
        gridPane.add(createPasswordField, 1, 2);
        gridPane.add(new Text("Confirm Password:"), 0, 3);
        gridPane.add(confirmPasswordField, 1, 3);
        gridPane.add(createAccountButton, 1, 4);
        gridPane.add(createStatusText, 0, 5, 2, 1);
        gridPane.add(continueToDashboardBtn, 0, 6, 2, 1);

        Scene accountCreationScene = new Scene(gridPane, 500, 400);
        primaryStage.setTitle("Accountable - Create Account");
        primaryStage.setScene(accountCreationScene);
    }



    private void showMainAppView() {
        GridPane mainGridPane = new GridPane();
        mainGridPane.setPadding(new Insets(10, 10, 10, 10));
        mainGridPane.setVgap(5);
        mainGridPane.setHgap(5);

        Text welcomeText = new Text("Welcome to the Accountable Dashboard!");
        
        // Main buttons for the grid.... might change later.
        Button savingsCategoriesBtn = new Button("Savings Categories");
        Button incomeBtn = new Button("Income");
        Button expensesBtn = new Button("Expenses");
        Button reportsBtn = new Button("Reports");
        Button settingsBtn = new Button("Settings");

        // Making actions for each of the main button.
        savingsCategoriesBtn.setOnAction(e -> openSavingsCategories());
        incomeBtn.setOnAction(e -> openIncomeSection());
        expensesBtn.setOnAction(e -> openExpensesSection());
        reportsBtn.setOnAction(e -> openReportsSection());
        settingsBtn.setOnAction(e -> openSettings());

        // Adding components to the grid.
        mainGridPane.add(welcomeText, 0, 0);
        mainGridPane.add(savingsCategoriesBtn, 0, 1);
        mainGridPane.add(incomeBtn, 1, 1);
        mainGridPane.add(expensesBtn, 0, 2);
        mainGridPane.add(reportsBtn, 1, 2);
        mainGridPane.add(settingsBtn, 0, 3);

        Scene mainScene = new Scene(mainGridPane, 500, 300);
        primaryStage.setTitle("Accountable - Dashboard");
        primaryStage.setScene(mainScene);
    }

    // Placeholder methods for button functionality:
    
    private void openSavingsCategories() {
        // Logic for Savings Categories goes here...
    }

    private void openIncomeSection() {
        // Logic for Income section goes here...
    }

    private void openExpensesSection() {
        // Logic for Expenses section goes here...
    }

    private void openReportsSection() {
        // Logic for Reports goes here...
    }

    private void openSettings() {
        // Logic for Settings goes here...
    }
}
