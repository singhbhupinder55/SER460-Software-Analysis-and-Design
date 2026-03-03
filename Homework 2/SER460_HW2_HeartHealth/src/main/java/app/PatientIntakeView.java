package app;

import app.model.Appointment;
import app.model.Patient;
import app.model.User;
import app.storage.DataStore;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class PatientIntakeView {

    private final Stage stage;
    private final DataStore store;
    private final User user;
    private final BorderPane root = new BorderPane();

    public PatientIntakeView(Stage stage, DataStore store, User user) {
        this.stage = stage;
        this.store = store;
        this.user = user;

        Label title = new Label("Patient Intake Form");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(20, 0, 10, 0));
        root.setTop(title);

        TextField firstNameTf = new TextField();
        TextField lastNameTf = new TextField();
        TextField emailTf = new TextField();
        TextField phoneTf = new TextField();
        TextField historyTf = new TextField();
        TextField insuranceTf = new TextField();

        DatePicker examDateDp = new DatePicker();
        examDateDp.setPrefWidth(420);
        examDateDp.setPromptText("Select exam date");

        // Disallow back-dates (past dates)
        examDateDp.setDayCellFactory(dp -> new DateCell() {
        @Override
        public void updateItem(java.time.LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        if (empty || date == null) return;

        // disable any date before today
        if (date.isBefore(java.time.LocalDate.now())) {
            setDisable(true);
            setStyle("-fx-opacity: 0.4;");
             }
            }
        });

        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);
        form.setPadding(new Insets(30));
        form.setAlignment(Pos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(140);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(420);
        form.getColumnConstraints().addAll(col1, col2);

        addRow(form, 0, "First Name:", firstNameTf);
        addRow(form, 1, "Last Name:", lastNameTf);
        addRow(form, 2, "Email:", emailTf);
        addRow(form, 3, "Phone Number:", phoneTf);
        addRow(form, 4, "Health History:", historyTf);
        addRow(form, 5, "Insurance ID:", insuranceTf);
        addRow(form, 6, "Exam Date:", examDateDp);

        Button backBtn = new Button("Back");
        backBtn.setPrefWidth(140);

        Button saveBtn = new Button("Save");
        saveBtn.setPrefWidth(140);

        HBox buttons = new HBox(15, backBtn, saveBtn);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setPadding(new Insets(10, 30, 30, 30));

        VBox centerBox = new VBox(10, form, buttons);
        centerBox.setAlignment(Pos.CENTER);
        root.setCenter(centerBox);

        backBtn.setOnAction(e ->
                stage.setScene(new Scene(new MainMenuView(stage, store, user).getRoot(), 900, 600))
        );

        saveBtn.setOnAction(e -> {
            if (isBlank(firstNameTf) || isBlank(lastNameTf) || isBlank(emailTf)
                    || isBlank(phoneTf) || isBlank(historyTf) || isBlank(insuranceTf)) {
                alert(Alert.AlertType.ERROR, "Missing Fields", "Please fill out all fields before saving.");
                return;
            }

            LocalDate examDate = examDateDp.getValue();
            if (examDate == null) {
                alert(Alert.AlertType.ERROR, "Missing Exam Date", "Please select an exam date.");
                return;
            }
            if (examDate.isBefore(LocalDate.now())) {
                alert(Alert.AlertType.ERROR, "Invalid Exam Date", "Exam date cannot be in the past.");
                return;
            }

            String newId = store.generateUniquePatientId();

            Patient p = new Patient();
            p.setPatientId(newId);
            p.setFirstName(firstNameTf.getText().trim());
            p.setLastName(lastNameTf.getText().trim());
            p.setEmail(emailTf.getText().trim());
            p.setPhoneNumber(phoneTf.getText().trim());
            p.setHealthHistory(historyTf.getText().trim());
            p.setInsuranceId(insuranceTf.getText().trim());

            if (!store.savePatientInfo(p)) {
                alert(Alert.AlertType.ERROR, "Save Failed", "Could not save patient info.");
                return;
            }

            Appointment appt = new Appointment();
            appt.setPatientId(newId);
            appt.setExamDate(examDate.toString());

            if (!store.saveAppointment(appt)) {
                alert(Alert.AlertType.ERROR, "Save Failed", "Patient saved, but appointment could not be saved.");
                return;
            }

            alert(Alert.AlertType.INFORMATION, "Saved!",
                    "Patient + Appointment saved.\n\nPatient ID: " + newId +
                            "\nPatient file: " + store.patientInfoPath(newId) +
                            "\nAppointment file: " + store.appointmentPath(newId));

            firstNameTf.clear(); lastNameTf.clear(); emailTf.clear();
            phoneTf.clear(); historyTf.clear(); insuranceTf.clear();
            examDateDp.setValue(null);
        });
    }

    private void addRow(GridPane gp, int row, String label, TextField tf) {
        Label l = new Label(label);
        l.setStyle("-fx-font-size: 14px;");
        tf.setPrefWidth(420);
        gp.add(l, 0, row);
        gp.add(tf, 1, row);
    }

    private void addRow(GridPane gp, int row, String label, DatePicker dp) {
        Label l = new Label(label);
        l.setStyle("-fx-font-size: 14px;");
        gp.add(l, 0, row);
        gp.add(dp, 1, row);
    }

    private boolean isBlank(TextField tf) {
        return tf.getText() == null || tf.getText().trim().isEmpty();
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