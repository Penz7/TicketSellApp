/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.pojo.Route;
import com.ddd.repostitories.RouteRepostitory;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
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

     public boolean checkRouteExists(int diID, int denID) throws SQLException {
        return ROUTE_REPOSITORY.checkRouteExists(diID, denID);
    }

    public List<Route> getRoute(String kw) throws SQLException {
        return ROUTE_REPOSITORY.getRoute(kw);
    }

    // Lấy danh sách các chuyến xe bởi id bến đến, id bến đi và ngày đặt
    public List<Route> getRouteByDesIdByDepId(String desId, String depId, Date orderDate) throws SQLException {
        return ROUTE_REPOSITORY.getRouteByDesIdByDepId(desId, depId, orderDate);
    }

    public boolean deleteRoute(Integer id) throws SQLException {
        return ROUTE_REPOSITORY.deleteRoute(id);
    }

    public boolean updateRouteById(String routeName, Integer routeID, Integer benDen, Integer benDi, Double Gia) {
        return ROUTE_REPOSITORY.updateRouteById(routeName, routeID, benDen, benDi, Gia);
    }

    public boolean addRoute(String tenCX, Integer BenDi, Integer BenDen, Double gia) {
        return ROUTE_REPOSITORY.addRoute(tenCX, BenDi, BenDen, gia);
    }
    
    public List<String> getNameStation() throws SQLException {
         return ROUTE_REPOSITORY.getNameStation();
    }

    public Integer getStationIDbyname(String name) throws SQLException {
        return ROUTE_REPOSITORY.getStationIDbyname( name);
    }
    
    public Timestamp getDepartureTimeByIdRoute(Integer id) throws SQLException {
        return ROUTE_REPOSITORY.getDepartureTimeByIdRoute( id);
    }

    public Route getOneRouteByID(Integer ID) throws SQLException {
        return ROUTE_REPOSITORY.getOneRouteByID(ID);
    }

    public List<Integer> getIdRoute() throws SQLException {
        return ROUTE_REPOSITORY.getIdRoute();
    }

}
