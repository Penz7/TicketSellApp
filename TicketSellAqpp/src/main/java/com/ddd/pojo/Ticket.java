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
    
    public Ticket() {
    }
    
    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Timestamp getPrintingDate() {
        return printingDate;
    }

    public void setPrintingDate(Timestamp printingDate) {
        this.printingDate = printingDate;
    }

    public Integer getCouchette() {
        return couchetteId;
    }

    public void setCouchette(Integer couchette) {
        this.couchetteId = couchette;
    }


    public void setRoute(Integer route) {
        this.routeId = route;
    }

    public Integer getRoute() {
        return routeId;
    }

    public Integer getCustomer() {
        return customerId;
    }

    public Integer getStaff() {
        return staffId;
    }

    public void setCustomer(Integer customer) {
        this.customerId = customer;
    }

    public void setStaff(Integer staff) {
        this.staffId = staff;
    }

    public Ticket(Timestamp printingDate, Integer couchette, Integer customer, Integer staff, Integer route) {
        this.printingDate = printingDate;
        this.couchetteId = couchette;
        this.customerId = customer;
        this.staffId = staff;
        this.routeId = route;
    }
    
    
    
    @Override
    public String toString() {
        return getTicketId().toString(); 
    }
    
    
}
