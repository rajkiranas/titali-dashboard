/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.table;

import com.quick.bean.TeacherStddivSubIdBean;
import com.quick.container.StudQuickLearnContainer;
import com.quick.data.TeacherSubjectAssociationContainer;
import com.vaadin.data.Property;
import com.vaadin.demo.dashboard.AddTeacher;
import com.vaadin.demo.dashboard.StudentMasterContainer;
import com.vaadin.demo.dashboard.TeacherAssociationFilter;
import com.vaadin.ui.Table;
import java.util.List;

/**
 *
 * @author sonalis
 */
public class TeacherSubjectAssociationTable extends Table{
    
    private AddTeacher addTeacher;
    
    public TeacherSubjectAssociationTable(AddTeacher addTeacher){
        this.addTeacher=addTeacher;
        addStyleName("borderless");
        setSortEnabled(false);
        setWidth("100%");
        setPageLength(5);
        setSelectable(true);
        setMultiSelect(true);
        setImmediate(true); 
        setContainerDataSource(addTeacher.getTeacherSubjectAssociationContainer());       
        setVisibleColumns(TeacherSubjectAssociationContainer.NATURAL_COL_ORDER_TEACHERSTDDIVSUBINFO);
        setColumnHeaders(TeacherSubjectAssociationContainer.COL_HEADERS_ENGLISH_TEACHERSTDDIVSUBINFO);
        addValueChangeListener((Property.ValueChangeListener)addTeacher);
    }
}
