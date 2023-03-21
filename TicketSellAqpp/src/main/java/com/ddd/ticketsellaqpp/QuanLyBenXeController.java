/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.repostitories.StationRepository;
import com.ddd.pojo.Station;
import com.ddd.pojo.User;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class QuanLyBenXeController implements Initializable {

    static StationRepository s = new StationRepository();

    private static User currentUser;

    @FXML
    private Button btnAddStation;

    @FXML
    TableView<Station> tbStation;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.loadTableStation();
            this.loadStationData(null);
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyBenXeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.txtSearch.textProperty().addListener(e -> {
            try {
                this.loadStationData(this.txtSearch.getText());
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyBenXeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void loadTableStation() {
        TableColumn colStationId = new TableColumn("Thứ tự");
        colStationId.setCellValueFactory(new PropertyValueFactory("stationId"));
        colStationId.setPrefWidth(100);

        TableColumn colStationName = new TableColumn("Tên thành phố");
        colStationName.setCellValueFactory(new PropertyValueFactory("stationName"));

        TableColumn colDel = new TableColumn();
        colDel.setCellFactory(r -> {
            Button btn = new Button("Delete");

            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Question",
                        "Are you sure to delete this question?",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        Station st = (Station) cell.getTableRow().getItem();
                        try {
                            if (s.deleteStation(st.getStationId().toString())) {
                                MessageBox.getBox("Question", "Xóa thành công!!!", Alert.AlertType.INFORMATION).show();
                                this.loadStationData(null);
                            } else {
                                MessageBox.getBox("Question", "Xóa thất bại!!!", Alert.AlertType.WARNING).show();
                            }

                        } catch (SQLException ex) {
                            MessageBox.getBox("Question", ex.getMessage(), Alert.AlertType.WARNING).show();
                            Logger.getLogger(QuanLyBenXeController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });
            });

            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });

        TableColumn colEdit = new TableColumn();
        colEdit.setCellFactory(r -> {
            Button btn = new Button("Sửa");
            btn.setOnAction(evt -> {
                StackPane secondaryLayout = new StackPane();
                Scene secondScene = new Scene(secondaryLayout, 500, 600);

                // Một cửa sổ mới (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Sửa thông tin bến xe");
                newWindow.setScene(secondScene);

                newWindow.show();

            });

            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;

        });

        this.tbStation.getColumns().addAll(colStationId, colStationName, colDel, colEdit);
    }

    private void loadStationData(String kw) throws SQLException {

        List<Station> ques = s.getStations(kw);
        this.tbStation.getItems().clear();
        this.tbStation.setItems(FXCollections.observableList(ques));
    }
    
    @FXML
    private void addStation() {
            StackPane secondaryLayout = new StackPane();
                Scene secondScene = new Scene(secondaryLayout, 500, 600);

                // Một cửa sổ mới (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Thêm bến xe");
                newWindow.setScene(secondScene);

                newWindow.show();
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
        QuanLyBenXeController.currentUser = currentUser;
    }

}
