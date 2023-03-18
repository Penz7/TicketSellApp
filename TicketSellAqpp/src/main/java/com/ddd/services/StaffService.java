/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

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
public class StaffService {

    public Staff getStaffById(Integer staId) throws SQLException {
        try (Connection connection = JdbcUtils.getConn()) {
            String query = "SELECT * FROM Person WHERE pers_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, staId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
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
        try (Connection connection = JdbcUtils.getConn()) {
            String query = "SELECT \n"
                    + "    p.pers_id, \n"
                    + "    p.pers_first_name, \n"
                    + "    p.pers_last_name, \n"
                    + "    p.per_id_card, \n"
                    + "    p.pers_phone_number, \n"
                    + "    p.pers_sex, \n"
                    + "    p.pers_date_of_birth, \n"
                    + "    p.pers_joined_date, \n"
                    + "    s.sta_username \n"
                    + "FROM \n"
                    + "    Staff s \n"
                    + "    INNER JOIN Person p ON s.id = p.pers_id \n"
                    + "WHERE \n"
                    + "    s.sta_username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username.trim());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Staff staff = new Staff();
                staff.setStaUsername(resultSet.getString("sta_username"));
                staff.setPersId(resultSet.getInt("pers_id"));
                staff.setPersFirstName(resultSet.getString("pers_first_name"));
                staff.setPersLastName(resultSet.getString("pers_last_name"));
                staff.setPersIdCard(resultSet.getString("per_id_card"));
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
