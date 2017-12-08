package backend;

import java.util.ArrayList;

public class Customer {
	final String END_OF_LINE = System.lineSeparator();
	private String username;
	private String password;
	private String ssn;
	private String name;
	private String address;
	private int phoneNumber;
	private ArrayList<Book> customerHistory;

	public Customer(String username, String password, String ssn, String name, String address, int phoneNumber) {
		customerHistory = new ArrayList<Book>();
		this.setUsername(username);
		this.setPassword(password);
		this.setSsn(ssn);
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
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
			boolean result = this.getUsername() == otherCustomer.getUsername();
			return result;

		} else {
			return false;
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String toString() {
		String result = "Name: " + getName() + "." + END_OF_LINE;
		result += "Address: " + getAddress() + "." + END_OF_LINE;
		result += "Phone Number: " + getPhoneNumber() + END_OF_LINE;
		result += "ssn: " + getSsn();
		return result;
	}
}
