package database.structure.pattern;

import java.util.ArrayList;

import database.structure.Column;
import database.structure.Table;
import database.structure.operand.Syntax;

public class Books{
	final String NAME = "books";
	ArrayList<Column> columns;
	public Books() throws Exception {
			columns = new ArrayList<Column>();
			columns.add(new Column(           "lid" , "bigint", true));
			columns.add(new Column(          "isbn" , "varchar"  ));
			columns.add(new Column(         "title" , "text"     ));
			columns.add(new Column(         "genre" , "text"     ));
			columns.add(new Column(        "author" , "text"     ));
			columns.add(new Column(     "publisher" , "text"     ));
			columns.add(new Column("date_published" , "date"     ));
			columns.add(new Column(         "shelf" , "text"     ));	
		}
	
	public String getName() {
		return NAME;
	}
	
	public ArrayList<Column> getColumns() {
		return columns;
	}
}
