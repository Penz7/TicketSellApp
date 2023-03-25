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
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
                        rs.getBigDecimal("giaChuyen"),
                        rs.getInt("ID_benDen"),
                        rs.getInt("ID_benDi")
                );
                routes.add(route);
            }
        }
        return routes;
    }
    
    public List<Route> getRouteByDesIdByDepId(String desId, String depId, String orderDate) throws SQLException {
        List<Route> route = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT chuyenxe.ID_ChuyenXe, chuyenxe.tenCX, chuyenxe.giaChuyen, chuyenxe.ID_benDen, chuyenxe.ID_benDi\n"
                    + "FROM chuyenxe\n"
                    + "INNER JOIN chuyenxe_xe ON chuyenxe.ID_ChuyenXe = chuyenxe_xe.ID_ChuyenXe\n";
            if (desId != null && !desId.isEmpty() && depId != null && !depId.isEmpty()) {
                sql += "WHERE chuyenxe.ID_benDen = '?' AND chuyenxe.ID_benDi = '?' AND chuyenxe_xe.gioKhoiHanh like concat('%', ?, '%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (desId != null && !desId.isEmpty() && depId != null && !depId.isEmpty()) {
                stm.setString(1, desId=desId.trim());
                stm.setString(2, depId=depId.trim());
                stm.setString(3, orderDate=orderDate.trim());
            }
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Route r = new Route(
                        rs.getInt("ID_ChuyenXe"),
                        rs.getString("tenCX"),
                        rs.getBigDecimal("giaChuyen"),
                        rs.getInt("ID_benDen"),
                        rs.getInt("ID_benDi"));
                route.add(r);
            }
        }
        return route;
    }
}
