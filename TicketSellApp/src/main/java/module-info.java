module com.ddd.ticketsellapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ddd.ticketsellapp to javafx.fxml;
    exports com.ddd.ticketsellapp;
}
