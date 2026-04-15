package edu.asu.sdc.ui;

import edu.asu.sdc.main.App;
import edu.asu.sdc.main.AppData;
import edu.asu.sdc.model.Club;
import edu.asu.sdc.model.Event;
import edu.asu.sdc.model.EventRegistration;
import edu.asu.sdc.model.MembershipRequest;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class StudentDashboardController {

    @FXML private TextField searchField;
    @FXML private ComboBox<String> categoryBox;
    @FXML private ComboBox<String> locationBox;
    @FXML private ComboBox<String> paidBox;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> popularityBox;
    @FXML private ListView<Event> eventList;
    @FXML private TextArea statusBox;
    @FXML private Label studentNameLabel;

    private final AppData appData = AppData.getInstance();

    @FXML
    public void initialize() {
        loadDynamicFilters();

        if (appData.getCurrentStudent() != null) {
            studentNameLabel.setText("Logged in as: " + appData.getCurrentStudent().getName());
        } else {
            studentNameLabel.setText("Logged in as: Student");
        }

        paidBox.getItems().addAll("All", "Free", "Paid");
        popularityBox.getItems().addAll("All", "10+", "15+", "20+");

        categoryBox.setValue("All");
        locationBox.setValue("All");
        paidBox.setValue("All");
        popularityBox.setValue("All");

        eventList.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Event event, boolean empty) {
                super.updateItem(event, empty);
                if (empty || event == null) {
                    setText(null);
                } else {
                    Club owningClub = appData.findClubByEvent(event);
                    String clubName = owningClub != null ? owningClub.getName() : "Unknown Club";

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a");
                    setText(event.getTitle()
                            + " | "
                            + clubName
                            + " | "
                            + event.getLocation()
                            + " | "
                            + event.getDateTime().format(formatter)
                            + " | "
                            + (event.isPaid() ? "Paid" : "Free")
                            + " | "
                            + event.getStatus());
                }
            }
        });

        loadEvents(getAllEvents());
        statusBox.setText("Showing all events across all clubs.");
    }

    private List<Event> getAllEvents() {
        List<Event> allEvents = new ArrayList<>();

        for (Club club : appData.getAllClubs()) {
            allEvents.addAll(club.getEvents());
        }

        return allEvents;
    }

    private void loadDynamicFilters() {
        List<Event> events = getAllEvents();

        Set<String> categories = new LinkedHashSet<>();
        Set<String> locations = new LinkedHashSet<>();

        categories.add("All");
        locations.add("All");

        for (Event event : events) {
            categories.add(event.getCategory());
            locations.add(event.getLocation());
        }

        categoryBox.getItems().setAll(categories);
        locationBox.getItems().setAll(locations);
    }

    private void loadEvents(List<Event> events) {
        eventList.getItems().setAll(events);
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText() == null ? "" : searchField.getText().trim().toLowerCase();
        String selectedCategory = categoryBox.getValue();
        String selectedLocation = locationBox.getValue();
        String selectedPaid = paidBox.getValue();
        LocalDate selectedDate = datePicker.getValue();
        String popularityValue = popularityBox.getValue();

        int popularityThreshold = 0;
        if (popularityValue != null && !popularityValue.equals("All")) {
            popularityThreshold = Integer.parseInt(popularityValue.replace("+", ""));
        }

        List<Event> events = new ArrayList<>(getAllEvents());
        List<Event> filtered = new ArrayList<>();

        for (Event event : events) {
            boolean matchesKeyword = keyword.isEmpty()
                    || event.getTitle().toLowerCase().contains(keyword)
                    || event.getDescription().toLowerCase().contains(keyword)
                    || event.getCategory().toLowerCase().contains(keyword);

            boolean matchesCategory = selectedCategory == null
                    || selectedCategory.equals("All")
                    || event.getCategory().equalsIgnoreCase(selectedCategory);

            boolean matchesLocation = selectedLocation == null
                    || selectedLocation.equals("All")
                    || event.getLocation().equalsIgnoreCase(selectedLocation);

            boolean matchesPaid = selectedPaid == null
                    || selectedPaid.equals("All")
                    || (selectedPaid.equals("Free") && !event.isPaid())
                    || (selectedPaid.equals("Paid") && event.isPaid());

            boolean matchesDate = selectedDate == null
                    || event.getDateTime().toLocalDate().equals(selectedDate);

            boolean matchesPopularity = popularityThreshold == 0
                    || event.getPopularityScore() >= popularityThreshold;

            if (matchesKeyword && matchesCategory && matchesLocation && matchesPaid && matchesDate && matchesPopularity) {
                filtered.add(event);
            }
        }

        loadEvents(filtered);
        statusBox.setText("Showing " + filtered.size() + " matching event(s) across all clubs.");
    }

    @FXML
    private void handleClearDate() {
        datePicker.setValue(null);
        statusBox.setText("Date filter cleared.");
    }

   @FXML
    private void handleRegister() {
        Event selected = eventList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            statusBox.setText("Please select an event first.");
        return;
        }

        if (isCurrentStudentSuspended()) {
            statusBox.setText("Your account is currently suspended. You cannot join clubs or register for events at this time. Please contact an admin.");
        return;
        }

        Club owningClub = appData.findClubByEvent(selected);
        if (owningClub == null) {
             statusBox.setText("Unable to determine the club for this event.");
        return;
        }

        if (isClubUnavailable(owningClub)) {
            statusBox.setText("This club is currently unavailable. You cannot join this club or register for its events at this time. Please contact the club leader, "
                + getLeaderNameForClub(owningClub) + ", for more information.");
            return;
        }

        EventRegistration registration =
            appData.getRegistrationController().register(appData.getCurrentStudent(), selected);

        if (registration != null) {
            statusBox.setText("Registered for: " + selected.getTitle());
        } else {
            statusBox.setText("Registration failed. Already registered or event is full.");
        }
    }

    @FXML
    private void handleViewDetails() {
        Event selected = eventList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            statusBox.setText("Please select an event first.");
            return;
        }

        appData.setSelectedEvent(selected);

        try {
            App.setRoot("event-details");
        } catch (Exception e) {
            e.printStackTrace();
            statusBox.setText("Unable to open event details.");
        }
    }

    @FXML
    private void handleJoinClub() {
        Event selected = eventList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            statusBox.setText("Please select an event first so the system knows which club you want to join.");
        return;
        }

        if (isCurrentStudentSuspended()) {
            statusBox.setText("Your account is currently suspended. You cannot join clubs or register for events at this time. Please contact an admin.");
        return;
        }

        Club owningClub = appData.findClubByEvent(selected);

        if (owningClub == null) {
            statusBox.setText("Unable to determine the club for this event.");
        return;
        }

        if (isClubUnavailable(owningClub)) {
            statusBox.setText("This club is currently unavailable. You cannot join this club or register for its events at this time. Please contact the club leader, "
                + getLeaderNameForClub(owningClub) + ", for more information.");
        return;
        }

        if (appData.getMembershipController().isStudentAlreadyMember(appData.getCurrentStudent(), owningClub)) {
            statusBox.setText(appData.getCurrentStudent().getName() + " is already a member of " + owningClub.getName() + ".");
        return;
        }

        if (appData.getMembershipController().hasPendingRequest(appData.getCurrentStudent(), owningClub)) {
            statusBox.setText("A pending membership request already exists for " + owningClub.getName() + ".");
        return;
        }

        MembershipRequest request = appData.getMembershipController()
            .requestToJoinClub(appData.getCurrentStudent(), owningClub);

        if (request != null) {
            statusBox.setText("Membership request sent to " + owningClub.getName() + ".");
        } else {
            statusBox.setText("Unable to create membership request.");
        }
    }

    @FXML
    private void handleViewClub() {
        Event selected = eventList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            statusBox.setText("Please select an event first so the system knows which club page to open.");
            return;
        }

        Club owningClub = appData.findClubByEvent(selected);

        if (owningClub == null) {
            statusBox.setText("Unable to determine the club for this event.");
            return;
        }

        appData.setSelectedClub(owningClub);

        try {
            App.setRoot("club-page");
        } catch (Exception e) {
            e.printStackTrace();
            statusBox.setText("Unable to open club page.");
        }
    }

    @FXML
    private void handleBack() {
        try {
            App.setRoot("primary");
        } catch (Exception e) {
            e.printStackTrace();
            statusBox.setText("Unable to go back.");
        }
    }


    private boolean isCurrentStudentSuspended() {
        return appData.getCurrentStudent() != null
            && appData.getCurrentStudent().getStatus().equalsIgnoreCase("Suspended");
    }

    private String getLeaderNameForClub(Club club) {
        for (var leader : appData.getAllLeaders()) {
            Club managedClub = appData.findClubByLeader(leader);
            if (managedClub != null && managedClub.getClubId().equalsIgnoreCase(club.getClubId())) {
                return leader.getName();
            }
        }
        return "the club leader";
    }

    private boolean isClubUnavailable(Club club) {
        return club != null && (
            club.getStatus().equalsIgnoreCase("Suspended")
            || club.getStatus().equalsIgnoreCase("Denied")
        );
    }
}