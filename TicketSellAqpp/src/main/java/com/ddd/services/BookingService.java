/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.pojo.Couchette;
import com.ddd.pojo.Ticket;
import com.ddd.repostitories.BookingRepostitory;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Admin
 */
public class BookingService {

    private final static BookingRepostitory BOOKING_REPOSTITORY;

    static {
        BOOKING_REPOSTITORY = new BookingRepostitory();
    }

    public boolean AddTicket(Ticket ticket, Couchette couchette) throws SQLException {
        return BOOKING_REPOSTITORY.AddTicket(ticket, couchette);
    }

    public boolean checkSeatLimit(int maVeXe) throws SQLException {
        return BOOKING_REPOSTITORY.checkSeatLimit(maVeXe);
    }

    public List<Ticket> getAllTicketByCustomerId(Integer customerId) throws SQLException {
        return BOOKING_REPOSTITORY.getAllTicketByCustomerId(customerId);
    }
}
