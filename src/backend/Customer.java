package backend;

import java.util.ArrayList;

public class Customer {
	final String END_OF_LINE = System.lineSeparator();
	private String customerId;
	private String name;
	private String address;
	private int phoneNumber;
	private ArrayList<Book> customerHistory;

	public Customer(String customerId) {
		this(customerId, "", "", 0);
	}
	
	public Customer(String customerId, String name, String address, int phoneNumber) {
		this.customerId = customerId;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public ArrayList<Book> getCustomerHistory() {
		return this.customerHistory;
	}
	
	public void setLid(String newLid) {
		this.customerId = newLid;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/*
	 * public void addToCustomerHistory(Book book) { boolean exists = false; for
	 * (Book s : this.customerHistory) { if (s.getLiid() == book.getLiid()) { exists
	 * = true; break; } } if (exists == false) { customerHistory.add(book); } }
	 */
	public boolean equals(Object otherObject) {

		if (otherObject instanceof Customer) {
			Customer otherCustomer = (Customer) otherObject;
			boolean result = getCustomerId() == otherCustomer.getCustomerId();
			return result;

		} else {
			return false;
		}
	}

	public String toString() {
		String result = "Name: " + getName() + END_OF_LINE;
		result += "Address: " + getAddress() + END_OF_LINE;
		result += "Phone Number: " + getPhoneNumber() + END_OF_LINE;
		result += "Library ID: " + getCustomerId();
		return result;
	}
}
