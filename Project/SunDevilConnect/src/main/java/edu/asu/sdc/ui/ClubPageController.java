package edu.asu.sdc.ui;

import edu.asu.sdc.main.App;
import edu.asu.sdc.main.AppData;
import edu.asu.sdc.model.Announcement;
import edu.asu.sdc.model.Club;
import edu.asu.sdc.model.Event;
import edu.asu.sdc.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ClubPageController {

    @FXML private Label clubName;
    @FXML private Label clubDescription;
    @FXML private Label clubStatus;

    @FXML private ListView<String> membersList;
    @FXML private ListView<String> announcementList;
    @FXML private ListView<String> eventList;

    private final AppData appData = AppData.getInstance();

    @FXML
    public void initialize() {
        Club club = appData.getSelectedClub();

        if (club == null) {
            club = appData.getClub();
        }

        clubName.setText(club.getName());
        clubDescription.setText(club.getDescription());
        clubStatus.setText("Status: " + club.getStatus());

        membersList.getItems().clear();
        for (Student s : club.getMembers()) {
            membersList.getItems().add(s.getName() + " (" + s.getMajor() + ")");
        }

        announcementList.getItems().clear();
        for (Announcement a : club.getAnnouncements()) {
            announcementList.getItems().add(a.getTitle() + " - " + a.getMessage());
        }

        eventList.getItems().clear();
        for (Event e : club.getEvents()) {
            eventList.getItems().add(e.getTitle() + " - " + e.getLocation() + " - " + e.getStatus());
        }
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