package edu.ubb.ccwp.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

import edu.ubb.ccwp.model.User;

@SuppressWarnings("serial")
public class BasePageUI  extends VerticalLayout{
	protected BasePageUI me = this;
	protected User user;
	protected boolean manager = false;

	public BasePageUI(User u) {
		user = u;
		if(user == null){
			user = new User();
			user.setUserName("Guest");
		}

		this.setImmediate(true);


		if (manager) {
			initManager();
		} else {
			initUser();
		}

	}


	protected void initManager() {
		Label label = new Label("Ez az admin init");
		addComponent(label);
		initMenuBar();
	}


	protected void initUser() {
		initMenuBar();
	}

	protected void initMenuBar(){
		MenuBar barmenu = new MenuBar();
		barmenu.addStyleName("mybarmenu");
		addComponent(barmenu);
		
		// Define a common menu command for all the menu items
		MenuBar.Command mycommand = new MenuBar.Command() {
			MenuItem previous = null;

			public void menuSelected(MenuItem selectedItem) {


				if(selectedItem.getText().equals("Logout")){

					getSession().setAttribute("user", null);
					getUI().getNavigator().navigateTo(LoginView.NAME);
				}
				if(selectedItem.getText().equals("Login")){              	
					getUI().getNavigator().navigateTo(LoginView.NAME);

				}
				if(selectedItem.getText().equals("Home")){

					getUI().getNavigator().navigateTo(InitPage.NAME);
				}
				if(selectedItem.getText().equals(user.getUserName()) && !user.getUserName().equals("Guest")){              	
					getUI().getNavigator().navigateTo(UserProfilePage.NAME);

				}
				if (previous != null)
					previous.setStyleName(null);
				selectedItem.setStyleName("highlight");
				previous = selectedItem;
			}  
		};

		// Put some items in the menu
		barmenu.addItem(user.getUserName(),  null, mycommand);
		if(user.getUserID() == -1){
			barmenu.addItem("Login", null, mycommand);
		}else{
			barmenu.addItem("Logout",null, mycommand);
		}
		barmenu.addItem("Home", null, mycommand);
		barmenu.setSizeFull();
	}
}
