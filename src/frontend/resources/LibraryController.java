package frontend.resources;

import frontend.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LibraryController implements Initializable{

	//Book buttons
	@FXML
	private Button logoutBtn, searchBtn, addBookBtn, editBookBtn, lendBookBtn;
	//Customer buttons
	@FXML
	private Button editCustomBtn, addCustomBtn, returnBookBtn, customerSearchBtn;
	//book directory table column initialize
	@FXML
	private TableView<Book> bookTable;
	@FXML
	private TableColumn<Book, String> IDCol;
	@FXML
	private TableColumn<Book, String> titleCol;
	@FXML
	private TableColumn<Book, String> authorCol;
	@FXML
	private TableColumn<Book, String> shelfCol;
	@FXML
	private TableColumn<Book, String> publisherCol;
	@FXML
	private TableColumn<Book, String> genreCol;
	
	
	//search textfields
	@FXML
	private TextField IDFilterField;
	@FXML
	private TextField titleFilterField;
	@FXML
	private TextField authorFilterField;
	@FXML
	private TextField shelfFilterField;
	@FXML
	private TextField publisherFilterField;
	@FXML
	private TextField genreFilterField;
	 
	//list of books arraylist
	ObservableList<Book> books = FXCollections.observableArrayList();
	
	public LibraryController() {
		
		books.add(new Book("A1", "Dragons", "Nigel", "11A", "Longmaen", "revolutionary"));
		books.add(new Book("A2", "bumblebee", "God", "2AB", "Penguin",	"Religion"));
		books.add(new Book("B2", "rawr", "hemp", "1337A", "Bleh", "New Age"));
		books.add(new Book("G201", "Help meh", "Salvatore", "The Forbidden One", "The Illegal One", "Self Halp"));
		books.add(new Book("T65", "Salva-me", "Salvatore", "Shelf of Help", "Help em All", "Troll"));
		books.add(new Book("A1", "A", "a", "1A", "A", "AAA"));
		books.add(new Book("A1", "B", "a", "1A", "A", "AAAb"));
		books.add(new Book("A14", "B", "ra", "12A", "A", "AAAb"));
		books.add(new Book("A13", "A", "ra", "12A", "A", "AAAc"));
		books.add(new Book("A12", "D", "sa", "13A", "A", "AAAc"));
		books.add(new Book("A12", "D", "sa", "13A", "A", "AAAd"));
		books.add(new Book("A2", "F", "ha", "1A", "A", "AAAd"));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 0. Initialize the columns
//The setCellValueFactory(...) that we set on the table columns are used to determine which field
//inside the Book objects should be used for the particular column.
		//if using ints and stuff, asObject() needs to be added after getproperty()
		IDCol.setCellValueFactory(cellData -> cellData.getValue().getIDProperty());
		titleCol.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
		authorCol.setCellValueFactory(cellData -> cellData.getValue().getAuthorProperty());
		shelfCol.setCellValueFactory(cellData -> cellData.getValue().getShelfProperty());
		publisherCol.setCellValueFactory(cellData -> cellData.getValue().getPublisherProperty());
		genreCol.setCellValueFactory(cellData -> cellData.getValue().getGenreProperty());
		
		ObjectProperty<Predicate<Book>> IDFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> titleFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> authorFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> shelfFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> publisherFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> genreFilter = new SimpleObjectProperty<>();
		
		IDFilter.bind(Bindings.createObjectBinding(() ->
			book -> book.getID().toLowerCase().contains(IDFilterField.getText().toLowerCase()),
				IDFilterField.textProperty()));
		
		titleFilter.bind(Bindings.createObjectBinding(() ->
			book -> book.getTitle().toLowerCase().contains(titleFilterField.getText().toLowerCase()),
				titleFilterField.textProperty()));
		
		authorFilter.bind(Bindings.createObjectBinding(() ->
			book -> book.getAuthor().toLowerCase().contains(authorFilterField.getText().toLowerCase()),
				authorFilterField.textProperty()));
		
		shelfFilter.bind(Bindings.createObjectBinding(() ->
		book -> book.getShelf().toLowerCase().contains(shelfFilterField.getText().toLowerCase()),
			shelfFilterField.textProperty()));
		
		publisherFilter.bind(Bindings.createObjectBinding(() ->
		book -> book.getPublisher().toLowerCase().contains(publisherFilterField.getText().toLowerCase()),
			publisherFilterField.textProperty()));
		
		genreFilter.bind(Bindings.createObjectBinding(() ->
		book -> book.getGenre().toLowerCase().contains(genreFilterField.getText().toLowerCase()),
			genreFilterField.textProperty()));
		
		// 1. Wrap the ObservableList in a FilteredList (initially display all data) 	
		FilteredList<Book> filteredBooks = new FilteredList<>(books, p -> true);
		
		filteredBooks.predicateProperty().bind(Bindings.createObjectBinding(() ->
				IDFilter.get().and(titleFilter.get().and(authorFilter.get().and(shelfFilter.get()
						.and(publisherFilter.get().and(genreFilter.get()))))),
				IDFilter, titleFilter, authorFilter, shelfFilter, publisherFilter, genreFilter));
		
		// 2. Set the filter Predicate whenever the filter changes.
		//addlistener calls changelistener.changed
		IDFilterField.textProperty().addListener((observable,oldValue,newValue) -> {
			 // If filter text is empty, display all persons.
			filteredBooks.setPredicate(book -> {
				if(newValue.equals(null) || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (book.getID().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false; //no matches found
			});
		}) ;
		
		titleFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {
			filteredBooks.setPredicate(books -> {
				if (newvalue.equals(null) || newvalue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newvalue.toLowerCase();
				if (books.getTitle().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});
		
		authorFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {
			filteredBooks.setPredicate(books -> {
				if (newvalue.equals(null) || newvalue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newvalue.toLowerCase();
				if (books.getAuthor().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});
		
		shelfFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {
			filteredBooks.setPredicate(books -> {
				if (newvalue.equals(null) || newvalue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newvalue.toLowerCase();
				if (books.getShelf().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});
		
		publisherFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {
			filteredBooks.setPredicate(books -> {
				if (newvalue.equals(null) || newvalue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newvalue.toLowerCase();
				if (books.getPublisher().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});
		
		genreFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {
			filteredBooks.setPredicate(books -> {
				if (newvalue.equals(null) || newvalue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newvalue.toLowerCase();
				if (books.getGenre().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Book> sortedBooks = new SortedList<>(filteredBooks);
		
		 // 4. Bind the SortedList comparator to the TableView comparator.
		sortedBooks.comparatorProperty().bind(bookTable.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		bookTable.setItems(sortedBooks);
		
		//ObservableList(Arraylist) >> FilteredList >> Sortedlist(comparator bind) >> into table 
	}
	
	public void handleButtonAction(ActionEvent event) throws IOException {
		Stage window;
		
		if (event.getSource().equals(logoutBtn)) {
			Parent parent = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene newScene = new Scene(parent);
			
			window = (Stage) (logoutBtn.getScene().getWindow());
			window.setScene(newScene);
			window.show();
		}
		
		
		
	}
	
	
	
	
	
	
	
	

}