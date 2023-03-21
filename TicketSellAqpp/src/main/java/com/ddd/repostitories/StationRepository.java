/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.repostitories;

import com.ddd.pojo.Station;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Admin
 */
public class StationRepository {
      public List<Station> getStationByKeyWord(String kw) throws SQLException {
        List<Station> results = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM benxe";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE TenBen like concat('%', ?, '%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Station s = new Station(rs.getInt("ID_Ben"), rs.getString("TenBen"));
                results.add(s);
            }
        }

        return results;
    }
      
     public List<Station> getAllStation() throws SQLException {
        List<Station> results = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM benxe";
            PreparedStatement stm = conn.prepareCall(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Station s = new Station(rs.getInt("ID_Ben"), rs.getString("TenBen"));
                results.add(s);
            }
        }

        return  results;
    }
     
     
     public List<Station> getStations(String kw) throws SQLException {
        List<Station> results = new ArrayList<>();

        try ( Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM benxe";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE TenBen like concat('%', ?, '%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty())
                stm.setString(1, kw);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Station s = new Station(rs.getInt("ID_Ben"),rs.getString("TenBen"));
                results.add(s);
            }
        }

        return results;
    }
    
    public boolean deleteStation(String id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM benxe WHERE ID_Ben=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id);
            
            return stm.executeUpdate() > 0;
        }
    }
    
    public void addStation(Integer stationId, String stationName) {
            String sql = "INSERT INTO benxe (ID_Benxe, TenBen) VALUES (?, ?)";
             try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, stationId.toString());
            preparedStatement.setString(2, stationName);

            // Thực hiện truy vấn và kiểm tra kết quả
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                // Hiển thị thông báo khi thêm station thành công
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Thêm station");
                alert.setHeaderText(null);
                alert.setContentText("Thêm station thành công!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Hiển thị thông báo lỗi nếu có lỗi xảy ra
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Không thể thêm station vào database: " + e.getMessage());
            alert.showAndWait();
        }
    }
}

