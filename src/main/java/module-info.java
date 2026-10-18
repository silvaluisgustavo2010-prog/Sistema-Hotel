module com.example.sistemahotel {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.sistemahotel to javafx.fxml;
    opens com.example.sistemahotel.controller to javafx.fxml;

    exports com.example.sistemahotel;
    exports com.example.sistemahotel.controller;
    exports com.example.sistemahotel.model;
    exports com.example.sistemahotel.service;
}