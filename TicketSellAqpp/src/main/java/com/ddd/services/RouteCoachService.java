/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.pojo.RouteCoach;
import com.ddd.repostitories.RouteCoachRepostitory;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Admin
 */
public class RouteCoachService {
       private final static RouteCoachRepostitory ROUTE_COACH_REPOSTITORY;

    static {
        ROUTE_COACH_REPOSTITORY = new RouteCoachRepostitory();
    }
    
    public RouteCoach getOneRouteCoachById(Integer RouteId, Integer CoachId) throws SQLException {
        return ROUTE_COACH_REPOSTITORY.getOneRouteCoachById(RouteId, CoachId);
    }
}
