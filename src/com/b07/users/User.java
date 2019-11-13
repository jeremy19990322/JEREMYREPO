package com.b07.users;

import java.sql.SQLException;

import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.security.PasswordHelpers;
/**
 * 
 * An abstract class User
 * Admin, Customer, Employee extend this abstract class for establishing instances.
 * 
 * @author ningh
 *
 */
public abstract class User {
  //TODO: Complete this class based on UML provided on the assignment sheet.
	private int id;
	private String name;
	private int age;
	private String address;
	private int roleld;
	private boolean authenticated;
	
	public User() { }
	
	// The constructor for User, taking id, name and address
	public User(int id, String name, int age, String address) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;		
	}
	
	// The constructor overload, taking one more boolean type authenticate and one more integer role id.
	public User(int id, String name, int age, String address, int roleld, boolean authenticated) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
		this.roleld = roleld;
		this.authenticated = authenticated;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name= name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getRoleld() {
		return roleld;
	}
	/**
	 * a final boolean returned value for validate the user has correct password
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public final boolean authenticate(String password) throws SQLException {
		String hashedcode = DatabaseSelectHelper.getPassword(this.id);
		this.authenticated = PasswordHelpers.comparePassword(hashedcode, password);
		return authenticated;
	}
	
}
