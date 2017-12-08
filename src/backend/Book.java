package backend;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
	final String END_OF_LINE = System.lineSeparator();
	private SimpleStringProperty ID;
	private SimpleStringProperty title;
	private SimpleStringProperty author;
	private SimpleStringProperty shelf;
	private SimpleStringProperty publisher;
	private SimpleStringProperty genre;
	private int liid;
	private String name;
	private String isbn;
	private Customer lentCustomer;
	private int lendDuration;
	private int lentTimes;

	
	public Book(String ID, String title, String author, String shelf, String publisher, String genre) {
		this.ID = new SimpleStringProperty(ID);
		this.title = new SimpleStringProperty(title);
		this.author = new SimpleStringProperty(author);
		this.shelf = new SimpleStringProperty(shelf);
		this.publisher = new SimpleStringProperty(publisher);
		this.genre = new SimpleStringProperty(genre);
		
	}
	//ID
	public String getID() {
		return ID.get();
	}
	public StringProperty getIDProperty() {
		return ID;
	}
	public void setID(String newID) {
		ID.set(newID);
	}
	
	//Title
	public String getTitle() {
		return title.get();
	}
	public StringProperty getTitleProperty() {
		return title;
	}
	public void setTitle(String newTitle) {
		title.set(newTitle);
	}
	
	//Author
	public String getAuthor() {
		return author.get();
	}
	public StringProperty getAuthorProperty() {
		return author;
	}
	public void setAuthor(String newAuthor) {
		author.set(newAuthor);
	}
	
	//shelf
	public String getShelf() {
		return shelf.get();
	}
	public StringProperty getShelfProperty() {
		return shelf;
	}
	public void setShelf(String newShelf) {
		shelf.set(newShelf);
	}
	
	//Publisher
	public String getPublisher() {
		return publisher.get();
	}
	public StringProperty getPublisherProperty() {
		return publisher;
	}
	public void setPublisher(String newPub) {
		publisher.set(newPub);
	}
	
	//genre
	public String getGenre() {
		return genre.get();
	}
	public StringProperty getGenreProperty() {
		return genre;
	}
	public void setGenre(String newGenre) {
		genre.set(newGenre);
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

	public Customer getLentCustomer() {
		return this.lentCustomer;
	}

	public int getLendDuration() {
		return this.lendDuration;
	}

	public int getLentTimes() {
		return this.lentTimes;
	}

	public void resetLentTimes() {
		this.lentTimes = 0;
	}

	public void setLentTimes(int newLent) {
		this.lentTimes = newLent;
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

	public String toString() {
		String result = "Name: " + getName() + "." + END_OF_LINE;
		result += "ISBN-13: " + getISBN() + "." + END_OF_LINE;
		result += "Author: " + getAuthor() + END_OF_LINE;
		result += "Genre: " + getGenre() + END_OF_LINE;
		result += "Publisher: " + getPublisher() + END_OF_LINE;
		result += "Shelf: " + getShelf() + END_OF_LINE;
		result += "Popularity" + getLentTimes();
		return result;
	}


}
