/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaadin.demo.dashboard;

import com.quick.bean.Userprofile;
import com.vaadin.data.util.BeanItemContainer;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonalis
 */
public class StudentMasterContainer  extends BeanItemContainer<Userprofile> implements Serializable {
    
    public static final Object[] NATURAL_COL_ORDER_STUDENT_INFO = new Object[]{
      "prn","rno","name","std","div","mobile","address"};
    /**
     * "Human readable" captions for properties in same order as in
     * NATURAL_COL_ORDER_DAILY_CALLS.
     */
    public static final String[] COL_HEADERS_ENGLISH_STUDENT_INFO = new String[]{
      "Prn","Roll No","Name","Standard","Divison","Mobile","Address"};

   
    
    
   public StudentMasterContainer() throws InstantiationException,
            IllegalAccessException {
        super(Userprofile.class);
    }
       
   public static StudentMasterContainer getAllStudentList(List<Userprofile> studentList) {
       StudentMasterContainer studentMasterContainer=null; 
       try {
            studentMasterContainer=new StudentMasterContainer();
             
            for(Userprofile u:studentList){
              studentMasterContainer.addItem(u);  
            }             
           
        } catch (InstantiationException ex) {
            Logger.getLogger(StudentMasterContainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(StudentMasterContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
         return studentMasterContainer;
    }
   
}