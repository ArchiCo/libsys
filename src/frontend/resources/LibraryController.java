package frontend.resources;

import frontend.*;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import backend.*;
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

//ayy so compact and cozy
	//Book buttons
	@FXML private Button logoutBtn;
	@FXML private Button lendBookBtn;
	
	//Customer buttons
	@FXML private Button editCustomerBtn;
	@FXML private Button addCustomerBtn;
	@FXML private Button addBookBtn;
	@FXML private Button editBookBtn;
	@FXML private Button returnBookBtn;
	//book directory table column initialize
	@FXML private TableView<Book> bookTable;
	@FXML private TableColumn<Book, String> bookIDCol;
	@FXML private TableColumn<Book, String> bookTitleCol;
	@FXML private TableColumn<Book, String> bookAuthorCol;
	@FXML private TableColumn<Book, String> bookShelfCol;
	@FXML private TableColumn<Book, String> bookPublisherCol;
	@FXML private TableColumn<Book, String> bookGenreCol;
	
	//customer directory table
	@FXML private TableView<Customer> customerTable;
	@FXML private TableColumn <Customer,String> customerIDCol;
	@FXML private TableColumn <Customer,String> customerNameCol;
	
	//search textfields
	@FXML private TextField IDFilterField;
	@FXML private TextField titleFilterField;
	@FXML private TextField authorFilterField;
	@FXML private TextField shelfFilterField;
	@FXML private TextField publisherFilterField;
	@FXML private TextField genreFilterField;
	@FXML private TextField custNameField;
	
	//Book details
	@FXML private Label LIDLabel;
	@FXML private Label ISBNLabel;
	@FXML private Label titleLabel;
	@FXML private Label authorLabel;
	@FXML private Label genreLabel;
	@FXML private Label publisherLabel; 
	@FXML private Label publicationDateLabel;
	
	private LibraryMenu libraryMenu;
	
	//list of books arraylist
	ObservableList<Book> books = FXCollections.observableArrayList();
	ObservableList<Customer> customers = FXCollections.observableArrayList();
	
	
	public LibraryController(LibraryMenu libraryMenu) {
	
		this.libraryMenu = libraryMenu;
		
		books.add(new Book("A1", "Dragons", "Nigel", "11A", "Longmaen", "revolutionary"));
		/*books.add(new Book("A2", "bumblebee", "God", "2AB", "Penguin",	"Religion"));
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
		books.add(new Book("A1", "Dragons", "Nigel", "11A", "Longmaen", "revolutionary"));
		books.add(new Book("asd", "porcodio","Salvatore","43B","Hey","fable"));
		
		customers.add(new Customer("100","Salvatore","street 1",400));
		customers.add(new Customer("101","nigel","korsvagen",432432));
		customers.add(new Customer("102","idontknow","via sassari 8",12121));
		customers.add(new Customer("103","newname","yeeeea",5005043));
		customers.add(new Customer("104","heeeey","stora ringvagen",32190));
		customers.add(new Customer("1111", "Damn", "Next Door", 1029435));
		customers.add(new Customer("2222", "Egg", "Over there", 3959591));
		*/
	}
	
	public LibraryController() {
	}

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 0. Initialize the columns
//The setCellValueFactory(...) that we set on the table columns are used to determine which field
//inside the Book objects should be used for the particular column.
		//if using ints and stuff, asObject() needs to be added after getproperty()
		LibraryMenu libraryMenu = new LibraryMenu();
		
		bookIDCol.setCellValueFactory(cellData -> cellData.getValue().getIDProperty());
		bookTitleCol.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
		bookAuthorCol.setCellValueFactory(cellData -> cellData.getValue().getAuthorProperty());
		bookShelfCol.setCellValueFactory(cellData -> cellData.getValue().getShelfProperty());
		bookPublisherCol.setCellValueFactory(cellData -> cellData.getValue().getPublisherProperty());
		bookGenreCol.setCellValueFactory(cellData -> cellData.getValue().getGenreProperty());
		
		customerIDCol.setCellValueFactory(cellData -> cellData.getValue().getLIDProperty());
		customerNameCol.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		
		
		////////////////////////////////SEARCH FUNCTION BOOK TABLE///////////////////////////
		ObjectProperty<Predicate<Book>> IDFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> titleFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> authorFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> shelfFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> publisherFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> genreFilter = new SimpleObjectProperty<>();
		
		ObjectProperty<Predicate<Customer>> custNameFilter = new SimpleObjectProperty<>();
		
		custNameFilter.bind(Bindings.createObjectBinding(() ->
		customer -> customer.getName().toLowerCase().contains(custNameField.getText().toLowerCase()),
				custNameField.textProperty()));
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
		FilteredList<Book> filteredBooks = new FilteredList<>(libraryMenu.getBooks(), p -> true);
		FilteredList<Customer> filteredCustomers = new FilteredList<>(libraryMenu.getCustomers(), p -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		//bind the filters to each other so the predicates of each are put into 1
		filteredBooks.predicateProperty().bind(Bindings.createObjectBinding(() ->
				IDFilter.get().and(titleFilter.get().and(authorFilter.get().and(shelfFilter.get()
						.and(publisherFilter.get().and(genreFilter.get()))))),
				IDFilter, titleFilter, authorFilter, shelfFilter, publisherFilter, genreFilter));
		
		filteredCustomers.predicateProperty().bind(Bindings.createObjectBinding(() ->
		custNameFilter.get(),custNameFilter));
		
		
		//addlistener calls changelistener.changed
		IDFilterField.textProperty().addListener((observable,oldValue,newValue) -> {}) ;
		titleFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {});
		authorFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {});
		shelfFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {});
		publisherFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {});	
		genreFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {});
		
		custNameField.textProperty().addListener((observable,oldValue,newValue) -> {});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Book> sortedBooks = new SortedList<>(filteredBooks);
		SortedList<Customer> sortedCustomers= new SortedList<>(filteredCustomers);
		
		 // 4. Bind the SortedList comparator to the TableView comparator.
		sortedBooks.comparatorProperty().bind(bookTable.comparatorProperty());
		sortedCustomers.comparatorProperty().bind(customerTable.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		bookTable.setItems(sortedBooks);
		customerTable.setItems(sortedCustomers);
		
		//ObservableList(Arraylist) >> FilteredList >> Sortedlist(comparator bind) >> into table
////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////BOOK DETAILS////////////////////////
		
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
				else {
					LIDLabel.setText("");
					titleLabel.setText("");
					authorLabel.setText("");
					genreLabel.setText("");
					publisherLabel.setText("");
				}
			}
		});
		/////////////////////////////////////////////////////////////////////////////////
		
		
	}
	public void setLibraryMenu(LibraryMenu libraryMenu) {
		this.libraryMenu = libraryMenu;
	}
	
	
	@FXML
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
	@FXML
	private void logoutEvent(ActionEvent event) throws IOException {
		if(event.getSource().equals(logoutBtn) ) {
			Stage window;
			Parent registerParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene registerScene = new Scene(registerParent);
			window = (Stage) logoutBtn.getScene().getWindow();
			window.setScene(registerScene);
			window.show();
		}

	}
	@FXML
	private void editCustomerEvent(ActionEvent event) throws IOException {
		if(event.getSource().equals(editCustomerBtn) ) {
			
			Parent root = FXMLLoader.load(getClass().getResource("EditCustomer.fxml"));
			Scene scene = new Scene(root);
			Stage window = new Stage ();
			window.setScene(scene);
			window.show();
		}
	}
	@FXML
	private void addCustomerEvent(ActionEvent event) throws IOException {
		 
		 
		if(event.getSource().equals(addCustomerBtn) ) {
			
			Parent root = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
			Scene scene = new Scene(root);
			Stage window = new Stage ();
			window.setScene(scene);
			window.show();
		}
	}
	@FXML
	private void addBookEvent(ActionEvent event) throws IOException {
		 
		if(event.getSource().equals(addBookBtn)) {
			
			Parent root = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
			Scene scene = new Scene(root);
			Stage window = new Stage ();
			window.setScene(scene);
			window.show();
			
		}
	}
	@FXML
	private void editBookEvent(ActionEvent event) throws IOException {
		 
		if(event.getSource().equals(editBookBtn)) {
			
			Parent root = FXMLLoader.load(getClass().getResource("EditBook.fxml"));
			Scene scene = new Scene(root);
			Stage window = new Stage ();
			window.setScene(scene);
			window.show();
		}
	}
	@FXML
	public void showBookDetails(MouseEvent event) {
		
	}

	
	
	
	
	
	
	
	

}
