/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.pojo.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class QuyDinhController implements Initializable {

    private static User currentUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
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
