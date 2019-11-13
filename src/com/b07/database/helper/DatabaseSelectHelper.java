package com.b07.database.helper;

import com.b07.database.DatabaseSelector;
import com.b07.enumerator.Roles;
import com.b07.inventory.Inventory;
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.Item;
import com.b07.inventory.ItemImpl;
import com.b07.store.Sale;
import com.b07.store.SaleImpl;
import com.b07.store.SalesLog;
import com.b07.store.SalesLogImpl;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * TODO: Complete the below methods to be able to get information out of the database.
 * TODO: The given code is there to aide you in building your methods.  You don't have
 * TODO: to keep the exact code that is given (for example, DELETE the System.out.println())
 * TODO: and decide how to handle the possible exceptions
 */
public class DatabaseSelectHelper extends DatabaseSelector {
	
	  public static List<Integer> getRoleIds() throws SQLException {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    ResultSet results = DatabaseSelector.getRoles(connection);
	    List<Integer> ids = new ArrayList<>();
	    while (results.next()) {
	      ids.add(results.getInt("ID"));
	    }
	    results.close();
	    connection.close();
	    return ids;    
	  }
	  
	  public static String getRoleName(int roleId) throws SQLException {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    String role = DatabaseSelector.getRole(roleId, connection);
	    connection.close();
	    return role;
	  }
	  
	  public static int getUserRoleId(int userId) throws SQLException {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    int roleId = DatabaseSelector.getUserRole(userId, connection);
	    connection.close();
	    return roleId;
	  }
	  
	  public static List<Integer> getUsersByRole(int roleId) throws SQLException {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    ResultSet results = DatabaseSelector.getUsersByRole(roleId, connection);
	    List<Integer> userIds = new ArrayList<>();
	    while (results.next()) {
	    	userIds.add(results.getInt("USERID"));
	    }
	    results.close();
	    connection.close();
	    return userIds;
	    
	  }
	  
	  public static List<User> getUsersDetails() throws SQLException {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    ResultSet results = DatabaseSelector.getUsersDetails(connection);
	    List<User> users = new ArrayList<>();
	    while (results.next()) {
	      
	      users.add(getUserDetails(results.getInt("ID")));
	    }
	    results.close();
	    connection.close();
	    return users;
	  }
	  
	  public static User getUserDetails(int userId) throws SQLException {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    ResultSet results = DatabaseSelector.getUserDetails(userId, connection);
	    results.next();
		User user = null;
	    int id = results.getInt("ID");
	    String name = results.getString("NAME");
	    int age = results.getInt("AGE");
	    String address = results.getString("ADDRESS");
	    
	    int roleId = getUserRoleId(id);
	    String roleName = getRoleName(roleId);
	    
	    if (roleName.equals(Roles.ADMIN.name())) {
	  	  user = new Admin(id, name, age, address);
	    } else if (roleName.equals(Roles.CUSTOMER.name())) {
	  	  user = new Customer(id, name, age, address);
	    } else if (roleName.equals(Roles.EMPLOYEE.name())) {
	  	  user = new Employee(id, name, age, address);
	    }
	    
	    results.close();
	    connection.close();
	    return user;
	  }
	  
	  public static String getPassword(int userId) throws SQLException {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    String password = DatabaseSelector.getPassword(userId, connection);
	    connection.close();
	    return password;
	  }
	  
	  public static List<Item> getAllItems() throws SQLException {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    ResultSet results = DatabaseSelector.getAllItems(connection);
	    List<Item> items = new ArrayList<>();
	    while(results.next()) {
	      items.add(getItem(results.getInt("ID")));
	    }
	    results.close();
	    connection.close();
	    return items;
	  }
	  
	  public static Item getItem(int itemId) throws SQLException {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    ResultSet results = DatabaseSelector.getItem(itemId, connection);
	    results.next();
	    int id = results.getInt("ID");
		String name = results.getString("NAME");
		BigDecimal price = new BigDecimal(results.getString("PRICE"));
		
		Item item = new ItemImpl(id, name, price);
		
	    results.close();
	    connection.close();
	    return item;
	  }
	  
	  public static Inventory getInventory() throws SQLException {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    ResultSet results = DatabaseSelector.getInventory(connection);
	    Inventory inventory = new InventoryImpl();
	    
	    while(results.next()) {
	    	inventory.updateMap(
	    			getItem(results.getInt("ITEMID")), 
	    			Integer.parseInt(results.getString("QUANTITY")));
	    	
	    }
	    results.close();
	    connection.close();
	    return inventory;
	  }
	  
	  public static int getInventoryQuantity(int itemId) throws SQLException  {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    int quantity = DatabaseSelector.getInventoryQuantity(itemId, connection);
	    connection.close();
	    return quantity;
	  }
	  
	  public static SalesLog getSales() throws SQLException {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    ResultSet results = DatabaseSelector.getSales(connection);
	    SalesLog saleslog = new SalesLogImpl();
	    while(results.next()) {
	    	int saleId = results.getInt("ID");
	    	int userId = results.getInt("USERID");
	    	Sale sale = new SaleImpl(saleId, getUserDetails(userId));
	        saleslog.addSale(sale);
	    }
	    results.close();
	    connection.close();
	    return null;
	  }
	  
	  // TODO: ===========================  ================================
	  public static Sale getSaleById(int saleId) throws SQLException {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    ResultSet results = DatabaseSelector.getSaleById(saleId, connection);
	    results.next();
//	    User user = getUserDetails(results.getInt("USERID"));
//	    int id = results.getInt("ID");
//	    BigDecimal totalPrice = new BigDecimal(results.getString("TOTALPRICE"));
	//    
//	    Sale sale = new SaleImpl(id, user);
//	    sale.setTotalPrice(totalPrice);
	    // Sale sale = getItemizedSaleById(saleId);
	    
	    
	    results.close();
	    connection.close();
	    // return sale;
	    return null;
	  }
	  
	  public static List<Sale> getSalesToUser(int userId) throws SQLException {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    ResultSet results = DatabaseSelectHelper.getSalesToUser(userId, connection);
	    List<Sale> sales = new ArrayList<>();
	    while (results.next()) {
	      sales.add(getSaleById(results.getInt("ID")));
	    }
	    results.close();
	    connection.close();
	    return sales;
	  }
	  
	  public static Sale getItemizedSaleById(int saleId) throws SQLException {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    ResultSet results = DatabaseSelector.getItemizedSaleById(saleId, connection);
	    
	    ResultSet temps = DatabaseSelector.getSaleById(saleId, connection);
	    temps.next();
	    User user = getUserDetails(temps.getInt("USERID"));
	    
	    Sale sale = new SaleImpl(saleId, user);
	    sale.setTotalPrice(new BigDecimal(0));
	    while (results.next()) {
		    int itemId = results.getInt("ITEMID");
		    int quantity = results.getInt("QUANTITY");
		    Item item = getItem(itemId);
		    sale.setTotalPrice(
		    		sale.getTotalPrice().add(
		    				item.getPrice().multiply(new BigDecimal(quantity))));
		    sale.updateItemMap(item, quantity);
	    
	    }
	    
	    results.close();
	    connection.close();
	    return sale;
	  }
	  
	  public static SalesLog getItemizedSales() throws SQLException {
	    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	    ResultSet results = DatabaseSelector.getItemizedSales(connection);
	    SalesLog saleslog = new SalesLogImpl();
	    while (results.next()) {
	      saleslog.addSale(getItemizedSaleById(results.getInt("SALEID")));
	    }
	    results.close();
	    connection.close();
	    return saleslog;
	  }
	}
