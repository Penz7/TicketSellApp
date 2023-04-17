/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.pojo.Ticket;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class TicketRepostitory {

    public Ticket getTicketById(Integer ticketId) throws SQLException {
        try (Connection connection = JdbcUtils.getConn()) {
            String query = "SELECT * FROM vexe WHERE id_VeXe = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ticketId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(rs.getInt("ID_VeXe"));
                ticket.setCouchetteId(rs.getInt("ID_Ghe"));
                ticket.setCustomerId(rs.getInt("ID_KhachHang"));
                ticket.setRouteId(rs.getInt("ID_ChuyenXe"));
                ticket.setStaffId(rs.getInt("ID_NhanVien"));
                ticket.setPrintingDate(rs.getTimestamp("NgayIn"));
                ticket.setIsConfirm(rs.getBoolean("isConfirm"));
                return ticket;
            }
        }
        return null;
    }

    public boolean addDateStaffOrder(Integer staffID, Timestamp date, Integer ticketID) throws SQLException {
        String query = "UPDATE vexe SET ID_NhanVien = ?, NgayIn = ? WHERE ID_VeXe = ?";
        try (Connection connection = JdbcUtils.getConn(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, staffID);
            preparedStatement.setTimestamp(2, date);
            preparedStatement.setInt(3, ticketID);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException ex) {
            System.err.println("Error adding staff order date to database: " + ex.getMessage());
            return false;
        }
    }

    public List<Ticket> getAllTickets(Integer kw) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM vexe";
            if (kw != null) {
                sql += " WHERE ID_VeXe LIKE CONCAT('%', ?, '%')";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (kw != null) {
                preparedStatement.setInt(1, kw);
            }
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(rs.getInt("ID_VeXe"));
                ticket.setCouchetteId(rs.getInt("ID_Ghe"));
                ticket.setCustomerId(rs.getInt("ID_KhachHang"));
                ticket.setRouteId(rs.getInt("ID_ChuyenXe"));
                ticket.setStaffId(rs.getInt("ID_NhanVien"));
                ticket.setPrintingDate(rs.getTimestamp("NgayIn"));
                ticket.setIsConfirm(rs.getBoolean("isConfirm"));
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            throw new SQLException("Error occurred while retrieving tickets: " + e.getMessage());
        }
        return tickets;
    }

    public boolean deleteTicket(Integer id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM vexe WHERE ID_VeXe=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);

            return stm.executeUpdate() > 0;
        }
    }

    public boolean checkConfirmOrder(Integer id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement("UPDATE vexe SET isConfirm = true WHERE ID_VeXe = ?")) {
            stm.setInt(1, id);
            return stm.executeUpdate() > 0;
        }
    }

    public boolean isUserCustomer(int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM user WHERE role_id = 3 AND user_id = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }
    
    public boolean updateTicketSeat(Integer ticketID, Integer seatID, Integer routeID) {
        String sql = "UPDATE vexe SET id_ghe = ?, id_chuyenxe = ? WHERE id_vexe = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, seatID);
            stm.setInt(2, routeID);
            stm.setInt(3, ticketID);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating ticket seat for ID " + ticketID, ex);
            return false;
        }
    }
}
