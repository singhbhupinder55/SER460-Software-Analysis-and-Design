package app;

import app.model.CTScan;
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

public class DoctorView {

    private final Stage stage;
    private final DataStore store;
    private final User user;
    private final BorderPane root = new BorderPane();

    public DoctorView(Stage stage, DataStore store, User user) {
        this.stage = stage;
        this.store = store;
        this.user = user;

        Label title = new Label("Doctor View (Determine Risk)");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(20, 0, 10, 0));
        root.setTop(title);

        TextField patientIdTf = new TextField();
        patientIdTf.setPrefWidth(380);

        Button viewBtn = new Button("View Results");
        viewBtn.setPrefWidth(180);

        HBox lookupRow = new HBox(15, new Label("Patient ID:"), patientIdTf, viewBtn);
        lookupRow.setAlignment(Pos.CENTER);

        VBox topBox = new VBox(15, lookupRow);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10));

        TextField totalTf = new TextField(); totalTf.setEditable(false);
        TextField lmTf = new TextField(); lmTf.setEditable(false);
        TextField ladTf = new TextField(); ladTf.setEditable(false);
        TextField lcxTf = new TextField(); lcxTf.setEditable(false);
        TextField rcaTf = new TextField(); rcaTf.setEditable(false);
        TextField pdaTf = new TextField(); pdaTf.setEditable(false);

        totalTf.setPrefWidth(420);
        Label totalLbl = new Label("The total Agatston CAC score");
        totalLbl.setMinWidth(220);

        HBox totalRow = new HBox(20, totalLbl, totalTf);
        totalRow.setAlignment(Pos.CENTER_LEFT);

        int smallW = 220;
        lmTf.setPrefWidth(smallW);
        ladTf.setPrefWidth(smallW);
        lcxTf.setPrefWidth(smallW);
        rcaTf.setPrefWidth(smallW);
        pdaTf.setPrefWidth(smallW);

        GridPane vesselGrid = new GridPane();
        vesselGrid.setHgap(20);
        vesselGrid.setVgap(18);
        vesselGrid.setAlignment(Pos.CENTER_LEFT);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setMinWidth(80);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setMinWidth(240);
        vesselGrid.getColumnConstraints().addAll(c1, c2);

        vesselGrid.add(new Label("LM:"), 0, 0);  vesselGrid.add(lmTf, 1, 0);
        vesselGrid.add(new Label("LAD:"), 0, 1); vesselGrid.add(ladTf, 1, 1);
        vesselGrid.add(new Label("LCX:"), 0, 2); vesselGrid.add(lcxTf, 1, 2);
        vesselGrid.add(new Label("RCA:"), 0, 3); vesselGrid.add(rcaTf, 1, 3);
        vesselGrid.add(new Label("PDA:"), 0, 4); vesselGrid.add(pdaTf, 1, 4);

        VBox resultsBox = new VBox(25, totalRow, vesselGrid);
        resultsBox.setAlignment(Pos.TOP_LEFT);
        resultsBox.setPadding(new Insets(20, 10, 10, 10));

        TextArea riskArea = new TextArea();
        riskArea.setEditable(false);
        riskArea.setWrapText(true);
        riskArea.setPrefWidth(420);
        riskArea.setPrefHeight(240);

        Label riskLbl = new Label("RISK");
        riskLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        VBox riskBox = new VBox(10, riskLbl, riskArea);
        riskBox.setAlignment(Pos.TOP_CENTER);
        riskBox.setPadding(new Insets(20, 10, 10, 10));
        riskBox.setPrefWidth(480);

        HBox middleRow = new HBox(40, resultsBox, riskBox);
        middleRow.setAlignment(Pos.CENTER);

        Button riskBtn = new Button("Determine Risk");
        riskBtn.setPrefWidth(160);
        riskBtn.setDisable(true);

        Button emailBtn = new Button("Email Patient");
        emailBtn.setPrefWidth(160);
        emailBtn.setDisable(true);

        HBox actionRow = new HBox(15, riskBtn, emailBtn);
        actionRow.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(20, topBox, middleRow, actionRow);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setPadding(new Insets(10));

        root.setCenter(centerBox);

        Button backBtn = new Button("Back");
        backBtn.setPrefWidth(140);

        HBox bottom = new HBox(backBtn);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        bottom.setPadding(new Insets(0, 30, 30, 30));
        root.setBottom(bottom);

        backBtn.setOnAction(e ->
                stage.setScene(new Scene(new MainMenuView(stage, store, user).getRoot(), 900, 600))
        );

        final CTScan[] currentScan = new CTScan[1];
        final Patient[] currentPatient = new Patient[1];
        final String[] lastRiskText = new String[1];

        viewBtn.setOnAction(e -> {
            String pid = patientIdTf.getText() == null ? "" : patientIdTf.getText().trim();

            if (pid.isBlank()) {
                alert(Alert.AlertType.ERROR, "Missing Patient ID", "Please enter a Patient ID.");
                return;
            }

            Patient p = store.loadPatientInfo(pid);
            if (p == null) {
                alert(Alert.AlertType.ERROR, "Wrong Patient ID", "Wrong patient ID.");
                clear(totalTf, lmTf, ladTf, lcxTf, rcaTf, pdaTf);
                riskArea.clear();
                riskBtn.setDisable(true);
                emailBtn.setDisable(true);
                currentScan[0] = null;
                currentPatient[0] = null;
                lastRiskText[0] = null;
                return;
            }

            CTScan scan = store.loadCTResults(pid);
            if (scan == null) {
                alert(Alert.AlertType.INFORMATION, "No Data", "CT scan data is not available yet for this patient.");
                clear(totalTf, lmTf, ladTf, lcxTf, rcaTf, pdaTf);
                riskArea.clear();
                riskBtn.setDisable(true);
                emailBtn.setDisable(true);
                currentScan[0] = null;
                currentPatient[0] = p;
                lastRiskText[0] = null;
                return;
            }

            totalTf.setText(String.valueOf(scan.getTotalScore()));
            lmTf.setText(String.valueOf(scan.getLm()));
            ladTf.setText(String.valueOf(scan.getLad()));
            lcxTf.setText(String.valueOf(scan.getLcx()));
            rcaTf.setText(String.valueOf(scan.getRca()));
            pdaTf.setText(String.valueOf(scan.getPda()));

            riskArea.clear();
            riskBtn.setDisable(false);
            emailBtn.setDisable(true);
            currentScan[0] = scan;
            currentPatient[0] = p;
            lastRiskText[0] = null;
        });

        riskBtn.setOnAction(e -> {
            if (currentScan[0] == null) {
                alert(Alert.AlertType.INFORMATION, "No CT Data", "Load CT results first.");
                return;
            }
            String riskText = CTScan.determineRisk(currentScan[0].getTotalScore());
            riskArea.setText(riskText);
            lastRiskText[0] = riskText;
            emailBtn.setDisable(false);
        });

        emailBtn.setOnAction(e -> {
            if (currentPatient[0] == null || currentScan[0] == null || lastRiskText[0] == null) {
                alert(Alert.AlertType.INFORMATION, "Not Ready", "Load results and determine risk first.");
                return;
            }

            boolean ok = store.saveDoctorEmailProof(currentPatient[0], currentScan[0], lastRiskText[0]);
            if (!ok) {
                alert(Alert.AlertType.ERROR, "Email Failed", "Could not save email proof file.");
                return;
            }

            alert(Alert.AlertType.INFORMATION, "Email Saved",
                    "Email proof saved in data/emails.\nThis counts as 'Email Patient' functionality.");
        });
    }

    private void clear(TextField... tfs) {
        for (TextField tf : tfs) tf.clear();
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