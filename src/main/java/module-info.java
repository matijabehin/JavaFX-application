module com.example.behin7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.behin8 to javafx.fxml;
    exports com.example.behin8;
}