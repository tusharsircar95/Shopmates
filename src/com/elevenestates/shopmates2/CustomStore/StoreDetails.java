package com.elevenestates.shopmates2.CustomStore;

public class StoreDetails {

	private String tag;
	private String address;
	private String pincode;
	String lat,lng;
	
	public StoreDetails(String tag, String address, String pincode,String lat,String lng)
	{
		this.tag = tag;
		this.address = address;
		this.pincode = pincode;
		this.lat = lat;
		this.lng = lng;
	}
	
	public String getTag()
	{
		return this.tag;
	}
	public String getAddress()
	{
		return this.address;
	}
	public String getPincode()
	{
		return this.pincode;
	}
	public String getLat()
	{
		return this.lat;
	}
	public String getLng()
	{
		return this.lng;
	}
	public void setTag(String tag)
	{
		this.tag = tag;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public void setPincode(String pincode)
	{
		this.pincode = pincode;
	}
	public void setLat(String lat)
	{
		this.lat = lat;
	}
	public void setLng(String lng)
	{
		this.lng = lng;
	}
	
}
