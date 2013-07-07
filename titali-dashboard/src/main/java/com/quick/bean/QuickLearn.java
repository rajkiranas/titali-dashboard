/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.bean;

import java.util.Date;

/**
 *
 * @author Sonali Sangle
 */
public class QuickLearn {
    
     private int uploadId;
     private String std;
     private String sub;
     private Date uploadDate;
     private String fordiv;
     private String topic;
     private String videoPath;
     private String videoInfo;
     private String lectureNotes;
     private String lectureNotesInformation;
     private String otherNotes;
     private String otherNotesInformation;
     private String previousQuestion;
     private String previousQuestionInformation;
     private String topicTags;

    public String getQuickNotes() {
        return QuickNotes;
    }

    public void setQuickNotes(String QuickNotes) {
        this.QuickNotes = QuickNotes;
    }
     private String QuickNotes;
     

    public String getFordiv() {
        return fordiv;
    }

    public void setFordiv(String fordiv) {
        this.fordiv = fordiv;
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

    public String getPreviousQuestion() {
        return previousQuestion;
    }

    public void setPreviousQuestion(String previousQuestion) {
        this.previousQuestion = previousQuestion;
    }

    public String getPreviousQuestionInformation() {
        return previousQuestionInformation;
    }

    public void setPreviousQuestionInformation(String previousQuestionInformation) {
        this.previousQuestionInformation = previousQuestionInformation;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopicTags() {
        return topicTags;
    }

    public void setTopicTags(String topicTags) {
        this.topicTags = topicTags;
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
   
    
    
}
