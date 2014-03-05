package edu.ubb.ccwp.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import edu.ubb.ccwp.model.User;

public class MainView extends CustomComponent implements View {
    Panel panel;
    public static final String NAME = "main";
    private VerticalLayout layout;
    
    // Menu navigation button listener
    class ButtonListener implements ClickListener {

        String menuitem;
        public ButtonListener(String menuitem) {
            this.menuitem = menuitem;
        }

        public void buttonClick(ClickEvent event) {
            // Navigate to a specific state
        	getUI().getNavigator().navigateTo("main" + "/" + menuitem);
        }
    }

    public MainView() {
    	layout = new VerticalLayout();
    	
        setSizeFull();
        User user = new User();
        user.setUserName("guest");
        BasePageUI base = new BasePageUI(user);
		layout.addComponent(base);
        // Layout with menu on left and view area on right
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setSizeFull();

        // Have a menu on the left side of the screen
        Panel menu = new Panel("List of Equals");
        menu.setHeight("100%");
        menu.setWidth(null);
        VerticalLayout menuContent = new VerticalLayout();
        menuContent.addComponent(new Button("Pig",
                  new ButtonListener("pig")));
        menuContent.addComponent(new Button("Cat",
                  new ButtonListener("cat")));
        menuContent.addComponent(new Button("Dog",      
                  new ButtonListener("dog")));
        menuContent.addComponent(new Button("Reindeer",
                  new ButtonListener("reindeer")));
        menuContent.addComponent(new Button("Penguin",
                  new ButtonListener("penguin")));
        menuContent.addComponent(new Button("Sheep",
                  new ButtonListener("sheep")));
        menuContent.setWidth(null);
        menuContent.setMargin(true);
        menu.setContent(menuContent);
        hLayout.addComponent(menu);

        // A panel that contains a content area on right
        panel = new Panel("An Equal");
        panel.setSizeFull();
        hLayout.addComponent(panel);
        hLayout.setExpandRatio(panel, 1.0f);

        layout.addComponent(hLayout);
        layout.setExpandRatio(hLayout, 1.0f);
        
        // Allow going back to the start
        Button logout = new Button("Logout",
                   new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	getUI().getNavigator().navigateTo(InitPage.NAME);
            }
        });
        layout.addComponent(logout);
        setCompositionRoot(layout);
    }        
    
    @Override
    public void enter(ViewChangeEvent event) {
        VerticalLayout panelContent = new VerticalLayout();
        User u = (User) getSession().getAttribute("userr");
    	
        System.out.println(u.getUserName()+u.getAddress());
        panelContent.setSizeFull();
        panelContent.setMargin(true);
        panel.setContent(panelContent); // Also clears

        if (event.getParameters() == null
            || event.getParameters().isEmpty()) {
            panelContent.addComponent(
                new Label("Nothing to see here, " +
                          "just pass along."));
            return;
        }

        // Display the fragment parameters
        Label watching = new Label(
            "You are currently watching a " +
            event.getParameters());
        watching.setSizeUndefined();
        panelContent.addComponent(watching);
        panelContent.setComponentAlignment(watching,
            Alignment.MIDDLE_CENTER);
        
        // Some other content
        Embedded pic = new Embedded(null,
            new ThemeResource("img/" + event.getParameters() +
                              "-128px.png"));
        panelContent.addComponent(pic);
        panelContent.setExpandRatio(pic, 1.0f);
        panelContent.setComponentAlignment(pic,
                Alignment.MIDDLE_CENTER);

        Label back = new Label("And the " +
            event.getParameters() + " is watching you");
        back.setSizeUndefined();
        panelContent.addComponent(back);
        panelContent.setComponentAlignment(back,
            Alignment.MIDDLE_CENTER);
    }
}