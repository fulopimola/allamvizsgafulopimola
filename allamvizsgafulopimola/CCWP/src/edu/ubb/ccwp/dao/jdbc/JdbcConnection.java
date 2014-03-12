package edu.ubb.ccwp.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import edu.ubb.ccwp.exception.DAOException;

public class JdbcConnection {
	private static Connection connection;

	static {
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "ccwp";
		String userName = "root";
		String password = "root";

		try {
			
			//Class.forName("com.myssl.jdbc.Driver").newInstance();
			com.mysql.jdbc.Driver.class.newInstance();
			connection = DriverManager.getConnection(url + dbName, userName,
					password);
		} catch (Exception e) {
			connection = null;
			System.out.println("nincs szerver");
		}
	}

	public static Connection getConnection() throws DAOException {
		if (connection == null) {
			throw new DAOException();
		}
		return connection;
	}
}
