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
    private Couchette couchette;
    private User customer;
    private User staff;
    private Route route;
    
    public Ticket() {
    }

    public Ticket(Integer ticketId, BigDecimal fare, Timestamp printingDate) {
        this.ticketId = ticketId;
        this.printingDate = printingDate;
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

    public Couchette getCouchette() {
        return couchette;
    }

    public void setCouchette(Couchette couchette) {
        this.couchette = couchette;
    }


    public void setRoute(Route route) {
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }

    public User getCustomer() {
        return customer;
    }

    public User getStaff() {
        return staff;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void setStaff(User staff) {
        this.staff = staff;
    }
    
    
    
    @Override
    public String toString() {
        return getTicketId().toString(); 
    }
    
    
}
