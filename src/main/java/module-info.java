module com.example.amo_lab3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.amo_lab3 to javafx.fxml;
    exports com.example.amo_lab3;
}