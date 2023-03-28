/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class RouteCoachCouchette {
    private Integer routeID;
    private String routeName;
    private String coachName;
    private Timestamp departureTime;
    private Integer couchetteID;
    private Double fare;

    public RouteCoachCouchette() {
    }

    public RouteCoachCouchette(Integer routeID, String routeName, String coachName, Timestamp departureTime, Integer couchetteID, Double fare) {
        this.routeID = routeID;
        this.routeName = routeName;
        this.coachName = coachName;
        this.departureTime = departureTime;
        this.couchetteID = couchetteID;
        this.fare = fare;
    }

    public String getCoachName() {
        return coachName;
    }

    public Integer getCouchetteID() {
        return couchetteID;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public Double getFare() {
        return fare;
    }

    public Integer getRouteID() {
        return routeID;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public void setCouchetteID(Integer couchetteID) {
        this.couchetteID = couchetteID;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public void setRouteID(Integer routeID) {
        this.routeID = routeID;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
}
