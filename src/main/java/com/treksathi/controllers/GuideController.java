package com.treksathi.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GuideController {
    @FXML
    private Button availabilityButton;

    private boolean isAvailable = false;

    @FXML
    private void toggleAvailability() {
        isAvailable = !isAvailable;
        availabilityButton.setText(isAvailable ? "Set Unavailable" : "Set Available");
    }
}
