/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaadin.demo.dashboard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quick.bean.MasteParmBean;
import com.quick.bean.QuickLearn;
import com.quick.bean.Userprofile;
import com.quick.container.StudQuickLearnContainer;
import com.vaadin.data.Property;
import com.quick.data.Generator;
import com.quick.demo.student.ui.DashBoardVideoPlayer;
import com.quick.global.GlobalConstants;
import com.quick.table.StudQuickLearnTable;
import com.quick.ui.QuickLearn.MyNotes;
import com.quick.ui.QuickLearn.MyOtherNotes;
import com.quick.ui.QuickLearn.MyVideo;
import com.quick.ui.QuickLearn.PreviousQuestion;
import com.quick.utilities.UIUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author suyogn
 */
public class StudQuickLearn extends VerticalLayout implements View,Property.ValueChangeListener {
    
    private TabSheet editors;
    private List<MasteParmBean> stdlist;
    private String selectedSub="";
    private int uploadId=0;
    private StudQuickLearnTable quickLearnTable =null;
    private TextArea notes;
    private String userNotes;
    VerticalLayout column = new VerticalLayout();
    QuickLearn studQuikLearnDetails;
    ComboBox cbSubject = new ComboBox();
    private int uploadIdToNavigate;
    private String topicForNotification;
    private  Userprofile loggedInUserProfile = null;
    private  SelectedTabChangeListener tabChangeListener;

    public String getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }
    
    
    public QuickLearn getStudQuikLearnDetails() {
        return studQuikLearnDetails;
    }

    public void setStudQuikLearnDetails(QuickLearn studQuikLearnDetails) {
        this.studQuikLearnDetails = studQuikLearnDetails;
    }

    public String getTopicForNotification() {
        return topicForNotification;
    }

    public void setTopicForNotification(String topicForNotification) {
        this.topicForNotification = topicForNotification;
    }
    
    
    
    
    public String getSelectedSub() {
        return selectedSub;
    }

    public void setSelectedSub(String selectedSub) {
        this.selectedSub = selectedSub;
    }


    
    
    @Override
    public void enter(ViewChangeEvent event) {
        setSizeFull();
        addStyleName("schedule");
        
        //below steps are done for selected the upload item topic 
        //on which the user has clicked from dashboard - whats new table
        
        //getting the uploadIdToNavigate
         loggedInUserProfile =((Userprofile)getSession().getAttribute(GlobalConstants.CurrentUserProfile));
        String strUploadIdToNavigate=GlobalConstants.emptyString+getSession().getAttribute("uploadIdToNavigate");
        if(!strUploadIdToNavigate.equals("null"))
        {
            uploadIdToNavigate = Integer.parseInt(strUploadIdToNavigate);
        }
        
        //if it is not zero then navigate to it otherwise first item of the topic table gets selected
        if(uploadIdToNavigate!=0){
             setSelectedTableRecord();
        }
       
        
    }
    
    
    /**
     * this method is used to select the item on upload table for which user has asked from dashboard whats new table
     */
     private void setSelectedTableRecord() {
         
         Iterator it  = quickLearnTable.getItemIds().iterator();
              while(it.hasNext()){
                  Object itemId = it.next();
                   Item i =  quickLearnTable.getItem(itemId);
                   int uploadId = Integer.parseInt(i.getItemProperty("uploadId").getValue().toString());
                   if(uploadId==uploadIdToNavigate){
                       quickLearnTable.select(itemId);
                       break;
                   }
              }  
         
      }
    
    public StudQuickLearn()
    {
        getStandardList();
        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setSpacing(true);
        top.setMargin(new MarginInfo(true, true, false, true));
        top.addStyleName("toolbar");
        addComponent(top);
        final Label title = new Label("Learn now");
        title.setSizeUndefined();
        title.addStyleName("h1");
        top.addComponent(title);
        top.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        top.setExpandRatio(title, 1);

        //top.addComponent();
        HorizontalLayout h = new HorizontalLayout();
        Label lblSubject =new Label("Subject");
        
        h.addComponent(lblSubject);
        h.setComponentAlignment(lblSubject,Alignment.MIDDLE_LEFT);
        h.addComponent(cbSubject);
        h.setComponentAlignment(cbSubject,Alignment.MIDDLE_RIGHT);
        h.setSpacing(true);
        h.setMargin(true);
        
        top.addComponent(h);
        top.setComponentAlignment(h, Alignment.MIDDLE_RIGHT);
      
        HorizontalLayout row = new HorizontalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        addComponent(row);
        setExpandRatio(row, 1.5f);
        Component c=buildTabSheetLayout();
        row.addComponent(CreateFirstPaneview());
        //row.addComponent(createPanel(boardDataProvider.getWhatsNewForme(whatsnewsList)));
        row.addComponent(c);

    }

    private CssLayout createPanel(Component content) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();

        Button configure = new Button();
        configure.addStyleName("configure");
        configure.addStyleName("icon-cog");
        configure.addStyleName("icon-only");
        configure.addStyleName("borderless");
        configure.setDescription("Configure");
        configure.addStyleName("small");
        configure.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Notification.show("Not implemented in this demo");
            }
        });
        panel.addComponent(configure);

        panel.addComponent(content);
        return panel;
    }

   

    Window notifications;

    private void buildNotifications(Button.ClickEvent event) {
        notifications = new Window("Notifications");
        VerticalLayout l = new VerticalLayout();
        l.setMargin(true);
        l.setSpacing(true);
        notifications.setContent(l);
        notifications.setWidth("300px");
        notifications.addStyleName("notifications");
        notifications.setClosable(false);
        notifications.setResizable(false);
        notifications.setDraggable(false);
        notifications.setPositionX(event.getClientX() - event.getRelativeX());
        notifications.setPositionY(event.getClientY() - event.getRelativeY());
        notifications.setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);

        Label label = new Label(
                "<hr><b>"
                        + Generator.randomFirstName()
                        + " "
                        + Generator.randomLastName()
                        + " created a new report</b><br><span>25 minutes ago</span><br>"
                        + Generator.randomText(18), ContentMode.HTML);
        l.addComponent(label);

        label = new Label("<hr><b>" + Generator.randomFirstName() + " "
                + Generator.randomLastName()
                + " changed the schedule</b><br><span>2 days ago</span><br>"
                + Generator.randomText(10), ContentMode.HTML);
        l.addComponent(label);
    }

    private Component CreateFirstPaneview() {
        
        quickLearnTable = new StudQuickLearnTable(this);
        notes = new TextArea("My short notes for this topic");
        
        column.setSpacing(true);
        cbSubject.setInputPrompt("subject");
        cbSubject.setImmediate(true);
        
         if (stdlist!=null && !stdlist.isEmpty()) {
            for (MasteParmBean mpb : stdlist) {
                cbSubject.addItem(mpb.getSub());
            }
            
            
             cbSubject.addValueChangeListener(new Property.ValueChangeListener() {

            
            @Override
            public void valueChange(ValueChangeEvent event) {
                setSelectedSub(""+event.getProperty().getValue());
                updateTopicsTable();
                notes.setValue("");
                uploadId=0;
                
            }
        });
        
            cbSubject.setValue(stdlist.get(0).getSub());
            
            if (uploadIdToNavigate == 0) {
                quickLearnTable.select(quickLearnTable.firstItemId());
            } else {
               
                quickLearnTable.select(quickLearnTable.getData());
            }


        }

       
        
       
        /* column.addComponent(subject);
        column.setExpandRatio(subject, 1); */
//        column.addComponent(new TopGrossingMoviesChart());
        // 
        
          
          column.addComponent(quickLearnTable);
          column.setExpandRatio(quickLearnTable,1);
            
      
        
        
        notes.setSizeFull();
        notes.setInputPrompt("My short notes");
        
        notes.addBlurListener(new FieldEvents.BlurListener() {

            @Override
            public void blur(BlurEvent event) {
                setUserNotes(notes.getValue());
                if(uploadId!=0)
                updateUserShortNotes();
            }
        });
        column.addComponent(notes);
        column.setExpandRatio(notes, 0.5f);
        return column;
       
    }

    
       
    private void updateTopicsTable(){
      quickLearnTable.setContainerDataSource(StudQuickLearnContainer.getStudQuickLearnContainer(getTopicList()));
      quickLearnTable.setVisibleColumns(StudQuickLearnContainer.NATURAL_COL_ORDER_QUICKLEARN);
      quickLearnTable.setColumnHeaders(StudQuickLearnContainer.COL_HEADERS_ENGLISH_QUICKLEARN);
    }
    
    
    
    private Component buildTabSheetLayout() {
           editors = new TabSheet();
           editors.setImmediate(true);
           editors.setSizeFull();
           editors.addTab(new DashBoardVideoPlayer(),"Video");
           //editors.addTab(new TopGrossingMoviesChart(), "Chart");
           editors.addTab(new MyNotes(), "Notes");
           editors.addTab(new MyOtherNotes(), "OtherNotes");
           editors.addTab(new PreviousQuestion(), "Previous Questions");
            tabChangeListener = new TabSheet.SelectedTabChangeListener() {

            @Override
            public void selectedTabChange(SelectedTabChangeEvent event) {
                 //loggedInUserProfile will be null when this quick learn screen loads
                // and the control comes first to this tab change method before user profile gets its value in enter method
                if(loggedInUserProfile!=null){
                    sendWhosDoingWhatNotificationToStudents(editors.getTab(event.getTabSheet().getSelectedTab()).getCaption());
                }
            }
        };
            
         editors.addSelectedTabChangeListener(tabChangeListener);
           
          
           return editors;
    }
    
    
    
      public List<MasteParmBean> getTopicList(){
          return getTopicListForMe(getSelectedSub());
      }
      
      
      
      
       @Override
    public void valueChange(ValueChangeEvent event) {
       Property property=event.getProperty();
        if(property==quickLearnTable){  
            
            MasteParmBean selectedTopicRow=(MasteParmBean) property.getValue();
            
             uploadId = selectedTopicRow.getUploadId();  
             setTopicForNotification(selectedTopicRow.getTopic());         
             setStudQuikLearnDetails(getStudentQuickLearnDetails());
             
             //Temp removing Listner to avoid multiple notifiaction on tab change
             
             editors.removeSelectedTabChangeListener(tabChangeListener);
             updateQuickLearnTabSheet();
             
             // Restoring after Tabsheet Load
             editors.addSelectedTabChangeListener(tabChangeListener);
             notes.setValue(getUserNotes());
             
              //loggedInUserProfile will be null when this quick learn screen loads
                // and the control comes first to this tab change method before user profile gets its value in enter method
             if(loggedInUserProfile!=null){
                
                    sendWhosDoingWhatNotificationToStudents(GlobalConstants.going_through);
                }
        }
    }
   
       
       
        private void updateQuickLearnTabSheet() {
//           editors.removeAllComponents();
           //editors.addTab(new DashBoardVideoPlayer(),"Video");
//           editors.addTab(new MyNotes(getStudQuikLearnDetails()), "Notes");
//           editors.addTab(new MyOtherNotes(getStudQuikLearnDetails()), "OtherNotes");
//           editors.addTab(new PreviousQuestion(getStudQuikLearnDetails()), "Previous Questions");
//           
           buildTabSheetLayout(getStudQuikLearnDetails().getVideoPath(),getStudQuikLearnDetails().getLectureNotes(),getStudQuikLearnDetails().getOtherNotes(),getStudQuikLearnDetails().getPreviousQuestion());

      }
        
        /////////////////////////////////////////////////
        private VerticalLayout buildTabSheetLayout(String videoPath, String notes, String otherNotes, String previousQuestions) {
//        VerticalLayout mainVertical=new VerticalLayout();
//        
           editors.removeAllComponents();
           editors.setSizeFull();
           //player = new DashBoardVideoPlayer();
           editors.addTab(getVideoPathLayout(videoPath),"Video");
           editors.addTab(getNotesLayout(notes), "Notes");
           editors.addTab(getOtherNotesLayout(otherNotes), "Other Notes");
           editors.addTab(getPreviousQuestionsLayout(previousQuestions), "Previous Questions");
//           CssLayout cssTabsheetLayout = UIUtils.createPanel(editors);
//           
//           mainVertical.addComponent(cssTabsheetLayout);
//           mainVertical.setExpandRatio(cssTabsheetLayout, 2);
//           mainVertical.setWidth("100%");
//           mainVertical.setHeight("97%");
           
           //CssLayout aboutLearnLayout =  UIUtils.createPanel(buildTopicDetailsLayout());
           //aboutLearnLayout.setCaption("Topic Information");
           
//           mainVertical.addComponent(aboutLearnLayout);
//           mainVertical.setExpandRatio(aboutLearnLayout, 1);
           return null;
    }
    

    private Label lableNoVideo;
    private Video vPlayer;

    private VerticalLayout getVideoPathLayout(String videoPath) {
       VerticalLayout layout= new VerticalLayout();
       layout.setSpacing(true);
       layout.setSizeFull();
       
       if(videoPath!=null)
       {
           // video is available, show it on video player
           vPlayer = new Video();
           vPlayer.setImmediate(true);
           vPlayer.setWidth("100%");
           vPlayer.setHeight("100%");
           vPlayer.addSource(new FileResource(new File(videoPath)));
           //vPlayer.addSource(new ExternalResource("file:/"+videoPath));
           layout.addComponent(vPlayer);
           layout.setComponentAlignment(vPlayer, Alignment.MIDDLE_CENTER);
           
           
       }
       else
       {
         // not video available, accept path from user
         lableNoVideo=new Label("<b><h3>No video available for this topic.</h3></b>", ContentMode.HTML);
         lableNoVideo.setImmediate(true);
         //txtVideoPath.setInputPrompt("Enter server video path");
         lableNoVideo.setCaption("About Video");
         lableNoVideo.setWidth("90%");
         //txtVideoPath.addValueChangeListener(this);
         layout.addComponent(lableNoVideo);
         layout.setComponentAlignment(lableNoVideo, Alignment.MIDDLE_CENTER);
         
       }
       
       return layout;
    }
    
    private TextArea notesTextArea;
    
    private VerticalLayout getNotesLayout(String strNotes) {
        VerticalLayout layout= new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setMargin(true);
        
        notesTextArea = new TextArea();
        
        notesTextArea.setSizeFull();
        if(strNotes!=null)
        {
            notesTextArea.setValue(strNotes);
        }
        
        notesTextArea.setImmediate(true);
        notesTextArea.addValueChangeListener(this);
        notesTextArea.setReadOnly(true);
        
        layout.addComponent(notesTextArea);
        layout.setExpandRatio(notesTextArea, 2);
        
        return layout;
        
    }
    
    private TextArea otherNotesTextArea;
    
    private VerticalLayout getOtherNotesLayout(String otherNotes) {
        VerticalLayout layout= new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setMargin(true);
        
        otherNotesTextArea = new TextArea();
        
        otherNotesTextArea.setSizeFull();
        if(otherNotes!=null)
        {
            otherNotesTextArea.setValue(otherNotes);
        }
        
        otherNotesTextArea.setImmediate(true);
        otherNotesTextArea.addValueChangeListener(this);
        otherNotesTextArea.setReadOnly(true);
        
        layout.addComponent(otherNotesTextArea);
        layout.setExpandRatio(otherNotesTextArea, 2);
        
        return layout;
        
    }
    
    private TextArea previousQuestionsTextArea;
    private VerticalLayout getPreviousQuestionsLayout(String previousQuestions) {
        VerticalLayout layout= new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setMargin(true);
        
        previousQuestionsTextArea = new TextArea();
        
        previousQuestionsTextArea.setSizeFull();
        if(previousQuestions!=null)
        {
            previousQuestionsTextArea.setValue(previousQuestions);
        }
        
        previousQuestionsTextArea.setImmediate(true);
        previousQuestionsTextArea.addValueChangeListener(this);
        previousQuestionsTextArea.setReadOnly(true);
        
        layout.addComponent(previousQuestionsTextArea);
        layout.setExpandRatio(previousQuestionsTextArea, 2);
        
        return layout;
        
    }
        ////////////////////////////////////////////////

           
    
    
    public void getStandardList(){
          try {
           

            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/MasterParam/stdsub");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            try {
                inputJson.put("standard", "MCA-I");
                inputJson.put("division", "A");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);

          
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<MasteParmBean>>() {
            }.getType();
            
            stdlist = new Gson().fromJson(outNObject.getString(GlobalConstants.STDSUBLIST), listType);
            
        } catch (JSONException ex) {
        }

    }

    private List<MasteParmBean> getTopicListForMe(String selectedSub) {
        
         List<MasteParmBean>list =null;
          try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/QuickLearn/whatsNewforme");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            try {
                inputJson.put("subject", selectedSub);
              
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);

          
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<MasteParmBean>>() {
            }.getType();
            
            list= new Gson().fromJson(outNObject.getString(GlobalConstants.WHATSNEW), listType);
            
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
          return list;
    }

    private QuickLearn getStudentQuickLearnDetails() {
       
          List<QuickLearn>list =null;
          try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/QuickLearn/quickLearn");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            try {
                inputJson.put("uploadId", uploadId);
              
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);

          
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);
            setUserNotes(outNObject.getString(GlobalConstants.MYQUICKNOTEs));
            Type listType = new TypeToken<ArrayList<QuickLearn>>() {
            }.getType();
            
            list= new Gson().fromJson(outNObject.getString(GlobalConstants.QUICKLEARNLIST), listType);
            
        } catch (JSONException ex) {
        }
          if(!list.isEmpty()){
              return list.get(0);
          }else{
              return null;
          }
          

        
    }

   
    private void updateUserShortNotes(){
       try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/QuickLearn/saveMyShortNotes");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            try {
                inputJson.put("uploadId", uploadId);
                inputJson.put("userNotes", getUserNotes());
                Userprofile loggedinProfile= (Userprofile)getSession().getAttribute(GlobalConstants.CurrentUserProfile);
                inputJson.put("userName", loggedinProfile.getUsername());
                
              
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);

          
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);
            Notification.show(outNObject.getString(GlobalConstants.STATUS), Notification.Type.WARNING_MESSAGE);
            
        } catch (JSONException ex) 
        {
            ex.printStackTrace();
        }
        
    }  
    
    
    private void sendWhosDoingWhatNotificationToStudents(String activity){
        try {
            JSONObject inputRequest = new JSONObject();
            
               
                inputRequest.put("name",loggedInUserProfile.getName());
                inputRequest.put("uploadId",uploadId);
                inputRequest.put("doingwhat",activity);
                inputRequest.put("div",loggedInUserProfile.getDiv());
                inputRequest.put("std",loggedInUserProfile.getStd());
                inputRequest.put("sub",cbSubject.getValue());
                inputRequest.put("topic",getTopicForNotification());
                                   
            Client client = Client.create();
                WebResource webResource = client.resource(GlobalConstants.getProperty(GlobalConstants.SEND_WHOS_DOING_WHAT_NOTIFICATIONS));
                ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputRequest);

                /*
                 * if (response.getStatus() != 201) { throw new RuntimeException("Failed
                 * : HTTP error code : " + response.getStatus()); }
                 */

                String output = response.getEntity(String.class);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

   
}
