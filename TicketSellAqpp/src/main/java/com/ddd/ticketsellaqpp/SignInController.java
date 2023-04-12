/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.utils.MessageBox;
import com.ddd.pojo.User;
import com.ddd.services.RoleService;
import com.ddd.services.SignInService;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class SignInController implements Initializable {

    private SignInService signInService;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSignIn;

    @FXML
    private Button btnRegiste;
    
    @FXML
    AnchorPane anc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signInService = new SignInService();
        this.btnSignIn.setOnMouseClicked((t) -> {
            checkAccount();
        });
        this.btnRegiste.setOnMouseClicked(t -> {
            registeAccount();
        });
        anc.setStyle("-fx-background-color: #E0FFFF;");
    }

    private void registeAccount() {
        try {
            App.setRoot("registe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void checkAccount() {
        try {
            if (!checkTextInput()) {
                return;
            }
            User user = signInService.getAccountMD5(txtUsername.getText().trim(), txtPassword.getText().trim());
            RoleService roleService = new RoleService();

            if (user == null) {
                MessageBox.getBox("Question", "Tài khoản mật khẩu không đúng !!", Alert.AlertType.INFORMATION).show();
            } else {
                App.currentUser = user;
                String roleName = roleService.getRoleById(user.getRole_id()).getName_role();
                if (roleName.trim().compareToIgnoreCase("admin") == 0) {
                    // admin
                    try {
                        App.setRoot("home-admin");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (roleName.trim().compareToIgnoreCase("staff") == 0) {
                    // nhan vien
                    try {
                        App.setRoot("home-staff");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        App.setRoot("home-customer");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        } else if ("".equals(this.txtPassword.getText().trim()) || this.txtPassword.getText().trim().length() < 6 ) {
            MessageBox.getBox("Question", "Mật khẩu phải có ít nhất 6 ký tự và không có khoảng trắng !!", Alert.AlertType.INFORMATION).show();
            return false;
        } else if(this.txtPassword.getText().contains(" ")){
             MessageBox.getBox("Question", "Mật khẩu của bạn đang có khoảng trắng", Alert.AlertType.INFORMATION).show();
             return false;
        }
        else {
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
