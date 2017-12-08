package frontend.resources;

import com.sun.org.apache.bcel.internal.generic.I2D;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {

	private SimpleStringProperty LID;
	private SimpleStringProperty name;
	private SimpleStringProperty address;
	private SimpleIntegerProperty phoneNum;
	
	public Customer(String LID, String name, String address, int phoneNum) {
		this.LID = new SimpleStringProperty(LID);
		this.name = new SimpleStringProperty(name);
		this.address = new SimpleStringProperty(address);
		this.phoneNum = new SimpleIntegerProperty(phoneNum);
		
	}
	
	//LID
	public String getLID() {
		return LID.get();
	}
	public StringProperty getLIDProp() {
		return LID;
	}
	public void setLID(String newLID) {
		LID.set(newLID);
	}
	
	//name
	public String getName() {
		return name.get();
	}
	public StringProperty getNameProp() {
		return name;
	}
	public void setNAme(String newName) {
		name.set(newName);
	}
	
	//address
	public String getAddress() {
		return address.get();
	}
	public StringProperty getAddressProp() {
		return address;
	}
	public void setAddress(String newAddress) {
		address.set(newAddress);
	}
	
	//phone
	public int getPhoneNum() {
		return phoneNum.get();
	}
	public IntegerProperty getPhoneNumProp() {
		return phoneNum;
	}
	public void setPhoneNum(int newPhoneNum) {
		phoneNum.set(newPhoneNum);
	}
	
}
