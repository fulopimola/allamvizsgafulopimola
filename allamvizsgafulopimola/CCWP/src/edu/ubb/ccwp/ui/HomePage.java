package edu.ubb.ccwp.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;


@SuppressWarnings("serial")
public class HomePage extends UI {

	
	Navigator navigator;
	protected static final String NAME = "init";

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = HomePage.class)
	
	public static class Servlet extends VaadinServlet {

	}

	
	@Override
	protected void init(VaadinRequest request) {
		// TODO Auto-generated method stub
		
		getPage().setTitle("Valami");
		
		navigator = new Navigator(this, this);
		navigator.addView(InitPage.NAME, InitPage.class);
		navigator.addView(MainView.NAME, MainView.class);
		navigator.addView(LoginView.NAME, LoginView.class);
		navigator.addView(RegistrationView.NAME, RegistrationView.class);
	}


}