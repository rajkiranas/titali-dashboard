/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.utilities;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sonalis
 */
public class DateUtil {   
        
    
  public static Calendar getCalenderInstance(){
         Calendar c= Calendar.getInstance();
         return c;
     }  
  
  public static int getYear(Calendar c){
        return c.get(Calendar.YEAR);
     }
     
  public static int getDateDifference(String value, Date date) {
        
         java.util.Calendar calendar = java.util.Calendar.getInstance();
         calendar.setTime(date);
         int dob=calendar.get(Calendar.YEAR);
         
         return dob-Integer.parseInt(value);
        
    }
  
    public static int getDateDifference(Date startDate, Date endDate ) {

        return (int) ((startDate.getTime() - endDate.getTime()) / (1000 * 60 * 60 * 24));
    }
    
}
