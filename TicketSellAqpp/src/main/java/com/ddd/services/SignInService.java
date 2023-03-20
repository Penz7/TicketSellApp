/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.utils.MD5Utils;
import com.ddd.pojo.User;
import com.ddd.repostitories.SignInResponstitory;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class SignInService {

    private final static SignInResponstitory SIGN_IN_REPOSITORY;

    static {
        SIGN_IN_REPOSITORY = new SignInResponstitory();
    }

    // Lay thong tin tai khoan
    public User getAccountMD5(String username, String password) throws SQLException, NoSuchAlgorithmException {
//        try {
//            password = MD5(password);
//        } catch (Exception ex) {
//            Logger.getLogger(Staff.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return SIGN_IN_REPOSITORY.getAccount(username, password);
    }

    // Lấy mã MD5 ---- Phục vụ viết testcase
    public String MD5(String text) {
        return MD5Utils.getMd5(text);
    }

}
