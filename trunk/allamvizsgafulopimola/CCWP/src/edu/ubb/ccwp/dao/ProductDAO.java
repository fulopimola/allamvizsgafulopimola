package edu.ubb.ccwp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.ProductNotFoundException;
import edu.ubb.ccwp.model.Product;

public interface ProductDAO {
	
	Product getProductByProductId(int productID) throws DAOException, ProductNotFoundException, SQLException;
	ArrayList<Product> getAllProduct() throws DAOException, SQLException;
	ArrayList<Product> getProductSearch(String likeName) throws DAOException, SQLException;
	Product getProductByProductname(String str) throws DAOException, SQLException, ProductNotFoundException;

}
