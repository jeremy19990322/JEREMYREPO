package com.b07.store;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;

import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.enumerator.Roles;
import com.b07.exceptions.IncorrectPasswordException;
import com.b07.exceptions.UserDoesNotExist;
import com.b07.inventory.Inventory;
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.Item;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;

public class SalesApplication {
  /**
   * This is the main method to run your entire program! Follow the "Pulling it together" 
   * instructions to finish this off.
   * @param argv unused.
   */
  public static void main(String[] argv) {
	BufferedReader user_input = new BufferedReader(new InputStreamReader(System.in));	
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    if (connection == null) {
      System.out.print("NOOO");
    }
    try {
      //TODO Check what is in argv 
      //If it is -1
      /*
       * TODO This is for the first run only!
       * Add this code:
       * DatabaseDriverExtender.initialize(connection);
       * Then add code to create your first account, an administrator with a password
       * Once this is done, create an employee account as well.
       * 
       */
    	if (argv[0].equals("-1")) {
    		//DatabaseDriverExtender.initialize(connection);
    		int adminRoleId = DatabaseInsertHelper.insertRole(Roles.ADMIN.name());
    		int employeeRoleId = DatabaseInsertHelper.insertRole(Roles.EMPLOYEE.name());
    		DatabaseInsertHelper.insertRole(Roles.CUSTOMER.name());
    		
    		
    		//**************Create admins****************
    		System.out.println("Welcome to Amabay Demo!");
    		System.out.println("Please Enter Your Admin's Name Here");
    		String name = user_input.readLine();
    		
    		System.out.println("Please Enter Your Admin's Age Here");
    		int age = Integer.parseInt(user_input.readLine());
    		
    		System.out.println("Please Enter Your Admin's Adress Here");
    		String address = user_input.readLine();
    		
    		System.out.println("Please Enter Your Admin's Password Here");
    		String password = user_input.readLine();
    		
    		int userId = DatabaseInsertHelper.insertNewUser(name, age, address, password);
    		
    		DatabaseInsertHelper.insertUserRole(userId, adminRoleId);
    		
    		
    		System.out.println("Excellent!" + "Please Remember ID: " + userId);
    		
    		//**************Create employees**************
    		System.out.println("Please enter the following information to crate employees!");
    		System.out.println("employee's name:--> ");
    		String employeeName = user_input.readLine();
    		
    		System.out.println("employee's age:--> ");
    		int employeeAge = Integer.parseInt(user_input.readLine());
    		
    		System.out.println("employee's address:--> ");
    		String employeeAddress = user_input.readLine();
    		
    		System.out.println("employees's password:--> ");
    		String EmployeePassword = user_input.readLine();
    		
    		userId = DatabaseInsertHelper.insertNewUser(employeeName, employeeAge, employeeAddress, EmployeePassword);
    		DatabaseInsertHelper.insertUserRole(userId, employeeRoleId);
    		System.out.println("Excellent!" + "Please Remember ID: " + userId);
    		System.out.println("Loading.......");
    		System.out.println("Congradulations! Your commands run successfully! :)");
    		System.out.println("You can re-run the program using different configurations!");
    	}
    	
    	
    	
    	
      //If it is 1
      /*
       * TODO In admin mode, the user must first login with a valid admin account
       * This will allow the user to promote employees to admins. Currently, this
       * is all an admin can do.
       */
    	
    	else if (argv[0].equals("1")) {
    		System.out.println("Admin Mode Has been Loaded");
    		System.out.println("Please Enter The Following Information: ");
    		
    		System.out.println("Admin ID:--> ");
    		int adminId = Integer.parseInt(user_input.readLine());
    		
    		System.out.println("Admin Password:--> ");
    		String adminPassword = user_input.readLine();
    		
    		Admin admin = (Admin) DatabaseSelectHelper.getUserDetails(adminId);
    		if (!admin.authenticate(adminPassword))
    			throw new IncorrectPasswordException();
    		System.out.println("Dear Admin, Please Enter The Employee's ID Who You Want To Promote! :>");
    		int employeeId = Integer.parseInt(user_input.readLine());
    		Employee employee = (Employee) DatabaseSelectHelper.getUserDetails(employeeId);
    		
    		if(admin.promoteEmployee(employee))
    			System.out.println("Employee " + employeeId + " Has Been Promoted Successfully! :>");
    		else {
    			System.out.println("Employee " + employeeId + " Fails To Be Promoted! :<");
    			
    		}
    	}
      //If anything else - including nothing
      /*
       * TODO Create a context menu, where the user is prompted with:
       * 1 - Employee Login
       * 2 - Customer Login
       * 0 - Exit
       * Enter Selection: 
       */
    	else {
    		while(true) {
	    		System.out.println("You Have The Following Memu, Choose One Command To Run!");
	    		System.out.println("1. Employee Log in");
	    		System.out.println("2. Customer Log in");
	    		System.out.println("0. Exit");
	    		System.out.println("Enter Your Command #: ");
    		
    	
    
      //If the user entered 1
      /*
       * TODO Create a context menu for the Employee interface
       * Prompt the employee for their id and password
       * Attempt to authenticate them.
       * If the Id is not that of an employee or password is incorrect, end the session
       * If the Id is an employee, and the password is correct, create an EmployeeInterface object
       * then give them the following options:
       * 1. authenticate new employee
       * 2. Make new User
       * 3. Make new account
       * 4. Make new Employee
       * 5. Restock Inventory
       * 6. Exit
       * 
       * Continue to loop through as appropriate, ending once you get an exit code (9)
       */
		    	String argument = user_input.readLine();
		    	if (argument.equals("1")) {
		    		System.out.println("To log in as an employee, ");
		    	    System.out.println("Please Enter Your Employee ID: ");
		    	    int employeeId = Integer.parseInt(user_input.readLine());
		    	    
		    	    System.out.println("Please Enter Your Password: ");
		    	    String password = user_input.readLine();
		    	    
		    	    Employee employee = (Employee) DatabaseSelectHelper.getUserDetails(employeeId);
		    	    
		    	    if (!employee.authenticate(password)) {
		    	    	System.out.println("NO ACCESS :<");
		    	    	continue;
		    	    }
		    	    
		    	    Inventory inventory = new InventoryImpl();
		    	    EmployeeInterface employeeInterface = new EmployeeInterface(employee, inventory);
		    	    
		    	    while(true) {
		    	    	System.out.println("Here are some commands: ");
		    	    	System.out.println("1. Authenticate new employee");
		    	    	System.out.println("2. Make new user");
		    	    	System.out.println("3. Make new account");
		    	    	System.out.println("4. Make new Employee");
		    	    	System.out.println("5. Restock inventory");
		    	    	System.out.println("6. Exit");
		    	    	
		    	    	String commandNumber = user_input.readLine();
		    	    	//************Authenticate new employee*************
		    	    	if (commandNumber.equals("1")) {
		    	    		System.out.println("To authenticate a new employee, the following information is required");
		    	    		System.out.println("new employee's ID:--> ");
		    	    		int newEmployeeId = Integer.parseInt(user_input.readLine());
		    	    		
		    	    		System.out.println("new employe's Password:--> ");
		    	    		String newEmployeePassword = user_input.readLine();
		    	    		
		    	    		Employee newEmployee = (Employee) DatabaseSelectHelper.getUserDetails(newEmployeeId);
		    	    		
		    	    		if (newEmployee.authenticate(newEmployeePassword)) {
		    	    			System.out.println("The new employee " + newEmployeeId + " has been authenticated! :>" );
		    	    		}}
		 
		  
		    	    		//**********make new user****consider new user as new customer!!!! for now...
		    	    	else if(commandNumber.equals("2")) {
		    	    		System.out.println("You are creating a new user!");
		    	    		System.out.println("Please follow the instructions below :> ");
		    	    		
		    	    		System.out.println("User name:--> ");
		    	    		String newUserName = user_input.readLine();
		    	    		
		    	    		System.out.println("User age:--> ");
		    	    		int newUserAge = Integer.parseInt(user_input.readLine());
		    	    		
		    	    		System.out.println("User address:--> ");
		    	    		String newUserAddress = user_input.readLine();
		    	    		
		    	    		System.out.println("User password:--> ");
		    	    		String newUserPassword = user_input.readLine();
		    	    		
		    	    		//fixed!!!
		    	    		//call method in EmployeeInterface -- creatCustomer(string, integer, string, string)
		    	    		employeeInterface.createCustomer(newUserName, newUserAge, newUserAddress, newUserPassword);
		    	    		System.out.println("Well done, now a new user " + newUserName + "has been created!");
		    	    		
		    	    	}
		    	    	//************make new account.???***also consider as new customer!!!! for now...
		    	    	// basically same as creating a new user above
		    	    	else if(commandNumber.equals("3")) {
		    	    		System.out.println("You are creating a new account!");
		    	    		System.out.println("Please follow the instructions below :>");
		    	    		
		    	    		System.out.println("User name:--> ");
		    	    		String newUserName = user_input.readLine();
		    	    		
		    	    		System.out.println("User age:--> ");
		    	    		int newUserAge = Integer.parseInt(user_input.readLine());
		    	    		
		    	    		System.out.println("User address:--> ");
		    	    		String newUserAddress = user_input.readLine();
		    	    		
		    	    		System.out.println("User password:--> ");
		    	    		String newUserPassword = user_input.readLine();
		    	    		
		    	    		employeeInterface.createCustomer(newUserName, newUserAge, newUserAddress, newUserPassword);
		    	    		
		    	    		System.out.println("Well done, now a new account " + newUserName + "has been created!");
		    	    	}
		    	    	//***********make new employee***********
		    	    	else if (commandNumber.equals("4")) {
		    	    		System.out.println("You are creating a new employee!");
		    	    		System.out.println("Please follow the instructions below :>");
		    	    		
		    	    		System.out.println("Employee name:--> ");
		    	    		String newEmployeeName = user_input.readLine();
		    	    		
		    	    		System.out.println("Employee age:--> ");
		    	    		int newEmployeeAge  = Integer.parseInt(user_input.readLine());
		    	    		
		    	    		System.out.println("Employee address:--> ");
		    	    		String newEmployeeAddress = user_input.readLine();
		    	    		
		    	    		System.out.println("Employee Password:--> ");
		    	    		String newEmployeePassword = user_input.readLine();
		    	    		
		    	    		employeeInterface.createEmployee(newEmployeeName, newEmployeeAge, newEmployeeAddress, newEmployeePassword);
		    	    		System.out.println("Well done, now a new employee " + newEmployeeName + "has been created!");
		    	    	}
		    	    	
		    	    	//************Re-stock Inventory*********
		    	    	else if (commandNumber.equals("5")){
		    	    		System.out.println("To re-stock inventory, please follow the instructions below: ");
		    	    		System.out.println("Please enter item ID:--> ");
		    	    		int itemId = Integer.parseInt(user_input.readLine());
		    	    		// get item by item ID
		    	    		Item item = DatabaseSelectHelper.getItem(itemId);
		    	    		
		    	    		System.out.println("Please enter the quantity to re-strock:--> ");
		    	    		int quantity = Integer.parseInt(user_input.readLine());
		    	    		
		    	    		DatabaseInsertHelper.insertInventory(itemId, quantity);
		    	    		//call method from employeeInterface!!!!!
		    	    		//fixed
		    	    		employeeInterface.restockInventory(item, quantity);
		    	    		System.out.println("Well done, now the inventory has " + quantity + item.getName());
		    	    	}
		    	    	else if (commandNumber.equals("6")) {
		    	    		break;
		    	    	}
		    	    }}
		    	    else if(argument.equals("1")) {
		    	    	Customer customer = null;
		    	    	while(true) {
		    	    		System.out.println("To login as a customer, ");
		    	    		System.out.println("Please Enter Your Customer ID:--> ");
		    	    		int customerId = Integer.parseInt(user_input.readLine());
		    	    	
		    	    		System.out.println("Please Enter Your Custoner Password:--> ");
		    	    		String customerPassword = user_input.readLine();
		    	    	
		    	    		customer = (Customer) DatabaseSelectHelper.getUserDetails(customerId);
		    	    		if (customer == null) continue;
		    	    		if (!customer.authenticate(customerPassword)) continue;
		    	    		
		    	    		int roleId = DatabaseSelectHelper.getUserRoleId(customer.getId());
		    	    		if (!DatabaseSelectHelper.getRoleName(roleId).equals(Roles.CUSTOMER.name()))
		    	    			continue;
		    	    		else
		    	    			break;
		    	    	}
		    	    	
		    	    	//Shopping Cart******************************
		    	    	//establish an instance of shopping cart which takes in a customer
		    	    	ShoppingCart shoppingCart = new ShoppingCart(customer);
		    	    	while(true) {
		    	    		System.out.println("1. List current items in cart");
		    	    		System.out.println("2. Add a quantity of an item to the cart");
		    	    		System.out.println("3. Check total price of items in the cart");
		    	    		System.out.println("4. Remove a quantity of an item from the cart");
		    	    		System.out.println("5. Check out");
		    	    		System.out.println("6. Exit");
		    	    		
		    	    		String shoppingCartCommand = user_input.readLine();
		    	    		// **************************list current items in cart!*************************8
		    	    		if(shoppingCartCommand.equals("1")) {
		    	    			System.out.println("Dear Customer, the products in your cart are listed: ");
		    	    			for (Item item : shoppingCart.getItems()) {
		    	    				System.out.println(item.getName());
		    	    			}
		    	    		}
		    	    		// *************************8add a quantity of an item to the cart**********************
		    	    		else if (shoppingCartCommand.equals("2")){
		    	    			System.out.println("Which item would you like to modify? Please enter the item Id:--> ");
		    	    			int addItemId = Integer.parseInt(user_input.readLine());
		    	    			
		    	    			System.out.println("How many items do you want to add to your cart? Please enter the quantiyu:--> ");
		    	    			int addQuantity = Integer.parseInt(user_input.readLine());
		    	    			
		    	    			Item item = DatabaseSelectHelper.getItem(addItemId);
		    	    			
		    	    			shoppingCart.addItem(item, addQuantity);
		    	    		// ********************check total price********************************8
		    	    			
		    	    		}
		    	    		else if (shoppingCartCommand.equals("3")) {
		    	    			//prices are big decimals
		    	    			BigDecimal totalPrice = shoppingCart.getTotal().multiply(shoppingCart.getTaxRate());
		    	    			System.out.println("Dear customer, the total price in your shopping cart is :  " + "$"+totalPrice);
		    	    		}
		    	    		//********************Remove a quantity of items from shopping cart*******************
		    	    		else if (shoppingCartCommand.equals("4")) {
		    	    			System.out.println("Please enter the item id you would like to remove:--> ");
		    	    			int removeItemId = Integer.parseInt(user_input.readLine());
		    	    			Item removeItem = DatabaseSelectHelper.getItem(removeItemId);		    	    			System.out.println("Please enter the quantity of items you would like to remove from your shopping cart:--> ");
		    	    			int removeQuantity = Integer.parseInt(user_input.readLine());
		    	    			
		    	    			shoppingCart.removeItem(removeItem, removeQuantity);
		    	    			
		    	    		}
		    	    		//*********************Check Out**********easy
		    	    		else if (shoppingCartCommand.equals("5")) {
		    	    			shoppingCart.CheckOut();
		    	    		}
		    	    		else if (shoppingCartCommand.equals("6")) {
		    	    			break;
		    	    		}
		    	    	}
		    	    	
		    	    	
		    	    	
		    	    	
		    	    	
		    	    	
		    	    	
		    	    }
		    	
		    	    else if(argument.equals("0")) {
		    	    	break;
		    	    }
		    	    
    	    	
    			
    	
    	
    	
    	
    	
      //If the user entered 2
      /*
       * TODO create a context menu for the customer Shopping cart
       * Prompt the customer for their id and password
       * Attempt to authenticate them
       * If the authentication fails or they are not a customer, repeat
       * If they get authenticated and are a customer, give them this menu:
       * 1. List current items in cart
       * 2. Add a quantity of an item to the cart
       * 3. Check total price of items in the cart
       * 4. Remove a quantity of an item from the cart
       * 5. check out 
       * 6. Exit
       * 
       * When checking out, be sure to display the customers total, and ask them if they wish
       * to continue shopping for a new order
       * 
       * For each of these, loop through and continue prompting for the information needed
       * Continue showing the context menu, until the user gives a 6 as input.
       */
      //If the user entered 0
      /*
       * TODO Exit condition
       */
      //If the user entered anything else:
      /*
       * TODO Re-prompt the user
       */
      
      
      
    		}
    }
    
    }catch (NumberFormatException e) {
    	System.out.println("Your age is not valid :( ");
    }catch (Exception e) {
      //TODO Improve this!
      System.out.println("Something went wrong :(");
    } finally {
      try {
        connection.close();
      } catch (Exception e) {
        System.out.println("Looks like it was closed already :)");
      }
    }
    
  
  }}

