package app;

import app.model.CTScan;
import app.model.User;
import app.storage.DataStore;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CTScanTechView {

    private final Stage stage;
    private final DataStore store;
    private final User user;
    private final BorderPane root = new BorderPane();

    public CTScanTechView(Stage stage, DataStore store, User user) {
        this.stage = stage;
        this.store = store;
        this.user = user;

        Label title = new Label("CT Scan Technician View");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(20, 0, 10, 0));
        root.setTop(title);

        TextField patientIdTf = new TextField();
        TextField totalTf = new TextField();
        TextField lmTf = new TextField();
        TextField ladTf = new TextField();
        TextField lcxTf = new TextField();
        TextField rcaTf = new TextField();
        TextField pdaTf = new TextField();

        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);
        form.setPadding(new Insets(30));
        form.setAlignment(Pos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(220);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(420);
        form.getColumnConstraints().addAll(col1, col2);

        addRow(form, 0, "Patient ID:", patientIdTf);
        addRow(form, 1, "The total Agatston CAC score:", totalTf);

        Label vesselLbl = new Label("Vessel level Agatston CAC score");
        vesselLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        form.add(vesselLbl, 0, 2, 2, 1); 

        addRow(form, 3, "LM:", lmTf);
        addRow(form, 4, "LAD:", ladTf);
        addRow(form, 5, "LCX:", lcxTf);
        addRow(form, 6, "RCA:", rcaTf);
        addRow(form, 7, "PDA:", pdaTf);

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
            String pid = patientIdTf.getText() == null ? "" : patientIdTf.getText().trim();

            if (pid.isBlank()) {
                alert(Alert.AlertType.ERROR, "Missing Patient ID", "Please enter a Patient ID.");
                return;
            }

            if (store.loadPatientInfo(pid) == null) {
                alert(Alert.AlertType.ERROR, "Wrong Patient ID",
                        "No such patient is available.\nPlease enter a valid 5-digit Patient ID.");
                return;
            }

            Integer total = parseIntOrNull(totalTf.getText());
            Integer lm = parseIntOrNull(lmTf.getText());
            Integer lad = parseIntOrNull(ladTf.getText());
            Integer lcx = parseIntOrNull(lcxTf.getText());
            Integer rca = parseIntOrNull(rcaTf.getText());
            Integer pda = parseIntOrNull(pdaTf.getText());

            if (total == null || lm == null || lad == null || lcx == null || rca == null || pda == null) {
                alert(Alert.AlertType.ERROR, "Invalid Input", "All score fields must be whole numbers (0 or higher).");
                return;
            }

            CTScan scan = new CTScan();
            scan.setPatientId(pid);
            scan.setTotalScore(total);
            scan.setLm(lm);
            scan.setLad(lad);
            scan.setLcx(lcx);
            scan.setRca(rca);
            scan.setPda(pda);

            if (!store.saveCTResults(scan)) {
                alert(Alert.AlertType.ERROR, "Save Failed", "Could not save CT results.");
                return;
            }

            alert(Alert.AlertType.INFORMATION, "Saved!",
                    "CT Results saved successfully.\n\nFile: " + store.ctResultsPath(pid));

            totalTf.clear();
            lmTf.clear();
            ladTf.clear();
            lcxTf.clear();
            rcaTf.clear();
            pdaTf.clear();
        });
    }

    private void addRow(GridPane gp, int row, String label, TextField tf) {
        Label l = new Label(label);
        l.setStyle("-fx-font-size: 14px;");
        tf.setPrefWidth(420);
        gp.add(l, 0, row);
        gp.add(tf, 1, row);
    }

    private Integer parseIntOrNull(String text) {
        try {
            int v = Integer.parseInt(text.trim());
            return v < 0 ? null : v;
        } catch (Exception e) {
            return null;
        }
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