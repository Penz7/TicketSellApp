/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

/**
 *
 * @author admin
 */
public class Couchette {
    private Integer CouchetteId;
    private Boolean Status;
    private Integer CouchId;

    public Couchette(Integer ID_Ghe, Boolean TinhTrangGhe, Integer ID_Xe) {
        this.CouchetteId = ID_Ghe;
        this.Status = TinhTrangGhe;
        this.CouchId = ID_Xe;
    }

    public Couchette() {
    }

    public Integer getID_Ghe() {
        return CouchetteId;
    }

    public void setID_Ghe(Integer ID_Ghe) {
        this.CouchetteId = ID_Ghe;
    }

    public Integer getCouchId() {
        return CouchId;
    }

    public void setCouchId(Integer CouchId) {
        this.CouchId = CouchId;
    }

    public Boolean getTinhTrangGhe() {
        return Status;
    }

    public void setTinhTrangGhe(Boolean TinhTrangGhe) {
        this.Status = TinhTrangGhe;
    }

    public Integer getCouchetteId() {
        return CouchetteId;
    }

    public void setCouchetteId(Integer CouchetteId) {
        this.CouchetteId = CouchetteId;
    }

    public void setStatus(Boolean Status) {
        this.Status = Status;
    }

    public Boolean getStatus() {
        return Status;
    }

    
    
    
}
