package edu.ubb.ccwp.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.vaadin.ui.TreeTable;

import edu.ubb.ccwp.dao.CategoryDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.model.Category;

public class CategoryJdbcDAO implements CategoryDAO {

	private Category getCategoryFromResult(ResultSet result) throws SQLException{
		Category categ = new Category();

		categ.setCategoryId(result.getInt("categoryId"));
		categ.setCategoryName(result.getString("CategoryName"));
		categ.setParentId(result.getInt("ParentId"));
		categ.setLevel(result.getInt("Level"));

		return categ;
	}

	@Override
	public ArrayList<Category> getAllCategory() throws SQLException, DAOException {
		// TODO Auto-generated method stub
		ArrayList<Category> cats= new ArrayList<Category>();

		String command = "SELECT * FROM `categories`";
		PreparedStatement statement = JdbcConnection.getConnection()
				.prepareStatement(command);

		ResultSet result = statement.executeQuery();

		while (result.next()) {
			Category cat = new Category();
			cat = getCategoryFromResult(result);
			cats.add(cat);
		}

		return cats;
	}

	@Override
	public TreeTable getAllCategoryTree() throws SQLException, DAOException {
		// TODO Auto-generated method stub

		ArrayList<Category> catTree = getAllCategory();

		TreeTable ttable = new TreeTable("Category");

		ttable.addContainerProperty("Name", String.class, "");
		ttable.setWidth("200px");

		for (Category cat : catTree) {
			ttable.addItem(new Object[]{cat.getCategoryName()},cat.getCategoryId());
			ttable.setParent(cat.getCategoryId(), cat.getParentId());
		}
		
		

		return ttable;
	}




}
