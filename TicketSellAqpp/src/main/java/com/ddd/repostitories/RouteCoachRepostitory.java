/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.pojo.RouteCoach;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class RouteCoachRepostitory {

    public RouteCoach getOneRouteCoachById(Integer RouteId, Integer CoachId) throws SQLException {
        try (Connection connection = JdbcUtils.getConn()) {
            String query = "SELECT *\n"
                    + "FROM chuyenxe_xe\n"
                    + "WHERE ID_ChuyenXe = ?\n"
                    + "AND ID_Xe = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, RouteId);
            preparedStatement.setInt(2, CoachId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                RouteCoach routeCoach = new RouteCoach();
                routeCoach.setRouteId(rs.getInt("ID_ChuyenXe"));
                routeCoach.setCoachId(rs.getInt("ID_Xe"));
                routeCoach.setDepartureTime(rs.getTimestamp("gioKhoiHanh"));
                return routeCoach;
            } else {
                throw new SQLException("No RouteID found with ID " + RouteId + " and CoachId with ID = " + CoachId);
            }

        }
    }

    public List<RouteCoach> getRouteCoach(String kw) throws SQLException {
        List<RouteCoach> results = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM chuyenxe_xe";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE ID_ChuyenXe like concat('%', ?, '%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                RouteCoach s = new RouteCoach(rs.getInt("ID_Chuyenxe"), rs.getInt("ID_Xe"), rs.getTimestamp("gioKhoiHanh"));
                results.add(s);
            }
        }

        return results;
    }

    public boolean deleteRouteCoach(Integer idChuyenXe, Integer idXe, Timestamp gioKhoiHanh) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM chuyenxe_xe WHERE ID_ChuyenXe=? AND ID_Xe=? AND gioKhoiHanh=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, idChuyenXe);
            stm.setInt(2, idXe);
            stm.setTimestamp(3, gioKhoiHanh);

            return stm.executeUpdate() > 0;
        }
    }

    public boolean updateRouteCoachbyID(String gioKhoiHanh, int idChuyenXe,int idXe) {
        String sql = "UPDATE chuyenxe_xe SET gioKhoiHanh = ? AND idXe=? WHERE ID_ChuyenXe = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, gioKhoiHanh);
            stm.setInt(2, idChuyenXe);
             stm.setInt(3, idXe);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating station name for ID " + idChuyenXe, ex);
            return false;
        }
    }

    public boolean addRouteCoach(Integer idChuyenxe, Integer idXe, String gioKhoiHanh) {
        String sql = "INSERT INTO chuyenxe_xe(ID_ChuyenXe, ID_Xe, gioKhoiHanh) VALUES (?,?,?)";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            statement.setInt(1, idChuyenxe);
            statement.setInt(2, idXe);
            statement.setString(3, gioKhoiHanh);

            statement.addBatch();
            statement.executeBatch();

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Integer> getIdRoute() throws SQLException {
        List<Integer> results = new ArrayList<>();
        String sql = "SELECT ID_ChuyenXe FROM chuyenxe";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("ID_ChuyenXe");
                results.add(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error getting IDs of routes", ex);
            throw ex;
        }

        return results;
    }
    
     public List<Integer> getIdCoach() throws SQLException {
        List<Integer> results = new ArrayList<>();
        String sql = "SELECT ID_Xe FROM xe";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("ID_Xe");
                results.add(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error getting IDs of routes", ex);
            throw ex;
        }

        return results;
    }
}
