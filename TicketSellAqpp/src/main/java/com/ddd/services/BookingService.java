/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;
import com.ddd.repostitories.BookingRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
/**
 *
 * @author Admin
 */
public class BookingService {
    private final static BookingRepository BOOKING_REPOSITORY;

    static {
        BOOKING_REPOSITORY = new BookingRepository();
    }
}
