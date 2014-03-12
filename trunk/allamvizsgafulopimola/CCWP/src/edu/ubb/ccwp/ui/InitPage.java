package edu.ubb.ccwp.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.regexp.ReaderCharacterIterator;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import edu.ubb.ccwp.model.User;



@SuppressWarnings("serial")
public class InitPage extends CustomComponent implements View {
	public static final String NAME = "";
	private VerticalLayout layout;
	private VerticalLayout baseLayout;


	private  User user;

	public InitPage() {
		layout = new VerticalLayout();
		layout.setSizeFull();
		baseLayout = new VerticalLayout();
		layout.addComponent(baseLayout);

		HorizontalLayout hLayout = new HorizontalLayout();
		// Have a menu on the left side of the screen
		//Panel menu = new Panel("List of Equals");
		Panel searchPanel = new Panel();
		searchPanel.setHeight("100%");
		searchPanel.setWidth(null);
		VerticalLayout searchContent = new VerticalLayout();
		searchContent.addComponent(new Button("Pig",
				new ButtonListener("pig")));
		searchContent.addComponent(new Button("Cat",
				new ButtonListener("cat")));
		searchContent.addComponent(new Button("Dog",      
				new ButtonListener("dog")));
		searchContent.addComponent(new Button("Reindeer",
				new ButtonListener("reindeer")));
		searchContent.addComponent(new Button("Penguin",
				new ButtonListener("penguin")));
		searchContent.addComponent(new Button("Sheep",
				new ButtonListener("sheep")));
		searchContent.setWidth(null);
		searchContent.setMargin(true);
		searchPanel.setContent(searchContent);
		hLayout.addComponent(searchPanel);


		// A panel that contains a content area on right
		//Panel panel = new Panel("An Equal");
		Panel textPanel = new Panel();
		textPanel.setSizeFull();
		textPanel.setHeight("100%");
		VerticalLayout textContent = new VerticalLayout();
		textContent.addComponent(new Label("Udv a honlapunkon"));
		textContent.addComponent(new Label("Ide kell irni egy udvozlo uzenetet!"));

		ExternalResource res=new ExternalResource("VAADIN/resources/resource.html"); 
		try {
			String basepath = VaadinService.getCurrent()
					.getBaseDirectory().getAbsolutePath();
			File file = new File(basepath + "/VAADIN/resources/resource.html");
			FileReader reader = new FileReader(file);
			BufferedReader bf = new BufferedReader(reader);
			String line = "";

			while(bf.ready()){
				line += bf.readLine();

			}


			Label label = new Label(line,ContentMode.HTML);

			textContent.addComponent(label);


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		textContent.setHeight("100%");
		textContent.setWidth("100%");
		textContent.setMargin(true);
		textPanel.setContent(textContent);

		hLayout.setSizeFull();
		hLayout.setHeight("100%");
		hLayout.setWidth("100%");
		hLayout.addComponent(textPanel);
		hLayout.setExpandRatio(textPanel, 1.0f);

		layout.addComponent(hLayout);
		layout.setExpandRatio(hLayout, 1.0f);
		setCompositionRoot(layout);
	}        

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("belepett az init enterbe");
		user = (User) getSession().getAttribute("user");

		baseLayout.addComponent(new BasePageUI(user));

		setImmediate(true);
		Button button = new Button("Go to Main View",
				new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainView.NAME);
			}
		});
		//Label watching = new Label(
		//        "You are currently watching a " +
		//        event.getParameters());
		//layout.addComponent(watching);

		Label footer = new Label("ide jar a lablec");

		layout.addComponent(footer);
		layout.addComponent(button);
		layout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);


	}

	class ButtonListener implements ClickListener {

		String menuitem;
		public ButtonListener(String menuitem) {
			this.menuitem = menuitem;
		}

		public void buttonClick(ClickEvent event) {
			// Navigate to a specific state
			getUI().getNavigator().navigateTo(InitPage.NAME+"/" + menuitem);
		}
	}
}
