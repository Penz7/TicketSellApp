/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

/**
 *
 * @author Admin
 */
public class Couchette {
    private Integer couchetteID;
    private Integer status; // 0 la chua co ai dat, 1 la da co khach dat

    public Couchette() {
    }

    public Couchette(Integer couchetteID, Integer status) {
        this.couchetteID = couchetteID;
        this.status = status;
    }

    public void setCouchetteID(Integer couchetteID) {
        this.couchetteID = couchetteID;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getCouchetteID() {
        return couchetteID;
    }

    @Override
    public String toString() {
        return super.toString(); 
    }
    
}
