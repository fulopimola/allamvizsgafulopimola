package edu.ubb.ccwp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.vaadin.ui.Tree;
import com.vaadin.ui.TreeTable;

import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.model.Category;

public interface CategoryDAO {
	
	ArrayList<Category> getAllCategory() throws SQLException, DAOException;
	TreeTable getAllCategoryTree() throws SQLException, DAOException;
}
