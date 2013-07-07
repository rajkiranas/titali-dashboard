/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.ui.QuickLearn;

import com.quick.bean.QuickLearn;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author rajkiran
 */
public class MyOtherNotes extends VerticalLayout {

    public MyOtherNotes() {
    }

    public MyOtherNotes(QuickLearn quickLearn) {

        setSizeFull();
        setSpacing(true);
        setMargin(true);

        if (quickLearn != null) {
            RichTextArea richTextArea = new RichTextArea();
            richTextArea.setSizeFull();
            //richTextArea.setHeight("98%");
            addComponent(richTextArea);
            setExpandRatio(richTextArea, 2);
            richTextArea.setValue(quickLearn.getOtherNotes());

            if (quickLearn.getOtherNotesInformation() != null && !quickLearn.getOtherNotesInformation().equals("")) {
                TextArea notes = new TextArea("Other notes information");
                notes.setValue(quickLearn.getOtherNotesInformation());
                notes.setSizeFull();
                addComponent(notes);
                setExpandRatio(notes, 0.5f);
            }

        } else {
            addComponent(new Label(" <p2><B> No data found on this topic </B></p2> ", ContentMode.HTML));

        }

    }
}
