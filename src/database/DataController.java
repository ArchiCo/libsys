package database;

import java.io.IOException;

import backend.Book;
import backend.Customer;

public class DataController extends Database{
	private RecordManager   rm;
	private CustomerManager cm;
	private BookManager     bm;
	
	public DataController () throws Exception {
		this(new Credentials());
	}
	
	public DataController (Credentials creds) throws Exception {
		super(creds);
		rm = new RecordManager(this);
		cm = new CustomerManager(this);
		bm = new BookManager(this);
	}
	
	public CustomerManager customers() { return cm; }
	public RecordManager   records()   { return rm; }
	public BookManager     books()     { return bm; }
	
	// Customer handlers
	
	private void dbFlush() {
		try {
			rm.dropTable();
			cm.dropTable();
			bm.dropTable();
			cm.createTable();
			bm.createTable();
			rm.createTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean registerCustomer(Customer customer) {
		try {
			return cm.add(customer); 
		} 
		catch (IOException e) {
			System.out.println("[Controller, registerCustomer] ERROR: " + e.getMessage());
		}
		return false;
	}
	
	public boolean deregisterCustomer(String customerId) {
		return deregisterCustomer(new Customer(customerId, null, null, "0"));
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
			return bm.add(book);
		} catch (Exception e) {
			System.out.println("[DataController, addBook] ERROR: " + e.getMessage());
			return false;
		}
	}
	
	public boolean removeBook(int lid) {
		try {
			return bm.delete(lid);
		} catch (Exception e) {
			System.out.println("[DataController, removeBook] ERROR: " + e.getMessage());
			return false;
		}
	}
	
	public boolean removeRecord(int aid) {
		try {
			return rm.delete(aid);
		} catch (Exception e) {
			System.out.println("[DataController, removeBook] ERROR: " + e.getMessage());
			return false;
		}
	}	
}
