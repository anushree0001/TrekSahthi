package com.treksathi.controllers;

import com.treksathi.utils.FileManagers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;


public class SignupController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    @FXML private Label titleLabel;
    @FXML private Button languageButton;
    @FXML private Button signupButton;
    @FXML private Hyperlink loginLink;
    @FXML private Button guideButton;
    @FXML private Button touristButton;

    private boolean isNepali = false;
    private String selectedRole = ""; // Track selected role

    @FXML
    private void initialize() {
        // Set initial language button text to match design
        languageButton.setText("En");

        // Add validation styling
        setupFieldValidation();
    }

    private void setupFieldValidation() {
        // Add listeners for real-time validation feedback
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
    private void handleGuideSelection() {
        if (selectedRole.equals("GUIDE")) {
            // If already selected, deselect it
            selectedRole = "";
            guideButton.getStyleClass().remove("role-tab-selected");
            guideButton.getStyleClass().add("role-tab");
        } else {
            // Select guide and deselect tourist if selected
            selectedRole = "GUIDE";

            // Update guide button styling
            guideButton.getStyleClass().remove("role-tab");
            guideButton.getStyleClass().add("role-tab-selected");

            // Deselect tourist button
            touristButton.getStyleClass().remove("role-tab-selected");
            touristButton.getStyleClass().add("role-tab");
        }
    }

    @FXML
    private void handleTouristSelection() {
        if (selectedRole.equals("TOURIST")) {
            // If already selected, deselect it
            selectedRole = "";
            touristButton.getStyleClass().remove("role-tab-selected");
            touristButton.getStyleClass().add("role-tab");
        } else {
            // Select tourist and deselect guide if selected
            selectedRole = "TOURIST";

            // Update tourist button styling
            touristButton.getStyleClass().remove("role-tab");
            touristButton.getStyleClass().add("role-tab-selected");

            // Deselect guide button
            guideButton.getStyleClass().remove("role-tab-selected");
            guideButton.getStyleClass().add("role-tab");
        }
    }

    @FXML
    private void toggleLanguage() {
        isNepali = !isNepali;
        if (isNepali) {
            // Switch to Nepali
            languageButton.setText("Ne");
            languageButton.getStyleClass().remove("language-btn-active");
            languageButton.getStyleClass().add("language-btn-inactive");

            titleLabel.setText("नयाँ खाता सिर्जना गर्नुहोस्");
            guideButton.setText("गाइड");
            touristButton.setText("पर्यटक");
            usernameField.setPromptText("अनुश्री प्रधान");
            passwordField.setPromptText("******");
            signupButton.setText("साइन अप");
            loginLink.setText("लगइन");

            // Update field labels - you might need to add these as FXML elements
            updateFieldLabels("नाम",  "पासवर्ड", "छान्नुहोस्");
        } else {
            // Switch to English
            languageButton.setText("En");
            languageButton.getStyleClass().remove("language-btn-inactive");
            languageButton.getStyleClass().add("language-btn-active");

            titleLabel.setText("Create new Account");
            guideButton.setText("Guide");
            touristButton.setText("Tourist");
            usernameField.setPromptText("Anushree Pradhan");
            passwordField.setPromptText("******");
            signupButton.setText("sign up");
            loginLink.setText("Login");

            // Update field labels
            updateFieldLabels("NAME", "PASSWORD", "CHOOSE");
        }
    }

    private void updateFieldLabels(String nameLabel, String passwordLabel, String chooseLabel) {
        // If you add Label references for field labels in FXML, update them here
        // For now, this is a placeholder method
        // You can expand this if you add @FXML Label references for the field labels
    }

    @FXML
    private void handleSignup() {
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
        } else if (password.length() < 6) {
            passwordField.getStyleClass().add("error-field");
            statusLabel.setText(isNepali ? "पासवर्ड कम्तिमा ६ अक्षरको हुनुपर्छ" : "Password must be at least 6 characters long");
            return;
        }

        if (hasErrors) {
            statusLabel.setText(isNepali ? "सबै फिल्डहरू आवश्यक छन्" : "All fields are required");
            return;
        }

        if (selectedRole.isEmpty()) {
            statusLabel.setText(isNepali ? "कृपया भूमिका चयन गर्नुहोस्" : "Please select a role");
            return;
        }

        //  Save user data to file
        FileManagers.appendToFile("users.txt", username + "," + password + "," + selectedRole);
        try {
            this.handleLoginLink();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Success feedback
        statusLabel.getStyleClass().remove("error-label");
        statusLabel.getStyleClass().add("success-label");
        statusLabel.setText(isNepali ? "साइन अप सफल भयो!" : "Sign up successful!");

        System.out.println("Signup Saved - " + username + "," + selectedRole);
    }


    private boolean isValidEmail(String email) {
        // Simple email validation
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }

    private void clearErrorStyling() {
        usernameField.getStyleClass().remove("error-field");
        passwordField.getStyleClass().remove("error-field");
        statusLabel.getStyleClass().remove("success-label");
        statusLabel.getStyleClass().add("error-label");
    }

    @FXML
    private void handleLoginLink() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent loginRoot = loader.load();
            Scene loginScene = new Scene(loginRoot);
            // Make sure to use the correct CSS file name
            loginScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            Stage stage = (Stage) loginLink.getScene().getWindow();
            stage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText(isNepali ? "लगइन पृष्ठ लोड गर्न सकिएन" : "Could not load login page");
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            Stage stage = (Stage) signupButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText(isNepali ? "स्वागत पृष्ठमा फर्कन त्रुटि" : "Error returning to welcome page");
        }
    }




}