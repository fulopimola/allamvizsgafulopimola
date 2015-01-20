package edu.ubb.ccwp.dao;

import java.sql.SQLException;

import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.UserNameExistsException;
import edu.ubb.ccwp.exception.UserNotFoundException;
import edu.ubb.ccwp.model.User;

public interface UserDAO {
	/**
	 * 
	 * @param userID
	 * @return User with requested ID
	 * @throws DAOException
	 *             in case of database access issues
	 * @throws UserNotFoundException
	 *             in case there is no such user in the database
	 * @throws SQLException 
	 */
	User getUserByUserID(int userID) throws DAOException, UserNotFoundException, SQLException;
	User getUserByEmail(String email) throws DAOException, UserNotFoundException, SQLException;
	User insertUser(User user) throws DAOException, UserNameExistsException, SQLException;
}
