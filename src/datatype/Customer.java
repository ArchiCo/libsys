package datatype;

import java.sql.Date;
import java.util.ArrayList;

public class Customer{
	String cid, password, ssn, name, surname, address, phone;
	

	public Customer( String  cid,    String ssn,
                     String name,    String surname,
                     String address, String phone) {
	
	this(cid, "", ssn, name, surname, address, phone);
}
	
	public Customer( String cid,     String password,
			         String ssn,     String name,
                     String surname, String address,
                     String phone) {
		
		this.cid      =      cid;
		this.password = password;
		this.ssn      =      ssn;
		this.name     =     name;
		this.surname  =  surname;
		this.address  =  address;
		this.phone    =    phone;
	}
	
	public String getCid()                { return cid; }
	public String getPassword()           { return password; }
	public String getSsn()                { return ssn; }
	public String getName()               { return name; }
	public String getSurname()            { return surname; }
	public String getAddress()            { return address; }
	public String getPhone()              { return phone; }
}

