/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.ui.exam;

/**
 * DISCLAIMER
 * 
 * The quality of the code is such that you should not copy any of it as best
 * practice how to build Vaadin applications.
 * 
 * @author jouni@vaadin.com
 * 
 */



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quick.bean.ExamBean;
import com.quick.bean.Userprofile;
import com.vaadin.demo.dashboard.TopSixTheatersChart;


import com.quick.entity.Notices;
import com.quick.entity.Whatsnew;
import com.quick.entity.Whoisdoingwhat;
import com.quick.table.MyDashBoardDataProvider;
import java.text.DecimalFormat;

import com.vaadin.data.Property;
import com.quick.data.Generator;
import com.quick.global.GlobalConstants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.RowHeaderMode;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class StudentExam extends VerticalLayout implements View  {

         TextField subtxt ;
         TextField markstxt;
         TextField scoretxt;
         TextField questionstxt;
         Button startExam;;
         
        
    Table t;
    MyDashBoardDataProvider boardDataProvider = new MyDashBoardDataProvider();
    private List<ExamBean> selectedExam;

   
    /**
     * @return the selectedExam
     */
    public List<ExamBean> getSelectedExam() {
        return selectedExam;
    }

    /**
     * @param selectedExam the selectedExam to set
     */
    public void setSelectedExam(List<ExamBean> selectedExam) {
        this.selectedExam = selectedExam;
    }

    
    
    
    
    public StudentExam() {
       // addStyleName("dashboard-view");
    }
    
    public void buildUi(){
        
        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setSpacing(true);
        top.addStyleName("toolbar");
        addComponent(top);
        final Label title = new Label("Student Exam");
        title.setSizeUndefined();
        title.addStyleName("h1");
        top.addComponent(title);
        top.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        top.setExpandRatio(title, 1);

        Button notify = new Button("2");
        notify.setDescription("Notifications (2 unread)");
        // notify.addStyleName("borderless");
        notify.addStyleName("notifications");
        notify.addStyleName("unread");
        notify.addStyleName("icon-only");
        notify.addStyleName("icon-bell");
        notify.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                //((DashboardUI) getUI()).clearDashboardButtonBadge();
                event.getButton().removeStyleName("unread");
                event.getButton().setDescription("Notifications");

                if (notifications != null && notifications.getUI() != null)
                    notifications.close();
                else {
                    buildNotifications(event);
                    getUI().addWindow(notifications);
                    notifications.focus();
                    ((CssLayout) getUI().getContent())
                            .addLayoutClickListener(new LayoutClickListener() {
                                @Override
                                public void layoutClick(LayoutClickEvent event) {
                                    notifications.close();
                                    ((CssLayout) getUI().getContent())
                                            .removeLayoutClickListener(this);
                                }
                            });
                }

            }
        });
        top.addComponent(notify);
        top.setComponentAlignment(notify, Alignment.MIDDLE_LEFT);

        Button edit = new Button();
        edit.addStyleName("icon-edit");
        edit.addStyleName("icon-only");
        top.addComponent(edit);
        edit.setDescription("Edit Dashboard");
        edit.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                final Window w = new Window("Edit Dashboard");

                w.setModal(true);
                w.setClosable(false);
                w.setResizable(false);
                w.addStyleName("edit-dashboard");

                getUI().addWindow(w);

                w.setContent(new VerticalLayout() {
                    TextField name = new TextField("Dashboard Name");
                    {
                        addComponent(new FormLayout() {
                            {
                                setSizeUndefined();
                                setMargin(true);
                                name.setValue(title.getValue());
                                addComponent(name);
                                name.focus();
                                name.selectAll();
                            }
                        });

                        addComponent(new HorizontalLayout() {
                            {
                                setMargin(true);
                                setSpacing(true);
                                addStyleName("footer");
                                setWidth("100%");

                                Button cancel = new Button("Cancel");
                                cancel.addClickListener(new ClickListener() {
                                    @Override
                                    public void buttonClick(ClickEvent event) {
                                        w.close();
                                    }
                                });
                                cancel.setClickShortcut(KeyCode.ESCAPE, null);
                                addComponent(cancel);
                                setExpandRatio(cancel, 1);
                                setComponentAlignment(cancel,
                                        Alignment.TOP_RIGHT);

                                Button ok = new Button("Save");
                                ok.addStyleName("wide");
                                ok.addStyleName("default");
                                ok.addClickListener(new ClickListener() {
                                    @Override
                                    public void buttonClick(ClickEvent event) {
                                        title.setValue(name.getValue());
                                        w.close();
                                    }
                                });
                                ok.setClickShortcut(KeyCode.ENTER, null);
                                addComponent(ok);
                            }
                        });

                    }
                });

            }
        });
        top.setComponentAlignment(edit, Alignment.MIDDLE_LEFT);
        startExam = new Button("Start Exam");
        startExam.addStyleName("default");
        startExam.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                startExam();
            }

            
        });
        
        
        top.addComponent(startExam);
        top.setComponentAlignment(startExam, Alignment.MIDDLE_LEFT);
        HorizontalLayout row = new HorizontalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        addComponent(row);
        setExpandRatio(row, 2); 

        Userprofile userprofile = (Userprofile) getSession().getAttribute(GlobalConstants.CurrentUserProfile);
        Table examlistTbl = StudentExamDataProvider.getStudentExamList(getExamList(userprofile.getStd(),userprofile.getDiv()));
        examlistTbl.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
               ExamBean eb = (ExamBean) event.getProperty().getValue(); 
               setSelectedExam(getSelectedExamDetailsById(eb.getExamId()));
               updateExamDetails();
            }

           
        });
        row.addComponent(createPanel(examlistTbl));
        row.addComponent(createPanel(StudentExamDataProvider.getMyExamPieChart(null)));

        row = new HorizontalLayout();
        row.setMargin(true);
        row.setSizeFull();
        row.setSpacing(true);
        addComponent(row);
        setExpandRatio(row, 2);

        t = new Table() {
            @Override
            protected String formatPropertyValue(Object rowId, Object colId,
                    Property<?> property) {
                if (colId.equals("Revenue")) {
                    if (property != null && property.getValue() != null) {
                        Double r = (Double) property.getValue();
                        String ret = new DecimalFormat("#.##").format(r);
                        return "$" + ret;
                    } else {
                        return "";
                    }
                }
                return super.formatPropertyValue(rowId, colId, property);
            }
        };
        t.setCaption("Top 10 Titles by Revenue");

        t.setWidth("100%");
        t.setPageLength(0);
        t.addStyleName("plain");
        t.addStyleName("borderless");
        t.setSortEnabled(false);
        t.setColumnAlignment("Revenue", Align.RIGHT);
        t.setRowHeaderMode(RowHeaderMode.INDEX);

        row.addComponent(createPanel(getSelectedExamDetails(null)));

        row.addComponent(createPanel(StudentExamDataProvider.getExamResult(null)));

    }
   
    public  Component getSelectedExamDetails(Object object) {
        FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);
        subtxt =new TextField();
        subtxt.setImmediate(true);
        subtxt.setCaption("subject");
        
        markstxt =new TextField();
        markstxt.setCaption("Marks");
        markstxt.setImmediate(true);
        
        scoretxt =new TextField();
        scoretxt.setCaption("Score");
        scoretxt.setImmediate(true);
        
        questionstxt =new TextField();
        questionstxt.setCaption("Questions");
        questionstxt.setImmediate(true);
        formLayout.addComponent(subtxt);
        formLayout.addComponent(markstxt);
        formLayout.addComponent(scoretxt);
        formLayout.addComponent(questionstxt);
        //throw new UnsupportedOperationException("Not yet implemented");
        return formLayout;
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
        configure.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Not implemented in this demo");
            }
        });
        panel.addComponent(configure);
        

        panel.addComponent(content);
        return panel;
    }

    @Override
    public void enter(ViewChangeEvent event) {
         setSizeFull();
         buildUi();
//        DataProvider dataProvider = ((DashboardUI) getUI()).dataProvider;
//        t.setContainerDataSource(dataProvider.getRevenueByTitle());
    }

    Window notifications;

    private void buildNotifications(ClickEvent event) {
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
        notifications.setCloseShortcut(KeyCode.ESCAPE, null);

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
    
    
    
     private void updateExamDetails() {
       updateSelectedExamDetailsPanel();         
     }

     private void updateSelectedExamDetailsPanel() {
        ExamBean eb = getSelectedExam().get(0);
      
        subtxt.setValue(eb.getSub());
       
        markstxt.setValue(""+eb.getTotalMarks());
        
        scoretxt.setValue(""+eb.getPassingMarks());
        
        questionstxt.setValue(""+eb.getNoOfQuestions());
    }
    
  
      private void startExam() {
         //throw new UnsupportedOperationException("Not yet implemented");
      }
    
    
    
    public  List<ExamBean> getExamList(String std,String div) {
       
         List<ExamBean> examList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/examResource/getExamList");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
             try{           
                inputJson.put("std", std);
                inputJson.put("div", div);
             }catch(Exception ex){
                 
             }
            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<ExamBean>>() {
            }.getType();
            
            examList= new Gson().fromJson(outNObject.getString(GlobalConstants.EXAMLIST), listType);
        } catch (JSONException ex) {
          //  Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return examList;
            
    }
    
    
    public List<ExamBean> getSelectedExamDetailsById(int examId){
         List<ExamBean> selectedExamDetails = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/examResource/getExamDetailsById");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
             try{           
                inputJson.put("exmId", examId);
//                inputJson.put("div", "A-1");
             }catch(Exception ex){
                 
             }
            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<ExamBean>>() {
            }.getType();
            
            selectedExamDetails= new Gson().fromJson(outNObject.getString(GlobalConstants.EXAMLIST), listType);
        } catch (JSONException ex) {
          //  Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return selectedExamDetails;
    }

    

   

    

}
