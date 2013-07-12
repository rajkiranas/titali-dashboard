
package com.quick.global;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class GlobalConstants
{
    

    /** Creates a new instance of EQ_GlobalConstants */
    public GlobalConstants() {
    }   
  
    public static final String CMS="CMS Dashboard";
    public static final String Footer_Line="POWERED BY SATERI SYSTEMS INC.";
    public static final String Logout="Logout";
    public static final String DATEFORMAT = "MM/dd/yyyy";
    public static final String REGEX_PATTERN="[a-z]+";;
  
    private static Properties properties = new Properties();
    public static final String userName = "userName";
    public static final String password = "password";
    public static final String KEY="sateri@gmail.com";
    public static final String IV ="initialvector123";
    public static final int YES = 1;
    public static final int NO = 0;
    public static final String isAuthenticated = "isAuthenticated";
    public static final String admin = "admin";
    public static final String teacher = "teacher";
    public static final String student = "student";
    public static final String role="role";
    public static final String WHATSNEW = "whatsNew";
    public static final String NOTICES = "notices";
    public static final String WHOSEDOINGWHAT = "Whodwht";
    public static final String STANDARDLIST = "standardList";
    public static final String STUDENTLIST = "studentList";
    public static final String QUALIFICATIONLIST = "qualificationList";
    public static final String ISROLLNOEXIST="isRollNoExist";
    public static final String TEACHERLIST = "teacherList";
    public static final String emptyString="";
    public static final String HYPHEN = "-";
    public static final String ISUSERNAMEEXIST="isUsernameExist";
    public static final String QUICKLEARNLIST = "quickLearnList";
    public static final String STDSUBLIST = "stdsub";
    public static final String STATUS = "Status";
    public static final String MYQUICKNOTEs="quicknotes";
    public static final String subjectList = "subjectList";
    public static final String divisionList = "divisionList";
    public static final String teacherStdDivSubIdList = "teacherStdDivSubIdList";
    public static final String Quick_Upload = "Quick Upload";
    public static final String Save = "Save";
    public static final String Cancel = "Cancel";
    public static final String toolbar_style = "toolbar";
    public static final String h1_style = "h1";
    public static final String default_style = "default";
    
    
    
     // == exam ============
    public static final String EXAMRESOURCE = "examResource";
    public static final String EXAMLIST="examList";
    public static final String EXAMQUESTIONLIST ="examQuestionList";
    public static final String CurrentUserProfile = "CurrentUserProfile";
    
    //===========URL FOR SERVICES===========
   public static final String DASHBOARD_URL="DASHBOARD_URL";
   public static final String LOGIN_URL="LOGIN_URL";
   public static final String STANDARD_LISTING_URL="STANDARD_LISTING_URL";
   public static final String QUICK_LEARN_UPLOAD_LIST_URL="QUICK_LEARN_UPLOAD_LIST_URL";
    
    static
    {
        try {
            loadProperties();
        } catch (IOException ex) {
          //  logger.debug("Exception occured in loadProperties() method, Exception=", ex);
            //Logger.getLogger(GlobalConstants.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    
    
    
    static class PropertyLoader {

        public InputStream getProperty() {
            InputStream l_objInputStream = getClass().getClassLoader().
                    getResourceAsStream("Default.properties");
            return l_objInputStream;
        }
    }

    private static void loadProperties() throws IOException {
            PropertyLoader PL = new PropertyLoader();
            InputStream inputStream = PL.getProperty();
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
             // logger.debug("Properties File not found!");
            }
    }
 
     public static String getProperty(String key)
     {
         return properties.getProperty(key);
     }
}
