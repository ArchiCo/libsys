package database.structure.pattern;

import java.util.ArrayList;

import database.structure.Column;
import database.structure.Table;
import database.structure.operand.Syntax;

public class Books{
	final String NAME = "books";
	ArrayList<Column> columns;
	
	// Default books table constant
	public int BOOKS_LID_COLUMN              = 1;
	public int BOOKS_ISBN_COLUMN             = 2;
	public int BOOKS_TITLE_COLUMN            = 3;
	public int BOOKS_GENRE_COLUMN            = 4;
	public int BOOKS_AUTHOR_COLUMN           = 5;
	public int BOOKS_PUBLISHER_COLUMN        = 6;
	public int BOOKS_PUBLICATION_DATE_COLUMN = 7;
	public int BOOKS_SHELF_COLUMN            = 8;
	
	public Books() throws Exception {
			columns = new ArrayList<Column>();
			columns.add(new Column(             "lid" , "text", true));
			columns.add(new Column(            "isbn" , "text"     ));
			columns.add(new Column(           "title" , "text"     ));
			columns.add(new Column(           "genre" , "text"     ));
			columns.add(new Column(          "author" , "text"     ));
			columns.add(new Column(       "publisher" , "text"     ));
			columns.add(new Column("publication_date" , "date"     ));
			columns.add(new Column(           "shelf" , "text"     ));	
		}
	
	public String getName() {
		return NAME;
	}
	
	public ArrayList<Column> getColumns() {
		return columns;
	}
}
