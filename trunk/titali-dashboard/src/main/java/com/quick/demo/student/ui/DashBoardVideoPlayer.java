/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.demo.student.ui;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import java.io.File;

/**
 *
 * @author suyogn
 */
public class DashBoardVideoPlayer extends VerticalLayout implements Button.ClickListener{
    
    public DashBoardVideoPlayer(){
        setSizeFull();
        setSpacing(true);
        Component c = buildVideoPlayer();
        addComponent(c);
        setExpandRatio(c, 2.5f);
        setExpandRatio(addStartStopButtons(),0.5f);
        
    //    Component c1 =buildVideoDetailsLayout();
      //  addComponent(c1);
        //setExpandRatio(c1,1.5f);
    }

    Video sample = new Video(null, new FileResource(new File(
               "C:/Users/rajkiran/Desktop/video/bbb_theora_486kbit.ogv")));
    Button play;
    Button stop;
    
    private Component buildVideoPlayer() {
        
       
        //sample.setParameter("allowFullScreen", "true");
        sample.setWidth(520.0f, Sizeable.Unit.PIXELS);
        sample.setHeight(450.0f, Sizeable.Unit.PIXELS);
        

            
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

    private HorizontalLayout addStartStopButtons() {
        play= new Button("Play",this);
        stop= new Button("Stop",this);
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addComponent(play);
        buttonLayout.addComponent(stop);
        buttonLayout.setWidth("100%");
        addComponent(buttonLayout);
        setComponentAlignment(buttonLayout, Alignment.TOP_LEFT);

        return buttonLayout;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        Button b = event.getButton();
        if(b==play)
        {
            sample.play();
        }
        else if(b==stop)
        {
            sample.pause();
        }
    }
    
}
