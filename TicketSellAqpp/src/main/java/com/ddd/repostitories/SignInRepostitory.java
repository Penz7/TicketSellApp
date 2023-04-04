/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.utils.JdbcUtils;
import com.ddd.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class SignInRepostitory {

    public User getAccount(String username, String password) throws SQLException {
        try (Connection connection = JdbcUtils.getConn()) {
            String query = "SELECT *\n" +
"                 FROM User \n" +
"                  \n" +
"                    WHERE user.username = ? \n" +
"                    AND user.password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole_id(rs.getInt("role_id"));
                user.setUser_fullname(rs.getString("user_fullname"));
                user.setUser_id_card(rs.getString("user_id_card"));
                user.setUser_date_join(rs.getDate("user_date_join"));
                user.setUser_address(rs.getString("user_address"));
                user.setUser_phone_number(rs.getString("user_phone_number"));
                user.setUser_id(rs.getInt("user_id"));
                return user;
            } else {
                return null;
            }
        }
    }
}
