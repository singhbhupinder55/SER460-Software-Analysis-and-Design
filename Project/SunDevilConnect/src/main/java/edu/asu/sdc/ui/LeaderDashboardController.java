package edu.asu.sdc.ui;

import edu.asu.sdc.controller.EventController;
import edu.asu.sdc.controller.MembershipController;
import edu.asu.sdc.main.App;
import edu.asu.sdc.main.AppData;
import edu.asu.sdc.model.Announcement;
import edu.asu.sdc.model.Club;
import edu.asu.sdc.model.Event;
import edu.asu.sdc.model.MembershipRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LeaderDashboardController {

    @FXML private Label clubNameLabel;
    @FXML private ListView<String> eventList;
    @FXML private ListView<String> membershipRequestList;
    @FXML private ListView<String> announcementList;
    @FXML private TextField announcementTitleField;
    @FXML private TextField announcementMessageField;
    @FXML private TextArea statusBox;

    @FXML private TextField eventTitleField;
    @FXML private TextField eventDescriptionField;
    @FXML private TextField eventLocationField;
    @FXML private TextField eventCategoryField;
    @FXML private DatePicker eventDatePicker;
    @FXML private TextField eventTimeField;
    @FXML private TextField eventPopularityField;
    @FXML private TextField eventCapacityField;
    @FXML private CheckBox paidCheckBox;
    @FXML private Label leaderNameLabel;

    @FXML private Button createEventButton;
    @FXML private Button updateEventButton;
    @FXML private Button cancelEventButton;
    @FXML private Button approveRequestButton;
    @FXML private Button postAnnouncementButton;

    private final AppData appData = AppData.getInstance();
    private final MembershipController membershipController = appData.getMembershipController();
    private final EventController eventController = appData.getEventController();

    private Event selectedEventForEdit = null;

    @FXML
    public void initialize() {
        Club club = appData.getSelectedClub();
            if (club == null) {
            club = appData.getClub();
        }
        clubNameLabel.setText("Managing: " + club.getName());
        
        if (appData.getCurrentLeader() != null) {
        leaderNameLabel.setText("Leader: " + appData.getCurrentLeader().getName());
        } else {
        leaderNameLabel.setText("Leader: Club Leader");
        }
        if (isCurrentClubUnavailable()) {
        statusBox.setText("This club is currently " + getCurrentClub().getStatus().toLowerCase()
            + ". Please contact an admin. Leader actions are disabled.");

        createEventButton.setDisable(true);
        updateEventButton.setDisable(true);
        cancelEventButton.setDisable(true);
        approveRequestButton.setDisable(true);
        postAnnouncementButton.setDisable(true);
        } else {
        statusBox.setText("Leader dashboard loaded.");
        }

        refreshEvents();
        refreshMembershipRequests();
        refreshAnnouncements();
    }

    @FXML
    private void handleCreateEvent() {
        try {
            String title = value(eventTitleField);
            String description = value(eventDescriptionField);
            String location = value(eventLocationField);
            String category = value(eventCategoryField);
            LocalDate date = eventDatePicker.getValue();
            String timeText = value(eventTimeField);
            String popularityText = value(eventPopularityField);
            String capacityText = value(eventCapacityField);
            boolean isPaid = paidCheckBox.isSelected();

            if (title.isEmpty() || description.isEmpty() || location.isEmpty() || category.isEmpty()
                    || date == null || timeText.isEmpty() || popularityText.isEmpty() || capacityText.isEmpty()) {
                statusBox.setText("Please fill in all event fields.");
                return;
            }

            if (!timeText.matches("^([01]\\d|2[0-3]):[0-5]\\d$")) {
            showErrorPopup("Please enter time in HH:mm format (e.g., 09:30 or 17:45).");
            return;
            }

            LocalTime time = LocalTime.parse(timeText);
            LocalDateTime dateTime = LocalDateTime.of(date, time);

            int popularity = Integer.parseInt(popularityText);
            int capacity = Integer.parseInt(capacityText);

            String eventId = "E" + System.currentTimeMillis();

            Event newEvent = new Event(
                    eventId,
                    title,
                    description,
                    dateTime,
                    location,
                    category,
                    isPaid,
                    popularity,
                    capacity,
                    "Active"
            );

            eventController.createEvent(getCurrentClub(), newEvent);
            refreshEvents();
            clearEventForm();
            selectedEventForEdit = null;

            statusBox.setText("Event created successfully.");
        } catch (Exception e) {
            statusBox.setText("Unable to create event. Check date/time and numeric fields.");
        }
    }

    @FXML
    private void handleLoadEventForEdit() {
    String selected = eventList.getSelectionModel().getSelectedItem();

    if (selected == null) {
        statusBox.setText("Please select an event first.");
        return;
    }

    String eventTitle = selected.split(" \\| ")[0];

    for (Event event : getCurrentClub().getEvents()) {
            if (event.getTitle().equals(eventTitle)) {
            selectedEventForEdit = event;

            eventTitleField.setText(event.getTitle());
            eventDescriptionField.setText(event.getDescription());
            eventLocationField.setText(event.getLocation());
            eventCategoryField.setText(event.getCategory());
            eventDatePicker.setValue(event.getDateTime().toLocalDate());
            eventTimeField.setText(event.getDateTime().toLocalTime().toString());
            eventPopularityField.setText(String.valueOf(event.getPopularityScore()));
            eventCapacityField.setText(String.valueOf(event.getCapacity()));
            paidCheckBox.setSelected(event.isPaid());

            statusBox.setText("Event loaded for editing.");
            return;
            }
        }

        statusBox.setText("Unable to load selected event.");
    }

    @FXML
    private void handleUpdateEvent() {
        if (selectedEventForEdit == null) {
            statusBox.setText("Please click 'Edit Selected Event' first.");
            return;
        }

        try {
            String title = value(eventTitleField);
            String description = value(eventDescriptionField);
            String location = value(eventLocationField);
            String category = value(eventCategoryField);
            LocalDate date = eventDatePicker.getValue();
            String timeText = value(eventTimeField);
            String popularityText = value(eventPopularityField);
            String capacityText = value(eventCapacityField);
            boolean isPaid = paidCheckBox.isSelected();

            if (title.isEmpty() || description.isEmpty() || location.isEmpty() || category.isEmpty()
                || date == null || timeText.isEmpty() || popularityText.isEmpty() || capacityText.isEmpty()) {
                statusBox.setText("Please fill in all event fields.");
                return;
                } 

            if (!timeText.matches("^([01]\\d|2[0-3]):[0-5]\\d$")) {
            showErrorPopup("Please enter time in HH:mm format (e.g., 09:30 or 17:45).");
            return;
            }

            LocalTime time = LocalTime.parse(timeText);
            LocalDateTime dateTime = LocalDateTime.of(date, time);

            int popularity = Integer.parseInt(popularityText);
            int capacity = Integer.parseInt(capacityText);

            Event updatedEvent = new Event(
                selectedEventForEdit.getEventId(),
                title,
                description,
                dateTime,
                location,
                category,
                isPaid,
                popularity,
                capacity,
                selectedEventForEdit.getStatus()
            );

            boolean updated = eventController.updateEvent(selectedEventForEdit.getEventId(), updatedEvent);

            if (updated) {
                selectedEventForEdit = null;
                clearEventForm();
                refreshEvents();
                statusBox.setText("Event updated successfully.");
            } else {
                statusBox.setText("Update failed. Event not found.");
                        }

        } catch (Exception e) {
            e.printStackTrace();
            statusBox.setText("Update failed. Check time format HH:mm and numeric fields.");
        }
    }

    @FXML
    private void handleApproveRequest() {
        String selected = membershipRequestList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            statusBox.setText("Please select a membership request first.");
            return;
        }

        String requestId = selected.split(" \\| ")[0];

        boolean approved = membershipController.approveMembershipRequest(
                getCurrentClub(),
                requestId
        );

        if (approved) {
            refreshMembershipRequests();
            statusBox.setText("Membership request approved.");
        } else {
            statusBox.setText("Unable to approve request.");
        }
    }

    @FXML
    private void handleCancelEvent() {
        String selected = eventList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            statusBox.setText("Please select an event first.");
            return;
        }

        String eventTitle = selected.split(" \\| ")[0];

        for (Event event : getCurrentClub().getEvents()) {
            if (event.getTitle().equals(eventTitle)) {
                eventController.cancelEvent(event.getEventId());
                break;
            }
        }

        refreshEvents();
        statusBox.setText("Selected event cancelled.");
    }

    @FXML
    private void handlePostAnnouncement() {
        String title = value(announcementTitleField);
        String message = value(announcementMessageField);

        if (title.isEmpty() || message.isEmpty()) {
            statusBox.setText("Please enter both announcement title and message.");
            return;
        }

        String announcementId = "A" + System.currentTimeMillis();

        Announcement announcement = new Announcement(
                announcementId,
                title,
                message,
                LocalDateTime.now()
        );

        appData.getClubController().postAnnouncement(getCurrentClub(), announcement);

        refreshAnnouncements();

        announcementTitleField.clear();
        announcementMessageField.clear();

        statusBox.setText("Announcement posted successfully.");
    }

    @FXML
    private void handleBack() {
        try {
            App.setRoot("primary");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshEvents() {
        eventList.getItems().clear();

        for (Event event : getCurrentClub().getEvents()) {
            eventList.getItems().add(
                    event.getTitle() + " | " + event.getLocation() + " | " + event.getStatus()
            );
        }
    }

    private void refreshMembershipRequests() {
        membershipRequestList.getItems().clear();

        for (MembershipRequest request : getCurrentClub().getMembershipRequests()) {
            if (request.getStatus().equalsIgnoreCase("Pending")) {
                membershipRequestList.getItems().add(
                        request.getRequestId() + " | " + request.getStudent().getName() + " | " + request.getStatus()
                );
            }
        }
    }

    private void refreshAnnouncements() {
        announcementList.getItems().clear();

        for (Announcement announcement : getCurrentClub().getAnnouncements()) {
            announcementList.getItems().add(
                    announcement.getTitle() + " - " + announcement.getMessage()
            );
        }
    }

    private void clearEventForm() {
        eventTitleField.clear();
        eventDescriptionField.clear();
        eventLocationField.clear();
        eventCategoryField.clear();
        eventDatePicker.setValue(null);
        eventTimeField.clear();
        eventPopularityField.clear();
        eventCapacityField.clear();
        paidCheckBox.setSelected(false);
    }

    private String value(TextField field) {
        return field.getText() == null ? "" : field.getText().trim();
    }

    private Club getCurrentClub() {
        Club club = appData.getSelectedClub();
        if (club == null) {
            club = appData.getClub();
        }
        return club;
    }

    private boolean isCurrentClubUnavailable() {
        Club club = getCurrentClub();
        return club.getStatus().equalsIgnoreCase("Suspended")
            || club.getStatus().equalsIgnoreCase("Denied");
    }

    private void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(statusBox.getScene().getWindow());
        alert.initModality(javafx.stage.Modality.WINDOW_MODAL);
        alert.showAndWait();
    }
}