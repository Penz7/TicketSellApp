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
    private Integer OrderOfCouchette;

    public Couchette(Integer CouchetteId, Boolean Status, Integer CouchId, Integer OrderOfCouchette) {
        this.CouchetteId = CouchetteId;
        this.Status = Status;
        this.CouchId = CouchId;
        this.OrderOfCouchette = OrderOfCouchette;
    }

    public Couchette() {
    }

    public Integer getCouchId() {
        return CouchId;
    }

    public void setCouchId(Integer CouchId) {
        this.CouchId = CouchId;
    }

    public Integer getCouchetteId() {
        return CouchetteId;
    }

    public void setCouchetteId(Integer CouchetteId) {
        this.CouchetteId = CouchetteId;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean Status) {
        this.Status = Status;
    }

    public Integer getOrderOfCouchette() {
        return OrderOfCouchette;
    }

    public void setOrderOfCouchette(Integer OrderOfCouchette) {
        this.OrderOfCouchette = OrderOfCouchette;
    }

   
    
    
    
    
}
