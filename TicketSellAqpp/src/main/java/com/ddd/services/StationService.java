/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.pojo.Station;
import com.ddd.repostitories.StationRepostitory;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Admin
 */
public class StationService {
     private final static StationRepostitory STATION_REPOSITORY;

    static {
        STATION_REPOSITORY = new StationRepostitory();
    }
    
    // Lấy các bến xe bởi từ khóa
    public Station getStationByName(String kw) throws SQLException {
        return STATION_REPOSITORY.getStationByName(kw);
    }
    
    // Lấy các bến xe có trong hệ thống
    public List<Station> getAllStation() throws SQLException {
        return STATION_REPOSITORY.getAllStation();
    }
    
}
