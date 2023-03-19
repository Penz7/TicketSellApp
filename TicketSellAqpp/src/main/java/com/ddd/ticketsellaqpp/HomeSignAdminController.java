package com.ddd.ticketsellaqpp;

import com.ddd.pojo.Staff;
import com.ddd.utils.MessageBox;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class HomeSignAdminController {

    @FXML
    private Button btnStaffManagement;

    @FXML
    private Button btnBookManagement;
   
    private static Scene adminScene;
    private static Staff currentStaff;
    private static String title;

    @FXML
     private void setStaffManagement() {
        try {
            App.setRoot("booking");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // đăng xuất
    @FXML
    private void setSignOut() {
        Alert alert = MessageBox.getBox("Đăng xuất", "Bạn có muốn đăng xuất không?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                App.currentStaff = null;
                App.setRoot("sign-in");
            } catch (IOException e) {
                throw new RuntimeException("Failed to set root to sign-in page.", e);
            }
        }
    }

}
