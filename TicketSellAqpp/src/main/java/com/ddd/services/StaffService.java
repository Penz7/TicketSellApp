/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.pojo.Staff;
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
public class StaffService {
    
   private static final String PERS_ID_COLUMN = "pers_id";
   private static final String PERS_FIRST_NAME_COLUMN = "pers_first_name";
   private static final String PERS_LAST_NAME_COLUMN = "pers_last_name";
   

    public Staff getStaffById(Integer staId) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM Person WHERE " + PERS_ID_COLUMN + " = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, staId);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
                Staff staff = new Staff();
                staff.setPersFirstName(resultSet.getString(PERS_FIRST_NAME_COLUMN));
                staff.setPersLastName(resultSet.getString(PERS_LAST_NAME_COLUMN));
                staff.setPersId(resultSet.getInt(PERS_ID_COLUMN));
                return staff;
            }
            return null;
    }
   }

    // lấy danh sách staff theo từ khóa
    public List<Staff> getListStaff(String kw) throws SQLException {
        List<Staff> listStaff = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM Person,Staff " +
                    "WHERE Person.pers_id = Staff.sta_id AND ( pers_last_name LIKE CONCAT(\"%\", ? , \"%\") OR " +
                    "pers_first_name LIKE CONCAT(\"%\", ? , \"%\") )";
            if (kw == null)
                kw = "";
            else kw=kw.trim();
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, kw);
            stm.setString(2, kw);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Staff staff = new Staff();
                int id = rs.getInt("pers_id");
                staff.setPersId(id);
                staff.setPersLastName(rs.getString("pers_last_name"));
                staff.setPersFirstName(rs.getString("pers_first_name"));
                staff.setPersIdCard(rs.getString("pers_id_card"));
                staff.setPersPhoneNumber(rs.getString("pers_phone_number"));
                staff.setSex(rs.getByte("pers_sex")==1 ? "Nam" : "Nữ");
                staff.setPersDateOfBirth(rs.getDate("pers_date_of_birth"));
                staff.setPersJoinedDate(rs.getDate("pers_joined_date"));
                staff.setActiveName(rs.getBoolean("pers_is_active")? "Đang hoạt động" : "Ngưng hoạt động");
                staff.setRoleName(rs.getBoolean("sta_is_admin") ? "Quản trị viên" : "Nhân viên");
                staff.setStaUsername(rs.getString("sta_username"));
                listStaff.add(staff);
            }
        }
        return listStaff;

    }

    
    // kiểm tra username tồn tại chưa
    public boolean isUsername(String username) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT sta_username FROM Staff WHERE sta_username = ? ";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return  true;
            }
        }
        return false;
    }

    // kiểm tra active của tài khoản
    public boolean isActive(String username) throws SQLException {
         try(Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT pers_is_active FROM Person, Staff WHERE Person.pers_id = Staff.sta_id " +
                    " AND sta_username = ? ";
             PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, username);

             ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                if(!(rs.getBoolean("pers_is_active")))
                    return  false;
                return true;
            }
        }
        return  false;
    }


    // cập nhật active = true
    public boolean setActive(String username) throws SQLException {
       try(Connection conn = JdbcUtils.getConn()) {
            String sql = "UPDATE Person, Staff SET pers_is_active = TRUE WHERE Person.pers_id = Staff.sta_id " +
                    " AND sta_username = ?";
           PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, username);
            return stm.executeUpdate() == 1;
        }
    }

    // thêm staff
    public boolean addStaff(Staff staff) throws SQLException {
        try(Connection conn = JdbcUtils.getConn()) {
            if(addPerson(staff)){
                String sql = "INSERT INTO Staff (sta_id, sta_username, sta_password, sta_is_admin ) VALUES (?, ?, ?, ?)";
                PreparedStatement stm = conn.prepareCall(sql);
                stm.setInt(1, getPerIdByIdCard(staff.getPersIdCard()));
                stm.setString(2, staff.getStaUsername());
                stm.setString(3, staff.getStaPassword());
                stm.setBoolean(4, staff.getStaIsAdmin());
                return stm.executeUpdate() == 1;
            }
        }
        return false;
    }

    // thêm dữ liêu vào bảng person
    public boolean addPerson(Staff staff) throws SQLException {
        try(Connection conn = JdbcUtils.getConn()) {
            String sql = "INSERT INTO Person (pers_id_card, pers_phone_number, pers_sex, " +
                    "pers_last_name, pers_first_name, pers_date_of_birth) " +
                    " VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, staff.getPersIdCard());
            stm.setString(2, staff.getPersPhoneNumber());
            stm.setByte(3, staff.getPersSex());
            stm.setString(4, staff.getPersLastName());
            stm.setString(5, staff.getPersFirstName());
            java.sql.Date dateOfBirth = new java.sql.Date(staff.getPersDateOfBirth().getTime());
            stm.setDate(6, dateOfBirth);
            return stm.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy per_id từ pers_id_card
    public Integer getPerIdByIdCard(String id_card) throws SQLException {
        try(Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT pers_id FROM Person WHERE pers_id_card = ? ";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id_card);

            ResultSet rs = stm.executeQuery();

            if (rs.next())
                return rs.getInt("pers_id");
        }
        return  0;
    }

    // kiểm tra số CMND có trùng không
    public boolean isExistCardId(String card_id) throws SQLException {
        try(Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM Person WHERE pers_id_card = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, card_id);
            return stm.executeQuery().next();
        }
    }

    // lấy staff theo id
    public Staff getStaffDataById(Integer id) throws SQLException {
        Staff staff = new Staff();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT pers_id_card, pers_phone_number, pers_sex, pers_last_name, " +
                    "pers_first_name, pers_date_of_birth, sta_username, sta_is_admin, sta_password, pers_is_active, " +
                    "pers_joined_date FROM Person, Staff WHERE Person.pers_id = Staff.sta_id AND " +
                    "Staff.sta_id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                staff.setPersLastName(rs.getString("pers_last_name"));
                staff.setPersFirstName(rs.getString("pers_first_name"));
                staff.setPersIdCard(rs.getString("pers_id_card"));
                staff.setPersPhoneNumber(rs.getString("pers_phone_number"));
                staff.setPersSex(rs.getByte("pers_sex"));
                staff.setPersDateOfBirth(rs.getDate("pers_date_of_birth"));
                staff.setStaIsAdmin(rs.getBoolean("sta_is_admin"));
                staff.setStaUsername(rs.getString("sta_username"));
                staff.setStaPassword(rs.getString("sta_password"));
                staff.setPersIsActive(rs.getBoolean("pers_is_active"));
                staff.setPersJoinedDate(rs.getDate("pers_joined_date")); // 13/04/2022
            }
        }
        return staff;
    }

    // cập nhật
    public boolean updateStaff(Staff staff) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) { // update từng thèn 1
            String sql = "UPDATE Person, Staff SET  pers_id_card = ?, pers_phone_number = ?, pers_sex = ?, " +
                    " pers_last_name = ?, pers_first_name = ?, pers_date_of_birth = ?, sta_is_admin = ? "+
                    " WHERE Person.pers_id = Staff.sta_id AND sta_username = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, staff.getPersIdCard());
            stm.setString(2, staff.getPersPhoneNumber());
            stm.setByte(3, staff.getPersSex());
            stm.setString(4, staff.getPersLastName());
            stm.setString(5, staff.getPersFirstName());
            java.sql.Date dateOfBirth = new java.sql.Date(staff.getPersDateOfBirth().getTime());
            stm.setDate(6, dateOfBirth);
            stm.setBoolean(7, staff.getStaIsAdmin());
            stm.setString(8, staff.getStaUsername());
            return stm.executeUpdate() == 2;
        }
    }

    // xóa staff
    public boolean deleteStaff(Integer id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "UPDATE Person SET pers_is_active = false WHERE  pers_id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);
            return stm.executeUpdate() == 1;
        }
    }

    // Lấy thông tin nhân viên dựa vào username
    public Staff getStaffByUsername(String username) throws SQLException {
        try(Connection conn = JdbcUtils.getConn()){
            String sql = "SELECT p.pers_id, p.pers_first_name, p.pers_last_name, p.pers_id_card, p.pers_phone_number, " +
                    " p.pers_sex, p.pers_date_of_birth, p.pers_joined_date, s.sta_username " +
                    "FROM Staff s JOIN Person p ON s.sta_id = p.pers_id " +
                    "WHERE s.sta_username = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, username.trim());
            ResultSet r = stm.executeQuery();
            if (r.next()){
                Staff staff = new Staff();
                staff.setStaUsername(r.getString("sta_username"));
                staff.setPersId(r.getInt("pers_id"));
                staff.setPersFirstName(r.getString("pers_first_name"));
                staff.setPersLastName(r.getString("pers_last_name"));
                staff.setPersIdCard(r.getString("pers_id_card"));
                staff.setPersPhoneNumber(r.getString("pers_phone_number"));
                staff.setPersSex(r.getByte("pers_sex"));
                staff.setPersDateOfBirth(r.getDate("pers_date_of_birth"));
                staff.setPersJoinedDate(r.getDate("pers_joined_date"));
                return staff;
            }
        }
        return null;
    }
    
}
