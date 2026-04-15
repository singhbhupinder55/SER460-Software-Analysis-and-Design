package edu.asu.sdc.ui;

import edu.asu.sdc.main.App;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class PrimaryController {

    @FXML
    private void handleStudent() {
        try {
            App.setRoot("student-select");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   

    @FXML
    private void handleLeader() {
        try {
            App.setRoot("leader-select");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdmin() {
        try {
            App.setRoot("admin-select");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Navigation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}