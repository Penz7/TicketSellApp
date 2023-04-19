/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.pojo.Coach;
import com.ddd.pojo.Station;
import com.ddd.pojo.User;
import com.ddd.repostitories.CoachRepostitory;
import com.ddd.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
public class QuanLyXeController implements Initializable {

    private final static CoachRepostitory COACH_REPOSTITORY;

    static {
        COACH_REPOSTITORY = new CoachRepostitory();
    }
    private static User currentUser;

    @FXML
    private Button btnAddStation;

    @FXML
    TableView<Coach> tbCoach;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.loadTableCoach();
            this.loadCoachData(null);
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyXeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.txtSearch.textProperty().addListener(e -> {
            try {
                this.loadCoachData(this.txtSearch.getText());
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyXeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void loadTableCoach() {
        TableColumn colCoachID = new TableColumn("Thứ tự");
        colCoachID.setCellValueFactory(new PropertyValueFactory("coachId"));
        colCoachID.setPrefWidth(100);

        TableColumn colCoachName = new TableColumn("Tên xe");
        colCoachName.setCellValueFactory(new PropertyValueFactory("coachName"));

        TableColumn colCoachBienso = new TableColumn("Biển số xe");
        colCoachBienso.setCellValueFactory(new PropertyValueFactory("licensePlates"));

        TableColumn<Coach, Void> colDel = new TableColumn<>("Xóa");
        colDel.setCellFactory(r -> {
            return new TableCell<Coach, Void>() {
                private final Button btn = new Button("Xóa");

                {
                    btn.setOnAction(evt -> {
                        Alert a = MessageBox.getBox("Question",
                                "Bạn có chắc chắn xóa xe này?",
                                Alert.AlertType.CONFIRMATION);
                        a.showAndWait().ifPresent(res -> {
                            if (res == ButtonType.OK) {
                                Button b = (Button) evt.getSource();
                                TableCell cell = (TableCell) b.getParent();
                                Coach st = (Coach) cell.getTableRow().getItem();
                                try {
                                    if (COACH_REPOSTITORY.deleteCoach(st.getCoachId())) {
                                        MessageBox.getBox("Question", "Xóa thành công!!!", Alert.AlertType.INFORMATION).show();
                                        loadCoachData(null);
                                    } else {
                                        MessageBox.getBox("Question", "Xóa thất bại!!!", Alert.AlertType.WARNING).show();
                                    }

                                } catch (SQLException ex) {
                                    MessageBox.getBox("Question", ex.getMessage(), Alert.AlertType.WARNING).show();
                                    Logger.getLogger(QuanLyXeController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        });
                    });

                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
        });

        TableColumn<Coach, Void> colEdit = new TableColumn<>("Sửa");
        colEdit.setCellFactory(column -> {
            return new TableCell<Coach, Void>() {
                private final Button btn = new Button("Sửa");

                {
                    btn.setOnAction(event -> {
                        StackPane secondaryLayout = new StackPane();
                        Label lbXe = new Label("Tên xe:");
                        TextField txtXe = new TextField();
                        Label lbBienso = new Label("Biển số xe:");
                        TextField txtBienso = new TextField();

                        // Tạo button Xác nhận
                        Button confirmButton = new Button("Xác nhận");
                        Scene secondScene = new Scene(secondaryLayout, 250, 200);
                        secondaryLayout.getChildren().addAll(lbXe, txtXe, lbBienso, txtBienso, confirmButton);
                        HBox hbox = new HBox(10);
                        hbox.getChildren().addAll(lbXe, txtXe);
                        HBox hbox1 = new HBox(10);
                        hbox.getChildren().addAll(lbBienso, txtBienso);
                        VBox vbox = new VBox(10);
                        vbox.getChildren().addAll(hbox, hbox1, confirmButton);
                        secondaryLayout.getChildren().addAll(vbox);

                        // Một cửa sổ mới (Stage)
                        Stage newWindow = new Stage();
                        newWindow.setTitle("Sửa thông tin bến xe");
                        newWindow.setScene(secondScene);
                        newWindow.show();

                        confirmButton.setOnAction(e -> {
                            if (txtXe.getText().isEmpty() || txtXe.getText() == null || txtXe.getText().trim().equals("")
                                    || txtBienso.getText().isEmpty() || txtBienso.getText() == null || txtBienso.getText().trim().equals("")) {
                                MessageBox.getBox("Thông báo", "Chưa nhập đủ thông tin!", Alert.AlertType.WARNING).show();
                            } else {
                                Button b = (Button) event.getSource();
                                TableCell cell = (TableCell) b.getParent();
                                Coach st = (Coach) cell.getTableRow().getItem();
                                try {
                                    if (COACH_REPOSTITORY.updateCoachById(txtXe.getText(), txtBienso.getText(), st.getCoachId())) {
                                        MessageBox.getBox("Question", "Sửa thông tin thành công!!!", Alert.AlertType.INFORMATION).show();
                                        loadCoachData(null);

                                    } else {
                                        MessageBox.getBox("Question", "Sửa thất bại!!!", Alert.AlertType.WARNING).show();
                                    }

                                } catch (SQLException ex) {
                                    MessageBox.getBox("Question", ex.getMessage(), Alert.AlertType.WARNING).show();
                                    Logger.getLogger(QuanLyXeController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });

                    });

                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }

            };
        });
        this.tbCoach.getColumns().addAll(colCoachID, colCoachName, colCoachBienso, colEdit, colDel);
    }

    private void loadCoachData(String kw) throws SQLException {

        List<Coach> ques = COACH_REPOSTITORY.getCoach(kw);
        this.tbCoach.getItems().clear();
        this.tbCoach.setItems(FXCollections.observableList(ques));
    }

    @FXML
    private void addStation() {
        this.btnAddStation.setOnAction(event -> {
            StackPane secondaryLayout = new StackPane();
            Label lbXe = new Label("Tên xe:");
            TextField txtXe = new TextField();
            Label lbBienso = new Label("Biển số xe:");
            TextField txtBienso = new TextField();

            // Tạo button Xác nhận
            Button confirmButton = new Button("Xác nhận");
            Scene secondScene = new Scene(secondaryLayout, 250, 200);
            secondaryLayout.getChildren().addAll(lbXe, txtXe, lbBienso, txtBienso, confirmButton);
            HBox hbox = new HBox(10);
            hbox.getChildren().addAll(lbXe, txtXe);
            HBox hbox1 = new HBox(10);
            hbox.getChildren().addAll(lbBienso, txtBienso);
            VBox vbox = new VBox(10);
            vbox.getChildren().addAll(hbox, hbox1, confirmButton);
            secondaryLayout.getChildren().addAll(vbox);

            // Một cửa sổ mới (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Thêm xe");
            newWindow.setScene(secondScene);
            newWindow.show();

            confirmButton.setOnAction(e -> {
                if (txtXe.getText().isEmpty() || txtXe.getText() == null || txtXe.getText().trim().equals("")
                        || txtBienso.getText().isEmpty() || txtBienso.getText() == null || txtBienso.getText().trim().equals("")) {
                    MessageBox.getBox("Thông báo", "Chưa nhập đủ thông tin!", Alert.AlertType.WARNING).show();
                } else {
                    try {
                        if (COACH_REPOSTITORY.addCoach(txtXe.getText(), txtBienso.getText())) {
                            MessageBox.getBox("Question", "Thêm xe thành công!!!", Alert.AlertType.INFORMATION).show();
                            this.loadCoachData(null);

                        } else {
                            MessageBox.getBox("Question", "Thêm thất bại!!!", Alert.AlertType.WARNING).show();
                        }

                    } catch (SQLException ex) {
                        MessageBox.getBox("Question", ex.getMessage(), Alert.AlertType.WARNING).show();
                        Logger.getLogger(QuanLyXeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        });
    }

    @FXML
    private void backMenu() {
        try {
            App.setRoot("home-admin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        QuanLyXeController.currentUser = currentUser;
    }

}
