/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class Route {
    private int routeId;
    private String nameRoute;
    private BigDecimal fare;

    public Route() {
    }

    public Route(int routeId, String nameRoute, BigDecimal fare) {
        this.routeId = routeId;
        this.nameRoute = nameRoute;
        this.fare = fare;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public String getNameRoute() {
        return nameRoute;
    }

    public int getRouteId() {
        return routeId;
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
