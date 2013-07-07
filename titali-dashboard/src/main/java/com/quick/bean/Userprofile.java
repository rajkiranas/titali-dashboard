/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.bean;

import java.util.Date;

/**
 *
 * @author suyogn
 */
public class Userprofile {
     private String username;
     private int prn;
     private String password;
     private String name;
     private Long creationdate;
     private Long mobile;

    @Override
    public String toString() {
        return "Userprofile{" + "username=" + username + ", prn=" + prn + ", password=" + password + ", name=" + name + ", creationdate=" + creationdate + ", mobile=" + mobile + ", div=" + div + ", std=" + std + ", address=" + address + ", doj=" + doj + ", rno=" + rno + ", gender=" + gender + ", dob=" + dob + ", eduYear=" + eduYear + ", qualName=" + qualName + '}';
    }
     private String div;
     private String std;
     private String address;
     private Date doj;
     private Short rno;
     private char gender;
     private Date dob;
     private String eduYear;
     private String qualName;
     
    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public Long getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Long creationdate) {
        this.creationdate = creationdate;
    }

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPrn() {
        return prn;
    }

    public void setPrn(int prn) {
        this.prn = prn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDoj() {
        return doj;
    }

    public void setDoj(Date doj) {
        this.doj = doj;
    }

    public Short getRno() {
        return rno;
    }

    public void setRno(Short rno) {
        this.rno = rno;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEduYear() {
        return eduYear;
    }

    public void setEduYear(String eduYear) {
        this.eduYear = eduYear;
    }

    public String getQualName() {
        return qualName;
    }

    public void setQualName(String qualName) {
        this.qualName = qualName;
    }
    
    
}
