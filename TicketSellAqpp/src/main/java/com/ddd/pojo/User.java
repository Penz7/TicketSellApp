/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

import java.sql.Date;

public class User{
   private Integer user_id;
   private String user_fullname;
   private String user_id_card;
   private String user_phone_number;
   private Date user_date_of_birth;
   private Date user_date_join;
   private String username;
   private String password;
   private String user_address;
   private Integer role_id;

    public User(Integer user_id, String user_fullname, String user_id_card, String user_phone_number, Date user_date_of_birth, Date user_date_join, String username, String password, String user_address, Integer role_id) {
        this.user_id = user_id;
        this.user_fullname = user_fullname;
        this.user_id_card = user_id_card;
        this.user_phone_number = user_phone_number;
        this.user_date_of_birth = user_date_of_birth;
        this.user_date_join = user_date_join;
        this.username = username;
        this.password = password;
        this.user_address = user_address;
        this.role_id = role_id;
    }



    public User() {
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public String getUser_id_card() {
        return user_id_card;
    }

    public void setUser_id_card(String user_id_card) {
        this.user_id_card = user_id_card;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public void setUser_phone_number(String user_phone_number) {
        this.user_phone_number = user_phone_number;
    }

    public Date getUser_date_of_birth() {
        return user_date_of_birth;
    }

    public void setUser_date_of_birth(Date user_date_of_birth) {
        this.user_date_of_birth = user_date_of_birth;
    }

    public Date getUser_date_join() {
        return user_date_join;
    }

    public void setUser_date_join(Date user_date_join) {
        this.user_date_join = user_date_join;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return super.toString(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
 
}
    
  
