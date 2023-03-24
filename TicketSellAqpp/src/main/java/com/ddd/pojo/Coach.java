/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

/**
 *
 * @author Admin
 */
public class Coach {
    private Integer coachId;
    private String coachName;
    private String licensePlates; // bien so xe

    public Coach() {
    }

    public Coach(Integer coachId, String coachName, String licensePlates) {
        this.coachId = coachId;
        this.coachName = coachName;
        this.licensePlates = licensePlates;
    }

    public Integer getCoachId() {
        return coachId;
    }

    public String getCoachName() {
        return coachName;
    }

    public String getLicensePlates() {
        return licensePlates;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public void setLicensePlates(String licensePlates) {
        this.licensePlates = licensePlates;
    }
}
