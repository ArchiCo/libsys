package backend;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class Customer {
	final String END_OF_LINE = System.lineSeparator();
	private SimpleStringProperty libraryID;
	private SimpleStringProperty name;

	private String address;
	private String phoneNumber;
	private ArrayList<Book> customerHistory;

	public Customer(String libraryID, String name, String address, String phoneNumber) {
		customerHistory = new ArrayList<Book>();
		
		this.libraryID = new SimpleStringProperty(libraryID);
		this.name= new SimpleStringProperty(name);
		
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	
	
	//LIBRARY ID
	public String getLibraryID() {
		return libraryID.get();
	}
	public StringProperty getLibraryIDProperty() {
		return libraryID;
	}
	public void setLibraryID(String newLibraryID) {
		libraryID.set(newLibraryID);
	}
	
	
	//NAME
	public String getName() {
		return name.get();
	}
	public StringProperty getNameProperty() {
		return name;
	}
	public void setName(String newName) {
		libraryID.set(newName);
	}
	
	
	
	public String getAddress() {
		return this.address;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public ArrayList<Book> getCustomerHistory() {
		return this.customerHistory;
	}

	public void addToCustomerHistory(Book book) {
		boolean exists = false;
		for (Book s : this.customerHistory) {
			if (s.getLiid() == book.getLiid()) {
				exists = true;
				break;
			}

		}
		if (exists == false) {
			customerHistory.add(book);
		}
	}

	public boolean equals(Object otherObject) {

		if (otherObject instanceof Customer) {
			Customer otherCustomer = (Customer) otherObject;
			boolean result = this.getLibraryID() == otherCustomer.getLibraryID();
			return result;

		} else {
			return false;
		}
	}

	public String toString() {
		String result = "Name: " + getName() + "." + END_OF_LINE;
		result += "Address: " + getAddress() + "." + END_OF_LINE;
		result += "Phone Number: " + getPhoneNumber() + END_OF_LINE;
		result += "Library ID: " + getLibraryID();
		return result;
	}
}
