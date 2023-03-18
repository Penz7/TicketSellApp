/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

import java.util.List;

/**
 *
 * @author admin
 */
public class Staff extends Person{
    private String staUsername;
    private String staPassword;
    private Boolean staIsAdmin;
    private List<Bill> bills;
    private String branchName;
    private String roleName;
    private String sex;
    private String activeName;

    public String getStaUsername() {
        return staUsername;
    }

    public void setStaUsername(String staUsername) {
        this.staUsername = staUsername;
    }

    public String getStaPassword() {
        return staPassword;
    }

    public void setStaPassword(String staPassword) {
        this.staPassword = staPassword;
    }

    public Boolean getStaIsAdmin() {
        return staIsAdmin;
    }

    public void setStaIsAdmin(Boolean staIsAdmin) {
        this.staIsAdmin = staIsAdmin;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public String getBranchName(){return branchName;}

    public void setBranchName(String name){this.branchName= name;}


    public String getRoleName(){return roleName;}

    public void setRoleName(String name){this.roleName= name;}

    public String getSex(){return sex;}

    public void setSex(String name){this.sex= name;}

    public String getActiveName(){return activeName;}

    public void setActiveName(String name){this.activeName = name;}
}
    
  