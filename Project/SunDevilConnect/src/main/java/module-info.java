module edu.asu.sdc {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.asu.sdc.main to javafx.fxml;
    opens edu.asu.sdc.ui to javafx.fxml;

    exports edu.asu.sdc.main;
    exports edu.asu.sdc.ui;
    exports edu.asu.sdc.controller;
    exports edu.asu.sdc.model;
}