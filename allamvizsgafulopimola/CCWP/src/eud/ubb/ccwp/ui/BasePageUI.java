package eud.ubb.ccwp.ui;

import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

import edu.ubb.ccwp.model.User;

public class BasePageUI  extends VerticalLayout{
	protected BasePageUI me = this;
	protected User user;

	private final MenuBar menuB = new MenuBar();
	private MenuItem account = menuB.addItem("Account", null);
	private MenuItem project = menuB.addItem("Project", null);
	private MenuItem request = menuB.addItem("Request", null);

	protected boolean manager = false;

	public BasePageUI(User u) {
		user = u;

		this.setImmediate(true);
		this.addComponent(menuB);
		menuB.setWidth("100%");
		account.setText(user.getUserName());

		if (manager) {
			initManager();
		} else {
			initUser();
		}

	}
	
	
	protected void initManager() {
		Label label = new Label("Ëz az admin init");
		addComponent(label);

	}
	protected void initUser() {
		Label label = new Label("Ëz a user init");
		addComponent(label);

	}
}
