package database.controllers;

import java.io.IOException;

import database.Credentials;
import datatype.Book;
import datatype.Customer;

public class Controller extends Database{
	private RecordManager   rm;
	private CustomerManager cm;
	private BookManager     bm;
	
	public Controller (Credentials creds) throws Exception {
		super(creds);
		rm = new RecordManager(this);
		cm = new CustomerManager(this);
		bm = new BookManager(this);
	}
	
	public CustomerManager customers() { return cm; }
	public RecordManager   records()   { return rm; }
	public BookManager     books()     { return bm; }
	
	// Customer handlers
	public boolean registerCustomer(Customer customer) {
		try {
			return cm.add(customer); 
		} 
		catch (IOException e) {
			System.out.println("[Controller, registerCustomer] ERROR: " + e.getMessage());
		}
		return false;
	}
	
	public boolean deregisterCustomer(Customer customer) {
		try {
			return cm.delete(customer);
		} 
		catch (Exception e) {
			System.out.println("[Controller, deregisterCustomer] ERROR: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	// Book handlers
	
	public boolean addBook(Book book) {
		try {
			//return 
		} catch (Exception e) {
			
		}
		return false;
	}
	
	
}
