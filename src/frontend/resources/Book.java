package frontend.resources;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {

	private SimpleStringProperty ID;
	private SimpleStringProperty title;
	private SimpleStringProperty author;
	private SimpleStringProperty shelf;
	private SimpleStringProperty publisher;
	private SimpleStringProperty genre;
	
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
	
}
