package edu.asu.sdc.ui;

import edu.asu.sdc.main.App;
import edu.asu.sdc.main.AppData;
import edu.asu.sdc.model.Admin;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class AdminSelectController {

    @FXML private ComboBox<String> adminBox;
    @FXML private Label statusLabel;

    private final AppData appData = AppData.getInstance();

    @FXML
    public void initialize() {
        adminBox.getItems().clear();

        for (Admin admin : appData.getAllAdmins()) {
            adminBox.getItems().add(admin.getName() + " (" + admin.getUserId() + ")");
        }

        statusLabel.setText("Select an admin to continue.");
    }

    @FXML
    private void handleContinue() {
        String selected = adminBox.getValue();

        if (selected == null || selected.isBlank()) {
            statusLabel.setText("Please select an admin.");
            return;
        }

        String adminId = extractId(selected);
        Admin admin = appData.findAdminById(adminId);

        if (admin == null) {
            statusLabel.setText("Admin not found.");
            return;
        }

        appData.setCurrentAdmin(admin);

        try {
            App.setRoot("admin-dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Unable to open admin dashboard.");
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

    private String extractId(String selected) {
        int start = selected.lastIndexOf('(');
        int end = selected.lastIndexOf(')');
        return selected.substring(start + 1, end);
    }
}