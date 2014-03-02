package eud.ubb.ccwp.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import edu.ubb.ccwp.model.User;

public class HomePage extends UI {

	/**
	 * 
	 */
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = HomePage.class)
	public static class Servlet extends VaadinServlet {
	
	}

	@Override
	protected void init(VaadinRequest request) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUserName("guest");
		BasePageUI base = new BasePageUI(user);
		setContent(base);
		
		Label label = new Label("Ez a home page inicializalasa!");
		base.addComponent(label);
	}

	
}