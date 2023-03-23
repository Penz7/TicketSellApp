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

/**
 *
 * @author Admin
 */
public class RouteRepostitory {

    public <List> Route getRoutetByDesByDepByDate(String Destination, String Departure, Date OrderDay) throws SQLException {
        List<Route> results = new ArrayList<>();

        try (Connection connection = JdbcUtils.getConn()) {
            String query = "SELECT cx.tenCX, bxd.TenBen as BenDi, bxd.TenBen as BenDen, cx.giaChuyen\n";
            if (Destination != null && !Destination.isEmpty() && Departure != null && !Departure.isEmpty() && OrderDay != null) {
                query += "FROM chuyenxe cx\n"
                        + "JOIN chuyenxe_xe cxx ON cx.ID_ChuyenXe = cxx.ID_ChuyenXe\n"
                        + "JOIN BenXe bxd ON cx.ID_BenDi = bxd.ID_ben\n"
                        + "JOIN BenXe bxd ON cx.ID_BenDen = bxd.ID_ben\n"
                        + "WHERE bxd.TenBen = '?' AND bxd.TenBen = '?' AND cxx.gioKhoiHanh = '?'";
            }

            PreparedStatement stm = connection.prepareCall(query);
            if (Destination != null && !Destination.isEmpty() && Departure != null && !Departure.isEmpty() && OrderDay != null) {
                stm.setString(1, Destination);
                stm.setString(2, Departure);
                stm.setDate(3, OrderDay);
            }

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Route route = new Route(// cho nay phai truyen routeId, nameRoute, fare);
                results.add(route);
            }
        }
        return results;
    }
}
