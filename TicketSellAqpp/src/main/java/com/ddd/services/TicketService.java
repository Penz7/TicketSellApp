/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.pojo.Ticket;
import com.ddd.repostitories.TicketRepostitory;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class TicketService {
    private final static TicketRepostitory TICKET_REPOSTITORY;
    
    static {
        TICKET_REPOSTITORY = new TicketRepostitory();
    }
    
    public Ticket getTicketById(Integer ticketId) throws SQLException{
        return TICKET_REPOSTITORY.getTicketById(ticketId);
    }
    
     public boolean checkUserCustomer(Integer user_id) throws SQLException{
        return TICKET_REPOSTITORY.isUserCustomer(user_id);
    }
}
