package edu.ubb.ccwp.ui;


import java.sql.SQLException;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;

import edu.ubb.ccwp.dao.DAOFactory;
import edu.ubb.ccwp.dao.ProductDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.NotInShopException;
import edu.ubb.ccwp.exception.ProductNotFoundException;
import edu.ubb.ccwp.exception.RateNotFound;
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
	private Slider slider;
	private Button saveRate;
	private int rate = -1;

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
		GridLayout glay = new GridLayout(2,2);
		Label productName = new Label("<h1>"+product.getProductName()+"</h1>", ContentMode.HTML);
		glay.addComponent(productName,0,0);
		glay.setSizeFull();

		saveRate = new Button("Rate", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println(slider.getValue());
				
				if(rate > 0){
					try {
						rate = slider.getValue().intValue();

						ProductDAO pd = DAOFactory.getInstance().getProductDAO();
						pd.updateRate(user.getUserID(), product.getProductId(), rate);
						getUI().getNavigator().navigateTo(ProductPage.NAME);

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ProductNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NotInShopException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					rate = slider.getValue().intValue();

					ProductDAO pd = DAOFactory.getInstance().getProductDAO();
					try {
						pd.insertRate(user.getUserID(), product.getProductId(), rate);
						getUI().getNavigator().navigateTo(ProductPage.NAME);

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ProductNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NotInShopException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		if(user != null){
			if(!user.getUserName().equals("Guest")){
				ProductDAO pd = DAOFactory.getInstance().getProductDAO();


				VerticalLayout hlay = new VerticalLayout();
				try {
					rate = pd.getUserProductRate(user.getUserID(), product.getProductId());
					System.out.println(rate + " az uj rate");
					slider = new Slider(1,5);
					slider.setWidth("70px");

					Label label = new Label("Your rate is: "+rate);
					System.out.println("Your rate is "+rate);

					if(rate>0) slider.setValue((double) rate);
					hlay.addComponent(label);
					hlay.addComponent(slider);
					hlay.addComponent(saveRate);
					hlay.setWidth("150px");
					glay.addComponent(hlay,1,1);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RateNotFound e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					//nincs meg ertekelve
					System.out.println("a catch-be vagyok "+rate);
					Label label = new Label("Rate this product:");
					slider = new Slider(1,5);
					slider.setWidth("70px");
					hlay.addComponent(label);
					hlay.addComponent(slider);
					hlay.addComponent(saveRate);
					hlay.setWidth("150px");
					glay.addComponent(hlay,1,1);

				}

			}
		}



		textContent.addComponent(glay);

		Label productDesc = new Label("<b>Description:<br></b>"+product.getProductDescription(), ContentMode.HTML);
		textContent.addComponent(productDesc);

		try {

			Label shopsLabel = new Label("<b>Shop:</b>", ContentMode.HTML);
			double[][] price = product.getProductInShops();
			//System.out.println("lenght "+price[0][1]);
			for (int i = 1; i<=(int)price[0][1];i++) {
				//System.out.println((int)price[i][1]+" "+(int)price[i][2]+" "+price[i][3]);
				Shop shop = DAOFactory.getInstance().getShopsDAO().getShopByShopId((int)price[i][1]);

				shopsLabel.setValue(shopsLabel.getValue()+"<br>"+ shop.getShopName() + " price = " +price[i][3]+" lej");
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
