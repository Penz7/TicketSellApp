/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.pojo.Route;
import com.ddd.pojo.RouteCoachCouchette;
import com.ddd.pojo.Station;
import com.ddd.pojo.User;
import com.ddd.repostitories.SeatReposititory;
import com.ddd.services.RouteCoachCouchetteService;
import com.ddd.services.RouteService;
import com.ddd.services.StationService;
import com.ddd.utils.MessageBox;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class BookingController implements Initializable {

    static SeatReposititory seat = new SeatReposititory();

    private static User currentUser;
    private final static RouteCoachCouchetteService ROUTE_COACH_COUCHETTE_SERVICE;
    private final static RouteService ROUTE_SERVICE;
    private final static StationService STATION_SERVICE;

    static {
        ROUTE_COACH_COUCHETTE_SERVICE = new RouteCoachCouchetteService();
        ROUTE_SERVICE = new RouteService();
        STATION_SERVICE = new StationService();
//      //DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtUserphone;

    @FXML
    private TextField txtUseraddress;

    @FXML
    private TextField txtOrderCount;

    @FXML
    private ComboBox cbTicketOrdered;

    @FXML
    private Button btnOrder;

    @FXML
    private void backMenu() {
        try {
            App.setRoot("home-customer");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private TextField txtSearchDestination;
    @FXML
    private TextField txtSearchDeparture;
    @FXML
    TableView<RouteCoachCouchette> tvRoute;
    @FXML
    DatePicker dpDateOrder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setInfoAcount();
        this.loadTableColumns();
        //        try {
        ////            this.loadTableStation();
        ////            this.loadStationData(null);
        ////        } catch (SQLException ex) {
        ////            Logger.getLogger(QuanLyBenXeController.class.getName()).log(Level.SEVERE, null, ex);
        //        }

        TextFields.bindAutoCompletion(txtSearchDestination, getAllNameStation());
        TextFields.bindAutoCompletion(txtSearchDeparture, getAllNameStation());
    }

    private List<String> getAllNameStation() {
        List<String> name = new ArrayList<>();
        List<Station> listStation = new ArrayList<>();
        StationService sr = new StationService();
        try {
            listStation = sr.getAllStation();
        } catch (SQLException ex) {
            Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO
        listStation.forEach(ls -> {
            name.add(ls.getStationName());
        });

        return name;
    }

    @FXML
    public void findRoute() throws SQLException {
        List<Route> listRoute = new ArrayList<>();
        listRoute = ROUTE_SERVICE.getRouteByDesIdByDepId(
                STATION_SERVICE.getStationByName(txtSearchDestination.getText()).getStationId().toString(),
                STATION_SERVICE.getStationByName(txtSearchDeparture.getText()).getStationId().toString(),
                java.sql.Date.valueOf(dpDateOrder.getValue()));
        if (listRoute.isEmpty()) {
            MessageBox.getBox("Warning", "không có chuyến xe nào", Alert.AlertType.ERROR).show();
        } else {
            for (Route r : listRoute) {
                // only changes num, not the array element
                loadRouteData(r.getRouteId());

            }
        }

    }

    private void loadTableColumns() {

        TableColumn<RouteCoachCouchette, Integer> col0 = new TableColumn("Mã chuyến xe");

        col0.setCellValueFactory(
                new PropertyValueFactory("routeID"));
        col0.setPrefWidth(
                100);

        TableColumn<RouteCoachCouchette, String> col1 = new TableColumn("Tên chuyến xe");

        col1.setCellValueFactory(
                new PropertyValueFactory("routeName"));
        col1.setPrefWidth(
                150);

        TableColumn<RouteCoachCouchette, String> col2 = new TableColumn("Tên xe");

        col2.setCellValueFactory(
                new PropertyValueFactory("coachName"));
        col2.setPrefWidth(
                150);

        TableColumn<RouteCoachCouchette, Date> col3 = new TableColumn("Giờ khởi hành");

        col3.setCellValueFactory(
                new PropertyValueFactory("departureTime"));
        col3.setPrefWidth(
                150);

        TableColumn<RouteCoachCouchette, Integer> col4 = new TableColumn("Ghế");

        col4.setCellValueFactory(
                new PropertyValueFactory("couchetteID"));
        col4.setPrefWidth(
                70);

        TableColumn<RouteCoachCouchette, BigDecimal> col5 = new TableColumn("Giá vé");

        col5.setCellValueFactory(
                new PropertyValueFactory("fare"));
        col5.setPrefWidth(
                100);

        TableColumn<RouteCoachCouchette, Void> colOrderTicket = new TableColumn<>("Đặt");

        colOrderTicket.setCellFactory(r
                -> {
            return new TableCell<RouteCoachCouchette, Void>() {
                private final Button btn = new Button("\u0110\u1EB7t");

                {
                    btn.setOnAction(evt -> {

                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        RouteCoachCouchette st = (RouteCoachCouchette) cell.getTableRow().getItem();
                        if ("đặt".equalsIgnoreCase(btn.getText())) {
                            Alert a = MessageBox.getBox("Đặt vé", "Bạn có chắc đặt vé này", Alert.AlertType.CONFIRMATION);
                            a.showAndWait().ifPresent(res -> {
                                if (res == ButtonType.OK) {

                                    cbTicketOrdered.getItems().add(st.getCouchetteID().toString());
                                    int itemCount = cbTicketOrdered.getItems().size();
                                    txtOrderCount.setText(Integer.toString(itemCount));
                                    if (itemCount > 0) {
                                        btnOrder.setDisable(false);
                                    }
                                    btn.setText("Hủy");
                                    MessageBox.getBox("Đặt vé", "Đặt vé xe thành công", Alert.AlertType.INFORMATION).show();
                                }
                            });
                        } else {
                            if ("hủy".equalsIgnoreCase(btn.getText())) {
                                Alert a = MessageBox.getBox("Hủy vé", "Bạn có chắc hủy vé này", Alert.AlertType.CONFIRMATION);
                                a.showAndWait().ifPresent(res -> {
                                    if (res == ButtonType.OK) {

                                        for (Object item : cbTicketOrdered.getItems()) {
                                            if (item.equals(st.getCouchetteID().toString())) {
                                                cbTicketOrdered.getItems().remove(item);
                                                break;
                                            }
                                        }
                                        int itemCount = cbTicketOrdered.getItems().size();
                                        txtOrderCount.setText(Integer.toString(itemCount));
                                        if (itemCount == 0) {
                                            btnOrder.setDisable(true);
                                        }
                                        btn.setText("Đặt");
                                        MessageBox.getBox("Hủy vé", "Hủy vé thành công", Alert.AlertType.INFORMATION).show();
                                    }
                                });
                            }
                        }
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
                    setOnMousePressed(event -> {
                        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                            btn.fire();
                        }
                    });
                }
            };
        }
        );

        this.tvRoute.getColumns()
                .addAll(col0, col1, col2, col3, col4, col5, colOrderTicket);
    }

    private void setInfoAcount() {
        User user = new User();
        user = App.currentUser;
        if (App.currentUser.getUser_id() > 0) {
            this.txtUsername.setText(user.getUser_fullname());
            this.txtUserphone.setText(user.getUser_phone_number());
            this.txtUseraddress.setText(user.getUser_address());
        }
    }

    @FXML
    private void checkOrder() {
        Alert a = MessageBox.getBox("Đặt vé", "Bạn có chắc chắn chưa!", Alert.AlertType.CONFIRMATION);
        a.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                try {
                    for (Object item : cbTicketOrdered.getItems()) {
                        seat.updateStatusSeat(Integer.parseInt((String) item), true);
                    }
                    this.txtOrderCount.setText("");
                    this.cbTicketOrdered.getItems().clear();
                    findRoute();
                } catch (SQLException ex) {
                    Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    private void loadRouteData(Integer routeId) throws SQLException {
        List<RouteCoachCouchette> data = ROUTE_COACH_COUCHETTE_SERVICE.getDataForTableViewBooking(routeId);
        this.tvRoute.setItems(FXCollections.observableList(data));
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        BookingController.currentUser = currentUser;
    }

}
