package backend;

import java.util.ArrayList;

public class Customer {
	final String END_OF_LINE = System.lineSeparator();
	private String libraryID;
	private String name;
	private String address;
	private int phoneNumber;
	private ArrayList<Book> customerHistory;

	public Customer(String libraryID, String name, String address, int phoneNumber) {
		customerHistory = new ArrayList<Book>();
		this.libraryID = libraryID;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public String getLibraryID() {
		return this.libraryID;
	}

	public String getName() {
		return this.name;
	}

	public String getAddress() {
		return this.address;
	}

	public int getPhoneNumber() {
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
