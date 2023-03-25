/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.pojo.Route;
import com.ddd.pojo.User;
import com.ddd.repostitories.RouteRepostitory;
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
import javafx.scene.control.ComboBox;
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
public class QuanLyChuyenXeController implements Initializable {

    static RouteRepostitory s = new RouteRepostitory();

    private static User currentUser;

    @FXML
    private TextField txtSearch;
    @FXML
    TableView<Route> tbRoute;

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
        TableColumn colRouteId = new TableColumn("Thứ tự");
        colRouteId.setCellValueFactory(new PropertyValueFactory("routeId"));
        colRouteId.setPrefWidth(100);

        TableColumn colRoutename = new TableColumn("Tên thành phố");
        colRoutename.setCellValueFactory(new PropertyValueFactory("nameRoute"));

        TableColumn colBendi = new TableColumn("Id thành phố đi");
        colBendi.setCellValueFactory(new PropertyValueFactory("destinationID"));

        TableColumn colBenden = new TableColumn("ID thành phố đến");
        colBenden.setCellValueFactory(new PropertyValueFactory("departureID"));

        TableColumn colgiaChuyen = new TableColumn("Giá tiền");
        colgiaChuyen.setCellValueFactory(new PropertyValueFactory("fare"));

        TableColumn<Route, Void> colDel = new TableColumn<>("Xóa");
        colDel.setCellFactory(r -> {
            return new TableCell<Route, Void>() {
        private final Button btn = new Button("Xóa");
        {
            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Question",
                        "Are you sure to delete this question?",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        Route st = (Route) cell.getTableRow().getItem();
                        try {
                            if (s.deleteRoute(st.getRouteId())) {
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
            if (empty || getTableRow().getItem() == null) {
                setGraphic(null);
            } else {
                setGraphic(btn);
            }
        }

//            TableCell c = new TableCell();
//            c.setGraphic(btn);
//            return c;
            };
        });

        TableColumn<Route, Void> colEdit = new TableColumn<>("Sửa");
        colEdit.setCellFactory(column -> {
            return new TableCell<Route, Void>() {
        private final Button btn = new Button("Sửa");

        {
            btn.setOnAction(event -> {
                StackPane secondaryLayout = new StackPane();
                Label lbDi = new Label("Tên thành phố đi:");

                Label lbDen = new Label("Tên thành phố đến:");

                ComboBox<String> cbDi = new ComboBox<>();
                ComboBox<String> cbDen = new ComboBox<>();
                try {
                    cbDi.getItems().addAll(s.getNameStation());
                    cbDen.getItems().addAll(s.getNameStation());
                } catch (SQLException ex) {
                    Logger.getLogger(QuanLyChuyenXeController.class.getName()).log(Level.SEVERE, null, ex);
                }

                Label lbGia = new Label("Giá chuyến:");
                TextField txtGia = new TextField();

                // Tạo button Xác nhận
                Button confirmButton = new Button("Xác nhận");
                Scene secondScene = new Scene(secondaryLayout, 600, 200);
                secondaryLayout.getChildren().addAll(lbDi, cbDi, lbDen, cbDen, lbGia, txtGia, confirmButton);
                HBox hbox = new HBox(10);
                hbox.getChildren().addAll(lbDi, cbDi, lbDen, cbDen, lbGia, txtGia);
                VBox vbox = new VBox(10);
                vbox.getChildren().addAll(hbox, confirmButton);
                secondaryLayout.getChildren().addAll(vbox);

                // Một cửa sổ mới (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Sửa thông tin chuyến xe");
                newWindow.setScene(secondScene);
                newWindow.show();

                confirmButton.setOnAction(e -> {
                    String nameDen = cbDen.getValue();
                    String nameDi = cbDi.getValue();
                    Button b = (Button) event.getSource();
                    TableCell cell = (TableCell) b.getParent();
                    Route st = (Route) cell.getTableRow().getItem();
                    if (nameDen != null && nameDi != null && !nameDen.isEmpty() && !nameDi.isEmpty()) {
                        try {
                            if (s.updateRouteById(nameDi + " - " + nameDen, st.getRouteId(), s.getStationIDbyname(nameDi), s.getStationIDbyname(nameDen), Double.parseDouble(txtGia.getText()))) {
                                MessageBox.getBox("Question", "Sửa thông tin thành công!!!", Alert.AlertType.INFORMATION).show();
                                loadRouteData(null);
                            } else {
                                MessageBox.getBox("Question", "Sửa thất bại!!!", Alert.AlertType.WARNING).show();
                            }
                        } catch (SQLException ex) {
                            MessageBox.getBox("Question", ex.getMessage(), Alert.AlertType.WARNING).show();
                            Logger.getLogger(QuanLyBenXeController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NumberFormatException ex) {
                            MessageBox.getBox("Question", "Giá chuyến phải là số!!!", Alert.AlertType.WARNING).show();
                        }
                    } else {
                        MessageBox.getBox("Question", "Vui lòng chọn địa điểm!!!", Alert.AlertType.WARNING).show();
                    }
                });

            });
                }
                 @Override
        public void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || getTableRow().getItem() == null) {
                setGraphic(null);
            } else {
                setGraphic(btn);
            }
        }

//            TableCell c = new TableCell();
//            c.setGraphic(btn);
//            return c;
            };
        });

        this.tbRoute.getColumns().addAll(colRouteId, colRoutename, colBendi, colBenden, colgiaChuyen, colDel, colEdit);
    }

    @FXML
    private void addRoute() {
        StackPane secondaryLayout = new StackPane();
        Label lbDi = new Label("Tên thành phố đi:");

        Label lbDen = new Label("Tên thành phố đến:");

        ComboBox<String> cbDi = new ComboBox<>();
        ComboBox<String> cbDen = new ComboBox<>();
        try {
            cbDi.getItems().addAll(s.getNameStation());
            cbDen.getItems().addAll(s.getNameStation());
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyChuyenXeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Label lbGia = new Label("Giá chuyến:");
        TextField txtGia = new TextField();

        // Tạo button Xác nhận
        Button confirmButton = new Button("Xác nhận");
        Scene secondScene = new Scene(secondaryLayout, 600, 200);
        secondaryLayout.getChildren().addAll(lbDi, cbDi, lbDen, cbDen, lbGia, txtGia, confirmButton);
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(lbDi, cbDi, lbDen, cbDen, lbGia, txtGia);
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(hbox, confirmButton);
        secondaryLayout.getChildren().addAll(vbox);

        // Một cửa sổ mới (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Thêm chuyến xe");
        newWindow.setScene(secondScene);
        newWindow.show();

        confirmButton.setOnAction(e -> {
            String nameDen = cbDen.getValue();
            String nameDi = cbDi.getValue();
            if (nameDen != null && nameDi != null && !nameDen.isEmpty() && !nameDi.isEmpty()) {
                try {
                    if (s.addRoute(nameDi + " - " + nameDen, s.getStationIDbyname(nameDi), s.getStationIDbyname(nameDen), Double.parseDouble(txtGia.getText()))) {
                        MessageBox.getBox("Question", "Thêm chuyến xe thành công!!!", Alert.AlertType.INFORMATION).show();
                        loadRouteData(null);
                    } else {
                        MessageBox.getBox("Question", "Thêm chuyến xe thất bại!!!", Alert.AlertType.WARNING).show();
                    }
                } catch (SQLException ex) {
                    MessageBox.getBox("Question", ex.getMessage(), Alert.AlertType.WARNING).show();
                    Logger.getLogger(QuanLyBenXeController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NumberFormatException ex) {
                    MessageBox.getBox("Question", "Giá chuyến phải là số!!!", Alert.AlertType.WARNING).show();
                }
            } else {
                MessageBox.getBox("Question", "Vui lòng chọn địa điểm!!!", Alert.AlertType.WARNING).show();
            }
        });
    }
                

    private void loadRouteData(String kw) throws SQLException {

        List<Route> ques = s.getRoute(kw);
        this.tbRoute.getItems().clear();
        this.tbRoute.setItems(FXCollections.observableList(ques));
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
        QuanLyChuyenXeController.currentUser = currentUser;
    }
}
