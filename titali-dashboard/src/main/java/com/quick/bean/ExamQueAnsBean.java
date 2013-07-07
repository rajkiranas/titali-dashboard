/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.bean;

/**
 *
 * @author rajkiran
 */
public class ExamQueAnsBean {
     private int examId;
     private int exType;
     private String username;
     private int questionId;
     private int questionType;
     private String question;
     private int marksForQuestion;
     private String option1;
     private String option2;
     private String option3;
     private String option4;
     private String ans;
     private String objAns;
     private String descriptiveAns;
     private int obtMarksForObjQuestion;
     private int obtMarksForDescQuestion;

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getExType() {
        return exType;
    }

    public void setExType(int exType) {
        this.exType = exType;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getDescriptiveAns() {
        return descriptiveAns;
    }

    public void setDescriptiveAns(String descriptiveAns) {
        this.descriptiveAns = descriptiveAns;
    }

 
    public int getMarksForQuestion() {
        return marksForQuestion;
    }

    public void setMarksForQuestion(int marksForQuestion) {
        this.marksForQuestion = marksForQuestion;
    }

    public String getObjAns() {
        return objAns;
    }

    public void setObjAns(String objAns) {
        this.objAns = objAns;
    }

    public int getObtMarksForDescQuestion() {
        return obtMarksForDescQuestion;
    }

    public void setObtMarksForDescQuestion(int obtMarksForDescQuestion) {
        this.obtMarksForDescQuestion = obtMarksForDescQuestion;
    }

    public int getObtMarksForObjQuestion() {
        return obtMarksForObjQuestion;
    }

    public void setObtMarksForObjQuestion(int obtMarksForObjQuestion) {
        this.obtMarksForObjQuestion = obtMarksForObjQuestion;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
     
}
