package edu.ubb.ccwp.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import edu.ubb.ccwp.model.Product;
import edu.ubb.ccwp.model.User;

@SuppressWarnings("serial")
public class ProductPage extends CustomComponent implements View{

	public static final String NAME = "productPage";
	private Product product = new Product();
	private VerticalLayout layout;
	private VerticalLayout baseLayout;
	private HorizontalLayout hLayout = new HorizontalLayout();
	private VerticalLayout textContent;
	private User user;
	
	public ProductPage(){
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
		
		textContent = new VerticalLayout();
		textContent.setHeight("100%");
		textContent.setWidth("100%");
		textContent.setMargin(true);
	}

	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		product = (Product) getSession().getAttribute("product");
		System.out.println(product);
		user = (User) getSession().getAttribute("user");
		baseLayout.addComponent(new BasePageUI(user));
		setImmediate(true);


		Panel textPanel = new Panel();
		textPanel.setSizeFull();
		textPanel.setHeight("100%");

		
		textPanel.setContent(textContent);

		Label label = new Label("Menu helye");
		textContent.addComponent(label);
		
		hLayout.addComponent(textPanel);
		hLayout.setExpandRatio(textPanel, 1.0f);
	}
}
