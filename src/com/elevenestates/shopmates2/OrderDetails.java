package com.elevenestates.shopmates2;

public class OrderDetails {

	public String shopperId;
	public String orderX,orderY;
	public String orderList;
	public String orderId;
	
	public OrderDetails(String orderId,String shopperId, String orderX, String orderY, String orderList)
	{
		this.shopperId = shopperId;
		this.orderX = orderX;
		this.orderY = orderY;
		this.orderList = orderList;
		this.orderId = orderId;
	}
}
