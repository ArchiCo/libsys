package datatype;

import java.sql.Date;
import java.util.ArrayList;

public class Book{
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
	
	public Book(int lid,
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
	
	public int getLid()                   { return lid; }
	public String getIsbn()               { return isbn; }
	public String getGenre()              { return genre; }
	public String getShelf()              { return shelf; }
	public String getTitle()              { return title; }
	public String getAuthor()             { return author; }
	public ArrayList<Record> getRecords() { return records; }
	public String getPublisher()          { return publisher; }
	public Date   getPublicationDate()    { return publicationDate; }
	public String getPublicationDateTxt() { 
		if (publicationDate == null) {
			return null;
		}
		return publicationDate.toString(); 
	}
	
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
		publicationDate = newDatePublished;
	}
	
	public String getPrintout(Book book) {
		String bookMeta = "LiD: "       + book.getLid()       + "\n" +
		                  "ISBN: "      + book.getIsbn()      + "\n" +
		                  "Title: "     + book.getTitle()     + "\n" +
		                  "Genre: "     + book.getGenre()     + "\n" +
		                  "Author: "    + book.getAuthor()    + "\n" +
                          "Publisher: " + book.getPublisher() + "\n" +
		                  "Publication Date: " + book.getPublicationDateTxt() + "\n" + 
		                  "Shelf: "     + book.getShelf();
		
		
		if (book.getRecords() != null && book.getRecords().size() > 0) {
		    String bookRecords = "Records:\n";
	        for (int i = 0; i < records.size(); i++) {
	    	   bookRecords +=  "Record #" + i +
	    			           "| ArchiveID: " + book.getRecords().get(i).getAid() +
	    			           "| Username: "  + book.getRecords().get(i).getCid() +
	    			           "| LiD: "       + book.getRecords().get(i).getLid() +
	    			           "| Taken: "     + book.getRecords().get(i).getTakenDateString() +
	    			           "| Due: "       + book.getRecords().get(i).getDueDateString() +
	    			           "| Returned:"   + book.getRecords().get(i).getReturnedDateString();
	    	   if (i < records.size()-1) {
	    		   bookRecords += "\n";
	    	   }
		    }
	        return bookMeta + "\n" + bookRecords;
		}
		
		return bookMeta + "\nRecords: Empty.";
	}
}

