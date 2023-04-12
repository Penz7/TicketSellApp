/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Admin
 */
public class Route {

    private int routeId;
    private String nameRoute;
    private Double fare;
    private Integer destinationID;
    private Integer departureID;

    public Route(int routeId, String nameRoute, Double fare, Integer destinationID, Integer departureID) {
        this.routeId = routeId;
        this.nameRoute = nameRoute;
        this.fare = fare;
        this.destinationID = destinationID;
        this.departureID = departureID;
    }
    
    public Route() {
    }

    public int getRouteId() {
        return routeId;
    }


    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public Integer getDepartureID() {
        return departureID;
    }

    public void setDepartureID(Integer departureID) {
        this.departureID = departureID;
    }

    public Integer getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(Integer destinationID) {
        this.destinationID = destinationID;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public String getNameRoute() {
        return nameRoute;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
    } 

}
