package com.treksathi.controllers;

import com.treksathi.managers.UserManager;
import com.treksathi.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    @FXML private Label titleLabel;
    @FXML private Label noAccountLabel;
    @FXML private Button languageButton;
    @FXML private Button loginButton;
    @FXML private Hyperlink signupLink;

    private boolean isNepali = false;

    @FXML
    private void initialize() {
        // Set initial language button text
        languageButton.setText("नेपाली");
        // Add validation styling
        setupFieldValidation();
    }

    private void setupFieldValidation() {
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                usernameField.getStyleClass().remove("error-field");
            }
        });
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                passwordField.getStyleClass().remove("error-field");
            }
        });
    }

    @FXML
    private void toggleLanguage() {
        isNepali = !isNepali;
        if (isNepali) {
            languageButton.setText("En");
            titleLabel.setText("ट्रेक साथीमा लगइन गर्नुहोस्");
            usernameField.setPromptText("प्रयोगकर्ता नाम");
            passwordField.setPromptText("पासवर्ड");
            loginButton.setText("लगइन");
            noAccountLabel.setText("खाता छैन?");
            signupLink.setText("साइन अप");
        } else {
            languageButton.setText("Ne");
            titleLabel.setText("Login to TrekSathi");
            usernameField.setPromptText("Username");
            passwordField.setPromptText("Password");
            loginButton.setText("Login");
            noAccountLabel.setText("Don't have an account?");
            signupLink.setText("Sign Up");
        }
    }

    @FXML
    private void handleLogin() {
        clearErrorStyling();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        boolean hasErrors = false;
        if (username.isEmpty()) {
            usernameField.getStyleClass().add("error-field");
            hasErrors = true;
        }
        if (password.isEmpty()) {
            passwordField.getStyleClass().add("error-field");
            hasErrors = true;
        }
        if (hasErrors) {
            statusLabel.getStyleClass().remove("success-label");
            statusLabel.getStyleClass().add("error-label");
            statusLabel.setText(isNepali ? "सबै फिल्डहरू आवश्यक छन्" : "All fields are required");
            return;
        }
        User user = UserManager.authenticateUser(username, password);
        if (user != null) {
            statusLabel.getStyleClass().remove("error-label");
            statusLabel.getStyleClass().add("success-label");
            statusLabel.setText(isNepali ? "लगइन सफल" : "Login successful");

            String fileName = user.getRole().equals("TOURIST") ? "/fxml/tourist.fxml" : "/fxml/guide.fxml";
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                statusLabel.setText(isNepali ? "पृष्ठ लोड गर्न त्रुटि" : "Error loading page");
            }
        } else {
            statusLabel.getStyleClass().remove("success-label");
            statusLabel.getStyleClass().add("error-label");
            statusLabel.setText(isNepali ? "अवैध प्रयोगकर्ता नाम वा पासवर्ड" : "Invalid username or password");
        }
    }

    private void clearErrorStyling() {
        usernameField.getStyleClass().remove("error-field");
        passwordField.getStyleClass().remove("error-field");
        statusLabel.getStyleClass().remove("success-label");
        statusLabel.getStyleClass().add("error-label");
    }

    @FXML
    private void handleSignupLink() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
        Parent signupRoot = loader.load();
        Scene signupScene = new Scene(signupRoot);
        signupScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        Stage stage = (Stage) signupLink.getScene().getWindow();
        stage.setScene(signupScene);
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText(isNepali ? "स्वागत पृष्ठमा फर्कन त्रुटि" : "Error returning to welcome page");
        }
    }
}
