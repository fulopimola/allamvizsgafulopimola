package edu.ubb.ccwp.ui;

import java.util.Arrays;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import edu.ubb.ccwp.dao.DAOFactory;
import edu.ubb.ccwp.dao.UserDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.UserNameExistsException;
import edu.ubb.ccwp.exception.UserNotFoundException;
import edu.ubb.ccwp.logic.Hash;
import edu.ubb.ccwp.model.User;

@SuppressWarnings("serial")
public class RegistrationView extends CustomComponent implements View, Button.ClickListener {
	public static final String NAME = "registration";

	private final TextField user;
	private final PasswordField password;
	private static PasswordField passwordAgain;
	private final TextField phone;
	private final TextField email;
	private final TextField address;

	private final Button loginButton;


	public RegistrationView() {
		setSizeFull();

		// Create the user input field
		User u = new User();
		u.setUserName("Guest");
		BasePageUI base = new BasePageUI(u);

		user = new TextField("User:");
		user.setWidth("300px");
		user.setRequired(true);
		user.setInputPrompt("The name must be 1-10 letters");
		user.addValidator(new UserNameValidator());

		// Create the password input field
		password = new PasswordField("Password:");
		password.setWidth("300px");
		password.addValidator(new PasswordValidator());
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");


		passwordAgain = new PasswordField("Password again:");
		passwordAgain.setWidth("300px");
		passwordAgain.setRequired(true);
		passwordAgain.setValue("");
		passwordAgain.setNullRepresentation("");

		phone = new TextField("Phone:");
		phone.setWidth("300px");
		phone.setRequired(true);
		phone.setInputPrompt("The phone must be 10 number");
		phone.addValidator(new PhoneValidator());

		email = new TextField("E-mail:");
		email.setWidth("300px");
		email.setRequired(true);
		email.setInputPrompt("The emal must be contain a @ letter");
		email.addValidator(new EmailValidator("The email must be an email address"));

		address = new TextField("address:");
		address.setWidth("300px");
		address.setRequired(true);
		address.setInputPrompt("The address must be 10 letters");
		address.addValidator(new StringLengthValidator(
				"The address must be 1-40 letters (was {0})",
				1, 40, true));

		// Create login button
		loginButton = new Button("Login", this);

		// Add both to a panel
		VerticalLayout fields = new VerticalLayout(user, password, passwordAgain, phone, email, address, loginButton);
		fields.setCaption("Please login to access the application. (test@test.com/passw0rd)");
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();
		fields.setComponentAlignment(user, Alignment.TOP_CENTER);

		// The view root layout
		VerticalLayout viewLayout = new VerticalLayout();

		viewLayout.addComponent(base);
		viewLayout.addComponent(fields);
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		//viewLayout.setStyleName(Reindeer.LAYOUT_WHITE);
		viewLayout.setSizeUndefined();
		viewLayout.setWidth("100%");
		setCompositionRoot(viewLayout);

	}



	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		user.focus();
	}

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
			// Password must be at least 4 characters long and contain at least
			// one number
			//

			String st = passwordAgain.getValue();
			if (value != null && (value.length() < 4 || !value.matches(".*\\d.*") || !st.equals(value)) ) {
				return false;
			}
			return true;
		}

		@Override
		public Class<String> getType() {
			return String.class;
		}
	}

	private static final class UserNameValidator extends
	AbstractValidator<String> {
		public UserNameValidator() {
			super("The username is not valid");
		}

		@Override
		protected boolean isValidValue(String value) {

			try {
				DAOFactory df = DAOFactory.getInstance();
				UserDAO ud = df.getUserDAO();
				ud.getUserByUserName(value);
				return false;
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				System.out.println("The database is stopped");
				return false;

			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				return true;
			}

		}

		@Override
		public Class<String> getType() {
			// TODO Auto-generated method stub
			return String.class;
		}

	}

	private static final class PhoneValidator extends
	AbstractValidator<String> {
		public PhoneValidator() {
			super("The phone number is not valid");
		}

		@Override
		protected boolean isValidValue(String value) {
			if (value != null && (value.length() < 8 || !value.matches("\\d*"))) {
				return false;
			}
			return true;
		}

		@Override
		public Class<String> getType() {
			// TODO Auto-generated method stub
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
		if (!user.isValid() || !password.isValid() || !phone.isValid() || !address.isValid() || !email.isValid()) {
			return;
		}

		String username = user.getValue();
		String password = this.password.getValue();
		String telephone = phone.getValue();
		String email = this.email.getValue();
		String address = this.address.getValue();
		//
		// Validate username and password with database here. For examples sake
		// I use a dummy username and password.
		//
		//boolean isValid = username.equals("test@test.com")
		//		&& password.equals("passw0rd");

		boolean isValid = false;

		try {
			User u = new User();
			u.setUserName(username);
			u.setPassword(Hash.hashString(password));
			u.setPhoneNumber(telephone);
			u.setEmail(email);
			u.setAddress(address);

			DAOFactory df = DAOFactory.getInstance();
			UserDAO ud = df.getUserDAO();
			u = ud.insertUser(u);

			getSession().setAttribute("user", u);
			// Navigate to main view
			getUI().getNavigator().navigateTo(InitPage.NAME);
		} catch (DAOException e) {
			this.password.setValue(null);
			this.password.focus();
		} catch (UserNameExistsException e) {
			// Wrong password clear the password field and refocuses it
			this.password.setValue(null);
			this.password.focus();
		}

	}
}