package edu.ubb.ccwp.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ubb.ccwp.dao.CompanyDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.model.Company;
import edu.ubb.ccwp.model.Product;

public class CompanyJdbcDAO implements CompanyDAO {

	private Company getCompanyFromResult(ResultSet result) throws SQLException{
		Company comp = new Company();
		comp.setCompanyId(result.getInt("CompId"));
		comp.setCompanyName(result.getString("CompanyName"));
		comp.setAddress(result.getString("Address"));
		comp.setLatitude(result.getFloat("Latitude"));
		comp.setLongitude(result.getFloat("Longitude"));
		return comp;
	}
	
	
	@Override
	public ArrayList<Company> getAllCompany() throws SQLException, DAOException {
		// TODO Auto-generated method stub
		ArrayList<Company> comp = new ArrayList<Company>();

		String command = "SELECT * FROM `companys`";
		PreparedStatement statement = JdbcConnection.getConnection()
				.prepareStatement(command);

		ResultSet result = statement.executeQuery();

		while (result.next()) {
			Company com = new Company();
			com = getCompanyFromResult(result);
			comp.add(com);
		}

		return comp;
	}

}
