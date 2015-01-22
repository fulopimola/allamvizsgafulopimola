package edu.ubb.ccwp.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ubb.ccwp.dao.ShopDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.ShopNotFoundException;
import edu.ubb.ccwp.model.Shop;



public class ShopJdbcDAO implements ShopDAO {

	private Shop getShopFromResult(ResultSet result) throws SQLException {
		Shop shop = new Shop();
		shop.setShopId(result.getInt("ShopId"));
		shop.setShopName(result.getString("Name"));
		shop.setDescription(result.getString("Description"));
		shop.setLatit(result.getFloat("Latitude"));
		shop.setLongit(result.getFloat("Longitude"));

		return shop;
	}

	@Override
	public Shop getShopByShopId(int id) throws SQLException, DAOException, ShopNotFoundException {
		Shop shop = new Shop();

		String command = "SELECT * FROM `shops` WHERE `ShopId` = ?";
		PreparedStatement statement = JdbcConnection.getConnection()
				.prepareStatement(command);
		statement.setInt(1, id);
		//System.out.println(statement.toString());
		ResultSet result = statement.executeQuery();
		if (result.next()) {
			shop = getShopFromResult(result);
		} else {
			throw new ShopNotFoundException();
		}

		return shop;
	}

	@Override
	public ArrayList<Shop> getShopsByProductId(int id) throws SQLException, DAOException, ShopNotFoundException {
		// TODO Auto-generated method stub
		ArrayList<Shop> shops = new  ArrayList<Shop>();
		Shop shop = new Shop();

		String command = "SELECT * FROM `productsinshop` WHERE `Products_ProductId` = ?";
		PreparedStatement statement = JdbcConnection.getConnection()
				.prepareStatement(command);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		int i = 0;
		while (result.next()) {
			i++;
			//System.out.println("van");
			shop = getShopByShopId(result.getInt("Shops_ShopId"));
			shops.add(shop);
		}
		if(i==0){
			throw new ShopNotFoundException();
		}

		return shops;
	}


}
