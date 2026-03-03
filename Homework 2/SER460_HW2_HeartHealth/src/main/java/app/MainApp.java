package app;

import app.storage.DataStore;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        DataStore store = new DataStore();

        Scene scene = new Scene(new LoginView(stage, store).getRoot(), 900, 600);
        stage.setTitle("Heart Health Imaging & Recording System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}