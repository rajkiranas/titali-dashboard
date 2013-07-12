/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.quick.bean.MasteParmBean;
import com.quick.bean.QuickLearn;
import com.quick.bean.TeacherStddivSubIdBean;
import com.quick.bean.Userprofile;
import com.quick.entity.QualificationMaster;
import com.quick.entity.Std;
import com.quick.global.GlobalConstants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vaadin.demo.dashboard.AddStudent;
import com.vaadin.ui.Notification;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author suyogn
 */
public class MasterDataProvider {
    
     public static List<Std> getStandardList() {
         List<Std> standardList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(GlobalConstants.getProperty(GlobalConstants.STANDARD_LISTING_URL));
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<Std>>() {
            }.getType();
            
            standardList= new Gson().fromJson(outNObject.getString(GlobalConstants.STANDARDLIST), listType);
        } catch (JSONException ex) {
            Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return standardList;
            
    }
     
    public static List<Userprofile> getAllStudentList() {
         List<Userprofile> studentList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/getAllstudentList");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<Userprofile>>() {
            }.getType();
            
            studentList= new Gson().fromJson(outNObject.getString(GlobalConstants.STUDENTLIST), listType);
        } catch (JSONException ex) {
            Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studentList;
            
    }
    
     public static List<QualificationMaster> getQualificationList() {
         List<QualificationMaster> qualificationList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/getQualificationList");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<QualificationMaster>>() {
            }.getType();
            
            qualificationList= new Gson().fromJson(outNObject.getString(GlobalConstants.QUALIFICATIONLIST), listType);
        } catch (JSONException ex) {
            Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qualificationList;
            
    }

   public static List<Userprofile> getAllTeacherList() {
         List<Userprofile> teacherList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/getAllTeacherList");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<Userprofile>>() {
            }.getType();
            Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
            teacherList= gson.fromJson(outNObject.getString(GlobalConstants.TEACHERLIST), listType);
        } catch (JSONException ex) {
            Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teacherList;
            
    }
    
   
   public static boolean IsUsernameAlreadyExist(String username) {
        boolean isUsernameExist = false;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/IsUsernameAlreadyExist");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            try {
                inputJson.put("username",username);
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

            if (response1.getBoolean(GlobalConstants.ISUSERNAMEEXIST)) {
               isUsernameExist = true;
               Notification.show("Username is already exist",Notification.Type.WARNING_MESSAGE);                
            }else{
               isUsernameExist = false;
              // Notification.show("Username is already exist",Notification.Type.WARNING_MESSAGE); 
            } 
        } catch (JSONException ex) {
           // Logger.getLogger(DashboardUI.class.getName()).log(Level.SEVERE, null, ex);
         
        }
        
       return isUsernameExist;
   }

  public static List<MasteParmBean> getQuickLearnUploadList() {
        List<MasteParmBean> uploadList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(GlobalConstants.getProperty(GlobalConstants.QUICK_LEARN_UPLOAD_LIST_URL));
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<MasteParmBean>>() {
            }.getType();
            Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
            uploadList= gson.fromJson(outNObject.getString(GlobalConstants.QUICKLEARNLIST), listType);
        } catch (JSONException ex) {
            Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return uploadList;
    }
       
       
   public static List<QuickLearn> getSubjectBystd(String std) {
         List<QuickLearn> subjectList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/MasterParam/getSubjectBystd");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
             try{           
                inputJson.put("std", std);  
             }catch(Exception ex){
                ex.printStackTrace(); 
             }
            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<QuickLearn>>() {
            }.getType();
            
            subjectList= new Gson().fromJson(outNObject.getString(GlobalConstants.subjectList), listType);
        } catch (JSONException ex) {
          //  Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjectList;
            
    }
   
   
   public static List<QuickLearn> getDivisionBystd(String std) {
         List<QuickLearn> subjectList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/MasterParam/getDivisionBystd");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
             try{           
                inputJson.put("std", std);  
             }catch(Exception ex){
                ex.printStackTrace(); 
             }
            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<QuickLearn>>() {
            }.getType();
            
            subjectList= new Gson().fromJson(outNObject.getString(GlobalConstants.divisionList), listType);
        } catch (JSONException ex) {
          //  Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjectList;
            
    }

    

   
   
}
