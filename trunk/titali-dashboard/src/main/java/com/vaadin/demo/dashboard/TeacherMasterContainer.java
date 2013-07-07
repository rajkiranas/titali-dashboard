/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaadin.demo.dashboard;

import com.quick.bean.TeacherStddivSubIdBean;
import com.quick.bean.Userprofile;
import com.vaadin.data.util.BeanItemContainer;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vmundhe
 */
public class TeacherMasterContainer  extends BeanItemContainer<Userprofile> implements Serializable {
    
    public static final Object[] NATURAL_COL_ORDER_TEACHER_INFO = new Object[]{
       "prn","name","doj","mobile","address"};
    /**
     * "Human readable" captions for properties in same order as in
     * NATURAL_COL_ORDER_DAILY_CALLS.
     */
    public static final String[] COL_HEADERS_ENGLISH_TEACHER_INFO = new String[]{
       "Prn","Name","Date of Joining","Mobile","Address"};

   
    
    
   public TeacherMasterContainer() throws InstantiationException,
            IllegalAccessException {
        super(Userprofile.class);
    }
   
    public static TeacherMasterContainer getAllTeacherList(List<Userprofile> teacherList){
     TeacherMasterContainer container=null; 
       try {
            container=new TeacherMasterContainer();
             
            for(Userprofile u:teacherList){
              container.addItem(u);  
            }             
           
        } catch (InstantiationException ex) {
            Logger.getLogger(StudentMasterContainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(StudentMasterContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
         return container;
    }
    
   
   
}