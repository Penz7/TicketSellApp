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
import java.util.ArrayList;
import java.util.List;

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
            }

        }
        return null;

    }
}
