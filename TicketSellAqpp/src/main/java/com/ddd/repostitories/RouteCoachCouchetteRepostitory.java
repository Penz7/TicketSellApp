/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.pojo.RouteCoachCouchette;
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
public class RouteCoachCouchetteRepostitory {

    public List<RouteCoachCouchette> getDataForTableViewBooking(Integer routeId) throws SQLException {
        List<RouteCoachCouchette> data = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConn()) {
            String query = "SELECT chuyenxe.ID_ChuyenXe, chuyenxe.tenCX, xe.TenXe, chuyenxe_xe.gioKhoiHanh,ghe.ID_Ghe, chuyenxe.giaChuyen\n"
                    + "FROM chuyenxe\n"
                    + "JOIN chuyenxe_xe ON chuyenxe.ID_ChuyenXe = chuyenxe_xe.ID_ChuyenXe\n"
                    + "JOIN xe ON chuyenxe_xe.ID_Xe = xe.ID_Xe\n"
                    + "JOIN ghe ON xe.ID_Xe = ghe.ID_Xe\n"
                    + "WHERE chuyenxe.ID_ChuyenXe = '?'";
            if (routeId == null) {
                return data;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, routeId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                RouteCoachCouchette routeCoachCouchette = new RouteCoachCouchette(
                        rs.getInt("ID_ChuyenXe"),
                        rs.getString("tenCX"),
                        rs.getString("TenXe"),
                        rs.getTimestamp("gioKhoiHanh"),
                        rs.getInt("ID_Ghe"),
                        rs.getDouble("giaChuyen"));
                data.add(routeCoachCouchette);
            }
        }
        return data;
    }

}
