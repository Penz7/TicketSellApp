/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.pojo.RouteCoachCouchette;
import com.ddd.repostitories.RouteCoachCouchetteRepostitory;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Admin
 */
public class RouteCoachCouchetteService {
    private final static RouteCoachCouchetteRepostitory ROUCOACOU_REPOSITORY;

    static {
        ROUCOACOU_REPOSITORY = new RouteCoachCouchetteRepostitory();
    }
    
     public List<RouteCoachCouchette> getDataForTableViewBooking(Integer routeId) throws SQLException {
         return ROUCOACOU_REPOSITORY.getDataForTableViewBooking(routeId);
     }
}
