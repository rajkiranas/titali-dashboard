/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaadin.demo.dashboard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.quick.bean.MasteParmBean;
import com.quick.bean.QuickLearn;
import com.quick.bean.Userprofile;
import com.quick.container.StudQuickLearnContainer;
import com.quick.data.MasterDataProvider;
import com.quick.entity.Std;
import com.quick.entity.Sub;
import com.quick.global.GlobalConstants;
import com.quick.table.QuickUploadTable;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.quick.demo.student.ui.DashBoardVideoPlayer;
import com.quick.ui.QuickLearn.MyNotes;
import com.quick.ui.QuickLearn.MyOtherNotes;
import com.quick.ui.QuickLearn.PreviousQuestion;
import com.quick.ui.QuickUpload.QuickUploadNotes;
import com.quick.ui.QuickUpload.QuickUploadOtherNotes;
import com.quick.ui.QuickUpload.QuickUploadPreviousQuestion;
import com.quick.utilities.UIUtils;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import java.awt.peer.TextFieldPeer;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Sonali Sangle
 */
public class QuickUpload extends VerticalLayout implements View,Button.ClickListener,Property.ValueChangeListener{
    
    private TabSheet editors;
    private List<Std> standardList;
    private ComboBox standardtxt;
    private ComboBox subjecttxt;
    private List<QuickLearn> subjectList;
    private List<MasteParmBean> uploadedList;
    private TextField topictxt;
    private TextField topicTagstxt;
    private Button savebtn;
    private Button newbtn;
    private Button cancelbtn;
    private boolean isNewQuickUpload;
    private int uploadId =0;
    private String notes;
    private String otherNotes;
    private String previousQuestions;
    private QuickUploadTable quickUploadTable;
    private MasteParmBean quikLearnMasterParamDetails;
    private Userprofile loggedInProfile;
    private HorizontalLayout row;
    private CssLayout cssTabSheetLayout;
    private DashBoardVideoPlayer player;
    private HorizontalLayout topicInformationLayout;


    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        setSizeFull();
        addStyleName("schedule");
        loggedInProfile=(Userprofile)getSession().getAttribute(GlobalConstants.CurrentUserProfile);
    }
    
    public QuickUpload(){
        isNewQuickUpload=true;
        
        buildTopHorizontalRowLayout();
        
        setStandardList(MasterDataProvider.getStandardList());
        setUploadedList(MasterDataProvider.getQuickLearnUploadList());
       
        row = new HorizontalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        addComponent(row);
        setExpandRatio(row, 1.5f);      
       
        row.addComponent(UIUtils.createPanel(buildUploadedTopicsTableLayout()));
        
        //row.addComponent(UIUtils.createPanel(buildTabSheetLayout()));
    }

    /** Used to show the list of uploaded items
     * called when admin user goes to upload topics menu
     */
    private VerticalLayout buildUploadedTopicsTableLayout() {
        VerticalLayout mainVertical = new VerticalLayout();
        //HorizontalLayout tableView = new HorizontalLayout();
        mainVertical.setSpacing(true);
        mainVertical.setWidth("100%");
        mainVertical.setHeight("97%");
        quickUploadTable = new QuickUploadTable(this);
        mainVertical.addComponent(quickUploadTable);
        //mainVertical.addComponent(tableView);
        return mainVertical;
    }
    
    
    private VerticalLayout buildTabSheetLayout(String videoPath, String notes, String otherNotes, String previousQuestions) {
        VerticalLayout mainVertical=new VerticalLayout();
        
           editors = new TabSheet();
           editors.setSizeFull();
           //player = new DashBoardVideoPlayer();
           editors.addTab(getVideoPathLayout(videoPath),"Video");
           editors.addTab(getNotesLayout(notes), "Notes");
           editors.addTab(getOtherNotesLayout(otherNotes), "Other Notes");
           editors.addTab(getPreviousQuestionsLayout(previousQuestions), "Previous Questions");
           CssLayout cssTabsheetLayout = UIUtils.createPanel(editors);
           
           mainVertical.addComponent(cssTabsheetLayout);
           mainVertical.setExpandRatio(cssTabsheetLayout, 2);
           mainVertical.setWidth("100%");
           mainVertical.setHeight("97%");
           
           CssLayout aboutLearnLayout =  UIUtils.createPanel(buildTopicDetailsLayout());
           aboutLearnLayout.setCaption("Topic Information");
           
           mainVertical.addComponent(aboutLearnLayout);
           mainVertical.setExpandRatio(aboutLearnLayout, 1);
           return mainVertical;
    }
    

    private TextField txtVideoPath;

    private VerticalLayout getVideoPathLayout(String videoPath) {
       VerticalLayout layout= new VerticalLayout();
       layout.setSpacing(true);
       layout.setSizeFull();
       
       if(videoPath!=null)
       {
           // video is available, show it on video player
           Video v = new Video();
           v.setImmediate(true);
           v.setWidth("100%");
           v.setHeight("100%");
           v.addSource(new FileResource(new File(videoPath)));
           layout.addComponent(v);
           layout.setComponentAlignment(v, Alignment.MIDDLE_CENTER);
           
       }
       else
       {
         // not video available, accept path from user
         txtVideoPath=new TextField();
         txtVideoPath.setImmediate(true);
         txtVideoPath.setInputPrompt("Enter server video path");
         txtVideoPath.setCaption("Video file");
         txtVideoPath.setWidth("70%");
         txtVideoPath.addValueChangeListener(this);
         layout.addComponent(txtVideoPath);
         layout.setComponentAlignment(txtVideoPath, Alignment.MIDDLE_CENTER);
         
       }
       
       return layout;
    }
    
    private RichTextArea notesRichTextArea;
    
    private VerticalLayout getNotesLayout(String strNotes) {
        VerticalLayout layout= new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setMargin(true);
        
        notesRichTextArea = new RichTextArea();
        
        notesRichTextArea.setSizeFull();
        if(strNotes!=null)
        {
            notesRichTextArea.setValue(strNotes);
        }
        
        notesRichTextArea.setImmediate(true);
        notesRichTextArea.addValueChangeListener(this);
        
        
        layout.addComponent(notesRichTextArea);
        layout.setExpandRatio(notesRichTextArea, 2);
        
        return layout;
        
    }
    
    private RichTextArea otherNotesRichTextArea;
    
    private VerticalLayout getOtherNotesLayout(String otherNotes) {
        VerticalLayout layout= new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setMargin(true);
        
        otherNotesRichTextArea = new RichTextArea();
        
        otherNotesRichTextArea.setSizeFull();
        if(otherNotes!=null)
        {
            otherNotesRichTextArea.setValue(otherNotes);
        }
        
        otherNotesRichTextArea.setImmediate(true);
        otherNotesRichTextArea.addValueChangeListener(this);
        
        layout.addComponent(otherNotesRichTextArea);
        layout.setExpandRatio(otherNotesRichTextArea, 2);
        
        return layout;
        
    }
    
    private RichTextArea previousQuestionsRichTextArea;
    private VerticalLayout getPreviousQuestionsLayout(String previousQuestions) {
        VerticalLayout layout= new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setMargin(true);
        
        previousQuestionsRichTextArea = new RichTextArea();
        
        previousQuestionsRichTextArea.setSizeFull();
        if(previousQuestions!=null)
        {
            previousQuestionsRichTextArea.setValue(previousQuestions);
        }
        
        previousQuestionsRichTextArea.setImmediate(true);
        previousQuestionsRichTextArea.addValueChangeListener(this);
        
        layout.addComponent(previousQuestionsRichTextArea);
        layout.setExpandRatio(previousQuestionsRichTextArea, 2);
        
        return layout;
        
    }
    

               
    private HorizontalLayout buildTopicDetailsLayout()
    {
        topicInformationLayout = new HorizontalLayout();
        
        topicInformationLayout.setSpacing(true);
        topicInformationLayout.setSizeFull();
        
        VerticalLayout baseLayout=new VerticalLayout();
        baseLayout.setSpacing(true);

        subjecttxt = new ComboBox();
        subjecttxt.setInputPrompt("Subject");
        subjecttxt.setNullSelectionAllowed(false);
        subjecttxt.setImmediate(true);
        
        standardtxt = new ComboBox();
        standardtxt.setInputPrompt("Standard");
        standardtxt.addItem("Select");
        standardtxt.setValue("Select"); 
        standardtxt.setImmediate(true);
        standardtxt.setNullSelectionAllowed(false);
        
        Iterator it=getStandardList().iterator();
        while(it.hasNext()){
            Std s=(Std) it.next();
            standardtxt.addItem(s.getStd());            
        }
        standardtxt.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                if(!standardtxt.getValue().equals("Select")){
                    String std=String.valueOf(standardtxt.getValue());
                    setSubjectList(MasterDataProvider.getSubjectBystd(std));
                    if(!getSubjectList().isEmpty()){
                         Iterator subItr=getSubjectList().iterator();
                         while(subItr.hasNext()){
                         QuickLearn s=(QuickLearn) subItr.next();
                         subjecttxt.addItem(s.getSub());            
                       }  
                    }     
               }
            }
        });
        
        topictxt=new TextField();
        topictxt.setInputPrompt("Topic");        
                
        baseLayout.addComponent(standardtxt);
        baseLayout.setExpandRatio(standardtxt, 1);
        
        baseLayout.addComponent(subjecttxt);
        baseLayout.setExpandRatio(subjecttxt, 1);
        
        baseLayout.addComponent(topictxt);
        baseLayout.setExpandRatio(topictxt, 1);
        
        topicInformationLayout.addComponent(baseLayout);
        
        VerticalLayout secondVerticalyt=new VerticalLayout();
        secondVerticalyt.setSpacing(true);
        
        topicTagstxt=new TextField();
        topicTagstxt.setInputPrompt("TAGS");
        topicTagstxt.setWidth("200px");
        topicTagstxt.setHeight("100px");       
        
        secondVerticalyt.addComponent(topicTagstxt);
        secondVerticalyt.setExpandRatio(topicTagstxt, 1);        
        
        topicInformationLayout.addComponent(secondVerticalyt);
        //topicInformationLayout.setVisible(visibility);
        return topicInformationLayout;
    }
      
    
    public QuickUploadMasterContainer getQuickUploadMasterContainer() {
        return QuickUploadMasterContainer.getQuickLearnUploadList(getUploadedList());
    }

    public List<MasteParmBean> getUploadedList() {
        return uploadedList;
    }

    public void setUploadedList(List<MasteParmBean> uploadedList) {
        this.uploadedList = uploadedList;
    }

    
    
    
    private void removeTabsheetLayout()
    {
        if(cssTabSheetLayout!=null)
        {
           row.removeComponent(cssTabSheetLayout);
        }
    }

    private static String Select = "Select";
    private void validateAndSaveQuickUploadDetails() throws JSONException {
        
        //validations
        if(loggedInProfile.getName()==null || loggedInProfile.getName().trim().equals(GlobalConstants.emptyString))
        {
            Notification.show("User not properly logged in.", Notification.Type.WARNING_MESSAGE);
        }
        else if(standardtxt.getValue()==null || ((String)standardtxt.getValue()).trim().equals(GlobalConstants.emptyString) || ((String)standardtxt.getValue()).trim().equalsIgnoreCase(Select))
        {
            Notification.show("Please enter standard.", Notification.Type.WARNING_MESSAGE);
        }
        else if(subjecttxt.getValue()==null || ((String)subjecttxt.getValue()).trim().equals(GlobalConstants.emptyString)|| ((String)subjecttxt.getValue()).trim().equalsIgnoreCase(Select))
        {
            Notification.show("Please enter subject.", Notification.Type.WARNING_MESSAGE);
        }
        else if(topictxt.getValue()==null || ((String)topictxt.getValue()).trim().equals(GlobalConstants.emptyString))
        {
            Notification.show("Please enter topic.", Notification.Type.WARNING_MESSAGE);
        }
        else if(topicTagstxt.getValue()==null || ((String)topicTagstxt.getValue()).trim().equals(GlobalConstants.emptyString))
        {
            Notification.show("Please enter tags for topic.", Notification.Type.WARNING_MESSAGE);
        }
        else
        {
            //executions - inserting in db
            JSONObject inputJson = new JSONObject();
            try 
            {
                String uploadedBy = loggedInProfile.getName();
                inputJson.put("uploadedBy", uploadedBy);
                inputJson.put("std", standardtxt.getValue());
                inputJson.put("sub", subjecttxt.getValue());
                inputJson.put("topic", topictxt.getValue());
                inputJson.put("tags", topicTagstxt.getValue());
                
                if (txtVideoPath != null)
                {
                    inputJson.put("video_path", txtVideoPath.getValue());
                }
                else
                {
                    String dummyString="null";
                    inputJson.put("video_path", dummyString);
                }

                if (!notesRichTextArea.getValue().equals(GlobalConstants.emptyString))
                {
                    inputJson.put("notes", notesRichTextArea.getValue());
                } else {
                    inputJson.put("notes", "no data");
                }

                if (!otherNotesRichTextArea.getValue().equals(GlobalConstants.emptyString)) {
                    inputJson.put("othernotes", otherNotesRichTextArea.getValue());
                } else {
                    inputJson.put("othernotes", "no data");
                }

                if (!previousQuestionsRichTextArea.getValue().equals(GlobalConstants.emptyString)) {
                    inputJson.put("pq", previousQuestionsRichTextArea.getValue());
                } else {
                    inputJson.put("pq", "no data");
                }

                if (isNewQuickUpload) {
                    inputJson.put("uploadId", "null");
                } else {
                    inputJson.put("uploadId", uploadId);
                }

            } catch (JSONException ex) 
            {
                ex.printStackTrace();
                throw ex;
            }


            Client client = Client.create();
            WebResource webResource = client.resource(GlobalConstants.getProperty(GlobalConstants.SAVE_UPLOAD_DETAILS_URL));
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);

            /*
             * if (response.getStatus() != 201) { throw new RuntimeException("Failed
             * : HTTP error code : " + response.getStatus()); }
             */

            String output = response.getEntity(String.class);
            System.out.println("output=" + output);
            
            Notification.show("Saved successfully", Notification.Type.WARNING_MESSAGE);
            removeTabsheetLayout();
            updateQuickUploadTable();
            
            newbtn.setVisible(true);
                savebtn.setVisible(false);
                cancelbtn.setVisible(false);

    }
}

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOtherNotes() {
        return otherNotes;
    }

    public void setOtherNotes(String otherNotes) {
        this.otherNotes = otherNotes;
    }

    public String getPreviousQuestions() {
        return previousQuestions;
    }

    public void setPreviousQuestions(String previousQuestions) {
        this.previousQuestions = previousQuestions;
    }

   
    private void updateQuickUploadTable() {
        
        row.removeAllComponents();
      /* quickUploadTable.setContainerDataSource(QuickUploadMasterContainer.getQuickLearnUploadList(getUploadedList()));
      quickUploadTable.setVisibleColumns(QuickUploadMasterContainer.NATURAL_COL_ORDER_QUICKUPLOAD_INFO);
      quickUploadTable.setColumnHeaders(QuickUploadMasterContainer.COL_HEADERS_ENGLISH_QUICKUPLOAD_INFO); */
        quickUploadTable=new QuickUploadTable(this);
       row.addComponent(UIUtils.createPanel(quickUploadTable));
      // show the first value selected in the table
     // quickUploadTable.setValue(quickUploadTable.firstItemId());
    }

   
    
    private void buildAndDisplaySelectedTopicInformation() {
        //fetch from db - service call
        setQuikLearnMasterParamDetails(getQuickUploadDetailsFromDB());
        
        if(cssTabSheetLayout!=null)
        {
           row.removeComponent(cssTabSheetLayout);
        }
        cssTabSheetLayout=UIUtils.createPanel(buildTabSheetLayout(getQuikLearnMasterParamDetails().getVideoPath(),getQuikLearnMasterParamDetails().getLectureNotes(),getQuikLearnMasterParamDetails().getOtherNotes(),getQuikLearnMasterParamDetails().getPreviousQuestion()));
       

        displayTopicInformation(getQuikLearnMasterParamDetails());
        row.addComponent(cssTabSheetLayout);
        
        //updateQuickUplaodTabSheet(getQuikLearnMasterParamDetails());
    }

   /*  private void updateQuickUplaodTabSheet(MasteParmBean quikLearnMasterParamDetails1) {
           
           editors.removeAllComponents();
           player = new DashBoardVideoPlayer();
           editors.addTab(player,"Video");
           editors.addTab(new QuickUploadNotes(getQuikLearnMasterParamDetails(),this), "Notes");
           editors.addTab(new QuickUploadOtherNotes(getQuikLearnMasterParamDetails(),this), "OtherNotes");
           editors.addTab(new QuickUploadPreviousQuestion(getQuikLearnMasterParamDetails(),this), "Previous Questions");
    } */

   
     private MasteParmBean getQuickUploadDetailsFromDB() {
       
          List<MasteParmBean>list =null;
          try {
            Client client = Client.create();
            WebResource webResource = client.resource(GlobalConstants.getProperty(GlobalConstants.GET_QUICK_LEARN_BY_UPLOAD_ID));
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
          
            Type listType = new TypeToken<ArrayList<MasteParmBean>>() {
            }.getType();
            Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
            list= gson.fromJson(outNObject.getString(GlobalConstants.QUICKLEARNLIST), listType);
            
        } catch (JSONException ex) 
        {
            ex.printStackTrace();
        }
          if(!list.isEmpty()){
              return list.get(0);
          }else{
              return null;
          }
          

        
    }

    public MasteParmBean getQuikLearnMasterParamDetails() {
        return quikLearnMasterParamDetails;
    }

    public void setQuikLearnMasterParamDetails(MasteParmBean QuikLearnDetails) {
        this.quikLearnMasterParamDetails = QuikLearnDetails;
        System.out.println("quikLearnMasterParamDetails="+quikLearnMasterParamDetails);
    }

    private void displayTopicInformation(MasteParmBean quickLearn){
        if(quickLearn!=null){
            
            topicInformationLayout.setVisible(true);
           standardtxt.setReadOnly(false);
           subjecttxt.setReadOnly(false);
           topictxt.setReadOnly(false);
           topicTagstxt.setReadOnly(false);
           
           
           standardtxt.setValue(quickLearn.getStd()); 
           subjecttxt.setValue(quickLearn.getSub());
           topictxt.setValue(quickLearn.getTopic());
           topicTagstxt.setValue(quickLearn.getTopicTags());
           
           
           standardtxt.setReadOnly(true);
           subjecttxt.setReadOnly(true);
           topictxt.setReadOnly(true);
           topicTagstxt.setReadOnly(true);
           
        }
    }
    private void buildTopHorizontalRowLayout() 
    {
        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setSpacing(true);
        top.addStyleName(GlobalConstants.toolbar_style);
        addComponent(top);
        final Label title = new Label(GlobalConstants.Upload_Topics);
        title.setSizeUndefined();
        title.addStyleName(GlobalConstants.h1_style);
        top.addComponent(title);
        top.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        top.setExpandRatio(title, 1);
        
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setMargin(true);
        buttons.setSpacing(true);
        buttons.setSizeUndefined();

        newbtn = new Button(GlobalConstants.New,(Button.ClickListener)this);
        newbtn.addStyleName(GlobalConstants.default_style);
        newbtn.setImmediate(true);  
        buttons.addComponent(newbtn);
        
        savebtn = new Button(GlobalConstants.Save,(Button.ClickListener)this);
        savebtn.addStyleName(GlobalConstants.default_style);
        savebtn.setImmediate(true);
        savebtn.setVisible(false);
        buttons.addComponent(savebtn);

        cancelbtn = new Button(GlobalConstants.Cancel,(Button.ClickListener)this);
        cancelbtn.setImmediate(true);  
        cancelbtn.addStyleName(GlobalConstants.default_style);
        cancelbtn.setVisible(false);
        buttons.addComponent(cancelbtn);        
       
        top.addComponent(buttons);
        top.setComponentAlignment(buttons, Alignment.TOP_RIGHT); 
    }
    
    public List<Std> getStandardList() {
        return standardList;
    }

    public void setStandardList(List<Std> standardList) {
        this.standardList = standardList;
    }

    public List<QuickLearn> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<QuickLearn> subjectList) {
        this.subjectList = subjectList;
    }


    @Override
    public void buttonClick(ClickEvent event) {
        final Button source = event.getButton();
        if (source == newbtn) {
            newbtn.setVisible(false);
            savebtn.setVisible(true);
            cancelbtn.setVisible(true);
            removeTabsheetLayout();
            //passing null below to create blank new video path layout,notes, other notes and questions so that admin can enter it
            cssTabSheetLayout=UIUtils.createPanel(buildTabSheetLayout(null,null,null,null));
            row.addComponent(cssTabSheetLayout);

        } else if (source == savebtn) {
            try 
            {
                
                validateAndSaveQuickUploadDetails();
                //removeTabsheetLayout();
                
            } catch (Exception ex) {
                ex.printStackTrace();
                Notification.show("Saved failed", Notification.Type.WARNING_MESSAGE);
            }
        } else if (source == cancelbtn) {
            newbtn.setVisible(true);
            savebtn.setVisible(false);
            cancelbtn.setVisible(false);
            topicInformationLayout.setVisible(false);
            removeTabsheetLayout();
        }
    }
    
     @Override
    public void valueChange(ValueChangeEvent event) {
        Property property=event.getProperty();
        if(property==quickUploadTable){
            Set<MasteParmBean> topic=(Set<MasteParmBean>) property.getValue();
            for(MasteParmBean u:topic){
               uploadId = u.getUploadId();  
            } 
             isNewQuickUpload=false;
             buildAndDisplaySelectedTopicInformation();
        }
        else if(property==txtVideoPath || property==notesRichTextArea || property==otherNotesRichTextArea || property==previousQuestionsRichTextArea)
        {
            savebtn.setVisible(true);
        }
    }
}
