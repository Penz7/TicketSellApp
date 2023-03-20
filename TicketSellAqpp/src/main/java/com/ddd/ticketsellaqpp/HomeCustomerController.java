/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.pojo.User;
import com.ddd.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 *
 * @author admin
 */
public class HomeCustomerController implements Initializable {

    @FXML
    private Label lbUserfullname;

    @FXML
    private Label lbUserphonenumber;

    @FXML
    private Label lblJoinedDate;

    @FXML
    private Label lbUsername;

    @FXML
    private Button btnBookManagement;

    private static User currentUser;

    @FXML
    private void setBookingStage() {
        try {
            App.setRoot("booking");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInfoAcount();
    }

    private void setInfoAcount() {
        User user = new User();
        user = App.currentUser;
        if (App.currentUser.getUser_id() > 0) {
            this.lbUserfullname.setText(":   " + user.getUser_fullname());
            this.lbUserphonenumber.setText(":   " + user.getUser_phone_number());
            this.lblJoinedDate.setText(":   " + user.getUser_date_join());
            this.lbUsername.setText(":   " + user.getUsername());
        }
    }

    @FXML
    private void backMenu() {
        try {
            App.setRoot("home-customer");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void setSignOut() {
        Alert alert = MessageBox.getBox("Đăng xuất", "Bạn có muốn đăng xuất không?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                App.currentUser = null;
                App.setRoot("sign-in");
            } catch (IOException e) {
                throw new RuntimeException("Failed to set root to sign-in page.", e);
            }
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        HomeCustomerController.currentUser = currentUser;
    }

}
