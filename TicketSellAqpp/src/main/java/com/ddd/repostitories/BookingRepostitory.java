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
        List<Ticket> results = new ArrayList<>();

        try (Connection connection = JdbcUtils.getConn()) {
            String query = "INSERT INTO vexe (ID_Ghe, ID_KhachHang, ID_ChuyenXe, "
                    + "ID_NhanVien, NgayIn) VALUES "
                    + "(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, couchette.getCouchetteId());
            preparedStatement.setInt(2, ticket.getCustomer().getUser_id());
            preparedStatement.setInt(3, ticket.getRoute().getRouteId());
            preparedStatement.setInt(4, ticket.getStaff().getUser_id());
            preparedStatement.setTimestamp(5, ticket.getPrintingDate());
            preparedStatement.executeUpdate();
            try {
                connection.commit();
                return true;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                return false;
            }
        }
    }
    
    
}


