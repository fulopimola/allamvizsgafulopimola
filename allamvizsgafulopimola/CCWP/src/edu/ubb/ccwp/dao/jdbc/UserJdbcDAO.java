package edu.ubb.ccwp.dao.jdbc;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.ubb.ccwp.dao.UserDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.UserNameExistsException;
import edu.ubb.ccwp.exception.UserNotFoundException;
import edu.ubb.ccwp.model.User;

public class UserJdbcDAO implements UserDAO {

	private User getUserFromResult(ResultSet result) throws SQLException {
		User user = new User();
		user.setUserID(result.getInt("UserID"));
		user.setEmail(result.getString("Email"));
		user.setUserName(result.getString("UserName"));
		user.setPassword(result.getBytes("Password"));
		user.setPhoneNumber(result.getString("PhoneNumber"));
		user.setAddress(result.getString("Address"));
		user.setLatitude(result.getFloat("Latitude"));
		user.setLongitude(result.getFloat("Longitude"));
		return user;
	}

	public User getUserByUserID(int userID) throws DAOException,
	UserNotFoundException, SQLException {
		User user = new User();
		
			String command = "SELECT * FROM `users` WHERE `UserID` = ?";
			PreparedStatement statement = JdbcConnection.getConnection()
					.prepareStatement(command);
			statement.setInt(1, userID);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				user = getUserFromResult(result);
			} else {
				throw new UserNotFoundException();
			}
	
		return user;
	}

	public User getUserByEmail(String email)throws DAOException, UserNotFoundException, SQLException {
		User user = new User();
		
			String command = "SELECT * FROM `users` WHERE `Email` = ?";
			PreparedStatement statement = JdbcConnection.getConnection()
					.prepareStatement(command);
			statement.setString(1, email);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				user = getUserFromResult(result);
			} else {
				throw new UserNotFoundException();
			}
		
		return user;
	}

	@Override
	public User insertUser(User user) throws DAOException, UserNameExistsException, SQLException
	{
		
			String command = "INSERT INTO `users`(`UserName`, `Password`, `PhoneNumber`, `Email`, `Address`, `Latitude`, `Longitude`) VALUES (?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement statement = JdbcConnection.getConnection()
					.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getUserName());
			statement.setBytes(2, user.getPassword());
			statement.setString(3, user.getPhoneNumber());
			statement.setString(4, user.getEmail());
			statement.setString(5, user.getAddress());
			statement.setFloat(6, user.getLatitude());
			statement.setFloat(7, user.getLongitude());
			statement.executeUpdate();
			ResultSet result = statement.getGeneratedKeys();
			result.next();
			user.setUserID(result.getInt(1));
		
		return user;
	}
}