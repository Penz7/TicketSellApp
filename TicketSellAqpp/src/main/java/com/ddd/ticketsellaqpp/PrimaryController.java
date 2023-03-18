package com.ddd.ticketsellaqpp;

import com.ddd.utils.MessageBox;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
          // đăng xuất
    private void setSignOut() {
         Alert alert = MessageBox.getBox("Đăng xuất", "Bạn có muốn đăng xuất không ?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().equals(ButtonType.OK)) {
            try {
                App.currentStaff = null;
                App.setRoot("sign-in");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
