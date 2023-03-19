module com.ddd.ticketsellaqpp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;


    opens com.ddd.ticketsellaqpp to javafx.fxml;
    exports com.ddd.ticketsellaqpp;
    
}
