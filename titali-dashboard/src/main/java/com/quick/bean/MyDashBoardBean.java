/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.bean;

/**
 *
 * @author suyogn
 */
public class MyDashBoardBean {
    private String itemid;
    private String dateTime;
    private String standard;
    private String division;
    private String subject;
    private String topic;
    private String bywhome;
    private String typeofActivity;
    private String  notification;
    private String  uploadId;

    public String getBywhome() {
        return bywhome;
    }

    public void setBywhome(String bywhome) {
        this.bywhome = bywhome;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTypeofActivity() {
        return typeofActivity;
    }

    public void setTypeofActivity(String typeofActivity) {
        this.typeofActivity = typeofActivity;
    }

    /**
     * @return the uploadId
     */
    public String getUploadId() {
        return uploadId;
    }

    /**
     * @param uploadId the uploadId to set
     */
    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }
    
    
}
