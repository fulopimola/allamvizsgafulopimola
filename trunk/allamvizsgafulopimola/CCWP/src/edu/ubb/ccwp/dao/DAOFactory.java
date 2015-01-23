package edu.ubb.ccwp.dao;

import edu.ubb.ccwp.dao.jdbc.JdbcDAOFactory;

public abstract class DAOFactory {
	public static DAOFactory getInstance() {
		return new JdbcDAOFactory();
	}
	
	public abstract UserDAO getUserDAO();
	public abstract ProductDAO getProductDAO();
	public abstract ShopDAO getShopsDAO();
	public abstract CompanyDAO getCompanyDAO();
	public abstract CategoryDAO getCategoryDAO();
}
