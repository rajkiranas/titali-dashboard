/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.ui.exam;

import com.quick.bean.ExamBean;
import com.quick.container.StudQuickLearnContainer;
import com.quick.container.StudentExamListContainer;
import com.vaadin.data.Property;
import com.vaadin.demo.dashboard.TopGrossingMoviesChart;
import com.vaadin.demo.dashboard.TopSixTheatersChart;
import com.vaadin.ui.*;
import java.util.List;

/**
 *
 * @author rajkiran
 */
public class StudentExamDataProvider {
    
    
    public static Table getStudentExamList(List<ExamBean> list){
        Table t1 = new Table();
        t1.setCaption("Student ExamList");
        //setSizeFull();
      //  addStyleName("plain");
        t1.addStyleName("borderless");
        t1.setSortEnabled(false);
        t1.setWidth("100%");
        t1.setPageLength(10);
        t1.setSelectable(true);
        //t1.setMultiSelect(true);
        t1.setImmediate(true); // react at once when something is selected
        t1.setContainerDataSource(StudentExamListContainer.getExamListContainer(list));
        t1.setVisibleColumns(StudentExamListContainer.NATURAL_COL_ORDER_QUICKLEARN);
        t1.setColumnHeaders(StudentExamListContainer.COL_HEADERS_ENGLISH_QUICKLEARN);
       
        return t1;
    }

    public static Component getMyExamPieChart(Object object) {
        HorizontalLayout baseLayout = new HorizontalLayout();
        baseLayout.setSizeFull();
        Component c1 = createPanel(new TopSixTheatersChart());
        baseLayout.addComponent(c1);
        baseLayout.setExpandRatio(c1, 1.5f);
         Component c2 = createPanel(new TopGrossingMoviesChart());
        baseLayout.addComponent(c2);
        baseLayout.setExpandRatio(c2,2);
        return baseLayout;
    }

    public static Component getSelectedExamDetails(Object object) {
        FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);
        TextField subtxt =new TextField();
        subtxt.setCaption("subject");
        TextField markstxt =new TextField();
        markstxt.setCaption("Marks");
        TextField scoretxt =new TextField();
        scoretxt.setCaption("Score");
        TextField questionstxt =new TextField();
        questionstxt.setCaption("Questions");
       formLayout.addComponent(subtxt);
        formLayout.addComponent(markstxt);
        formLayout.addComponent(scoretxt);
        formLayout.addComponent(questionstxt);
        //throw new UnsupportedOperationException("Not yet implemented");
        return formLayout;
    }

    public static Component getExamResult(Object object) {
        HorizontalLayout baseLayout = new HorizontalLayout();
         baseLayout.setSizeFull();
        baseLayout.addComponent(createPanel(new TopSixTheatersChart()));
        baseLayout.addComponent(createPanel(new TopGrossingMoviesChart()));
        return baseLayout;

    }
    
    
    
     private static CssLayout createPanel(Component content) {
        CssLayout panel = new CssLayout();
       // panel.addStyleName("layout-panel");
        panel.setSizeFull();
        panel.addComponent(content);
        return panel;
    }

    
}
