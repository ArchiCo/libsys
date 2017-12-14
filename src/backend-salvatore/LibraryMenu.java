package backend;

import frontend.*;
import frontend.resources.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.TimeZone;

import backend.FlexibleBookComparator.Order;
import frontend.resources.LibraryController;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;

import java.util.Observable;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Pair;
import sun.security.jgss.LoginConfigImpl;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class LibraryMenu extends Application{
	public static final int CUSTOMER_REGISTRATION = 1;
	public static final int PRINT_CUSTOMER = 2;
	public static final int LEND_BOOK = 3;
	public static final int RETURN_BOOK = 4;
	public static final int ADD_BOOK = 5;
	public static final int ADVANCE_TIME = 6;
	public static final int SORT_BOOK = 7;
	public static final int PRINT_CUSTOMER_HISTORY = 8;
	public static final int PRINT_POPULAR_BOOKS = 9;
	public static final int QUIT = 11;
	private Scanner sc;
	private Library library;

	private RegisteredCustomer regCustomer;
	private LoginController loginController;
	private LibraryController libraryController;
	
	private Stage primaryStage;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			this.primaryStage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(LibraryMenu.class.getResource("/frontend/resources/Login.fxml"));
			Parent root = loader.load();
		    Scene scene = new Scene(root);
		    primaryStage.setTitle("Library System");

		    //gets the controller of where the program starts
			LoginController loginController = loader.getController();
			//sets library menu reference to this instance of the librarymenu? i think
			loginController.setLibraryMenu(this);
		    
		    primaryStage.setScene(scene);
			primaryStage.show();
			
						
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public LibraryMenu() {
		this.library = new Library();
		this.regCustomer = new RegisteredCustomer();
		this.libraryController = new LibraryController(this);
		
		sc = new Scanner(System.in);
		
	}

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
					printCustomer();
					break;
				case LEND_BOOK:
					lendBook();
					break;
				case RETURN_BOOK:
					returnBook();
					break;
				case ADD_BOOK:
					addBook();
					break;
				case ADVANCE_TIME:
					advanceTime();
					break;
				case PRINT_CUSTOMER_HISTORY:
					printCustomerHistory();
					break;

				case SORT_BOOK:
					library.sortListBooksBy(Order.Author);
					for (Book book : this.library.books) {
						System.out.println("");
						System.out.println(book);
					}
					break;

				case PRINT_POPULAR_BOOKS:
					library.printPopularBooks();
					for (Book popularBook : this.library.popularBooks) {
						System.out.println("");
						System.out.println(popularBook);
					}
					break;
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
	
	public ObservableList<Book> getBooks() {
		return library.books;
	}
	public ObservableList<Customer> getCustomers() {
		return library.customers;
	}

	private void printMenuOptions() {
		System.out.println(" ");
		System.out.println(" Choose an option below: ");
		System.out.println(" ");
		System.out.println(" 1. Register a customer. ");
		System.out.println(" 2. Print a customer's information. ");
		System.out.println(" 3. Borrow book. ");
		System.out.println(" 4. Return book. ");
		System.out.println(" 5. Add book. ");
		System.out.println(" 6. Advance time. ");
		System.out.println(" 7. Sort books by author's name. ");
		System.out.println(" 8. Print a customer's history");
		System.out.println(" 9. Show what books are most popular. ");
		System.out.println(" 11. Quit the program.");

	}

	private void registerCustomer() {
		System.out.print("Please enter customer's name: ");
		String name = sc.nextLine();
		System.out.print("Please enter customer's address: ");
		String address = sc.nextLine();
		System.out.print("Please enter customer's phone number: ");
		String phoneNumber = sc.nextLine();
		sc.nextLine();
		String libraryID;
		do {
			libraryID = generateRandomChars("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", 4);
		} while (this.regCustomer.findCustomer(libraryID) != null);
		this.regCustomer.registerCustomer(libraryID, name, address, phoneNumber);
		System.out.println("Here's your library card!");
		Customer foundCustomer = regCustomer.findCustomer(libraryID);
		System.out.print(foundCustomer);
	}

	private Customer printCustomer() {
		System.out.print("Please show your library card: ");
		String libraryID = sc.nextLine();
		Customer foundCustomer = regCustomer.findCustomer(libraryID);
		if (foundCustomer != null) {
			System.out.print("");
			System.out.print(foundCustomer);
			return foundCustomer;
		} else
			System.out.println("You are not registered in the system");
		return null;
	}

	private void printCustomerHistory() {
		System.out.print("Please show your library card: ");
		String libraryID = sc.nextLine();
		Customer foundCustomer = regCustomer.findCustomer(libraryID);
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
		for (Book book : customer.getCustomerHistory()) {
			System.out.println(book);
		}
	}

	private void lendBook() {
		Customer foundCustomer = printCustomer();
		int borrowDays = 30;
		TimeZone timeZone = TimeZone.getTimeZone("UTC+1");
		Calendar calendar = Calendar.getInstance(timeZone);
		SimpleDateFormat bookDate = new SimpleDateFormat("EE dd MMM yyyy", Locale.ENGLISH);
		bookDate.setTimeZone(timeZone);

		System.out.println("");
		if (foundCustomer != null) {
			searchBook();
			Book foundBook = printBook();
			System.out.println("");
			System.out.println("Today's date is " + bookDate.format(calendar.getTime()) + ".");
			System.out.println("");
			this.library.lendBook(foundCustomer, foundBook, borrowDays);
			calendar.add(Calendar.DATE, borrowDays);
			System.out.println("Please return the book " + bookDate.format(calendar.getTime()) + ".");

		}
	}

	private void returnBook() {
		Customer foundCustomer = printCustomer();
		System.out.println("");
		if (foundCustomer != null) {
			Book book = this.library.returnBook(foundCustomer);
			int exceededDays = this.library.getAdvancedDays() - book.getLendDuration();
			if (exceededDays > 0)
				System.out.println("You have exceeded borrowing duration by " + exceededDays + " days.");
			System.out.println("You have been charged " + this.library.lateReturnCharge(book) + " SEK.");
			this.library.retrieveBook(book);
		}
	}

	private int searchBook() {
		System.out.print("Please enter author's name: ");
		String authorName = sc.nextLine();
		int counter = 0;
		for (Book s : this.library.books) {
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

	private void addBook() {
		System.out.print("Please enter book's title: ");
		String name = sc.nextLine();
		System.out.print("Please enter book's author: ");
		String author = sc.nextLine();
		System.out.print("Please enter book's genre: ");
		String genre = sc.nextLine();
		System.out.print("Please enter book's publisher: ");
		String publisher = sc.nextLine();
		System.out.print("Please enter book's ISBN: ");
		String isbn = sc.nextLine();
		Book b = new Book(name, isbn, publisher, genre, "", author);
		this.library.addBook(b);

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