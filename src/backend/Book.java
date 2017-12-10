package backend;

import java.beans.Customizer;
import java.sql.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
	
	final String END_OF_LINE = System.lineSeparator();

	private SimpleStringProperty ID;
	private SimpleStringProperty isbn;
	private SimpleStringProperty title;
	private SimpleStringProperty author;
	private SimpleStringProperty shelf;
	private SimpleStringProperty publisher;
	private SimpleStringProperty genre;
	private ObjectProperty<Date> lendDate;
	private ObjectProperty<Date> dueDate;
	private ObjectProperty<Date> returnDate;
	private ObjectProperty<Customer> lentCustomer;
	private SimpleIntegerProperty lendDuration;
	private SimpleIntegerProperty lentTimes;
	
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
	
	//ISBN
	public SimpleStringProperty isbnProperty() {
		return this.isbn;
	}
	public String getIsbn() {
		return this.isbnProperty().get();
	}
	public void setIsbn(final String newIsbn) {
		this.isbnProperty().set(newIsbn);
	}
	
	//Due Date
	public ObjectProperty<Date> dueDateProperty() {
		return this.dueDate;
	}
	public Date getDueDate() {
		return this.dueDateProperty().get();
	}
	public void setDueDate(final Date dueDate) {
		this.dueDateProperty().set(dueDate);
	}
	
	//Return Date
	public ObjectProperty<Date> returnDateProperty() {
		return this.returnDate;
	}
	public Date getReturnDate() {
		return this.returnDateProperty().get();
	}
	public void setReturnDate(final Date returnDate) {
		this.returnDateProperty().set(returnDate);
	}
	
	///////////////The getters and setters below need 1 more method each////////////
	public ObjectProperty<Date> getLendDate() {
		return lendDate;
	}
	public void setLendDate(ObjectProperty<Date> lendDate) {
		this.lendDate = lendDate;
	}
	
	public Customer getLentCustomer() {
		return this.lentCustomer.get();
	}

	public int getLendDuration() {
		return this.lendDuration.get();
	}

	public int getLentTimes() {
		return this.lentTimes.get();
	}

	public void resetLentTimes() {
		this.lentTimes.set(0);
	}

	public void setLentTimes(int newLent) {
		this.lentTimes.set(newLent); 
	}

	public void bookPopularityUp() {
		this.lentTimes.set(lentTimes.get() + 1);
	}

	public void setLentCustomer(Customer newCustomer) {
		this.lentCustomer.set(newCustomer);
	}

	public void setLendDuration(int duration) {
		this.lendDuration.set(duration);
	}

	public String toString() {
		String result = "Title: " + getTitle() + "." + END_OF_LINE;
		result += "ISBN-13: " + getIsbn() + "." + END_OF_LINE;
		result += "Author: " + getAuthor() + END_OF_LINE;
		result += "Genre: " + getGenre() + END_OF_LINE;
		result += "Publisher: " + getPublisher() + END_OF_LINE;
		result += "Shelf: " + getShelf() + END_OF_LINE;
		result += "Popularity" + getLentTimes();
		return result;
	}
	

	

	
}
