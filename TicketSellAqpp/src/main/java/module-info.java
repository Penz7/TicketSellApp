module com.ddd.ticketsellaqpp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires org.controlsfx.controls;


    opens com.ddd.ticketsellaqpp to javafx.fxml;
    exports com.ddd.ticketsellaqpp;
    exports com.ddd.pojo;
    exports com.ddd.services;
    exports com.ddd.repostitories;
    exports com.ddd.utils;
}
