package com.ddd.ticketsellaqpp;

import com.ddd.pojo.Staff;
import com.ddd.utils.MessageBox;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class HomeSignAdmin {

   @FXML
    private Button btnStaffManagement;

    @FXML
    private Button btnBookManagement;
    
    private static Staff currentStaff; 
    private static String title;

  
    private void changeScene(Button btn, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btn.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(title);
        primaryStage.show();
    }

    @FXML
    void staffManagement(ActionEvent event) throws IOException {
        title = "Quản lý tác giả";
        changeScene(btnStaffManagement, "booking.fxml");
    }

    @FXML
    void bookManagement(ActionEvent event) throws IOException {
        title = "Quản lý sách";
        changeScene(btnBookManagement, "FXMLBook.fxml");
    }
    // đăng xuất
    @FXML
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
