/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaadin.demo.dashboard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quick.bean.MasteParmBean;
import com.quick.bean.QuickLearn;
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
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    }
    
    public StudQuickLearn(){
        getStandardList();
        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setSpacing(true);
        top.addStyleName("toolbar");
        addComponent(top);
        final Label title = new Label("My Dashboard");
        title.setSizeUndefined();
        title.addStyleName("h1");
        top.addComponent(title);
        top.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        top.setExpandRatio(title, 1);

      
        HorizontalLayout row = new HorizontalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        addComponent(row);
        setExpandRatio(row, 1.5f);
        row.addComponent(CreateFirstPaneview());
        //row.addComponent(createPanel(boardDataProvider.getWhatsNewForme(whatsnewsList)));
        row.addComponent(buildTabSheetLayout());

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
       
        column.setSpacing(true);
        ComboBox subject = new ComboBox();
        subject.setInputPrompt("subject");
        subject.setImmediate(true);
        for(MasteParmBean mpb:stdlist){
            subject.addItem(mpb.getSub());
        }
        
        
        subject.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                setSelectedSub(""+event.getProperty().getValue());
                updateTopicsTable();
                notes.setValue("");
                uploadId=0;
                
            }
        });
        column.addComponent(subject);
        column.setExpandRatio(subject, 1);
//        column.addComponent(new TopGrossingMoviesChart());
        // 
        
          quickLearnTable = new StudQuickLearnTable(this);
          column.addComponent(quickLearnTable);
          column.setExpandRatio(quickLearnTable,1.5f);
            
      
        
        notes = new TextArea("My short notes/feedback");
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
        column.setExpandRatio(notes, 0.7f);
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
           return editors;
    }
    
    
    
      public List<MasteParmBean> getTopicList(){
          return getTopicListForMe(getSelectedSub());
      }
      
      
      
      
       @Override
    public void valueChange(ValueChangeEvent event) {
       Property property=event.getProperty();
        if(property==quickLearnTable){            
            Set<MasteParmBean> topic=(Set<MasteParmBean>) property.getValue();
            for(MasteParmBean u:topic){
               uploadId = u.getUploadId();  
            }            
             setStudQuikLearnDetails(getStudentQuickLearnDetails());
             updateQuickLearnTabSheet();
             notes.setValue(getUserNotes());
        }
    }
   
       
       
        private void updateQuickLearnTabSheet() {
           editors.removeAllComponents();
           editors.addTab(new DashBoardVideoPlayer(),"Video");
           editors.addTab(new MyNotes(getStudQuikLearnDetails()), "Notes");
           editors.addTab(new MyOtherNotes(getStudQuikLearnDetails()), "OtherNotes");
           editors.addTab(new PreviousQuestion(getStudQuikLearnDetails()), "Previous Questions");

      }

           
    
    
    public void getStandardList(){
          try {
           

            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/MasterParam/stdsub");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            try {
                inputJson.put("standard", "I");
                inputJson.put("division", "A-1");
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
                inputJson.put("userName", getSession().getAttribute("UserName"));
                
              
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);

          
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);
            Notification.show(outNObject.getString(GlobalConstants.STATUS), Notification.Type.WARNING_MESSAGE);
            
        } catch (JSONException ex) {
        }
        
    }   
}
