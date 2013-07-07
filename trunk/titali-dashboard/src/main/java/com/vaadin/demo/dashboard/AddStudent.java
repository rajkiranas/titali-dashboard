/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaadin.demo.dashboard;

import com.quick.bean.Userprofile;
import com.quick.entity.Std;
import com.quick.global.GlobalConstants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vaadin.data.Property;
import com.quick.data.MasterDataProvider;
import com.quick.utilities.DateUtil;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import java.util.*;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;




/**
 *
 * @author sonalis
 */
public class AddStudent extends Window implements Button.ClickListener{
    
    private HorizontalLayout buttonLayout;
    private TextField prntxt;
    private TextField rollNotxt;
    private TextField studNametxt;
    private DateField dob;
    private TextField userNametxt;
    private PasswordField passwordtxt;
    private OptionGroup genderOption;
    private TextField mobiletxt;
    private DateField creationDate;
    private ComboBox eduYeartxt;
    private ComboBox standardtxt;
    private ComboBox divisiontxt;
    private TextField address;
    private static final List<String> genderList = Arrays.asList(new String[]{
                "Male", "Female"});
    private VerticalLayout baseLayout;
    private StudentView studentView;
    private Button savebtn;
    private Button cancelbtn;
    private List<Std> standardList;
    private boolean isNewStudent=false;
    private int prn =0;

  

    public List<Std> getStandardList() {
        return standardList;
    }

    public void setStandardList(List<Std> standardList) {
        this.standardList = standardList;
    }
    
    private int YEAR=DateUtil.getYear(DateUtil.getCalenderInstance());
    
    
    public AddStudent(StudentView studentView){
        this.studentView=studentView;
        isNewStudent=true;
        baseLayout = new VerticalLayout();
        baseLayout.setSpacing(true);
        setModal(true);
        setCaption("Welcome to Add New Student");
        setContent(baseLayout);
        center();        
        setClosable(false);
        setWidth("80%");
        setHeight("80%"); 
        setStandardList(MasterDataProvider.getStandardList());
        buildBaseStudentLayout();
        
    }
    
    public AddStudent(StudentView studentView, List<Userprofile> studentList) {
        this.studentView=studentView;
        baseLayout = new VerticalLayout();
        baseLayout.setSpacing(true);       
        setModal(true);       
        setCaption("Welcome to Edit Student");
        setContent(baseLayout);
        center();        
        setClosable(false);
        setWidth("80%");
        setHeight("80%");
        setStandardList(MasterDataProvider.getStandardList());
        buildBaseStudentLayout();
        setStudentFormData(studentList);
    }
    
     private void buildBaseStudentLayout(){
              
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
       if(isNewStudent){
           formPanel.setCaption("Add Student");
       }else{
           formPanel.setCaption("Edit Student");
       }
       
       
       
       HorizontalLayout formLayout=new HorizontalLayout();
       formLayout.setSpacing(true);
       formLayout.setMargin(true);
       formLayout.setSizeUndefined();
               
       FormLayout studform1=new FormLayout();
       studform1.setSizeUndefined();
       studform1.setMargin(true);

       rollNotxt=new TextField();
       rollNotxt.setCaption("Roll No");
       rollNotxt.setImmediate(true);
       rollNotxt.setRequired(true);
       rollNotxt.addBlurListener(new FieldEvents.BlurListener() {

            @Override
            public void blur(BlurEvent event) {
                if(!NumberValidator.isValidCellNumber((String) rollNotxt.getValue())) {
                   Notification.show("Please enter valid roll no",Notification.Type.WARNING_MESSAGE);  
                }else if(IsRollNoAlreadyExist(String.valueOf(rollNotxt.getValue()))){
                    rollNotxt.setValue("");
                }                
            }
        });
       
       userNametxt=new TextField();
       userNametxt.setCaption("Username");
       userNametxt.setRequired(true);
       userNametxt.setImmediate(true);
       userNametxt.addBlurListener(new FieldEvents.BlurListener() {
           
            @Override
            public void blur(BlurEvent event) {
                 if (((String) userNametxt.getValue()).equals(GlobalConstants.emptyString)) {
                       Notification.show("Please enter valid username",Notification.Type.WARNING_MESSAGE);              
                }else if(MasterDataProvider.IsUsernameAlreadyExist(String.valueOf(userNametxt.getValue()))){
                    userNametxt.setValue("");
                }  
            }           
        });
       
       if(!isNewStudent){
           userNametxt.setEnabled(false);
       }
       
       genderOption=new OptionGroup("Gender",genderList);
       genderOption.setStyleName("horizontal");
       genderOption.setRequired(true);
            
       standardtxt=new ComboBox("Standard");
       standardtxt.addItem("Select");
       standardtxt.setValue("Select");       
       standardtxt.setRequired(true);
       standardtxt.setImmediate(true);
       standardtxt.setNullSelectionAllowed(false);
       Iterator it=getStandardList().iterator();
       while(it.hasNext()){
            Std s=(Std) it.next();
            standardtxt.addItem(s.getStd());            
       }
      
       mobiletxt=new TextField();
       mobiletxt.setCaption("Mobile");
       mobiletxt.setRequired(true);
       
       address=new TextField();
       address.setCaption("Address");
       address.setRequired(true);
       
       studform1.addComponent(rollNotxt);
       studform1.addComponent(userNametxt);
       studform1.addComponent(genderOption);
       studform1.addComponent(standardtxt);
       studform1.addComponent(mobiletxt);
       studform1.addComponent(address);

       formLayout.addComponent(studform1);
       
       FormLayout studform2=new FormLayout();       
      
       studNametxt=new TextField();
       studNametxt.setCaption("Name");
       studNametxt.setRequired(true);
                     
       dob=new DateField();
       dob.setCaption("Date Of Birth");
       dob.setDateFormat(GlobalConstants.DATEFORMAT);
       dob.setRequired(true);
       
       passwordtxt=new PasswordField();
       passwordtxt.setCaption("Password");
       passwordtxt.setRequired(true);
              
       eduYeartxt=new ComboBox("Eduaction Year");
       eduYeartxt.addItem("Select");
       eduYeartxt.setValue("Select");
       eduYeartxt.addItem(YEAR);
       eduYeartxt.setRequired(true);
       eduYeartxt.setNullSelectionAllowed(false);
       
       divisiontxt=new ComboBox("Division");
       divisiontxt.addItem("Select");
       divisiontxt.setValue("Select");
       divisiontxt.addItem("A-1");
       divisiontxt.addItem("A-2");
       divisiontxt.setNullSelectionAllowed(false);
       divisiontxt.setRequired(true);
               
       studform2.addComponent(studNametxt);
       studform2.addComponent(passwordtxt);    
       studform2.addComponent(dob);
       studform2.addComponent(divisiontxt);
       studform2.addComponent(eduYeartxt);
       
       
       formLayout.addComponent(studform2);       
       formPanel.setContent(formLayout);
       baseLayout.addComponent(formPanel);
       baseLayout.setComponentAlignment(formPanel,Alignment.MIDDLE_CENTER);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        final Button source=event.getButton();
        if(source==savebtn){
                if(validateStudent()){
                   saveStudent();
                   studentView.setStudentList(MasterDataProvider.getAllStudentList()); 
                   studentView.getStudentTable().removeValueChangeListener((Property.ValueChangeListener)studentView);
                   studentView.getStudentTable().setContainerDataSource(studentView.getStudentMasterContainer());
                   studentView.getStudentTable().setVisibleColumns(StudentMasterContainer.NATURAL_COL_ORDER_STUDENT_INFO);
                   studentView.getStudentTable().setColumnHeaders(StudentMasterContainer.COL_HEADERS_ENGLISH_STUDENT_INFO);
                   studentView.getStudentTable().addValueChangeListener((Property.ValueChangeListener)studentView);
                   this.close();
                   Notification.show("Saved",Notification.Type.WARNING_MESSAGE); 
                }
                
        }else if(source == cancelbtn){
              this.close();
        }
        
    }

    private void saveStudent(){
        Client client=Client.create();
        WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/saveStudent");
        JSONObject inputJson=new JSONObject();
        try{           
           inputJson.put("rollNo", rollNotxt.getValue());  
           inputJson.put("name", studNametxt.getValue());
           inputJson.put("username", userNametxt.getValue());
           inputJson.put("password", passwordtxt.getValue());           
           inputJson.put("gender", genderOption.getValue());           
           inputJson.put("dob", dob.getValue());
           inputJson.put("standard", standardtxt.getValue());
           inputJson.put("edu_year", eduYeartxt.getValue());
           inputJson.put("phnumber", mobiletxt.getValue());
           inputJson.put("division", divisiontxt.getValue());
           inputJson.put("address", address.getValue());   
             if(isNewStudent){
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

    private boolean validateStudent() {
        if(((rollNotxt.getValue()).equals("")) && ((studNametxt.getValue()).equals(""))
           && ((userNametxt.getValue()).equals("")) && ((passwordtxt.getValue()).equals("")) 
           && (genderOption.getValue()==null) && (dob.getValue()==null) 
           && ((standardtxt.getValue()).equals("Select")) && ((divisiontxt.getValue()).equals("Select"))
           && ((mobiletxt.getValue()).equals("")) && ((eduYeartxt.getValue()).equals("Select"))
           && ((address.getValue()).equals(""))){
            Notification.show("Please enter mandetory fields",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if((rollNotxt.getValue()).equals("")){
            Notification.show("Please enter roll number",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if((studNametxt.getValue()).equals("")){
            Notification.show("Please enter name",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if (!StringFieldValidator.isValidName((String) studNametxt.getValue())) {
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
        }else if((standardtxt.getValue()).equals("Select")){
            Notification.show("Please select standard",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if((divisiontxt.getValue()).equals("Select")){
            Notification.show("Please select division",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if((mobiletxt.getValue()).equals("")){
            Notification.show("Please enter contact number",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if(!NumberValidator.isValidCellNumber((String) mobiletxt.getValue()) || !(((String) mobiletxt.getValue()).length() == 10)) {
            Notification.show("Please enter valid contact number",Notification.Type.WARNING_MESSAGE);
            return false;
        }else if((eduYeartxt.getValue().toString()).equals("Select")){
            Notification.show("Please select education year",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if((address.getValue()).equals("")){
            Notification.show("Please enter address",Notification.Type.WARNING_MESSAGE); 
            return false;
        }else if(DateUtil.getDateDifference(""+eduYeartxt.getValue(),(Date)dob.getValue())>0){
            Notification.show("Education year should be greater than date of birth",Notification.Type.WARNING_MESSAGE); 
            return false;
        }
        return true;
    }

    
  private static boolean IsRollNoAlreadyExist(String rollNo) {
        boolean isRollNoExist = false;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/IsRollNoAlreadyExist");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            try {
                inputJson.put("rollNo",rollNo);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }


            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);

            /*
             * if (response.getStatus() != 201) { throw new
             * RuntimeException("Failed : HTTP error code : " +
             * response.getStatus()); }
             */

            String output = response.getEntity(String.class);
            System.out.println("output=" + output);


            JSONObject response1 = null;

            response1 = new JSONObject(output);

            if (response1.getBoolean(GlobalConstants.ISROLLNOEXIST)) {
               isRollNoExist = true;
               Notification.show("Roll No is already exist",Notification.Type.WARNING_MESSAGE);                
            }else{
               isRollNoExist = false;
               //Notification.show("Roll No is already exist",Notification.Type.WARNING_MESSAGE); 
            } 
        } catch (JSONException ex) {
           // Logger.getLogger(DashboardUI.class.getName()).log(Level.SEVERE, null, ex);
         
        }
        
           return isRollNoExist;
    }

    private void setStudentFormData(List<Userprofile> studentList) {
        for(Userprofile studprofile:studentList){
            rollNotxt.setValue(String.valueOf(studprofile.getRno()));
            studNametxt.setValue(String.valueOf(studprofile.getName()));
            userNametxt.setValue(studprofile.getUsername());
            passwordtxt.setValue(studprofile.getPassword());
            
            if(studprofile.getGender()=='M'){
               genderOption.setValue("Male");               
            }else{
               genderOption.setValue("Female");                
            }
            dob.setValue(studprofile.getDob());
            eduYeartxt.setValue(Integer.parseInt(studprofile.getEduYear()));
            address.setValue(studprofile.getAddress());
            
            divisiontxt.setValue(studprofile.getDiv());
            mobiletxt.setValue(String.valueOf(studprofile.getMobile())); 
            standardtxt.setValue(studprofile.getStd());
            prn = studprofile.getPrn();
        }
    }
   
    
   
    
}
