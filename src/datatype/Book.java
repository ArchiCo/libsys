package datatype;

import java.sql.Date;

public class Book {
	int liid;
	String isbn, title, genre, author, publisher, customerSsn, record = null;
	Date datePublished, dateTaken, dateDue = null;
	
	public Book(int    liid,
				String isbn, 
			    String title, 
			    String genre, 
			    String author, 
			    String publisher, 
			    Date datePublished) {
		
		this.liid          =          liid;
		this.isbn          =          isbn;
		this.title         =         title;
		this.genre         =         genre;
		this.author        =        author;
		this.publisher     =     publisher;
		this.datePublished = datePublished;	
	}
	
	public int    getLiid()          { return          liid; }
	public String getIsbn()          { return          isbn; }
	public String getTitle()         { return         title; }
	public String getGenre()         { return         genre; }
	public String getAuthor()        { return        author; }
	public String getPublisher()     { return     publisher; }
	public String getCustomerSsn()   { return   customerSsn; }
	
	
	public Date   getDatePublished() { return datePublished; }
	
	
	
}
