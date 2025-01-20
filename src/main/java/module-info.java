module com.benat.cano.jasperej2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.benat.cano.jasperej2 to javafx.fxml;
    exports com.benat.cano.jasperej2;
}