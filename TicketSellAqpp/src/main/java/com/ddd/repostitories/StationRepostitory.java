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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class StationRepostitory {

    public Station getStationByName(String NameStation) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM benxe \n";
            if (NameStation != null && !NameStation.isEmpty()) {
                sql += "WHERE TenBen = ?";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (NameStation != null && !NameStation.isEmpty()) {
                stm.setString(1, NameStation);
            }
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Station s = new Station(rs.getInt("ID_Ben"), rs.getString("TenBen"));
                return s;
            }
        }
        return null;
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

    public List<Station> getStations(String kw) throws SQLException {
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

    public boolean deleteStation(String id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM benxe WHERE ID_Ben=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id);

            return stm.executeUpdate() > 0;
        }
    }

    public boolean updateStationNameById(String id, String stationName) {
        String sql = "UPDATE benxe SET TenBen = ? WHERE ID_Ben = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareCall(sql)) {
            stm.setString(1, stationName);
            stm.setString(2, id);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating station name for ID " + id, ex);
            return false;
        }
    }

    public boolean addStation(String stationName) {
        String sql = "INSERT INTO benxe(TenBen) VALUES (?)";
        Connection conn = null;

        try {
            conn = JdbcUtils.getConn();
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, stationName);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            // Handle the error and log it
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isExistStationByName(String name) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT COUNT(*) FROM benxe WHERE TenBen=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }
}
