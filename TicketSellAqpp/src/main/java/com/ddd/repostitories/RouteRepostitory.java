/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.pojo.Route;
import com.ddd.pojo.Station;
import com.ddd.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public class RouteRepostitory {
    
    public List<Route> getRoutesById(Integer id) throws SQLException {
        List<Route> routes = new ArrayList<>();
        String query = "SELECT ID_ChuyenXe, tenCX, giaChuyen FROM chuyenxe WHERE ID_ChuyenXe = ?";
        
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


//    public List<Route> getRoutetById(Integer ID_ChuyenXe) throws SQLException {
//        List<Route> routes = new ArrayList<>();
//        
//        try (Connection connection = JdbcUtils.getConn()) {
//            String query = "SELECT * FROM chuyenxe WHERE ID_ChuyenXe = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1, ID_ChuyenXe);
//            ResultSet rs = preparedStatement.executeQuery();
//            if (rs.next()) {
//                Route route = new Route(
//                        rs.getInt("ID_ChuyenXe"),
//                        rs.getString("tenCX"),
//                        rs.getBigDecimal("giaChuyen")
//                rs.getString());
//                routes.add(route);
//            }
//        }
//        return routes;
//    }
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
            String sql = "SELECT chuyenxe.ID_ChuyenXe, chuyenxe.tenCX, chuyenxe.giaChuyen, chuyenxe.ID_benDen, chuyenxe.ID_benDi\n"
                    + "FROM chuyenxe\n"
                    + "INNER JOIN chuyenxe_xe ON chuyenxe.ID_ChuyenXe = chuyenxe_xe.ID_ChuyenXe\n";
            if (desId != null && !desId.isEmpty() && depId != null && !depId.isEmpty()) {
                sql += "WHERE chuyenxe.ID_benDen = ? AND chuyenxe.ID_benDi = ? AND chuyenxe_xe.gioKhoiHanh like concat('%',?,'%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (desId != null && !desId.isEmpty() && depId != null && !depId.isEmpty()) {
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

//    private String getStationnamebyID(int stateID) throws SQLException {
//        if (stateID <= 0)
//            return "";
//        try (Connection connection = JdbcUtils.getConn()) {
//            String query = "SELECT COUNT(p.pro_id) as pro_amount " +
//                    "FROM Manufacturer m JOIN Product p ON m.man_id = p.man_id " +
//                    "WHERE m.man_id = ? AND m.man_is_active = TRUE";
//            PreparedStatement prepareStatement = connection.prepareStatement(query);
//            prepareStatement.setInt(1, manId);
//            ResultSet resultSet = prepareStatement.executeQuery();
//            if (resultSet.next())
//                return resultSet.getInt("pro_amount");
//            return 0;
//        }
//    }

}
