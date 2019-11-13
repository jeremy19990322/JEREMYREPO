package com.b07.inventory;

import java.util.HashMap;

public class InventoryImpl implements Inventory {
	
	HashMap<Item, Integer> itemMap;
	
	public InventoryImpl() {
		itemMap = new HashMap<Item, Integer>();
	}

	@Override
	public HashMap<Item, Integer> getItemMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setItemMap(HashMap<Item, Integer> itemMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMap(Item item, Integer value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTotalItems() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTotalItems(int total) {
		// TODO Auto-generated method stub
		
	}

}
