/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.pojo.Route;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.Date;
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
public class RouteRepostitory {

    public List<Route> getRoutesById(Integer id) throws SQLException {
        List<Route> routes = new ArrayList<>();
        String query = "SELECT ID_ChuyenXe, tenCX, giaChuyen, ID_benDen, ID_benDi FROM chuyenxe WHERE ID_ChuyenXe = ?";

        try (Connection connection = JdbcUtils.getConn(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Route route = new Route(
                        rs.getInt("ID_ChuyenXe"),
                        rs.getString("tenCX"),
                        rs.getDouble("giaChuyen"),
                        rs.getInt("ID_benDen"),
                        rs.getInt("ID_benDi")
                );
                routes.add(route);
            }
        }
        return routes;
    }

    public boolean checkRouteExists(int diID, int denID) throws SQLException {
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement ps = conn.prepareStatement("SELECT * FROM chuyenxe WHERE ID_benDi = ? AND ID_benDen = ?")) {
            ps.setInt(1, diID);
            ps.setInt(2, denID);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public List<Route> getRoute(String kw) throws SQLException {
        List<Route> results = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM chuyenxe";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE tenCX like concat('%', ?, '%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Route s = new Route(rs.getInt("ID_Chuyenxe"), rs.getString("tenCX"), rs.getDouble("giaChuyen"), rs.getInt("ID_benDen"), rs.getInt("ID_benDi"));
                results.add(s);
            }
        }

        return results;
    }

    public List<Route> getRouteByDesIdByDepId(String desId, String depId, Date orderDate) throws SQLException {
        List<Route> route = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT cx.ID_ChuyenXe, cx.tenCX, cx.ID_benDen, cx.ID_benDi, cx.giaChuyen\n"
                    + "FROM chuyenxe cx\n"
                    + "INNER JOIN chuyenxe_xe cxx ON cx.ID_ChuyenXe = cxx.ID_ChuyenXe\n";
            if (desId != null && !desId.isEmpty() && depId != null && !depId.isEmpty()) {
                sql += "WHERE cx.ID_benDen = ? AND cx.ID_benDi = ? AND DATE(cxx.gioKhoiHanh) = ?";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (desId != "null" && !desId.isEmpty() && depId != "null" && !depId.isEmpty()) {
                stm.setInt(1, Integer.valueOf(desId));
                stm.setInt(2, Integer.valueOf(depId));
                stm.setDate(3, orderDate);
            }
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Route r = new Route(
                        rs.getInt("ID_ChuyenXe"),
                        rs.getString("tenCX"),
                        rs.getDouble("giaChuyen"),
                        rs.getInt("ID_benDen"),
                        rs.getInt("ID_benDi"));
                route.add(r);
            }
        }
        return route;
    }

    public boolean deleteRoute(Integer id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM chuyenxe WHERE ID_ChuyenXe=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);

            return stm.executeUpdate() > 0;
        }
    }

    public boolean updateRouteById(String routeName, Integer routeID, Integer benDen, Integer benDi, Double Gia) {
        String sql = "UPDATE chuyenxe SET TenCX = ?, ID_benDen = ?, ID_benDi = ?, giaChuyen = ? WHERE ID_ChuyenXe = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareCall(sql)) {
            stm.setString(1, routeName);
            stm.setInt(2, benDen);
            stm.setInt(3, benDi);
            stm.setDouble(4, Gia);
            stm.setInt(5, routeID);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating station name for ID " + routeID, ex);
            return false;
        }
    }

    public boolean addRoute(String tenCX, Integer BenDi, Integer BenDen, Double gia) {
        String sql = "INSERT INTO chuyenxe(TenCX, ID_BenDi, ID_BenDen,giaChuyen) VALUES (?,?,?,?)";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            statement.setString(1, tenCX);
            statement.setInt(2, BenDi);
            statement.setInt(3, BenDen);
            statement.setDouble(4, gia);

            statement.addBatch();
            statement.executeBatch();

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getNameStation() throws SQLException {
        List<String> results = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT tenBen FROM benxe";
            PreparedStatement stm = conn.prepareCall(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String name = rs.getString("tenBen");
                results.add(name);
            }
        }
        return results;
    }

    public Integer getStationIDbyname(String name) throws SQLException {
        Integer a = null;
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT ID_Ben FROM benxe WHERE TenBen= ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                a = rs.getInt("ID_Ben");
            }
        }
        return a;
    }

    public Integer getRouteIDbyname(String name) throws SQLException {
        Integer a = null;
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT ID_ChuyenXe FROM chuyenxe WHERE tenCX= ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                a = rs.getInt("ID_ChuyenXe");
            }
        }
        return a;
    }

    public Route getOneRouteByID(Integer ID) throws SQLException {
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement("SELECT * FROM chuyenxe WHERE ID_ChuyenXe = ?")) {
            stm.setInt(1, ID);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Route s = new Route(rs.getInt("ID_Chuyenxe"), rs.getString("tenCX"), rs.getDouble("giaChuyen"), rs.getInt("ID_benDen"), rs.getInt("ID_benDi"));
                    return s;
                } else {
                    throw new SQLException("No route found with ID " + ID);
                }
            }
        }
    }

    public Timestamp getDepartureTimeByIdRoute(Integer id) throws SQLException {
        String query = "SELECT gioKhoiHanh FROM chuyenxe_xe WHERE ID_ChuyenXe = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getTimestamp("gioKhoiHanh");
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new SQLException("Error finding route with ID " + id, ex);
        }
    }

    public List<Integer> getIdRoute() throws SQLException {
        List<Integer> results = new ArrayList<>();
        String sql = "SELECT ID_ChuyenXe FROM chuyenxe"
                + " ORDER BY ID_ChuyenXe ASC";
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

}
