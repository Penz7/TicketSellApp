/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.pojo.RouteCoach;
import com.ddd.pojo.User;
import com.ddd.repostitories.RouteCoachRepostitory;
import com.ddd.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
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
import javafx.scene.control.ComboBox;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
public class QuanLyChuyenXeXeController implements Initializable {

    static RouteCoachRepostitory s = new RouteCoachRepostitory();

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    
    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private static User currentUser;

    @FXML
    private TextField txtSearch;
    @FXML
    TableView<RouteCoach> tbRouteCoach;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.loadTableRoute();
            this.loadRouteData(null);
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyChuyenXeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.txtSearch.textProperty().addListener(e -> {
            try {
                this.loadRouteData(this.txtSearch.getText());
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyChuyenXeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void loadTableRoute() {
        TableColumn colRouteid = new TableColumn("ID Chuyến Xe");
        colRouteid.setCellValueFactory(new PropertyValueFactory("routeId"));

        TableColumn colCoachid = new TableColumn("ID Xe");
        colCoachid.setCellValueFactory(new PropertyValueFactory("coachId"));

        TableColumn colTime = new TableColumn("Thời gian khởi hành");
        colTime.setCellValueFactory(new PropertyValueFactory("departureTime"));

        TableColumn<RouteCoach, Void> colDel = new TableColumn<>("Xóa");
        colDel.setCellFactory(r -> {
            return new TableCell<RouteCoach, Void>() {
                private final Button btn = new Button("Xóa");

                {
                    btn.setOnAction(evt -> {
                        Alert a = MessageBox.getBox("Question",
                                "Bạn muốn xóa chuyến xe này?",
                                Alert.AlertType.CONFIRMATION);
                        a.showAndWait().ifPresent(res -> {
                            if (res == ButtonType.OK) {
                                Button b = (Button) evt.getSource();
                                TableCell cell = (TableCell) b.getParent();
                                RouteCoach st = (RouteCoach) cell.getTableRow().getItem();
                                try {
                                    if (s.deleteRouteCoach(st.getRouteId(), st.getCoachId(), st.getDepartureTime())) {
                                        MessageBox.getBox("Question", "Xóa thành công!!!", Alert.AlertType.INFORMATION).show();
                                        loadRouteData(null);
                                    } else {
                                        MessageBox.getBox("Question", "Xóa thất bại!!!", Alert.AlertType.WARNING).show();
                                    }

                                } catch (SQLException ex) {
                                    MessageBox.getBox("Warning", "Chuyến xe hiện đang sắp khởi hành hoặc đang trong danh sách chạy!!!", Alert.AlertType.WARNING).show();
                                    Logger.getLogger(QuanLyBenXeController.class.getName()).log(Level.SEVERE, null, ex);
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

        TableColumn<RouteCoach, Void> colEdit = new TableColumn<>("Sửa");
        colEdit.setCellFactory(column -> {
            return new TableCell<RouteCoach, Void>() {
                private final Button btn = new Button("Sửa");

                {
                    btn.setOnAction(event -> {
                        Button b = (Button) event.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        RouteCoach st = (RouteCoach) cell.getTableRow().getItem();
                        StackPane secondaryLayout = new StackPane();
                        Label lbXe = new Label("ID Xe:");
                        ComboBox<Integer> coach = new ComboBox<>();
                        coach.setValue(st.getCoachId());
                        
                        Label lbThoigian = new Label("Thời gian khởi hành:");
                        TextField txtThoigian = new TextField();
                        txtThoigian.setText(st.getDepartureTime().toString());
                        Label lb2 = new Label("Thời gian có dạng là: YYYY-MM-DD 20:20:00");
                        
                        Button confirmButton = new Button("Xác nhận");
                        Scene secondScene = new Scene(secondaryLayout, 600, 200);
                        secondaryLayout.getChildren().addAll( lbXe, coach, lbThoigian, txtThoigian, confirmButton);
                        HBox hbox = new HBox(10);
                        hbox.getChildren().addAll( lbXe, coach, lbThoigian, txtThoigian, lb2);
                        VBox vbox = new VBox(10);
                        vbox.getChildren().addAll(hbox, confirmButton);
                        secondaryLayout.getChildren().addAll(vbox);

                        // Một cửa sổ mới (Stage)
                        Stage newWindow = new Stage();
                        newWindow.setTitle("Sửa thời gian khởi hành");
                        newWindow.setScene(secondScene);
                        newWindow.show();

                        try {
                            coach.getItems().addAll(s.getIdCoach());

                        } catch (SQLException ex) {
                            Logger.getLogger(QuanLyChuyenXeXeController.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                        confirmButton.setOnAction(e -> {
                            Integer id_xe = coach.getValue();
                            if (!txtThoigian.getText().isEmpty()) {
                                try {
                                    dateFormat.parse(txtThoigian.getText());
                                    if (s.updateRouteCoachbyID(Timestamp.valueOf(txtThoigian.getText()), st.getRouteId(),id_xe)) {
                                        MessageBox.getBox("Thông báo", "Sửa chuyến xe xe thành công!!!", Alert.AlertType.INFORMATION).show();
                                        loadRouteData(null);
                                    } else {
                                        MessageBox.getBox("Question", "Sửa thất bại!!!", Alert.AlertType.ERROR).show();
                                    }
                                } catch (ParseException ex) {
                                    MessageBox.getBox("Warning", "Thời gian không đúng định dạng (dd-MM-yyyy HH:mm:ss)!!!", Alert.AlertType.ERROR).show();
                                } catch (SQLException ex) {
                                    MessageBox.getBox("Question", ex.getMessage(), Alert.AlertType.WARNING).show();
                                    Logger.getLogger(QuanLyChuyenXeXeController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                MessageBox.getBox("Warning", "Chưa nhập thời gian cần sửa!!!", Alert.AlertType.ERROR).show();
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

        this.tbRouteCoach.getColumns().addAll(colRouteid, colCoachid, colTime, colDel, colEdit);
    }

    @FXML
    private void addRouteCoach() {
        // Create UI elements
        Label lbChuyenXe = new Label("ID Chuyến xe:");
        ComboBox<Integer> route = new ComboBox<>();
        Label lbXe = new Label("ID Xe:");
        ComboBox<Integer> coach = new ComboBox<>();
        Label lbThoigian = new Label("Thời gian khởi hành:");
        TextField txtThoigian = new TextField();
        Label lb2 = new Label("Thời gian có dạng là: YYYY-MM-DD 20:20:00");
        Button confirmButton = new Button("Xác nhận");

        // Configure UI elements
        try {
            route.getItems().addAll(s.getIdRoute());
            coach.getItems().addAll(s.getIdCoach());

        } catch (SQLException ex) {
            Logger.getLogger(QuanLyChuyenXeXeController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        // Configure layout
        HBox hbox = new HBox(10, lbChuyenXe, route, lbXe, coach, lbThoigian, txtThoigian);
        VBox vbox = new VBox(10, hbox, lb2, confirmButton);
        StackPane secondaryLayout = new StackPane(vbox);
        Scene secondScene = new Scene(secondaryLayout, 600, 200);

        // Create new window
        Stage newWindow = new Stage();
        newWindow.setTitle("Thêm chuyến xe");
        newWindow.setScene(secondScene);
        newWindow.show();

        // Handle button click
        confirmButton.setOnAction(e -> {
            Integer id_chuyenxe = route.getValue();
            Integer id_xe = coach.getValue();
            String thoigian = txtThoigian.getText();
            if (id_chuyenxe != null && id_xe != null && !thoigian.isEmpty()) {
                try {
                    dateFormat.parse(thoigian);
                    if (s.addRouteCoach(id_chuyenxe, id_xe, thoigian)) {
                        MessageBox.getBox("Thông báo", "Thêm chuyến xe xe thành công!!!", Alert.AlertType.INFORMATION).show();
                        loadRouteData(null);
                    } else {
                        MessageBox.getBox("Question", "Thêm thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                } catch (ParseException ex) {
                    MessageBox.getBox("Warning", "Thời gian không đúng định dạng (dd-MM-yyyy HH:mm:ss)!!!", Alert.AlertType.ERROR).show();
                } catch (SQLException ex) {
                    MessageBox.getBox("Question", ex.getMessage(), Alert.AlertType.WARNING).show();
                    Logger
                            .getLogger(QuanLyChuyenXeXeController.class
                                    .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                MessageBox.getBox("Warning", "Chưa chọn hết các thông tin!!!", Alert.AlertType.ERROR).show();
            }
        });
    }

    private void loadRouteData(String kw) throws SQLException {
        List<RouteCoach> ques = s.getRouteCoach(kw);
        this.tbRouteCoach.getItems().clear();
        this.tbRouteCoach.setItems(FXCollections.observableList(ques));
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
        QuanLyChuyenXeXeController.currentUser = currentUser;
    }
}
