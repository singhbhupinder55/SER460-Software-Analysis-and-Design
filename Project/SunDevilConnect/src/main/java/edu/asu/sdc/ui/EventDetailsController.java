package edu.asu.sdc.ui;

import edu.asu.sdc.main.App;
import edu.asu.sdc.main.AppData;
import edu.asu.sdc.model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.format.DateTimeFormatter;

public class EventDetailsController {

    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label dateTimeLabel;
    @FXML private Label locationLabel;
    @FXML private Label categoryLabel;
    @FXML private Label paidLabel;
    @FXML private Label popularityLabel;
    @FXML private Label capacityLabel;
    @FXML private Label statusLabel;

    private final AppData appData = AppData.getInstance();

    @FXML
    public void initialize() {
        Event event = appData.getSelectedEvent();

        if (event == null) {
            titleLabel.setText("No event selected.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy h:mm a");

        titleLabel.setText(event.getTitle());
        descriptionLabel.setText("Description: " + event.getDescription());
        dateTimeLabel.setText("Date/Time: " + event.getDateTime().format(formatter));
        locationLabel.setText("Location: " + event.getLocation());
        categoryLabel.setText("Category: " + event.getCategory());
        paidLabel.setText("Type: " + (event.isPaid() ? "Paid" : "Free"));
        popularityLabel.setText("Popularity Score: " + event.getPopularityScore());
        capacityLabel.setText("Capacity: " + event.getCapacity());
        statusLabel.setText("Status: " + event.getStatus());
    }

    @FXML
    private void handleBack() {
        try {
            App.setRoot("student-dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}