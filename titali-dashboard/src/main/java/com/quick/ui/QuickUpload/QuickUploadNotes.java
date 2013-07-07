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
import com.vaadin.event.FieldEvents;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Sonali Sangle
 */
public class QuickUploadNotes extends VerticalLayout implements Property.ValueChangeListener{
   
    private RichTextArea notesRichTextArea;
    private QuickUpload quickUpload;
    
    public QuickUploadNotes(final QuickUpload quickUpload) {
        this.quickUpload=quickUpload;
        setSizeFull();
        setSpacing(true);
        setMargin(true);
        notesRichTextArea = new RichTextArea();
        notesRichTextArea.setSizeFull();
        addComponent(notesRichTextArea);
        setExpandRatio(notesRichTextArea, 2); 
        notesRichTextArea.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
               if(notesRichTextArea!=null){
                      quickUpload.setNotes(notesRichTextArea.getValue());  
                }
            }
        });
        
//        if(notesRichTextArea!=null){
//          quickUpload.setNotes(notesRichTextArea.getValue());  
//        }else{
//          quickUpload.setNotes("no data");  
//        }
    }
    
    
    

    public QuickUploadNotes(MasteParmBean quickLearn,final QuickUpload quickUpload) {
        this.quickUpload=quickUpload;
        setSizeFull();
        setSpacing(true);
        setMargin(true);

        if (quickLearn != null) {
            notesRichTextArea = new RichTextArea();
            notesRichTextArea.setSizeFull();
            addComponent(notesRichTextArea);
            setExpandRatio(notesRichTextArea, 2); 
            notesRichTextArea.setValue(quickLearn.getLectureNotes());
            quickUpload.setNotes(notesRichTextArea.getValue());  
            notesRichTextArea.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
               if(notesRichTextArea!=null){
                      quickUpload.setNotes(notesRichTextArea.getValue());  
                }
            }
        });
//            if (quickLearn.getLectureNotesInformation() != null && !quickLearn.getLectureNotesInformation().equals("")) {
//                TextArea notes = new TextArea("Notes information");
//                notes.setValue(quickLearn.getLectureNotesInformation());
//                notes.setSizeFull();
//                addComponent(notes);
//                setExpandRatio(notes, 0.5f);
//
//            }

        }else{
            addComponent(new Label(" <p2><B> No data found on this topic </B></p2> ",ContentMode.HTML));
        }
    }

    @Override
    public void valueChange(ValueChangeEvent event) {
        
    }
    
}
