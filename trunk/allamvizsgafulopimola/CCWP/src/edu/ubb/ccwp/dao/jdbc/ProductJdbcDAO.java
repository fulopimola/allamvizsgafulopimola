package edu.ubb.ccwp.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ubb.ccwp.dao.ProductDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.ProductNotFoundException;
import edu.ubb.ccwp.model.Product;

public class ProductJdbcDAO implements ProductDAO {

	@Override
	public Product getProductByProductId(int userID) throws DAOException,
	ProductNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Product> getAllProduct() throws DAOException {

		ArrayList<Product> products = new ArrayList<Product>();

		try {
			String command = "SELECT * FROM `Products`";
			PreparedStatement statement = JdbcConnection.getConnection()
					.prepareStatement(command);
			
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				Product product = new Product();
				product = getProductFromResult(result);
				products.add(product);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

	private Product getProductFromResult(ResultSet result) throws SQLException {
		Product product = new Product();
		product.setProductId(result.getInt("ProductId"));
		product.setProductName(result.getString("ProductName"));
		product.setProductDescription(result.getString("Description"));
		product.setProductRate(result.getDouble("Rate"));
		product.setCompanyId(result.getInt("Companys_CompId"));
		product.setCategoryId(result.getInt("Categories_CategoryId"));
		return product;
	}

	@Override
	public ArrayList<Product> getProductSearch(String likeName)
			throws DAOException {
		// TODO Auto-generated method stub
		ArrayList<Product> products = new ArrayList<Product>();

		try {
			String command = "SELECT * FROM `Products` WHERE `ProductName` LIKE ?";
			PreparedStatement statement = JdbcConnection.getConnection()
					.prepareStatement(command);
			statement.setString(1, "%"+likeName+"%");
			
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				Product product = new Product();
				product = getProductFromResult(result);
				products.add(product);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

}
