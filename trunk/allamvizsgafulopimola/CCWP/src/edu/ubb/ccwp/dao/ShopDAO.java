package edu.ubb.ccwp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.ShopNotFoundException;
import edu.ubb.ccwp.model.Shop;

public interface ShopDAO {
	
	Shop getShopByShopId(int id) throws SQLException, DAOException, ShopNotFoundException;
	ArrayList<Shop> getShopsByProductId(int id) throws SQLException, DAOException, ShopNotFoundException;
	ArrayList<Shop> getAllShop() throws SQLException, DAOException;
}
