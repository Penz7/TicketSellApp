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
    
    public List<Couchette> getSeatByCoadID(Integer kw) throws SQLException {
        return COUCHETTE_REPOSTITORY.getSeatByCoadID(kw);
    }
    
    public Couchette getOneCouchetteByID(Integer ID) throws SQLException {
        return COUCHETTE_REPOSTITORY.getOneCouchetteByID(ID);
    }
    
    public List<Couchette> getAllSeat() throws SQLException {
        return COUCHETTE_REPOSTITORY.getAllSeat();
    }
    
    public boolean updateStatusSeat(Integer seatId, boolean status) {
        return COUCHETTE_REPOSTITORY.updateStatusSeat(seatId, status);
    }
}