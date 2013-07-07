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
public class NumberValidator {
    
public static Pattern m_objPattern;
    public static Matcher m_objMatcher;

    /**
     * This method is used to validate the cell phone number
     * @param p_strPhoneNumber
     * @return boolean
     */
    public static boolean isValidCellNumber(String p_strPhoneNumber) {
        boolean isValid = false;
        
        m_objPattern = Pattern.compile("[0-9]+");
        m_objMatcher = m_objPattern.matcher(p_strPhoneNumber);
        while (m_objMatcher.find()) {            
            if (m_objMatcher.group().equals(p_strPhoneNumber)) {
                isValid = true;
            }
        }
        return isValid;
    }
    
    
    /**
     * This method is used to validate the zip code
     * @param p_strZipCode
     * @return boolean
     */
    public static boolean isValidZipCode(String p_strZipCode) {
        boolean isValid = false;
        
        m_objPattern = Pattern.compile("[0-9]+");
        m_objMatcher = m_objPattern.matcher(p_strZipCode);
        while (m_objMatcher.find()) {            
            if (m_objMatcher.group().equals(p_strZipCode)) {
                isValid = true;
            }
        }
        return isValid;
    }
    
    /**
     * This method is used to validate the number
     * @param p_strNumber
     * @return boolean
     */
    public static boolean isValidNumber(String p_strNumber) {
        boolean isValid = false;
        
        m_objPattern = Pattern.compile("[0-9]+");
        m_objMatcher = m_objPattern.matcher(p_strNumber);
        while (m_objMatcher.find()) {            
            if (m_objMatcher.group().equals(p_strNumber)) {
                isValid = true;
            }
        }
        return isValid;
    }
}
