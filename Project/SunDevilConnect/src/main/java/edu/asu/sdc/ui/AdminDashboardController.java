package edu.asu.sdc.ui;

import edu.asu.sdc.controller.AdminController;
import edu.asu.sdc.controller.ModerationController;
import edu.asu.sdc.main.App;
import edu.asu.sdc.main.AppData;
import edu.asu.sdc.model.Club;
import edu.asu.sdc.model.FlaggedContent;
import edu.asu.sdc.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;


import java.util.Optional;

public class AdminDashboardController {

    @FXML private ListView<String> clubList;
    @FXML private ListView<String> userList;
    @FXML private ListView<String> flaggedList;
    @FXML private TextArea statusBox;
    @FXML private Label adminNameLabel;

    @FXML private Button approveClubButton;
    @FXML private Button denyClubButton;
    @FXML private Button suspendClubButton;
    @FXML private Button suspendUserButton;
    @FXML private Button activateUserButton;
    @FXML private Button resolveFlagButton;
    @FXML private Button dismissFlagButton;


    private final AppData appData = AppData.getInstance();
    private final AdminController adminController = appData.getAdminController();
    private final ModerationController moderationController = appData.getModerationController();

    @FXML
    public void initialize() {
        if (appData.getCurrentAdmin() != null) {
            adminNameLabel.setText("Logged in as: " + appData.getCurrentAdmin().getName());
        } else {
            adminNameLabel.setText("Logged in as: Admin");
        }
        bindButtons();
        refreshAll();
        statusBox.setText("Admin dashboard loaded.");
    }

    private void bindButtons() {
        approveClubButton.disableProperty().bind(clubList.getSelectionModel().selectedItemProperty().isNull());
        denyClubButton.disableProperty().bind(clubList.getSelectionModel().selectedItemProperty().isNull());
        suspendClubButton.disableProperty().bind(clubList.getSelectionModel().selectedItemProperty().isNull());

        suspendUserButton.disableProperty().bind(userList.getSelectionModel().selectedItemProperty().isNull());
        activateUserButton.disableProperty().bind(userList.getSelectionModel().selectedItemProperty().isNull());

        resolveFlagButton.disableProperty().bind(flaggedList.getSelectionModel().selectedItemProperty().isNull());
        dismissFlagButton.disableProperty().bind(flaggedList.getSelectionModel().selectedItemProperty().isNull());
    }

    private void refreshAll() {
        refreshClubs();
        refreshUsers();
        refreshFlags();
    }

    private void refreshClubs() {
        clubList.getItems().clear();
        for (Club c : adminController.viewAllClubs()) {
            clubList.getItems().add(c.getName() + " (" + c.getClubId() + ") — " + c.getStatus());
        }
    }

    private void refreshUsers() {
        userList.getItems().clear();
        for (User u : adminController.viewAllUsers()) {
            userList.getItems().add(u.getName() + " (" + u.getUserId() + ") — " + u.getStatus());
        }
    }

    private void refreshFlags() {
        flaggedList.getItems().clear();
        for (FlaggedContent f : moderationController.reviewFlaggedContent()) {
            if (f.getStatus().equalsIgnoreCase("Open")) {
                flaggedList.getItems().add(f.getContentType() + " (" + f.getFlagId() + ") — " + f.getStatus());
            }
        }
    }

    private String extractIdFromClub(String selected) {
        int start = selected.lastIndexOf('(');
        int end = selected.lastIndexOf(')');
        return selected.substring(start + 1, end);
    }

    private String extractIdFromUser(String selected) {
        int start = selected.lastIndexOf('(');
        int end = selected.lastIndexOf(')');
        return selected.substring(start + 1, end);
    }

    private String extractIdFromFlag(String selected) {
        int start = selected.lastIndexOf('(');
        int end = selected.lastIndexOf(')');
        return selected.substring(start + 1, end);
    }

    private boolean confirmAction(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Action");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.initOwner(statusBox.getScene().getWindow());
        alert.initModality(javafx.stage.Modality.WINDOW_MODAL);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @FXML
    private void handleApproveClub() {
        String selected = clubList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (!confirmAction("Approve Club", "Are you sure you want to approve this club?")) {
            return;
        }

        if (adminController.approveClub(extractIdFromClub(selected))) {
            statusBox.setText("Club approved successfully.");
            refreshClubs();
        } else {
            statusBox.setText("Unable to approve club.");
        }
    }

    @FXML
    private void handleDenyClub() {
        String selected = clubList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (!confirmAction("Deny Club", "Are you sure you want to deny this club?")) {
            return;
        }

        if (adminController.denyClub(extractIdFromClub(selected))) {
            statusBox.setText("Club denied successfully.");
            refreshClubs();
        } else {
            statusBox.setText("Unable to deny club.");
        }
    }

    @FXML
    private void handleSuspendClub() {
        String selected = clubList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (!confirmAction("Suspend Club", "Are you sure you want to suspend this club?")) {
            return;
        }

        if (adminController.suspendClub(extractIdFromClub(selected))) {
            statusBox.setText("Club suspended successfully.");
            refreshClubs();
        } else {
            statusBox.setText("Unable to suspend club.");
        }
    }

    @FXML
    private void handleSuspendUser() {
        String selected = userList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (!confirmAction("Suspend User", "Are you sure you want to suspend this user?")) {
            return;
        }

        if (adminController.suspendUser(extractIdFromUser(selected))) {
            statusBox.setText("User suspended successfully.");
            refreshUsers();
        } else {
            statusBox.setText("Unable to suspend user.");
        }
    }

    @FXML
    private void handleActivateUser() {
        String selected = userList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (!confirmAction("Activate User", "Are you sure you want to activate this user?")) {
            return;
        }

        if (adminController.activateUser(extractIdFromUser(selected))) {
            statusBox.setText("User activated successfully.");
            refreshUsers();
        } else {
            statusBox.setText("Unable to activate user.");
        }
    }

    @FXML
    private void handleResolveFlag() {
        String selected = flaggedList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (!confirmAction("Resolve Flag", "Are you sure you want to resolve this flagged content?")) {
            return;
        }

        if (moderationController.resolveFlaggedContent(extractIdFromFlag(selected))) {
            statusBox.setText("Flag resolved successfully.");
            refreshFlags();
        } else {
            statusBox.setText("Unable to resolve flag.");
        }
    }

    @FXML
    private void handleDismissFlag() {
        String selected = flaggedList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (!confirmAction("Dismiss Flag", "Are you sure you want to dismiss this flagged content?")) {
            return;
        }

        if (moderationController.dismissFlaggedContent(extractIdFromFlag(selected))) {
            statusBox.setText("Flag dismissed successfully.");
            refreshFlags();
        } else {
            statusBox.setText("Unable to dismiss flag.");
        }
    }

    @FXML
    private void handleBack() {
        try {
            App.setRoot("primary");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}