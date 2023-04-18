/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.pojo.Couchette;
import com.ddd.repostitories.CouchetteRepostitory;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class SeatController implements Initializable {

    static CouchetteRepostitory s = new CouchetteRepostitory();

    @FXML
    private Button btnSeat;

    @FXML
    private void getSeat() {
        try {
            Label idLabel = new Label("Nhập id của xe cần tìm:");
            TextField idTextField = new TextField();
            Button searchBtn = new Button("Tìm kiếm");

            TableView<Couchette> seatTable = new TableView<>();
            TableColumn<Couchette, Integer> idColumn = new TableColumn<>("Số ghế");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("ID_Ghe"));

            TableColumn<Couchette, Boolean> seatStatusColumn = new TableColumn<>("Tình trạng ghế");
            seatStatusColumn.setCellValueFactory(new PropertyValueFactory<>("TinhTrangGhe"));

            TableColumn<Couchette, Integer> coachIdColumn = new TableColumn<>("Xe");
            coachIdColumn.setCellValueFactory(new PropertyValueFactory<>("ID_Xe"));

            seatTable.getColumns().addAll(idColumn, seatStatusColumn, coachIdColumn);

            List<Couchette> seats = s.getCouchettesByCoachId(null);
            seatTable.setItems(FXCollections.observableList(seats));

            HBox hbox = new HBox(10);
            hbox.getChildren().addAll(idLabel, idTextField, searchBtn);

            Stage newWindow = new Stage();
            newWindow.setTitle("Thông tin các ghế trên");
            newWindow.setScene(new Scene(new VBox(hbox, seatTable), 250, 300));
            newWindow.show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getSeat();
    }

}
