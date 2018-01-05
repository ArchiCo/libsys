package datatype;

import java.util.ArrayList;

public class Customer {
	final String END_OF_LINE = System.lineSeparator();
	private String cid, name, address, phone;
	private ArrayList<Book> history;

	public Customer(String cid) {
		this(cid, "", "", "0");
	}
	
	public Customer(String cid, String name, String address, String phoneNumber) {
		this.cid     = cid;
		this.name    = name;
		this.address = address;
		this.phone   = phoneNumber;
	}
	// Getters
	public String          getCid()     { return     cid; }
	public String          getName()    { return    name; }
	public String          getAddress() { return address; }
	public String          getPhone()   { return   phone; }
	public ArrayList<Book> getHistory() { return history; }
	// Setters
	public void setCid    (String var) { cid     = var; }
	public void setName   (String var) { name    = var; }
	public void setAddress(String var) { address = var; }
	public void setPhone  (String var) { phone   = var; }
	
	public boolean equals(Object otherObject) {
		if (otherObject instanceof Customer) {
			Customer otherCustomer = (Customer) otherObject;
			boolean result = (cid == otherCustomer.getCid());
			return result;
		} else {
			return false;
		}
	}

	public String toString() {
		String result = "";
	    result += "Name: "         + getName()    + END_OF_LINE;
		result += "Address: "      + getAddress() + END_OF_LINE;
		result += "Phone Number: " + getPhone()   + END_OF_LINE;
		result += "Library ID: "   + getCid()     + END_OF_LINE;
		return result;
	}
}
