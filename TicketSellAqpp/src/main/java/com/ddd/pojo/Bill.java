/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

/**
 *
 * @author admin
 */
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Bill {
    private Integer billId;
    private Timestamp billCreatedDate;
    private BigDecimal billCustomerMoney;
    private BigDecimal billTotalMoney;
    private BigDecimal billTotalSaleMoney;
    private Staff staff;

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Timestamp getBillCreatedDate() {
        return billCreatedDate;
    }

    public void setBillCreatedDate(Timestamp billCreatedDate) {
        this.billCreatedDate = billCreatedDate;
    }

    public BigDecimal getBillCustomerMoney() {
        return billCustomerMoney;
    }

    public void setBillCustomerMoney(BigDecimal billCustomerMoney) {
        this.billCustomerMoney = billCustomerMoney;
    }

    public BigDecimal getBillTotalMoney() {
        return billTotalMoney;
    }

    public void setBillTotalMoney(BigDecimal billTotalMoney) {
        this.billTotalMoney = billTotalMoney;
    }

    public BigDecimal getBillTotalSaleMoney() {
        return billTotalSaleMoney;
    }

    public void setBillTotalSaleMoney(BigDecimal billTotalSaleMoney) {
        this.billTotalSaleMoney = billTotalSaleMoney;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}

   
