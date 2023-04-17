/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.services;

import com.ddd.utils.MD5Utils;
import com.ddd.pojo.User;
import com.ddd.repostitories.SignInRepostitory;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class SignInService {

    private final static SignInRepostitory SIGN_IN_REPOSITORY;

    static {
        SIGN_IN_REPOSITORY = new SignInRepostitory();
    }

    // Lay thong tin tai khoan
    public User getAccountMD5(String username, String password) throws SQLException, NoSuchAlgorithmException {
        return SIGN_IN_REPOSITORY.getAccount(username, password);
    }
}
