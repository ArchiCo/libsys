package database.structure.pattern;

import java.util.ArrayList;

import database.structure.Column;
import database.structure.Table;

public class Customers{
	final String NAME = "customers";
	ArrayList<Column> columns;
	
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
