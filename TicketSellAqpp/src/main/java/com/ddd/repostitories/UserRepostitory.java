/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.pojo.User;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class UserRepostitory {

    public List<User> getUser(String kw) throws SQLException {
        List<User> results = new ArrayList<>();
        String sql = "SELECT * FROM user";
        if (kw != null && !kw.isEmpty()) {
            sql += " WHERE TenBen LIKE ?";
            kw = "%" + kw + "%";
        }

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User s = new User(
                        rs.getInt("user_id"),
                        rs.getString("user_fullname"),
                        rs.getString("user_id_card"),
                        rs.getString("user_phone_number"),
                        rs.getDate("user_date_of_birth"),
                        rs.getDate("user_date_join"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_address"),
                        rs.getInt("role_id")
                );
                results.add(s);
            }
        }

        return results;
    }
    
    public List<User> getStaff(String kw) throws SQLException {
        List<User> results = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE role_id = 2";
        if (kw != null && !kw.isEmpty()) {
            sql += " AND user_fullname LIKE CONCAT('%', ?, '%')";
        }
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User s = new User(
                        rs.getInt("user_id"),
                        rs.getString("user_fullname"),
                        rs.getString("user_id_card"),
                        rs.getString("user_phone_number"),
                        rs.getDate("user_date_of_birth"),
                        rs.getDate("user_date_join"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_address"),
                        rs.getInt("role_id")
                );
                results.add(s);
            }
        }
        return results;
    }

    public boolean updateUserById(String user_fullname, String user_id_card, String user_phone_number, Date user_date_of_birth, String username, String password, String user_address, Integer user_id) {
        String sql = "UPDATE user SET user_fullname = ?, user_id_card = ?, user_phone_number = ?, user_date_of_birth = ?, username = ?,password = ?, user_address =?  WHERE user_id = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareCall(sql)) {
            stm.setString(1, user_fullname);
            stm.setString(2, user_id_card);
            stm.setString(3, user_phone_number);
            stm.setDate(4, user_date_of_birth);
            stm.setString(5, username);
            stm.setString(6, password);
            stm.setString(7, user_address);
            stm.setInt(8, user_id);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating station name for ID " + user_id, ex);
            return false;
        }
    }

    public boolean deleteStaff(String id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM user WHERE user_id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id);

            return stm.executeUpdate() > 0;
        }
    }

    public boolean addUser(String user_fullname, String user_id_card, String user_phone_number, Date user_date_of_birth, String username, String password, String user_address) {
        String sql = "INSERT INTO user (user_fullname,user_id_card,user_phone_number,user_date_of_birth,user_date_join,username,password,user_address,role_id ) VALUES (?,?,?,?,?,?,?,?,?)";
        Connection conn = null;

        try {
            Integer role_id = 2;
            java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());
            conn = JdbcUtils.getConn();
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, user_fullname);
            statement.setString(2, user_id_card);
            statement.setString(3, user_phone_number);
            statement.setDate(4, user_date_of_birth);
            statement.setDate(5, date);
            statement.setString(6, username);
            statement.setString(7, password);
            statement.setString(8, user_address);
            statement.setInt(9, role_id);
 

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
    
     public User getOneUserByID(Integer ID) throws SQLException {
    try (Connection conn = JdbcUtils.getConn();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user WHERE user_id = ?")) {
        stm.setInt(1, ID);
        try (ResultSet rs = stm.executeQuery()) {
            if (rs.next()) {
                User s = new User(
                        rs.getInt(ID),
                        rs.getString("user_fullname"),
                        rs.getString("user_id_card"),
                        rs.getString("user_phone_number"),
                        rs.getDate("user_date_of_birth"),
                        rs.getDate("user_date_join"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_address"),
                        rs.getInt("role_id")
                );
                return s;
            } else {
                throw new SQLException("No user found with ID " + ID);
            }
        }
    }
}
}
