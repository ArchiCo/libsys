package datatype;

import java.sql.Date;
import java.util.ArrayList;

public class LibraryBook extends Book{
	int liid;
	ArrayList<BookRecord> record = null;
	
	
	public LibraryBook(String isbn, String title, String genre, String author, String publisher, Date datePublished) {
		super(isbn, title, genre, author, publisher, datePublished);
		// TODO Auto-generated constructor stub
	}
	
	public int    getLiid()          { return      liid; }
	public void   setLiid(int liid)  { this.liid = liid; }
}
