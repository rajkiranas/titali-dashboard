/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.ui.QuickLearn;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quick.bean.MasteParmBean;
import com.quick.bean.QuickLearn;
import com.quick.global.GlobalConstants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author rajkiran
 */
public class MyNotes extends VerticalLayout {

    public MyNotes() {
        setSizeFull();
    }

    public MyNotes(QuickLearn quickLearn) {
        setSizeFull();
        setSpacing(true);
        setMargin(true);

        if (quickLearn != null) {
            RichTextArea richTextArea = new RichTextArea();
            richTextArea.setSizeFull();
            //richTextArea.setHeight("98%");
            addComponent(richTextArea);
            setExpandRatio(richTextArea, 2);
            richTextArea.setValue(quickLearn.getLectureNotes());
            

            if (quickLearn.getLectureNotesInformation() != null && !quickLearn.getLectureNotesInformation().equals("")) {
                TextArea notes = new TextArea("Notes information");
                notes.setValue(quickLearn.getLectureNotesInformation());
                notes.setSizeFull();
                addComponent(notes);
                setExpandRatio(notes, 0.5f);

            }

        }else{
            addComponent(new Label(" <p2><B> No data found on this topic </B></p2> ",ContentMode.HTML));
        }
        //setComponentAlignment(richTextArea, ALIGNMENT_DEFAULT);


    }
//    public void getStandardList(){
//          try {
//           
//
//            Client client = Client.create();
//            WebResource webResource = client.resource("http://localhost:8084/titali/rest/MasterParam/stdsub");
//            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
//            JSONObject inputJson = new JSONObject();
//            try {
//                inputJson.put("standard", "I");
//                inputJson.put("division", "A-1");
//            } catch (JSONException ex) {
//                ex.printStackTrace();
//            }
//
//            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
//
//          
//            JSONObject outNObject = null;
//            String output = response.getEntity(String.class);
//            outNObject = new JSONObject(output);
//
//            Type listType = new TypeToken<ArrayList<MasteParmBean>>() {
//            }.getType();
//            
//            stdlist = new Gson().fromJson(outNObject.getString(GlobalConstants.STDSUBLIST), listType);
//            
//        } catch (JSONException ex) {
//        }
//
//    }
}
