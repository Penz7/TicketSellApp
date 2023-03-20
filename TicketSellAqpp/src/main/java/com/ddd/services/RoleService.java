/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.pojo.Role;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class RoleService {
    public List<Role> getRoles(String kw) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM role WHERE name like  concat('%', ?, '%') order by id_role");
            if (kw == null)
                kw = "";
            stm.setString(1, kw);
            ResultSet rs = stm.executeQuery();
            List<Role> roles = new ArrayList<>();

            while (rs.next()){
                int id = rs.getInt("id_role");
                String name = rs.getString("name_role");
                roles.add(new Role(id, name));
            }
            return roles;
        }
    }
    
     public  Role getRoleById(int roleId) throws  SQLException{
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM role WHERE id_role=?");
            stm.setInt(1, roleId);

            ResultSet rs = stm.executeQuery();
            Role r = null;
            if (rs.next()){
                r = new Role();
                r.setId_role(rs.getInt("id_role"));
                r.setName_role(rs.getString("name_role"));
            }
            return r;
        }
    }
}
