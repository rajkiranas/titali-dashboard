/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.ui.QuickLearn;

import com.vaadin.ui.Label;

/**
 *
 * @author suyogn
 */
public class HtmlBrowseForm extends com.vaadin.ui.LoginForm {

    /**
     * This is parameterized constructor
     * @param caption
     */
   
   
   
    String url;
    String token;
    HtmlBrowseForm(String url, String token) {
        //setCaption(caption);
//        setStyleName("v-loginform");
//        setImmediate(false);
        this.url=url;
        this.token=token;

    }

    /**
     *
     * @return
     */
    @Override
    protected String getLoginHTML() {
        // Application URI needed for submitting form
        String appUri = "abc" + "/";

        String x, h, b; // XML header, HTML head and body


        x = "<!DOCTYPE html PUBLIC \"-//W3C//DTD "
                + "XHTML 1.0 Transitional//EN\" "
                + "\"http://www.w3.org/TR/xhtml1/"
                + "DTD/xhtml1-transitional.dtd\">\n";


       

        h = "<head><script type='text/javascript'>"
                + "var setTarget = function() {"
                + "  var uri = '" + appUri + "loginHandler';"
                + "  var f = document.getElementById('loginf');"
                + "  document.forms[0].action = uri;"
                + "  document.forms[0].username.focus();"
                + "};"
                + ""
                + "var styles = window.parent.document.styleSheets;"
                + "for(var j = 0; j < styles.length; j++) {\n"
                + "  if(styles[j].href) {"
                + "    var stylesheet = document.createElement('link');\n"
                + "    stylesheet.setAttribute('rel', 'stylesheet');\n"
                + "    stylesheet.setAttribute('type', 'text/css');\n"
                + "    stylesheet.setAttribute('href', styles[j].href);\n"
                + "    document.getElementsByTagName('head')[0]"
                + "                .appendChild(stylesheet);\n"
                + "  }"
                + "}\n"
                + "function submitOnEnter(e) {"
                + "  var keycode = e.keyCode || e.which;"
                + "  if (keycode == 13) {document.forms[0].submit();}"
                + "}\n"
                + "</script>"
                + "</head>";

       
        Label sample = new Label("<form action='"+this.url+"?nexturl=null' method ='post' enctype='multipart/form-data'>"
            + "<input type='file' name='file'/>"
            + "<input type='hidden' name='token' value='"+this.token+"'/>"
            + "<input type='submit' value='go' />"
            + "</form>", Label.CONTENT_XHTML);

        b = "<body>"               
                + sample
                + "</body>";


        return (x + "<html>" +  b + "</html>");
    }
   
    /* b = "<body onload='setTarget();'"
                + "  style='margin:0;padding:0; background:transparent;overflow:hidden;'"
                + "  class='"
                + "'>"
                + "<div class='v-app v-app-loginpage'"
                + "     style='background:transparent;'>"
                + "<iframe name='logintarget' style='width:0;height:0;"
                + "border:0;margin:0;padding:0;'></iframe>"
                + "<form id='loginf' target='logintarget'"
                + "      onkeypress='submitOnEnter(event)'"
                + "      method='post'>"
                + "<table>"
                + "<tr><td> Username </td>"
                + "<td><input class='v-textfield' style='display:block;'"
                + "           type='text' name='username'></td></tr>"
                + "<tr><td> Password </td>"
                + "<td><input class='v-textfield'"
                + "style='display:block;' type='password'"
                + "name='password'></td></tr>"
                + "<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr>"
                + "<td></td>"
                + "<td>"
                + " <div>"
                + "<div onclick='document.forms[0].submit();'"
                + "tabindex='0' class='v-button' role='button'>"
                + "<span class='v-button-wrap'>"
                + "<span class='v-button-caption'>"
                + " Login </span>"
                + "</span></div></div></form></div></td></tr>     "
                + "</table>"
                + "<div id='player'></div>"
                +"<script>"
                +"var tag = document.createElement('script');"
                +" tag.src = 'https://www.youtube.com/iframe_api';"
                +" var firstScriptTag = document.getElementsByTagName('script')[0];"
                +" firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);"
                + " var player;"
                +" function onYouTubeIframeAPIReady() {"
                +" player = new YT.Player('player', {"
                +"height: '390',"
                +" width: '640',"
                +" videoId: 'M7lc1UVf-VE',"
                +" events: {"
                +"  'onReady': onPlayerReady,"
                +" 'onStateChange': onPlayerStateChange"
                +" }"
                +" });"
                +" }"
                +" function onPlayerReady(event) { event.target.playVideo();      }"
                +" var done = false;      function onPlayerStateChange(event) {        if (event.data == YT.PlayerState.PLAYING && !done) {          setTimeout(stopVideo, 6000);          done = true;        }      }     function stopVideo() {        player.stopVideo();      }"           
                +" </script>"
                + "</body>";*/
}