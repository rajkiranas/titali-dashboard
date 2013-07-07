/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaadin.demo.dashboard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quick.bean.QuickLearn;
import com.quick.bean.TeacherStddivSubIdBean;
import com.quick.bean.Userprofile;
import com.quick.entity.QualificationMaster;
import com.quick.entity.Std;
import com.quick.global.GlobalConstants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vaadin.data.Property;
import com.quick.data.MasterDataProvider;
import com.quick.data.TeacherSubjectAssociationContainer;
import com.quick.table.TeacherSubjectAssociationTable;
import com.quick.utilities.DateUtil;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author vmundhe
 */
public class AddTeacher extends Window implements Button.ClickListener,Property.ValueChangeListener{

    private TeacherView teacherView;
    private VerticalLayout baseLayout;
    private HorizontalLayout buttonLayout;
    private Button savebtn;
    private Button cancelbtn;
    private TextField teacherNametxt;
    private DateField dob;
    private DateField doj;             
    private TextField userNametxt;
    private TextField passwordtxt;
    private OptionGroup genderOption;
    private TextField mobiletxt;
    private TextField address;
    private ComboBox qualNametxt;
    private static final List<String> genderList = Arrays.asList(new String[]{
                "Male", "Female"});
    private List<Std> standardList;
    private List<QualificationMaster> qualificationList;
    private boolean isNewTeacher=false;
    private int prn = 0;
    private List<QuickLearn> subjectList;
    private List<QuickLearn> divisionList;
    private ComboBox subjecttxt;    
    private Button associatebtn;
    String teacherSubjson;
    private List<TeacherStddivSubIdBean> teacherSubAssociationList=new ArrayList<TeacherStddivSubIdBean>();
    private TeacherSubjectAssociationTable associationTable;
    
    public List<QuickLearn> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<QuickLearn> subjectList) {
        this.subjectList = subjectList;
    }

    public List<QuickLearn> getDivisionList() {
        return divisionList;
    }

    public void setDivisionList(List<QuickLearn> divisionList) {
        this.divisionList = divisionList;
    }

   
    
    public List<QualificationMaster> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(List<QualificationMaster> qualificationList) {
        this.qualificationList = qualificationList;
    }
    
    public List<Std> getStandardList() {
        return standardList;
    }

    public void setStandardList(List<Std> standardList) {
        this.standardList = standardList;
    }
    
    public AddTeacher(TeacherView teacherView) {
        this.teacherView=teacherView;
        baseLayout = new VerticalLayout();
        baseLayout.setSpacing(true);
        isNewTeacher=true;
        setModal(true);
        setCaption("Welcome to Add New Teacher");
        setContent(baseLayout);
        center();        
        setClosable(false);
        setWidth("80%");
        setHeight("80%");  
        setStandardList(MasterDataProvider.getStandardList());
        setQualificationList(MasterDataProvider.getQualificationList());
        buildBaseTeacherLayout();
        
    }
    
    public AddTeacher(TeacherView teacherView, List<Userprofile> teacherList) {
        this.teacherView=teacherView;
        baseLayout = new VerticalLayout();
        baseLayout.setSpacing(true);
        setModal(true);
        setCaption("Welcome to Edit Teacher");
        setContent(baseLayout);
        center();        
        setClosable(false);
        setWidth("80%");
        setHeight("80%");  
        setStandardList(MasterDataProvider.getStandardList());        
        setQualificationList(MasterDataProvider.getQualificationList());       
        buildBaseTeacherLayout();
        setTeacherFormData(teacherList);
        setTeacherSubAssociationList(getTeacherStdDivSubList(userNametxt.getValue()));
        buildTeacherAssociationTable(); 
        baseLayout.addComponent(associationTable);
        baseLayout.setComponentAlignment(associationTable,Alignment.MIDDLE_CENTER);
    }
    
     private void buildBaseTeacherLayout(){
              
       buttonLayout=new HorizontalLayout();
       buttonLayout.setImmediate(false);
       buttonLayout.setWidth("100%");
       buttonLayout.setHeight("100%");
       buttonLayout.setMargin(false);
       buttonLayout.setSpacing(false);
       
       HorizontalLayout buttons = new HorizontalLayout();
       buttons.setMargin(true);
       buttons.setSpacing(true);
       buttons.setSizeUndefined();
       
       
       associatebtn= new Button("Teacher Subject Association",(Button.ClickListener)this);
       associatebtn.addStyleName("default");
       buttons.addComponent(associatebtn);
       
       savebtn = new Button("Save",(Button.ClickListener)this);
       savebtn.addStyleName("default");
       buttons.addComponent(savebtn);
        
       buttonLayout.addComponent(buttons);
       
       cancelbtn = new Button("Cancel",(Button.ClickListener)this);
       cancelbtn.setImmediate(true);  
       cancelbtn.addStyleName("default");
       buttons.addComponent(cancelbtn); 
       
       
       
       buttonLayout.setComponentAlignment(buttons, Alignment.TOP_RIGHT); 
       
       baseLayout.addComponent(buttonLayout);
       
       buildStudentForm();                 
    }
    
    private void buildStudentForm(){
       
       Panel formPanel=new Panel();
       formPanel.setSizeUndefined();
       formPanel.setWidth("800px");
       formPanel.setHeight("350px");
       if(isNewTeacher){
          formPanel.setCaption("Add Teacher");
       }else{
          formPanel.setCaption("Edit Teacher"); 
       }      
       
       HorizontalLayout formLayout=new HorizontalLayout();
       formLayout.setSpacing(true);
       formLayout.setMargin(true);
       formLayout.setSizeUndefined();
               
       FormLayout teacherForm1=new FormLayout();
       teacherForm1.setSizeUndefined();
       teacherForm1.setMargin(true);

       teacherNametxt=new TextField();
       teacherNametxt.setCaption("Name");
       teacherNametxt.setRequired(true);
      
       userNametxt=new TextField();
       userNametxt.setCaption("Username");
       userNametxt.setRequired(true);
       userNametxt.addBlurListener(new FieldEvents.BlurListener() {
           
            @Override
            public void blur(FieldEvents.BlurEvent event) {
                 if (((String) userNametxt.getValue()).equals(GlobalConstants.emptyString)) {
                       Notification.show("Please enter valid username",Notification.Type.WARNING_MESSAGE);              
                }else if(MasterDataProvider.IsUsernameAlreadyExist(String.valueOf(userNametxt.getValue()))){
                    userNametxt.setValue("");
                }  
            }           
        });
       
       if(!isNewTeacher){
          userNametxt.setEnabled(false); 
       }
       
       dob=new DateField();
       dob.setCaption("Date Of Birth");
       dob.setRequired(true);
       dob.setDateFormat(GlobalConstants.DATEFORMAT);
       
       qualNametxt=new ComboBox("Qualification Name");
       qualNametxt.addItem("Select");
       qualNametxt.setValue("Select"); 
       qualNametxt.setRequired(true);
       qualNametxt.setNullSelectionAllowed(false);
       Iterator qualit=getQualificationList().iterator();
       while(qualit.hasNext()){
            QualificationMaster qm=(QualificationMaster) qualit.next();
            qualNametxt.addItem(qm.getQualificationName());            
       }       
       
       address=new TextField();
       address.setCaption("Address");
       address.setRequired(true);       
       
             
       teacherForm1.addComponent(teacherNametxt);
       teacherForm1.addComponent(userNametxt);     
       teacherForm1.addComponent(dob);     
       teacherForm1.addComponent(qualNametxt);
       teacherForm1.addComponent(address);

       formLayout.addComponent(teacherForm1);
       
       FormLayout teacherForm2=new FormLayout();       
      
       genderOption=new OptionGroup("Gender",genderList);
       genderOption.setStyleName("horizontal");
       genderOption.setRequired(true);
       genderOption.setImmediate(true);
            
       passwordtxt=new TextField();
       passwordtxt.setCaption("Password");
       passwordtxt.setRequired(true);
       
       doj=new DateField();
       doj.setCaption("Date Of Joining");
       doj.setRequired(true);
       doj.setDateFormat(GlobalConstants.DATEFORMAT);
       
       mobiletxt=new TextField();
       mobiletxt.setCaption("Mobile");
       mobiletxt.setRequired(true);
              
       teacherForm2.addComponent(genderOption);
       teacherForm2.addComponent(passwordtxt);   
       teacherForm2.addComponent(doj);    
       teacherForm2.addComponent(mobiletxt);  
       
       formLayout.addComponent(teacherForm2);       
       formPanel.setContent(formLayout);      
       
       baseLayout.addComponent(formPanel);
       baseLayout.setComponentAlignment(formPanel,Alignment.MIDDLE_CENTER);
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        final Button source=event.getButton();
        if(source==savebtn){
            if(validateTeacher()){
                saveTeacher();
                teacherView.setTeacherList(MasterDataProvider.getAllTeacherList()); 
                teacherView.getTeacherTable().removeValueChangeListener((Property.ValueChangeListener)teacherView);
                teacherView.getTeacherTable().setContainerDataSource(teacherView.getTeacherMasterContainer());
                teacherView.getTeacherTable().setVisibleColumns(TeacherMasterContainer.NATURAL_COL_ORDER_TEACHER_INFO);
                teacherView.getTeacherTable().setColumnHeaders(TeacherMasterContainer.COL_HEADERS_ENGLISH_TEACHER_INFO);
                teacherView.getTeacherTable().addValueChangeListener((Property.ValueChangeListener)teacherView);
                this.close();
                Notification.show("Saved",Notification.Type.WARNING_MESSAGE);
            }                 
        }else if(source == cancelbtn){
              this.close();
        }else if(source==associatebtn){
              Window w = new TeacherAssociationFilter(this,userNametxt.getValue());
              UI.getCurrent().addWindow(w);
              w.focus();
        }      
    }

    public void saveTeacher(){
        Client client=Client.create();
        WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/saveTeacher");
        JSONObject inputJson=new JSONObject();
        try{                 
           inputJson.put("name", teacherNametxt.getValue());
           inputJson.put("username", userNametxt.getValue());
           inputJson.put("password", passwordtxt.getValue());           
           inputJson.put("gender", genderOption.getValue());           
           inputJson.put("dob", dob.getValue());
           inputJson.put("doj", doj.getValue());
          // inputJson.put("standard", standardtxt.getValue());
           inputJson.put("mobile", mobiletxt.getValue());
         //  inputJson.put("division", divisiontxt.getValue());
           inputJson.put("qualName", qualNametxt.getValue());
           inputJson.put("address", address.getValue());  
           inputJson.put("teacherSubjson", teacherSubjson);
           
           if(isNewTeacher){
               inputJson.put("prn","null");  
           }else{
               inputJson.put("prn",prn);  
           }
           
           
        }catch (JSONException ex){
            ex.printStackTrace();
        }
        
        
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);

        /*
         * if (response.getStatus() != 201) { throw new RuntimeException("Failed
         * : HTTP error code : " + response.getStatus()); }
         */

        String output = response.getEntity(String.class);
        System.out.println("output="+output);
    }

    
     private boolean validateTeacher() {
         
        if(((teacherNametxt.getValue()).equals("")) && ((userNametxt.getValue()).equals(""))
             && ((passwordtxt.getValue()).equals("")) && (genderOption.getValue()==null)
             && (dob.getValue()==null) && (doj.getValue()==null) && ((qualNametxt.getValue()).equals("Select")) 
           ){
            Notification.show("Please enter mandetory fields",Notification.Type.WARNING_MESSAGE); 
            return false;            
        }else if((teacherNametxt.getValue()).equals("")){
            Notification.show("Please enter name",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if (!StringFieldValidator.isValidName((String) teacherNametxt.getValue())) {
            Notification.show("Please enter valid name",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if((userNametxt.getValue()).equals("")){
            Notification.show("Please enter username",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if((passwordtxt.getValue()).equals("")){
            Notification.show("Please enter password",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if(genderOption.getValue()==null){
            Notification.show("Please select gender",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if(dob.getValue()==null){
            Notification.show("Please select date of birth",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if(doj.getValue()==null){
            Notification.show("Please select date of birth",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if((qualNametxt.getValue()).equals("Select")){
            Notification.show("Please select standard",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if((mobiletxt.getValue()).equals("")){
            Notification.show("Please enter contact number",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if(!NumberValidator.isValidCellNumber((String) mobiletxt.getValue()) || !(((String) mobiletxt.getValue()).length() == 10)) {
            Notification.show("Please enter valid contact number",Notification.Type.WARNING_MESSAGE);
            return false;
        }else if((address.getValue()).equals("")){
            Notification.show("Please enter address",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if(DateUtil.getDateDifference((Date)doj.getValue(),(Date)dob.getValue())>0){
            Notification.show("Joining Date should not be greater than date of birth",Notification.Type.WARNING_MESSAGE); 
            return false;
        } 
         return true;
         
     }

    private void setTeacherFormData(List<Userprofile> teacherList) {
        for(Userprofile teacherProfile:teacherList){
            address.setValue(teacherProfile.getAddress());
         //   divisiontxt.setValue(teacherProfile.getDiv());
            dob.setValue(teacherProfile.getDob());
            doj.setValue(teacherProfile.getDoj());
            mobiletxt.setValue(String.valueOf(teacherProfile.getMobile()));
            userNametxt.setValue(teacherProfile.getUsername());
            passwordtxt.setValue(teacherProfile.getPassword());
            
            if(teacherProfile.getGender()=='M'){
               genderOption.setValue("Male");               
            }else{
               genderOption.setValue("Female");               
            }
            qualNametxt.setValue(teacherProfile.getQualName());
            teacherNametxt.setValue(teacherProfile.getName());
          //  standardtxt.setValue(teacherProfile.getStd());     
            prn = teacherProfile.getPrn();
        }
    }

   public void saveTeacherSubjectAssociation(List<TeacherStddivSubIdBean> teacherSubAssociationList) {
        Gson gson = new Gson();
        teacherSubjson = gson.toJson(teacherSubAssociationList);
        if(associationTable!=null){
            baseLayout.removeComponent(associationTable);
        }        
        buildTeacherAssociationTable();       
        baseLayout.addComponent(associationTable);
        baseLayout.setComponentAlignment(associationTable,Alignment.MIDDLE_CENTER);
    }

    public List<TeacherStddivSubIdBean> getTeacherSubAssociationList() {
        return teacherSubAssociationList;
    }

    public void setTeacherSubAssociationList(List<TeacherStddivSubIdBean> teacherSubAssociationList) {
        this.teacherSubAssociationList = teacherSubAssociationList;
    }
    
    private Table buildTeacherAssociationTable() {
        return associationTable=new TeacherSubjectAssociationTable(this);
    }

    public TeacherSubjectAssociationContainer getTeacherSubjectAssociationContainer() {
        if(isNewTeacher){
            return TeacherSubjectAssociationContainer.getTeacherStdDivsSubAssociationList(getTeacherSubAssociationList());
        }else{
            return TeacherSubjectAssociationContainer.getTeacherStdDivsSubAssociationList(getTeacherSubAssociationList());
        }
       
    }

    @Override
    public void valueChange(ValueChangeEvent event) {      
            Property property=event.getProperty();
            if(property==associationTable){             
                Set<TeacherStddivSubIdBean> beans=(Set<TeacherStddivSubIdBean>) property.getValue();
                Iterator<TeacherStddivSubIdBean> it=beans.iterator();     
                while(it.hasNext()){
                    TeacherStddivSubIdBean bean = it.next(); 
                    Window w = new TeacherAssociationFilter(this,bean,userNametxt.getValue());
                    UI.getCurrent().addWindow(w);
                    w.focus();
                }          
            } 
        
       
    }   
    
    public static List<TeacherStddivSubIdBean> getTeacherStdDivSubList(String username) {
          List<TeacherStddivSubIdBean> teacherStddivSubIdList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/getTeacherStdDivSubListByUsername");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
             try{           
                inputJson.put("username", username);  
             }catch(Exception ex){
                ex.printStackTrace(); 
             }
            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<TeacherStddivSubIdBean>>() {
            }.getType();
            
            teacherStddivSubIdList= new Gson().fromJson(outNObject.getString(GlobalConstants.teacherStdDivSubIdList), listType);
        } catch (JSONException ex) {
          //  Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teacherStddivSubIdList;
    }
}
