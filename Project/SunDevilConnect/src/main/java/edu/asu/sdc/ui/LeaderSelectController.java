package edu.asu.sdc.ui;

import edu.asu.sdc.main.App;
import edu.asu.sdc.main.AppData;
import edu.asu.sdc.model.Club;
import edu.asu.sdc.model.ClubLeader;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class LeaderSelectController {

    @FXML private ComboBox<String> leaderBox;
    @FXML private Label statusLabel;

    private final AppData appData = AppData.getInstance();

    @FXML
    public void initialize() {
        leaderBox.getItems().clear();

        for (ClubLeader leader : appData.getAllLeaders()) {
            Club club = appData.findClubByLeader(leader);
            String clubName = club != null ? club.getName() : "Unknown Club";

            leaderBox.getItems().add(
                leader.getName() + " (" + leader.getUserId() + ") - " + clubName
            );
        }

        statusLabel.setText("Select a leader to continue.");
    }

    @FXML
    private void handleContinue() {
        String selected = leaderBox.getValue();

        if (selected == null || selected.isBlank()) {
            statusLabel.setText("Please select a leader first.");
            return;
        }

        String leaderId = extractId(selected);

        ClubLeader matchedLeader = null;
        for (ClubLeader leader : appData.getAllLeaders()) {
            if (leader.getUserId().equalsIgnoreCase(leaderId)) {
                matchedLeader = leader;
                break;
            }
        }

        if (matchedLeader == null) {
            statusLabel.setText("Unable to find the selected leader.");
            return;
        }

        appData.setCurrentLeader(matchedLeader);
        Club club = appData.findClubByLeader(matchedLeader);

        if (club == null) {
            statusLabel.setText("Unable to determine the club managed by this leader.");
            return;
        }

        appData.setSelectedClub(club);

        try {
            App.setRoot("leader-dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Unable to open leader dashboard.");
        }
    }

    @FXML
    private void handleBack() {
        try {
            App.setRoot("primary");
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Unable to go back.");
        }
    }

    private String extractId(String selected) {
        int start = selected.lastIndexOf('(');
        int end = selected.lastIndexOf(')');
        return selected.substring(start + 1, end);
    }
}