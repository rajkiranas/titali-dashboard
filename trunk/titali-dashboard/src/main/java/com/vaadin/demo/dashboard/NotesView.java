/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaadin.demo.dashboard;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 *
 * @author Sonali Sangle
 */
public class NotesView extends VerticalLayout implements View{
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        setSizeFull();        
        addStyleName("schedule");
    }
    
    public NotesView(){
        VerticalLayout column = new VerticalLayout();
        column.setSpacing(true);
        column.setMargin(true);
        
        HorizontalLayout baseLayout=new HorizontalLayout();
        baseLayout.setMargin(true);
        baseLayout.setSpacing(true);
        RichTextArea notetxt = new RichTextArea();
        notetxt.setWidth("200px");
        notetxt.setHeight("200px");
        baseLayout.addComponent(notetxt);
        column.addComponent(baseLayout); 
        
        Button upload= new Button("Save");
        upload.addStyleName("wide");
        upload.addStyleName("default");
        upload.setImmediate(true);
        column.addComponent(upload);     
        column.setComponentAlignment(upload, Alignment.MIDDLE_RIGHT);          
       
        addComponent(column);
    }
}
