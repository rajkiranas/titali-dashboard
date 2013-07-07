/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.table;

import com.vaadin.data.Property;
import com.vaadin.demo.dashboard.TeacherMasterContainer;
import com.vaadin.demo.dashboard.TeacherView;
import com.vaadin.ui.Table;

/**
 *
 * @author vmundhe
 */
public class TeacherTable extends Table{

   private TeacherView teacherView;
    
   public TeacherTable(TeacherView teacherView) {
        this.teacherView=teacherView;
        setSizeFull();
        addStyleName("borderless");
        setSelectable(true);
        setColumnCollapsingAllowed(true);
        setColumnReorderingAllowed(true);
        setContainerDataSource(teacherView.getTeacherMasterContainer());
        setVisibleColumns(TeacherMasterContainer.NATURAL_COL_ORDER_TEACHER_INFO);
        setColumnHeaders(TeacherMasterContainer.COL_HEADERS_ENGLISH_TEACHER_INFO);
        addValueChangeListener((Property.ValueChangeListener)teacherView);
        setImmediate(true);
        setFooterVisible(true);
        setMultiSelect(true);
    }
    
}
