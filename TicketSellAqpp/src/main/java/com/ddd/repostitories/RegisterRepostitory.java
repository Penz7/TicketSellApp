/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author Admin
 */
public class RegisterRepostitory {

    public boolean addAccount(String fullName, String idCard, String phoneNumber, String username, String password, String address) {
        LocalDate currentDate = LocalDate.now();
        String sql = "INSERT INTO user(user_fullname, user_id_card, user_phone_number, user_date_join, username, password, user_address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = JdbcUtils.getConn();
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, fullName);
            statement.setString(2, idCard);
            statement.setString(3, phoneNumber);
            statement.setDate(4, Date.valueOf(currentDate));
            statement.setString(5, username);
            statement.setString(6, password);
            statement.setString(7, address);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            // Handle the error and log it
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean checkAccountUsername(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return (count == 0); // trả về true nếu username mới không trùng với username trong database
                }
            }
        } catch (SQLException e) {
            // Xử lý lỗi và ghi log
            e.printStackTrace();
        }
        return false;
    }
}
