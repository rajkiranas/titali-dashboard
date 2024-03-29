package com.quick.entity;
// Generated 5 Jun, 2013 6:08:00 PM by Hibernate Tools 3.2.1.GA



/**
 * ExamStudentResponseId generated by hbm2java
 */
public class ExamStudentResponseId  implements java.io.Serializable {


     private int exId;
     private String username;
     private int questionId;

    public ExamStudentResponseId() {
    }

    public ExamStudentResponseId(int exId, String username, int questionId) {
       this.exId = exId;
       this.username = username;
       this.questionId = questionId;
    }
   

    public int getExId() {
        return this.exId;
    }
    
    public void setExId(int exId) {
        this.exId = exId;
    }

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public int getQuestionId() {
        return this.questionId;
    }
    
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ExamStudentResponseId) ) return false;
		 ExamStudentResponseId castOther = ( ExamStudentResponseId ) other; 
         
		 return (this.getExId()==castOther.getExId())
 && ( (this.getUsername()==castOther.getUsername()) || ( this.getUsername()!=null && castOther.getUsername()!=null && this.getUsername().equals(castOther.getUsername()) ) )
 && (this.getQuestionId()==castOther.getQuestionId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getExId();
         result = 37 * result + ( getUsername() == null ? 0 : this.getUsername().hashCode() );
         result = 37 * result + this.getQuestionId();
         return result;
   }   


}


