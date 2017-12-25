package frontend.resources;

import frontend.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.Delayed;
import java.util.concurrent.FutureTask;
import java.util.function.Predicate;

import javax.swing.event.TableColumnModelListener;

import com.sun.javafx.scene.control.skin.IntegerFieldSkin;

import backend.*;
import database.structure.Column;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Optional;

import javafx.util.Callback;
import javafx.util.Pair;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;

public class LibraryController implements Initializable {

	// Book buttons
	@FXML private Button logoutBtn;
	@FXML private Button lendBookBtn;
	@FXML private Button returnBookBtn;
	@FXML private Button addBookBtn;
	@FXML private Button removeBookBtn;
	@FXML private Button editBookBtn;
	@FXML private Button resetBtn;
	
	// Customer buttons
	@FXML private Button editCustomerBtn;
	@FXML private Button addCustomerBtn;
	@FXML private Button removeCustomerBtn;
	
	// book directory table column initialize
	@FXML private TableView<Book> bookTable;
	@FXML private TableColumn<Book, Integer> bookIDCol;
	@FXML private TableColumn<Book, String> bookTitleCol;
	@FXML private TableColumn<Book, String> bookAuthorCol;
	@FXML private TableColumn<Book, String> bookShelfCol;
	@FXML private TableColumn<Book, String> bookPublisherCol;
	@FXML private TableColumn<Book, String> bookGenreCol;
	@FXML private TableColumn<Book, String> bookISBNCol;
	// customer directory table
	@FXML private TableView<Customer> customerTable;
	@FXML private TableColumn<Customer, String> customerIDCol;
	@FXML private TableColumn<Customer, String> customerNameCol;

	//Lent table
	@FXML private TableView<Book> lentTable;
	@FXML private TableColumn<Book, String> lentBookIDCol;
	@FXML private TableColumn<Book, String> lentBookNameCol;

	// Customer borrowed books history
	@FXML private TableView<Book> cstHistoryTable;
	@FXML private TableColumn<Book, String> cstHistBookIDCol;
	@FXML private TableColumn<Book, String> cstHistBookTitleCol;
	@FXML private TableColumn<Book, String> cstHistBookAuthorCol;
	@FXML private TableColumn<Book, String> cstHistBookGenreCol;
	
	// customer currently borrowed
	@FXML private TableView<Book> cstCurrentBorrowedTable;
	@FXML private TableColumn<Book, String> borrowLateCol;
	@FXML private TableColumn<Book, String> borrowIDCol;
	@FXML private TableColumn<Book, String> borrowNameCol;
	@FXML private TableColumn<Library, Record> borrowLateFeeCol;
	
	//popular books
	@FXML private TableView<Book> popularBooksTable;
	@FXML private TableColumn<Book, String> popIsbnCol;
	@FXML private TableColumn<Book, String> popTitleCol;
	@FXML private TableColumn<Book, Integer> popTimesBorrowedCol;
	
	//all borrowed books
	@FXML private TableView<Book> allBorrowedBooksTable;
	@FXML private TableColumn<Book, String> allBorrowIdCol;
	@FXML private TableColumn<Book, String> allBorrowTitleCol;
	@FXML private TableColumn<Book, String> allBorrowAuthorCol;
	@FXML private TableColumn<Book, String> allBorrowPublisherCol;
	@FXML private TableColumn<Book, String> allBorrowGenreCol;
	@FXML private TableColumn<Record, String> allBorrowDelayCol;
	@FXML private TableColumn<Record, String> allBorrowDelayFeeCol;
	
	//basket table
	@FXML private TableView<Book> basketTable;
	@FXML private TableColumn<Book, Integer> basketIDCol;
	@FXML private TableColumn<Book, String> basketTitleCol;
	@FXML private TableColumn<Book, String> basketAuthorCol;
	@FXML private TableColumn<Book, String> basketIsbnCol;
	
	// search filter fields
	@FXML private TextField IDFilterField;
	@FXML private TextField titleFilterField;
	@FXML private TextField authorFilterField;
	@FXML private TextField shelfFilterField;
	@FXML private TextField publisherFilterField;
	@FXML private TextField genreFilterField;
	@FXML private TextField custNameField;

	// Book details
	@FXML private Label LIDLabel;
	@FXML private Label ISBNLabel;
	@FXML private Label titleLabel;
	@FXML private Label authorLabel;
	@FXML private Label genreLabel;
	@FXML private Label publisherLabel;
	@FXML private Label publicationDateLabel;

	// Customer details
	@FXML private Label cstIDLabel;
	@FXML private Label cstNameLabel;
	@FXML private Label cstPhoneLabel;
	@FXML private Label cstAddressLabel;

	protected LibraryMenu libraryMenu;
	protected Library library;
	
	private FilteredList<Book> filteredBooks;
	private FilteredList<Customer> filteredCustomers;
	
	private SortedList<Book> sortedBooks;
	private SortedList<Customer> sortedCustomers;
	private SortedList<Book> customerHistory;
	private SortedList<Book> sortedPopularBooks;
	private SortedList<Book> sortedAllBorrowed;
	private SortedList<Book> sortedCstCurrentBorrowed;
	
	
	protected Book tempBook;
	protected Customer tempCst;
	
	private ObjectProperty<Predicate<Book>> IDFilter;
	ObjectProperty<Predicate<Book>> titleFilter;
	ObjectProperty<Predicate<Book>> authorFilter;
	ObjectProperty<Predicate<Book>> shelfFilter;
	ObjectProperty<Predicate<Book>> publisherFilter;
	ObjectProperty<Predicate<Book>> genreFilter;

	ObjectProperty<Predicate<Customer>> custNameFilter;
	
	ObservableList<Book> basket;
	public LibraryController(LibraryMenu libraryMenu, Library library) {
		this.libraryMenu = libraryMenu;
		this.library = library;
	}

	public LibraryController() {
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 0. Initialize the columns
		// The setCellValueFactory(...) that we set on the table columns are used to
		// determine which field
		// inside the Book objects should be used for the particular column.
		// if using ints and stuff, asObject() needs to be added after getproperty()
		this.libraryMenu = new LibraryMenu();
		this.library = new Library();
		this.basket = FXCollections.observableArrayList();
		
		////////////////////////////////////////////////////////////////////////////
		bookIDCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> param) {
						Book book = param.getValue();
						SimpleIntegerProperty convertedLid = getIntegerProperty(book.getLid());
						return convertedLid.asObject();
					}});
		bookTitleCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						Book book = param.getValue();
						SimpleStringProperty convertedTitle = getStringProperty(book.getTitle());
						return convertedTitle;
					}
				});
		bookAuthorCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						Book book = param.getValue();
						SimpleStringProperty convertedAuthor = getStringProperty(book.getAuthor());
						return convertedAuthor;
					}
				});
		bookShelfCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						Book book = param.getValue();
						SimpleStringProperty convertedShelf = getStringProperty(book.getShelf());
						return convertedShelf;
					}
				});
		bookPublisherCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						Book book = param.getValue();
						SimpleStringProperty convertedPublisher = getStringProperty(book.getPublisher());
						return convertedPublisher;
					}
				});
		bookGenreCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						Book book = param.getValue();
						SimpleStringProperty convertedGenre = getStringProperty(book.getGenre());
						return convertedGenre;
					}
				});
		bookISBNCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedIsbn = getStringProperty(book.getIsbn());
				return convertedIsbn;
			}
		});
////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
		customerIDCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Customer, String> param) {
						Customer customer = param.getValue();
						SimpleStringProperty convertedCstId = getStringProperty(customer.getCustomerId());
						return convertedCstId;
					}
				});
		customerNameCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Customer, String> param) {
						Customer customer = param.getValue();
						SimpleStringProperty convertedCstName = getStringProperty(customer.getName());
						return convertedCstName;
					}
				});
////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
		borrowIDCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedId = getStringProperty(Integer.toString(book.getLid()));
				return convertedId;
			}
		});
		borrowNameCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedTitle = getStringProperty(book.getTitle());
				return convertedTitle;
			}
		});
/*		borrowLateCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Customer,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Customer, String> param) {
				Customer customer = param.getValue();
				SimpleStringProperty convertedCstId = getStringProperty(customer.());
				return convertedCstId;
			}
		});
*/		
/*		borrowLateFeeCol.setCellValueFactory ( new Callback<TableColumn.CellDataFeatures<Library, Record>, ObservableValue>() {
			@Override
			public ObservableValue call(CellDataFeatures param) {
				
				ObservableValue<Record> convertedFee = new SimpleObjectProperty<>(findRecord());
				return convertedFee;
			}
		});

		borrowLateFeeCol.setCellFactory(column-> {
			return new TableCell<Library,Record>() {
				@Override
				protected void updateItem(Record item, boolean empty) {
					super.updateItem(item, empty);
				if(item == null || empty) {
					setText(null);
				}
				else {
					setText(Long.toString(item.getFine()));
				}
				}
			};
		});
*/
		cstHistBookIDCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param){
				Book book = param.getValue();
				SimpleStringProperty convertedBookId = getStringProperty(Integer.toString(book.getLid()));
				return convertedBookId;
			}
		});
////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
		cstHistBookTitleCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedTitle = getStringProperty(book.getTitle());
				return convertedTitle;
			}
		});
		cstHistBookAuthorCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedAuthor = getStringProperty(book.getAuthor());
				return convertedAuthor;
			}
		});
		cstHistBookGenreCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedGenre = getStringProperty(book.getGenre());
				return convertedGenre;
			}
		});
		
////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
		allBorrowIdCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedId = getStringProperty(Integer.toString(book.getLid()));
				return convertedId;
			}
		});

		allBorrowTitleCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedTitle = getStringProperty(book.getTitle());
				return convertedTitle;
			}
		});

		allBorrowAuthorCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedAuthor = getStringProperty(book.getAuthor());
				return convertedAuthor;
			}
		});
		
		allBorrowPublisherCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedPublisher = getStringProperty(book.getPublisher());
				return convertedPublisher;
			}
		});
		
		allBorrowGenreCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedGenre = getStringProperty(book.getGenre());
				return convertedGenre;
			}
		});
/*		allBorrowDelayCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Record, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Record, String> param) {
				Record record = param.getValue();
				LocalDate delayed =
				SimpleStringProperty convertedDelayCol = getStringProperty(record.getDateTaken());
				return convertedDelayCol;
			}
		});
*/
/*		allBorrowDelayFeeCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Record, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Record, String> param) {
				Record record = param.getValue();
				SimpleStringProperty convertedFee = getStringProperty(Long.toString(record.getFine()));
				return convertedFee;
			}
		});
*/
		//////////////////////Popular Books////////////////////////
		popIsbnCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedIsbn = getStringProperty(book.getIsbn());
				return convertedIsbn;
			}
		});
		popTitleCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedTitle = getStringProperty(book.getTitle());
				return convertedTitle;
			}
		});
		popTimesBorrowedCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> param) {
				Book book = param.getValue();
				SimpleIntegerProperty convertedLentTimes = getIntegerProperty(book.getLentTimes());
				return convertedLentTimes.asObject();
			}
		});
		/////////////////////Basket///////////////////////////
		
		basketIDCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> param) {
				Book book = param.getValue();
				SimpleIntegerProperty convertedLid = getIntegerProperty(book.getLid());
				return convertedLid.asObject();
			}});
		basketTitleCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedTitle = getStringProperty(book.getTitle());
				return convertedTitle;
			}
		});
		basketAuthorCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedAuthor = getStringProperty(book.getAuthor());
				return convertedAuthor;
			}
		});
		basketIsbnCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedIsbn = getStringProperty(book.getIsbn());
				return convertedIsbn;
			}
		});
		
		
		//////////////////////////////// SEARCH FUNCTION BOOK TABLE///////////////////////////
		IDFilter = new SimpleObjectProperty<>();
		titleFilter = new SimpleObjectProperty<>();
		authorFilter = new SimpleObjectProperty<>();
		shelfFilter = new SimpleObjectProperty<>();
		publisherFilter = new SimpleObjectProperty<>();
		genreFilter = new SimpleObjectProperty<>();

		custNameFilter = new SimpleObjectProperty<>();

		custNameFilter.bind(Bindings.createObjectBinding(
				() -> customer -> customer.getName().toLowerCase().contains(custNameField.getText().toLowerCase()), 
				custNameField.textProperty()));
		
		IDFilter.bind(Bindings.createObjectBinding(
				() -> book -> Integer.toString(book.getLid()).toLowerCase().contains(IDFilterField.getText().toLowerCase()), 
				IDFilterField.textProperty()));

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
		//adding books to basket//
		bookTable.setRowFactory( rawr -> {
		    TableRow<Book> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            Book rowData = row.getItem();
		            System.out.println(rowData);
		            if(basket.contains(tempBook)) {
		            };
		            if(!basket.contains(tempBook)) {
		            	 basket.add(tempBook);
		            }
		        }
		    });
		    return row ;
		});
		
		// 1. Wrap the ObservableList in a FilteredList (initially display all data)

		filteredBooks = new FilteredList<>(getObsBooks(library.getAvailableBooks()), p -> true);
		filteredCustomers = new FilteredList<>(getObsCustomers(library.getCustomersList()), p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		// bind the filters to each other so the predicates of each are put into 1
		filteredBooks.predicateProperty().bind(Bindings.createObjectBinding(
						() -> IDFilter.get()
								.and(titleFilter.get()
										.and(authorFilter.get().and(
												shelfFilter.get().and(publisherFilter.get().and(genreFilter.get()))))),
						IDFilter, titleFilter, authorFilter, shelfFilter, publisherFilter, genreFilter));

		filteredCustomers.predicateProperty()
				.bind(Bindings.createObjectBinding(() -> custNameFilter.get(), custNameFilter));

		// addlistener calls changelistener.changed
		IDFilterField.textProperty().addListener((observable, oldValue, newValue) -> {});
		titleFilterField.textProperty().addListener((observable, oldvalue, newvalue) -> {});
		authorFilterField.textProperty().addListener((observable, oldvalue, newvalue) -> {});
		shelfFilterField.textProperty().addListener((observable, oldvalue, newvalue) -> {});
		publisherFilterField.textProperty().addListener((observable, oldvalue, newvalue) -> {});
		genreFilterField.textProperty().addListener((observable, oldvalue, newvalue) -> {});

		custNameField.textProperty().addListener((observable, oldValue, newValue) -> {});

		// 3. Wrap the FilteredList in a SortedList.
		sortedBooks = new SortedList<>(filteredBooks);
		sortedCustomers = new SortedList<>(filteredCustomers);
		sortedPopularBooks = new SortedList<>(getObsBooks(library.getPopularBooksArray()));
		sortedAllBorrowed = new SortedList<>(getObsBooks(library.getListLentBooks()));
	
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedBooks.comparatorProperty().bind(bookTable.comparatorProperty());
		sortedCustomers.comparatorProperty().bind(customerTable.comparatorProperty());
		sortedAllBorrowed.comparatorProperty().bind(allBorrowedBooksTable.comparatorProperty());
		sortedPopularBooks.comparatorProperty().bind(popularBooksTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		bookTable.setItems(sortedBooks);
		basketTable.setItems(basket);
		customerTable.setItems(sortedCustomers);

//		cstHistoryTable.setItems(value);
//		cstCurrentBorrowedTable.setItems(sortedCstCurrentBorrowed);
		
		allBorrowedBooksTable.setItems(sortedAllBorrowed);
		popularBooksTable.setItems(sortedPopularBooks);
	

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
					ISBNLabel.setText(newValue.getIsbn());
				} else {
					LIDLabel.setText("");
					titleLabel.setText("");
					authorLabel.setText("");
					genreLabel.setText("");
					publisherLabel.setText("");
					ISBNLabel.setText("");
				}
			}
		});
		/////////////////////////////////////////////////////////////////////////////////
		///////////////////// SAME THING WITH CUSTOMER////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////

		customerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {

		
			@Override
			public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
				
//				ObservableList<Book> customerHistoryArray = FXCollections.observableArrayList();
			
			
	//			sortedCstCurrentBorrowed = new SortedList<>(getObsBooks(library.getBorrowedBooks(tempCst)));
				tempCst = customerTable.getSelectionModel().getSelectedItem();
				System.out.println("customer row " + customerTable.getSelectionModel().getSelectedIndex());
				if (newValue != null) {
					cstIDLabel.setText(newValue.getCustomerId());
					cstNameLabel.setText(newValue.getName());
					cstPhoneLabel.setText(Integer.toString(newValue.getPhoneNumber()));
					cstAddressLabel.setText(newValue.getAddress());
					
					
					//show individual customer history
				ArrayList<Book> borrowCheck = library.getBorrowedBooks(tempCst);
				if(borrowCheck != null) {
					cstCurrentBorrowedTable.setItems(getObsBooks(library.getBorrowedBooks(tempCst)));
				}
				
				ArrayList<Book> fetchedBook = library.getCustomerHistoryArray(tempCst);
				if (!fetchedBook.isEmpty()) {
					cstHistoryTable.setItems(getObsBooks(library.getCustomerHistoryArray(tempCst)));
				}
				else {
					LIDLabel.setText("");
					titleLabel.setText("");
					authorLabel.setText("");
					genreLabel.setText("");
					publisherLabel.setText("");
					cstHistoryTable.getItems().clear();
				}
			}
			}
			});
		
		cstCurrentBorrowedTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				cstCurrentBorrowedTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
		if(tempCst != null) {
		if (event.getSource().equals(editCustomerBtn)) {
			TextInputDialog dialog = new TextInputDialog("Edit Customer");
			// dialog.setTitle("Text Input Dialog");
			// dialog.setHeaderText("Look, a Text Input Dialog");
			// dialog.setContentText("Please enter your name:");

			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));

			TextField name = new TextField();
			name.setPromptText(tempCst.getName());

			TextField phone = new TextField();
			phone.setPromptText(Integer.toString(tempCst.getPhoneNumber()));

			TextField address = new TextField();
			address.setPromptText(tempCst.getAddress());

			grid.add(new Label("Name:"), 0, 0);
			grid.add(name, 1, 0);

			grid.add(new Label("Address:"), 0, 1);
			grid.add(address, 1, 1);

			grid.add(new Label("Phone:"), 0, 2);
			grid.add(phone, 1, 2);

			dialog.getDialogPane().setContent(grid);
			Optional<String> result = dialog.showAndWait();
			
			if (result.isPresent()) {
				Alert confirmModify = new Alert (AlertType.CONFIRMATION);
				confirmModify.setTitle("Modify Customer");
				confirmModify.setHeaderText("MANIPULATE THAT INFO");
				confirmModify.setContentText("Modify Customer Info?");
				Optional <ButtonType> choice = confirmModify.showAndWait();
				if(choice.get() == ButtonType.OK) {
					libraryMenu.modifyCustomer(
							tempCst.getCustomerId(), name.getText(), address.getText(), phone.getText());
				refreshTable();
				}
			}
			else {
				dialog.close();
				refreshTable();
				}
			}
		}
	}
		
	@FXML
	private void addCustomerEvent(ActionEvent event) throws IOException {

		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Join the Gang");
		dialog.setHeaderText("Participate in the rough handling of Books");
		dialog.setContentText("What String shall we use to summon Your Gracious?");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField name = new TextField();
		name.setPromptText("Title");

		TextField phone = new TextField();
		phone.setPromptText("Phone");
		//do not allow letters
		phone.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            phone.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		TextField address = new TextField();
		address.setPromptText("Address");

		grid.add(new Label("Name:"), 0, 1);
		grid.add(name, 1, 1);

		grid.add(new Label("Address:"), 0, 2);
		grid.add(address, 1, 2);

		grid.add(new Label("Phone:"), 0, 3);
		grid.add(phone, 1, 3);

		dialog.getDialogPane().setContent(grid);
		
		final Button cancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
		cancel.addEventFilter(ActionEvent.ACTION, event1 ->
		    System.out.println("Cancel was definitely pressed")
		);
		 final Button ok = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
	        ok.addEventFilter(ActionEvent.ACTION, event1 ->
	            System.out.println("OK was definitely pressed")
	        );
		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		
	
	
		if (result.isPresent() && cstFieldsAreEmpty(name, address, phone) == false) {
			Alert confirmedGangMember = new Alert(AlertType.INFORMATION);
			confirmedGangMember.setTitle("Woot");
			confirmedGangMember.setHeaderText(null);
			confirmedGangMember.setContentText("Welcome, " + name.getText());
			confirmedGangMember.showAndWait();
			libraryMenu.registerCustomer(name.getText(), address.getText(), Integer.parseInt(phone.getText()));
			refreshTable();
		}
		if(result.isPresent() && cstFieldsAreEmpty(name, address, phone) == true) {
			Alert confirmedGangMember = new Alert(AlertType.ERROR);
			confirmedGangMember.setTitle("Field Empty");
			confirmedGangMember.setHeaderText(null);
			confirmedGangMember.setContentText("A field was left empty. Retry.");
			confirmedGangMember.showAndWait();
			refreshTable();
			}
		}
	
	@FXML
	private void removeCustomerEvent(ActionEvent event) throws IOException{
		if (event.getSource().equals(removeCustomerBtn)) {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Remove Customer");
			alert.setHeaderText("RIP Customer");
			alert.setContentText("Remove?");
			Optional <ButtonType> result = alert.showAndWait();
			
			if(result.get() == ButtonType.OK && tempCst != null) {
				Alert confirmedCustomerKill = new Alert(AlertType.INFORMATION);
				confirmedCustomerKill.setTitle("Customer Kill Confirmed");
				confirmedCustomerKill.setHeaderText(null);
				confirmedCustomerKill.setContentText("Kill Confirmed.");
				confirmedCustomerKill.showAndWait();
				libraryMenu.removeCustomer(tempCst.getCustomerId());
				refreshTable();
			} else {
				alert.close();
				refreshTable();
			}
		}
	}

/*
	 * Alert alert = new Alert(AlertType.ERROR); alert.setTitle("ERROR");
	 * alert.setHeaderText("Error during adding a customer");
	 * alert.setContentText("The customer is already registered in the library.");
	 * 
	 * alert.showAndWait(); } } }
	 */
	@FXML
	private void addBookEvent(ActionEvent event) throws IOException {

		if (event.getSource().equals(addBookBtn)) {

			TextInputDialog dialog = new TextInputDialog("Add Book");
			dialog.setTitle("Text Input Dialog");
			dialog.setHeaderText("Add a new book.");

			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));


			TextField ISBN = new TextField();
			ISBN.setPromptText("");

			TextField Title = new TextField();
			Title.setPromptText("");

			TextField Genre = new TextField();
			Genre.setPromptText("");

			TextField Author = new TextField();
			Author.setPromptText("");

			TextField Publisher = new TextField();
			Publisher.setPromptText("");
			
			TextField Shelf = new TextField();
			Shelf.setPromptText("");

			grid.add(new Label("ISBN:"), 0, 0);
			grid.add(ISBN, 1, 0);
			grid.add(new Label("Title:"), 0, 1);
			grid.add(Title, 1, 1);
			grid.add(new Label("Genre:"), 0, 2);
			grid.add(Genre, 1, 2);
			grid.add(new Label("Author:"), 0, 3);
			grid.add(Author, 1, 3);
			grid.add(new Label("Publisher:"), 0, 4);
			grid.add(Publisher, 1, 4);
			grid.add(new Label("Shelf:"), 0, 5);
			grid.add(Shelf, 1, 5);

			dialog.getDialogPane().setContent(grid);
			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				Boolean existing = false;
				Book newBook = new Book("", Title.getText(), Author.getText(), Shelf.getText(),
						Publisher.getText(), Genre.getText());

				if (!bookFieldsAreEmpty( Title, Author, Shelf, Publisher, Genre)) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("Book correctly added.");

					alert.showAndWait();
					libraryMenu.addBook("", Title.getText(), Author.getText(), Genre.getText(),
							Publisher.getText(), Shelf.getText());
					refreshTable();
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
				// tostring = int >> string
				// parseint = string >> int
				
				TextField ISBN = new TextField();
				ISBN.setPromptText(tempBook.getIsbn());

				TextField Title = new TextField();
				Title.setPromptText(tempBook.getTitle());

				TextField Genre = new TextField();
				Genre.setPromptText(tempBook.getGenre());

				TextField Author = new TextField();
				Author.setPromptText(tempBook.getAuthor());

				TextField Publisher = new TextField();
				Publisher.setPromptText(tempBook.getPublisher());
				
				TextField Shelf = new TextField();
				Shelf.setPromptText(tempBook.getShelf());

				grid.add(new Label("ISBN:"), 0, 0);
				grid.add(ISBN, 1, 0);

				grid.add(new Label("Title:"), 0, 1);
				grid.add(Title, 1, 1);

				grid.add(new Label("Genre:"), 0, 2);
				grid.add(Genre, 1, 2);

				grid.add(new Label("Author:"), 0, 3);
				grid.add(Author, 1, 3);

				grid.add(new Label("Publisher:"), 0, 4);
				grid.add(Publisher, 1, 4);
				
				grid.add(new Label("Shelf:"), 0, 5);
				grid.add(Shelf, 1, 5);


				dialog.getDialogPane().setContent(grid);
				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					Book modifyIntoThis= tempBook;
					if(!ISBN.getText().isEmpty())
						modifyIntoThis.setIsbn(ISBN.getText());
					if(!Title.getText().isEmpty())
						modifyIntoThis.setTitle(Title.getText());
					if (!Author.getText().isEmpty())
						modifyIntoThis.setAuthor(Author.getText());
					if (!Shelf.getText().isEmpty())
						modifyIntoThis.setShelf(Shelf.getText());
					if (!Publisher.getText().isEmpty())
						modifyIntoThis.setPublisher(Publisher.getText());
					if (!Genre.getText().isEmpty())
						modifyIntoThis.setGenre(Genre.getText());
					
					//tempBook=bookTable.getSelectionModel().getSelectedItem();
//					library.findBook(tempBook.getLid()).setTitle(Title.getText());
//					library.findBook(tempBook.getLid()).setAuthor(Author.getText());
//					library.findBook(tempBook.getLid()).setShelf(Shelf.getText());
//					library.findBook(tempBook.getLid()).setPublisher(Publisher.getText());
//					library.findBook(tempBook.getLid()).setGenre(Genre.getText());
					library.changeBookInformation(modifyIntoThis);
					refreshTable();
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
			refreshTable();
		} else {
			alert.close();
			refreshTable();
			}
		}
	}
	@FXML
	public void lendBookEvent(ActionEvent event) throws IOException{
		if (event.getSource() == lendBookBtn) {
			Stage window = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontend/resources/LendBook.fxml"));
			Parent root = loader.load();
			LendBookController lendBookController = loader.getController();
			
			lendBookController.setChosenBook(tempBook);
			lendBookController.setBasket(this.basket);
//			lendBookController.setLibraryMenu(libraryMenu);
//			lendBookController.setLibraryController(this);
	
			
			Scene newScene = new Scene(root);
			window.setTitle("Choose Customer");
			window.setScene(newScene);
			window.showAndWait();
			this.basket.clear();
		}
		cstCurrentBorrowedTable.getItems().clear();
		cstHistoryTable.getItems().clear();
		refreshTable();
	}
	@FXML
	public void returnBookEvent() {
/*		Alert confirmedReturn = new Alert(AlertType.CONFIRMATION);
		confirmedReturn.setTitle("Confirm Return");
		confirmedReturn.setHeaderText("Gotta give bakk em buhkz");
		confirmedReturn.setContentText("Return?");
		Optional <ButtonType> result = confirmedReturn.showAndWait();
		
*/		
		TextInputDialog dialog = new TextInputDialog("Return a book");
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		//search the record
		Record cstRec=findRecord();
		
		grid.add(new Label("customer: " + tempCst.getName()), 0, 0);
		grid.add(new Label("book: " + cstCurrentBorrowedTable.getSelectionModel().getSelectedItem().getTitle()), 0, 1);
		grid.add(new Label("date borrowed: " + cstRec.getDateTaken()), 0,2);
		grid.add(new Label("date returned: " + cstRec.getDateReturned()), 0, 3);
		grid.add(new Label("fee: " + library.lateReturnCharge(cstRec)), 0, 4);
		
		dialog.getDialogPane().setContent(grid);

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			Alert confirmation = new Alert(AlertType.INFORMATION);
			confirmation.setTitle("Book(s) returned");
			confirmation.setHeaderText(null);
			confirmation.setContentText("Book(s) Returned");
//			for (Book book : cstCurrentBorrowedTable.getSelectionModel().getSelectedItems()) {
				libraryMenu.returnBook(tempCst, cstCurrentBorrowedTable.getSelectionModel().getSelectedItem());
				confirmation.showAndWait();
			}
		refreshTable();
	}
	
////////////////////////////////////////TextField Checks//////////////////////////////////////////
	public Boolean bookFieldsAreEmpty(TextField a, TextField b, TextField c, TextField d, TextField e) {
		Boolean x = false;
		if (a.getText().isEmpty() || b.getText().isEmpty() || c.getText().isEmpty() || d.getText().isEmpty()
				|| e.getText().isEmpty()) {
			x = true;
		System.out.println("There's at least an empty field!");
		} else {
			x = false;
		}
		return x;
	}

	public Boolean cstFieldsAreEmpty(TextField a, TextField b, TextField c) {
		Boolean x = false; //wtf 
		if (a.getText().isEmpty() || b.getText().isEmpty() || c.getText().isEmpty()) {
			x = true;
		System.out.println("There's at least an empty field!");
		} else {
			x = false;
		}
		return x;
	}

	/////////////////////////////////////// CONVERSION/////////////////////////////////////////////
	/////////////////////////////////////// METHODS/////////////////////////////////////////////////
	protected ObservableList<Book> getObsBooks(ArrayList<Book> arrayToConvert) {
		return FXCollections.observableArrayList(arrayToConvert);
	}

	protected ObservableList<Customer> getObsCustomers(ArrayList<Customer> arrayToConvert) {
		return FXCollections.observableArrayList(arrayToConvert);
	}

	protected ObservableList<Record> getObsRecords(ArrayList<Record> arrayToConvert) {
		return FXCollections.observableArrayList(arrayToConvert);
	}
	protected ObservableList<History> getObsHistory(ArrayList<History> arrayToConvert) {
		return FXCollections.observableArrayList(arrayToConvert);
	}

	protected SimpleStringProperty getStringProperty(String string) {
		SimpleStringProperty convertedString = new SimpleStringProperty(string);
		return convertedString;
	}

	protected SimpleIntegerProperty getIntegerProperty(int number) {
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
	
	private void refreshTable() {
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

		filteredBooks = new FilteredList<>(getObsBooks(library.getAvailableBooks()), p -> true);
		filteredCustomers = new FilteredList<>(getObsCustomers(library.getCustomersList()), p -> true);

		filteredBooks.predicateProperty().bind(Bindings.createObjectBinding(
				() -> IDFilter.get()
						.and(titleFilter.get()
								.and(authorFilter.get().and(
										shelfFilter.get().and(publisherFilter.get().and(genreFilter.get()))))),
				IDFilter, titleFilter, authorFilter, shelfFilter, publisherFilter, genreFilter));

		filteredCustomers.predicateProperty()
				.bind(Bindings.createObjectBinding(() -> custNameFilter.get(), custNameFilter));

		// addlistener calls changelistener.changed
				IDFilterField.textProperty().addListener((observable, oldValue, newValue) -> {});
				titleFilterField.textProperty().addListener((observable, oldvalue, newvalue) -> {});
				authorFilterField.textProperty().addListener((observable, oldvalue, newvalue) -> {});
				shelfFilterField.textProperty().addListener((observable, oldvalue, newvalue) -> {});
				publisherFilterField.textProperty().addListener((observable, oldvalue, newvalue) -> {});
				genreFilterField.textProperty().addListener((observable, oldvalue, newvalue) -> {});

				custNameField.textProperty().addListener((observable, oldValue, newValue) -> {});

				// 3. Wrap the FilteredList in a SortedList.
				sortedBooks = new SortedList<>(filteredBooks);
				sortedCustomers = new SortedList<>(filteredCustomers);
				sortedPopularBooks = new SortedList<>(getObsBooks(library.getPopularBooksArray()));
				sortedAllBorrowed = new SortedList<>(getObsBooks(library.getListLentBooks()));

				// 4. Bind the SortedList comparator to the TableView comparator.
				sortedBooks.comparatorProperty().bind(bookTable.comparatorProperty());
				sortedCustomers.comparatorProperty().bind(customerTable.comparatorProperty());
				
				sortedPopularBooks.comparatorProperty().bind(popularBooksTable.comparatorProperty());
				sortedAllBorrowed.comparatorProperty().bind(allBorrowedBooksTable.comparatorProperty());

				// 5. Add sorted (and filtered) data to the table.

				bookTable.setItems(sortedBooks);
				customerTable.setItems(sortedCustomers);
				
				cstCurrentBorrowedTable.getItems().clear();
				
				ArrayList<Book> borrowCheck;
				if(tempCst != null) {
				borrowCheck = library.getBorrowedBooks(tempCst);
					if(borrowCheck != null) {
						cstCurrentBorrowedTable.setItems(getObsBooks(library.getBorrowedBooks(tempCst)));
					}
				}
				
				cstHistoryTable.getItems().clear();
				
				if(tempCst != null) {
				ArrayList<Book> fetchedBook = library.getCustomerHistoryArray(tempCst);
				if (!fetchedBook.isEmpty()) {
					cstHistoryTable.setItems(getObsBooks(library.getCustomerHistoryArray(tempCst)));
					}
				}
				
				allBorrowedBooksTable.setItems(sortedAllBorrowed);
				popularBooksTable.setItems(sortedPopularBooks);

	}
	
	public void undoBookSelection() {
		bookTable.getSelectionModel().clearSelection();
		tempBook = null;
	}
	public void undoCstSelection() {
		customerTable.getSelectionModel().clearSelection();
		tempCst = null;
	}
	@FXML
	private void resetSearchEvent(ActionEvent event) throws IOException {
		 
		if(event.getSource().equals(resetBtn)) {	
			IDFilterField.setText("");
			shelfFilterField.setText("");
			titleFilterField.setText("");
			publisherFilterField.setText("");
			authorFilterField.setText("");
			genreFilterField.setText("");
		}

	}
	
	public Record findRecord() {
		int i;
		ArrayList<Record> allRecs = library.getRecords();
		for (i=0; allRecs.get(i).getCustomerId() != tempCst.getCustomerId() && allRecs.get(i).getLid()
				!= cstCurrentBorrowedTable.getSelectionModel().getSelectedItem().getLid(); i++);
		System.out.println(allRecs.get(i).toString());
		return allRecs.get(i);
	
	}
	

}
