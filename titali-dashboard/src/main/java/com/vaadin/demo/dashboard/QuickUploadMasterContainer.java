/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaadin.demo.dashboard;

import com.quick.bean.MasteParmBean;
import com.quick.bean.QuickLearn;
import com.quick.bean.Userprofile;
import com.vaadin.data.util.BeanItemContainer;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sonali Sangle
 */
public class QuickUploadMasterContainer extends BeanItemContainer<MasteParmBean> implements Serializable {
    
    public static final Object[] NATURAL_COL_ORDER_QUICKUPLOAD_INFO = new Object[]{
      "uploadId","uploadDate","std","div","sub","topic"};
    /**
     * "Human readable" captions for properties in same order as in
     * NATURAL_COL_ORDER_DAILY_CALLS.
     */
    public static final String[] COL_HEADERS_ENGLISH_QUICKUPLOAD_INFO = new String[]{
      "Upload Id","Upload Date","Standard","Division","Subject","Topic"};

       
   public QuickUploadMasterContainer() throws InstantiationException,
            IllegalAccessException {
        super(MasteParmBean.class);
    }
   
    public static QuickUploadMasterContainer getQuickLearnUploadList(List<MasteParmBean> uploadedList) {
         QuickUploadMasterContainer container=null; 
       try {
            container=new QuickUploadMasterContainer();
             
            for(MasteParmBean u:uploadedList){
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
