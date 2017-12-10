package backend;

import java.util.ArrayList;
import java.util.Collections;

import backend.FlexibleBookComparator.Order;
import frontend.resources.LibraryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

public class Library {

	private static int days;
	private final static int DAILY_OVERDUE_FEE = 2;
	
	//list of books arraylist
	protected ObservableList<Book> books = FXCollections.observableArrayList();
	protected ObservableList<Customer> customers = FXCollections.observableArrayList();
	protected ObservableList<Book> lentBooks = FXCollections.observableArrayList();
	protected ObservableList<Book> popularBooks = FXCollections.observableArrayList();

	public Library() {
		
		books.add(new Book("A1", "Dragons", "Nigel", "11A", "Longmaen", "revolutionary"));
		books.add(new Book("A2", "bumblebee", "God", "2AB", "Penguin",	"Religion"));
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
		books.add(new Book("Java", "978-0133761313", "Pearson", "Education", "", "Y. Daniel Liang"));
		books.add(new Book("Math", "978-0073383095", "McGraw-Hill Education", "Education", "", "Kenneth H Rosen"));
		books.add(new Book("Interfaces", "978-1449379704", "O'Reilly Media", "Education", "", "Jenifer Tidwell"));
		
		customers.add(new Customer("100","Salvatore","street 1",400));
		customers.add(new Customer("101","nigel","korsvagen",432432));
		customers.add(new Customer("102","idontknow","via sassari 8",12121));
		customers.add(new Customer("103","newname","yeeeea",5005043));
		customers.add(new Customer("104","heeeey","stora ringvagen",32190));
		customers.add(new Customer("1111", "Damn", "Next Door", 1029435));
		customers.add(new Customer("2222", "Egg", "Over there", 3959591));
		
		
	}

	public void addBook(Book book) {
		books.add(book);
	}

	public void lendBook(Customer regCustomer, Book book, int duration) {
		this.lentBooks.add(book);
		this.books.remove(book);
		book.setLentCustomer(regCustomer);
		book.setLendDuration(duration);
		regCustomer.addToCustomerHistory(book);

	}

	public Book returnBook(Customer regCustomer) {
		for (Book s : this.lentBooks) {
			if (s != null && s.getLentCustomer().equals(regCustomer))
				return s;
		}
		return null;
	}

	public void sortListBooksBy(FlexibleBookComparator.Order sortingBy) {
		FlexibleBookComparator comparator = new FlexibleBookComparator();
		comparator.setSortingBy(sortingBy);
		Collections.sort(this.books, comparator);

	}

	public void printPopularBooks() {
		if (this.books.size() > 0) {
			for (Book libBook : this.books) {
				boolean libExists = false;
				if (this.popularBooks.size() == 0) {
					popularBooks.add(libBook);
				} else
					for (Book popBook : this.popularBooks) {
						if (libBook.getIsbn().equals(popBook.getIsbn())) {
							libExists = true;
							break;
						}
					}
				if (libExists == false) {
					popularBooks.add(libBook);
				}
			}
			for (Book popBook : this.popularBooks) {
				for (Book libBook : this.books) {
					if (libBook.getIsbn().equals(popBook.getIsbn()) && !libBook.equals(popBook)) {
						int newLent = popBook.getLentTimes() + libBook.getLentTimes();
						popBook.setLentTimes(newLent);
					}
				}
			}
		}

		if (this.lentBooks.size() > 0) {
			for (Book lentBook : this.lentBooks) {
				boolean lentExists = false;
				if (this.popularBooks.size() == 0) {
					popularBooks.add(lentBook);
				} else
					for (Book popBook : this.popularBooks) {
						if (lentBook.getIsbn().equals(popBook.getIsbn())) {
							lentExists = true;
							break;

						}
					}
				if (lentExists == false) {
					popularBooks.add(lentBook);
				}
			}
			for (Book popBook : this.popularBooks) {
				for (Book lentBook : this.lentBooks) {
					if (lentBook.getIsbn().equals(popBook.getIsbn()) && !lentBook.equals(popBook)) {
						int newLent = popBook.getLentTimes() + lentBook.getLentTimes();
						popBook.setLentTimes(newLent);
					}
				}
			}
		}
		sortPopularBooksBy(Order.Popularity);

	}

	 	public void sortPopularBooksBy(FlexibleBookComparator.Order sortingBy) {
		FlexibleBookComparator comparator = new FlexibleBookComparator();
		comparator.setSortingBy(sortingBy);
		Collections.sort(this.popularBooks, comparator);

	}
	

	public void retrieveBook(Book book) {
		this.books.add(book);
		this.lentBooks.remove(book);
		book.setLentCustomer(null);
		book.setLendDuration(0);
	}

	public Book findBook(String ISBN) {
		for (Book s : this.books) {
			if (s != null && s.getIsbn().equals(ISBN))
				return s;
		}
		return null;
	}

	public void advanceDays(int days) {
		Library.days = days;
	}

	public int getAdvancedDays() {
		return Library.days;
	}

	public int lateReturnCharge(Book book) {
		return (Library.days - book.getLendDuration()) * DAILY_OVERDUE_FEE;
	}
}
