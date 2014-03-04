package eud.ubb.ccwp.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

import edu.ubb.ccwp.model.User;

@SuppressWarnings("serial")
public class BasePageUI  extends VerticalLayout{
	protected BasePageUI me = this;
	protected User user;
	protected Navigator navigator;

	
	protected boolean manager = false;

	public BasePageUI(User u, Navigator navi) {
	
		navigator = navi;
		user = u;

		this.setImmediate(true);
		
		if (manager) {
			initManager();
		} else {
			initUser();
		}
		
		  //menubar
        MenuBar barmenu = new MenuBar();
        barmenu.addStyleName("mybarmenu");
        addComponent(barmenu);
                
        // A feedback component
        final Label selection = new Label("-");
        addComponent(selection);

        // Define a common menu command for all the menu items
        MenuBar.Command mycommand = new MenuBar.Command() {
            MenuItem previous = null;

            public void menuSelected(MenuItem selectedItem) {
                selection.setValue("Ordered a /" +
                        selectedItem.getText() +
                        "/ from menu.");

                
                
                if(selectedItem.getText().equals("InitPage")){              	
                	 navigator.navigateTo("");
                	 System.out.println("init page");
                }
                if(selectedItem.getText().equals("MainView")){              	
               	 navigator.navigateTo("main");
               	 System.out.println("main view");
                }
                if (previous != null)
                    previous.setStyleName(null);
                selectedItem.setStyleName("highlight");
                previous = selectedItem;
            }  
        };
                
        // Put some items in the menu
        barmenu.addItem("InitPage", null, mycommand);
        barmenu.addItem("MainView", null, mycommand);
        barmenu.addItem("Services", null, mycommand);
        
	}
	
	
	protected void initManager() {
		Label label = new Label("Ez az admin init");
		addComponent(label);

	}
	protected void initUser() {
		Label label = new Label("Ëz a user init");
		addComponent(label);

	}
}
