package com.treksathi.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private PieChart bookingPieChart;

    @FXML
    private BarChart<String, Number> monthlyBookingChart;

    @FXML
    private LineChart<String, Number> touristTrendChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupPieChart();
        setupBarChart();
        setupLineChart();

        // Apply custom styling to charts
        styleCharts();
    }

    private void setupPieChart() {
        // Clear any existing data
        bookingPieChart.getData().clear();

        // Add data with proper labels
        bookingPieChart.getData().addAll(
                new PieChart.Data("Guides (30%)", 30),
                new PieChart.Data("Tourists (70%)", 70)
        );

        // Set title and styling
        bookingPieChart.setTitle("User Distribution");
        bookingPieChart.setLegendVisible(true);
    }

    private void setupBarChart() {
        // Clear any existing data
        monthlyBookingChart.getData().clear();

        // Create series for bookings
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Bookings 2025");

        // Add data points
        series.getData().add(new XYChart.Data<>("Jan", 40));
        series.getData().add(new XYChart.Data<>("Feb", 60));
        series.getData().add(new XYChart.Data<>("Mar", 50));
        series.getData().add(new XYChart.Data<>("Apr", 90));
        series.getData().add(new XYChart.Data<>("May", 75));
        series.getData().add(new XYChart.Data<>("Jun", 85));

        monthlyBookingChart.getData().add(series);

        // Set chart properties
        monthlyBookingChart.setTitle("Monthly Booking Trends");
        monthlyBookingChart.setLegendVisible(true);
    }

    private void setupLineChart() {
        // Clear any existing data
        touristTrendChart.getData().clear();

        // Create series for tourist visits
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Tourist Visits");

        // Add data points
        series.getData().add(new XYChart.Data<>("Jan", 100));
        series.getData().add(new XYChart.Data<>("Feb", 120));
        series.getData().add(new XYChart.Data<>("Mar", 80));
        series.getData().add(new XYChart.Data<>("Apr", 150));
        series.getData().add(new XYChart.Data<>("May", 180));
        series.getData().add(new XYChart.Data<>("Jun", 200));

        touristTrendChart.getData().add(series);

        // Set chart properties
        touristTrendChart.setTitle("Tourist Visit Trends Over Time");
        touristTrendChart.setLegendVisible(true);
        touristTrendChart.setCreateSymbols(true);
    }

    private void styleCharts() {
        // Style Pie Chart
        bookingPieChart.setStyle("-fx-background-color: transparent;");

        // Style Bar Chart
        monthlyBookingChart.setStyle("-fx-background-color: transparent;");
        monthlyBookingChart.lookup(".chart-plot-background").setStyle("-fx-background-color: #fafafa;");

        // Style Line Chart
        touristTrendChart.setStyle("-fx-background-color: transparent;");
        touristTrendChart.lookup(".chart-plot-background").setStyle("-fx-background-color: #fafafa;");

        // Set axis labels
        if (monthlyBookingChart.getXAxis() != null) {
            monthlyBookingChart.getXAxis().setLabel("Month");
        }
        if (monthlyBookingChart.getYAxis() != null) {
            monthlyBookingChart.getYAxis().setLabel("Number of Bookings");
        }

        if (touristTrendChart.getXAxis() != null) {
            touristTrendChart.getXAxis().setLabel("Month");
        }
        if (touristTrendChart.getYAxis() != null) {
            touristTrendChart.getYAxis().setLabel("Number of Tourists");
        }
    }
}