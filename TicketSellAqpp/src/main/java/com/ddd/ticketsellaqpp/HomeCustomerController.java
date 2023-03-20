/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.pojo.User;
import com.ddd.utils.MessageBox;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

/**
 *
 * @author admin
 */
public class HomeCustomerController {
    
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
     
    @FXML
    private void backMenu() {
        try {
            App.setRoot("home-admin");
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
