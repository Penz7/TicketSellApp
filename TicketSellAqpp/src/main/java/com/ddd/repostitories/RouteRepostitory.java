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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class RouteRepostitory {

    public List<Route> getRoutetById(Integer ID_ChuyenXe) throws SQLException {
        List<Route> routes = new ArrayList<>();
        
        try (Connection connection = JdbcUtils.getConn()) {
            String query = "SELECT * FROM chuyenxe WHERE ID_ChuyenXe = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ID_ChuyenXe);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Route route = new Route(
                        rs.getInt("ID_ChuyenXe"),
                        rs.getString("tenCX"),
                        rs.getBigDecimal("giaChuyen")
                rs.getString());
                routes.add(route);
            }
        }
        return routes;
    }
    
    
}