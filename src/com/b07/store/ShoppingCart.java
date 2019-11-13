package com.b07.store;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ItemNotFoundException;
import com.b07.inventory.Item;
import com.b07.users.Customer;

public class ShoppingCart {
	private HashMap<Item, Integer> items;
	private Customer customer;
	private BigDecimal total;
	private static final BigDecimal TAXRATE = new BigDecimal(1.13);
	
	public ShoppingCart(Customer customer) {
		this.customer = customer;
	}
	public void addItem(Item item, int quantity) {
		if (items.get(item) == null)
			items.put(item, quantity);
		else {
			items.put(item, items.get(item) + quantity);
		}
		BigDecimal itemPrice = item.getPrice();
		total = total.add(itemPrice.multiply(new BigDecimal(quantity)));
	}
	public void removeItem(Item item, int quantity) throws ItemNotFoundException{
		if (!items.containsKey(item))
			throw new ItemNotFoundException();
		
		BigDecimal itemPrice = item.getPrice();
		int originalQuantity = items.get(item);
		int newQuantity = originalQuantity - quantity;
		if (newQuantity > 0) {
			items.put(item, newQuantity);
			total = total.subtract(itemPrice.multiply(new BigDecimal(quantity)));
	}	else {
			items.remove(item);
			total = total.subtract(itemPrice.multiply(new BigDecimal(originalQuantity)));
	}
	}
	public List<Item> getItems(){
		Set<Item> itemSet = items.keySet();
		List<Item> itemList = new ArrayList<Item>();
		for (Item item: itemSet)
			itemList.add(item);
		return itemList;
	}
	public Customer getCustomer() {
		return customer;
		
	}
	public BigDecimal getTotal() {
		return total;
	}
	public BigDecimal getTaxRate() {
		return ShoppingCart.TAXRATE;
	}
	public boolean CheckOut() throws SQLException, DatabaseInsertException{
		if (customer == null) return false;
		if (checkSufficientItem()) {
			for (Item item : items.keySet()) {
				int itemId = item.getId();
				int purchaseQuantity = items.get(item);
				int afterPurchaseQuantity = DatabaseSelectHelper.getInventoryQuantity(itemId) - purchaseQuantity;
				DatabaseUpdateHelper.updateInventoryQuantity(afterPurchaseQuantity, itemId);
				// insert sale table
				BigDecimal itemPrice = item.getPrice();
				itemPrice.multiply(new BigDecimal(purchaseQuantity));
				int saleId = DatabaseInsertHelper.insertSale(customer.getId(), itemPrice);
				DatabaseInsertHelper.insertItemizedSale(saleId, itemId, purchaseQuantity);
				
			}
			clearCart();
			return true;
			
		}
		return false;
	}
	private boolean checkSufficientItem(){
		try {for (Item item : items.keySet()) {
			int itemId = item.getId();
			int purchaseQuantity = items.get(item);
			int inventoryQuantity = DatabaseSelectHelper.getInventoryQuantity(itemId);
			if (purchaseQuantity > inventoryQuantity)
				return false;}
		return true;}catch (Exception SQLException) {
			return false;
		
		}
		
	}
	public void clearCart() {
		items.clear();
		total.subtract(total);
		total = new BigDecimal(0);
	}
	

}
