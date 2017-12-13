package backend;

public class Book {
	final String END_OF_LINE = System.lineSeparator();
	private int lid;
	private String title;
	private String isbn;
	private String publisher;
	private String genre;
	private String author;
	private String shelf;
	private int lentTimes;

	public Book(String isbn, String title, String genre, String author, String publisher, String shelf) {
		this(-1, isbn, title, genre, author, publisher, shelf);
	}
	public Book(int lid, String isbn, String title, String genre, String author, String publisher, String shelf) {
		this.setLid(lid);
		this.setTitle(title);
		this.setIsbn(isbn);
		this.setPublisher(publisher);
		this.setGenre(genre);
		this.setAuthor(author);
		this.setShelf(shelf);
	}

	public int getLid() {
		return lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getShelf() {
		return shelf;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

	public String toString() {
		String result = "LID: " + getLid() + END_OF_LINE;
		result += "Name: " + getTitle() + "." + END_OF_LINE;
		result += "ISBN-13: " + getIsbn() + "." + END_OF_LINE;
		result += "Author: " + getAuthor() + END_OF_LINE;
		result += "Genre: " + getGenre() + END_OF_LINE;
		result += "Publisher: " + getPublisher() + END_OF_LINE;
		result += "Shelf: " + getShelf() + END_OF_LINE;
		result += "Popularity: ";
		return result;
	}

	public int getLentTimes() {
		return lentTimes;
	}

	public void setLentTimes(int lentTimes) {
		this.lentTimes += lentTimes;
	}

}
