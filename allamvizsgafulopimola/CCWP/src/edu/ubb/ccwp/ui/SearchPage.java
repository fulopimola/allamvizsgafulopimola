package edu.ubb.ccwp.ui;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractSelect.ItemDescriptionGenerator;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import edu.ubb.ccwp.dao.DAOFactory;
import edu.ubb.ccwp.dao.ProductDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.NotInShopException;
import edu.ubb.ccwp.exception.ProductNotFoundException;
import edu.ubb.ccwp.model.Company;
import edu.ubb.ccwp.model.Product;
import edu.ubb.ccwp.model.Shop;
import edu.ubb.ccwp.model.User;


@SuppressWarnings("serial")
@Theme("styles")
public class SearchPage extends CustomComponent implements View {

	public static final String NAME = "searchPage";
	private VerticalLayout layout;
	private VerticalLayout baseLayout;
	private HorizontalLayout hLayout = new HorizontalLayout();
	private VerticalLayout textContent;
	private User user;
	private Table table;
	private ComboBox shopSelect;
	private ComboBox compSelect;
	private TreeTable catTree;


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
		textContent = new VerticalLayout();
		textContent.setHeight("100%");
		textContent.setWidth("100%");
		textContent.setMargin(true);

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


		textPanel.setContent(textContent);

		searchField();

		final Label label = new Label("Menu helye");
		textContent.addComponent(label);

		table = new Table("All Product!");
		table.setSelectable(true);
		table.setImmediate(true);

		//table.setHeight("600px");
		//table.setWidth("400px");
		loadProduct();

		//select a product in table -> new page
		table.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				try {
					DAOFactory df = DAOFactory.getInstance();
					ProductDAO pd = df.getProductDAO();

					Product prod = pd.getProductByProductId((Integer) table.getItem(table.getValue()).getItemProperty("Id").getValue());

					//System.out.println(prod);
					getSession().setAttribute("product",prod);
					getUI().getNavigator().navigateTo(ProductPage.NAME);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Notification.show("Error");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Notification.show("Error");
				} catch (ProductNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Notification.show("Not found");
				} catch (NotInShopException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		textContent.addComponent(table);

		hLayout.addComponent(textPanel);
		hLayout.setExpandRatio(textPanel, 1.0f);


	}

	private void loadProduct() {
		// TODO Auto-generated method stub
		ArrayList<Product> products = new ArrayList<Product>();
		table.removeAllItems();
		table.addContainerProperty("Id", Integer.class, null);
		table.addContainerProperty("Name", String.class, null);
		table.addContainerProperty("Description",  String.class, null);
		table.addContainerProperty("Rate",  Double.class, null);
		table.addContainerProperty("Price(avg)",  String.class, null);
		table.setColumnWidth("Description", 80);

		try {
			DAOFactory df = DAOFactory.getInstance();
			ProductDAO prod = df.getProductDAO();
			int shopId = -1;
			if(shopSelect.getValue() != null) shopId = ((Shop)shopSelect.getValue()).getShopId();
			int compId = -1;
			if(compSelect.getValue() != null) compId = ((Company)compSelect.getValue()).getCompanyId();
			int catId = -1;
			
			System.out.println();
			if(catTree.getValue() != null){
				ArrayList<Integer> cats = new ArrayList<Integer>();
				cats = getAllProductFromCategory( (Integer)catTree.getValue(), catTree);
				cats.add((Integer)catTree.getValue());
				for (Integer integer : cats) {
					System.out.println("van "+integer);
					products.addAll(prod.getProductSearch(searchText.getValue(), shopId, compId, integer));
				}
			}else{
				products = prod.getProductSearch(searchText.getValue(), shopId, compId, catId);
			}
			
		


		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show("Error");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show("Error");
		} catch (NotInShopException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int i = 0;
		for (Product product : products) {
			//System.out.println(product);
			String price = "";
			if(shopSelect.getValue() != null){
				Shop shop = (Shop) shopSelect.getValue();
				price =String.format("%1$,.2f",getPriceShopId(product, shop.getShopId())) + " lej";
			}else{
				price = String.format("%1$,.2f",getAvgPrice(product))+ " lej"; 
			}
			table.addItem(new Object[] {product.getProductId(), product.getProductName(), product.getProductDescription(),product.getProductRate(), price}, i);
			i++;
			table.setItemDescriptionGenerator(new ItemDescriptionGenerator() {                             
				public String generateDescription(Component source, Object itemId, Object propertyId) {
					if(propertyId == null){
						return "Row description "+ itemId;
					} else if(propertyId == "Description") {
						return table.getItem(itemId).getItemProperty(propertyId).getValue()+"";
					}                                                                       
					return null;
				}
			});
		}
		
	}



	private double getAvgPrice(Product product) {
		// TODO Auto-generated method stub
		double[][] price = product.getProductInShops();
		double all = 0;
		for(int i = 1; i<=price[0][1];i++){
			all+=price[i][3];
		}
		return all/price[0][1];
	}

	private double getPriceShopId(Product product, int shopId) {
		// TODO Auto-generated method stub
		double[][] price = product.getProductInShops();
		for (int i = 1; i<=price[0][1];i++){
			if(price[i][1] == shopId)
				return price[i][3];
		}
		return 0;
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


		shopSelect = new ComboBox("Select a shop");
		try {
			ArrayList<Shop> shops = DAOFactory.getInstance().getShopsDAO().getAllShop();

			for (Shop shop : shops) {

				shopSelect.addItem(shop);
				shopSelect.setItemCaption(shop, shop.getShopName());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		compSelect = new ComboBox("Select a company");

		try {
			ArrayList<Company> companys = DAOFactory.getInstance().getCompanyDAO().getAllCompany();
			for (Company company : companys) {
				compSelect.addItem(company);
				compSelect.setItemCaption(company, company.getCompanyName());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			catTree = DAOFactory.getInstance().getCategoryDAO().getAllCategoryTree();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catTree.setSelectable(true);

		catTree.setHeight("160px");

		searchMenuContent.addComponent(shopSelect);
		searchMenuContent.addComponent(compSelect);
		searchMenuContent.addComponent(catTree);


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

	private ArrayList<Integer> getAllProductFromCategory( int catId, TreeTable cattree){

		ArrayList<Integer> catIds = new ArrayList<Integer>();
		
		if(catId >= 0){
			catIds = getAllSubCategoryId(catIds, cattree, catId);
		}

		return catIds;
	}

	private ArrayList<Integer> getAllSubCategoryId(ArrayList<Integer> catIds, TreeTable cattree, int catId){

		Collection<Integer> cat = (Collection<Integer>) cattree.getChildren(catId);
		if(cat != null){
			for (int object : cat) {
				catIds.add(object);
				getAllSubCategoryId(catIds, cattree, object);
			}
		}
		return catIds;
	}
}
