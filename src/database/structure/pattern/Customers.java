package database.structure.pattern;

import java.util.ArrayList;

import database.structure.Column;
import database.structure.Table;

public class Customers{
	final String NAME = "customers";
	ArrayList<Column> columns;
	
	// Default customer table constant
	int CUSTOMER_USERNAME_COLUMN = 1;
	int CUSTOMER_PASSWORD_COLUMN = 2;
	int CUSTOMER_SSN_COLUMN      = 3;
	int CUSTOMER_NAME_COLUMN     = 4;
	int CUSTIMER_SURNAME_COLUMN  = 5;
	int CUSTOMER_ADDRESS_COLUMN  = 6;
	int CUSTOMER_PHONE_COLUMN    = 7;
	
	public Customers() throws Exception {
		super();
			columns = new ArrayList<Column>();
			columns.add(new Column("username" , "text", true));
			columns.add(new Column("password" , "text"));
			columns.add(new Column(     "ssn" , "text"));
			columns.add(new Column(    "name" , "text"));
			columns.add(new Column( "surname" , "text"));
			columns.add(new Column( "address" , "text"));
			columns.add(new Column(   "phone" , "text"));
		}
	
	public String getName() {
		return NAME;
	}
	
	public ArrayList<Column> getColumns() {
		return columns;
	}
		
}
