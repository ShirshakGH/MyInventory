package com.mybusiness.inventory.service;

import com.mybusiness.inventory.bo.Inventory;
import com.mybusiness.inventory.bo.Order;

public interface InventoryService {
	
	public void createInventory(Inventory inv);
	public void update(Inventory inv, Order order);
	public void deleteInventory(Inventory inv);
	public void generateReport();
}
