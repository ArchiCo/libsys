package datatype;

import java.sql.Date;
import java.util.ArrayList;

public class Book {
	int lid;
	String isbn, title, genre, author, publisher, shelf;
	Date publicationDate;
	ArrayList<Record> records;
	
	public Book(int lid, String isbn, String title, String genre, 
		        String author,  String publisher, Date publicationDate) {
		
		this(lid, isbn, title, genre, author, publisher, publicationDate, "");
	}
	
	public Book(int lid, String isbn, String title, String genre, 
			    String author,  String publisher, Date publicationDate,
			    String shelf) {
		
		this(lid, isbn, title, genre, author, publisher, 
			 publicationDate, shelf, new ArrayList<Record>());
	}
	
	public Book(int    lid,
				String isbn, 
			    String title, 
			    String genre, 
			    String author, 
			    String publisher, 
			    Date   publicationDate,
			    String shelf,
			    ArrayList<Record> records) {
		
		this.lid             =             lid;
		this.isbn            =            isbn;
		this.title           =           title;
		this.genre           =           genre;
		this.author          =          author;
		this.publisher       =       publisher;
		this.publicationDate = publicationDate;
		this.shelf           =           shelf;
		this.records         =         records;
	}
	
	public int    getLid()                { return lid; }
	public String getIsbn()               { return isbn; }
	public String getGenre()              { return genre; }
	public String getShelf()              { return shelf; }
	public String getTitle()              { return title; }
	public String getAuthor()             { return author; }
	public ArrayList<Record> getRecords() { return records; }
	public String getPublisher()          { return publisher; }
	public Date   getPublicationDate()    { return publicationDate; }	

	
}
