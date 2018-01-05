package backend;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

import database.DataController;
import datatype.Book;
import datatype.Customer;
import datatype.History;
import datatype.Record;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Library extends Application{
	private ArrayList<Book> popularBooks;
	private final static long DAILY_OVERDUE_FEE = 2;
	private LocalDate today;
	private DataController database; 
	
	private Consumer<Book> registerBookCallback;
	private Consumer<Book> unregisterBookCallback;
	
	public Library(){
		try {
			database = new DataController();
			today = LocalDate.now();

			this.registerBookCallback = book -> {};
			this.unregisterBookCallback = book -> {};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Application starting method
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/frontend/resources/Library.fxml"));
			Parent root = loader.load();
		    Scene scene = new Scene(root);
		    primaryStage.setTitle("Library System");
			
		    
		    primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void registerCustomer(String customerId, String name, String address, String phoneNumber) {
		Customer newCustomer = new Customer(customerId, name, address, phoneNumber);
		database.registerCustomer(newCustomer);

	}

	public Customer findCustomer(String customerId) {
		Customer customer = database.customers().fetchByCid(customerId);
		if (customer != null) {
			return customer;
		} else {
			return null;
		}
	}
	
	public boolean deregisterCustomer(String customerId) {
		return database.deregisterCustomer(customerId);
	}
	
	public boolean removeBook(int lid) {
		return database.removeBook(lid);
	}
	
	public boolean removeRecord(int aid) {
		return database.removeRecord(aid);
	}
	
	public boolean changeCustomerInformation(Customer customer) {
		try {
			return database.customers().modify(customer);
		} catch (Exception e) {
			return false;
		}
	}

	public boolean changeBookInformation(Book book) {
		try {
			return database.books().modify(book);
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean changeRecordInformation(Record record) {
		try {
			return database.records().modify(record);
		} catch (Exception e) {
			return false;
		}
	}
	
	public void addBook(String isbn, String title, String genre, String author, String publisher, int shelf) {
		Book newBook = new Book(isbn, title, genre, author, publisher, shelf);
		addBook(newBook);
	}
	
	public void addBook(Book book) {
		database.addBook(book);
		registerBookCallback.accept(book);
	}
	
	public void addRecord(String cid, int lid, String dateTaken, String dateDue) {
		addRecord(new Record(cid, lid, LocalDate.parse(dateTaken), LocalDate.parse(dateDue)));
	}

	public void addRecord(Record record) {
		try {
			database.records().add(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void lendBook(String libraryID, Book foundBook) {
		Customer foundCustomer = findCustomer(libraryID);
		System.out.println("");
		if (foundCustomer != null) {
			System.out.println("\nToday's date is " + this.getDate() + ".\n");
			lendBook(foundCustomer, foundBook);
			System.out.println("Please return the book in 2 weeks.\n");


		}
	}
	
	public void lendBook(Customer regCustomer, Book book) {
		if (regCustomer != null && book != null) {
			LocalDate dueDate = today.plusDays(14);
			Record newRecord = new Record(regCustomer.getCid(), book.getLid(), today, dueDate);
			database.records().add(newRecord);
			System.out.println(newRecord);
		}
	}

	public void returnBook(Book book, Record record) {
		record.setDateReturned(today);
		record.setFine(lateReturnCharge(record));
		database.records().modify(record);
	}

	public ArrayList<Book> getBooksByAuthor() {
		return database.books().fetchAllByAuthor();
	}

	public void printPopularBooks() {
		System.out.println(database.books().fetchPopularity());
	}
	
	public ArrayList<Book> getPopularBooksArray() {
		return database.books().fetchPopularity();
	}

	public Book findBook(String ISBN) {
		for (Book s : this.getAvailableBooks()) {
			if (s != null && s.getIsbn().equals(ISBN))
				return s;
		}
		return null;
	}
	
	public Book findBook(int lid) {
		return database.books().fetchByLid(lid);
	}

	public long exceededDays(Record record) {
		long daysLate = record.getDateDue().until(today, ChronoUnit.DAYS);
		if (daysLate < 0) {
			daysLate = 0;
		}
		if (record.getDateReturned() != null) {
			daysLate = record.getDateDue().until(record.getDateReturned(), ChronoUnit.DAYS);
		}
		return daysLate;
	}

	public long lateReturnCharge(Record record) {
		return exceededDays(record) * DAILY_OVERDUE_FEE;
	}

	public LocalDate getDate() {
		return today;
	}
	
	public void printCustomerHistory(String cid) {
		ArrayList<History> fetchedHistory = getCustomerHistory(cid);
		for (History history: fetchedHistory) {
			System.out.println(history.getBook());
			System.out.println(history.getRecord().getDatesToString());
		}
	}
	
	public ArrayList<History> getCustomerHistory(String cid) {
		return database.customers().fetchHistory(cid);
	}

	public void advanceDays(int days) {
		today = today.plusDays(days);
		System.out.println("Advanced time by " + days + " days.");
	}

	public ArrayList<Record> getRecords() {
		return database.records().fetchAll();
		//return records;
	}
	
	public ArrayList<Customer> getCustomersList() {
		return database.customers().fetchAll();
	}
	
	public ArrayList<Book> getAvailableBooks() {
		return database.books().fetchAvailable();
	}

	public ArrayList<Book> getListBooks() {
		return database.books().fetchAll();
	}

	public ArrayList<Book> getListLentBooks() {
		return database.books().fetchBorrowed();
	}

	public ArrayList<Book> getBorrowedBooks(Customer customer) {
		return database.books().fetchBorrowed(customer);
	}
	
	public ArrayList<Book> getBorrowedBooks(Customer customer, int lid) {
		return database.books().fetchBorrowed(customer, lid);
	}
	public ArrayList<Record> getBorrowedRecords(Customer customer) {
		ArrayList<Book> borrowedBooks = getBorrowedBooks(customer);
		ArrayList<Record> borrowedRecords = new ArrayList<>();
		ArrayList<Record> sameBorrowedBookRecs = new ArrayList<>();
		
		for (Book book : borrowedBooks) {
		sameBorrowedBookRecs = findSameCustomerBookRecList(customer, book);
		borrowedRecords.add(findLatestBorrowedRecord(sameBorrowedBookRecs));
		}
		return borrowedRecords;
	}
	public ArrayList<Book> getListDelayedBooks() {
		return database.books().fetchDelayed(today);
	}
	
	public Record findRecord(Customer regCustomer, Book book) {
		for (Record record : database.records().fetchAll()) {
			if (record != null && record.getCid().equals(regCustomer.getCid()))
				if (record.getLid() == book.getLid())
					return record;
			}
		return null;
	}
	
	
	public Record findRecord(int aid) {
		Record record = database.records().fetchById(aid);
		if (record != null) {
			return record;
		} else {
			return null;
		}
	}
	
	//first takes all of the records that the same customer borrowed with the same exact book
	public ArrayList<Record> findSameCustomerBookRecList(Customer customer, Book book) {
		ArrayList<Record> foundRecs = new ArrayList<>();
		for (Record record : database.records().fetchAll()) {
			if (record != null && record.getCid().equals(customer.getCid())) {
				if (record.getLid() == book.getLid()) {
					foundRecs.add(record);
					System.out.println(record);
				}
			}
		}
		return foundRecs;
	}

	//second takes only the latest of all of these records to return 
	public Record findLatestBorrowedRecord(ArrayList<Record> foundRecs) {
		Record latestRec = foundRecs.get(0);
		if (foundRecs != null) {
			System.out.println(foundRecs.toString());
			if(foundRecs.size() > 1) {
				for (Record record : foundRecs) {
					if (record.getAid() > latestRec.getAid()) {
						latestRec = record;
					}
				}
			}
		}
		return latestRec;
	}
		
					

	public ArrayList<Book> getPopularBooks() {
		return popularBooks;
	}

	public void setPopularBooks(ArrayList<Book> popularBooks) {
		this.popularBooks = popularBooks;
	}
	public void setBookRegisterCallback(Consumer<Book> registerBookCallback) {
		this.registerBookCallback = registerBookCallback;
	}
	public void setBookUnregisterCallback(Consumer<Book> unregisterBookCallback) {
		this.unregisterBookCallback = unregisterBookCallback;
	}
	public ArrayList<Book> getCustomerHistoryArray(Customer customer) {
		ArrayList<Book> customerHistoryBook = new ArrayList<Book>();
	if (getCustomerHistory(customer.getCid()) != null) {
		for (History s : getCustomerHistory(customer.getCid())) {
			if (s != null)
				customerHistoryBook.add(s.getBook());
			}
		return customerHistoryBook;
	}
	else {
		return customerHistoryBook;
	}
	}

	
	public void modifyCustomer(String libraryID, String name, String address, String phoneNumber) {
		Customer customer = this.findCustomer(libraryID);
		if (customer != null) {

			if (!name.isEmpty()) {
				customer.setName(name);
			}
			if (!address.isEmpty()) {
				customer.setAddress(address);
			}
			try {
				if (!phoneNumber.isEmpty()) {
					customer.setPhone(phoneNumber);
				}
			} catch (Exception e) {
			}
			this.changeCustomerInformation(customer);
		} else {
			System.out.println("Customer is not registered in the system.");
		}
	}
	
	public void registerCustomer(String name, String address, String phoneNumber) {

		String libraryID;
		do {
			libraryID = generateRandomChars("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", 4);
		} while (this.findCustomer(libraryID) != null);
		
		this.registerCustomer(libraryID, name, address, phoneNumber);
		System.out.println("Here's your library card!");
		Customer foundCustomer = this.findCustomer(libraryID);
		System.out.print(foundCustomer);
	}
	
	public static String generateRandomChars(String candidateChars, int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
		}
		return sb.toString();
	}
	
	public void removeCustomer(String libraryID) {
		this.deregisterCustomer(libraryID);
	}
	


}