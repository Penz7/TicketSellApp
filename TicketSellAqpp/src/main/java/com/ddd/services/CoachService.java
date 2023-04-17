/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.pojo.Coach;
import com.ddd.repostitories.CoachRepostitory;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
public class CoachService {

    private final static CoachRepostitory COACH_REPOSTITORY = new CoachRepostitory();

    public List<Coach> getCoach(String kw) throws SQLException {
        return COACH_REPOSTITORY.getCoach(kw);
    }

    public boolean deleteCoach(Integer id) throws SQLException {
        return COACH_REPOSTITORY.deleteCoach(id);
    }

    public boolean updateCoachById(String tenXe, String BienSo, Integer ID_Xe) {
        return COACH_REPOSTITORY.updateCoachById(tenXe, BienSo, ID_Xe);
    }

    public boolean addCoach(String tenXe, String BienSo) {
        return COACH_REPOSTITORY.addCoach(tenXe, BienSo);
    }
    
     public Integer getCoachIDbyname(String name) throws SQLException {
          return COACH_REPOSTITORY.getCoachIDbyname(name);
     }
     
      public Coach getCoachByID(Integer ID) throws SQLException {
          return COACH_REPOSTITORY.getCoachByID( ID);
      }

}
