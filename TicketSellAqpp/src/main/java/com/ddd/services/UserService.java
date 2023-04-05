/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.pojo.User;
import com.ddd.repostitories.UserRepostitory;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 */
public class UserService {
    
    private final static UserRepostitory USER_REPOSTITORY;
    
    static {
        USER_REPOSTITORY = new UserRepostitory();
    }
    
    public List<User> getUser(String kw) throws SQLException {
        return USER_REPOSTITORY.getUser(kw);
    }
    
    public List<User> getStaff(String kw) throws SQLException {
        return USER_REPOSTITORY.getStaff(kw);
    }
    
    public boolean updateUserById(String user_fullname, String user_id_card, String user_phone_number, java.sql.Date user_date_of_birth, String username, String password, String user_address, Integer user_id) {
        return USER_REPOSTITORY.addUser(user_fullname, user_id_card, user_phone_number, user_date_of_birth, username, password, user_address);
    }
    
    public boolean deleteStaff(String id) throws SQLException {
        return USER_REPOSTITORY.deleteStaff(id);
    }
    
    public boolean addUser(String user_fullname, String user_id_card, String user_phone_number, java.sql.Date user_date_of_birth, String username, String password, String user_address) {
        return USER_REPOSTITORY.addUser(user_fullname, user_id_card, user_phone_number, user_date_of_birth, username, password, user_address);
    }
    
    public User getOneUserByID(Integer ID) throws SQLException {
        return USER_REPOSTITORY.getOneUserByID(ID);
    }
    
    public User getOneUserIdByName(String nameUser) throws SQLException {
        return USER_REPOSTITORY.getOneUserIdByName(nameUser);
    }
//    public List<User> getUser() throws SQLException {
//        try(Connection conn = JdbcUtils.getConn()) {
//            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user");
//           
//            ResultSet rs = stm.executeQuery();
//            
//            List<User> users = new ArrayList<>();
//            while(rs.next()) {
//                int id = rs.getInt("id");
//                String username = rs.getString("username");
//                String password = rs.getString("password");
//                String fullname = rs.getString("fullname");
//                int gender = rs.getByte("gender");
//                Date birth = rs.getDate("dob");
//                String address = rs.getString("address");
//                String phone = rs.getString("phone");
//                int roleId = rs.getInt("role_id");
//                int departmentId = rs.getInt("department_id");
//                Date createdDate = rs.getDate("created_date");
//                
//                users.add(new User(id, username, password, fullname, gender, birth, address, phone, roleId, departmentId, createdDate));
//            }
//            
//            return users;
//        }
//    }

    public User getByUsername(String username) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user WHERE username = ?");
            
            stm.setString(1, username);
            
            ResultSet rs = stm.executeQuery();
            
            User user = new User();
            while (rs.next()) {
                user.setUser_id(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUser_fullname(rs.getString("fullname"));
                user.setUser_date_of_birth(rs.getDate("dob"));
                user.setUser_address(rs.getString("address"));
                user.setUser_phone_number(rs.getString("phone"));
                user.setRole_id(rs.getInt("role_id"));
                user.setUser_date_join(rs.getDate("created_date"));
            }
            
            return user;
        }
    }
}
