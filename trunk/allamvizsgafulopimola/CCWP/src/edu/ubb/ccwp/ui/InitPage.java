package edu.ubb.ccwp.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
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
	private HorizontalLayout hLayout = new HorizontalLayout();



	public InitPage() {
		layout = new VerticalLayout();
		layout.setSizeFull();
		baseLayout = new VerticalLayout();
		layout.addComponent(baseLayout);
		hLayout.setSizeFull();
		hLayout.setHeight("100%");
		hLayout.setWidth("100%");

		layout.addComponent(hLayout);
		layout.setExpandRatio(hLayout, 1.0f);
		setCompositionRoot(layout);
		
		menuLoad();
		
	}        

	@Override
	public void enter(ViewChangeEvent event) {
		user = (User) getSession().getAttribute("user");

		baseLayout.addComponent(new BasePageUI(user));

		setImmediate(true);

		Label footer = new Label("ide jar a lablec");

		layout.addComponent(footer);

		Panel textPanel = new Panel();
		textPanel.setSizeFull();
		textPanel.setHeight("100%");
		VerticalLayout textContent = new VerticalLayout();

		textContent.setHeight("100%");
		textContent.setWidth("100%");
		textContent.setMargin(true);
		textPanel.setContent(textContent);
		try {
			String basepath = VaadinService.getCurrent()
					.getBaseDirectory().getAbsolutePath();
			File file = new File(basepath + "/VAADIN/resources/Home.html");
			FileReader reader = new FileReader(file);
			BufferedReader bf = new BufferedReader(reader);
			String line = "";

			while(bf.ready()){
				line += bf.readLine();

			}


			Label label = new Label(line,ContentMode.HTML);

			textContent.addComponent(label);

			bf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		hLayout.addComponent(textPanel);
		hLayout.setExpandRatio(textPanel, 1.0f);

		VerticalLayout panelContent = new VerticalLayout();
		panelContent.setSizeFull();
		panelContent.setMargin(true);
		layout.addComponent(panelContent); // Also clears


		if (event.getParameters() == null
				|| event.getParameters().isEmpty()) {
			panelContent.addComponent(
					new Label("Nothing to see here, " +
							"just pass along."));

		}else{

			// Display the fragment parameters
			String parameter = event.getParameters();
			Label watching = new Label(
					"You are currently watching a " +
							parameter);
			watching.setSizeUndefined();
			panelContent.addComponent(watching);
			panelContent.setComponentAlignment(watching,
					Alignment.MIDDLE_CENTER);

			Label back = new Label("And the " +
					parameter + " is watching you");
			back.setSizeUndefined();
			panelContent.addComponent(back);
			panelContent.setComponentAlignment(back,
					Alignment.MIDDLE_CENTER);

			//System.out.println(event.getParameters());
			if (parameter.equals("search")){
				searchLoad();
			}
			
		}


	}

	class ButtonListener implements ClickListener {

		String menuitem;
		public ButtonListener(String menuitem) {
			this.menuitem = menuitem;
		}

		public void buttonClick(ClickEvent event) {
			// Navigate to a specific state
			if( menuitem.equals("search")){
				getUI().getNavigator().navigateTo(InitPage.NAME+"/" + menuitem);
			}else{
				getUI().getNavigator().navigateTo(InitPage.NAME+"/" + menuitem);
			}
		}
	}


	private void menuLoad(){

		Panel verticalMenuPanel = new Panel();
		verticalMenuPanel.setHeight("100%");
		verticalMenuPanel.setWidth(null);
		VerticalLayout verticalMenuContent = new VerticalLayout();
		verticalMenuContent.addComponent(new Button("Erre lehet",
				new ButtonListener("pig")));
		verticalMenuContent.addComponent(new Button("Hogy nem lesz",
				new ButtonListener("cat")));
		verticalMenuContent.addComponent(new Button("Szukseg",      
				new ButtonListener("dog")));
		verticalMenuContent.addComponent(new Button("Reindeer",
				new ButtonListener("reindeer")));
		verticalMenuContent.addComponent(new Button("Penguin",
				new ButtonListener("penguin")));
		verticalMenuContent.addComponent(new Button("search",
				new ButtonListener("search")));
		verticalMenuContent.setWidth(null);
		verticalMenuContent.setMargin(true);
		verticalMenuPanel.setContent(verticalMenuContent);
		hLayout.addComponent(verticalMenuPanel);


	}

	private void searchLoad(){

		Panel searchMenuPanel = new Panel();
		searchMenuPanel.setHeight("100%");
		searchMenuPanel.setWidth(null);
		VerticalLayout searchMenuContent = new VerticalLayout();
		searchMenuContent.addComponent(new Button("search",
				new ButtonListener("search")));
		searchMenuContent.setWidth(null);
		searchMenuContent.setMargin(true);
		searchMenuPanel.setContent(searchMenuContent);
		hLayout.addComponent(searchMenuPanel);
	}
}

