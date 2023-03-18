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
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class SignInController implements Initializable {

    private SignInService signInService;
    private final static StaffService staff = new StaffService();

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSignIn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signInService = new SignInService();
        this.btnSignIn.setOnMouseClicked(event -> {
            try {
                MessageBox.getBox("dat", "dumamay", Alert.AlertType.ERROR);
                checkAccount();
            } catch (IOException ex) {
                Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
            }
   
});
    }

    @FXML
    private void checkAccount() throws IOException {
        try {
            if (checkTextInput()) {
//                Staff staff = signInService.getAccountMD5(this.txtUsername.getText().trim(), this.txtPassword.getText().trim());

//                if (staff == null) {
//                    MessageBox.getBox("Warning", "Tài khoản mật khẩu không đúng", Alert.AlertType.ERROR).show();
//                } else {
                    App.currentStaff = staff.getStaffByUsername(this.txtUsername.getText().trim());
                    if (true) {
                        handleAdmin();
                    } else {
                        handleEmployee();
                    }
//                }
            }
        } catch (SQLException | NoSuchAlgorithmException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleAdmin() throws IOException {
        try {
            App.setRoot("primary");
        } catch (IOException e) {
            MessageBox.getBox("Warning", "Hiện chưa có giao diện admin", Alert.AlertType.ERROR).show();
        }
    }

    private void handleEmployee() throws IOException {
        try {
            App.setRoot("secondary");
        } catch (IOException e) {
            MessageBox.getBox("Warning", "Hiện chưa có giao diện nhân viên", Alert.AlertType.ERROR).show();
        }
    }

    // Kiểm tra dữ liệu nhập
    private boolean checkTextInput() throws SQLException, NoSuchAlgorithmException {
        if ("".equals(this.txtUsername.getText().trim()) || this.txtUsername.getText().trim().length() < 6) {
            MessageBox.getBox("Warning", "Tên tài khoản phải có ít nhất 6 kí tự !!", Alert.AlertType.ERROR).show();
            return false;
        } else if ("".equals(this.txtPassword.getText().trim()) || this.txtPassword.getText().trim().length() < 6) {
            MessageBox.getBox("Đăng nhập", "Mật khẩu phải có ít nhất 6 kí tự !!", Alert.AlertType.ERROR).show();
            return false;
        } else {
            return true;
        }
    }

    // Sự kiện bàn phím
    @FXML
    public void setKeyEnter(KeyEvent event) throws SQLException {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                checkAccount();
            } catch (IOException ex) {
                Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
