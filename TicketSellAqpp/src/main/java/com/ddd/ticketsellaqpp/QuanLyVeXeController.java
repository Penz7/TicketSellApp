/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.pojo.Ticket;
import com.ddd.pojo.User;
import com.ddd.repostitories.TicketRepostitory;
import com.ddd.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author admin
 */
public class QuanLyVeXeController implements Initializable {

    static TicketRepostitory s = new TicketRepostitory();
    private final static DateTimeFormatter DTF;

    static {
        DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
            this.confirmOrder();
            this.loadTableTicket();
            this.loadTicketData();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyVeXeController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                                if (s.deleteTicket(ticket.getTicketId())) {
                                    MessageBox.getBox("Question", "Xóa thành công!!!", Alert.AlertType.INFORMATION).show();
                                    loadTicketData();
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
                if (empty || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    setGraphic(cancelBtn);
                }
            }
        });

        tbTicket.getColumns().addAll(colTicketId, colCouchetteId, colCustomerId, colRouteId, colStaffId, colPrintingDate, colIsConfirm, colCancel);
    }

    private void loadTicketData() throws SQLException {
        List<Ticket> tickets = s.getAllTickets();
        this.tbTicket.setItems(FXCollections.observableList(tickets));
    }

    private Alert createConfirmationDialog(String message) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Xác nhận");
        confirmationDialog.setHeaderText(message);
        return confirmationDialog;
    }

    public void confirmOrder() throws SQLException {
        // Add listener to selection model
        tbTicket.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Get the selected ticket's confirmation status
                boolean isConfirmed = newSelection.isIsConfirm();

                // Enable/disable the confirm button and update its text
                confirmBtn.setDisable(isConfirmed);
                confirmBtn.setText(isConfirmed ? "Vé đã xác nhận" : "Xác nhận vé");
            }
        });

        // Add event handler to the confirm button
        confirmBtn.setOnAction(evt -> {
            // Get the selected ticket
            Ticket selectedRow = tbTicket.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                int ticketId = selectedRow.getTicketId();
                try {
                    boolean isConfirmed = s.checkConfirmOrder(ticketId);
                    if (isConfirmed) {
                        Timestamp printingDate = Timestamp.valueOf(LocalDateTime.now().format(DTF));
                        if(s.addDateStaffOrder(App.currentUser.getUser_id(), printingDate,ticketId)) {
                        MessageBox.getBox("Question", "Xác nhận thành công!!!", Alert.AlertType.INFORMATION).show();
                        loadTicketData();
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
