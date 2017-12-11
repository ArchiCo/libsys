package backend;

import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {
	
	final String END_OF_LINE = System.lineSeparator();

	private SimpleStringProperty LID;
	private SimpleStringProperty name;
	private SimpleStringProperty address;
	private SimpleIntegerProperty phoneNum;
	
	private ObservableList<Book> customerHistory;
	
	public Customer(String LID, String name, String address, int phoneNum) {
		
		customerHistory = FXCollections.observableArrayList();
		
		this.LID = new SimpleStringProperty(LID);
		this.name = new SimpleStringProperty(name);
		this.address = new SimpleStringProperty(address);
		this.phoneNum = new SimpleIntegerProperty(phoneNum);
		
	}
	
	//LID
	public String getLID() {
		return LID.get();
	}
	public StringProperty getLIDProperty() {
		return LID;
	}
	public void setLID(String newLID) {
		LID.set(newLID);
	}
	
	//name
	public String getName() {
		return name.get();
	}
	public StringProperty getNameProperty() {
		return name;
	}
	public void setNAme(String newName) {
		name.set(newName);
	}
	
	//address
	public String getAddress() {
		return address.get();
	}
	public StringProperty getAddressProperty() {
		return address;
	}
	public void setAddress(String newAddress) {
		address.set(newAddress);
	}
	
	//phone
	public int getPhoneNum() {
		return phoneNum.get();
	}
	public IntegerProperty getPhoneNumProperty() {
		return phoneNum;
	}
	public void setPhoneNum(int newPhoneNum) {
		phoneNum.set(newPhoneNum);
	}
	
	public ObservableList<Book> getCustomerHistory() {
		return this.customerHistory;
	}

	public void addToCustomerHistory(Book book) {
		boolean exists = false;
		for (Book s : this.customerHistory) {
			if (s.getID() == book.getID()) {
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
			boolean result = this.getLID() == otherCustomer.getLID();
			return result;

		} else {
			return false;
		}
	}

	public String toString() {
		String result = "Name: " + getName() + "." + END_OF_LINE;
		result += "Address: " + getAddress() + "." + END_OF_LINE;
		result += "Phone Number: " + getPhoneNum() + END_OF_LINE;
		result += "Library ID: " + getLID();
		return result;
	}
	
}
