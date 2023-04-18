/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.pojo.Couchette;
import com.ddd.pojo.User;
import com.ddd.repostitories.CouchetteRepostitory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
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
public class QuyDinhController {

    static CouchetteRepostitory s = new CouchetteRepostitory();

    private static User currentUser;

    @FXML
    private void checkSeat() {
        StackPane secondaryLayout = new StackPane();

        Label lbSeat = new Label("Giới hạn số lượng ghế:");
        TextField txtSeat = new TextField();

        // Tạo button Xác nhận
        Button confirmButton = new Button("Xác nhận");

        HBox hbox = new HBox(10, lbSeat, txtSeat);

        VBox vbox = new VBox(10, hbox, confirmButton);
        secondaryLayout.getChildren().addAll(vbox);

        // Một cửa sổ mới (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Sửa quy định");
        newWindow.setScene(new Scene(secondaryLayout, 500, 200));
        newWindow.show();

        confirmButton.setOnAction(e -> {

        });
    }

    @FXML
    private void getSeat() {
        Label idLabel = new Label("Nhập id của xe cần tìm:");
        TextField idTextField = new TextField();
        Label idLabel2 = new Label("Nhập id của ghế cần tìm:");
        TextField idTextField2 = new TextField();
        Button searchBtn = new Button("Tìm kiếm");

        TableView<Couchette> seatTable = new TableView<>();
        TableColumn<Couchette, Integer> idColumn = new TableColumn<>("Số ghế");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("CouchetteId"));

        TableColumn<Couchette, Boolean> seatStatusColumn = new TableColumn<>("Tình trạng ghế");
        seatStatusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));

        TableColumn<Couchette, Integer> coachIdColumn = new TableColumn<>("Xe");
        coachIdColumn.setCellValueFactory(new PropertyValueFactory<>("CouchId"));

        TableColumn<Couchette, Integer> orderedColumn = new TableColumn<>("Thứ tự");
        orderedColumn.setCellValueFactory(new PropertyValueFactory<>("OrderOfCouchette"));

        seatTable.getColumns().addAll(orderedColumn, idColumn, seatStatusColumn, coachIdColumn);

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(idLabel, idTextField, idLabel2, idTextField2, searchBtn);

        idTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                List<Couchette> seats = s.getCouchettesByCoachId(Integer.parseInt(newValue));
                seatTable.setItems(FXCollections.observableList(seats));
            } catch (SQLException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error getting seats by vehicle ID", ex);
            }
        });

        searchBtn.setOnAction(event -> {
            try {
                List<Couchette> seats = s.getCouchettesByCoachId(Integer.parseInt(idTextField.getText()));
                seatTable.setItems(FXCollections.observableList(seats));
            } catch (SQLException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error getting seats by vehicle ID", ex);
            }
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(hbox, seatTable);

        Scene scene = new Scene(vbox, 500, 600);
        Stage newWindow = new Stage();
        newWindow.setTitle("Thông tin các ghế trên");
        newWindow.setScene(scene);
        newWindow.show();
    }

    @FXML
    private void backMenu() {
        try {
            App.setRoot("home-admin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        QuyDinhController.currentUser = currentUser;
    }

}
