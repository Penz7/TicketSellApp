/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.pojo.Seat;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class SeatReposititory {

//    Lấy ghế trong các xe
  public List<Seat> getSeatByCoadID(Integer kw) throws SQLException {
    List<Seat> results = new ArrayList<>();

    try (Connection conn = JdbcUtils.getConn();
         PreparedStatement stm = conn.prepareStatement("SELECT * FROM ghe" + (kw != null ? " WHERE ID_Xe = ?" : ""));
    ) {
        if (kw != null) {
            stm.setInt(1, kw);
        }
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Seat s = new Seat(rs.getInt("ID_Ghe"),rs.getBoolean("TinhTrangGhe"), rs.getInt("ID_Xe"));
            results.add(s);
        }
    }

    return results;
}
  
    
     public List<Seat> getAllSeat() throws SQLException {
        List<Seat> results = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM ghe";
            PreparedStatement stm = conn.prepareCall(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Seat s = new Seat(rs.getInt("ID_Ghe"), rs.getBoolean("TinhTrangGhe"), rs.getInt("ID_Xe"));
                results.add(s);
            }
        }
        return results;
    }
}
