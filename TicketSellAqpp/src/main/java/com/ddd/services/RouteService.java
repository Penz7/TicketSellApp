/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.pojo.Route;
import com.ddd.repostitories.RouteRepostitory;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Admin
 */
public class RouteService {

    private final static RouteRepostitory ROUTE_REPOSITORY;

    static {
        ROUTE_REPOSITORY = new RouteRepostitory();
    }

    
    //Lấy các chuyến xe bởi id
    public List<Route> getRoutesById(Integer id) throws SQLException {
        return ROUTE_REPOSITORY.getRoutesById(id);
    }
    
    // Lấy danh sách các chuyến xe bởi id bến đến, id bến đi và ngày đặt
    public List<Route> getRouteByDesIdByDepId(String desId, String depId, String orderDate) throws SQLException {
        return ROUTE_REPOSITORY.getRouteByDesIdByDepId(desId, depId, orderDate);
    }
}
