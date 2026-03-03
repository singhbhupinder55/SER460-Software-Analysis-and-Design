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

public class PatientView {

    private final Stage stage;
    private final DataStore store;
    private final User user;
    private final BorderPane root = new BorderPane();

    public PatientView(Stage stage, DataStore store, User user) {
        this.stage = stage;
        this.store = store;
        this.user = user;

        Label title = new Label("Patient View (Seeing the Results)");
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

        Label helloLbl = new Label("");
        helloLbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField totalTf = new TextField(); totalTf.setEditable(false);
        TextField lmTf = new TextField(); lmTf.setEditable(false);
        TextField ladTf = new TextField(); ladTf.setEditable(false);
        TextField lcxTf = new TextField(); lcxTf.setEditable(false);
        TextField rcaTf = new TextField(); rcaTf.setEditable(false);
        TextField pdaTf = new TextField(); pdaTf.setEditable(false);

        totalTf.setPrefWidth(520);
        Label totalLbl = new Label("The total Agatston CAC score");
        totalLbl.setMinWidth(260);

        HBox totalRow = new HBox(20, totalLbl, totalTf);
        totalRow.setAlignment(Pos.CENTER);

        int smallW = 220;
        lmTf.setPrefWidth(smallW);
        ladTf.setPrefWidth(smallW);
        lcxTf.setPrefWidth(smallW);
        rcaTf.setPrefWidth(smallW);
        pdaTf.setPrefWidth(smallW);

        GridPane vesselGrid = new GridPane();
        vesselGrid.setHgap(20);
        vesselGrid.setVgap(18);
        vesselGrid.setAlignment(Pos.CENTER);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setMinWidth(120);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setMinWidth(240);
        vesselGrid.getColumnConstraints().addAll(c1, c2);

        vesselGrid.add(new Label("LM:"), 0, 0);  vesselGrid.add(lmTf, 1, 0);
        vesselGrid.add(new Label("LAD:"), 0, 1); vesselGrid.add(ladTf, 1, 1);
        vesselGrid.add(new Label("LCX:"), 0, 2); vesselGrid.add(lcxTf, 1, 2);
        vesselGrid.add(new Label("RCA:"), 0, 3); vesselGrid.add(rcaTf, 1, 3);
        vesselGrid.add(new Label("PDA:"), 0, 4); vesselGrid.add(pdaTf, 1, 4);

        VBox resultsBox = new VBox(30, helloLbl, totalRow, vesselGrid);
        resultsBox.setAlignment(Pos.TOP_CENTER);
        resultsBox.setPadding(new Insets(35, 20, 20, 20));

        Button backBtn = new Button("Back");
        backBtn.setPrefWidth(140);

        HBox bottom = new HBox(backBtn);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        bottom.setPadding(new Insets(0, 30, 30, 30));

        VBox centerBox = new VBox(10, topBox, resultsBox);
        centerBox.setAlignment(Pos.TOP_CENTER);

        root.setCenter(centerBox);
        root.setBottom(bottom);

        backBtn.setOnAction(e ->
                stage.setScene(new Scene(new MainMenuView(stage, store, user).getRoot(), 900, 600))
        );

        viewBtn.setOnAction(e -> {
            String pid = patientIdTf.getText() == null ? "" : patientIdTf.getText().trim();
            if (pid.isBlank()) {
                alert(Alert.AlertType.ERROR, "Missing Patient ID", "Please enter a Patient ID.");
                return;
            }

            Patient p = store.loadPatientInfo(pid);
            if (p == null) {
                alert(Alert.AlertType.ERROR, "Wrong Patient ID", "Wrong patient ID.");
                helloLbl.setText("");
                totalTf.clear(); lmTf.clear(); ladTf.clear(); lcxTf.clear(); rcaTf.clear(); pdaTf.clear();
                return;
            }

            helloLbl.setText("Hello " + p.getFirstName() + " " + p.getLastName());

            CTScan scan = store.loadCTResults(pid);
            if (scan == null) {
                alert(Alert.AlertType.INFORMATION, "No Data", "CT scan data is not available yet for this patient.");
                totalTf.clear(); lmTf.clear(); ladTf.clear(); lcxTf.clear(); rcaTf.clear(); pdaTf.clear();
                return;
            }

            totalTf.setText(String.valueOf(scan.getTotalScore()));
            lmTf.setText(String.valueOf(scan.getLm()));
            ladTf.setText(String.valueOf(scan.getLad()));
            lcxTf.setText(String.valueOf(scan.getLcx()));
            rcaTf.setText(String.valueOf(scan.getRca()));
            pdaTf.setText(String.valueOf(scan.getPda()));
        });
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