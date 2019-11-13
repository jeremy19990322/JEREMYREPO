package com.b07.store;

import java.math.BigDecimal;
import java.util.HashMap;

import com.b07.inventory.Item;
import com.b07.users.User;

public class SaleImpl implements Sale {
	private int id;
	private User user;
	private HashMap<Item, Integer> itemMap;
	private BigDecimal totalPrice;
	
	public SaleImpl(int id, User user) {
		this.id = id;
		this.user = user;
	}
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getTotalPrice() {
		// TODO Auto-generated method stub
		
		return totalPrice;
	}

	@Override
	public void setTotalPrice(BigDecimal price) {
		// TODO Auto-generated method stub
		this.totalPrice = price;
		
	}

	@Override
	public HashMap<Item, Integer> getItemMap() {
		// TODO Auto-generated method stub
		return itemMap;
	}

	@Override
	public void setItemMap(HashMap<Item, Integer> itemMap) {
		// TODO Auto-generated method stub
		this.itemMap = itemMap;
		
	}
	@Override
	public void updateItemMap(Item item, Integer value) {
		// TODO Auto-generated method stub
		
	}

}
