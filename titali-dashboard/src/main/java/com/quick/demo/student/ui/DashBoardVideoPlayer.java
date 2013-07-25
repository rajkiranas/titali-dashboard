/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.demo.student.ui;

import com.vaadin.event.MouseEvents;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
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
        //Component c = buildVideoPlayer();
        addComponent(getVideoPathLayout());
        //setExpandRatio(c, 2.5f);
        //setExpandRatio(addStartStopButtons(),0.5f);
        //addComponent(addStartStopButtons());        
    }

    Video sample = new Video(null, new FileResource(new File(
               "/home/rajkirans/NetBeansProjects/project/video/bbb_theora_486kbit.ogv")));
    private Button play;
    private Button stop;
    private TextField txtVideoPath=new TextField();
    
    private Component buildVideoPlayer() {
        
       
        //sample.setParameter("allowFullScreen", "true");
        sample.setWidth(500.0f, Sizeable.Unit.PIXELS);
        sample.setHeight(250.0f, Sizeable.Unit.PIXELS);
        

            
            //URL mediaURL = new URL("https://www.youtube.com/watch?v=9e_89klM9ek");
            
            //row.addComponent(createPanel(new TopSixTheatersChart()));
          return sample;
    }
    
    private Embedded getUploadImage() {
        
       
        ThemeResource resource = new ThemeResource("icons/Cloud-upload-icon.jpg");
        
        Embedded embedded_UserImage = new Embedded();
        embedded_UserImage.setImmediate(false);
        embedded_UserImage.setWidth("400px");
        embedded_UserImage.setHeight("250px");
        embedded_UserImage.setSource(resource);
        embedded_UserImage.setType(1);
        embedded_UserImage.setMimeType("image/jpg");
        embedded_UserImage.addClickListener(new MouseEvents.ClickListener() {

            @Override
            public void click(MouseEvents.ClickEvent event) {
                System.out.println("clicked");
            }
        });
        
        return embedded_UserImage;
    }
    
    
    
     private VerticalLayout getVideoPathLayout() {
       VerticalLayout layout= new VerticalLayout();
       
         txtVideoPath.setInputPrompt("Enter server video path");
         txtVideoPath.setCaption("Video file");
         txtVideoPath.setWidth("70%");
         layout.setSizeFull();
         layout.addComponent(txtVideoPath);
         layout.setComponentAlignment(txtVideoPath, Alignment.MIDDLE_CENTER);
       
       return layout;
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
        
        //setComponentAlignment(buttonLayout, Alignment.TOP_LEFT);

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

    /**
     * @return the videoPath
     */
    public String getVideoPath() {
        return txtVideoPath.getValue();
    }

    /**
     * @param videoPath the videoPath to set
     */
    public void setVideoPath(String videoPath) {
        this.txtVideoPath.setValue(videoPath);
    }
    
}
