/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.container;

import com.quick.bean.ExamBean;
import com.vaadin.data.util.BeanItemContainer;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rajkiran
 */
public class StudentExamListContainer extends BeanItemContainer<ExamBean> {
    public static Object[] NATURAL_COL_ORDER_QUICKLEARN={"examId","exName","startDt","endDt"};
    public static String[] COL_HEADERS_ENGLISH_QUICKLEARN={"ExamId","ExamName","StartDate","EndDate"};
    
    public StudentExamListContainer(){
        super(ExamBean.class);
    }
    
    
    public static StudentExamListContainer getExamListContainer(List<ExamBean> list){
        StudentExamListContainer container = new StudentExamListContainer();
       ExamBean bean =null;
        for(ExamBean eb:list){
           bean = new ExamBean();
           bean.setExName(eb.getExName());
           bean.setStartDt(eb.getStartDt()); 
           bean.setEndDt(eb.getEndDt()); 
           bean.setExamId(eb.getExamId()); 
           container.addItem(bean);
        }
        
        return container;
    }
    
}
