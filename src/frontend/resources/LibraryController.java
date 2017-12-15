package frontend.resources;

import frontend.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import backend.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.util.Optional;
import javafx.util.Pair;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;

public class LibraryController implements Initializable{

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
	
	//
	@FXML private TableView<Book>lentTable;
	@FXML private TableColumn<Book,String> lentBookIDCol;
	@FXML private TableColumn<Book,String> lentBookNameCol;
	
	
	//Customer borrowed books history
	@FXML private TableView<Book> cstHistoryTable;
	
	@FXML private TableColumn <Customer,String> borrowLateCol;
	@FXML private TableColumn <Customer,String> borrowIDCol;
	@FXML private TableColumn <Customer,String> borrowNameCol;
	
	
	//search filter fields
	@FXML private TextField IDFilterField;
	@FXML private TextField titleFilterField;
	@FXML private TextField authorFilterField;
	@FXML private TextField shelfFilterField;
	@FXML private TextField publisherFilterField;
	@FXML private TextField genreFilterField;
	@FXML private TextField custNameField;
	//reset button
	@FXML private Button resetBtn;
	//Book details
	@FXML private Label LIDLabel;
	@FXML private Label ISBNLabel;
	@FXML private Label titleLabel;
	@FXML private Label authorLabel;
	@FXML private Label genreLabel;
	@FXML private Label publisherLabel; 
	@FXML private Label publicationDateLabel;
	
	//Customer details
	@FXML private Label cstIDLabel;
	@FXML private Label cstNameLabel;
	@FXML private Label cstPhoneLabel;
	@FXML private Label cstAddressLabel;
	
	private LibraryMenu libraryMenu;
	
	public LibraryController(LibraryMenu libraryMenu) {
		this.libraryMenu = libraryMenu;
	}
	
	public LibraryController() {
	}

	private Book tempBook;
	private Customer tempCst;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		// 0. Initialize the columns
		//The setCellValueFactory(...) that we set on the table columns are used to determine which field
		//inside the Book objects should be used for the particular column.
		//if using ints and stuff, asObject() needs to be added after getproperty()
		this.libraryMenu = new LibraryMenu();
		
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

		bookTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Book>() {

			@Override
			public void changed(ObservableValue<? extends Book> observable, Book oldValue, Book newValue) {
				tempBook=bookTable.getSelectionModel().getSelectedItem();
				System.out.println("book row " + bookTable.getSelectionModel().getSelectedIndex());
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
		/////////////////////SAME THING WITH CUSTOMER////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////

		customerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {

			@Override
			public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
				tempCst=customerTable.getSelectionModel().getSelectedItem();
				System.out.println("customer row " +customerTable.getSelectionModel().getSelectedIndex());
				if (newValue != null) {
					cstIDLabel.setText(newValue.getLID());
					cstNameLabel.setText(newValue.getName());
					cstPhoneLabel.setText(newValue.getPhoneNum());
					cstAddressLabel.setText(newValue.getAddress());
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
			TextInputDialog dialog = new TextInputDialog("Edit Book");
//			dialog.setTitle("Text Input Dialog");
//			dialog.setHeaderText("Look, a Text Input Dialog");
//			dialog.setContentText("Please enter your name:");

			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));

			TextField ID = new TextField();
			ID.setPromptText(tempCst.getLID());
			
			TextField Name = new TextField();
			Name.setPromptText(tempCst.getName());

			TextField Phone = new TextField();
			Phone.setPromptText(tempCst.getPhoneNum());
			
			TextField Address = new TextField();
			Address.setPromptText(tempCst.getAddress());

			grid.add(new Label("ID:"), 0, 0);
			grid.add(ID, 1, 0);
			
			grid.add(new Label("Name:"), 0, 1);
			grid.add(Name, 1, 1);
			
			grid.add(new Label("Address:"), 0, 2);
			grid.add(Address, 1, 2);
			
			grid.add(new Label("Phone:"), 0, 3);
			grid.add(Phone, 1, 3);

			dialog.getDialogPane().setContent(grid);
			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				for (int i=0; i< libraryMenu.getCustomers().size();i++) {
					if( libraryMenu.getCustomers().get(i).equals(tempCst)) {
						libraryMenu.getCustomers().get(i).setLID(ID.getText());
						libraryMenu.getCustomers().get(i).setName(Name.getText());
						libraryMenu.getCustomers().get(i).setAddress(Address.getText());
						libraryMenu.getCustomers().get(i).setPhoneNum(Phone.getText());
						}
					}
				}
			}
	}
	@FXML
	private void addCustomerEvent(ActionEvent event) throws IOException {
		 
		 
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Text Input Dialog");
		dialog.setHeaderText("Look, a Text Input Dialog");
		dialog.setContentText("Please enter your name:");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField ID = new TextField();
		ID.setPromptText("ID");
		
		TextField Name = new TextField();
		Name.setPromptText("Title");

		TextField Phone = new TextField();
		Phone.setPromptText("Phone");
		
		TextField Address = new TextField();
		Address.setPromptText("Address");

		grid.add(new Label("ID:"), 0, 0);
		grid.add(ID, 1, 0);
		
		grid.add(new Label("Name:"), 0, 1);
		grid.add(Name, 1, 1);
		
		grid.add(new Label("Address:"), 0, 2);
		grid.add(Address, 1, 2);
		
		grid.add(new Label("Phone:"), 0, 3);
		grid.add(Phone, 1, 3);

		dialog.getDialogPane().setContent(grid);
		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			Boolean existing=false;
			Customer newCustomer = new Customer(ID.getText(),Name.getText(),Address.getText(),Phone.getText());
			///////CHECK IF THE BOOK ID ALREADY EXISTS AND IF FIELDS ARE NOT EMPTY
			for (int i=0; i<libraryMenu.getCustomers().size();i++) {
				if (ID.getText().equals(libraryMenu.getCustomers().get(i).getLID()))
					existing=true;
			}
			///////IF IT DOES NOT, ADD THE BOOK
			if(existing==false &&  !cstFieldsAreEmpty(ID,Name,Address,Phone)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText(null);
				alert.setContentText("Customer correctly added.");

				alert.showAndWait();
				this.libraryMenu.getCustomers().add(newCustomer);}
			//////IF IT DOES, DON'T ADD IT
			else if (existing==true) {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("Error during adding a customer");
				alert.setContentText("The customer is already registered in the library.");

				alert.showAndWait();
			}
		}
	}
	@FXML
	private void addBookEvent(ActionEvent event) throws IOException {
		 
		if(event.getSource().equals(addBookBtn)) {
			
			TextInputDialog dialog = new TextInputDialog("walter");
			dialog.setTitle("Text Input Dialog");
			dialog.setHeaderText("Look, a Text Input Dialog");
			dialog.setContentText("Please enter your name:");

			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));

			TextField ID = new TextField();
			ID.setPromptText("ID");
			
			TextField Title = new TextField();
			Title.setPromptText("Title");
			
			TextField Author = new TextField();
			Author.setPromptText("Surname");
			
			TextField Shelf = new TextField();
			Shelf.setPromptText("Shelf");
			
			TextField Publisher = new TextField();
			Publisher.setPromptText("Publisher");
			
			TextField Genre = new TextField();
			Genre.setPromptText("Genre");

			grid.add(new Label("ID:"), 0, 0);
			grid.add(ID, 1, 0);
			
			grid.add(new Label("Title:"), 0, 1);
			grid.add(Title, 1, 1);
			
			grid.add(new Label("Author:"), 0, 2);
			grid.add(Author, 1, 2);
			
			grid.add(new Label("Shelf:"), 0, 3);
			grid.add(Shelf, 1, 3);
			
			grid.add(new Label("Publisher:"), 0, 4);
			grid.add(Publisher, 1, 4);
			
			grid.add(new Label("Genre:"), 0, 5);
			grid.add(Genre, 1, 5);

			dialog.getDialogPane().setContent(grid);
			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				Boolean existing=false;
				Book newBook = new Book(ID.getText(),Title.getText(),Author.getText(),Shelf.getText(),Publisher.getText(),Genre.getText());
				
				///////CHECK IF THE BOOK ID ALREADY EXISTS AND IF FIELDS ARE NOT EMPTY
				for (int i=0; i<libraryMenu.getBooks().size();i++) {
					if (ID.getText().equals(libraryMenu.getBooks().get(i).getID()))
						existing=true;
				}
				///////IF IT DOES NOT, ADD THE BOOK
				if(existing==false &&  !bookFieldsAreEmpty(ID,Title,Author,Shelf,Publisher,Genre)) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("Book correctly added.");

					alert.showAndWait();
					this.libraryMenu.getBooks().add(newBook);}
				//////IF IT DOES, DON'T ADD IT
				else if (existing==true) {
					
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR");
					alert.setHeaderText("Error during adding a book");
					alert.setContentText("The book already exists in this library.");

					alert.showAndWait();
				}
			}
		}	
	}
	
	@FXML
	private void editBookEvent(ActionEvent event) throws IOException {
		 
		if(event.getSource().equals(editBookBtn)) {		 
				TextInputDialog dialog = new TextInputDialog("Edit Book");
//				dialog.setTitle("Text Input Dialog");
//				dialog.setHeaderText("Look, a Text Input Dialog");
//				dialog.setContentText("Please enter your name:");

				GridPane grid = new GridPane();
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(20, 150, 10, 10));

				TextField ID = new TextField();
				ID.setPromptText(tempBook.getID());
				
				TextField Title = new TextField();
				Title.setPromptText(tempBook.getTitle());
				
				TextField Author = new TextField();
				Author.setPromptText(tempBook.getAuthor());
				
				TextField Shelf = new TextField();
				Shelf.setPromptText(tempBook.getShelf());
				
				TextField Publisher = new TextField();
				Publisher.setPromptText(tempBook.getPublisher());
				
				TextField Genre = new TextField();
				Genre.setPromptText(tempBook.getGenre());

				grid.add(new Label("ID:"), 0, 0);
				grid.add(ID, 1, 0);
				
				grid.add(new Label("Title:"), 0, 1);
				grid.add(Title, 1, 1);
				
				grid.add(new Label("Author:"), 0, 2);
				grid.add(Author, 1, 2);
				
				grid.add(new Label("Shelf:"), 0, 3);
				grid.add(Shelf, 1, 3);
				
				grid.add(new Label("Publisher:"), 0, 4);
				grid.add(Publisher, 1, 4);
				
				grid.add(new Label("Genre:"), 0, 5);
				grid.add(Genre, 1, 5);

				dialog.getDialogPane().setContent(grid);
				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					for (int i=0; i< libraryMenu.getBooks().size();i++) {
						if( libraryMenu.getBooks().get(i).equals(tempBook)) {
							libraryMenu.getBooks().get(i).setID(ID.getText());
							libraryMenu.getBooks().get(i).setTitle(Title.getText());
							libraryMenu.getBooks().get(i).setAuthor(Author.getText());
							libraryMenu.getBooks().get(i).setShelf(Shelf.getText());
							libraryMenu.getBooks().get(i).setPublisher(Publisher.getText());
							libraryMenu.getBooks().get(i).setGenre((Genre.getText()));
							
							
						}
					}
				}
			}
	}
	@FXML
	public void showBookDetails(MouseEvent event) {
		
	}
	
	public Boolean bookFieldsAreEmpty(TextField a, TextField b,TextField c, TextField d, TextField e, TextField f) {
		Boolean x=false;
		if (a.getText().isEmpty() || b.getText().isEmpty() || c.getText().isEmpty() || d.getText().isEmpty() || e.getText().isEmpty() || f.getText().isEmpty())
			x=true;
		System.out.println("There's at least an empty field!");
		return x;
	}

	public Boolean cstFieldsAreEmpty(TextField a, TextField b,TextField c, TextField d) {
		Boolean x=false;
		if (a.getText().isEmpty() || b.getText().isEmpty() || c.getText().isEmpty() || d.getText().isEmpty())
			x=true;
		System.out.println("There's at least an empty field!");
		return x;
	}
	
	@FXML
	private void resetSearchEvent(ActionEvent event) throws IOException {
		 
		if(event.getSource().equals(resetBtn)) {	
			IDFilterField.clear();
			shelfFilterField.clear();
			titleFilterField.clear();
			publisherFilterField.clear();
			authorFilterField.clear();
			genreFilterField.clear();
			
		}
		
	}
	
	
	
	
	
	

}
