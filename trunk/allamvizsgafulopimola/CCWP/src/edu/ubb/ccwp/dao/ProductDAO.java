package edu.ubb.ccwp.dao;

import java.util.ArrayList;

import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.ProductNotFoundException;
import edu.ubb.ccwp.model.Product;

public interface ProductDAO {
	
	Product getProductByProductId(int userID) throws DAOException, ProductNotFoundException;
	ArrayList<Product> getAllProduct() throws DAOException;
	ArrayList<Product> getProductSearch(String likeName) throws DAOException;

}
