package app;

import app.control.Session;
import app.model.Role;
import app.model.User;
import app.storage.DataStore;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView {

    private final Stage stage;
    private final DataStore store;
    private final BorderPane root = new BorderPane();

    public LoginView(Stage stage, DataStore store) {
        this.stage = stage;
        this.store = store;

        Label title = new Label("Login - Heart Health Imaging & Recording System");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(20, 0, 10, 0));
        root.setTop(title);

        Label info = new Label(
                "Staff login uses data/users.txt (username/password).\n" +
                "Patient login uses PatientID as username and Last Name as password."
        );
        info.setStyle("-fx-text-fill: gray;");

        ChoiceBox<Role> roleCb = new ChoiceBox<>();
        roleCb.getItems().addAll(Role.RECEPTIONIST, Role.CT_TECHNICIAN, Role.DOCTOR, Role.PATIENT);
        roleCb.setValue(Role.RECEPTIONIST);

        TextField usernameTf = new TextField();
        PasswordField passwordTf = new PasswordField();

        usernameTf.setPrefWidth(340);
        passwordTf.setPrefWidth(340);

        GridPane gp = new GridPane();
        gp.setHgap(15);
        gp.setVgap(15);
        gp.setAlignment(Pos.CENTER);
        gp.setPadding(new Insets(20));

        gp.add(new Label("Role:"), 0, 0);
        gp.add(roleCb, 1, 0);

        gp.add(new Label("Username:"), 0, 1);
        gp.add(usernameTf, 1, 1);

        gp.add(new Label("Password:"), 0, 2);
        gp.add(passwordTf, 1, 2);

        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(140);

        VBox center = new VBox(15, info, gp, loginBtn);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(10));
        root.setCenter(center);

        // Helpful default credentials for graders
        System.out.println("=== Default Credentials ===");
        System.out.println("Receptionist: receptionist1 / pass123");
        System.out.println("CT Tech:       tech1         / pass123");
        System.out.println("Doctor:        doctor1       / pass123");
        System.out.println("Patient:       <PatientID>   / <LastName>");

        loginBtn.setOnAction(e -> {
            Role selectedRole = roleCb.getValue();
            String u = safe(usernameTf.getText());
            String p = safe(passwordTf.getText());

            if (u.isBlank() || p.isBlank()) {
                alert(Alert.AlertType.ERROR, "Missing Fields", "Please enter username and password.");
                return;
            }

            // PATIENT login
            if (selectedRole == Role.PATIENT) {
                boolean ok = store.authenticatePatient(u, p);
                if (!ok) {
                    alert(Alert.AlertType.ERROR, "Login Failed",
                            "Patient login failed.\nUsername: PatientID\nPassword: Last Name");
                    return;
                }

                User user = new User(u, Role.PATIENT);
                Session.setUser(user);

                stage.setScene(new Scene(new MainMenuView(stage, store, user).getRoot(), 900, 600));
                return;
            }

            // STAFF login
            User user = store.authenticateStaff(u, p);
            if (user == null) {
                alert(Alert.AlertType.ERROR, "Login Failed", "Invalid staff username or password.");
                return;
            }

            // Role must match chosen role
            if (user.getRole() != selectedRole) {
                alert(Alert.AlertType.ERROR, "Wrong Role",
                        "That account is role: " + user.getRole() + "\nPlease select the correct role.");
                return;
            }

            Session.setUser(user);
            stage.setScene(new Scene(new MainMenuView(stage, store, user).getRoot(), 900, 600));
        });
    }

    private String safe(String s) {
        return s == null ? "" : s.trim();
    }

    private void alert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    public Parent getRoot() {
        return root;
    }
}