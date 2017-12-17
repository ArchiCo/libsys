package backend;


import backend.*;
import java.util.Observable;
import frontend.resources.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import sun.security.jgss.LoginConfigImpl;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import backend.FlexibleBookComparator.Order;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LibraryMenu extends Application{
	public static final int CUSTOMER_REGISTRATION = 1;
	public static final int PRINT_CUSTOMER = 2;
	public static final int SHOW_CUSTOMERS = 3;
	public static final int MODIFY_CUSTOMER = 4;
	public static final int REMOVE_CUSTOMER = 5;
	
	public static final int ADD_BOOK = 6;
	public static final int PRINT_BOOK = 7;
	public static final int SHOW_BOOKS = 8;
	public static final int MODIFY_BOOK = 9;
	public static final int REMOVE_BOOK = 10;
	
	public static final int ADD_RECORD = 11;
	public static final int PRINT_RECORD = 12;
	public static final int SHOW_RECORDS = 13;
	public static final int MODIFY_RECORD = 14;
	public static final int REMOVE_RECORD = 15;
	
	public static final int LEND_BOOK = 16;
	public static final int RETURN_BOOK = 17;
	public static final int SORT_BOOK = 18;
	
	public static final int BORROWED_BOOKS = 19;
	public static final int DELAYED_BOOKS = 20;
	public static final int BORROWING_STATUS = 21;
	public static final int PRINT_POPULAR_BOOKS = 22;
	public static final int PRINT_CUSTOMER_HISTORY = 23;
	public static final int ADVANCE_TIME = 24;
	public static final int QUIT = 25;
	
	private Scanner sc;
	private Library library;

	public LibraryMenu() {
		this.library = new Library();
		sc = new Scanner(System.in);
	}
//////////////////////////////////////////////////////////////
	//Application starting method
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/frontend/resources/Login.fxml"));
			Parent root = loader.load();
		    Scene scene = new Scene(root);
		    primaryStage.setTitle("Library System");
			
		    
		    primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
//////////////////////////////////////////////////////////////
	
	public void run() {
		int option = 0;
		do {
			try {
				printMenuOptions();
				System.out.print(" Type the option number: ");

				option = sc.nextInt();
				sc.nextLine();
				switch (option) {
				
				case CUSTOMER_REGISTRATION:
					registerCustomer();
					break;
				case PRINT_CUSTOMER:
					findCustomer();
					break;
				case SHOW_CUSTOMERS:
					showCustomers();
					break;
				case MODIFY_CUSTOMER:
					modifyCustomer();
					break;
				case REMOVE_CUSTOMER:
					removeCustomer();
					break;
					
				case ADD_BOOK:
					addBook();
					break;
				case PRINT_BOOK:
					findBook();
					break;
				case SHOW_BOOKS:
					showBooks(library.getListBooks());
					break;
				case MODIFY_BOOK:
					modifyBook();
					break;
				case REMOVE_BOOK:
					removeBook();
					break;
					
				case ADD_RECORD:
					addRecord();
					break;
				case PRINT_RECORD:
					findRecord();
					break;
				case SHOW_RECORDS:
					showRecords();
					break;
				case MODIFY_RECORD:
					modifyRecord();
					break;
				case REMOVE_RECORD:
					removeRecord();
					break;
					
				case LEND_BOOK:
					lendBook();
					break;
				case RETURN_BOOK:
					returnBook();
					break;
				case SORT_BOOK:
					//library.sortListBooksBy(Order.Author);
					for (Book book : library.getBooksByAuthor()) {
						System.out.println("");
						System.out.println(book);
					}
					break;
					
				case BORROWED_BOOKS:
					showBooks(library.getListLentBooks());
					break;
				case DELAYED_BOOKS:
					showBooks(library.getListDelayedBooks());
					break;	
				case PRINT_POPULAR_BOOKS: 
					library.printPopularBooks();
					break;
				case PRINT_CUSTOMER_HISTORY:
					printCustomerHistory();
					break;
				case ADVANCE_TIME:
					advanceTime();
					break;

				/*
				 * case PRINT_POPULAR_BOOKS: library.printPopularBooks(); for (Book popularBook
				 * : this.library.getPopularBooks()) { System.out.println("");
				 * System.out.println(popularBook); } break;
				 */
				case QUIT:
					System.out.println();
					break;

				default:
					System.out.println("Option " + option + " is not valid.");
					System.out.println();
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Error: please input an interger not a String");
				sc.next();
			}
		} while (option != QUIT);

	}
//////////////////////////////////////////////////////////////////////////////////
	
	private void printMenuOptions() {
		System.out.println(" ");
		System.out.println(" Choose an option below: ");
		System.out.println(" ");
		System.out.println(" Administrative Commands: ");
		System.out.println("  1. Register a customer. ");
		System.out.println("  2. Print a customer's information. ");
		System.out.println("  3. Print all customers. ");
		System.out.println("  4. Modify customer's information. ");
		System.out.println("  5. Deregister customer. \n");
		
		System.out.println("  6. Add book. ");
		System.out.println("  7. Print book's information. ");	
		System.out.println("  8. Print all books. ");
		System.out.println("  9. Modify book's information. ");
		System.out.println(" 10. Remove book. \n");
		
		System.out.println(" 11. Add record. ");
		System.out.println(" 12. Print record's information. ");	
		System.out.println(" 13. Print all records. ");
		System.out.println(" 14. Modify records's information. ");
		System.out.println(" 15. Remove record. \n");		
		
		System.out.println("  G-graded Functions: ");
		System.out.println(" 16. Borrow book. ");
		System.out.println(" 17. Return book. ");
		System.out.println(" 18. Sort books by author's name. \n");
		
		System.out.println(" VG-graded Functions: ");
		System.out.println(" 19. Show all borrowed books. ");
		System.out.println(" 20. Show all delayed books. ");
		System.out.println(" 21. Print out borrowing status. ");
		System.out.println(" 22. Show what books are most popular. ");
		System.out.println(" 23. Print a customer's history");
		
		System.out.println(" 24. Advance time. ");
		System.out.println(" 25. Quit the program.");

	}

	public void registerCustomer(String name, String address, int phoneNumber) {
	/*	System.out.print("Please enter customer's name: ");
		String name = sc.nextLine();
		System.out.print("Please enter customer's address: ");
		String address = sc.nextLine();
		System.out.print("Please enter customer's phone number: ");
		int phoneNumber = sc.nextInt();
		sc.nextLine();
	*/
		String libraryID;
		do {
			libraryID = generateRandomChars("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", 4);
		} while (this.library.findCustomer(libraryID) != null);
		
		library.registerCustomer(libraryID, name, address, phoneNumber);
		System.out.println("Here's your library card!");
		Customer foundCustomer = library.findCustomer(libraryID);
		System.out.print(foundCustomer);
	}

	private Customer findCustomer() {
		System.out.print("Please show your library card: ");
		String libraryID = sc.nextLine();
		Customer foundCustomer = library.findCustomer(libraryID);
		if (foundCustomer != null) {
			System.out.print("");
			System.out.print(foundCustomer);
			return foundCustomer;
		} else
			System.out.println("You are not registered in the system");
		return null;
	}
	
	private void showCustomers() {
		try {
			ArrayList<Customer> customers = library.getCustomersList();
			System.out.println("Customers, total: " + customers.size());
			if (!customers.isEmpty()) {
				for (Customer customer : customers) {
					System.out.println(customer.toString());
				}
			} else {
				System.out.println("No customers registered in the system.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void modifyCustomer(String libraryID, String name, String address, String phoneNumber) {
//		System.out.print("Please show your library card: ");
//		String libraryID = sc.nextLine();
		
		Customer customer = library.findCustomer(libraryID);
		if (customer != null) {
		/*	System.out.print("Please enter new name: ");
			String name = sc.nextLine();
			System.out.print("Please enter new address: ");
			String address = sc.nextLine();
			System.out.print("Please enter new phone number: ");
			String phoneNumber = sc.nextLine();
		*/
			if (!name.isEmpty()) {
				customer.setName(name);
			}
			if (!address.isEmpty()) {
				customer.setAddress(address);
			}
			try {
				if (!phoneNumber.isEmpty()) {
					customer.setPhoneNumber(Integer.parseInt(phoneNumber));
				}
			} catch (Exception e) {
			}
			library.changeCustomerInformation(customer);
		} else {
			System.out.println("Customer is not registered in the system.");
		}
	}
	
	public void removeCustomer(String libraryID) {
	//	System.out.print("Please show your library card: ");
	//	String libraryID = sc.nextLine();
		library.deregisterCustomer(libraryID);
	}
	
	
	private void printCustomerHistory() {
		System.out.print("Please show your library card: ");
		String libraryID = sc.nextLine();
		Customer foundCustomer = library.findCustomer(libraryID);
		if (foundCustomer != null) {
			System.out.println("");
			customerHistory(foundCustomer);

		} else
			System.out.println("You are not registered in the system.");
	}

	private Book printBook() {
		System.out.print("Please choose the book that you want to borrow and enter the book's ISBN-13: ");
		String ISBN = sc.nextLine();
		Book foundBook = this.library.findBook(ISBN);
		if (foundBook != null) {
			System.out.print("");
			System.out.print(foundBook);
			return foundBook;
		} else
			System.out.println("No book found.");
		return null;
	}

	private void customerHistory(Customer customer) {
		/*for (Book book : customer.getCustomerHistory()) {
			System.out.println(book);
		}*/
		library.printCustomerHistory(customer.getCustomerId());
	}

	private void lendBook() {
		Customer foundCustomer = findCustomer();
		System.out.println("");
		if (foundCustomer != null) {
			searchBook();
			Book foundBook = printBook();
			System.out.println("");
			System.out.println("Today's date is " + library.getDate() + ".");
			System.out.println("");
			this.library.lendBook(foundCustomer, foundBook);
			System.out.println("Please return the book in 2 weeks.");

		}
	}

	private void returnBook() {
		Customer foundCustomer = findCustomer();
		System.out.println("");
		if (foundCustomer != null) {
			Book foundLentBook = findLentBook(foundCustomer);
			if (foundLentBook != null) {
				Record foundRecord = library.findRecord(foundCustomer, foundLentBook);
				library.returnBook(foundLentBook, foundRecord);
				long exceededDays = library.exceededDays(foundRecord);
				if (exceededDays > 0) {
					System.out.println("You have exceeded borrowing duration by " + exceededDays + " days.");
					System.out.println("You have been charged " + foundRecord.getFine() + " SEK.");
				}
			}
		}
	}

	private Book findLentBook(Customer customer) {
		System.out.print("Please enter the book's ID: ");
		int lid = sc.nextInt();
		sc.nextLine();
		ArrayList<Book> lentBooks = library.getBorrowedBooks(customer, lid);
		if (!lentBooks.isEmpty()) {
			return lentBooks.get(0);
		}
		return null;
	}

	private int searchBook() {
		System.out.print("Please enter author's name: ");
		String authorName = sc.nextLine();
		int counter = 0;
		for (Book s : library.getAvailableBooks()) {
			if (s != null && s.getAuthor().contains(authorName)) {
				System.out.println();
				System.out.println("===============================");
				System.out.println(s);
				System.out.println("===============================");
				counter++;
			}
		}
		if (counter == 0)
			System.out.println("No book found");
		return counter;
	}

	public void addBook(String isbn, String title, String genre,String author, String publisher, String shelf ) {
/*		System.out.print("Please enter book's ISBN: ");
		String isbn = sc.nextLine();
		System.out.print("Please enter book's title: ");
		String title = sc.nextLine();
		System.out.print("Please enter book's genre: ");
		String genre = sc.nextLine();
		System.out.print("Please enter book's author: ");
		String author = sc.nextLine();
		System.out.print("Please enter book's publisher: ");
		String publisher = sc.nextLine();
	*/
		this.library.addBook(isbn, title, genre, author, publisher, shelf);
	}
	
	private Book findBook() {
		System.out.print("Please enter book's library ID: ");
		int libraryID = sc.nextInt();
		Book foundBook = library.findBook(libraryID);
		if (foundBook != null) {
			System.out.print("");
			System.out.print(foundBook);
			return foundBook;
		} else
			System.out.println("The book is not registered in the system");
		return null;
	}
	
	private void showBooks(ArrayList<Book> books) {
		try {
			System.out.println("Books, total: " + books.size());
			for (Book book : books) {
				System.out.println(book.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void modifyBook() {
		System.out.print("Please enter book's library ID: ");
		int libraryID = sc.nextInt();
		sc.nextLine();
		Book book = library.findBook(libraryID);
		if (book != null) {

			System.out.print("Please enter new ISBN-13: ");
			String isbn = sc.nextLine();
			System.out.print("Please enter new title: ");
			String title = sc.nextLine();
			System.out.print("Please enter new author: ");
			String author = sc.nextLine();
			System.out.print("Please enter new genre: ");
			String genre = sc.nextLine();
			System.out.print("Please enter new publisher: ");
			String publisher = sc.nextLine();
			System.out.print("Please enter new shelf: ");
			String shelf = sc.nextLine();
			
			if (!isbn.isEmpty()) {
				book.setIsbn(isbn);
			}
			if (!title.isEmpty()) {
				book.setTitle(title);
			}
			if (!author.isEmpty()) {
				book.setAuthor(author);
			}
			if (!genre.isEmpty()) {
				book.setGenre(genre);
			}
			if (!publisher.isEmpty()) {
				book.setPublisher(publisher);
			}
			if (!shelf.isEmpty()) {
				book.setShelf(shelf);
			}
			library.changeBookInformation(book);
		} else {
			System.out.println("Book is not registered in the system.");
		}
	}

	public void removeBook(int libraryID) {
		System.out.print("Please enter book's library ID: ");
	//	int libraryID = sc.nextInt();
	//	sc.nextLine();
		library.removeBook(libraryID);
	}
	
	
	private void addRecord() {
		System.out.print("Please enter record's CID: ");
		String cid = sc.nextLine();
		System.out.print("Please enter record's LID: ");
		int lid = sc.nextInt();
		sc.nextLine();
		System.out.print("Please enter record's date taken: ");
		String taken = sc.nextLine();
		System.out.print("Please enter record's date returned: ");
		String due = sc.nextLine();
		library.addRecord(cid, lid, taken, due);
	}
	
	private Record findRecord() {
		System.out.print("Please enter record's archive ID: ");
		int archiveID = sc.nextInt();
		Record foundRecord = library.findRecord(archiveID);
		if (foundRecord != null) {
			System.out.print("");
			System.out.print(foundRecord);
			return foundRecord;
		} else
			System.out.println("The book is not registered in the system");
		return null;
	}
	
	private void showRecords() {
		try {
			ArrayList<Record> records = library.getRecords();
			System.out.println("Records, total: " + records.size());
			for (Record record : records) {
				System.out.println(record.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void modifyRecord() {
		System.out.print("Please enter record's archive ID: ");
		int id = sc.nextInt();
		sc.nextLine();
		Record record = library.findRecord(id);
		if (record != null) {

			System.out.print("Please enter new CID: ");
			String cid = sc.nextLine();
			System.out.print("Please enter new LID: ");
			int lid = sc.nextInt();
			sc.nextLine();
			System.out.print("Please enter new date taken: ");
			String taken = sc.nextLine();
			System.out.print("Please enter new date due: ");
			String due = sc.nextLine();
			System.out.print("Please enter new date returned: ");
			String returned = sc.nextLine();
			
			try {
				if (!cid.isEmpty()) {
					record.setLibraryID(cid);
				}
				if (lid > 0) {
					record.setLid(lid);
				}
				if (!taken.isEmpty()) {
					record.setDateTaken(LocalDate.parse(taken));
				}
				if (!due.isEmpty()) {
					record.setDateDue(LocalDate.parse(due));
				}
				if (!returned.isEmpty()) {
					record.setDateReturned(LocalDate.parse(returned));
				}
				library.changeRecordInformation(record);
			} catch (Exception e) {
				e.printStackTrace();;
			}
		} else {
			System.out.println("Book is not registered in the system.");
		}
	}

	private void removeRecord() {
		System.out.print("Please enter record's archive ID: ");
		int archiveID = sc.nextInt();
		sc.nextLine();
		library.removeRecord(archiveID);
	}
	
	private void borrowedBooks() {
		
	}
	
	
	private void advanceTime() {
		System.out.print("Enter number of days: ");
		int days = sc.nextInt();
		System.out.println("Advanced time by " + days + " days.");
		this.library.advanceDays(days);
	}

	public static String generateRandomChars(String candidateChars, int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		LibraryMenu program = new LibraryMenu();
		program.run();
	}
}
