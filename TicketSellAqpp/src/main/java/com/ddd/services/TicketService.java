/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.pojo.Ticket;
import com.ddd.repostitories.TicketRepostitory;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TicketService {

    private final static TicketRepostitory TICKET_REPOSTITORY;

    static {
        TICKET_REPOSTITORY = new TicketRepostitory();
    }

    public Ticket getTicketById(Integer ticketId) throws SQLException {
        return TICKET_REPOSTITORY.getTicketById(ticketId);
    }

    public boolean checkUserCustomer(Integer user_id) throws SQLException {
        return TICKET_REPOSTITORY.isUserCustomer(user_id);
    }

    public boolean deleteTicket(Integer id) throws SQLException {
        return TICKET_REPOSTITORY.deleteTicket(id);
    }

    public boolean updateTicketSeat(Integer ticketID, Integer seatID, Integer routeID) {
        return TICKET_REPOSTITORY.updateTicketSeat(ticketID, seatID, routeID);
    }

    public List<Ticket> getAllTickets(Integer kw) throws SQLException {
        return TICKET_REPOSTITORY.getAllTickets(kw);
    }

    public boolean checkConfirmOrder(Integer id) throws SQLException {
        return TICKET_REPOSTITORY.checkConfirmOrder(id);
    }

    public boolean updatePrintingDateAndStaffConfirm(Integer staffID, Timestamp date, Integer ticketID) throws SQLException {
        return TICKET_REPOSTITORY.updatePrintingDateAndStaffConfirm(staffID, date, ticketID);
    }
}
