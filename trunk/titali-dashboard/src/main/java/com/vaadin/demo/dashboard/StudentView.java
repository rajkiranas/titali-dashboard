/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaadin.demo.dashboard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.quick.bean.Userprofile;
import com.quick.entity.UserMaster;
import com.quick.global.GlobalConstants;
import com.quick.table.StudentTable;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.quick.data.MasterDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.Runo;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author sonalis
 */

public class StudentView extends VerticalLayout implements View,Button.ClickListener,Property.ValueChangeListener{
   
    private TabSheet editors;
    private StudentTable studentTable;
    private Button addNewStudentbtn;
    private List<Userprofile> studentList;
    private Button filterBtn;
    List<Userprofile> studentFilterList;
    private Button allStudBtn;
    
    public List<Userprofile> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Userprofile> studentList) {
        this.studentList = studentList;
    }
    
    @Override
    public void enter(ViewChangeEvent event) {
        setSizeFull();
        setStudentList(MasterDataProvider.getAllStudentList());
        addComponent(buildBaseStudent());  
    }
    
    private Component buildBaseStudent(){
        
        editors = new TabSheet();
        editors.setSizeFull();       
        
        final CssLayout center = new CssLayout();
        center.setSizeFull();
        center.setCaption("All Student");        
        
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setWidth("100%");        
        toolbar.setHeight("-1px");
        toolbar.setSpacing(false);
        toolbar.setMargin(false);
        center.addComponent(toolbar);     
       
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setMargin(true);
        buttons.setSpacing(true);
        buttons.setSizeUndefined();
        
        allStudBtn=new Button("All Student",(Button.ClickListener)this);
        allStudBtn.addStyleName("default");
        allStudBtn.setImmediate(true);
        buttons.addComponent(allStudBtn);      
        
        filterBtn=new Button("Search Student",(Button.ClickListener)this);
        filterBtn.addStyleName("default");
        filterBtn.setImmediate(true);
        buttons.addComponent(filterBtn);       
                
        addNewStudentbtn=new Button("Add Student",(Button.ClickListener)this);        
        addNewStudentbtn.addStyleName("default");
        addNewStudentbtn.setImmediate(true);
        buttons.addComponent(addNewStudentbtn);
        
        toolbar.addComponent(buttons);
        toolbar.setComponentAlignment(buttons, Alignment.TOP_RIGHT); 
        
       
        center.addComponent(buildStudentTable());
        editors.addComponent(center);        
        return editors;
    }
    
    private Table buildStudentTable() {
        return studentTable=new StudentTable(this);
    }

    public StudentMasterContainer getStudentMasterContainer() {
        return StudentMasterContainer.getAllStudentList(getStudentList());
    }

    @Override
    public void buttonClick(ClickEvent event) {
        final Button source=event.getButton();
        if(source==addNewStudentbtn){
            Window w = new AddStudent(this);
            UI.getCurrent().addWindow(w);
            w.focus();
        }else if(source==filterBtn){
            Window w = new SearchStudentFilter(this);
            UI.getCurrent().addWindow(w);
            w.focus();
        }else if(source==allStudBtn){
           studentTable.removeAllItems();
           studentTable.setContainerDataSource(StudentMasterContainer.getAllStudentList(MasterDataProvider.getAllStudentList()));
           studentTable.setVisibleColumns(StudentMasterContainer.NATURAL_COL_ORDER_STUDENT_INFO);
           studentTable.setColumnHeaders(StudentMasterContainer.COL_HEADERS_ENGLISH_STUDENT_INFO);
        }
    }

    @Override
    public void valueChange(ValueChangeEvent event) {
        Property property=event.getProperty();
        if(property==studentTable){            
//            Item item = studentTable.getItem(studentTable.getValue());
//            int prn=Integer.parseInt(String.valueOf(item.getItemProperty("prn")));
            int prn=0;
            Set<Userprofile> userprofiles=(Set<Userprofile>) property.getValue();
            for(Userprofile u:userprofiles){
              prn=u.getPrn();  
            }            
            setStudentList(getStudentDetailsByPrn(prn));
            Window w = new AddStudent(this,getStudentList());
            UI.getCurrent().addWindow(w);
            w.focus();
        }
    }
    
    
   public static List<Userprofile> getStudentDetailsByPrn(int prn) {
         List<Userprofile> studentList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/getStudentDetailsByPrn");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            try {
                inputJson.put("prn",prn);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<Userprofile>>() {
            }.getType();
            Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
            
            studentList= gson.fromJson(outNObject.getString(GlobalConstants.student), listType);
        } catch (JSONException ex) {
           // Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studentList;
            
    }

    public StudentTable getStudentTable() {
        return studentTable;
    }

   public void searchFilterCriteria(String searchCriteria, String searchValue) {
      searchStudentFilterCriteria(searchCriteria,searchValue);
   }

   private void searchStudentFilterCriteria(String searchCriteria, String searchValue) {
       studentFilterList=getsearchStudentFilterCriteria(searchCriteria,searchValue);
       if(studentFilterList!=null && !studentFilterList.isEmpty()){
           studentTable.removeAllItems();
           studentTable.setContainerDataSource(StudentMasterContainer.getAllStudentList(studentFilterList));
           studentTable.setVisibleColumns(StudentMasterContainer.NATURAL_COL_ORDER_STUDENT_INFO);
           studentTable.setColumnHeaders(StudentMasterContainer.COL_HEADERS_ENGLISH_STUDENT_INFO);
       }else{
            Notification.show("No such record found",Notification.Type.WARNING_MESSAGE);            
       }
    }
    
   private List<Userprofile> getsearchStudentFilterCriteria(String searchCriteria, String searchValue) {
      List<Userprofile> searchstudentList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/getsearchStudentFilterCriteriaList");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            try {
                inputJson.put("searchCriteria",searchCriteria);
                inputJson.put("searchValue",searchValue);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }            
            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<Userprofile>>() {
            }.getType();
            
            searchstudentList= new Gson().fromJson(outNObject.getString(GlobalConstants.STUDENTLIST), listType);
        } catch (JSONException ex) {
           // Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searchstudentList;
            
    }
    
}
