package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLoginScreen();
    }

    private void showLoginScreen() {
        // Create UI components
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        Text usernameLabel = new Text("Username");
        TextField usernameField = new TextField();
        Text passwordLabel = new Text("Password");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);

        // Define login action
        loginButton.setOnAction(e -> {
            if (validateLogin(usernameField.getText(), passwordField.getText())) {
                showMainAppView();
            } else {
                // Handle login failure (e.g., show an error message)
            }
        });

        Scene loginScene = new Scene(gridPane, 400, 200);
        primaryStage.setTitle("Accountable - Login");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private boolean validateLogin(String username, String password) {
        // Here, you'd check the provided user name and password against your stored data
        // For now, we'll just use a dummy check:
        return "admin".equals(username) && "password".equals(password);
    }

    private void showMainAppView() {
        // This would be the method where you'd set up your main application UI
        // For instance, setting up the TabPane from the previous example.
        // Once set up, you'd replace the primaryStage's scene with the main app's scene.
    }
}
