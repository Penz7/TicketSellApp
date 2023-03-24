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
    private Station destination;
    private Station departure;
    private Coach coachName;
    private RouteCoach departureTime;
    private Couchette couchette;

    public Route() {
    }

    public BigDecimal getFare() {
        return fare;
    }

    public Coach getCoachName() {
        return coachName;
    }

    public Couchette getCouchette() {
        return couchette;
    }

    public RouteCoach getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(RouteCoach departureTime) {
        this.departureTime = departureTime;
    }

    public void setCoachName(Coach coachName) {
        this.coachName = coachName;
    }

    public void setCouchette(Couchette couchette) {
        this.couchette = couchette;
    }

    
    
    public Route(int routeId, String nameRoute, BigDecimal fare, Station destination, Station departure, Coach coachName, RouteCoach departureTime, Couchette couchette) {
        this.routeId = routeId;
        this.nameRoute = nameRoute;
        this.fare = fare;
        this.destination = destination;
        this.departure = departure;
        this.coachName = coachName;
        this.departureTime = departureTime;
        this.couchette = couchette;
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

    public Station getDeparture() {
        return departure;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDeparture(Station departure) {
        this.departure = departure;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

}
