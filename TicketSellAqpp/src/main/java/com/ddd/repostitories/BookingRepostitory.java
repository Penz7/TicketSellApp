/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.pojo.Couchette;
import com.ddd.pojo.Ticket;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class BookingRepostitory {

    public boolean AddTicket(Ticket ticket, Integer couchetteId) throws SQLException {
        try (Connection connection = JdbcUtils.getConn()) {
            connection.setAutoCommit(false);
            String query = "INSERT INTO vexe (ID_Ghe, ID_KhachHang, ID_ChuyenXe, "
                    + "ID_NhanVien, NgayIn, isConfirm) VALUES "
                    + "(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, couchetteId);
            preparedStatement.setInt(2, ticket.getCustomerId());
            preparedStatement.setInt(3, ticket.getRouteId());
            preparedStatement.setInt(4, ticket.getStaffId());
            preparedStatement.setTimestamp(5, ticket.getPrintingDate());
            preparedStatement.setBoolean(6, ticket.isIsConfirm());
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    public List<Ticket> getAllTicketByCustomerId(Integer customerId) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConn()) {
            String query = "SELECT *\n"
                    + "FROM vexe\n"
                    + "WHERE ID_KhachHang = ?\n"
                    + "ORDER BY ID_VeXe DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
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
        }
        return tickets;
    }
    
}
