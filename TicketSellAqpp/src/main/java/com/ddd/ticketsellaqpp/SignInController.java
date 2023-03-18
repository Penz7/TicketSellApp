/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.utils.MessageBox;
import com.ddd.pojo.Staff;
import com.ddd.services.SignInService;
import com.ddd.services.StaffService;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class SignInController implements Initializable{
     private SignInService signInService;
    private final static StaffService STAFF_SERVICE;

    static {
        STAFF_SERVICE = new StaffService();
    }

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSignIn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signInService = new SignInService();
        this.btnSignIn.setOnMouseClicked((t) -> {
            checkAccount();
        });
    }
    
    // Kiểm tra mật khẩu
    @FXML
    private void checkAccount() {
        try {
            if (checkTextInput()) {
                Staff staff = signInService.getAccountMD5(this.txtUsername.getText().trim(), this.txtPassword.getText().trim());
                if (staff == null)
                   MessageBox.getBox("Question", "Tài khoản mật khẩu không đúng !!", Alert.AlertType.INFORMATION).show();
                else {
                    App.currentStaff = STAFF_SERVICE.getStaffByUsername(this.txtUsername.getText().trim());
                    if (staff.getStaIsAdmin()) // admin
                        try {
                            App.setRoot("primary");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    else       // nhan vien
                        try {
                            App.setRoot("secondary");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                    MessageBox.getBox("Thông báo", "Đăng nhập thành công", Alert.AlertType.CONFIRMATION).show();
                }
            }
        } catch (SQLException | NoSuchAlgorithmException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Kiểm tra dữ liệu nhập
    private boolean checkTextInput() throws SQLException, NoSuchAlgorithmException {
        if ("".equals(this.txtUsername.getText().trim()) || this.txtUsername.getText().trim().length() < 6) {
            MessageBox.getBox("Question", "Tên tài khoản phải có ít nhất 6 ký tự !!", Alert.AlertType.INFORMATION).show();
            return false;
        } else if ("".equals(this.txtPassword.getText().trim()) || this.txtPassword.getText().trim().length() < 6) {
          MessageBox.getBox("Question", "Mật khẩu phải có ít nhất 6 ký tự !!", Alert.AlertType.INFORMATION).show();
            return false;
        } else {
            return true;
        }
    }

    // Sự kiện bàn phím
    @FXML
    public void setKeyEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            checkAccount();
        }
    }
}
