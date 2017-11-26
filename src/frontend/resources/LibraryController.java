package frontend.resources;

import frontend.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

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
	private Button logoutBtn;
	@FXML
	private Button searchBtn;
	@FXML
	private Button addBookBtn;
	@FXML
	private Button editBookBtn;
	@FXML
	private Button lendBookBtn;
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
	 
	
	ObservableList<Book> books = FXCollections.observableArrayList();
	
	
	public LibraryController() {
		
		books.add(new Book("A1", "Dragons", "Nigel", "11A", "Longmaen", "revolutionary"));
		books.add(new Book("A2", "bumblebee", "God", "2AB", "Penguin",	"Religion"));
		books.add(new Book("B2", "rawr", "hemp", "1337A", "Bleh", "New Age"));
		books.add(new Book("G201", "Help meh", "Salvatore", "The Forbidden One", "The Illegal One", "Self Halp"));
		books.add(new Book("T65", "Salva-me", "Salvatore", "Shelf of Help", "Help em All", "Troll"));
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
		
		// 1. Wrap the ObservableList in a FilteredList (initially display all data) 	
		FilteredList<Book> filteredBooks = new FilteredList<>(books, p -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
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
