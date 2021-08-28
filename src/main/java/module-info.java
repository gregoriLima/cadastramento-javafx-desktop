module com.cadastramento {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires okhttp3;
    requires com.google.gson;


    opens com.cadastramento to javafx.fxml, javafx.graphics, com.google.gson;
    exports com.cadastramento;
    exports com.cadastramento.client;
    opens com.cadastramento.client to javafx.fxml;
    opens com.cadastramento.controller to javafx.fxml;
    opens com.cadastramento.model;
    opens com.cadastramento.enumeration;
}