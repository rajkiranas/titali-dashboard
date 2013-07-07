/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.demo.student.ui;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

/**
 *
 * @author suyogn
 */
public class DashBoardVideoPlayer extends VerticalLayout {
    
    public DashBoardVideoPlayer(){
        setSizeFull();
        setSpacing(true);
        Component c = buildVideoPlayer();
        addComponent(c);
        setExpandRatio(c, 2);
        
        Component c1 =buildVideoDetailsLayout();
        addComponent(c1);
        setExpandRatio(c1,1.5f);
    }

    private Component buildVideoPlayer() {
        Flash sample = new Flash(null, new ExternalResource(
               "http://www.youtube.com/v/KsvvF1zgMQM&hl=en_US&fs=1&"));
       
        sample.setParameter("allowFullScreen", "true");
        sample.setWidth(520.0f, Unit.PIXELS);
        sample.setHeight(350.0f, Unit.PIXELS);
        

            
            //URL mediaURL = new URL("https://www.youtube.com/watch?v=9e_89klM9ek");
            
            //row.addComponent(createPanel(new TopSixTheatersChart()));
          return sample;
    }

    private Component buildVideoDetailsLayout() {
         TextArea notes = new TextArea();
         notes.setValue("Remember to:\n· Zoom in and out in the Sales view\n· Filter the transactions and drag a set of them to the Reports tab");
         notes.setWidth("97%");
         return notes;
    }
    
}
