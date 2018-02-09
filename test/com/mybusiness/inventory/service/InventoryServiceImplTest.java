package com.mybusiness.inventory.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mybusiness.inventory.bo.Inventory;
import com.mybusiness.inventory.bo.Order;
import com.mybusiness.inventory.service.InventoryServiceImpl;

public class InventoryServiceImplTest {

	private InventoryServiceImpl invService = new InventoryServiceImpl();
	private Inventory inventory = null;

	@Before
	public void setup() {
		inventory = new Inventory("Book01", 100.00, 105.00);
	}

	@Test
	public void testCreateInventory() {
		invService.createInventory(inventory);
		assertEquals(invService.getInventoryMap().size(), 1);
	}

	@Test
	public void testUpdateBuy() {

		invService.createInventory(inventory);

		Inventory inv = invService.getInventoryMap().get("Book01");
		Order order = new Order("Buy", 250);
		invService.update(inv, order);

		assertEquals(inv.getQuantity(), 250);
	}

	@Test
	public void testUpdateSell() {

		invService.createInventory(inventory);

		Inventory inv = invService.getInventoryMap().get("Book01");
		Order order = new Order("Buy", 100);
		invService.update(inv, order);
		
		order = new Order("Sell", 25);
		invService.update(inv, order);

		assertEquals(inv.getQuantity(), 75);
	}

	@Test
	public void testDeleteInventory() {

		invService.createInventory(inventory);

		Inventory inv = invService.getInventoryMap().get("Book01");
		invService.deleteInventory(inv);

		assertEquals(invService.getInventoryMap().size(), 0);
	}
	
	@Test
	public void testGenerateReport() {
		invService.createInventory(inventory);
		
		invService.generateReport();
		
		assertNotNull(invService.getInventoryMap());
	}

}
