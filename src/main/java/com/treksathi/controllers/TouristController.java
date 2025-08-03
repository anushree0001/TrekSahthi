package com.treksathi.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import java.io.*;
import java.util.Random;

public class TouristController {
    @FXML
    private FlowPane locationPane, guideList;

    @FXML
    private Label selectedGuideLabel, selectedLocationLabel, totalCostLabel;

    @FXML
    private Button confirmBookingButton;

    private static final String USER_FILE = "users.txt";
    private final int[] priceOptions = {2000, 2500, 3000, 3500, 4000, 4500, 5000};

    // Track selected items
    private VBox selectedGuideCard = null;
    private VBox selectedLocationCard = null;
    private String selectedGuideName = "";
    private String selectedLocationName = "";
    private int selectedGuidePrice = 0;
    private int selectedLocationPrice = 0;
    private String selectedLocationAltitude = "";

    @FXML
    public void initialize() {
        loadGuidesFromFile();
        loadLocations();
    }

    private void loadGuidesFromFile() {
        Random random = new Random();

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[2].equalsIgnoreCase("Guide")) {
                    String guideName = parts[0];
                    int price = priceOptions[random.nextInt(priceOptions.length)];

                    VBox card = createGuideCard(guideName, price);
                    guideList.getChildren().add(card);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private VBox createGuideCard(String guideName, int price) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 10; -fx-background-color: #f9f9f9; -fx-cursor: hand;");
        card.setPrefWidth(180);

        Label nameLabel = new Label("Guide: " + guideName);
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label priceLabel = new Label("Price: Rs " + price + " / day");
        priceLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        card.getChildren().addAll(nameLabel, priceLabel);

        // Add click handler
        card.setOnMouseClicked(e -> selectGuide(card, guideName, price));

        return card;
    }

    private void selectGuide(VBox card, String guideName, int price) {
        // Deselect previous guide
        if (selectedGuideCard != null) {
            selectedGuideCard.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 10; -fx-background-color: #f9f9f9; -fx-cursor: hand;");
        }

        // Select new guide
        selectedGuideCard = card;
        selectedGuideName = guideName;
        selectedGuidePrice = price;

        // Highlight selected card
        card.setStyle("-fx-border-color: #2196F3; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 9; -fx-background-color: #e3f2fd; -fx-cursor: hand;");

        // Update UI
        selectedGuideLabel.setText(guideName + " - Rs " + price + "/day");
        updateTotal();
    }

    private void loadLocations() {
        String[][] locations = {
                {"Annapurna Base Camp", "/images/AnnapurnaBaseCamp.jpg", "24000", "3000"},
                {"Kanchenjunga", "/images/anchenjunga Base Camp Trek.jpg", "11000", "2389"},
                {"Everest Base Camp", "/images/Everest BAse Camp.jpg", "35000", "5364"},
                {"Helambu Trek", "/images/Helambu Trek.jpg", "21000", "3600"},
                {"Khopra Ridge Trek", "/images/Khopra Ridge Trek.jpg", "27000", "3660"},
                {"Rara Lake Trek", "/images/Rara Lake Trek.jpg", "17000", "2990"},
                {"Upper Dolpo Trek", "/images/Upper Dolpo Trek.jpg", "21000", "5375"}
        };


        for (String[] location : locations) {
            String name = location[0];
            String imageUrl = location[1];
            int price = Integer.parseInt(location[2]);
            int altitude = Integer.parseInt(location[3]);

            VBox card = createLocationCard(name, imageUrl, price, altitude);
            locationPane.getChildren().add(card);
        }
    }

    private VBox createLocationCard(String name, String imageUrl, int price, int altitude) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 10; -fx-background-color: #f9f9f9; -fx-cursor: hand;");
        card.setPrefWidth(180);

        try {
            ImageView imageView = new ImageView(new Image(imageUrl, 160, 100, true, true));
            card.getChildren().add(imageView);
        } catch (Exception e) {
            // If image fails to load, add a placeholder
            Label placeholder = new Label("Image not available");
            placeholder.setStyle("-fx-text-fill: #999; -fx-font-style: italic;");
            card.getChildren().add(placeholder);
        }

        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Label altitudeLabel = new Label("Altitude: " + altitude + " m");
        altitudeLabel.setStyle("-fx-text-fill: #888; -fx-font-size: 12px;");

        Label priceLabel = new Label("Rs " + price);
        priceLabel.setStyle("-fx-text-fill: #888; -fx-font-size: 12px;");

        card.getChildren().addAll(nameLabel, altitudeLabel, priceLabel);

        // Add click handler
        card.setOnMouseClicked(e -> selectLocation(card, name, altitude,price));

        return card;
    }

    private void selectLocation(VBox card, String locationName, int altitude, int price) {
        // Deselect previous location
        if (selectedLocationCard != null) {
            selectedLocationCard.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 10; -fx-background-color: #f9f9f9; -fx-cursor: hand;");
        }

        // Select new location
        selectedLocationCard = card;
        selectedLocationName = locationName;
        if (altitude > 3000) {
            selectedLocationAltitude = altitude + " m, Warning!! Take measures for Altitude Sickness "  ;
        }else{
            selectedLocationAltitude =   altitude  + " m" ;

        }

        selectedLocationPrice = price;

        // Highlight selected card
        card.setStyle("-fx-border-color: #2196F3; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 9; -fx-background-color: #e3f2fd; -fx-cursor: hand;");

        // Update UI
        selectedLocationLabel.setText(locationName + " - Rs " + price);
        updateTotal();
    }

    private void updateTotal() {
        int total = selectedGuidePrice + selectedLocationPrice;
        totalCostLabel.setText("Rs " + total);

        // Enable confirm button only if both guide and location are selected
        confirmBookingButton.setDisable(selectedGuideCard == null || selectedLocationCard == null);
    }

    @FXML
    private void confirmBooking() {
        if (selectedGuideCard != null && selectedLocationCard != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Booking Confirmed");
            alert.setHeaderText("Booking Completed Successfully!");
            alert.setContentText("Guide: " + selectedGuideName + "\n" +
                    "Location: " + selectedLocationName + "\n" +
                    "Altitude: " + selectedLocationAltitude + "\n" +
                    "Total Cost: Rs " + (selectedGuidePrice + selectedLocationPrice));
            alert.showAndWait();

            // Reset selections after booking
            resetSelections();
        }
    }

    private void resetSelections() {
        // Reset guide selection
        if (selectedGuideCard != null) {
            selectedGuideCard.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 10; -fx-background-color: #f9f9f9; -fx-cursor: hand;");
            selectedGuideCard = null;
        }

        // Reset location selection
        if (selectedLocationCard != null) {
            selectedLocationCard.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 10; -fx-background-color: #f9f9f9; -fx-cursor: hand;");
            selectedLocationCard = null;
        }

        // Reset variables
        selectedGuideName = "";
        selectedLocationName = "";
        selectedGuidePrice = 0;
        selectedLocationPrice = 0;

        // Reset UI
        selectedGuideLabel.setText("None selected");
        selectedLocationLabel.setText("None selected");
        totalCostLabel.setText("Rs 0");
        confirmBookingButton.setDisable(true);
    }
}