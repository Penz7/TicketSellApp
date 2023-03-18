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
public class StaffRepostitory {
    //Lấy thông tin của người
    public Staff getStaffById(Integer staId) throws SQLException {
        try(Connection connection = JdbcUtils.getConn()){
            String query = "SELECT * FROM Person WHERE pers_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, staId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Staff staff = new Staff();
                staff.setPersFirstName(resultSet.getString("pers_first_name"));
                staff.setPersLastName(resultSet.getString("pers_last_name"));
                staff.setPersId(resultSet.getInt("pers_id"));
                return staff;
            }
        }
        return null;
    }
    
     // Lấy thông tin nhân viên dựa vào username
    public Staff getStaffByUsername(String username) throws SQLException {
        try(Connection connection = JdbcUtils.getConn()){
            String query = "SELECT p.pers_id, p.pers_first_name, p.pers_last_name, p.pers_id_card, p.pers_phone_number, " +
                    " p.pers_sex, p.pers_date_of_birth, p.pers_joined_date, s.sta_username " +
                    "FROM Staff s JOIN Person p ON s.sta_id = p.pers_id " +
                    "WHERE s.sta_username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username.trim());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Staff staff = new Staff();
                staff.setStaUsername(resultSet.getString("sta_username"));
                staff.setPersId(resultSet.getInt("pers_id"));
                staff.setPersFirstName(resultSet.getString("pers_first_name"));
                staff.setPersLastName(resultSet.getString("pers_last_name"));
                staff.setPersIdCard(resultSet.getString("pers_id_card"));
                staff.setPersPhoneNumber(resultSet.getString("pers_phone_number"));
                staff.setPersSex(resultSet.getByte("pers_sex"));
                staff.setPersDateOfBirth(resultSet.getDate("pers_date_of_birth"));
                staff.setPersJoinedDate(resultSet.getDate("pers_joined_date"));
                return staff;
            }
        }
        return null;
    }
}
