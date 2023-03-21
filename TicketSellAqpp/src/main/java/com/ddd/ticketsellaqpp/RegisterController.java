/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.services.RegisterService;
import com.ddd.utils.MessageBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author admin
 */
public class RegisterController implements Initializable {

    RegisterService registerService = new RegisterService();

    @FXML
    private Button btnRegis;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserfullname;

    @FXML
    private TextField txtUserphonenumber;

    @FXML
    private TextField txtUseraddress;

    @FXML
    private TextField txtUseridcard;

    @FXML
    private TextField txtPassword2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.btnRegis.setOnMouseClicked(t -> {
            handleRegister();
        });
    }

    public void clearFields() {
        this.txtUserfullname.setText("");
        this.txtPassword.setText("");
        this.txtPassword2.setText("");
        this.txtUseraddress.setText("");
        this.txtUseridcard.setText("");
        this.txtUserphonenumber.setText("");
        this.txtUsername.setText("");
    }

    @FXML
    private void handleRegister() {
        String fullName = txtUserfullname.getText();
        String idCard = txtUseridcard.getText();
        String phoneNumber = txtUserphonenumber.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String address = txtUseraddress.getText();

        boolean isUsernameTaken = registerService.checkAccountUsername(username);
        if (isUsernameTaken) {
            boolean isSuccess = registerService.addAccount(fullName, idCard, phoneNumber, username, password, address);
            if (isSuccess) {
                MessageBox.getBox("Đăng ký tài khoản", "Đăng ký thành công !!", Alert.AlertType.CONFIRMATION).show();
                clearFields(); // Xóa dữ liệu trên form đăng ký
            } else {
                MessageBox.getBox("Đăng ký tài khoản", "Đăng ký thất bại !!", Alert.AlertType.ERROR).show();
            }
        } else {
            MessageBox.getBox("Đăng ký tài khoản", "Tên tài khoản đã bị trùng!!", Alert.AlertType.ERROR).show();
        }

    }
}
