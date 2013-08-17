/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.table;

import com.quick.bean.MasteParmBean;
import com.quick.entity.Notices;
import com.quick.entity.Whatsnew;
import com.quick.entity.Whoisdoingwhat;
import com.quick.data.MyDashBoardContainer;
import com.vaadin.data.Property;
import com.vaadin.demo.dashboard.DashboardView;
import com.vaadin.ui.Table;
import java.util.List;

/**
 *
 * @author suyogn
 */
public class MyDashBoardDataProvider {
    
    public MyDashBoardDataProvider(){
        
    }
    
    public Table getWhatsNewForme(List<Whatsnew>whatsnews,DashboardView dashBoardView){
        
        Table t =new Table();
        t.setCaption("Whats New");
        t.addStyleName("plain");
        t.addStyleName("borderless");
        t.setSortEnabled(false);
        t.setWidth("100%");
        t.setPageLength(0);
        t.setSelectable(true);
        t.addValueChangeListener((Property.ValueChangeListener)dashBoardView);
        t.setImmediate(true); // react at once when something is selected
        t.setContainerDataSource(MyDashBoardContainer.getWhatsNewForMeContainer(whatsnews));
        t.setVisibleColumns(MyDashBoardContainer.NATURAL_COL_ORDER_WHATS_NEW);
        t.setColumnHeaders(MyDashBoardContainer.COL_HEADERS_ENGLISH_WHATS_NEW);
        return t;
    }
    
    public Table getMyNoticeBoard(List<Notices>noticeses){
        
        Table t =new Table();
        t.setCaption("Notices");
        t.addStyleName("plain");
        t.addStyleName("borderless");
        t.setSortEnabled(false);
         t.setPageLength(0);
        //t.setRowHeaderMode(Table.RowHeaderMode.INDEX);
        t.setWidth("100%");
        t.setSelectable(false);
        t.setMultiSelect(true);
        t.setImmediate(true); // react at once when something is selected
        t.setContainerDataSource(MyDashBoardContainer.getNoticesForMeContainer(noticeses));
        t.setVisibleColumns(MyDashBoardContainer.NATURAL_COL_ORDER_NOtice);
        t.setColumnHeaders(MyDashBoardContainer.COL_HEADERS_ENGLISH__NOtice);
        return t;
    }
    public Table getWhoIsDoingWhat(List<MasteParmBean>whoisdoingwhats){
        
        Table t =new Table();
        t.setCaption("Who's doing what");
        t.addStyleName("plain");
        t.addStyleName("borderless");
        t.setSortEnabled(false);
         t.setPageLength(0);
        //t.setRowHeaderMode(Table.RowHeaderMode.INDEX);
        t.setWidth("100%");
        t.setSelectable(false);
        
        t.setMultiSelect(true);
        t.setImmediate(true); // react at once when something is selected
        t.setContainerDataSource(MyDashBoardContainer.getWhoIsDoingWhatContainer(whoisdoingwhats));
        t.setVisibleColumns(MyDashBoardContainer.NATURAL_COL_ORDER_Activity);
        t.setColumnHeaders(MyDashBoardContainer.COL_HEADERS_ENGLISH_Activity);
        return t;
    }
}
