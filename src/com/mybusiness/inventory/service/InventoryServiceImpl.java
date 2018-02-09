package com.mybusiness.inventory.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.mybusiness.inventory.bo.Inventory;
import com.mybusiness.inventory.bo.Order;

public class InventoryServiceImpl implements InventoryService {

	private Map<String, Inventory> inventoryMap = new TreeMap<String, Inventory>();
	private BigDecimal currentProfit = BigDecimal.ZERO;
	private BigDecimal prevProfit = BigDecimal.ZERO;

	/*
	 * @see com.mybusiness.inventory.service.InventoryService#createInventory(com.mybusiness.inventory.bo.Inventory)
	 * @param inv Inventory Object
	 */
	@Override
	public void createInventory(Inventory inv) {
		inventoryMap.put(inv.getItemName(), inv);
	}

	/*
	 * @see com.mybusiness.inventory.service.InventoryService#update(com.mybusiness.inventory.bo.Inventory, com.mybusiness.inventory.bo.Order)
	 * Updates inventory for buy or sell.
	 * @param inv Inventory Object
	 * @param order Order object
	 */
	@Override
	public void update(Inventory inv, Order order) {

		if ("Buy".equalsIgnoreCase(order.getOrderType())) {
			inv.setQuantity(inv.getQuantity() + order.getOrderQuantity());
		} else if ("Sell".equalsIgnoreCase(order.getOrderType())) {
			inv.setQuantity(inv.getQuantity() - order.getOrderQuantity());

			// Increment profit for sold items
			currentProfit = currentProfit.add(new BigDecimal((inv.getSoldPrice() - inv.getBoughtPrice()) * order.getOrderQuantity()));
		}
		else{
			throw new RuntimeException("Unable to update Inventory. Invalid transaction type: "+order.getOrderType());
		}
	}

	/*
	 * @see com.mybusiness.inventory.service.InventoryService#deleteInventory(com.mybusiness.inventory.bo.Inventory)
	 * Delete inventory of the given item.
	 * @param inv Inventory Object
	 */
	@Override
	public void deleteInventory(Inventory inv) {
		
		if(inventoryMap.containsKey(inv.getItemName())){
			
			inventoryMap.remove(inv.getItemName());
	
			// Decrement profit for deleted items
			currentProfit = currentProfit.subtract(new BigDecimal(inv.getBoughtPrice() * inv.getQuantity()));
		}
		else{
			throw new RuntimeException("Unable to delete from Inventory. Item not found.");
		}
	}

	/*
	 * @see com.mybusiness.inventory.service.InventoryService#generateReport()
	 * Generates on-demand report for current inventory.
	 */
	@Override
	public void generateReport() {

		List<Inventory> invList = new ArrayList<Inventory>(inventoryMap.values());

		System.out.println("\nINVENTORY REPORT");
		System.out.println("Item Name\tBought At\tSold At\t\tAvailableQty\tValue");
		System.out.println("-- -- -- -- \t-- -- -- \t-- -- --\t -- -- --\t-- --");

		BigDecimal totalValue = BigDecimal.ZERO;
		for (Inventory inv : invList) {
			System.out.println(inv);
			totalValue = totalValue.add(new BigDecimal(inv.getBoughtPrice() * inv.getQuantity()));
		}

		BigDecimal netProfit = currentProfit.subtract(prevProfit);

		System.out.println("-----------------------------------------------------------------------");
		
		//DecimalFormat df2 = new DecimalFormat(".##");
		//System.out.println("Total Value\t\t\t\t\t\t\t" + df2.format(totalValue));
		//System.out.println("Profit since previous report\t\t\t\t\t" + df2.format(netProfit));
		
		System.out.println("Total Value\t\t\t\t\t\t\t" + totalValue.setScale(2, BigDecimal.ROUND_HALF_UP));
		System.out.println("Profit since previous report\t\t\t\t\t" + netProfit.setScale(2, BigDecimal.ROUND_HALF_UP));

		prevProfit = netProfit;
	}

	public Map<String, Inventory> getInventoryMap() {
		return inventoryMap;
	}	
}
