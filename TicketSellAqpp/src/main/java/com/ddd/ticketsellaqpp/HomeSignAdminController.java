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


public class HomeSignAdminController implements Initializable{

    private static User currentUser;
    
     @FXML
    private Label lbUserfullname;

    @FXML
    private Label lbUserphonenumber;

    @FXML
    private Label lblJoinedDate;

    @FXML
    private Label lbUsername;
 
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      setInfoAcount();
    }
    
    @FXML
    private void setChuyenxeStage() {
        try {
            App.setRoot("QuanLyChuyenXe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void setBenxeStage() {
        try {
            App.setRoot("QuanLyBenXe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     @FXML
    private void setQuyDinh() {
        try {
            App.setRoot("QuyDinh");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     @FXML
    private void setStaffStage() {
        try {
            App.setRoot("QuanLyNhanVien");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
