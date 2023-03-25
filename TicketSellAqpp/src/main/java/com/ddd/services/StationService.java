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

    // Lấy các bến xe bởi tên
    public Station getStationByName(String name) throws SQLException {
        return STATION_REPOSITORY.getStationByName(name);
    }

    // Lấy tất cả các bến xe có trong hệ thống
    public List<Station> getAllStation() throws SQLException {
        return STATION_REPOSITORY.getAllStation();
    }

    //Lấy các bến xe bởi từ khóa 
    public List<Station> getStations(String kw) throws SQLException {
        return STATION_REPOSITORY.getStations(kw);
    }

    //Xóa bến xe bởi id
    public boolean deleteStation(String id) throws SQLException {
        return STATION_REPOSITORY.deleteStation(id);
    }

    // Cập nhật lại bến xe bởi tên và id
    public boolean updateStationNameById(String id, String stationName) {
        return STATION_REPOSITORY.updateStationNameById(id, stationName);
    }

    // Thêm mới một bến xe bởi tên
    public boolean addStation(String stationName) {
        return STATION_REPOSITORY.addStation(stationName);
    }
    
    // Kiểm tra bằng tên bến, xem có tồn tại tên bến như vậy không
     public boolean isExistStationByName(String name) throws SQLException {
         return STATION_REPOSITORY.isExistStationByName(name);
     }
}
