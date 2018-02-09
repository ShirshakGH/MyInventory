package com.mybusiness.inventory.bo;

public class Order {

	private String orderType;
	private int orderQuantity;
	
	public Order(String orderType, int orderQuantity) {
		this.orderType = orderType;
		this.orderQuantity = orderQuantity;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}	
}
