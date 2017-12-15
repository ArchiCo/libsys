package frontend.resources;

import frontend.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javax.security.auth.Refreshable;

import com.sun.org.glassfish.external.probe.provider.annotations.Probe;

import backend.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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

import java.util.ArrayList;
import java.util.Optional;

import javafx.util.Callback;
import javafx.util.Pair;
import sun.java2d.pipe.SpanShapeRenderer.Simple;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;

public class LibraryController implements Initializable {

	// Book buttons
	@FXML
	private Button logoutBtn;
	@FXML
	private Button lendBookBtn;

	// Customer buttons
	@FXML
	private Button editCustomerBtn;
	@FXML
	private Button addCustomerBtn;
	@FXML
	private Button addBookBtn;
	@FXML
	private Button removeBookBtn;
	@FXML
	private Button editBookBtn;
	@FXML
	private Button returnBookBtn;

	// book directory table column initialize
	@FXML
	private TableView<Book> bookTable;
	@FXML
	private TableColumn<Book, Integer> bookIDCol;
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

	// customer directory table
	@FXML
	private TableView<Customer> customerTable;
	@FXML
	private TableColumn<Customer, String> customerIDCol;
	@FXML
	private TableColumn<Customer, String> customerNameCol;

	//
	@FXML
	private TableView<Book> lentTable;
	@FXML
	private TableColumn<Book, String> lentBookIDCol;
	@FXML
	private TableColumn<Book, String> lentBookNameCol;

	// Customer borrowed books history
	@FXML
	private TableView<Book> cstHistoryTable;

	@FXML
	private TableColumn<Customer, String> borrowLateCol;
	@FXML
	private TableColumn<Customer, String> borrowIDCol;
	@FXML
	private TableColumn<Customer, String> borrowNameCol;

	// search filter fields
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
	private TextField custNameField;

	// Book details
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

	// Customer details
	@FXML
	private Label cstIDLabel;
	@FXML
	private Label cstNameLabel;
	@FXML
	private Label cstPhoneLabel;
	@FXML
	private Label cstAddressLabel;

	private LibraryMenu libraryMenu;
	private Library library;
	
	FilteredList<Book> filteredBooks;
	FilteredList<Customer> filteredCustomers;
	
	private SortedList<Book> sortedBooks;
	private SortedList<Customer> sortedCustomers;
	

	public LibraryController(LibraryMenu libraryMenu, Library library) {
		this.libraryMenu = libraryMenu;
		this.library = library;
	}

	public LibraryController() {
	}

	private Book tempBook;
	private Customer tempCst;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 0. Initialize the columns
		// The setCellValueFactory(...) that we set on the table columns are used to
		// determine which field
		// inside the Book objects should be used for the particular column.
		// if using ints and stuff, asObject() needs to be added after getproperty()
		this.libraryMenu = new LibraryMenu();
		this.library = new Library();

		bookIDCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> param) {
						Book book = param.getValue();
						SimpleIntegerProperty convertedLid = getIntegerProperty(book.getLid());
						return convertedLid.asObject();
					}
				});
		bookTitleCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						Book book = param.getValue();
						SimpleStringProperty convertedTitle = getStringProperty(book.getTitle());
						return convertedTitle;
					}
				});
		bookAuthorCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						Book book = param.getValue();
						SimpleStringProperty convertedAuthor = getStringProperty(book.getAuthor());
						return convertedAuthor;
					}
				});
		bookShelfCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						Book book = param.getValue();
						SimpleStringProperty convertedShelf = getStringProperty(book.getShelf());
						return convertedShelf;
					}
				});
		bookPublisherCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						Book book = param.getValue();
						SimpleStringProperty convertedPublisher = getStringProperty(book.getPublisher());
						return convertedPublisher;
					}
				});
		bookGenreCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						Book book = param.getValue();
						SimpleStringProperty convertedGenre = getStringProperty(book.getGenre());
						return convertedGenre;
					}
				});
		customerIDCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Customer, String> param) {
						Customer customer = param.getValue();
						SimpleStringProperty convertedCstId = getStringProperty(customer.getCustomerId());
						return convertedCstId;
					}
				});
		customerNameCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Customer, String> param) {
						Customer customer = param.getValue();
						SimpleStringProperty convertedCstName = getStringProperty(customer.getName());
						return convertedCstName;
					}
				});

		//////////////////////////////// SEARCH FUNCTION BOOK
		//////////////////////////////// TABLE///////////////////////////
		ObjectProperty<Predicate<Book>> IDFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> titleFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> authorFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> shelfFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> publisherFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Book>> genreFilter = new SimpleObjectProperty<>();

		ObjectProperty<Predicate<Customer>> custNameFilter = new SimpleObjectProperty<>();

		custNameFilter.bind(Bindings.createObjectBinding(
				() -> customer -> customer.getName().toLowerCase().contains(custNameField.getText().toLowerCase()),
				custNameField.textProperty()));
		IDFilter.bind(Bindings.createObjectBinding(() -> book -> Integer.toString(book.getLid()).toLowerCase()
				.contains(IDFilterField.getText().toLowerCase()), IDFilterField.textProperty()));

		titleFilter.bind(Bindings.createObjectBinding(
				() -> book -> book.getTitle().toLowerCase().contains(titleFilterField.getText().toLowerCase()),
				titleFilterField.textProperty()));

		authorFilter.bind(Bindings.createObjectBinding(
				() -> book -> book.getAuthor().toLowerCase().contains(authorFilterField.getText().toLowerCase()),
				authorFilterField.textProperty()));

		shelfFilter.bind(Bindings.createObjectBinding(
				() -> book -> book.getShelf().toLowerCase().contains(shelfFilterField.getText().toLowerCase()),
				shelfFilterField.textProperty()));

		publisherFilter.bind(Bindings.createObjectBinding(
				() -> book -> book.getPublisher().toLowerCase().contains(publisherFilterField.getText().toLowerCase()),
				publisherFilterField.textProperty()));

		genreFilter.bind(Bindings.createObjectBinding(
				() -> book -> book.getGenre().toLowerCase().contains(genreFilterField.getText().toLowerCase()),
				genreFilterField.textProperty()));

		// 1. Wrap the ObservableList in a FilteredList (initially display all data)

		filteredBooks = new FilteredList<>(getObsBooks(library.getListBooks()), p -> true);
		filteredCustomers = new FilteredList<>(getObsCustomers(library.getCustomersList()),
				p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		// bind the filters to each other so the predicates of each are put into 1
		filteredBooks.predicateProperty()
				.bind(Bindings.createObjectBinding(
						() -> IDFilter.get()
								.and(titleFilter.get()
										.and(authorFilter.get().and(
												shelfFilter.get().and(publisherFilter.get().and(genreFilter.get()))))),
						IDFilter, titleFilter, authorFilter, shelfFilter, publisherFilter, genreFilter));

		filteredCustomers.predicateProperty()
				.bind(Bindings.createObjectBinding(() -> custNameFilter.get(), custNameFilter));

		// addlistener calls changelistener.changed
		IDFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
		});
		titleFilterField.textProperty().addListener((observable, oldvalue, newvalue) -> {
		});
		authorFilterField.textProperty().addListener((observable, oldvalue, newvalue) -> {
		});
		shelfFilterField.textProperty().addListener((observable, oldvalue, newvalue) -> {
		});
		publisherFilterField.textProperty().addListener((observable, oldvalue, newvalue) -> {
		});
		genreFilterField.textProperty().addListener((observable, oldvalue, newvalue) -> {
		});

		custNameField.textProperty().addListener((observable, oldValue, newValue) -> {
		});

		// 3. Wrap the FilteredList in a SortedList.
		sortedBooks = new SortedList<>(filteredBooks);
		sortedCustomers = new SortedList<>(filteredCustomers);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedBooks.comparatorProperty().bind(bookTable.comparatorProperty());
		sortedCustomers.comparatorProperty().bind(customerTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		bookTable.setItems(sortedBooks);
		customerTable.setItems(sortedCustomers);

		// ObservableList(Arraylist) >> FilteredList >> Sortedlist(comparator bind) >>
		// into table
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////// BOOK DETAILS////////////////////////

		// ---Apparently getselectionmodel is not as good as gettablerow()--- do i care

		bookTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Book>() {

			@Override
			public void changed(ObservableValue<? extends Book> observable, Book oldValue, Book newValue) {
				tempBook = bookTable.getSelectionModel().getSelectedItem();
				System.out.println("book row " + bookTable.getSelectionModel().getSelectedIndex());
				if (newValue != null) {
					LIDLabel.setText(Integer.toString(newValue.getLid()));
					titleLabel.setText(newValue.getTitle());
					authorLabel.setText(newValue.getAuthor());
					genreLabel.setText(newValue.getGenre());
					publisherLabel.setText(newValue.getPublisher());
				} else {
					LIDLabel.setText("");
					titleLabel.setText("");
					authorLabel.setText("");
					genreLabel.setText("");
					publisherLabel.setText("");
				}
			}
		});
		/////////////////////////////////////////////////////////////////////////////////
		///////////////////// SAME THING WITH CUSTOMER////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////

		customerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {

			@Override
			public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
				tempCst = customerTable.getSelectionModel().getSelectedItem();
				System.out.println("customer row " + customerTable.getSelectionModel().getSelectedIndex());
				if (newValue != null) {
					cstIDLabel.setText(newValue.getCustomerId());
					cstNameLabel.setText(newValue.getName());
					cstPhoneLabel.setText(Integer.toString(newValue.getPhoneNumber()));
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
		if (event.getSource().equals(logoutBtn)) {
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
		if (event.getSource().equals(editCustomerBtn)) {
			TextInputDialog dialog = new TextInputDialog("Edit Book");
			// dialog.setTitle("Text Input Dialog");
			// dialog.setHeaderText("Look, a Text Input Dialog");
			// dialog.setContentText("Please enter your name:");

			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));

			TextField ID = new TextField();
			ID.setPromptText(tempCst.getCustomerId());

			TextField Name = new TextField();
			Name.setPromptText(tempCst.getName());

			TextField Phone = new TextField();
			Phone.setPromptText(Integer.toString(tempCst.getPhoneNumber()));

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
			if (result.isPresent()) {
				for (int i = 0; i < library.getCustomersList().size(); i++) {
					if (library.getCustomersList().get(i).equals(tempCst)) {
						library.getCustomersList().get(i).setLid(ID.getText());
						library.getCustomersList().get(i).setName(Name.getText());
						library.getCustomersList().get(i).setAddress(Address.getText());
						library.getCustomersList().get(i).setPhoneNumber(Integer.parseInt(Phone.getText()));
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

		// TextField ID = new TextField();
		// ID.setPromptText("ID");

		TextField Name = new TextField();
		Name.setPromptText("Title");

		TextField Phone = new TextField();
		Phone.setPromptText("Phone");

		TextField Address = new TextField();
		Address.setPromptText("Address");

		// grid.add(new Label("ID:"), 0, 0);
		// grid.add(ID, 1, 0);

		grid.add(new Label("Name:"), 0, 1);
		grid.add(Name, 1, 1);

		grid.add(new Label("Address:"), 0, 2);
		grid.add(Address, 1, 2);

		grid.add(new Label("Phone:"), 0, 3);
		grid.add(Phone, 1, 3);

		dialog.getDialogPane().setContent(grid);
		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			Boolean existing = false;
			libraryMenu.registerCustomer(Name.getText(), Address.getText(), Integer.parseInt(Phone.getText()));

			// Start working on the LibraryMenu function to connect
		}
		customerTable.setItems(sortedCustomers);
		customerTable.refresh();
	}

	/*
	 * ///////CHECK IF THE BOOK ID ALREADY EXISTS AND IF FIELDS ARE NOT EMPTY for
	 * (int i=0; i<libraryMenu.getCustomersList().size();i++) { if
	 * (ID.getText().equals(libraryMenu.getCustomersList().get(i).getLID()))
	 * existing=true; } ///////IF IT DOES NOT, ADD THE BOOK if(existing==false &&
	 * !cstFieldsAreEmpty(ID,Name,Address,Phone)) { Alert alert = new
	 * Alert(AlertType.INFORMATION); alert.setTitle("Information");
	 * alert.setHeaderText(null); alert.setContentText("Customer correctly added.");
	 * 
	 * alert.showAndWait(); this.libraryMenu.getCustomersList().add(newCustomer);}
	 * //////IF IT DOES, DON'T ADD IT else if (existing==true) {
	 * 
	 * Alert alert = new Alert(AlertType.ERROR); alert.setTitle("ERROR");
	 * alert.setHeaderText("Error during adding a customer");
	 * alert.setContentText("The customer is already registered in the library.");
	 * 
	 * alert.showAndWait(); } } }
	 */
	@FXML
	private void addBookEvent(ActionEvent event) throws IOException {

		if (event.getSource().equals(addBookBtn)) {

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
			if (result.isPresent()) {
				Boolean existing = false;
				Book newBook = new Book(ID.getText(), Title.getText(), Author.getText(), Shelf.getText(),
						Publisher.getText(), Genre.getText());

				/////// CHECK IF THE BOOK ID ALREADY EXISTS AND IF FIELDS ARE NOT EMPTY
				for (int i = 0; i < getObsBooks(library.getListBooks()).size(); i++) {
					if (ID.getText().equals(getObsBooks(library.getListBooks()).get(i).getLid()))
						existing = true;
				}
				/////// IF IT DOES NOT, ADD THE BOOK
				if (existing == false && !bookFieldsAreEmpty(ID, Title, Author, Shelf, Publisher, Genre)) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("Book correctly added.");

					alert.showAndWait();
					libraryMenu.addBook(ID.getText(), Title.getText(), Author.getText(), Genre.getText(),
							Publisher.getText(), Shelf.getText());
					bookTable.setItems(sortedBooks);
					bookTable.refresh();
				}
				////// IF IT DOES, DON'T ADD IT
				else if (existing == true) {

					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR");
					alert.setHeaderText("Error during adding a book");
					alert.setContentText("The book already exists in this library.");

					alert.showAndWait();
				}
			}
		}
		bookTable.refresh();
	}
	

	@FXML
	private void editBookEvent(ActionEvent event) throws IOException {

		if (event.getSource().equals(editBookBtn)) {
			TextInputDialog dialog = new TextInputDialog("Edit Book");
			// dialog.setTitle("Text Input Dialog");
			// dialog.setHeaderText("Look, a Text Input Dialog");
			// dialog.setContentText("Please enter your name:");

			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));
			// tostring = int >> string
			// parseint = string >> int
			TextField ID = new TextField();
			ID.setPromptText(Integer.toString(tempBook.getLid()));

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
			if (result.isPresent()) {
				for (int i = 0; i < getObsBooks(library.getListBooks()).size(); i++) {
					if (getObsBooks(library.getListBooks()).get(i).equals(tempBook)) {
						getObsBooks(library.getListBooks()).get(i).setLid((Integer.parseInt(ID.getText())));
						getObsBooks(library.getListBooks()).get(i).setTitle(Title.getText());
						getObsBooks(library.getListBooks()).get(i).setAuthor(Author.getText());
						getObsBooks(library.getListBooks()).get(i).setShelf(Shelf.getText());
						getObsBooks(library.getListBooks()).get(i).setPublisher(Publisher.getText());
						getObsBooks(library.getListBooks()).get(i).setGenre((Genre.getText()));

					}
				}
			}
		}
	}

	@FXML
	public void removeBookEvent(ActionEvent event) {
		if (event.getSource().equals(removeBookBtn)) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove Book");
		alert.setHeaderText("RIP Book");
		alert.setContentText("Remove?");
		Optional <ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			Alert confirmedBookKill = new Alert(AlertType.INFORMATION);
			confirmedBookKill.setTitle("Book Kill Confirmed");
			confirmedBookKill.setHeaderText(null);
			confirmedBookKill.setContentText("Kill Confirmed.");
			confirmedBookKill.showAndWait();
			libraryMenu.removeBook(tempBook.getLid());
			bookTable.setItems(refreshBookTable());
			bookTable.refresh();
		} else {
			alert.close();
		}
		
		
		}
	}

	public Boolean bookFieldsAreEmpty(TextField a, TextField b, TextField c, TextField d, TextField e, TextField f) {
		Boolean x = false;
		if (a.getText().isEmpty() || b.getText().isEmpty() || c.getText().isEmpty() || d.getText().isEmpty()
				|| e.getText().isEmpty() || f.getText().isEmpty()) {
			x = true;
		System.out.println("There's at least an empty field!");
		} else {
			x = false;
		}
		return x;
	}

	public Boolean cstFieldsAreEmpty(TextField a, TextField b, TextField c, TextField d) {
		Boolean x = false;
		if (a.getText().isEmpty() || b.getText().isEmpty() || c.getText().isEmpty() || d.getText().isEmpty()) {
			x = true;
		System.out.println("There's at least an empty field!");
		} else {
			x = false;
		}
		return x;
	}

	/////////////////////////////////////// CONVERSION/////////////////////////////////////////////
	/////////////////////////////////////// METHODS/////////////////////////////////////////////////
	private ObservableList<Book> getObsBooks(ArrayList<Book> arrayToConvert) {
		return FXCollections.observableArrayList(arrayToConvert);
	}

	private ObservableList<Customer> getObsCustomers(ArrayList<Customer> arrayToConvert) {
		return FXCollections.observableArrayList(arrayToConvert);
	}

	private ObservableList<Record> getObsRecords(ArrayList<Record> arrayToConvert) {
		return FXCollections.observableArrayList(arrayToConvert);
	}

	private SimpleStringProperty getStringProperty(String string) {
		SimpleStringProperty convertedString = new SimpleStringProperty(string);
		return convertedString;
	}

	private SimpleIntegerProperty getIntegerProperty(int number) {
		SimpleIntegerProperty convertedInt = new SimpleIntegerProperty(number);
		return convertedInt;
	}
	//////////////////////////////////Refresh/////////////////////////
	private FilteredList<Book> getFilteredBooks(){
		return this.filteredBooks;
	}
	private SortedList<Book> getSortedBooks(){
		return this.sortedBooks;
	}
	private SortedList<Book> refreshBookTable() {
		FilteredList<Book> filteredBooks = new FilteredList<>(getObsBooks(library.getListBooks()), p -> true);
		sortedBooks = new SortedList<>(filteredBooks);
		sortedBooks.comparatorProperty().bind(bookTable.comparatorProperty());
		return sortedBooks;
	}

}
