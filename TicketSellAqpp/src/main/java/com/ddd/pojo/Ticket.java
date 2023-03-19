/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Admin
 */
public class Ticket {
    private Integer ticketId;
    private Date printingDate; // Ngay in

    public Ticket() {
    }

    public Ticket(Integer ticketId, BigDecimal fare, Date printingDate) {
        this.ticketId = ticketId;
        this.printingDate = printingDate;
    }


    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Date getPrintingDate() {
        return printingDate;
    }

    public void setPrintingDate(Date printingDate) {
        this.printingDate = printingDate;
    }

    @Override
    public String toString() {
        return getTicketId().toString(); 
    }
    
    
}
