package edu.asu.sdc.ui;

import edu.asu.sdc.main.App;
import edu.asu.sdc.main.AppData;
import edu.asu.sdc.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class StudentSelectController {

    @FXML private ComboBox<String> studentBox;
    @FXML private Label statusLabel;

    private final AppData appData = AppData.getInstance();

    @FXML
    public void initialize() {
        studentBox.getItems().clear();

        for (Student s : appData.getAllStudents()) {
            studentBox.getItems().add(s.getName() + " (" + s.getUserId() + ")");
        }

        statusLabel.setText("Select a student to continue.");
    }

    @FXML
    private void handleContinue() {
        String selected = studentBox.getValue();

        if (selected == null || selected.isBlank()) {
            statusLabel.setText("Please select a student.");
            return;
        }

        String studentId = extractId(selected);
        Student student = appData.findStudentById(studentId);

        if (student == null) {
            statusLabel.setText("Student not found.");
            return;
        }

        appData.setCurrentStudent(student);

        try {
            App.setRoot("student-dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Unable to open dashboard.");
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