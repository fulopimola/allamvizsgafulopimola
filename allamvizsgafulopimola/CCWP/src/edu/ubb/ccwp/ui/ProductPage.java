package edu.ubb.ccwp.ui;


import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.dev.jjs.ast.JLabel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import edu.ubb.ccwp.dao.DAOFactory;
import edu.ubb.ccwp.dao.ShopDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.ShopNotFoundException;
import edu.ubb.ccwp.model.Product;
import edu.ubb.ccwp.model.Shop;
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
		//System.out.println(product);
		//System.out.println("Valami "+product.getProductInShops()[1][1]);
		user = (User) getSession().getAttribute("user");
		baseLayout.addComponent(new BasePageUI(user));
		setImmediate(true);


		Panel textPanel = new Panel();
		textPanel.setSizeFull();
		textPanel.setHeight("100%");


		textPanel.setContent(textContent);


		//Show Product data
		Label productName = new Label("<h1>"+product.getProductName()+"</h1>", ContentMode.HTML);
		textContent.addComponent(productName);

		Label productDesc = new Label("<b>Description:<br></b>"+product.getProductDescription(), ContentMode.HTML);
		textContent.addComponent(productDesc);

		try {

			Label shopsLabel = new Label("<b>Shop:</b>", ContentMode.HTML);
			double[][] price = product.getProductInShops();
			//System.out.println("lenght "+price[0][1]);
			for (int i = 1; i<=(int)price[0][1];i++) {
				//System.out.println((int)price[i][1]+" "+(int)price[i][2]+" "+price[i][3]);
				Shop shop = DAOFactory.getInstance().getShopsDAO().getShopByShopId((int)price[i][1]);

				shopsLabel.setValue(shopsLabel.getValue()+",<br>"+ shop.getShopName() + " price = " +price[i][3]);
			}
			textContent.addComponent(shopsLabel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShopNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		hLayout.addComponent(textPanel);
		hLayout.setExpandRatio(textPanel, 1.0f);
	}
}
