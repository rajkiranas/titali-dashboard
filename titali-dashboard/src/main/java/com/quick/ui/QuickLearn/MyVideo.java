/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.ui.QuickLearn;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.geo.impl.GeoRssWhere;
import com.google.gdata.data.media.mediarss.MediaCategory;
import com.google.gdata.data.media.mediarss.MediaDescription;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.media.mediarss.MediaTitle;
import com.google.gdata.data.youtube.FormUploadToken;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.data.youtube.YouTubeNamespace;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.google.gwt.canvas.client.Canvas;
import com.quick.bean.QuickLearn;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rajkiran
 */
public class MyVideo extends VerticalLayout{
    
    public MyVideo(){
        
    }
    
    public MyVideo(QuickLearn quickLearn){
            setSizeFull();
            setSpacing(true);
            setMargin(true);

            if (quickLearn != null) {
            try {
                VideoEntry newEntry = new VideoEntry();

                YouTubeMediaGroup mg = newEntry.getOrCreateMediaGroup();
                mg.setTitle(new MediaTitle());
                mg.getTitle().setPlainTextContent("My Test Movie");
                mg.addCategory(new MediaCategory(YouTubeNamespace.CATEGORY_SCHEME, "Autos"));
                mg.setKeywords(new MediaKeywords());
                mg.getKeywords().addKeyword("cars");
                mg.getKeywords().addKeyword("funny");
                mg.setDescription(new MediaDescription());
                mg.getDescription().setPlainTextContent("My description");
                mg.setPrivate(false);
                mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "mydevtag"));
                mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "anotherdevtag"));

                newEntry.setGeoCoordinates(new GeoRssWhere(37.0, -122.0));
    // alternatively, one could specify just a descriptive string
    // newEntry.setLocation("Mountain View, CA");

                URL uploadUrl = new URL("http://gdata.youtube.com/action/GetUploadToken");
               
                YouTubeService service= authenticate();
                FormUploadToken token = service.getFormUploadToken(uploadUrl, newEntry);

                System.out.println(token.getUrl());
                System.out.println(token.getToken());
                System.out.println("kid="+newEntry.getId());
                addComponent(new HtmlBrowseForm(token.getUrl(),token.getToken()));
       
                
                
                
                //addComponent(richTextArea);
                //setExpandRatio(richTextArea, 2);
               // richTextArea.setValue(quickLearn.getLectureNotes());

                if (quickLearn.getLectureNotesInformation() != null && !quickLearn.getLectureNotesInformation().equals("")) {
                    TextArea notes = new TextArea("Notes information");
                    notes.setValue(quickLearn.getLectureNotesInformation());
                    notes.setSizeFull();
                    addComponent(notes);
                    setExpandRatio(notes, 0.5f);

                }
            } catch (ServiceException ex) {
                Logger.getLogger(MyVideo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MyVideo.class.getName()).log(Level.SEVERE, null, ex);
            } 
            }else{
                addComponent(new Label(" <p2><B> No data found on this topic </B></p2> ",ContentMode.HTML));
            }
            //setComponentAlignment(richTextArea, ALIGNMENT_DEFAULT);


            
            
            
              
    }
   

    
    
    private static YouTubeService authenticate()
  {
      String username = "titali.mitpune@gmail.com";
    String password = "mitpune@1";
    String developerKey = "AI39si7F0w3vOlTx4Vws7uqc7i_BUwbVPGYeuf_aRrBy_ZC6efJdZV-nilhoar3Mq_PAGt8cWyzuGrJiYW49Sr8wfy_89yn_Ow";
    //boolean help = parser.containsKey("help", "h");

    

    YouTubeService service = new YouTubeService("gdataSample-YouTubeAuth-1",developerKey);

    try {
      service.setUserCredentials(username, password);
    } catch (AuthenticationException e)
    {
        e.printStackTrace();        
      System.out.println("Invalid login credentials.");
      System.exit(1);
    }
    return service;
  }
}
