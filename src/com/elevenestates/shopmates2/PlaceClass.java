package com.elevenestates.shopmates2;

public class PlaceClass {

	private String placeName;
	private String placeLat;
	private String placeLong;
	private String placeId;
	
	PlaceClass(String placeName,String placeId)
	{
		this.placeName = placeName;
		this.placeId = placeId;
	}
	
	public String getPlaceName()
	{
		return placeName;
	}
	public String getPlaceId()
	{
		return placeId;
	}
	
	
	
}
