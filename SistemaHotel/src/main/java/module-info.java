module com.example.sistemahotel {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sistemahotel to javafx.fxml;
    exports com.example.sistemahotel;
}