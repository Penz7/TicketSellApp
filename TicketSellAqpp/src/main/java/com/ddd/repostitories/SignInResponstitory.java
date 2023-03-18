/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.utils.JdbcUtils;
import com.ddd.pojo.Staff;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class SignInResponstitory {
     
    public Staff getAccount(String username, String password) throws SQLException{
        try (Connection connection = JdbcUtils.getConnection()) {
            String query = "SELECT * FROM Staff,Person  WHERE Staff.id = Person.pers_id"
                    + " AND Staff.sta_username = ? "
                    + " AND Staff.sta_password =  ? "
                    + " AND Person.pers_is_active = true";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                Staff staff = new Staff();
                
                staff.setStaUsername(rs.getString("sta_username"));
                staff.setStaPassword(rs.getString("sta_password"));
                staff.setStaIsAdmin(rs.getBoolean("sta_is_admin"));
                staff.setPersId(rs.getInt("id"));
                return  staff;
            }
            else
                return null;
        }
    }
}
