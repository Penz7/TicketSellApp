/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

/**
 *
 * @author admin
 */
public class Seat {
    private Integer ID_Ghe;
    private Boolean TinhTrangGhe;
    private Integer ID_Xe;

    public Seat(Integer ID_Ghe, Boolean TinhTrangGhe, Integer ID_Xe) {
        this.ID_Ghe = ID_Ghe;
        this.TinhTrangGhe = TinhTrangGhe;
        this.ID_Xe = ID_Xe;
    }

    public Seat() {
    }

    public Integer getID_Ghe() {
        return ID_Ghe;
    }

    public void setID_Ghe(Integer ID_Ghe) {
        this.ID_Ghe = ID_Ghe;
    }

    public Integer getID_Xe() {
        return ID_Xe;
    }

    public void setID_Xe(Integer ID_Xe) {
        this.ID_Xe = ID_Xe;
    }

    public Boolean getTinhTrangGhe() {
        return TinhTrangGhe;
    }

    public void setTinhTrangGhe(Boolean TinhTrangGhe) {
        this.TinhTrangGhe = TinhTrangGhe;
    }

    
    
    
}
