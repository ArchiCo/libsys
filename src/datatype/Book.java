package datatype;

public class Book {
	final String END_OF_LINE = System.lineSeparator();
	private int lid, lentTimes;
	private String isbn, title, author, genre, publisher, shelf;

	public Book(String isbn, String title, String genre, String author, String publisher, String shelf) {
		this(-1, isbn, title, genre, author, publisher, shelf);
	}
	public Book(int lid, String isbn, String title, String genre, String author, String publisher, String shelf) {
		this.lid       = lid;
		this.isbn      = isbn;
		this.title     = title;
		this.author    = author;
		this.genre     = genre;
		this.publisher = publisher;
		this.shelf     = shelf;
	}
	
	// Getters
	public int    getLid()       { return       lid; }
	public String getIsbn()      { return      isbn; }
	public String getTitle()     { return     title; }
	public String getAuthor()    { return    author; }	
	public String getGenre()     { return     genre; }
	public String getPublisher() { return publisher; }
	public String getShelf()     { return     shelf; }
	public int    getLentTimes() { return lentTimes; }
	// Setters
	public void setLid      (int    var) { lid        = var; }
	public void setIsbn     (String var) { isbn       = var; }
	public void setTitle    (String var) { title      = var; }
	public void setAuthor   (String var) { author     = var; }
	public void setGenre    (String var) { genre      = var; }
	public void setPublisher(String var) { publisher  = var; }
	public void setShelf    (String var) { shelf      = var; }
	public void setLentTimes(int    var) { lentTimes += var; }

	public String toString() {
		String result = "";
		result += "LID: "        + getLid()       + END_OF_LINE;
		result += "Name: "       + getTitle()     + END_OF_LINE;
		result += "ISBN-13: "    + getIsbn()      + END_OF_LINE;
		result += "Author: "     + getAuthor()    + END_OF_LINE;
		result += "Genre: "      + getGenre()     + END_OF_LINE;
		result += "Publisher: "  + getPublisher() + END_OF_LINE;
		result += "Shelf: "      + getShelf()     + END_OF_LINE;
		result += "Popularity: " + getLentTimes() + END_OF_LINE;
		return result;
	}
}
