/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.utils.JdbcUtils;
import com.ddd.utils.MD5Utils;
import com.ddd.pojo.Staff;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class SignInService {

     public Staff getAccount(String username, String password) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM Staff,Person  WHERE Staff.sta_id = Person.pers_id"
                    + " AND Staff.sta_username = ? "
                    + " AND Staff.sta_password =  ? "
                    + " AND Person.pers_is_active = true";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            
            if(rs.next()){
                Staff staff = new Staff();
                staff.setStaUsername(rs.getString("sta_username"));
                staff.setStaPassword(rs.getString("sta_password"));
                staff.setStaIsAdmin(rs.getBoolean("sta_is_admin"));
                staff.setPersId(rs.getInt("sta_id"));
                return  staff;
            }
            else
                return null;
        }
    }
     
     // Lay thong tin tai khoan
    public Staff getAccountMD5(String username, String password) throws SQLException {
        try {
            password = MD5(password);
        } catch (Exception ex) {
            Logger.getLogger(Staff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getAccount(username, password);
    }

    // Lấy mã MD5 ---- Phục vụ viết testcase
    public String MD5(String text){
        return MD5Utils.getMd5(text);
    }
}
