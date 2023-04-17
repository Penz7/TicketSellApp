/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class Ticket {
    private Integer ticketId;
    private Timestamp printingDate; // Ngay in
    private Integer couchetteId;
    private Integer customerId;
    private Integer staffId;
    private Integer routeId;
    private boolean isConfirm;

    public Ticket() {
    }

    public Ticket(Timestamp printingDate, Integer couchetteId, Integer customerId, Integer staffId, Integer routeId, boolean isConfirm) {
        this.ticketId = ticketId;
        this.printingDate = printingDate;
        this.couchetteId = couchetteId;
        this.customerId = customerId;
        this.staffId = staffId;
        this.routeId = routeId;
        this.isConfirm = isConfirm;
    }

    public Integer getCouchetteId() {
        return couchetteId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Timestamp getPrintingDate() {
        return printingDate;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public boolean isIsConfirm() {
        return isConfirm;
    }

    public void setCouchetteId(Integer couchetteId) {
        this.couchetteId = couchetteId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setIsConfirm(boolean isConfirm) {
        this.isConfirm = isConfirm;
    }

    public void setPrintingDate(Timestamp printingDate) {
        this.printingDate = printingDate;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }
    
    
}
