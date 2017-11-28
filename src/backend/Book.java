package backend;

import java.sql.Date;

public class Book {
	final String END_OF_LINE = System.lineSeparator();
	private int liid;
	private String name;
	private String isbn;
	private String publisher;
	private String genre;
	private String author;
	private String shelf;
	private Date lendDate;
	private Date dueDate;
	private Date returnDate;
	private Customer lentCustomer;
	private int lendDuration;
	private int lentTimes;

	public Book(String name, String isbn, String publisher, String genre, String shelf, String author) {
		this.name = name;
		this.isbn = isbn;
		this.publisher = publisher;
		this.genre = genre;
		this.author = author;
		this.shelf = shelf;
		this.lentTimes = 0;
	}

	public int getLiid() {
		return this.liid;
	}

	public String getName() {
		return this.name;
	}

	public String getISBN() {
		return this.isbn;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public String getGenre() {
		return this.genre;
	}

	public String getShelf() {
		return this.shelf;
	}

	public String getAuthor() {
		return this.author;
	}

	public Customer getLentCustomer() {
		return this.lentCustomer;
	}

	public int getLendDuration() {
		return this.lendDuration;
	}

	public int getLentTimes() {
		return this.lentTimes;
	}

	public void bookPopularityUp() {
		this.lentTimes++;
	}

	public void setLentCustomer(Customer newCustomer) {
		this.lentCustomer = newCustomer;
	}

	public void setLendDuration(int duration) {
		this.lendDuration = duration;
	}

	public Date getLendDate() {
		return this.lendDate;
	}

	public Date getReturnDate() {
		return this.returnDate;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setLendDate(Date lendDate) {
		this.lendDate = lendDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String toString() {
		String result = "Name: " + getName() + "." + END_OF_LINE;
		result += "ISBN-13: " + getISBN() + "." + END_OF_LINE;
		result += "Author: " + getAuthor() + END_OF_LINE;
		result += "Genre: " + getGenre() + END_OF_LINE;
		result += "Publisher: " + getPublisher() + END_OF_LINE;
		result += "Shelf: " + getShelf();
		return result;
	}
}
