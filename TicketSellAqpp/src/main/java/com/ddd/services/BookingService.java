/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.repostitories.BookingRepostitory;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Admin
 */
public class BookingService {
//    private final static BookingRepostitory BOOKING_REPOSITORY;

    public boolean checkSeatLimit(int maVeXe) throws SQLException {
        String query = "SELECT COUNT(*) AS seat_count FROM ghe g JOIN vexe v ON g.ID_Ghe = v.ID_Ghe WHERE v.ID_VeXe = ? AND g.TinhTrangGhe = 1";

        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maVeXe);

            ResultSet rs = ps.executeQuery();
            int seatCount = 0;
            if (rs.next()) {
                seatCount = rs.getInt("seat_count");
            }

            if (seatCount >= 25) {
                return false;
            }
            return true;
        }
    }
}
