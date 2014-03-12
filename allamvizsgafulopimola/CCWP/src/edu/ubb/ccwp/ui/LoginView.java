package edu.ubb.ccwp.ui;

import java.util.Arrays;

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

import edu.ubb.ccwp.dao.DAOFactory;
import edu.ubb.ccwp.dao.UserDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.UserNotFoundException;
import edu.ubb.ccwp.logic.Hash;
import edu.ubb.ccwp.model.User;


@SuppressWarnings("serial")
public class LoginView extends CustomComponent implements View,
Button.ClickListener {

	public static final String NAME = "login";

	private final TextField email;
	private final PasswordField password;
	private final Button loginButton;
	private final Button registration;

	public LoginView() {
		setSizeFull();

		
		// Create the user input field
		User u = new User();
		u.setUserName("Guest");
		BasePageUI base = new BasePageUI(u);
		
		email = new TextField("User:");
		email.setWidth("300px");
		email.setRequired(true);
		email.setInputPrompt("Your username (eg. joe@email.com)");
		email.addValidator(new EmailValidator("Username must be an email address"));
		/*email.addValidator(new StringLengthValidator(
			    "The name must be 1-10 letters (was {0})",
			    1, 10, true));*/
		email.setInvalidAllowed(false);

		// Create the password input field
		password = new PasswordField("Password:");
		password.setWidth("300px");
		password.addValidator(new PasswordValidator());
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");

		// Create login button
		loginButton = new Button("Login", this);
		registration  = new Button("Registration",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	getUI().getNavigator().navigateTo(RegistrationView.NAME);
            }
        });
		
		// Add both to a panel
		VerticalLayout fields = new VerticalLayout(email, password, loginButton, registration);
		fields.setCaption("Please login to access the application. (test@test.com/passw0rd)");
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();

		// The view root layout
		VerticalLayout viewLayout = new VerticalLayout();
		
		viewLayout.setSizeUndefined();
		viewLayout.setWidth("100%");
		viewLayout.addComponent(base);
		viewLayout.addComponent(fields);
		viewLayout.setComponentAlignment(fields, Alignment.TOP_CENTER);
		//viewLayout.setStyleName(Reindeer.LAYOUT_WHITE);
		
		setCompositionRoot(viewLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// focus the username field when user arrives to the login view
		getSession().setAttribute("user", null);
		email.focus();
	}

	//
	// Validator for validating the passwords
	//
	private static final class PasswordValidator extends
	AbstractValidator<String> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
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
					&& (value.length() < 4 || !value.matches(".*\\d.*"))) {
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
		if (!email.isValid() || !password.isValid()) {
			return;
		}

		String email2 = email.getValue();
		String password = this.password.getValue();

		//
		// Validate username and password with database here. For examples sake
		// I use a dummy username and password.
		//
		//boolean isValid = username.equals("test@test.com")
		//		&& password.equals("passw0rd");

		boolean isValid = false;
		
		User u = new User();
		try {
			DAOFactory df = DAOFactory.getInstance();
			UserDAO ud = df.getUserDAO();
			
			u = ud.getUserByEmail(email2);
			
			if(Arrays.equals(Hash.hashString(password), u.getPassword())){
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
			getSession().setAttribute("user", u);

			// Navigate to main view
			getUI().getNavigator().navigateTo(InitPage.NAME);

		} else {

			// Wrong password clear the password field and refocuses it
			this.password.setValue(null);
			this.password.focus();
		}
	}
}