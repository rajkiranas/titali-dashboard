/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.data;

import com.quick.bean.MasteParmBean;
import com.quick.bean.TeacherStddivSubIdBean;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.demo.dashboard.StudentMasterContainer;
import com.vaadin.demo.dashboard.TeacherMasterContainer;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sonali Sangle
 */
public class TeacherSubjectAssociationContainer extends BeanItemContainer<TeacherStddivSubIdBean> implements Serializable {
    
    public static final Object[] NATURAL_COL_ORDER_TEACHERSTDDIVSUBINFO = new Object[]{
     "username","std","div","sub"};
    /**
     * "Human readable" captions for properties in same order as in
     * NATURAL_COL_ORDER_DAILY_CALLS.
     */
    public static final String[] COL_HEADERS_ENGLISH_TEACHERSTDDIVSUBINFO = new String[]{
      "Username","Standard","Division","Subject"};

       
   public TeacherSubjectAssociationContainer() throws InstantiationException,
            IllegalAccessException {
        super(TeacherStddivSubIdBean.class);
    }
   
  public static TeacherSubjectAssociationContainer getTeacherStdDivsSubAssociationList(List<TeacherStddivSubIdBean> teacherSubAssociationList) {
        TeacherSubjectAssociationContainer container=null; 
       try {
            container=new TeacherSubjectAssociationContainer();
             
            for(TeacherStddivSubIdBean u:teacherSubAssociationList){
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
