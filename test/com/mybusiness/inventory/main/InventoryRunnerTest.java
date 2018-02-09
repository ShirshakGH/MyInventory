package com.mybusiness.inventory.main;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class InventoryRunnerTest {

	
	private List<String> itemsList = new ArrayList<String>();
	private InventoryRunner invRunner = new InventoryRunner();
		
	@Before
	public void setup(){
		itemsList.add("create Book01 10.50 13.79");
		itemsList.add("updateBuy Book01 100");
		itemsList.add("updateSell Book01 2");
		itemsList.add(" Book01 2");
		itemsList.add("report");
		itemsList.add("delete Book01");
		itemsList.add("#");			
	}
	
	@Test
	public void testMaintainInventory() {		
		invRunner.maintainInventory(itemsList);		
		
		assertEquals(invRunner.getInventoryMap().size(), 0); 
	}
	
	@Test
	public void testMaintainInventoryEmptyList() {		
				
		itemsList = new ArrayList<String>();
		invRunner.maintainInventory(itemsList);	
		
		assertEquals(invRunner.getInventoryMap().size(), 0);  
	}
}
