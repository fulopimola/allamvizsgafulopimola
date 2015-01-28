package edu.ubb.ccwp.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ubb.ccwp.dao.ProductDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.NotInShopException;
import edu.ubb.ccwp.exception.ProductNotFoundException;
import edu.ubb.ccwp.exception.RateNotFound;
import edu.ubb.ccwp.exception.ShopNotFoundException;
import edu.ubb.ccwp.model.Product;

public class ProductJdbcDAO implements ProductDAO {

	@Override
	public Product getProductByProductId(int productId) throws DAOException,
	ProductNotFoundException, SQLException, NotInShopException {
		Product product = new Product();

		String command = "SELECT * FROM `Products` WHERE `ProductId` = ?";
		PreparedStatement statement;

		statement = JdbcConnection.getConnection()
				.prepareStatement(command);
		statement.setInt(1, productId);

		ResultSet result = statement.executeQuery();

		if(result.next()){
			product = getProductFromResult(result);
		}else{
			throw new ProductNotFoundException();
		}
		return product;
	}

	@Override
	public ArrayList<Product> getAllProduct() throws DAOException, SQLException, NotInShopException {

		ArrayList<Product> products = new ArrayList<Product>();

		String command = "SELECT * FROM `Products`";
		PreparedStatement statement = JdbcConnection.getConnection()
				.prepareStatement(command);

		ResultSet result = statement.executeQuery();

		while (result.next()) {
			Product product = new Product();
			product = getProductFromResult(result);
			products.add(product);
		}

		return products;
	}

	private Product getProductFromResult(ResultSet result) throws SQLException, DAOException, NotInShopException {
		//add shop list

		Product product = new Product();
		product.setProductId(result.getInt("ProductId"));
		product.setProductName(result.getString("ProductName"));
		product.setProductDescription(result.getString("Description"));
		product.setProductRate(result.getDouble("Rate"));
		product.setCompanyId(result.getInt("Companys_CompId"));
		product.setCategoryId(result.getInt("Categories_CategoryId"));
		product.setProductInShops(getproductInShopPrice(product.getProductId()));

		return product;
	}

	@Override
	public ArrayList<Product> getProductSearch(String likeName, int shopId, int compId, int catId)
			throws DAOException, SQLException, NotInShopException {
		// TODO Auto-generated method stub
		ArrayList<Product> products = new ArrayList<Product>();
		ArrayList<Product> newproducts = new ArrayList<Product>();
		//System.out.println(catId);
		String command = "SELECT * FROM `Products` WHERE `ProductName` LIKE ?";

		if (compId != -1){
			command += "AND `Companys_CompId` = "+compId;
		}

		if (catId != -1){
			command += "AND `Categories_CategoryId` = "+catId;
		}

		PreparedStatement statement = JdbcConnection.getConnection()
				.prepareStatement(command);
		statement.setString(1, "%"+likeName+"%");

		ResultSet result = statement.executeQuery();

		while (result.next()) {
			Product product = new Product();
			product = getProductFromResult(result);
			products.add(product);
		}

		if(shopId>0){
			//System.out.println(shopId);
			for (Product product : products) {
				double[][] shopid = product.getProductInShops();
				for(int i = 1; i<=shopid[0][1];i++){
					if(shopId ==shopid[i][1]){
						newproducts.add(product);
					}
				}
			}
		}
		else{
			newproducts = products;
		}

		return newproducts;
	}

	@Override
	public Product getProductByProductname(String str) throws DAOException, SQLException, ProductNotFoundException, NotInShopException {
		Product product = new Product();

		String command = "SELECT * FROM `Products` WHERE `ProductName` = ?";
		PreparedStatement statement;

		statement = JdbcConnection.getConnection()
				.prepareStatement(command);
		statement.setString(1, str);

		ResultSet result = statement.executeQuery();

		if(result.next()){
			product = getProductFromResult(result);
		}
		else{
			throw new ProductNotFoundException();
		}
		return product;
	}

	public double[][] getproductInShopPrice(int productId) throws SQLException, DAOException, NotInShopException{

		//select the shops from database and the price
		double[][] matrix = new double[255][255];
		String command = "SELECT * FROM `productsinshop` WHERE `Products_ProductId` = ?";
		PreparedStatement statement;

		statement = JdbcConnection.getConnection()
				.prepareStatement(command);
		statement.setInt(1, productId);

		ResultSet res = statement.executeQuery();
		int i=0;
		while(res.next()){
			i++;
			matrix[i][1] = res.getInt("Shops_ShopId");
			matrix[i][2] = res.getInt("Products_ProductId");
			matrix[i][3] = res.getDouble("Price");
			//System.out.println("matrix "+matrix[i][1] + " " + matrix[i][2] + " "+ matrix[i][3]);

		}
		matrix[0][1] = i;
		if (i==0){
			throw new NotInShopException();
		}

		return matrix;

	}

	@Override
	public int getUserProductRate(int userID, int productId) throws SQLException, DAOException, RateNotFound {
		String command = "SELECT `Value` FROM `usersratedproducts` WHERE `users_UserID` = ? AND `Products_ProductId` = ?";
		PreparedStatement statement;

		statement = JdbcConnection.getConnection()
				.prepareStatement(command);
		statement.setInt(1, userID);
		statement.setInt(2, productId);

		ResultSet res = statement.executeQuery();
		if(res.next()){
			return res.getInt("Value");
		}else{
			throw new RateNotFound();
		}

	}

	@Override
	public int updateRate(int userID, int productId, int rate) throws SQLException, DAOException, ProductNotFoundException, NotInShopException {
		String command = "UPDATE `usersratedproducts` SET `Value`= ? WHERE `users_UserID`=? and`Products_ProductId`=?;";
		PreparedStatement statement;
		System.out.println("Update van "+rate);

		statement = JdbcConnection.getConnection()
				.prepareStatement(command);
		statement.setInt(1, rate);
		statement.setInt(2, userID);
		statement.setInt(3, productId);

		int res = statement.executeUpdate();
		statement.close();
		updateProductRate(productId);
		return res;
	}

	@Override
	public int insertRate(int userID, int productId, int rate) throws SQLException, DAOException, ProductNotFoundException, NotInShopException {
		// TODO Auto-generated method stub
		String command = "INSERT INTO `usersratedproducts` (`users_UserID`, `Products_ProductId`, `Value`) VALUES (?, ?, ?);";
		PreparedStatement statement;

		System.out.println("Insert van "+rate);
		statement = JdbcConnection.getConnection()
				.prepareStatement(command);
		statement.setInt(1, userID);
		statement.setInt(2, productId);
		statement.setInt(3, rate);

		int res = statement.executeUpdate();
		statement.close();
		updateProductRate(productId);
		return res;

	}

	private void updateProductRate(int prodId) throws SQLException, DAOException, ProductNotFoundException, NotInShopException{

		String command = "SELECT AVG(`Value`) FROM `usersratedproducts` WHERE `Products_ProductId` = ?";
		PreparedStatement statement;

		statement = JdbcConnection.getConnection()
				.prepareStatement(command);
		statement.setInt(1, prodId);
		ResultSet res = statement.executeQuery();
		double rateavg = 0;
		if(res.next()){
			rateavg = res.getDouble("AVG(`Value`)");
		}

		Product prod = getProductByProductId(prodId);

		prod.setProductRate(rateavg);
		updateProduct(prod);


	}

	public void updateProduct(Product prod) throws SQLException, DAOException {
		// TODO Auto-generated method stub

		String command = "UPDATE `products` SET `ProductName`= ?, `Description`= ?, `Rate` = ?, `Companys_CompId` = ?, `Categories_CategoryId` = ? WHERE `ProductId`=?;";
		PreparedStatement statement;

		statement = JdbcConnection.getConnection()
				.prepareStatement(command);
		statement.setString(1, prod.getProductName());
		statement.setString(2, prod.getProductDescription());
		statement.setDouble(3, prod.getProductRate());
		statement.setInt(4, prod.getCompanyId());
		statement.setInt(5, prod.getCategoryId());
		statement.setInt(6, prod.getProductId());
		int res = statement.executeUpdate();
		statement.close();
		

	}

}
