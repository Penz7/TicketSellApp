package com.ddd.ticketsellaqpp;

import com.ddd.pojo.User;
import com.ddd.utils.MessageBox;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;


public class HomeSignAdminController {

    static User currentUser;
  

    

    // đăng xuất
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
        HomeSignAdminController.currentUser = currentUser;
    }

   
    
    

}
