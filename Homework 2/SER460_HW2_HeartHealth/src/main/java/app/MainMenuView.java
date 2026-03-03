package app;

import app.control.Session;
import app.model.Role;
import app.model.User;
import app.storage.DataStore;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuView {

    private final Stage stage;
    private final VBox root;
    private final DataStore store;

    public MainMenuView(Stage stage, DataStore store, User user) {
        this.stage = stage;
        this.store = store;

        Label title = new Label("Welcome To Heart Health Imaging & Recording System");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label loggedIn = new Label("Logged in as: " + user.getRole() + " (" + user.getUsername() + ")");
        loggedIn.setStyle("-fx-text-fill: gray;");

        Button intakeBtn = new Button("Patient Intake");
        Button ctTechBtn = new Button("CT Scan Tech View");
        Button patientViewBtn = new Button("Patient View");
        Button doctorViewBtn = new Button("Doctor View");
        Button logoutBtn = new Button("Logout");

        intakeBtn.setPrefWidth(260);
        ctTechBtn.setPrefWidth(260);
        patientViewBtn.setPrefWidth(260);
        doctorViewBtn.setPrefWidth(260);
        logoutBtn.setPrefWidth(260);

        // Navigation 
        intakeBtn.setOnAction(e -> stage.setScene(new Scene(new PatientIntakeView(stage, store, user).getRoot(), 900, 600)));
        ctTechBtn.setOnAction(e -> stage.setScene(new Scene(new CTScanTechView(stage, store, user).getRoot(), 900, 600)));
        patientViewBtn.setOnAction(e -> stage.setScene(new Scene(new PatientView(stage, store, user).getRoot(), 900, 600)));
        doctorViewBtn.setOnAction(e -> stage.setScene(new Scene(new DoctorView(stage, store, user).getRoot(), 900, 600)));

        // Logout
        logoutBtn.setOnAction(e -> {
            Session.clear();
            stage.setScene(new Scene(new LoginView(stage, store).getRoot(), 900, 600));
        });

        Role role = user.getRole();
        intakeBtn.setDisable(role != Role.RECEPTIONIST);
        ctTechBtn.setDisable(role != Role.CT_TECHNICIAN);
        patientViewBtn.setDisable(role != Role.PATIENT);
        doctorViewBtn.setDisable(role != Role.DOCTOR);

        root = new VBox(12, title, loggedIn, intakeBtn, ctTechBtn, patientViewBtn, doctorViewBtn, logoutBtn);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
    }

    public Parent getRoot() {
        return root;
    }
}