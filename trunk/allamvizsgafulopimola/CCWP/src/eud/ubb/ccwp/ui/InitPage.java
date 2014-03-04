package eud.ubb.ccwp.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import edu.ubb.ccwp.model.User;



public class InitPage extends VerticalLayout implements View {
	public static final String NAME = "";
	
    public InitPage() {
        setSizeFull();
        
        User user = new User();
        user.setUserName("Guest");
        BasePageUI base = new BasePageUI(user);
		this.addComponent(base);
        
        Button button = new Button("Go to Main View",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	getUI().getNavigator().navigateTo(MainView.NAME);
            }
        });
        
        
        addComponent(button);
        setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        
        
        
      
    }        
        
    @Override
    public void enter(ViewChangeEvent event) {
        Notification.show("Welcome to the Animal Farm");
    }
}

