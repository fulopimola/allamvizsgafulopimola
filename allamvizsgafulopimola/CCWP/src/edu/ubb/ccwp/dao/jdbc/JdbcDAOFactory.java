package edu.ubb.ccwp.dao.jdbc;

import edu.ubb.ccwp.dao.DAOFactory;
import edu.ubb.ccwp.dao.UserDAO;

public class JdbcDAOFactory extends DAOFactory {

	@Override
	public UserDAO getUserDAO() {
		return new UserJdbcDAO();
	}

}
