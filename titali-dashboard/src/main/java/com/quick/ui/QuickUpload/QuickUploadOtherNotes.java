/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.ui.QuickUpload;

import com.quick.bean.MasteParmBean;
import com.quick.bean.QuickLearn;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.demo.dashboard.QuickUpload;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Sonali Sangle
 */
public class QuickUploadOtherNotes extends VerticalLayout {

    private RichTextArea otherNotesrichTextArea;
    private QuickUpload quickUpload;
    
    public QuickUploadOtherNotes(final QuickUpload quickUpload) {
         this.quickUpload=quickUpload;
         setSizeFull();
         setSpacing(true);
         setMargin(true);
         otherNotesrichTextArea= new RichTextArea();
         otherNotesrichTextArea.setSizeFull();       
         addComponent(otherNotesrichTextArea);
         setExpandRatio(otherNotesrichTextArea, 2);
         
         otherNotesrichTextArea.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
               if(otherNotesrichTextArea!=null){
                   quickUpload.setOtherNotes(otherNotesrichTextArea.getValue());  
               } 
            }
        });
//         if(otherNotesrichTextArea!=null){
//          quickUpload.setOtherNotes(otherNotesrichTextArea.getValue());  
//         }else{
//          quickUpload.setOtherNotes("no data");  
//         }
    }

    public QuickUploadOtherNotes(MasteParmBean quickLearn,final QuickUpload quickUpload) {
        this.quickUpload=quickUpload;
        setSizeFull();
        setSpacing(true);
        setMargin(true);

        if (quickLearn != null) {
             otherNotesrichTextArea= new RichTextArea();
             otherNotesrichTextArea.setSizeFull();       
             addComponent(otherNotesrichTextArea);
             setExpandRatio(otherNotesrichTextArea, 2);
             otherNotesrichTextArea.setValue(quickLearn.getOtherNotes());
             quickUpload.setOtherNotes(otherNotesrichTextArea.getValue()); 
             otherNotesrichTextArea.addValueChangeListener(new Property.ValueChangeListener() {

             @Override
             public void valueChange(ValueChangeEvent event) {
               if(otherNotesrichTextArea!=null){
                   quickUpload.setOtherNotes(otherNotesrichTextArea.getValue());  
               } 
              }
             });
//            if (quickLearn.getOtherNotesInformation() != null && !quickLearn.getOtherNotesInformation().equals("")) {
//                TextArea notes = new TextArea("Other notes information");
//                notes.setValue(quickLearn.getOtherNotesInformation());
//                notes.setSizeFull();
//                addComponent(notes);
//                setExpandRatio(notes, 0.5f);
//            }

        } else {
            addComponent(new Label(" <p2><B> No data found on this topic </B></p2> ", ContentMode.HTML));

        }

    }
}
