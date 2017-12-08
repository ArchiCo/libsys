package frontend.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import backend.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LibraryController implements Initializable{

	@FXML
	private Button logoutBtn;
	@FXML
	private Button editCustomerBtn;
	@FXML
	private Button addCustomerBtn;
	@FXML
	private Button addBookBtn;
	@FXML
	private Button editBookBtn;

	//book directory table column initialize
	@FXML
	private TableView<Book> bookTable;
	@FXML
	private TableColumn<Book, String> bookIDCol;
	@FXML
	private TableColumn<Book, String> bookTitleCol;
	@FXML
	private TableColumn<Book, String> bookAuthorCol;
	@FXML
	private TableColumn<Book, String> bookShelfCol;
	@FXML
	private TableColumn<Book, String> bookPublisherCol;
	@FXML
	private TableColumn<Book, String> bookGenreCol;
	
	//customers directory table and columns
	@FXML private TableView<Customer> customerTable;
	@FXML private TableColumn <Customer,String> customerIDCol;
	@FXML private TableColumn <Customer,String> customerNameCol;
	
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
	@FXML
	private TextField cstNameField;
	
	ObservableList<Book> books = FXCollections.observableArrayList();
	ObservableList<Customer> customers = FXCollections.observableArrayList();

	public LibraryController() {
		books.add(new Book("A1", "Dragons", "Nigel", "11A", "Longmaen", "revolutionary"));
		books.add(new Book("asd", "porcodio","Salvatore","43B","Hey","fable"));
		
		customers.add(new Customer("100","Salvatore","street 1",400));
		customers.add(new Customer("101","nigel","korsvagen",432432));
		customers.add(new Customer("102","idontknow","via sassari 8",12121));
		customers.add(new Customer("103","newname","yeeeea",5005043));
		customers.add(new Customer("104","heeeey","stora ringvagen",32190));

	

		
	}

 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 0. Initialize the columns
//The setCellValueFactory(...) that we set on the table columns are used to determine which field
//inside the Book objects should be used for the particular column.
		//if using ints and stuff, asObject() needs to be added after getproperty()
		bookIDCol.setCellValueFactory(cellData -> cellData.getValue().getIDProperty());
		bookTitleCol.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
		bookAuthorCol.setCellValueFactory(cellData -> cellData.getValue().getAuthorProperty());
		bookShelfCol.setCellValueFactory(cellData -> cellData.getValue().getShelfProperty());
		bookPublisherCol.setCellValueFactory(cellData -> cellData.getValue().getPublisherProperty());
		bookGenreCol.setCellValueFactory(cellData -> cellData.getValue().getGenreProperty());
		
		customerIDCol.setCellValueFactory(cellData -> cellData.getValue().getLibraryIDProperty());
		customerNameCol.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		
		
		
		ObjectProperty<Predicate<Customer>> cstNameFilter = new SimpleObjectProperty<>();

		
		ObjectProperty<Predicate<Book>> IDFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> titleFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> authorFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> shelfFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> publisherFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> genreFilter = new SimpleObjectProperty<>();
		
		cstNameFilter.bind(Bindings.createObjectBinding(() ->
		customer -> customer.getName().toLowerCase().contains(cstNameField.getText().toLowerCase()),
				cstNameField.textProperty()));
		
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
		FilteredList<Customer> filteredCustomers = new FilteredList<>(customers, p -> true);
		

		
		filteredBooks.predicateProperty().bind(Bindings.createObjectBinding(() ->
				IDFilter.get().and(titleFilter.get().and(authorFilter.get().and(shelfFilter.get()
						.and(publisherFilter.get().and(genreFilter.get()))))),
				IDFilter, titleFilter, authorFilter, shelfFilter, publisherFilter, genreFilter));
		
		filteredCustomers.predicateProperty().bind(Bindings.createObjectBinding(() ->
		cstNameFilter.get(),cstNameFilter));
		
		
		
		
		// 2. Set the filter Predicate whenever the filter changes.
		//addlistener calls changelistener.changed
		IDFilterField.textProperty().addListener((observable,oldValue,newValue) -> {
			 // If filter text is empty, display all persons.
			//filteredBooks.setPredicate(book -> {
				//if(newValue.equals(null) || newValue.isEmpty()) {
					//return true;
				//}
				//String lowerCaseFilter = newValue.toLowerCase();
				//if (book.getID().toLowerCase().contains(lowerCaseFilter)) {
					//return true;
				//}
				//return false; //no matches found
			//});
		}) ;
		cstNameField.textProperty().addListener((observable,oldValue,newValue) -> {
			
		});
		
		titleFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {
			//filteredBooks.setPredicate(books -> {
				//if (newvalue.equals(null) || newvalue.isEmpty()) {
					//return true;
				//}
				//String lowerCaseFilter = newvalue.toLowerCase();
				//if (books.getTitle().toLowerCase().contains(lowerCaseFilter)) {
					//return true;
				//}
				//return false;
			//});
		});
		
		authorFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {
		//	filteredBooks.setPredicate(books -> {
				//if (newvalue.equals(null) || newvalue.isEmpty()) {
					//return true;
				//}
				//String lowerCaseFilter = newvalue.toLowerCase();
				//if (books.getAuthor().toLowerCase().contains(lowerCaseFilter)) {
					//return true;
				//}
				//return false;
			//});
		});
		
		shelfFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {
			//filteredBooks.setPredicate(books -> {
				//if (newvalue.equals(null) || newvalue.isEmpty()) {
					//return true;
				//}
				//String lowerCaseFilter = newvalue.toLowerCase();
				//if (books.getShelf().toLowerCase().contains(lowerCaseFilter)) {
					//return true;
				//}
				//return false;
			//});
		});
		
		publisherFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {
			//filteredBooks.setPredicate(books -> {
				//if (newvalue.equals(null) || newvalue.isEmpty()) {
					//return true;
				//}
				//String lowerCaseFilter = newvalue.toLowerCase();
				//if (books.getPublisher().toLowerCase().contains(lowerCaseFilter)) {
					//return true;
				//}
				//return false;
			//});
		});
		
		genreFilterField.textProperty().addListener((observable,oldvalue,newvalue) -> {
			//filteredBooks.setPredicate(books -> {
				//if (newvalue.equals(null) || newvalue.isEmpty()) {
					//return true;
				//}
				//String lowerCaseFilter = newvalue.toLowerCase();
				//if (books.getGenre().toLowerCase().contains(lowerCaseFilter)) {
					//return true;
				//}
				//return false;
			//});
		});
		
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
	}
	
	 @FXML
	 private void handleButtonAction(ActionEvent event) throws IOException {
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

}
