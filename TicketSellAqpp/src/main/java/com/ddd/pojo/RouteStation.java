/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

/**
 *
 * @author admin
 */
public class RouteStation {
  private int routeId;
    private String nameRoute;
    private Double fare;
     private Integer stationId;
    private String stationName;

    public RouteStation(int routeId, String nameRoute, Double fare, Integer stationId, String stationName) {
        this.routeId = routeId;
        this.nameRoute = nameRoute;
        this.fare = fare;
        this.stationId = stationId;
        this.stationName = stationName;
    }

    public RouteStation() {
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
    }

    public String getNameRoute() {
        return nameRoute;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
    
    

    
  
    
    
}
