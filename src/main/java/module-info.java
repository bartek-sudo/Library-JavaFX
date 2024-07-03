module com.example.library {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.library to javafx.fxml;
    opens com.example.library.controller to javafx.fxml;
    opens com.example.library.model to javafx.base;

    exports com.example.library;
    exports com.example.library.controller;

}