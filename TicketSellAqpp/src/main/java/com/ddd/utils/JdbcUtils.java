/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class JdbcUtils {
       private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String HOST = "localhost";
    private final static String DATABASE = "db_banvexe";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "123456789";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(String.format("jdbc:mysql://%s/%s", HOST, DATABASE), USERNAME, PASSWORD);
    }

    public static Connection getConn() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
