package edu.ubb.ccwp.ui;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import edu.ubb.ccwp.dao.DAOFactory;
import edu.ubb.ccwp.dao.UserDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.UserNotFoundException;
import edu.ubb.ccwp.logic.Hash;
import edu.ubb.ccwp.model.User;


public class LoginView extends CustomComponent implements View,
Button.ClickListener {

	public static final String NAME = "login";

	private final TextField user;
	private final PasswordField password;
	private final Button loginButton;

	public LoginView() {
		setSizeFull();

		// Create the user input field
		User u = new User();
		u.setUserName("Guest");
		BasePageUI base = new BasePageUI(u);
		
		user = new TextField("User:");
		user.setWidth("300px");
		user.setRequired(true);
		user.setInputPrompt("Your username (eg. joe@email.com)");
		user.addValidator(new EmailValidator("Username must be an email address"));
		user.setInvalidAllowed(false);

		// Create the password input field
		password = new PasswordField("Password:");
		password.setWidth("300px");
		password.addValidator(new PasswordValidator());
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");

		// Create login button
		loginButton = new Button("Login", this);

		// Add both to a panel
		VerticalLayout fields = new VerticalLayout(user, password, loginButton);
		fields.setCaption("Please login to access the application. (test@test.com/passw0rd)");
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();

		// The view root layout
		VerticalLayout viewLayout = new VerticalLayout();
		
		viewLayout.setSizeFull();
		viewLayout.addComponent(base);
		viewLayout.addComponent(fields);
		viewLayout.setComponentAlignment(fields, Alignment.TOP_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_WHITE);
		
		setCompositionRoot(viewLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// focus the username field when user arrives to the login view
		user.focus();
	}

	//
	// Validator for validating the passwords
	//
	private static final class PasswordValidator extends
	AbstractValidator<String> {

		public PasswordValidator() {
			super("The password provided is not valid");
		}

		@Override
		protected boolean isValidValue(String value) {
			//
			// Password must be at least 8 characters long and contain at least
			// one number
			//
			if (value != null
					&& (value.length() < 8 || !value.matches(".*\\d.*"))) {
				return false;
			}
			return true;
		}

		@Override
		public Class<String> getType() {
			return String.class;
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {

		//
		// Validate the fields using the navigator. By using validors for the
		// fields we reduce the amount of queries we have to use to the database
		// for wrongly entered passwords
		//
		if (!user.isValid() || !password.isValid()) {
			return;
		}

		String username = user.getValue();
		String password = this.password.getValue();

		//
		// Validate username and password with database here. For examples sake
		// I use a dummy username and password.
		//
		//boolean isValid = username.equals("test@test.com")
		//		&& password.equals("passw0rd");

		boolean isValid = false;
		
		
		try {
			DAOFactory df = DAOFactory.getInstance();
			UserDAO ud = df.getUserDAO();
			User u;
			u = ud.getUserByUserName(username);
			if(u.getPassword().equals(Hash.hashString(password))){
				isValid = true;
			}
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(isValid){
			// Store the current user in the service session
			getSession().setAttribute("user", username);

			// Navigate to main view
			getUI().getNavigator().navigateTo(InitPage.NAME);

		} else {

			// Wrong password clear the password field and refocuses it
			this.password.setValue(null);
			this.password.focus();
		}
	}
}