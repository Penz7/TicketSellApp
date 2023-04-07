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

    public boolean AddTicket(Ticket ticket, Couchette couchette) throws SQLException {
        try (Connection connection = JdbcUtils.getConn()) {
            connection.setAutoCommit(false);
            String query = "INSERT INTO vexe (ID_Ghe, ID_KhachHang, ID_ChuyenXe, "
                    + "ID_NhanVien, NgayIn) VALUES "
                    + "(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, couchette.getCouchetteId());
            preparedStatement.setInt(2, ticket.getCustomer());
            preparedStatement.setInt(3, ticket.getRoute());
            preparedStatement.setInt(4, ticket.getStaff());
            preparedStatement.setTimestamp(5, ticket.getPrintingDate());
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
    
     public List<Ticket> getAllTicketByCustomerId(Integer customerId) throws SQLException{
         List<Ticket> tickets = new ArrayList<>();
        try(Connection connection = JdbcUtils.getConn()){
            String query = "SELECT * FROM vexe WHERE ID_KhachHang = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Ticket ticket = new Ticket();
                ticket.setTicketId(rs.getInt("ID_VeXe"));
                ticket.setCouchette(rs.getInt("ID_Ghe"));
                ticket.setCustomer(rs.getInt("ID_KhachHang"));
                ticket.setRoute(rs.getInt("ID_ChuyenXe"));
                ticket.setStaff(rs.getInt("ID_NhanVien"));
                ticket.setPrintingDate(rs.getTimestamp("NgayIn"));
                tickets.add(ticket);
            }
        }
         return tickets;
    } 
}
