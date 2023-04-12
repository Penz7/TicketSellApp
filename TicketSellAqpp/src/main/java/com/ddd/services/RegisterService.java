/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.repostitories.RegisterRepostitory;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.ResultSet;

/**
 *
 * @author admin
 */
public class RegisterService {
       private final static RegisterRepostitory REGISTER_REPOSTITORY;

    static {
        REGISTER_REPOSTITORY = new RegisterRepostitory();
    }
    
    public boolean addAccount(String fullName, String idCard, String phoneNumber, String username, String password, String address) {
        return REGISTER_REPOSTITORY.addAccount(fullName, idCard, phoneNumber, username, password, address);
    }
    
    public boolean checkAccountUsername(String username) {
        return REGISTER_REPOSTITORY.checkAccountUsername(username);
    }
}
