/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.data;

import com.quick.bean.MasteParmBean;
import com.quick.bean.MyDashBoardBean;
import com.quick.entity.Notices;
import com.quick.entity.Whatsnew;
import com.quick.entity.Whoisdoingwhat;
import com.vaadin.data.util.BeanItemContainer;
import java.util.List;

/**
 *
 * @author suyogn
 */
public class MyDashBoardContainer extends BeanItemContainer<MyDashBoardBean> {
     public static final Object[] NATURAL_COL_ORDER_WHATS_NEW = new Object[]{
       "notification"};
     //"notification","dateTime"};
        /**
        * "Human readable" captions for properties in same order as in
        * NATURAL_COL_ORDER_DAILY_CALLS.
        */
    public static final String[] COL_HEADERS_ENGLISH_WHATS_NEW = new String[]{
        "Notification's"};
    //"Notification's","Release time"};
    
    
    
    
     public static final Object[] NATURAL_COL_ORDER_NOtice = new Object[]{
       "notification"};
        /**
        * "Human readable" captions for properties in same order as in
        * NATURAL_COL_ORDER_DAILY_CALLS.
        */
    public static final String[] COL_HEADERS_ENGLISH__NOtice = new String[]{
        "Notice"};
    
    
    
     public static final Object[] NATURAL_COL_ORDER_Activity = new Object[]{
       "notification"};
        /**
        * "Human readable" captions for properties in same order as in
        * NATURAL_COL_ORDER_DAILY_CALLS.
        */
    public static final String[] COL_HEADERS_ENGLISH_Activity = new String[]{
        "Notification's"};

    public MyDashBoardContainer(){
        super(MyDashBoardBean.class);
    }
    
    public static MyDashBoardContainer getWhatsNewForMeContainer(List<Whatsnew>whatsnews) {
       MyDashBoardContainer boardContainer=null;
       MyDashBoardBean bean;
       boardContainer=new MyDashBoardContainer();
       try{
           
           for(Whatsnew w:whatsnews){
                 bean=new MyDashBoardBean();
                 bean.setNotification(w.getDisplaynotification());
                 bean.setItemid(String.valueOf(w.getItemid()));
                 bean.setDateTime(w.getReleasedate().toString());
                 boardContainer.addItem(bean);
           }
       }catch(Exception ex){
           ex.printStackTrace();
       }
       return boardContainer;
        
    }
    
    
      public static MyDashBoardContainer getNoticesForMeContainer(List<Notices>noticeses) {
       MyDashBoardContainer boardContainer=null;
       MyDashBoardBean bean;
       boardContainer=new MyDashBoardContainer();
       try{
        
             
           for(Notices notices:noticeses){
                 bean=new MyDashBoardBean();
                 bean.setNotification(notices.getNoticeline());
                 boardContainer.addItem(bean);
           }
           
           
       }catch(Exception ex){
           ex.printStackTrace();
       }
       
       
       
       return boardContainer;
        
    }

      
       public static MyDashBoardContainer getWhoIsDoingWhatContainer(List<MasteParmBean>whoisdoingwhats) {
       MyDashBoardContainer boardContainer=null;
       MyDashBoardBean bean;
       boardContainer=new MyDashBoardContainer();
       try{
       
            for(MasteParmBean whoisdoingwhat:whoisdoingwhats){
                bean = new MyDashBoardBean();
                /* bean.setItemid(""+whoisdoingwhat.getActivityId());
                bean.setBywhome(whoisdoingwhat.getByWhom());
                bean.setTopic(whoisdoingwhat.getTopic()); */
                bean.setNotification(whoisdoingwhat.getDisplaynotification());
                bean.setUploadId(String.valueOf(whoisdoingwhat.getUploadId()));                
                boardContainer.addItem(bean);
            }
           
           
       }catch(Exception ex){
           ex.printStackTrace();
       }
       return boardContainer;
        
    }
        
}
