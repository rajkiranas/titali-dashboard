/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.container;

import com.quick.bean.MasteParmBean;
import com.quick.data.MasterDataProvider;
import com.vaadin.data.util.BeanItemContainer;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rajkiran
 */
public class StudQuickLearnContainer extends BeanItemContainer<MasteParmBean> {
    

      public static final Object[] NATURAL_COL_ORDER_QUICKLEARN = new Object[]{
       "uploadDate","topic"};
        /**
        * "Human readable" captions for properties in same order as in
        * NATURAL_COL_ORDER_DAILY_CALLS.
        */
    public static final String[] COL_HEADERS_ENGLISH_QUICKLEARN = new String[]{
        "Date","Topic"};
    
    public StudQuickLearnContainer(){
        super(MasteParmBean.class);
    }
    
    public static StudQuickLearnContainer getStudQuickLearnContainer(List<MasteParmBean> list){
        StudQuickLearnContainer container = null;
        container = new StudQuickLearnContainer();
         MasteParmBean bean = null;
         if(list!=null){
             for(MasteParmBean mpb:list){
           bean = new MasteParmBean();
           bean.setUploadDate(mpb.getUploadDate());
           bean.setTopic(mpb.getTopic());
           //upload id is not displayed but used to fetch all information on value select of the topic table
           bean.setUploadId(mpb.getUploadId());
           container.addItem(bean);
        }
       }
        
        return container;
    }
}
