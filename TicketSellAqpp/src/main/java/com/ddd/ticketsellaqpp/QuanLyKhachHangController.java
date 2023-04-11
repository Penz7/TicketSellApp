/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;
import com.ddd.pojo.User;
import com.ddd.repostitories.UserRepostitory;
import com.ddd.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class QuanLyKhachHangController implements Initializable  {
     static UserRepostitory s = new UserRepostitory();

    private static User currentUser;

    @FXML
    private TextField txtSearch;
    @FXML
    TableView<User> tbUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.loadTableCustomer();
            this.loadCustomerData(null);
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.txtSearch.textProperty().addListener(e -> {
            try {
                this.loadCustomerData(this.txtSearch.getText());
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void loadTableCustomer() {
        TableColumn colID = new TableColumn("Thứ tự");
        colID.setCellValueFactory(new PropertyValueFactory("user_id"));
        colID.setPrefWidth(70);

        TableColumn colName = new TableColumn("Họ và tên");
        colName.setCellValueFactory(new PropertyValueFactory("user_fullname"));

        TableColumn colCCCD = new TableColumn("CCCD");
        colCCCD.setCellValueFactory(new PropertyValueFactory("user_id_card"));

        TableColumn colPhone = new TableColumn("SĐT");
        colPhone.setCellValueFactory(new PropertyValueFactory("user_phone_number"));

        TableColumn colBirth = new TableColumn("Ngày sinh");
        colBirth.setCellValueFactory(new PropertyValueFactory("user_date_of_birth"));

        TableColumn colJoin = new TableColumn("Ngày gia nhập");
        colJoin.setCellValueFactory(new PropertyValueFactory("user_date_join"));

        TableColumn colUsername = new TableColumn("username");
        colUsername.setCellValueFactory(new PropertyValueFactory("username"));

        TableColumn colPass = new TableColumn("password");
        colPass.setCellValueFactory(new PropertyValueFactory("password"));

        TableColumn colAdress = new TableColumn("Địa chỉ");
        colAdress.setCellValueFactory(new PropertyValueFactory("user_address"));

       
        this.tbUser.getColumns().addAll(colID, colName, colCCCD, colPhone, colBirth, colAdress, colUsername, colPass, colJoin);
    }

    @FXML
    private void addCustomer() {
        StackPane secondaryLayout = new StackPane();
        Label lbFullname = new Label("Tên người dùng:");
        TextField txtFullname = new TextField();

        Label lbusername = new Label("Tài khoản:");
        TextField txtUsername = new TextField();

        Label lbPassword = new Label("Mật khẩu:");
        TextField txtPassword = new TextField();

        Label lbUserIdCard = new Label("CCCD:");
        TextField txtUserIdCard = new TextField();

        Label lbPhone = new Label("Số điện thoại:");
        TextField txtPhone = new TextField();

        Label lbBirth = new Label("Ngày sinh:");
        DatePicker dpBirth = new DatePicker();

        Label lbAdress = new Label("Địa chỉ:");
        TextField txtAdress = new TextField();

        // Tạo button Xác nhận
        Button confirmButton = new Button("Xác nhận");

        HBox hbox = new HBox(10, lbFullname, txtFullname);
        HBox hbox1 = new HBox(10, lbUserIdCard, txtUserIdCard);
        HBox hbox2 = new HBox(10, lbusername, txtUsername);
        HBox hbox3 = new HBox(10, lbPassword, txtPassword);
        HBox hbox4 = new HBox(10, lbPhone, txtPhone);
        HBox hbox5 = new HBox(10, lbBirth, dpBirth);
        HBox hbox6 = new HBox(10, lbAdress, txtAdress);

        VBox vbox = new VBox(10, hbox, hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, confirmButton);
        secondaryLayout.getChildren().addAll(vbox);

        // Một cửa sổ mới (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Sửa thông tin nhân viên");
        newWindow.setScene(new Scene(secondaryLayout, 600, 200));
        newWindow.show();

        confirmButton.setOnAction(e -> {
            try {
                LocalDate birthDate = dpBirth.getValue();
                java.sql.Date sqlBirthDate = java.sql.Date.valueOf(birthDate);
                if (s.addUser(txtFullname.getText(), txtUserIdCard.getText(), txtPhone.getText(), sqlBirthDate, txtUsername.getText(), txtPassword.getText(), txtAdress.getText())) {
                    MessageBox.getBox("Question", "Thêm khách hàng thành công!!!", Alert.AlertType.INFORMATION).show();
                    loadCustomerData(null);
                } else {
                    MessageBox.getBox("Question", "Thêm khách hàng thất bại!!!", Alert.AlertType.WARNING).show();
                }
            } catch (SQLException ex) {
                MessageBox.getBox("Question", ex.getMessage(), Alert.AlertType.WARNING).show();
                Logger.getLogger(QuanLyNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex) {
                MessageBox.getBox("Question", "Cột số chuyến phải là số!!!", Alert.AlertType.WARNING).show();
            }
        });
    }

    private void loadCustomerData(String kw) throws SQLException {

        List<User> ques = s.getCustomer(kw);
        this.tbUser.getItems().clear();
        this.tbUser.setItems(FXCollections.observableList(ques));
    }

    @FXML
    private void backMenu() {
        try {
            App.setRoot("SellTicket");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        QuanLyKhachHangController.currentUser = currentUser;
    }
}
