/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.pojo.Couchette;
import com.ddd.repostitories.CouchetteRepostitory;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CouchetteService {
     private final static CouchetteRepostitory COUCHETTE_REPOSTITORY;

    static {
        COUCHETTE_REPOSTITORY = new CouchetteRepostitory();
    }
    
    public List<Couchette> getCouchettesByCoachId(Integer coachId) throws SQLException {
        return COUCHETTE_REPOSTITORY.getCouchettesByCoachId(coachId);
    }
    
    public Couchette getOneCouchetteByID(Integer CouchetteId) throws SQLException {
        return COUCHETTE_REPOSTITORY.getOneCouchetteByID(CouchetteId);
    }
    
    public List<Couchette> getAllCouchette() throws SQLException {
        return COUCHETTE_REPOSTITORY.getAllCouchette();
    }
    
    public boolean updateStatusCouchette(Integer seatId, boolean status) {
        return COUCHETTE_REPOSTITORY.updateStatusCouchette(seatId, status);
    }

     public List<Integer> getIdCouchetteByIdRoute(Integer routeID) throws SQLException {
        return COUCHETTE_REPOSTITORY.getIdCouchetteByIdRoute(routeID);
    }
     
     public List<Boolean> getStatusCouchettebyIDRoute(Integer idRoute) throws SQLException {
         return COUCHETTE_REPOSTITORY.getStatusCouchettebyIDRoute(idRoute);
     }
}
