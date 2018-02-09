package com.mybusiness.inventory.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mybusiness.inventory.bo.Inventory;
import com.mybusiness.inventory.bo.Order;
import com.mybusiness.inventory.service.InventoryServiceImpl;

public class InventoryRunner {

	private InventoryServiceImpl inventoryService = new InventoryServiceImpl();
	
	public static void main(String args[]) {

		List<String> invList = loadData();
		InventoryRunner invRunner = new InventoryRunner();
		invRunner.maintainInventory(invList);
	}
	
	/*
	 * Load Data from given flat file.
	 */
	private static List<String> loadData() {

		List<String> inventoryActionList = new ArrayList<String>();
		String fileName = "input.txt";

		try {			
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream inputStream = loader.getResourceAsStream(fileName);
			
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			while (bufferedReader.ready()) {
				inventoryActionList.add(bufferedReader.readLine());
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		return inventoryActionList;
	}
	
	/*
	 * Perform different actions as per the input.
	 */
	public void maintainInventory(List<String> itemsList) {

		String[] invDetails = null;
		String action = null;
		String itemName = null;
		String orderType = null;
		int orderQuantity = 0;
		Inventory inv = null;
		Order order = null;

		for (String item : itemsList) {

			if ("#".equalsIgnoreCase(item)) {
				break;
			}

			if ("report".equalsIgnoreCase(item)) {
				inventoryService.generateReport();
			} else {
				invDetails = item.split(" ");
				action = invDetails[0];
				itemName = invDetails[1];

				if ("create".equalsIgnoreCase(action)) {
					double costPrice = Double.parseDouble(invDetails[2]);
					double sellingPrice = Double.parseDouble(invDetails[3]);

					inv = new Inventory(itemName, costPrice, sellingPrice);
					inventoryService.createInventory(inv);
				} else if (action!=null && action.startsWith("update")) {

					inv = getInventoryMap().get(itemName);
					orderType = action.equalsIgnoreCase("updateBuy") ? "Buy" : "Sell";
					orderQuantity = Integer.parseInt(invDetails[2]);
					order = new Order(orderType, orderQuantity);

					inventoryService.update(inv, order);
				} else if ("delete".equalsIgnoreCase(action)) {
					inv = getInventoryMap().get(itemName);
					inventoryService.deleteInventory(inv);
				}
			}
		}
	}	
	
	public Map<String, Inventory> getInventoryMap(){
		return inventoryService.getInventoryMap();
	}	
}
