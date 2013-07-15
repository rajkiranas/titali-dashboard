/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.utilities;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;

/**
 *
 * @author rajkiran
 */
public class UIUtils {
    public static CssLayout createPanel(Component content) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        //panel.setSizeFull();
        panel.setWidth("100%");
        panel.setHeight("97%");
        panel.addComponent(content);
        return panel;
    }
}
