/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.pojo;

import java.util.Date;

/**
 *
 * @author admin
 */
public class Person {
    protected Integer persId;
    protected String persIdCard;
    protected String persPhoneNumber;
    protected Byte persSex;
    protected String persFirstName;
    protected String persLastName;
    protected Date persDateOfBirth;
    protected Date persJoinedDate;
    protected Boolean persIsActive = Boolean.TRUE;

    public Person(Integer persId, String persIdCard, String persPhoneNumber, Byte persSex, String persFirstName, String persLastName, Date persDateOfBirth, Date persJoinedDate) {
        this.persId = persId;
        this.persIdCard = persIdCard;
        this.persPhoneNumber = persPhoneNumber;
        this.persSex = persSex;
        this.persFirstName = persFirstName;
        this.persLastName = persLastName;
        this.persDateOfBirth = persDateOfBirth;
        this.persJoinedDate = persJoinedDate;
    }

    public Person() {
    }
    

    public Integer getPersId() {
        return persId;
    }

    public void setPersId(Integer persId) {
        this.persId = persId;
    }

    public String getPersIdCard() {
        return persIdCard;
    }

    public void setPersIdCard(String persIdCard) {
        this.persIdCard = persIdCard;
    }

    public String getPersPhoneNumber() {
        return persPhoneNumber;
    }

    public void setPersPhoneNumber(String persPhoneNumber) {
        this.persPhoneNumber = persPhoneNumber;
    }

    public Byte getPersSex() {
        return persSex;
    }

    public void setPersSex(Byte persSex) {
        this.persSex = persSex;
    }

    public String getPersFirstName() {
        return persFirstName;
    }

    public void setPersFirstName(String persFirstName) {
        this.persFirstName = persFirstName;
    }

    public String getPersLastName() {
        return persLastName;
    }

    public void setPersLastName(String persLastName) {
        this.persLastName = persLastName;
    }

    public Date getPersDateOfBirth() {
        return persDateOfBirth;
    }

    public void setPersDateOfBirth(Date persDateOfBirth) {
        this.persDateOfBirth = persDateOfBirth;
    }

    public Date getPersJoinedDate() {
        return persJoinedDate;
    }

    public void setPersJoinedDate(Date persJoinedDate) {
        this.persJoinedDate = persJoinedDate;
    }

    public Boolean getPersIsActive() {
        return persIsActive;
    }

    public void setPersIsActive(Boolean persIsActive) {
        this.persIsActive = persIsActive;
    }

    public String toString(){
        return persLastName + " "+ persFirstName;
    }
}

