module com.example.electroniccommerce {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.electroniccommerce to javafx.fxml;
    exports com.example.electroniccommerce;
}