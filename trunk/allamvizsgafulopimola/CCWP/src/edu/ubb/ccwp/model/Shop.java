package edu.ubb.ccwp.model;

public class Shop {
	
	private int shopId;
	private String shopName;
	private String shopAddress;
	private String description;
	private float latit = 0;
	private float longit = 0;
	
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getLatit() {
		return latit;
	}
	public void setLatit(float latit) {
		this.latit = latit;
	}
	public float getLongit() {
		return longit;
	}
	public void setLongit(float longit) {
		this.longit = longit;
	}
	
	@Override
	public String toString() {
		return "Shop name = "+shopName+" address = "+shopAddress+" descriptios = "+description+" lat = "+latit+" long= "+longit;
	}

}
