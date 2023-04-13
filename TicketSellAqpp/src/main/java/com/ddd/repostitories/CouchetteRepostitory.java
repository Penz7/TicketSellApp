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
                    throw new SQLException("No couchette found with ID " + CouchetteId);
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

    public List<Integer> getIdSeatbyIDRoute(Integer idRoute) throws SQLException {
        List<Integer> results = new ArrayList<>();
        String sql = "SELECT ghe.id_ghe\n"
                + "FROM ghe\n"
                + "JOIN chuyenxe_xe ON ghe.id_xe = chuyenxe_xe.id_xe\n"
                + "Where ID_ChuyenXe = ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {
            // Set the parameter value for the PreparedStatement
            stm.setInt(1, idRoute);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_ghe"); // retrieve the correct column name
                    results.add(id);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error getting IDs of routes", ex);
            throw ex;
        }

        return results;
    }

    public List<Boolean> getStatusSeatbyIDRoute(Integer idRoute) throws SQLException {
        List<Boolean> results = new ArrayList<>();
        String sql = "SELECT TinhTrangGhe FROM ghe "
                + "JOIN chuyenxe_xe ON ghe.id_xe = chuyenxe_xe.id_xe "
                + "WHERE ID_ChuyenXe = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, idRoute);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    boolean seatStatus = rs.getBoolean("TinhTrangGhe");
                    results.add(seatStatus);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error getting seat status", ex);
            throw ex;
        }
        return results;
    }

}
