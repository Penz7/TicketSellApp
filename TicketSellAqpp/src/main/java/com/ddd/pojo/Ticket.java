/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class Ticket {
    private Integer ticketId;
    private BigDecimal fare; // gia ve

    public Ticket() {
    }

    public Ticket(Integer ticketId, BigDecimal fare) {
        this.ticketId = ticketId;
        this.fare = fare;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public BigDecimal getFare() {
        return fare;
    }
    
    
}
