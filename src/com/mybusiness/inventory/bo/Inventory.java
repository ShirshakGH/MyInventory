package com.mybusiness.inventory.bo;

public class Inventory {

	private String itemName;
	private double boughtPrice;
	private double soldPrice;
	private int quantity;

	public Inventory(String itemName, double boughtPrice, double soldPrice) {
		
		this.itemName = itemName;
		this.boughtPrice = boughtPrice;
		this.soldPrice = soldPrice;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getBoughtPrice() {
		return boughtPrice;
	}

	public void setBoughtPrice(double boughtPrice) {
		this.boughtPrice = boughtPrice;
	}

	public double getSoldPrice() {
		return soldPrice;
	}

	public void setSoldPrice(double soldPrice) {
		this.soldPrice = soldPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return itemName + "\t\t" + boughtPrice + "\t\t" + soldPrice + "\t\t" + quantity + "\t\t"
				+ (boughtPrice * quantity);
	}
}
