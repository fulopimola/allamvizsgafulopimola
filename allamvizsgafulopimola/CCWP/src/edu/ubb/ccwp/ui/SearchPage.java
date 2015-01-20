package edu.ubb.ccwp.ui;


import java.util.ArrayList;

import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.vaadin.data.Item;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import edu.ubb.ccwp.dao.DAOFactory;
import edu.ubb.ccwp.dao.ProductDAO;
import edu.ubb.ccwp.dao.UserDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.model.Product;
import edu.ubb.ccwp.model.User;


public class SearchPage extends CustomComponent implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3321369975621444076L;
	
	
	public static final String NAME = "searchPage";
	private VerticalLayout layout;
	private VerticalLayout baseLayout;
	private HorizontalLayout hLayout = new HorizontalLayout();
	private VerticalLayout textContent;
	private User user;
	private Table table;
	
	
	private TextField searchText;

	
	public SearchPage() {
		// TODO Auto-generated constructor stub
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
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		user = (User) getSession().getAttribute("user");
		baseLayout.addComponent(new BasePageUI(user));
		setImmediate(true);
		
		
		Panel textPanel = new Panel();
		textPanel.setSizeFull();
		textPanel.setHeight("100%");
		
		textContent = new VerticalLayout();
		textContent.setHeight("100%");
		textContent.setWidth("100%");
		textContent.setMargin(true);
		textPanel.setContent(textContent);
		
		searchField();
		
		Label label = new Label("Menu helye");
		textContent.addComponent(label);
		
		table = new Table("All Product!");
		table.setSelectable(true);
		table.setImmediate(true);
		table.setHeight("600px");
		table.setWidth("400px");
		loadProduct();
		textContent.addComponent(table);
		
		hLayout.addComponent(textPanel);
		hLayout.setExpandRatio(textPanel, 1.0f);
		
		
	}
	
	private void loadProduct() {
		// TODO Auto-generated method stub
		ArrayList<Product> products = new ArrayList<Product>();
		table.removeAllItems();
		table.addContainerProperty("Name", String.class, null);
		table.addContainerProperty("Description",  String.class, null);
		table.addContainerProperty("Rate",  Double.class, null);
		
		try {
			DAOFactory df = DAOFactory.getInstance();
			ProductDAO prod = df.getProductDAO();
			products = prod.getProductSearch(searchText.getValue());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int i = 0;
		for (Product product : products) {
			//System.out.println(product);
			table.addItem(new Object[] {product.getProductName(), product.getProductDescription(),product.getProductRate()}, i);
			i++;
		}
		
		
	
	}

	private void searchField(){
		Panel searchMenuPanel = new Panel();
		searchMenuPanel.setHeight("100%");
		searchMenuPanel.setWidth(null);
		VerticalLayout searchMenuContent = new VerticalLayout();
		
		searchText = new TextField("Search");
		final Label counter = new Label();
		// Display the current length interactively in the counter
		
		

		searchMenuContent.addComponent(searchText);
		searchMenuContent.addComponent(counter);
	
		searchMenuContent.addComponent(new Button("search",
				new ButtonListener("search")));
		searchMenuContent.setWidth(null);
		searchMenuContent.setMargin(true);
		searchMenuPanel.setContent(searchMenuContent);
		hLayout.addComponent(searchMenuPanel);
	}
	class ButtonListener implements ClickListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		
		String menuitem;
		public ButtonListener(String menuitem) {
			this.menuitem = menuitem;
		}

		
		
		
		public void buttonClick(ClickEvent event) {
			// Navigate to a specific state
			loadProduct();
			 
		}
	}
}
