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
import javafx.scene.control.cell.PropertyValueFactory;
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
	//Time
	@FXML private Button advanceTimeBtn;
	@FXML private TextField toTheFutureField;
	@FXML private Label today;
	
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
//	@FXML private TableColumn<Book, String> cstHistBookAuthorCol;
//	@FXML private TableColumn<Book, String> cstHistBookGenreCol;
	
	// customer currently borrowed
	@FXML private TableView<Book> cstCurrentBorrowedTable;
	@FXML private TableColumn<Book, String> borrowIDCol;
	@FXML private TableColumn<Book, String> borrowNameCol;
	//delay table
	@FXML private TableView<Record> cstCurrentBorrowedDelayTable;
	@FXML private TableColumn<Record, String> borrowDelayDaysCol;
	@FXML private TableColumn<Record, String> borrowDelayFeeCol;
	@FXML private TableColumn<Record, String> borrowDelayAidCol;
	@FXML private TableColumn<Record, String> borrowDelayLidCol;
	
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
	@FXML private TableColumn<Book, String> allBorrowDelayCol;
	@FXML private TableColumn<Book, String> allBorrowDelayFeeCol;
	
	//all Books
	@FXML private TableView<Book> allBooksTable;
	@FXML private TableColumn<Book, Integer> allBooksIDCol;
	@FXML private TableColumn<Book, String> allBooksISBNCol;
	@FXML private TableColumn<Book, String> allBooksTitleCol;
	@FXML private TableColumn<Book, String> allBooksAuthorCol;
	@FXML private TableColumn<Book, String> allBooksShelfCol;
	@FXML private TableColumn<Book, String> allBooksPublisherCol;
	@FXML private TableColumn<Book, String> allBooksGenreCol;

	//basket table
	@FXML private TableView<Book> basketTable;
	@FXML private TableColumn<Book, Integer> basketIDCol;
	@FXML private TableColumn<Book, String> basketTitleCol;
	@FXML private TableColumn<Book, String> basketAuthorCol;
	@FXML private TableColumn<Book, String> basketIsbnCol;
	
	//Records Table
	@FXML private TableView<Record> recordTable;
	@FXML private TableColumn<Record, String> aidCol;
	@FXML private TableColumn<Record, String> cidCol;
	@FXML private TableColumn<Record, String> lidCol;
	@FXML private TableColumn<Record, String> dateTakenCol;
	@FXML private TableColumn<Record, String> dateDueCol;
	@FXML private TableColumn<Record, String> dateReturnCol;
	@FXML private TableColumn<Record, String> delayFeeCol;
	@FXML private TableColumn<Record, String> delayDaysCol;
	
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
	//Arraylists
	private FilteredList<Book> filteredBooks;
	private FilteredList<Customer> filteredCustomers;
	
	ObservableList<Record> cstCurrentBookRecs = FXCollections.observableArrayList();
	
	private SortedList<Book> sortedBooks;
	private SortedList<Customer> sortedCustomers;
	private SortedList<Book> customerHistory;
	private SortedList<Book> sortedPopularBooks;
	private SortedList<Book> sortedAllBorrowed;
	private SortedList<Book> sortedCstCurrentBorrowed;
	private SortedList<Record> sortedRecords;
	private SortedList<Book> sortedAllBooks;
	
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
		
		
		//time label
		today.setText(library.getDate().toString());
		//making sure only numbers are allowed for time input
		toTheFutureField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            toTheFutureField.setText(newValue.replaceAll("[^\\d]", ""));
		        }}});
		
		//////////////////////////////Book Table//////////////////////////////////////////////
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
			}});
		bookAuthorCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedAuthor = getStringProperty(book.getAuthor());
				return convertedAuthor;
			}});
		bookShelfCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedShelf = getStringProperty(book.getShelf());
				return convertedShelf;
			}});
		bookPublisherCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedPublisher = getStringProperty(book.getPublisher());
				return convertedPublisher;
			}});
		bookGenreCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedGenre = getStringProperty(book.getGenre());
				return convertedGenre;
			}});	
		bookISBNCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedIsbn = getStringProperty(book.getIsbn());
				return convertedIsbn;
			}});
		//////////////////////All Books Table///////////////////////////////
		allBooksIDCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> param) {
				Book book = param.getValue();
				SimpleIntegerProperty convertedLid = getIntegerProperty(book.getLid());
				return convertedLid.asObject();
			}});
		allBooksISBNCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedIsbn = getStringProperty(book.getIsbn());
				return convertedIsbn;
			}});
		allBooksTitleCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedTitle = getStringProperty(book.getTitle());
				return convertedTitle;
			}});
		allBooksAuthorCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedAuthor = getStringProperty(book.getAuthor());
				return convertedAuthor;
			}});
		allBooksShelfCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedShelf = getStringProperty(book.getShelf());
				return convertedShelf;
			}});
		allBooksPublisherCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedPublisher = getStringProperty(book.getPublisher());
				return convertedPublisher;
			}});
		allBooksGenreCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedGenre = getStringProperty(book.getGenre());
				return convertedGenre;
			}});	
/////////////////////////////Customer Table///////////////////////////////////////////////
		customerIDCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Customer, String> param) {
				Customer customer = param.getValue();
				SimpleStringProperty convertedCstId = getStringProperty(customer.getCustomerId());
				return convertedCstId;
			}});
		customerNameCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Customer, String> param) {
				Customer customer = param.getValue();
				SimpleStringProperty convertedCstName = getStringProperty(customer.getName());
				return convertedCstName;
			}});
////////////////////////////////////////////////////////////////////////////
//////////////////////////////BorrowedBooks Table//////////////////////////////////////////////
		borrowIDCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedId = getStringProperty(Integer.toString(book.getLid()));
				return convertedId;
			}});
		borrowNameCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedTitle = getStringProperty(book.getTitle());
				return convertedTitle;
			}});
//////////////////////Delay Table/////////////////////////
		borrowDelayLidCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Record, String> param){
				Record record = param.getValue();
				SimpleStringProperty convertedID = getStringProperty(Integer.toString(record.getLid()));
				return convertedID;
			}});
		borrowDelayDaysCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Record,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Record, String> param) {
				Customer customer = tempCst;
				Record record = param.getValue();
				SimpleStringProperty notDelayed = getStringProperty("0");
				long delayDays = library.exceededDays(record);
				SimpleStringProperty convertedDays = getStringProperty(Long.toString(library.exceededDays(record)));
				if (delayDays > 0) { return convertedDays; }
				else { return notDelayed; }
			}});
		borrowDelayFeeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Record, String> param){
				Record record = param.getValue();
				SimpleStringProperty noFee = getStringProperty("0");
				long fee = library.lateReturnCharge(record);
				SimpleStringProperty convertedFee = getStringProperty(Long.toString(library.lateReturnCharge(record)));
				if (fee > 0) {
				return convertedFee;
				}
				else {
				return noFee;
				}
			}});
		borrowDelayAidCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Record, String> param){
				Record record = param.getValue();
				SimpleStringProperty convertedAid = getStringProperty(Integer.toString(record.getArchiveId()));
				return convertedAid;
			}});
////////////////////////////////////////////////////////////////////////////
///////////////////////////////history table/////////////////////////////////////////////
		cstHistBookIDCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param){
				Book book = param.getValue();
				SimpleStringProperty convertedBookId = getStringProperty(Integer.toString(book.getLid()));
				return convertedBookId;
			}});
		cstHistBookTitleCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedTitle = getStringProperty(book.getTitle());
				return convertedTitle;
			}});
/*		cstHistBookAuthorCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedAuthor = getStringProperty(book.getAuthor());
				return convertedAuthor; 
			}});
		cstHistBookGenreCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedGenre = getStringProperty(book.getGenre());
				return convertedGenre;
			}});
*/
////////////////////////////////////////////////////////////////////////////
//////////////////////////All Borrowed Table//////////////////////////////////////////////////
		allBorrowIdCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedId = getStringProperty(Integer.toString(book.getLid()));
				return convertedId;
			}});
		allBorrowTitleCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedTitle = getStringProperty(book.getTitle());
				return convertedTitle;
			}});
		allBorrowAuthorCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedAuthor = getStringProperty(book.getAuthor());
				return convertedAuthor;
			}});
		allBorrowPublisherCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedPublisher = getStringProperty(book.getPublisher());
				return convertedPublisher;
			}});
		allBorrowGenreCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedGenre = getStringProperty(book.getGenre());
				return convertedGenre;
			}});
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
/*		allBorrowDelayFeeCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				Book book = param.getValue();
				SimpleStringProperty convertedFee = getStringProperty(Long.toString(book.));
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
			}});
		popTimesBorrowedCol.setCellValueFactory (new Callback<TableColumn.CellDataFeatures<Book, Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> param) {
				Book book = param.getValue();
				SimpleIntegerProperty convertedLentTimes = getIntegerProperty(book.getLentTimes());
				return convertedLentTimes.asObject();
			}});
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
		
		//////////////////////////////Records///////////////////////////////
		aidCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Record, String> param){
				Record record = param.getValue();
				SimpleStringProperty convertedAid = getStringProperty(Integer.toString(record.getArchiveId()));
				return convertedAid;
			}
		});
		cidCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Record, String> param){
				Record record = param.getValue();
				SimpleStringProperty convertedCid = getStringProperty(record.getCustomerId());
				return convertedCid;
			}
		});
		lidCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Record, String> param){
				Record record = param.getValue();
				SimpleStringProperty convertedLid = getStringProperty(Integer.toString(record.getLid()));
				return convertedLid;
			}
		});
		dateTakenCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Record, String> param){
				Record record = param.getValue();
				SimpleStringProperty convertedTaken = getStringProperty(record.getDateTaken().toString());
				return convertedTaken;
			}
		});
		dateDueCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Record, String> param){
				Record record = param.getValue();
				LocalDate convertedDue = record.getDateDue();
				return getStringProperty(convertedDue.toString());
			}
		});
		dateReturnCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Record, String> param){
				Record record = param.getValue();
				SimpleStringProperty notReturned = getStringProperty("Not Returned Yet");
				LocalDate convertedReturn = record.getDateReturned();
				if (convertedReturn != null) {
				return getStringProperty(convertedReturn.toString());
				}
				else {
				return notReturned;
				}
			}
		});
		delayFeeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Record, String> param){
				Record record = param.getValue();
				SimpleStringProperty noFee = getStringProperty("0");
				long fee = library.lateReturnCharge(record);
				SimpleStringProperty convertedFee = getStringProperty(Long.toString(library.lateReturnCharge(record)));
				if (fee > 0) {
				return convertedFee;
				}
				else {
				return noFee;
				}
			}
		});
		delayDaysCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Record, String> param){
//				ArrayList<Book> borrowCheck = library.getBorrowedBooks(tempCst);
				Record record = param.getValue();
				SimpleStringProperty notDelayed = getStringProperty("0");
				long delayDays = library.exceededDays(record);
				SimpleStringProperty convertedDays = getStringProperty(Long.toString(library.exceededDays(record)));
				if (delayDays > 0) { return convertedDays; }
				
				else { return notDelayed; }
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
		//removing books from basket//
		basketTable.setRowFactory( rawr -> {
		    TableRow<Book> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            Book rowData = row.getItem();
		            System.out.println(rowData);
		            basket.remove(rowData);    
		        }
		    });
		    return row ;
		});
		
		//binding disabled property to tableview size//
		//disabling lendbookbtn when there are no books in basket//
		//disabling all other relevant buttons
		lendBookBtn.disableProperty().bind(Bindings.size(basket).isEqualTo(0));
		editBookBtn.disableProperty().bind(bookTable.getSelectionModel().selectedItemProperty().isNull());
		removeBookBtn.disableProperty().bind(bookTable.getSelectionModel().selectedItemProperty().isNull());
		//customer table disable buttons//
		editCustomerBtn.disableProperty().bind(customerTable.getSelectionModel().selectedItemProperty().isNull());		
		removeCustomerBtn.disableProperty().bind(customerTable.getSelectionModel().selectedItemProperty().isNull());
		returnBookBtn.disableProperty().bind(cstCurrentBorrowedTable.getSelectionModel().selectedItemProperty().isNull());
	
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
		sortedRecords = new SortedList<>(getObsRecords(library.getRecords()));
		sortedAllBooks = new SortedList<>(getObsBooks(library.getListBooks()));
	
		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedBooks.comparatorProperty().bind(bookTable.comparatorProperty());
		sortedCustomers.comparatorProperty().bind(customerTable.comparatorProperty());
		sortedAllBorrowed.comparatorProperty().bind(allBorrowedBooksTable.comparatorProperty());
		sortedPopularBooks.comparatorProperty().bind(popularBooksTable.comparatorProperty());
		sortedRecords.comparatorProperty().bind(recordTable.comparatorProperty());
		sortedAllBooks.comparatorProperty().bind(allBooksTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		bookTable.setItems(sortedBooks);
		basketTable.setItems(basket);
		customerTable.setItems(sortedCustomers);
		allBooksTable.setItems(sortedAllBooks);		
		allBorrowedBooksTable.setItems(sortedAllBorrowed);
		popularBooksTable.setItems(sortedPopularBooks);
		
		recordTable.setItems(sortedRecords);
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
	
				tempCst = customerTable.getSelectionModel().getSelectedItem();
				System.out.println("customer row " + customerTable.getSelectionModel().getSelectedIndex());
				if (newValue != null) {
					cstIDLabel.setText(newValue.getCustomerId());
					cstNameLabel.setText(newValue.getName());
					cstPhoneLabel.setText(Integer.toString(newValue.getPhoneNumber()));
					cstAddressLabel.setText(newValue.getAddress());
					
					//show individual customer history
				ArrayList<Book> borrowCheck = library.getBorrowedBooks(tempCst);
				
				//setting secondary tables according to each customer
				if(borrowCheck != null) {
					cstCurrentBorrowedTable.setItems(getObsBooks(library.getBorrowedBooks(tempCst)));
					cstCurrentBorrowedDelayTable.setItems(getObsRecords(library.getBorrowedRecords(tempCst)));
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
				//allowing shift click in currently borrowed
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
		//making sure the buttons pressed are correct and dealing with each
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

				if (!bookFieldsAreEmpty( Title, Author, Shelf, Publisher, Genre)) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("Book correctly added.");

					alert.showAndWait();
					libraryMenu.addBook(ISBN.getText(), Title.getText(), Author.getText(), Genre.getText(),
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
					//putting back new info, and ignoring existing info
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
		Alert confirmedReturn = new Alert(AlertType.CONFIRMATION);
		confirmedReturn.setTitle("Confirm Return");
		confirmedReturn.setHeaderText("Gotta give bakk em buhkz");
		confirmedReturn.setContentText("Return?");
		Optional <ButtonType> result = confirmedReturn.showAndWait();
		
		if (result.get() == ButtonType.OK) {
//			ObservableList<Book> booklist = cstCurrentBorrowedTable.getSelectionModel().getSelectedItems();
			if (cstCurrentBorrowedTable.getSelectionModel().getSelectedItems().size() == 1) {
				TextInputDialog dialog = new TextInputDialog("Return a book");
				GridPane grid = new GridPane();
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(20, 150, 10, 10));

				//search the record
				Book selectedBook = cstCurrentBorrowedTable.getSelectionModel().getSelectedItem();
				Record cstRec = findRecord(tempCst, selectedBook.getLid());
						//library.findRecord(tempCst, selectedBook);
				
				grid.add(new Label("Customer: " + tempCst.getName()), 0, 0);
				grid.add(new Label("Book: " + selectedBook.getTitle()), 0, 1);
				grid.add(new Label("Date Borrowed: " + cstRec.getDateTaken()), 0,2);
				grid.add(new Label("Date Returned: " + cstRec.getDateReturned()), 0, 3);
				grid.add(new Label("Fee: " + library.lateReturnCharge(cstRec)), 0, 4);
				
				dialog.getDialogPane().setContent(grid);

				Optional<String> result1 = dialog.showAndWait();
				if (result1.isPresent()) {
					Alert confirmation = new Alert(AlertType.INFORMATION);
					confirmation.setTitle("Book(s) returned");
					confirmation.setHeaderText(null);
					confirmation.setContentText("Book(s) Returned");
					library.returnBook(selectedBook, cstRec);
					confirmation.showAndWait();
					}
				}
			if (cstCurrentBorrowedTable.getSelectionModel().getSelectedItems().size() > 1) {
				for (Book book : cstCurrentBorrowedTable.getSelectionModel().getSelectedItems()) {
					TextInputDialog dialog = new TextInputDialog("Return a book");
					GridPane grid = new GridPane();
					grid.setHgap(10);
					grid.setVgap(10);
					grid.setPadding(new Insets(20, 150, 10, 10));

					//search the record
					Record cstRec = findRecord(tempCst, book.getLid());
							//library.findRecord(tempCst, selectedBook);
					
					grid.add(new Label("Customer: " + tempCst.getName()), 0, 0);
					grid.add(new Label("Book: " + book.getTitle()), 0, 1);
					grid.add(new Label("Date Borrowed: " + cstRec.getDateTaken()), 0,2);
					grid.add(new Label("Date Returned: " + cstRec.getDateReturned()), 0, 3);
					grid.add(new Label("Fee: " + library.lateReturnCharge(cstRec)), 0, 4);
					
					dialog.getDialogPane().setContent(grid);

					Optional<String> result1 = dialog.showAndWait();
					if (result1.isPresent()) {
						Alert confirmation = new Alert(AlertType.INFORMATION);
						confirmation.setTitle("Book(s) returned");
						confirmation.setHeaderText(null);
						confirmation.setContentText("Book(s) Returned");
						library.returnBook(book, cstRec);
						confirmation.showAndWait();
						}	
					}
				}	
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
		//rebind all filters
		//recreate new lists and arrays from scratch
		//rebind all predicates
		//re add all listeners in filters
		//re-set all arrays into tables
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
				sortedRecords = new SortedList<>(getObsRecords(library.getRecords()));
				sortedAllBooks = new SortedList<>(getObsBooks(library.getListBooks()));

				// 4. Bind the SortedList comparator to the TableView comparator.
				sortedBooks.comparatorProperty().bind(bookTable.comparatorProperty());
				sortedCustomers.comparatorProperty().bind(customerTable.comparatorProperty());
				sortedPopularBooks.comparatorProperty().bind(popularBooksTable.comparatorProperty());
				sortedAllBorrowed.comparatorProperty().bind(allBorrowedBooksTable.comparatorProperty());
				sortedRecords.comparatorProperty().bind(recordTable.comparatorProperty());
				sortedAllBooks.comparatorProperty().bind(allBooksTable.comparatorProperty());
				
				// 5. Add sorted (and filtered) data to the table.

				bookTable.setItems(sortedBooks);
				customerTable.setItems(sortedCustomers);

				allBorrowedBooksTable.setItems(sortedAllBorrowed);
				popularBooksTable.setItems(sortedPopularBooks);
				recordTable.setItems(sortedRecords);
				allBooksTable.setItems(sortedAllBooks);
				
			//secondary tables	refresh
				cstCurrentBorrowedTable.getItems().clear();
				ArrayList<Book> borrowCheck;
				if(tempCst != null) {
				borrowCheck = library.getBorrowedBooks(tempCst);
					if(borrowCheck != null) {
						cstCurrentBorrowedTable.setItems(getObsBooks(library.getBorrowedBooks(tempCst)));
						cstCurrentBorrowedDelayTable.setItems(getObsRecords(library.getBorrowedRecords(tempCst)));
					}
				}
				cstHistoryTable.getItems().clear();
				
				if(tempCst != null) {
				ArrayList<Book> fetchedBook = library.getCustomerHistoryArray(tempCst);
				if (!fetchedBook.isEmpty()) {
					cstHistoryTable.setItems(getObsBooks(library.getCustomerHistoryArray(tempCst)));
					}
				}
				


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
	
	public Record findRecord(Customer chosenCustomer, int lid) {
		//not really findRecord anymore...
		ArrayList<Record> borrowedRecs = library.getBorrowedRecords(chosenCustomer);
		//takes all the borrowed records, looks for the right record with Lid
		Record foundRec = borrowedRecs.get(0);
		//this is due to the fact that 1 customer may borrow 2 books of the same name
		for (Record record : borrowedRecs) {
			if (record.getLid() == lid) {
				foundRec = record;
			}
		}
		return foundRec;
	}
	
	@FXML 
	private void advanceTimeEvent(ActionEvent event) {
		if(event.getSource().equals(advanceTimeBtn) && !toTheFutureField.getText().isEmpty()) {
		System.out.println(library.getDate());
		libraryMenu.advanceTime(Integer.parseInt(toTheFutureField.getText()));
		System.out.println(library.getDate());
		}
		refreshTable();
	}
	

}
