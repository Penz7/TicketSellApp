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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TicketRepository {
    public Ticket getTicketById(Integer ticketId) throws SQLException{
        try(Connection connection = JdbcUtils.getConn()){
            String query = "SELECT * FROM vexe WHERE id_VeXe = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ticketId);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Ticket ticket = new Ticket();
                ticket.setPrintingDate(rs.getDate("NgayIn"));
                return ticket;
            }
        }
         return null;
    }
    
  
}
