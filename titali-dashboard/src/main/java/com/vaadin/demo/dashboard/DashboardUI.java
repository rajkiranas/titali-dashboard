/**
 * DISCLAIMER
 * 
 * The quality of the code is such that you should not copy any of it as best
 * practice how to build Vaadin applications.
 * 
 * @author jouni@vaadin.com
 * 
 */

package com.vaadin.demo.dashboard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quick.bean.Userprofile;
import com.quick.global.GlobalConstants;
import com.quick.global.GlobalNotifications;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.quick.data.DataProvider;
import com.quick.data.Generator;
import com.quick.data.MyConverterFactory;
import com.quick.ui.exam.StudentExam;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect.AcceptItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Theme("dashboard")
@Title("Titali Dashboard")
public class DashboardUI extends UI{

    DataProvider dataProvider = new DataProvider();

    private static final long serialVersionUID = 1L;

    CssLayout root = new CssLayout();

    VerticalLayout loginLayout;

    CssLayout menu = new CssLayout();
    CssLayout content = new CssLayout();

    HashMap<String, Class<? extends View>> routes = new HashMap<String, Class<? extends View>>() {
        {
            put("/dashboard", DashboardView.class);
            put("/learn", StudQuickLearn.class);
            //put("/transactions", TransactionsView.class);
            //put("/reports", ReportsView.class);
            //put("/schedule", ScheduleView.class);
            put("/student",StudentView.class);
            put("/teacher",TeacherView.class);
            put("/upload",QuickUpload.class);
            put("/MyExam",StudentExam.class);
        }
    };

    HashMap<String, Button> viewNameToMenuButton = new HashMap<String, Button>();

    private Navigator nav;

    private HelpManager helpManager;

    private static final String rootStyleName="root";
    private static final String loginBgStyleName="login-bg";
    @Override
    protected void init(VaadinRequest request) {
        getSession().setConverterFactory(new MyConverterFactory());

        helpManager = new HelpManager(this);

        setLocale(Locale.US);

        setContent(root);
        root.addStyleName(rootStyleName);
        root.setSizeFull();

        // Unfortunate to use an actual widget here, but since CSS generated
        // elements can't be transitioned yet, we must
        Label bg = new Label();
        bg.setSizeUndefined();
        bg.addStyleName(loginBgStyleName);
        root.addComponent(bg);

        buildLoginView(false);

    }
    
    

    private static final String loginStyle="login";
    private static final String loginLayoutStyle="login-layout";
    private static final String loginPanelStyle="login-panel";
    private static final String lablesStyle="labels";
    private static final String lableWelcome="Welcome";
    private static final String h4Style="h4";
    private static final String h2Style="h2";
    private static final String lableTitaliDashboard="Titali Dashboard";
    private static final String lightStyle="light";
    private static final String fieldsStyle="fields";
    private static final String strUsername="Username";
    private static final String strPassword="Password";
    private static final String strSignIn="Sign In";
    private static final String errorStyle="error";
    private static final String strWrongCredentials="Wrong username or password.";
    private static final String vAnimateRevealStyle="v-animate-reveal";
    private static final String defaultStyle="default";
    
     private void buildLoginView(boolean exit) {
        if (exit) {
            root.removeAllComponents();
        }
        helpManager.closeAll();
        HelpOverlay w = helpManager
                .addOverlay(
                        "Welcome to the Titali Dashboard Application",
                        "<p>it only demonstrates an application built with the <a href=\"http://vaadin.com\">Vaadin framework</a>.</p><p>No username or password is required, just click the ‘Sign In’ button to continue. You can try out a random username and password, though.</p>",
                        "login");
        w.center();
        addWindow(w);

        addStyleName(loginStyle);

        loginLayout = new VerticalLayout();
        loginLayout.setSizeFull();
        loginLayout.addStyleName(loginLayoutStyle);
        root.addComponent(loginLayout);

        final CssLayout loginPanel = new CssLayout();
        loginPanel.addStyleName(loginPanelStyle);

        HorizontalLayout labels = new HorizontalLayout();
        labels.setWidth("100%");
        labels.setMargin(true);
        labels.addStyleName(lablesStyle);
        loginPanel.addComponent(labels);

        Label welcome = new Label(lableWelcome);
        welcome.setSizeUndefined();
        welcome.addStyleName(h4Style);
        labels.addComponent(welcome);
        labels.setComponentAlignment(welcome, Alignment.MIDDLE_LEFT);

        Label title = new Label(lableTitaliDashboard);
        title.setSizeUndefined();
        title.addStyleName(h2Style);
        title.addStyleName(lightStyle);
        labels.addComponent(title);
        labels.setComponentAlignment(title, Alignment.MIDDLE_RIGHT);

        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.setMargin(true);
        fields.addStyleName(fieldsStyle);

        final TextField username = new TextField(strUsername);
        username.focus();
        fields.addComponent(username);
        username.setValue("sonali");

        final PasswordField password = new PasswordField(strPassword);
        fields.addComponent(password);
        password.setValue("sonali");

        final Button signin = new Button(strSignIn);
        signin.addStyleName(defaultStyle);
        fields.addComponent(signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        final ShortcutListener enter = new ShortcutListener(strSignIn,
                KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                signin.click();
            }
        };

        signin.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                
                
                if (username.getValue() != null && !(username.getValue().equals(GlobalConstants.emptyString)) && (password.getValue() != null)
                        && !(password.getValue().equals(GlobalConstants.emptyString)) && isValidUser(username.getValue(),password.getValue())) {
                        signin.removeShortcutListener(enter);
                        buildMainView();
                        //getSession().setAttribute(userName,username.getValue());
                }
                else {
                    if (loginPanel.getComponentCount() > 2) {
                        // Remove the previous error message
                        loginPanel.removeComponent(loginPanel.getComponent(2));
                    }
                    // Add new error message
                    Label error = new Label(
                            strWrongCredentials,
                            ContentMode.HTML);
                    error.addStyleName(errorStyle);
                    error.setSizeUndefined();
                    error.addStyleName(lightStyle);
                    // Add animation
                    error.addStyleName(vAnimateRevealStyle);
                    loginPanel.addComponent(error);
                    username.focus();
                }
            }

           
        });

        signin.addShortcutListener(enter);

        loginPanel.addComponent(fields);

        loginLayout.addComponent(loginPanel);
        loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
    }

     
     private static final String brandingStyle="branding";
     private static final String sidebarStyle="sidebar";
     private static final String userStyle="user";

    private void buildMainView() {

        nav = new Navigator(this, content);

        for (String route : routes.keySet()) {
            nav.addView(route, routes.get(route));
        }

        helpManager.closeAll();
        removeStyleName(loginStyle);
        root.removeComponent(loginLayout);

        root.addComponent(new HorizontalLayout() {
            {
                setSizeFull();
                addStyleName("main-view");
                addComponent(new VerticalLayout() {
                    // Sidebar
                    {
                        addStyleName(sidebarStyle);
                        setWidth(null);
                        setHeight("100%");

                        // Branding element
                        addComponent(new CssLayout() {
                            {
                                addStyleName(brandingStyle);
                                Label logo = new Label(
                                        "<span>Titali</span> Dashboard",
                                        ContentMode.HTML);
                                logo.setSizeUndefined();
                                addComponent(logo);
                                // addComponent(new Image(null, new
                                // ThemeResource(
                                // "img/branding.png")));
                            }
                        });

                        // Main menu
                        addComponent(menu);
                        setExpandRatio(menu, 1);

                        // User menu
                        addComponent(new VerticalLayout() {
                            {
                                setSizeUndefined();
                                addStyleName(userStyle);
                                Image profilePic = new Image(
                                        null,
                                        new ThemeResource("img/profile-pic.png"));
                                profilePic.setWidth("34px");
                                addComponent(profilePic);
                                Label userName = new Label();
                                /*Generator
                                        .randomFirstName()
                                        + " "
                                        + Generator.randomLastName() */
                                userName.setSizeUndefined();
                                userName.setValue(loggedinProfile.getName());
                                addComponent(userName);

                                Command cmd = new Command() {
                                    @Override
                                    public void menuSelected(
                                            MenuItem selectedItem) {
                                        Notification
                                                .show("Not implemented in this demo");
                                    }
                                };
                                MenuBar settings = new MenuBar();
                                MenuItem settingsMenu = settings.addItem("",
                                        null);
                                settingsMenu.setStyleName("icon-cog");
                                settingsMenu.addItem("Settings", cmd);
                                settingsMenu.addItem("Preferences", cmd);
                                settingsMenu.addSeparator();
                                settingsMenu.addItem("My Account", cmd);
                                addComponent(settings);

                                Button exit = new NativeButton("Exit");
                                exit.addStyleName("icon-cancel");
                                exit.setDescription("Sign Out");
                                addComponent(exit);
                                exit.addClickListener(new ClickListener() {
                                    @Override
                                    public void buttonClick(ClickEvent event) {
                                        buildLoginView(true);
                                    }
                                });
                            }
                        });
                    }
                });
                // Content
                addComponent(content);
                content.setSizeFull();
                content.addStyleName("view-content");
                setExpandRatio(content, 1);
            }

        });

        menu.removeAllComponents();

        /*for (final String view : new String[] { "dashboard", "learn",
                "transactions", "reports", "schedule","student","teacher","upload","MyExam" }) {*/
        for (final String view : new String[] { "dashboard", "learn",
                "student","teacher","upload","MyExam" }) {
            
            Button b = new NativeButton(view.substring(0, 1).toUpperCase()
                    + view.substring(1).replace('-', ' '));
            b.addStyleName("icon-" + view);
            b.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    clearMenuSelection();
                    event.getButton().addStyleName("selected");
                    if (!nav.getState().equals("/" + view))
                        nav.navigateTo("/" + view);
                }
            });

            if (view.equals("reports")) {
                // Add drop target to reports button
                DragAndDropWrapper reports = new DragAndDropWrapper(b);
                reports.setDragStartMode(DragStartMode.NONE);
                reports.setDropHandler(new DropHandler() {

                    @Override
                    public void drop(DragAndDropEvent event) {
                        clearMenuSelection();
                        viewNameToMenuButton.get("/reports").addStyleName(
                                "selected");
                        autoCreateReport = true;
                        items = event.getTransferable();
                        nav.navigateTo("/reports");
                    }

                    @Override
                    public AcceptCriterion getAcceptCriterion() {
                        return AcceptItem.ALL;
                    }

                });
                menu.addComponent(reports);
                menu.addStyleName("no-vertical-drag-hints");
                menu.addStyleName("no-horizontal-drag-hints");
            } else {
                menu.addComponent(b);
            }

            viewNameToMenuButton.put("/" + view, b);
        }
        menu.addStyleName("menu");
        menu.setHeight("100%");

        viewNameToMenuButton.get("/dashboard").setHtmlContentAllowed(true);
        viewNameToMenuButton.get("/dashboard").setCaption(
                "Dashboard<span class=\"badge\">2</span>");

        String f = Page.getCurrent().getUriFragment();
        if (f != null && f.startsWith("!")) {
            f = f.substring(1);
        }
        if (f == null || f.equals("") || f.equals("/")) {
            nav.navigateTo("/dashboard");
            menu.getComponent(0).addStyleName("selected");
            helpManager.showHelpFor(DashboardView.class);
        } else {
            if(!f.equals(GlobalConstants.emptyString))
            {
            nav.navigateTo(f);
            helpManager.showHelpFor(routes.get(f));
            viewNameToMenuButton.get(f).addStyleName("selected");
            }
        }

        nav.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event) {
                helpManager.closeAll();
                return true;
            }

            @Override
            public void afterViewChange(ViewChangeListener.ViewChangeEvent event) {
                View newView = event.getNewView();
                helpManager.showHelpFor(newView);
                if (autoCreateReport && newView instanceof ReportsView) {
                    ((ReportsView) newView).autoCreate(2, items, transactions);
                }
                autoCreateReport = false;
            }
        });

    }

    private Transferable items;

    private void clearMenuSelection() {
        for (Iterator<Component> it = menu.getComponentIterator(); it.hasNext();) {
            Component next = it.next();
            if (next instanceof NativeButton) {
                next.removeStyleName("selected");
            } else if (next instanceof DragAndDropWrapper) {
                // Wow, this is ugly (even uglier than the rest of the code)
                ((DragAndDropWrapper) next).iterator().next()
                        .removeStyleName("selected");
            }
        }
    }

    void updateReportsButtonBadge(String badgeCount) {
        viewNameToMenuButton.get("/reports").setHtmlContentAllowed(true);
        viewNameToMenuButton.get("/reports").setCaption(
                "Reports<span class=\"badge\">" + badgeCount + "</span>");
    }

    void clearDashboardButtonBadge() {
        viewNameToMenuButton.get("/dashboard").setCaption("Dashboard");
    }

    boolean autoCreateReport = false;
    Table transactions;

    public void openReports(Table t) {
        transactions = t;
        autoCreateReport = true;
        nav.navigateTo("/reports");
        clearMenuSelection();
        viewNameToMenuButton.get("/reports").addStyleName("selected");
    }

    HelpManager getHelpManager() {
        return helpManager;
    }
    
    private static final String userName="userName";
    private static final String strPwd="password";
    private boolean isValidUser(String username, String pwd) {
        
        boolean isvaliduser = false;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(GlobalConstants.getProperty(GlobalConstants.LOGIN_URL));
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            try {
                inputJson.put(userName, username.trim());
                inputJson.put(strPwd, pwd.trim());
            } catch (JSONException ex) {
                ex.printStackTrace();
            }


            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);

            /*
             * if (response.getStatus() != 201) { throw new
             * RuntimeException("Failed : HTTP error code : " +
             * response.getStatus()); }
             */

            String output = response.getEntity(String.class);
            System.out.println("output=" + output);


            JSONObject response1 = null;

            response1 = new JSONObject(output);

            if (response1.get(GlobalConstants.isAuthenticated).equals(GlobalConstants.YES)) {
                isvaliduser = true;
                Type listType = new TypeToken<ArrayList<Userprofile>>() {}.getType();
                List<Userprofile> list =new Gson().fromJson(response1.getString(GlobalConstants.CurrentUserProfile), listType);
                loggedinProfile=list.get(0);
                getSession().setAttribute(GlobalConstants.CurrentUserProfile,loggedinProfile);
                
            } else {
                 Notification.show(GlobalNotifications.INVALID_CREDENTIALS);
            }
        } catch (JSONException ex) {
            Logger.getLogger(DashboardUI.class.getName()).log(Level.SEVERE, null, ex);
          
        }
        
           return isvaliduser;
    }
    
    private Userprofile loggedinProfile;
}