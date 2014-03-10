package edu.ubb.ccwp.dao;

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
	 */
	User getUserByUserID(int userID) throws DAOException, UserNotFoundException;
	User getUserByEmail(String email) throws DAOException, UserNotFoundException;
	User insertUser(User user) throws DAOException, UserNameExistsException;
}
