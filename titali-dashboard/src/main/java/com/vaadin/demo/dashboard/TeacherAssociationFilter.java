/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaadin.demo.dashboard;

import com.quick.bean.QuickLearn;
import com.quick.bean.TeacherStddivSubIdBean;
import com.quick.data.MasterDataProvider;
import com.quick.entity.Std;
import com.quick.table.TeacherSubjectAssociationTable;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sonalis
 */
public class TeacherAssociationFilter extends Window implements Button.ClickListener{
    
    private AddTeacher addTeacher;
    private ComboBox standardtxt;
    private ComboBox divisiontxt;
    private ListSelect subjecttxt;
    private Button addbtn;
    private List<Std> standardList;
    private List<QuickLearn> subjectList;
    private List<QuickLearn> divisionList;
    private VerticalLayout l;
    private Button confirmbtn;
    private Button cancelbtn;
    private Button clearAllbtn;
    private Button addAnotherSubbtn;    
  //  private List<TeacherStddivSubIdBean> teacherSubAssociationList=new ArrayList<TeacherStddivSubIdBean>();
    private Set subjectVal=new HashSet();
    private TeacherStddivSubIdBean teacherStddivSubIdBean; 
    String username=null;
    private boolean isTeacherstdDivsubassociationNew=false;
    
    public TeacherAssociationFilter(AddTeacher addTeacher,String uname){
        this.addTeacher=addTeacher;
        this.username=uname;
        isTeacherstdDivsubassociationNew=true;
        l = new VerticalLayout();
        l.setSpacing(true);
        setCaption("Teacher Subject Association");
        setContent(l);
        center();
        setModal(true);
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(true);
        addStyleName("no-vertical-drag-hints");
        addStyleName("no-horizontal-drag-hints");
        setStandardList(MasterDataProvider.getStandardList());
        buildMainLayout(); 
       
    }

    public TeacherAssociationFilter(AddTeacher addTeacher, TeacherStddivSubIdBean bean,String uname) {
        this.addTeacher=addTeacher;   
        this.username=uname;
        this.teacherStddivSubIdBean=bean;
        l = new VerticalLayout();
        l.setSpacing(true);
        setCaption("Teacher Subject Association");
        setContent(l);
        center();
        setModal(true);
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(true);
        addStyleName("no-vertical-drag-hints");
        addStyleName("no-horizontal-drag-hints");
        setStandardList(MasterDataProvider.getStandardList());
        buildMainLayout(); 
        setTeacherStdDivSubData(bean);
    }
    
    private void buildMainLayout(){        
        HorizontalLayout details = new HorizontalLayout();
        details.setSpacing(true);
        details.setMargin(true);
        l.addComponent(details);
        
        divisiontxt = new ComboBox("Division");
        divisiontxt.setInputPrompt("Division");
        divisiontxt.setNullSelectionAllowed(false);
        divisiontxt.setImmediate(true);
        
        subjecttxt = new ListSelect("Subject");        
        subjecttxt.setRows(5);
        subjecttxt.setNullSelectionAllowed(false);
        subjecttxt.setMultiSelect(true);
        subjecttxt.setImmediate(true);
        subjecttxt.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                 subjectVal= (Set) event.getProperty().getValue();
            }
        });
        
        standardtxt=new ComboBox("Standard");
        standardtxt.setRequired(true);
        standardtxt.addItem("Select");
        standardtxt.setValue("Select"); 
        standardtxt.setNullSelectionAllowed(false);
        standardtxt.setImmediate(true);
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
                    setDivisionList(MasterDataProvider.getDivisionBystd(std));
                    if(!getSubjectList().isEmpty()){
                         Iterator subit=getSubjectList().iterator();
                         while(subit.hasNext()){
                         QuickLearn s=(QuickLearn) subit.next();
                         subjecttxt.addItem(s.getSub());            
                       }  
                    } 
                    
                    if(!getDivisionList().isEmpty()){
                         Iterator divit=getDivisionList().iterator();
                         while(divit.hasNext()){
                         QuickLearn s=(QuickLearn) divit.next();
                         divisiontxt.addItem(s.getFordiv());            
                       }  
                    }
                    
               }
            }
        });
        
        addbtn= new Button("Add",(Button.ClickListener)this);
        addbtn.addStyleName("default");
        addbtn.setImmediate(true);
       

        details.addComponent(standardtxt);
        details.addComponent(divisiontxt);
        details.addComponent(subjecttxt);
        details.addComponent(addbtn);
        l.addComponent(details);
        l.setComponentAlignment(details, Alignment.MIDDLE_CENTER); 
        
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

    public List<QuickLearn> getDivisionList() {
        return divisionList;
    }

    public void setDivisionList(List<QuickLearn> divisionList) {
        this.divisionList = divisionList;
    }
    
     @Override
    public void buttonClick(ClickEvent event) {
       final Button source=event.getButton();
       if(source==addbtn){
           if(isTeacherstdDivsubassociationNew){
               Iterator it=subjectVal.iterator();
               while(it.hasNext()){
                    teacherStddivSubIdBean=new TeacherStddivSubIdBean();
                    teacherStddivSubIdBean.setUsername(username);
                    teacherStddivSubIdBean.setDiv(String.valueOf(divisiontxt.getValue()));
                    teacherStddivSubIdBean.setStd(String.valueOf(standardtxt.getValue()));
                    teacherStddivSubIdBean.setSub(""+it.next());
                    addTeacher.getTeacherSubAssociationList().add(teacherStddivSubIdBean);               
                }                
           }else{
               Iterator it=subjectVal.iterator();
               while(it.hasNext()){
                  addTeacher.getTeacherSubAssociationList().remove(teacherStddivSubIdBean);
                  teacherStddivSubIdBean=new TeacherStddivSubIdBean();
                  teacherStddivSubIdBean.setUsername(username);
                  teacherStddivSubIdBean.setDiv(String.valueOf(divisiontxt.getValue()));
                  teacherStddivSubIdBean.setStd(String.valueOf(standardtxt.getValue()));
                  teacherStddivSubIdBean.setSub(""+it.next());
                  addTeacher.getTeacherSubAssociationList().add(teacherStddivSubIdBean);  
               }               
           }
           addTeacher.saveTeacherSubjectAssociation(addTeacher.getTeacherSubAssociationList());               
           this.close();
            
       }
    }

    private void setTeacherStdDivSubData(TeacherStddivSubIdBean bean) {
        standardtxt.setValue(bean.getStd());
        divisiontxt.setValue(bean.getDiv());
        subjecttxt.setValue(bean.getSub());
    }
}
