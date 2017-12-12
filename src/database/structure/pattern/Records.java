package database.structure.pattern;

import java.util.ArrayList;

import database.structure.Column;
import database.structure.Table;

public class Records extends Table{
	final String NAME = "records";
	ArrayList<Column> columns;
	
	// Default records table constant
	public int RECORDS_ARCHIVE_ID_COLUMN    = 1;
	public int RECORDS_USERNAME_COLUMN      = 2;
	public int RECORDS_LID_COLUMN           = 3;
	public int RECORDS_DATE_TAKEN_COLUMN    = 4;
	public int RECORDS_DATE_DUE_COLUMN      = 5;
	public int RECORDS_DATE_RETURNED_COLUMN = 6;
	
	public Records() throws Exception {
		super();
			columns = new ArrayList<Column>();
			columns.add(new Column(      "id" , "int", true));
			columns.add(new Column(     "username" , "text"));
			columns.add(new Column(          "lid" , "text"));
			columns.add(new Column(   "date_taken" , "date"));
			columns.add(new Column(     "date_due" , "date"));
			columns.add(new Column("date_returned" , "date"));
		}
	
	public String getName() {
		return NAME;
	}
	
	public ArrayList<Column> getColumns() {
		return columns;
	}
	
}
