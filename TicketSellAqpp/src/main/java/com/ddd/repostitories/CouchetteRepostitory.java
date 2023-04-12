/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.pojo.Couchette;
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
 * @author admin
 */
public class CouchetteRepostitory {

//    Lấy ghế trong các xe
    public List<Couchette> getSeatsByVehicleId(Integer vehicleId) throws SQLException {
        List<Couchette> seats = new ArrayList<>();
        String sql = "SELECT * FROM ghe";
        if (vehicleId != null) {
            sql += " WHERE ID_Xe = ?";
        }

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (vehicleId != null) {
                stmt.setInt(1, vehicleId);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Couchette seat = new Couchette(rs.getInt("ID_Ghe"), rs.getBoolean("TinhTrangGhe"), rs.getInt("ID_Xe"), rs.getInt("ThuTuGhe"));
                seats.add(seat);
            }
        }

        return seats;
    }

    public Couchette getOneCouchetteByID(Integer CouchetteId) throws SQLException {
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement("SELECT * FROM ghe WHERE ID_Ghe = ? ")) {
            stm.setInt(1, CouchetteId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Couchette s = new Couchette(rs.getInt("ID_Ghe"), rs.getBoolean("TinhTrangGhe"), rs.getInt("ID_Xe"), rs.getInt("ThuTuGhe"));
                    return s;
                } else {
                    throw new SQLException("No couchette found with ID " + CouchetteId );
                }
            }
        }
    }

    public List<Couchette> getAllSeat() throws SQLException {
        List<Couchette> results = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM ghe";
            PreparedStatement stm = conn.prepareCall(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Couchette s = new Couchette(rs.getInt("ID_Ghe"), rs.getBoolean("TinhTrangGhe"), rs.getInt("ID_Xe"), rs.getInt("ThuTuGhe"));
                results.add(s);
            }
        }
        return results;
    }

    public boolean updateStatusSeat(Integer seatId, boolean status) {
        String sql = "UPDATE ghe SET TinhTrangGhe = ? WHERE ID_Ghe = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setBoolean(1, status);
            stm.setInt(2, seatId);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating seat status for ID " + seatId, ex);
            return false;
        }
    }
}
