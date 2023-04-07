/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.pojo.Ticket;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TicketRepostitory {
    public Ticket getTicketById(Integer ticketId) throws SQLException{
        try(Connection connection = JdbcUtils.getConn()){
            String query = "SELECT * FROM vexe WHERE id_VeXe = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ticketId);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Ticket ticket = new Ticket();
                ticket.setTicketId(rs.getInt("ID_VeXe"));
                ticket.setCouchette(rs.getInt("ID_Ghe"));
                ticket.setCustomer(rs.getInt("ID_KhachHang"));
                ticket.setRoute(rs.getInt("ID_ChuyenXe"));
                ticket.setStaff(rs.getInt("ID_NhanVien"));
                ticket.setPrintingDate(rs.getTimestamp("NgayIn"));
                return ticket;
            }
        }
         return null;
    } 
}
