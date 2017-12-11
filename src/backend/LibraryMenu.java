package backend;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import backend.FlexibleBookComparator.Order;

public class LibraryMenu {
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

	public LibraryMenu() {
		this.library = new Library();
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
					findCustomer();
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
					for (Book book : this.library.getListBooks()) {
						System.out.println("");
						System.out.println(book);
					}
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
		int phoneNumber = sc.nextInt();
		sc.nextLine();
		String libraryID;
		do {
			libraryID = generateRandomChars("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", 4);
		} while (this.library.findCustomer(libraryID) != null);
		this.library.registerCustomer(libraryID, name, address, phoneNumber);
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
		for (Book book : customer.getCustomerHistory()) {
			System.out.println(book);
		}
	}

	private void lendBook() {
		Customer foundCustomer = findCustomer();
		System.out.println("");
		if (foundCustomer != null) {
			searchBook();
			Book foundBook = printBook();
			System.out.println("");
			System.out.println("Today's date is " + this.library.getDate() + ".");
			System.out.println("");
			this.library.lendBook(foundCustomer, foundBook);
			System.out.println("Please return the book in 2 weeks.");

		}
	}

	private void returnBook() {
		Customer foundCustomer = findCustomer();
		System.out.println("");
		if (foundCustomer != null) {
			Book foundLentBook = findLentBook();
			if (foundLentBook != null) {
				Records foundRecord = this.library.findRecord(foundCustomer, foundLentBook);
				this.library.returnBook(foundLentBook, foundRecord);
				long exceededDays = this.library.exceededDays(foundRecord);
				if (exceededDays > 0) {
					System.out.println("You have exceeded borrowing duration by " + exceededDays + " days.");
					System.out.println("You have been charged " + foundRecord.getFine() + " SEK.");
				}
			}
		}
	}

	private Book findLentBook() {
		System.out.print("Please enter the book's ID: ");
		int lid = sc.nextInt();
		for (Book s : this.library.getListLentBooks()) {
			if (s != null && s.getLid() == lid) {
				return s;
			}
		}
		return null;
	}

	private int searchBook() {
		System.out.print("Please enter author's name: ");
		String authorName = sc.nextLine();
		int counter = 0;
		for (Book s : this.library.getListBooks()) {
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
