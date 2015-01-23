package edu.ubb.ccwp.dao.jdbc;

import edu.ubb.ccwp.dao.CategoryDAO;
import edu.ubb.ccwp.dao.CompanyDAO;
import edu.ubb.ccwp.dao.DAOFactory;
import edu.ubb.ccwp.dao.ProductDAO;
import edu.ubb.ccwp.dao.ShopDAO;
import edu.ubb.ccwp.dao.UserDAO;

public class JdbcDAOFactory extends DAOFactory {

	@Override
	public UserDAO getUserDAO() {
		return new UserJdbcDAO();
	}
	

	@Override
	public ProductDAO getProductDAO() {
		// TODO Auto-generated method stub
		return new ProductJdbcDAO();
	}


	@Override
	public ShopDAO getShopsDAO() {
		// TODO Auto-generated method stub
		return new ShopJdbcDAO();
	}


	@Override
	public CompanyDAO getCompanyDAO() {
		// TODO Auto-generated method stub
		return new CompanyJdbcDAO();
	}


	@Override
	public CategoryDAO getCategoryDAO() {
		// TODO Auto-generated method stub
		return new CategoryJdbcDAO();
	}

}
