package eud.ubb.ccwp.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import edu.ubb.ccwp.model.User;

public class HomePage extends UI {

	Navigator navigator;
	protected static final String MAINVIEW = "main";

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = HomePage.class)
	public static class Servlet extends VaadinServlet {

	}

	
	@Override
	protected void init(VaadinRequest request) {
		// TODO Auto-generated method stub

		getPage().setTitle("Valami");

		navigator = new Navigator(this, this);
		navigator.addView("", new InitPage(navigator));
		navigator.addView(MAINVIEW, new MainView(navigator));

	}


}