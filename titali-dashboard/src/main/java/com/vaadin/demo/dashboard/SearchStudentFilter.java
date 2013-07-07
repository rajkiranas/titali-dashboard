/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaadin.demo.dashboard;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

/**
 *
 * @author sonalis
 */
public class SearchStudentFilter extends Window implements Button.ClickListener {

    private StudentView studentView;
    private Button applyFilterbtn;
    private ComboBox searchCriteriacmb;
    private TextField inputCriteriaval; 
    
    public SearchStudentFilter(StudentView  studentView) {
        this.studentView=studentView;
        VerticalLayout l = new VerticalLayout();
        l.setSpacing(true);
        setCaption("Search Student");
        setContent(l);
        center();
        setModal(true);
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(false);
        addStyleName("no-vertical-drag-hints");
        addStyleName("no-horizontal-drag-hints");
        
        HorizontalLayout details = new HorizontalLayout();
        details.setSpacing(true);
        details.setMargin(true);
        l.addComponent(details);
        
        FormLayout fields = new FormLayout();
        fields.setWidth("35em");
        fields.setSpacing(true);
        fields.setMargin(true);
        details.addComponent(fields);       
        
        searchCriteriacmb=new ComboBox("Select Criteria");
        searchCriteriacmb.setWidth("180px");
        searchCriteriacmb.setNewItemsAllowed(false);
        searchCriteriacmb.setNullSelectionAllowed(false);
        searchCriteriacmb.setImmediate(false);
        searchCriteriacmb.setInputPrompt("Select");
        searchCriteriacmb.addItem("Select"); 
        searchCriteriacmb.setValue("Select"); 
        searchCriteriacmb.addItem("Roll No"); 
        searchCriteriacmb.addItem("Student Name");               
        searchCriteriacmb.addItem("Division");
        searchCriteriacmb.addItem("Standard");
        fields.addComponent(searchCriteriacmb);       
       
        inputCriteriaval=new TextField("Input Criteria");
        inputCriteriaval.setWidth("180px");
        fields.addComponent(inputCriteriaval);        
        
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName("footer");
        footer.setWidth("100%");
        footer.setMargin(true);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setMargin(false);
        buttons.setSpacing(true);
        buttons.setSizeUndefined();
        
        applyFilterbtn= new Button("Apply Filter",(Button.ClickListener)this);
        applyFilterbtn.addStyleName("wide");
        applyFilterbtn.addStyleName("default");
        applyFilterbtn.setImmediate(true);
        buttons.addComponent(applyFilterbtn);      
        
        Button ok = new Button("Close");
        ok.addStyleName("wide");
        ok.addStyleName("default");
        ok.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                close();
            }
        });
        buttons.addComponent(ok);
        footer.addComponent(buttons);
        footer.setComponentAlignment(buttons, Alignment.TOP_RIGHT);
        
        l.addComponent(footer);
        
    }

    
    
    
    @Override
    public void buttonClick(ClickEvent event) {
       final Button source=event.getButton();
       if(source==applyFilterbtn){
           if(validateSearchFilter()){
               String searchCriteria=String.valueOf(searchCriteriacmb.getValue());
               String searchValue=String.valueOf(inputCriteriaval.getValue());
               studentView.searchFilterCriteria(searchCriteria,searchValue);
               this.close(); 
           }
        }
    }

    private boolean validateSearchFilter() {
       if((searchCriteriacmb.getValue()).equals("Select")){
            Notification.show("Please select criteria",Notification.Type.WARNING_MESSAGE); 
            return false;         
       }else if((inputCriteriaval.getValue()).equals("")){
            Notification.show("Please enter input criteria",Notification.Type.WARNING_MESSAGE); 
            return false;
       }
        
        return true;
    }

    
}
