package edu.ubb.ccwp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.model.Company;

public interface CompanyDAO {
	ArrayList<Company> getAllCompany() throws SQLException, DAOException;
}
