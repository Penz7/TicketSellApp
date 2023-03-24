/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.pojo.Route;
import com.ddd.pojo.Station;
import com.ddd.repostitories.StationRepostitory;
import com.ddd.services.StationService;
import com.ddd.utils.MessageBox;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class BookingController implements Initializable {

    @FXML
    private void backMenu() {
        try {
            App.setRoot("home-customer");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private TextField txtSearchDestination;
    @FXML
    private TextField txtSearchDeparture;
    @FXML
    TableView<Route> tvRoute;
    @FXML
    DatePicker dpDateOrder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.loadTableStation();
            this.loadStationData(null);
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyBenXeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        TextFields.bindAutoCompletion(txtSearchDestination, getAllNameStation());
        TextFields.bindAutoCompletion(txtSearchDeparture, getAllNameStation());
    }

    private List<String> getAllNameStation() {
        List<String> name = new ArrayList<>();
        List<Station> listStation = new ArrayList<>();
        StationService sr = new StationService();
        try {
            listStation = sr.getAllStation();
        } catch (SQLException ex) {
            Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO
        listStation.forEach(ls -> {
            name.add(ls.getStationName());
        });

        return name;
    }

    public void findRoute(ActionEvent evt) {

    }

    private void loadTableColumns() {
        TableColumn<Route, Integer> colContent = new TableColumn("Mã chuyến xe");
        colContent.setCellValueFactory(new PropertyValueFactory("routeId"));
        colContent.setPrefWidth(250);

        TableColumn<Route, String> col1 = new TableColumn("Tên chuyến xe");
        col1.setCellValueFactory(new PropertyValueFactory("nameRoute"));

        TableColumn<Route, String> col2 = new TableColumn("Tên xe");
        col2.setCellValueFactory(new PropertyValueFactory("coach"));

        TableColumn<Route, Time> col3 = new TableColumn("Giờ khởi hành");
        col3.setCellValueFactory(new PropertyValueFactory("departureTime"));

        TableColumn<Route, Integer> col4 = new TableColumn("Ghế");
        col4.setCellValueFactory(new PropertyValueFactory("couchette"));

        TableColumn<Route, Integer> col5 = new TableColumn("Ghế");
        col5.setCellValueFactory(new PropertyValueFactory("couchette"));

        TableColumn<Route, BigDecimal> col6 = new TableColumn("Giá vé");
        col6.setCellValueFactory(new PropertyValueFactory("fare"));

        TableColumn colOrderTicket = new TableColumn();
        colOrderTicket.setCellFactory(r -> {
            Button btn = new Button("Đặt");

            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Đặt vé",
                        "Bạn có chắc đặt vé này",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        MessageBox.getBox("Đặt vé", "Đặt vé thành công", Alert.AlertType.INFORMATION).show();
                    }
                });
            });
            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });

        this.tvRoute.getColumns().addAll(col1, col2, col3, col4, col5, col6, colOrderTicket);
    }
}
