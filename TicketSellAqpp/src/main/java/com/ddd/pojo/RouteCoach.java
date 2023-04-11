/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

import java.sql.Time;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class RouteCoach {
    private Integer routeId;
    private Integer coachId;
    private Timestamp departureTime; // giờ khởi hành

    public RouteCoach() {
    }

    public RouteCoach(Integer routeId, Integer coachId, Timestamp departureTime) {
        this.routeId = routeId;
        this.coachId = coachId;
        this.departureTime = departureTime;
    }

    public Integer getCoachId() {
        return coachId;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

   
    
    
}
