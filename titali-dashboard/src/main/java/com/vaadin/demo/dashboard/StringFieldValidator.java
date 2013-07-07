/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaadin.demo.dashboard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sonalis
 */
public class StringFieldValidator {
    
public static Pattern m_objPattern;
    public static Matcher m_objMatcher;

    /**
     * This method is used to validate the user name name
     * @param p_strField 
     * @return true if valid name otherwise false
     */
    public static boolean isValidName(String p_strField) {
        boolean isValid = false;
        
        m_objPattern = Pattern.compile("[a-z A-Z]+");
        m_objMatcher = m_objPattern.matcher(p_strField);
        while (m_objMatcher.find()) {            
            if (m_objMatcher.group().equals(p_strField)) {
                isValid = true;
            }
        }
        return isValid;
    }
    
    /**
     * This method is used to validate the city name
     * @param p_strField 
     * @return true if valid user city name otherwise false
     */
    public static boolean isValidCityName(String p_strField) {
        boolean isValid = false;
        
        m_objPattern = Pattern.compile("[a-zA-Z ]+");
        m_objMatcher = m_objPattern.matcher(p_strField);
        while (m_objMatcher.find()) {            
            if (m_objMatcher.group().equals(p_strField)) {
                isValid = true;
            }
        }
        return isValid;
    }    
}
