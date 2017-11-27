package database.structure.pattern;

import java.util.ArrayList;

import database.structure.Column;
import database.structure.Table;

public class Records extends Table{
	final String NAME = "records";
	ArrayList<Column> columns;
	
	public Records() throws Exception {
		super();
			columns = new ArrayList<Column>();
			columns.add(new Column(   "archive_id" ,    "bigint", true));
			columns.add(new Column(     "username" ,      "text"));
			columns.add(new Column(          "lid" ,    "bigint"));
			columns.add(new Column(   "date_taken" , "timestamp"));
			columns.add(new Column(     "date_due" , "timestamp"));
			columns.add(new Column("date_returned" , "timestamp"));

		}
	
	public String getName() {
		return NAME;
	}
	
	public ArrayList<Column> getColumns() {
		return columns;
	}
	
}
