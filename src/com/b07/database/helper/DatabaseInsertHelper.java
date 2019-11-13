package com.b07.database.helper;

import com.b07.database.DatabaseInserter;
import com.b07.database.helper.DatabaseDriverHelper;
import com.b07.enumerator.Roles;
import com.b07.exceptions.DatabaseInsertException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * fixed all errors in starter codes
 * add throws keywords for all methods
 * @author ningh
 *
 */
public class DatabaseInsertHelper extends DatabaseInserter {
	private static boolean contains(String name) {
		for (Roles r : Roles.values())
			if (r.name().equals(name)) return true;
		return false;
	}
  /**
   * INSERT A ROLE TO DATABASE!!!!***
   * @param name
   * @return
   * @throws DatabaseInsertException
   * @throws SQLException
   */
  public static int insertRole(String name) throws DatabaseInsertException, SQLException {
    //TODO Implement this method as stated on the assignment sheet 
    //hint: You should be using these three lines in your final code
	// check if the name exists or not. if not, throw an exception 
	if (!contains(name)) {
		throw new DatabaseInsertException();
	}
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int roleId = DatabaseInserter.insertRole(name, connection);
    connection.close();
    return roleId;
  }
  /**
   * INSERT A NEW USER!!!! Static
   * @param name
   * @param age
   * @param address
   * @param password
   * @return
   * @throws DatabaseInsertException
   * @throws SQLException
   */
  public static int insertNewUser(String name, int age, String address, String password) throws DatabaseInsertException, SQLException {
    //TODO Implement this method as stated on the assignment sheet 
    //hint: You should be using these three lines in your final code
	if (age < 0) {
		throw new DatabaseInsertException();
	}
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int userId = DatabaseInserter.insertNewUser(name, age, address, password, connection);
    connection.close();
    return userId;
  }
  
  public static int insertUserRole(int userId, int roleId) throws DatabaseInsertException, SQLException {
    //TODO Implement this method as stated on the assignment sheet 
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int userRoleId = DatabaseInserter.insertUserRole(userId, roleId, connection);
    connection.close();
    return userRoleId;
  }

  public static int insertItem(String name, BigDecimal price) throws DatabaseInsertException, SQLException {
    //TODO Implement this method as stated on the assignment sheet 
    //hint: You should be using these three lines in your final code
	// name length must be less than 64 chars and price can't be negative  
	if (name.length() > 64 || price.floatValue() < 0) {
		throw new DatabaseInsertException();
	}
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    //rounding by half even
    price = price.setScale(2, RoundingMode.HALF_EVEN);
    int itemId = DatabaseInserter.insertItem(name, price, connection);
    connection.close();
    return itemId;
  }

  public static int insertInventory(int itemId, int quantity) throws DatabaseInsertException, SQLException {
    //TODO Implement this method as stated on the assignment sheet 
    //hint: You should be using these three lines in your final code
	// check if the item exists and quantity can't be negative
	  if (quantity < 0 || DatabaseSelectHelper.getItem(itemId) == null) {
		throw new DatabaseInsertException();
	}
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int inventoryId = DatabaseInserter.insertInventory(itemId, quantity, connection);
    connection.close();
    return inventoryId;
  }

  public static int insertSale(int userId, BigDecimal totalPrice) throws DatabaseInsertException, SQLException {
    //TODO Implement this method as stated on the assignment sheet 
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    if (DatabaseSelectHelper.getUserDetails(userId) == null || totalPrice.floatValue() < 0) {
    	throw new DatabaseInsertException();
    }
    int saleId = DatabaseInserter.insertSale(userId, totalPrice, connection);
    connection.close();
    return saleId;
  }

  public static int insertItemizedSale(int saleId, int itemId, int quantity) throws DatabaseInsertException, SQLException {
  //TODO Implement this method as stated on the assignment sheet 
    //hint: You should be using these three lines in your final code
    if (DatabaseSelectHelper.getSaleById(saleId) == null || DatabaseSelectHelper.getItem(itemId) == null ||
  quantity < 0) {
    	throw new DatabaseInsertException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    int itemizedId = DatabaseInserter.insertItemizedSale(saleId, itemId, quantity, connection);
    connection.close();
    return itemizedId;
  }

  
}
