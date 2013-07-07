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
public class QuickUploadPreviousQuestion extends VerticalLayout {

    private RichTextArea preQuestrichTextArea ;
    private QuickUpload quickUpload;
    
    public QuickUploadPreviousQuestion(final QuickUpload quickUpload) {
        this.quickUpload=quickUpload;
        setSizeFull();
        setSpacing(true);
        setMargin(true);
        preQuestrichTextArea = new RichTextArea();
        preQuestrichTextArea.setSizeFull();
        addComponent(preQuestrichTextArea);
        setExpandRatio(preQuestrichTextArea, 2);
        
        preQuestrichTextArea.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                if(preQuestrichTextArea!=null){
                     quickUpload.setPreviousQuestions(preQuestrichTextArea.getValue());  
                }
             }
        });
        
//        if(preQuestrichTextArea!=null){
//          quickUpload.setPreviousQuestions(preQuestrichTextArea.getValue());  
//        }else{
//          quickUpload.setPreviousQuestions("no data");  
//        }
    }

    public QuickUploadPreviousQuestion(MasteParmBean quickLearn,final QuickUpload quickUpload) {
        this.quickUpload=quickUpload;
        setSizeFull();
        setSpacing(true);
        setMargin(true);

        if (quickLearn != null) {
            preQuestrichTextArea = new RichTextArea();
            preQuestrichTextArea.setSizeFull();
            addComponent(preQuestrichTextArea);
            setExpandRatio(preQuestrichTextArea, 2);
            preQuestrichTextArea.setValue(quickLearn.getPreviousQuestion());
            preQuestrichTextArea.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                if(preQuestrichTextArea!=null){
                     quickUpload.setPreviousQuestions(preQuestrichTextArea.getValue());  
                }
             }
        });  
            
//            if (quickLearn.getPreviousQuestionInformation() != null && !quickLearn.getPreviousQuestionInformation().equals("")) {
//                TextArea notes = new TextArea("Previous Question Information");
//                notes.setValue(quickLearn.getPreviousQuestionInformation());
//                notes.setSizeFull();
//                addComponent(notes);
//                setExpandRatio(notes, 0.5f);
//            }

        } else {
            addComponent(new Label(" <p2><B> No data found on this topic </B></p2> ", ContentMode.HTML));
        }


    }
}
