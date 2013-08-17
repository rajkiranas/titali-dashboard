/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.table;

import com.quick.bean.MasteParmBean;
import com.quick.container.StudQuickLearnContainer;
import com.quick.data.MyDashBoardContainer;
import com.vaadin.data.Property;
import com.vaadin.demo.dashboard.StudQuickLearn;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

/**
 *
 * @author rajkiran
 */
public class StudQuickLearnTable extends Table {
    
    public StudQuickLearnTable(final StudQuickLearn quickLearn){
        
        ///setCaption("Whats New");
        //setSizeFull();
      //  addStyleName("plain");
        addStyleName("borderless");
        setSortEnabled(false);
        setWidth("100%");
        setPageLength(10);
        setSelectable(true);
        setImmediate(true); // react at once when something is selected
        setContainerDataSource(StudQuickLearnContainer.getStudQuickLearnContainer(quickLearn.getTopicList()));
        setVisibleColumns(StudQuickLearnContainer.NATURAL_COL_ORDER_QUICKLEARN);
        setColumnHeaders(StudQuickLearnContainer.COL_HEADERS_ENGLISH_QUICKLEARN);
        addValueChangeListener((Property.ValueChangeListener)quickLearn);
       
        }
    
    
    
    
    
    
}
