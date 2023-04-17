/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.pojo.Coach;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class CoachRepostitory {

    public List<Coach> getCoach(String kw) throws SQLException {
        List<Coach> results = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM xe";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE TenXe like concat('%', ?, '%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Coach s = new Coach(rs.getInt("ID_Xe"), rs.getString("TenXe"), rs.getString("BienSo"));
                results.add(s);
            }
        }

        return results;
    }


    public boolean deleteCoach(Integer id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM xe WHERE ID_Xe=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);

            return stm.executeUpdate() > 0;
        }
    }

    public boolean updateCoachById(String tenXe, String BienSo, Integer ID_Xe) {
        String sql = "UPDATE xe SET TenXe = ?, BienSo = ? WHERE ID_Xe = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareCall(sql)) {
            stm.setString(1, tenXe);
            stm.setString(2, BienSo);
            stm.setInt(3, ID_Xe);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating station name for ID " + ID_Xe, ex);
            return false;
        }
    }

    public boolean addCoach(String TenXe, String BienSo) {
        String sql = "INSERT INTO xe(TenXe, BienSo) VALUES (?,?)";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            statement.setString(1, TenXe);
            statement.setString(2, BienSo);
            statement.addBatch();
            statement.executeBatch();
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Integer getCoachIDbyname(String name) throws SQLException {
        Integer a = null;
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT ID_Xe FROM xe WHERE TenXe= ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                a = rs.getInt("ID_Xe");
            }
        }
        return a;
    }
    
    public Coach getCoachByID(Integer ID) throws SQLException {
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement("SELECT * FROM xe WHERE ID_Xe = ?")) {
            stm.setInt(1, ID);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Coach s = new Coach(rs.getInt("ID_Xe"), rs.getString("TenXe"), rs.getString("BienSo"));
                    return s;
                } else {
                    throw new SQLException("No route found with ID " + ID);
                }
            }
        }
    }

}
