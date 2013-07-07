/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author vmundhe
 */
public class TeacherBean implements Serializable{
    
    private int prn;
    private String username;
    private String password;
    private String name;
    private String mobile;
    private Date dob;    
    private Date doj;
    private char gender;
    private String address;
    private char isClassTeacher;
    private String qualName;
    private String standard;
    private String subject;
    private String division;
    private String isCtClass;
    private String IsDeleted;
    
    
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public char getIsClassTeacher() {
        return isClassTeacher;
    }

    public void setIsClassTeacher(char isClassTeacher) {
        this.isClassTeacher = isClassTeacher;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
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

    public String getQualName() {
        return qualName;
    }

    public void setQualName(String qualName) {
        this.qualName = qualName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(String IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    public Date getDoj() {
        return doj;
    }

    public void setDoj(Date doj) {
        this.doj = doj;
    }

    public String getIsCtClass() {
        return isCtClass;
    }

    public void setIsCtClass(String isCtClass) {
        this.isCtClass = isCtClass;
    }
    
    
}
