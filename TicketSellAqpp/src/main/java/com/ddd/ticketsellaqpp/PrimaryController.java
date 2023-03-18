package com.ddd.ticketsellaqpp;

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText("Bạn có muốn đăng xuất không ?");
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
