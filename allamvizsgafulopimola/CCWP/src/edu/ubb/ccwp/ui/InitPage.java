package edu.ubb.ccwp.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import edu.ubb.ccwp.dao.DAOFactory;
import edu.ubb.ccwp.dao.UserDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.UserNameExistsException;
import edu.ubb.ccwp.logic.Hash;
import edu.ubb.ccwp.model.User;



public class InitPage extends CustomComponent implements View {
	public static final String NAME = "";
	private Label label;
	private VerticalLayout layout;
	
    public InitPage() {
    	
    	layout = new VerticalLayout();
    	
    	
    	layout.setSizeFull();
        
        User user = new User();
        user.setUserName("Guest");
        BasePageUI base = new BasePageUI(user);
		layout.addComponent(base);
		
		/*
		//insert a new User
		User u = new User();
		u.setUserName("user");
		u.setPassword(Hash.hashString("user1"));
		u.setAddress("Kolozsvar");
		u.setEmail("user@user.com");
		u.setPhoneNumber("0755555555");
		
		DAOFactory df = DAOFactory.getInstance();
		UserDAO ud = df.getUserDAO();
		try {
			ud.insertUser(u);
			System.out.println(u.getUserID()+" az uj id-je");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNameExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("A username mar letezik");
		}*/
		
		
        Button button = new Button("Go to Main View",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	getUI().getNavigator().navigateTo(MainView.NAME);
            }
        });
        
        
        layout.addComponent(button);
        layout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        
        
        setCompositionRoot(layout);
      
    }        
        
    @Override
    public void enter(ViewChangeEvent event) {
        Notification.show("Welcome to the Animal Farm");
    }
}

