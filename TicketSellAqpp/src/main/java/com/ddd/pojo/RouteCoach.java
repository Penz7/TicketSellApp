/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

import java.sql.Time;

/**
 *
 * @author Admin
 */
public class RouteCoach {
    private Route route;
    private Coach coach;
    private Time departureTime; // giờ khởi hành

    public RouteCoach() {
    }

    public RouteCoach(Route route, Coach coach, Time departureTime) {
        this.route = route;
        this.coach = coach;
        this.departureTime = departureTime;
    }

    public Coach getCoach() {
        return coach;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public Route getRoute() {
        return route;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }
    
    
}
