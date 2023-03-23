/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.pojo.Station;
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
public class StationRepostitory {
      public List<Station> getStationByKeyWord(String kw) throws SQLException {
        List<Station> results = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM benxe";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE TenBen like concat('%', ?, '%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Station s = new Station(rs.getInt("ID_Ben"), rs.getString("TenBen"));
                results.add(s);
            }
        }
        return results;
    }
      
     public List<Station> getAllStation() throws SQLException {
        List<Station> results = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM benxe";
            PreparedStatement stm = conn.prepareCall(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Station s = new Station(rs.getInt("ID_Ben"), rs.getString("TenBen"));
                results.add(s);
            }
        }
        return results;
    }
     
       public boolean isExistStationByKeyWord(String kw) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM benxe";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE TenBen = '?'";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return false;
            }
        }
        return true;
    }
}
