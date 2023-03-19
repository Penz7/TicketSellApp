/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

/**
 *
 * @author Admin
 */
public class Station {
    private Integer stationId;
    private String stationName;

    public Station() {
    }

    public Station(Integer stationId, String stationName) {
        this.stationId = stationId;
        this.stationName = stationName;
    }

    public Integer getStationId() {
        return stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
    
}
