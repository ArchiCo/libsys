package backend;

import java.util.ArrayList;
import java.util.Collections;

import backend.FlexibleBookComparator.Order;

public class Library {
	protected ArrayList<Book> listBooks;
	protected ArrayList<Book> listLentBooks;
	protected ArrayList<Book> popularBooks;
	private Book book1, book2, book3;
	private static int days;
	private final static int DAILY_OVERDUE_FEE = 2;

	public Library() {
		this.listBooks = new ArrayList<Book>();
		this.listLentBooks = new ArrayList<Book>();
		book1 = new Book("Java", "978-0133761313", "Pearson", "Education", "", "Y. Daniel Liang");
		book2 = new Book("Math", "978-0073383095", "McGraw-Hill Education", "Education", "", "Kenneth H Rosen");
		book3 = new Book("Interfaces", "978-1449379704", "O'Reilly Media", "Education", "", "Jenifer Tidwell");
		addBook(book1);
		addBook(book2);
		addBook(book3);
	}

	public void addBook(Book book) {
		listBooks.add(book);
	}

	public void lendBook(Customer regCustomer, Book book, int duration) {
		this.listLentBooks.add(book);
		this.listBooks.remove(book);
		book.setLentCustomer(regCustomer);
		book.setLendDuration(duration);
		regCustomer.addToCustomerHistory(book);

	}

	public Book returnBook(Customer regCustomer) {
		for (Book s : this.listLentBooks) {
			if (s != null && s.getLentCustomer().equals(regCustomer))
				return s;
		}
		return null;
	}

	public void sortListBooksBy(FlexibleBookComparator.Order sortingBy) {
		FlexibleBookComparator comparator = new FlexibleBookComparator();
		comparator.setSortingBy(sortingBy);
		Collections.sort(this.listBooks, comparator);

	}

	public void printPopularBooks() {
		this.popularBooks = new ArrayList<Book>();
		if (this.listBooks.size() > 0) {
			for (Book libBook : this.listBooks) {
				boolean libExists = false;
				if (this.popularBooks.size() == 0) {
					popularBooks.add(libBook);
				} else
					for (Book popBook : this.popularBooks) {
						if (libBook.getISBN().equals(popBook.getISBN())) {
							libExists = true;
							break;
						}
					}
				if (libExists == false) {
					popularBooks.add(libBook);
				}
			}
			for (Book popBook : this.popularBooks) {
				for (Book libBook : this.listBooks) {
					if (libBook.getISBN().equals(popBook.getISBN()) && !libBook.equals(popBook)) {
						int newLent = popBook.getLentTimes() + libBook.getLentTimes();
						popBook.setLentTimes(newLent);
					}
				}
			}
		}

		if (this.listLentBooks.size() > 0) {
			for (Book lentBook : this.listLentBooks) {
				boolean lentExists = false;
				if (this.popularBooks.size() == 0) {
					popularBooks.add(lentBook);
				} else
					for (Book popBook : this.popularBooks) {
						if (lentBook.getISBN().equals(popBook.getISBN())) {
							lentExists = true;
							break;

						}
					}
				if (lentExists == false) {
					popularBooks.add(lentBook);
				}
			}
			for (Book popBook : this.popularBooks) {
				for (Book lentBook : this.listLentBooks) {
					if (lentBook.getISBN().equals(popBook.getISBN()) && !lentBook.equals(popBook)) {
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
		this.listBooks.add(book);
		this.listLentBooks.remove(book);
		book.setLentCustomer(null);
		book.setLendDuration(0);
	}

	public Book findBook(String ISBN) {
		for (Book s : this.listBooks) {
			if (s != null && s.getISBN().equals(ISBN))
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
