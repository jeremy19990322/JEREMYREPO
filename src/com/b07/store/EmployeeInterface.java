package com.b07.store;

import java.sql.SQLException;
import java.util.List;

import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.enumerator.Roles;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.users.Employee;

public class EmployeeInterface {
	
	private Employee currentEmployee;
	
	private Inventory inventory;
	
	
	public EmployeeInterface(Employee employee, Inventory inventory) throws SQLException {
		
		// this.currentEmployee = employee;
		setCurrentEmployee(employee);
		this.inventory = inventory;
	}
	
	public EmployeeInterface (Inventory inventory) {
		this.inventory = inventory;
	}
	
	
	public void setCurrentEmployee(Employee employee) throws SQLException {

		if (employee.authenticate(
				DatabaseSelectHelper.getPassword(employee.getId()))) {
			this.currentEmployee = employee;
		}		
	}
	
	public boolean hasCurrentEmployee() {
		return currentEmployee != null;
	}
	
	public boolean restockInventory(Item item, int quantity ) {
		if (quantity < 0) 
			return false;
		else {
			inventory.updateMap(item, quantity);
			return true;
		}	
	}
	
	private int createUser(String name, int age, String address, String password, String role) throws DatabaseInsertException, SQLException {
		int userId = DatabaseInsertHelper.insertNewUser(name, age, address, password);
		
		
		List<Integer> roleIds = DatabaseSelectHelper.getRoleIds();
		int roleId = 0;
		for (int rid : roleIds) {
			if (DatabaseSelectHelper.getRoleName(rid).equals(role)) {
				roleId = rid;
				break;
			}
		}
							
		DatabaseInsertHelper.insertUserRole(userId, roleId);
		return userId;
	}
	
	public int createCustomer(String name, int age, String address, String password) throws DatabaseInsertException, SQLException {
		return createUser(name, age, address, password, Roles.CUSTOMER.name());
	}
	
	public int createEmployee(String name, int age, String address, String password) throws SQLException, DatabaseInsertException {
		return createUser(name, age, address, password, Roles.EMPLOYEE.name());
	}	
}
