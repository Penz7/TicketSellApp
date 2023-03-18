module com.ddd.ticketsellaqpp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ddd.ticketsellaqpp to javafx.fxml;
    exports com.ddd.ticketsellaqpp;
}
