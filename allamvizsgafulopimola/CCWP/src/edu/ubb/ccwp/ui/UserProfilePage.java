package edu.ubb.ccwp.ui;

import java.util.Arrays;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import edu.ubb.ccwp.model.User;

@SuppressWarnings("serial")
public class UserProfilePage extends CustomComponent implements View {

	public static final String NAME = "userPage";
	
	private User user;
	private VerticalLayout layout;
	private VerticalLayout baseLayout;
	private Panel panel;
	
	private TextField userName;
	private TextField email;
	private PasswordField password;
	private PasswordField passwordAgain;
	private TextField phone;
	private TextField address;
	
	
	public UserProfilePage(){
		layout = new VerticalLayout();
		layout.setImmediate(true);
		panel = new Panel();
		panel.setContent(layout);
		layout.setSizeFull();
		baseLayout = new VerticalLayout();
		layout.addComponent(baseLayout);
		
		setCompositionRoot(panel);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		user = (User) getSession().getAttribute("user");
		baseLayout.addComponent(new BasePageUI(user));
		setImmediate(true);
		
		Label label = new Label("<h2>Welcome to the Profile page!</h2>", ContentMode.HTML);
		layout.addComponent(label);
		layout.setComponentAlignment(label, Alignment.TOP_CENTER);

		userName = new TextField("Name:");
		userName.setValue(user.getUserName());
		layout.addComponent(userName);
		
		email = new TextField("Email:");
		email.setValue(user.getEmail());
		layout.addComponent(email);
		
		address = new TextField("Address:");
		address.setValue(user.getAddress());
		layout.addComponent(address);
		
		phone = new TextField("Phone:");
		phone.setValue(user.getPhoneNumber());
		layout.addComponent(phone);
		
		setRead(true);
		
		final Button change = new Button("Modify");
		final Button save = new Button("Save");
		
		save.setVisible(false);
		
		change.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		        setRead(false);
		        save.setVisible(true);
		        change.setVisible(false);
		    }
		});
		
		save.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		        setRead(true);
		        save.setVisible(false);
		        change.setVisible(true);
		    }
		});
		
		layout.addComponent(change);
		layout.addComponent(save);
	}
	
	private void setRead(boolean value){
		userName.setReadOnly(value);
		email.setReadOnly(value);
		phone.setReadOnly(value);
		address.setReadOnly(value);
	}

}
