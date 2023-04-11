/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.pojo.Route;
import com.ddd.pojo.RouteCoachCouchette;
import com.ddd.pojo.Station;
import com.ddd.pojo.Ticket;
import com.ddd.pojo.User;
import com.ddd.repostitories.CouchetteRepostitory;
import com.ddd.repostitories.RouteCoachRepostitory;
import com.ddd.services.BookingService;
import com.ddd.services.CouchetteService;
import com.ddd.services.RouteCoachCouchetteService;
import com.ddd.services.RouteCoachService;
import com.ddd.services.RouteService;
import com.ddd.services.StationService;
import com.ddd.services.TicketService;
import com.ddd.services.UserService;
import com.ddd.utils.MessageBox;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import org.controlsfx.control.textfield.TextFields;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class BookingController implements Initializable {

    static CouchetteRepostitory seat = new CouchetteRepostitory();

    private static User currentUser;
    private final static RouteCoachCouchetteService ROUTE_COACH_COUCHETTE_SERVICE;
    private final static RouteService ROUTE_SERVICE;
    private final static StationService STATION_SERVICE;
    private final static BookingService BOOKING_SERVICE;
    private final static TicketService TICKET_SERVICE;
    private final static CouchetteService COUCHETTE_SERVICE;
    private final static UserService USER_SERVICE;
    private final static DateTimeFormatter DTF;
    private final static RouteCoachService ROUTE_COACH_SERVICE;

    static {
        ROUTE_COACH_COUCHETTE_SERVICE = new RouteCoachCouchetteService();
        ROUTE_SERVICE = new RouteService();
        STATION_SERVICE = new StationService();
        BOOKING_SERVICE = new BookingService();
        TICKET_SERVICE = new TicketService();
        COUCHETTE_SERVICE = new CouchetteService();
        USER_SERVICE = new UserService();
        DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ROUTE_COACH_SERVICE = new RouteCoachService();
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
    private Button btnFindRoute;

    @FXML
    private TextArea txtArea;

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

    Map<Integer /*ID route*/, List<Integer> /*ID cua*/> map = new HashMap<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setInfoAcount();
        this.loadTableColumns();
        try {
            this.loadTicketOrdered();
        } catch (SQLException ex) {
            Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        TextFields.bindAutoCompletion(txtSearchDestination, getAllNameStation());
        TextFields.bindAutoCompletion(txtSearchDeparture, getAllNameStation());
    }

    private List<String> getAllNameStation() {
        List<String> name = new ArrayList<>();
        List<Station> listStation = new ArrayList<>();
        try {
            listStation = STATION_SERVICE.getAllStation();
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
        if (txtSearchDestination.getText() != null && txtSearchDestination.getText() != "" && txtSearchDeparture.getText() != null && txtSearchDeparture.getText() != "" && dpDateOrder.getValue() != null) {
            // Lấy ngày tháng năm hiện tại
            LocalDate currentDate = LocalDate.now();

            // Chuyển đổi giá trị dpDateOrder sang kiểu LocalDate
            LocalDateTime dateTimeOrder = dpDateOrder.getValue().atStartOfDay();
            LocalDate dateOrder = dateTimeOrder.toLocalDate();
            if (STATION_SERVICE.getStationByName(txtSearchDestination.getText()) == null) {
                MessageBox.getBox("Warning", "Bạn đã nhập chuyến đến không đúng", Alert.AlertType.INFORMATION).show();
                loadRouteData(null);
            } else if (STATION_SERVICE.getStationByName(txtSearchDeparture.getText()) == null) {
                MessageBox.getBox("Warning", "Bạn đã nhập chuyến đi không đúng", Alert.AlertType.INFORMATION).show();
                loadRouteData(null);
            } else if (dateOrder.isBefore(currentDate) || dateOrder.equals(currentDate)) {
                MessageBox.getBox("Warning", "Bạn đã nhập ngày ở quá khứ", Alert.AlertType.INFORMATION).show();
                loadRouteData(null);
            } else {
                List<Route> listRoute = new ArrayList<>();
                listRoute = ROUTE_SERVICE.getRouteByDesIdByDepId(
                        STATION_SERVICE.getStationByName(txtSearchDestination.getText()).getStationId().toString(),
                        STATION_SERVICE.getStationByName(txtSearchDeparture.getText()).getStationId().toString(),
                        java.sql.Date.valueOf(dpDateOrder.getValue()));
                if (listRoute.isEmpty() == true) {
                    MessageBox.getBox("Warning", "Không có chuyến xe cần tìm", Alert.AlertType.INFORMATION).show();
                    loadRouteData(null);
                } else {
                    for (Route r : listRoute) {
                        // only changes num, not the array element
                        loadRouteData(r.getRouteId());
                    }
                }
            }
        } else {
            MessageBox.getBox("Warning", "Chưa nhập đủ dữ liệu cần thiết!", Alert.AlertType.WARNING).show();
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
                                    if (map.containsKey(st.getRouteID()) == true) {
                                        map.get(st.getRouteID()).add(st.getCouchetteID());
                                    } else {
                                        List<Integer> list1 = new ArrayList<>();
                                        list1.add(st.getCouchetteID());
                                        map.put(st.getRouteID(), list1);
                                    }
                                    cbTicketOrdered.getItems().clear();

                                    for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
                                        Integer routeId = entry.getKey();
                                        List<Integer> couchetteIds = entry.getValue();
                                        if (!couchetteIds.isEmpty()) {
                                            for (Integer couchetteId : couchetteIds) {
                                                cbTicketOrdered.getItems().add("Chuyến: " + routeId + " - Ghế: " + couchetteId);
                                            }
                                        }
                                    }
                                    int itemCount = 0;
                                    for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
                                        itemCount += entry.getValue().size();
                                    }
                                    txtOrderCount.setText(Integer.toString(itemCount));
                                    if (itemCount > 0) {
                                        btnOrder.setDisable(false);
                                        btnFindRoute.setDisable(true);
                                        txtSearchDeparture.setDisable(true);
                                        txtSearchDestination.setDisable(true);
                                        dpDateOrder.setDisable(true);
                                    }
                                    for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
                                        Integer currentRouteId = entry.getKey();
                                        List<Integer> list = map.get(currentRouteId);
                                        if (!list.isEmpty()) {
                                            for (Integer couchetteId : list) {
                                                if (st.getRouteID() == currentRouteId && st.getCouchetteID() == couchetteId) {
                                                    btn.setText("Hủy");
                                                }
                                            }
                                        }
                                    }
                                    MessageBox.getBox("Đặt vé", "Vé xe đã vào danh sách xác nhận", Alert.AlertType.INFORMATION).show();
                                }
                            });
                        } else {
                            if ("hủy".equalsIgnoreCase(btn.getText())) {
                                Alert a = MessageBox.getBox("Hủy vé", "Bạn có chắc hủy vé này", Alert.AlertType.CONFIRMATION);
                                a.showAndWait().ifPresent(res -> {
                                    if (res == ButtonType.OK) {
                                        if (map.isEmpty()) {
                                            MessageBox.getBox("Lỗi hủy vé", "Vé hiện tại trong giỏ đợi đang rỗng!", Alert.AlertType.CONFIRMATION).showAndWait();
                                        } else {
                                            map.get(st.getRouteID()).remove(Integer.valueOf(st.getCouchetteID()));
                                            cbTicketOrdered.getItems().remove("Chuyến: " + st.getRouteID() + " - " + "Ghế: " + st.getCouchetteID());
                                        }
//                                        routeId.remove(st.getRouteID());
//                                        for (Object item : cbTicketOrdered.getItems()) {
//                                            if (item.equals(st.getCouchetteID().toString())) {
//                                                cbTicketOrdered.getItems().remove(item);
//                                                break;
//                                            }
//                                        }
                                        int itemCount = 0;
                                        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
                                            itemCount += entry.getValue().size();
                                        }
                                        txtOrderCount.setText(Integer.toString(itemCount));
                                        if (itemCount == 0) {
                                            btnOrder.setDisable(true);
                                            btnFindRoute.setDisable(false);
                                            txtSearchDeparture.setDisable(false);
                                            txtSearchDestination.setDisable(false);
                                            dpDateOrder.setDisable(false);
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
        Alert a = MessageBox.getBox("Đặt vé", "Xác nhận đặt vé!", Alert.AlertType.CONFIRMATION);
        Timestamp printingDate = Timestamp.valueOf(LocalDateTime.now().format(DTF));
        a.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                try {
                    for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
                        Integer currentRouteId = entry.getKey();
                        List<Integer> list = map.get(currentRouteId);
                        Iterator<Integer> iterator = list.iterator();
                        while (iterator.hasNext()) {
                            int i = iterator.next();
                            Ticket t = new Ticket(null,
                                    i,
                                    App.currentUser.getUser_id(),
                                    USER_SERVICE.getOneUserIdByName("Duy nến").getUser_id(),
                                    currentRouteId,
                                    false);
                            if (BOOKING_SERVICE.AddTicket(t, COUCHETTE_SERVICE.getOneCouchetteByID(i))) {
                                seat.updateStatusSeat(i, true);
                                iterator.remove();
                            } else {
                                MessageBox.getBox("Xác nhận đặt vé không thành công", "Vui lòng đặt vé lại!", Alert.AlertType.ERROR).showAndWait();
                            }
                        }
                    }

                    MessageBox.getBox("Xác nhận đặt vé thành công", "Hãy đến quầy OUBus để lấy vé!", Alert.AlertType.INFORMATION).showAndWait();
                    this.txtOrderCount.setText("");
                    this.cbTicketOrdered.getItems().clear();
                    this.btnFindRoute.setDisable(false);
                    this.txtSearchDeparture.setDisable(false);
                    this.txtSearchDestination.setDisable(false);
                    this.dpDateOrder.setDisable(false);
                    loadTicketOrdered();
                    this.tvRoute.refresh();
                    findRoute();

                } catch (SQLException ex) {
                    Logger.getLogger(BookingController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        );
    }

//    @FXML 
//    private void processBooking() {
//        Alert confirmationDialog = showConfirmationDialog();
//        confirmationDialog.showAndWait().ifPresent(res -> {
//            if (res == ButtonType.OK) {
//                try {
//                    for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
//                        Integer currentRouteId = entry.getKey();
//                        List<Integer> couchetteIds = map.get(currentRouteId);
//                        Iterator<Integer> iterator = couchetteIds.iterator();
//                        while (iterator.hasNext()) {
//                            int couchetteId = iterator.next();
//                            int userId = App.currentUser.getUser_id();
//                            int systemUserId = USER_SERVICE.getOneUserIdByName("Duy nến").getUser_id();
//                            Ticket ticket = new Ticket(null, couchetteId, userId, systemUserId, currentRouteId);
//                            if (addTicketToBooking(ticket, couchetteId)) {
//                                seat.updateStatusSeat(couchetteId, true);
//                                iterator.remove();
//                            } else {
//                                MessageBox.getBox("Xác nhận đặt vé không thành công", "Vui lòng đặt vé lại!", Alert.AlertType.ERROR).showAndWait();
//                            }
//                        }
//                    }
//                    MessageBox.getBox("Xác nhận đặt vé thành công", "Hãy đến quầy OUBus để lấy vé!", Alert.AlertType.INFORMATION).showAndWait();
//                    this.txtOrderCount.setText("");
//                    this.cbTicketOrdered.getItems().clear();
//                    loadRouteData(null);
//                } catch (SQLException ex) {
//                    Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//    }
//
//    private Alert showConfirmationDialog() {
//        return MessageBox.getBox("Đặt vé", "Xác nhận đặt vé!", Alert.AlertType.CONFIRMATION);
//    }
//
//    private boolean addTicketToBooking(Ticket ticket, int couchetteId) throws SQLException {
//        return BOOKING_SERVICE.AddTicket(ticket, COUCHETTE_SERVICE.getOneCouchetteByID(couchetteId));
//    }
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

    public void loadTicketOrdered() throws SQLException {
        StringBuilder sb = new StringBuilder();
        if (BOOKING_SERVICE.getAllTicketByCustomerId(App.currentUser.getUser_id()).isEmpty()) {
            txtArea.clear();
        } else {
            for (Ticket ticket : BOOKING_SERVICE.getAllTicketByCustomerId(App.currentUser.getUser_id())) {
                String info = "Mã vé: " + ticket.getTicketId() + " - Mã chuyến: " + ticket.getRoute() + " - Ghế: " + ticket.getCouchette();
                LocalDateTime departureTime = (ROUTE_COACH_SERVICE.getOneRouteCoachById(ticket.getRoute(), COUCHETTE_SERVICE.getOneCouchetteByID(ticket.getCouchette()).getCouchId())).getDepartureTime().toLocalDateTime();
                String formattedDepartureTime = DTF.format(departureTime);
                info += "- Thời gian: " + formattedDepartureTime;
                if (ticket.getPrintingDate() == null) {
                    info += " - Tình trạng vé: Chưa lấy vé";
                } else {
                    info += " - Tình trạng vé: Đã lấy vé";
                }
                sb.append(info);
                sb.append("\n\n"); // add two new lines between each ticket info
            }
            txtArea.setText(sb.toString());
        }
    }
}
