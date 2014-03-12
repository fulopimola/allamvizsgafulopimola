package edu.ubb.ccwp.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import edu.ubb.ccwp.model.User;



@SuppressWarnings("serial")
public class InitPage extends CustomComponent implements View {
	public static final String NAME = "";
	private Label label;
	private VerticalLayout layout;
	private VerticalLayout baseLayout;
	
	
	private  User user;
	
    public InitPage() {
    	layout = new VerticalLayout();
    	layout.setSizeFull();
    	baseLayout = new VerticalLayout();
    	layout.addComponent(baseLayout);
    	layout.addComponent(new Label(" sdfsd sd f"));
 
        setCompositionRoot(layout);
    }        
        
    @Override
    public void enter(ViewChangeEvent event) {
    	System.out.println("belepett az init enterbe");
    	user = (User) getSession().getAttribute("user");
    	
    	baseLayout.addComponent(new BasePageUI(user));
    	
		setImmediate(true);
    	Button button = new Button("Go to Main View",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	getUI().getNavigator().navigateTo(MainView.NAME);
            }
        });
        layout.addComponent(button);
        layout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        
    	
    }
}

