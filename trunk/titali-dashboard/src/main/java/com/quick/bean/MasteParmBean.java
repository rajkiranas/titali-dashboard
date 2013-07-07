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
public class MasteParmBean {
    private String sub;
    private String std;
    private String div;
    private String topic;
    private int uploadId;    
    private Date uploadDate;
    private String notes;
    private String othernotes;
    private String topicTags;
    private String previousQuestion;
    private String videoPath;
    private String videoInfo;
    private String lectureNotes;
    private String lectureNotesInformation;
    private String otherNotes;
    private String otherNotesInformation;
    private String previousQuestionInformation;
    private int activityId;
    private String byWhom;
    private String displaynotification;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getByWhom() {
        return byWhom;
    }

    public void setByWhom(String byWhom) {
        this.byWhom = byWhom;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getUploadId() {
        return uploadId;
    }

    public void setUploadId(int uploadId) {
        this.uploadId = uploadId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTopicTags() {
        return topicTags;
    }

    public void setTopicTags(String topicTags) {
        this.topicTags = topicTags;
    }

    public String getOthernotes() {
        return othernotes;
    }

    public void setOthernotes(String othernotes) {
        this.othernotes = othernotes;
    }

    public String getPreviousQuestion() {
        return previousQuestion;
    }

    public void setPreviousQuestion(String previousQuestion) {
        this.previousQuestion = previousQuestion;
    }

    public String getLectureNotes() {
        return lectureNotes;
    }

    public void setLectureNotes(String lectureNotes) {
        this.lectureNotes = lectureNotes;
    }

    public String getLectureNotesInformation() {
        return lectureNotesInformation;
    }

    public void setLectureNotesInformation(String lectureNotesInformation) {
        this.lectureNotesInformation = lectureNotesInformation;
    }

    public String getOtherNotes() {
        return otherNotes;
    }

    public void setOtherNotes(String otherNotes) {
        this.otherNotes = otherNotes;
    }

    public String getOtherNotesInformation() {
        return otherNotesInformation;
    }

    public void setOtherNotesInformation(String otherNotesInformation) {
        this.otherNotesInformation = otherNotesInformation;
    }

    public String getPreviousQuestionInformation() {
        return previousQuestionInformation;
    }

    public void setPreviousQuestionInformation(String previousQuestionInformation) {
        this.previousQuestionInformation = previousQuestionInformation;
    }

    public String getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(String videoInfo) {
        this.videoInfo = videoInfo;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    /**
     * @return the displaynotification
     */
    public String getDisplaynotification() {
        return displaynotification;
    }

    /**
     * @param displaynotification the displaynotification to set
     */
    public void setDisplaynotification(String displaynotification) {
        this.displaynotification = displaynotification;
    }

   
    
    
}
