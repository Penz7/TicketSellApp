/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.pojo.Ticket;
import com.ddd.pojo.User;
import com.ddd.services.CouchetteService;
import com.ddd.services.RouteService;
import com.ddd.services.TicketService;
import com.ddd.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class QuanLyVeXeController implements Initializable {

    private final static TicketService TICKET_SERVICE;
    private final static CouchetteService COUCHETTE_SERVICE;
    private final static RouteService ROUTE_SERVICE;

    private final static DateTimeFormatter DTF;

    static {
        DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        TICKET_SERVICE = new TicketService();
        COUCHETTE_SERVICE = new CouchetteService();
        ROUTE_SERVICE = new RouteService();
    }
    private static User currentUser;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button confirmBtn;

    @FXML
    TableView<Ticket> tbTicket;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            confirmOrder();
            loadTableTicket();
            loadTicketData(null);
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyVeXeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue.trim();
            if (searchText.isEmpty()) {
                try {
                    loadTicketData(null); // Load all tickets
                } catch (SQLException ex) {
                    Logger.getLogger(QuanLyVeXeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (searchText.matches("\\d+")) {
                int ticketId = Integer.parseInt(searchText);
                try {
                    loadTicketData(ticketId); // Load tickets by ID
                } catch (SQLException ex) {
                    Logger.getLogger(QuanLyVeXeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } // No need for an else statement here since invalid input is ignored
        });
    }

    private void loadTableTicket() {
        TableColumn<Ticket, Integer> colTicketId = new TableColumn<>("Vé");
        colTicketId.setCellValueFactory(new PropertyValueFactory<>("ticketId"));

        TableColumn<Ticket, Integer> colCouchetteId = new TableColumn<>("Ghế");
        colCouchetteId.setCellValueFactory(new PropertyValueFactory<>("couchette"));

        TableColumn<Ticket, Integer> colCustomerId = new TableColumn<>("Khách hàng");
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customer"));

        TableColumn<Ticket, Integer> colRouteId = new TableColumn<>("Chuyến xe");
        colRouteId.setCellValueFactory(new PropertyValueFactory<>("route"));

        TableColumn<Ticket, Integer> colStaffId = new TableColumn<>("Nhân viên");
        colStaffId.setCellValueFactory(new PropertyValueFactory<>("staff"));

        TableColumn<Ticket, Timestamp> colPrintingDate = new TableColumn<>("Ngày xác nhận");
        colPrintingDate.setCellValueFactory(new PropertyValueFactory<>("printingDate"));

        TableColumn<Ticket, Boolean> colIsConfirm = new TableColumn<>("Xác nhận");
        colIsConfirm.setCellValueFactory(new PropertyValueFactory<>("isConfirm"));

        TableColumn<Ticket, Void> colCancel = new TableColumn<>("Hủy vé");
        colCancel.setCellFactory(column -> new TableCell<>() {
            private final Button cancelBtn = new Button("Hủy vé");

            {
                cancelBtn.setOnAction(event -> {
                    Ticket ticket = getTableRow().getItem();
                    Alert confirmationDialog = createConfirmationDialog("Bạn có chắc chắn muốn xóa vé này?");
                    confirmationDialog.showAndWait().ifPresent(result -> {
                        if (result == ButtonType.OK) {
                            try {
                                if (TICKET_SERVICE.deleteTicket(ticket.getTicketId())) {
                                    COUCHETTE_SERVICE.updateStatusSeat(ticket.getCouchette(), false);
                                    MessageBox.getBox("Question", "Xóa thành công!!!", Alert.AlertType.INFORMATION).show();
                                    loadTicketData(null);
                                } else {
                                    MessageBox.getBox("Question", "Xóa thất bại!!!", Alert.AlertType.WARNING).show();
                                }
                            } catch (SQLException ex) {
                                MessageBox.getBox("Warning", "Chuyến xe hiện đang sắp khởi hành hoặc đang trong danh sách chạy!!!", Alert.AlertType.WARNING).show();
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
                    setGraphic(cancelBtn);
                }
            }
        });

        TableColumn<Ticket, Void> colChange = new TableColumn<>("Đổi vé");
        colChange.setCellFactory(column -> new TableCell<>() {
            private final Button cancelBtn = new Button("Đổi vé");

            {
                cancelBtn.setOnAction(event -> {
                    Button b = (Button) event.getSource();
                    TableCell cell = (TableCell) b.getParent();
                    Ticket ticket = (Ticket) cell.getTableRow().getItem();
                    try {
                        if (!checkTimeOrder(ROUTE_SERVICE.getDepartureTimeByIdRoute(ticket.getRoute()))) {
                            Label seatLabel = new Label("ID Ghế:");
                            ComboBox<Integer> seatComboBox = new ComboBox<>();
                            Label routeLabel = new Label("ID Chuyến xe:");
                            ComboBox<Integer> routeComboBox = new ComboBox<>();
                            Button confirmButton = new Button("Xác nhận");
                            HBox inputBox = new HBox(10, seatLabel, seatComboBox, routeLabel, routeComboBox);
                            VBox root = new VBox(10, inputBox, confirmButton);
                            Scene scene = new Scene(root, 600, 200);
                            Stage stage = new Stage();
                            stage.setTitle("Sửa thời gian khởi hành");
                            stage.setScene(scene);
                            stage.show();
                            
                            try {
                                routeComboBox.getItems().addAll(ROUTE_SERVICE.getIdRoute());
                            } catch (SQLException ex) {
                                Logger.getLogger(QuanLyVeXeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            routeComboBox.setOnAction(event2 -> {
                                try {
                                    seatComboBox.getItems().clear();
                                    Integer selectedRouteId = routeComboBox.getValue();
                                    seatComboBox.getItems().addAll(COUCHETTE_SERVICE.getidSeatbyIdRoute(selectedRouteId));
                                } catch (SQLException ex) {
                                    Logger.getLogger(QuanLyVeXeController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                            
                            confirmButton.setOnAction(event2 -> {
                                Integer selectedSeatId = seatComboBox.getValue();
                                Integer selectedRouteId = routeComboBox.getValue();
                                COUCHETTE_SERVICE.updateStatusSeat(selectedRouteId, false);
                                if (TICKET_SERVICE.updateTicketSeat(ticket.getTicketId(), selectedSeatId, selectedRouteId)) {
                                    MessageBox.getBox("Thông báo", "Sửa vé xe thành công!!!", Alert.AlertType.INFORMATION).show();
                                    try {
                                        loadTicketData(null);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(QuanLyVeXeController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } else {
                                    // If the update fails, revert the seat's status to "available" in the database and show an error message
                                    COUCHETTE_SERVICE.updateStatusSeat(ticket.getCouchette(), true);
                                    MessageBox.getBox("Question", "Sửa thất bại!!!", Alert.AlertType.ERROR).show();
                                }
                                stage.close();
                            });
                        } else {
                            MessageBox.getBox("Thông báo", "Chuyến xe sắp khởi hành trong 60 phút tới!! Vé xe không được sửa đổi nữa!!", Alert.AlertType.WARNING).show();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(QuanLyVeXeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    setGraphic(cancelBtn);
                }
            }
        });

        tbTicket.getColumns().addAll(colTicketId, colCouchetteId, colCustomerId, colRouteId, colStaffId, colPrintingDate, colIsConfirm, colChange, colCancel);
    }

    private void loadTicketData(Integer kw) throws SQLException {
        List<Ticket> tickets = TICKET_SERVICE.getAllTickets(kw);
        this.tbTicket.setItems(FXCollections.observableList(tickets));
    }

    private Alert createConfirmationDialog(String message) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Xác nhận");
        confirmationDialog.setHeaderText(message);
        return confirmationDialog;
    }

    public void confirmOrder() throws SQLException {
        tbTicket.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                boolean isConfirmed = newSelection.isIsConfirm();
                confirmBtn.setDisable(isConfirmed);
                confirmBtn.setText(isConfirmed ? "Vé đã xác nhận" : "Xác nhận vé");
            }
        });
        confirmBtn.setOnAction(evt -> {
            Ticket selectedRow = tbTicket.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                int ticketId = selectedRow.getTicketId();
                try {
                    boolean isConfirmed = TICKET_SERVICE.checkConfirmOrder(ticketId);
                    if (isConfirmed) {
                        Timestamp printingDate = Timestamp.valueOf(LocalDateTime.now().format(DTF));
                        if (TICKET_SERVICE.addDateStaffOrder(App.currentUser.getUser_id(), printingDate, ticketId)) {
                            MessageBox.getBox("Question", "Xác nhận thành công!!!", Alert.AlertType.INFORMATION).show();
                            loadTicketData(null);
                        } else {
                            MessageBox.getBox("Warning", "Thêm thất bại", Alert.AlertType.ERROR);
                        }
                    } else {
                        MessageBox.getBox("Question", "Thất bại!!!", Alert.AlertType.WARNING).show();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(QuanLyVeXeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    
     public boolean checkTimeOrder(Timestamp departureTime) {
        LocalTime depTime = departureTime.toLocalDateTime().toLocalTime();
        LocalTime bookingTime = LocalTime.now().plusMinutes(60);
        return (depTime.isAfter(LocalTime.now()) && depTime.isBefore(bookingTime));
    }

    @FXML
    private void backMenu() {
        try {
            App.setRoot("home-staff");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        QuanLyVeXeController.currentUser = currentUser;
    }

}
