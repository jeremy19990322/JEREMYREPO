package com.b07.database.helper;

import com.b07.database.DatabaseUpdater;
import com.b07.enumerator.Roles;
import com.b07.exceptions.DatabaseUpdateException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUpdateHelper extends DatabaseUpdater {
	private static boolean contains(String name) {
		for (Roles r : Roles.values())
			if (r.name().equals(name)) return true;
		return false;
	}
  
  public static boolean updateRoleName(String name, int id) throws SQLException, DatabaseUpdateException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
	
	try {
		DatabaseSelectHelper.getRoleName(id);
	} catch (Exception SQLException) {
		throw new DatabaseUpdateException();
	}
	
	if (!contains(name))
		throw new DatabaseUpdateException();
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateRoleName(name, id, connection);
    connection.close();
    return complete;
  } 
  
  public static boolean updateUserName(String name, int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserName(name, userId, connection);
    connection.close();
    return complete;
  }
  
  public static boolean updateUserAge(int age, int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserAge(age, userId, connection);
    connection.close();
    return complete;
  }
  
  public static boolean updateUserAddress(String address, int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserAddress(address, userId, connection);
    connection.close();
    return complete;

  }
  
  public static boolean updateUserRole(int roleId, int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserRole(roleId, userId, connection);
    connection.close();
    return complete;

  }
  
  public static boolean updateItemName(String name, int itemId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateItemName(name, itemId, connection);
    connection.close();
    return complete;

  }
  
  public static boolean updateItemPrice(BigDecimal price, int itemId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateItemPrice(price, itemId, connection);
    connection.close();
    return complete;
  }
  
  public static boolean updateInventoryQuantity(int quantity, int itemId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateInventoryQuantity(quantity, itemId, connection);
    connection.close();
    return complete;
  }
}
