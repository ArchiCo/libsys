package backend;

import java.util.ArrayList;

public class Book {
	final String END_OF_LINE = System.lineSeparator();
	private int liid;
	private String title;
	private String isbn;
	private String publisher;
	private String genre;
	private String author;
	private String shelf;
	private ArrayList<Records> records;

	public Book(String name, String isbn, String publisher, String genre, String shelf, String author) {
		setRecords(new ArrayList<Records>());
		this.title = title;
		this.isbn = isbn;
		this.publisher = publisher;
		this.genre = genre;
		this.author = author;
		this.shelf = shelf;
	}

	public int getLiid() {
		return this.liid;
	}

	public String getTitle() {
		return this.title;
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

	public String toString() {
		String result = "Name: " + getTitle() + "." + END_OF_LINE;
		result += "ISBN-13: " + getISBN() + "." + END_OF_LINE;
		result += "Author: " + getAuthor() + END_OF_LINE;
		result += "Genre: " + getGenre() + END_OF_LINE;
		result += "Publisher: " + getPublisher() + END_OF_LINE;
		result += "Shelf: " + getShelf();
		return result;
	}

	public ArrayList<Records> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<Records> records) {
		this.records = records;
	}
}
