package frontend.resources;

import frontend.*;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
	
	//Book details
	@FXML 
	private Label LIDLabel;
	@FXML 
	private Label ISBNLabel;
	@FXML 
	private Label titleLabel;
	@FXML 
	private Label authorLabel;
	@FXML 
	private Label genreLabel;
	@FXML 
	private Label publisherLabel;
	@FXML 
	private Label publicationDateLabel;
	
	//list of books arraylist
	ObservableList<Book> books = FXCollections.observableArrayList();
	ObservableList<Customer> customers = FXCollections.observableArrayList();
	
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
		
		customers.add(new Customer("1111", "Damn", "Next Door", 1029435));
		customers.add(new Customer("2222", "Egg", "Over there", 3959591));
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
		
		////////////////////////////////SEARCH FUNCTION BOOK TABLE///////////////////////////
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
		IDFilterField.textProperty().addListener((observable,oldValue,newValue) -> {}) ;
		
		titleFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {});
		
		authorFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {});
		
		shelfFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {});
		
		publisherFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {});
		
		genreFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Book> sortedBooks = new SortedList<>(filteredBooks);
		
		 // 4. Bind the SortedList comparator to the TableView comparator.
		sortedBooks.comparatorProperty().bind(bookTable.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		bookTable.setItems(sortedBooks);
		//ObservableList(Arraylist) >> FilteredList >> Sortedlist(comparator bind) >> into table
////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////Details section of book////////////////////////
		
		//---Apparently getselectionmodel is not as good as gettablerow()--- do i care
		Book chosenBook;
		chosenBook = bookTable.getSelectionModel().selectedItemProperty().get();
		bookTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Book>() {

			@Override
			public void changed(ObservableValue<? extends Book> observable, Book oldValue, Book newValue) {
				if (newValue != null) {
					LIDLabel.setText(newValue.getID());
					titleLabel.setText(newValue.getTitle());
					authorLabel.setText(newValue.getAuthor());
					genreLabel.setText(newValue.getGenre());
					publisherLabel.setText(newValue.getPublisher());
				}
			}
		});
		//------------------------------------------------------------------------//
		
		
		
		
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
	
	public void showBookDetails(MouseEvent event) {
		
	}
	
	
	
	
	
	
	
	

}
