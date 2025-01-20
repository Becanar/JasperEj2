module com.benat.cano.jasperej2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;


    exports com.benat.cano.jasperej2.controller;
    opens com.benat.cano.jasperej2.controller to javafx.fxml;
    exports com.benat.cano.jasperej2.app;
    opens com.benat.cano.jasperej2.app to javafx.fxml;
}