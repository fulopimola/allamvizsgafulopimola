package edu.ubb.ccwp.model;

public class Company {
	private int companyId;
	private String companyName;
	private String address;
	private float latitude = 0;
	private float longitude = 0;
	
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString(){
		return "Company id = "+companyId+" name = "+companyName+" address = "+address +" lat = "+latitude + " long = "+longitude ;
		
	}

}
