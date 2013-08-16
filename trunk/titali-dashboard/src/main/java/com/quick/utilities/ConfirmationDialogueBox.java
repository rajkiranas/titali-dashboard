/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.utilities;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

/**
 *
 * @author sapanaj
 */
public class ConfirmationDialogueBox extends Window implements Button.ClickListener
{
  
    Callback callback;
    Button btnYes=new Button("Yes",this);
    Button btnNo=new Button("No",this);
    String question;

    public ConfirmationDialogueBox(String caption,String question,Callback callback)
    {
        this.callback=callback;
        this.setCaption(caption);
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        //subWindow.setContent(subContent);
        subContent.setWidth("100%");
        subContent.setHeight("100%");
        
        // Put some components in it
        subContent.addComponent(new Label(question));
        HorizontalLayout h=new HorizontalLayout();
        h.addComponent(btnYes);
        h.addComponent(btnNo);
        h.setHeight("100%");
        h.setWidth("40%");
        subContent.addComponent(h);
        setContent(subContent);
        setHeight("20%");
        setWidth("30%");
        // Center it in the browser window
        //subWindow.center();
        this.center();
    }   
    
    public ConfirmationDialogueBox()
    {
        //setContent(new Label("Are you sure, you want to remove this ?"));
        
        // Create a sub-window and set the content
        //subWindow = new Window("Confirmation");
        this.setCaption("Confirmation");
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        //subWindow.setContent(subContent);
        subContent.setWidth("100%");
        subContent.setHeight("100%");
        
        // Put some components in it
        subContent.addComponent(new Label("Are you sure, you want to remove this ?"));
        HorizontalLayout h=new HorizontalLayout();
        h.addComponent(btnYes);
        h.addComponent(btnNo);
        h.setHeight("100%");
        h.setWidth("40%");
        subContent.addComponent(h);
        setContent(subContent);
        setHeight("20%");
        setWidth("30%");
        // Center it in the browser window
        //subWindow.center();
        this.center();
        
        // Open it in the UI
        //UI.getCurrent().addWindow(subWindow);
        
    }
    @Override
    public void buttonClick(ClickEvent event) 
    {
        Button button = event.getButton();
        if(getParent()!=null)
        {
            if(button==btnYes)
            {
                // passing true to confirm delete 
               callback.onDialogResult(true); 
            }
            if(button==btnNo)
            {
                // passing false to confirm delete
               //callback.onDialogResult(false);
                callback.onDialogResult(false); 
            }
            
            //after yes or no, do process and close this confirmation box
            UI.getCurrent().removeWindow(this);
            //getParent().removeWindow(this);
            ///UI.getCurrent().removeWindow(this);
        }
        
    }

    // Window subWindow;
    
//    protected void init(VaadinRequest request) {
//        // Some other UI content
//        setContent(new Label("Are you sure, you want to remove this ?"));
//        
//        // Create a sub-window and set the content
//        subWindow = new Window("Confirmation");
//        VerticalLayout subContent = new VerticalLayout();
//        subContent.setMargin(true);
//        subWindow.setContent(subContent);
//        
//        // Put some components in it
//        subContent.addComponent(btnYes);
//        subContent.addComponent(btnNo);
//        
//        // Center it in the browser window
//        subWindow.center();
//        
//        // Open it in the UI
//        setContent(subWindow);
//    }

    
    public interface Callback
    {
        public void onDialogResult(boolean isYes);
    }
}
