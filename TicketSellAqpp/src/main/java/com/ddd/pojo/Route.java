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
    private BigDecimal fare;
    private Integer destinationId;
    private Integer departureId;

    public Route() {
    }

    public Route(int routeId, String nameRoute, BigDecimal fare, Integer destinationId, Integer departureId) {
        this.routeId = routeId;
        this.nameRoute = nameRoute;
        this.fare = fare;
        this.destinationId = destinationId;
        this.departureId = departureId;
    }

    

    public Integer getDepartureId() {
        return departureId;
    }
    

    public Integer getDestinationId() {
        return destinationId;
    }

    public String getNameRoute() {
        return nameRoute;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setDepartureId(Integer departureId) {
        this.departureId = departureId;
    }

    public void setDestinationId(Integer destination) {
        this.destinationId = destination;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
    
    
}
