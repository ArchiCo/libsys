package datatype;

import java.sql.Date;

public class Book{
	int liid;
	String isbn, title, genre, author, publisher, customerSsn = null;
	Date datePublished = null; 
	
	// Constructor for a new book;
	public Book
	(String isbn,
     String title,
     String genre,
     String author,
     String publisher,
     Date   datePublished) {
		
		
		this.isbn          =          isbn;
		this.title         =         title;
		this.genre         =         genre;
		this.author        =        author;
		this.publisher     =     publisher;
		this.datePublished = datePublished;	
	}
	
	public String getIsbn()          { return          isbn; }
	public String getTitle()         { return         title; }
	public String getGenre()         { return         genre; }
	public String getAuthor()        { return        author; }
	public String getPublisher()     { return     publisher; }
	public Date   getDatePublished() { return datePublished; }
	
	public void   setIsbn(String newIsbn) { 
		isbn = newIsbn; 
	}
	
	public void   setTitle(String newTitle) { 
		title = newTitle; 
	}
	public void   setGenre(String newGenre) { 
		genre = newGenre; 
	}
	public void   setAuthor(String newAuthor) { 
		author = newAuthor;
	}
	public void   setPublisher(String newPublisher) {
		publisher = newPublisher;
	}
	public void   setDatePublished(Date newDatePublished) {
		datePublished = newDatePublished;
	}
}

