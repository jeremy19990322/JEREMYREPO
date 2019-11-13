package com.b07.users;

import java.sql.SQLException;

import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.enumerator.Roles;

/**
 * one of user class ---- admin, extending User
 * implements all methods from User class
 * auto generated
 * @author ningh
 *
 */
public class Admin extends User{
	public Admin(int id, String name, int age, String address) {
		super (id, name, age, address);
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}

	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub
		super.setId(id);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		super.setName(name);
	}

	@Override
	public int getAge() {
		// TODO Auto-generated method stub
		return super.getAge();
	}

	@Override
	public void setAge(int age) {
		// TODO Auto-generated method stub
		super.setAge(age);
	}

	@Override
	public String getAddress() {
		// TODO Auto-generated method stub
		return super.getAddress();
	}

	@Override
	public void setAddress(String address) {
		// TODO Auto-generated method stub
		super.setAddress(address);
	}

	@Override
	public int getRoleld() {
		// TODO Auto-generated method stub
		return super.getRoleld();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	/**
	 * promote am employee --- this method does not destroy the
	 * original role in database
	 * this method takes in a parameter -->An Employee
	 */
	public boolean promoteEmployee(Employee employee) throws SQLException {
		for (int roleId : DatabaseSelectHelper.getRoleIds()) {
			// here to check if the role name is in database or not
			// if yes, update role by taking in role id and id
			// if not, just return false
			if (DatabaseSelectHelper.getRoleName(roleId).equals(Roles.ADMIN.name())) {
				DatabaseUpdateHelper.updateUserRole(roleId, employee.getId());
				return true;
			}
		}
		return false;
	}
}
