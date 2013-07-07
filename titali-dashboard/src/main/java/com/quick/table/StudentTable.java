/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.table;

import com.vaadin.data.Property;
import com.vaadin.demo.dashboard.StudentMasterContainer;
import com.vaadin.demo.dashboard.StudentView;
import com.vaadin.ui.Table;

/**
 *
 * @author sonalis
 */
public class StudentTable extends Table{
    
    private StudentView studentView;
    
    public StudentTable(StudentView studentView){
        this.studentView=studentView;
        setSizeFull();
        setImmediate(true);
        addStyleName("borderless");
        setSelectable(true);
        setColumnCollapsingAllowed(true);
        setColumnReorderingAllowed(true);        
        setContainerDataSource(studentView.getStudentMasterContainer());       
        setVisibleColumns(StudentMasterContainer.NATURAL_COL_ORDER_STUDENT_INFO);
        setColumnHeaders(StudentMasterContainer.COL_HEADERS_ENGLISH_STUDENT_INFO);
        addValueChangeListener((Property.ValueChangeListener)studentView);
        setFooterVisible(true);
        setMultiSelect(true);
    }

    
    
}
