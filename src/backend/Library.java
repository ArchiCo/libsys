package backend;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

import backend.FlexibleBookComparator.Order;
import database.Credentials;
import database.DataController;

public class Library {
	private ArrayList<Book> listBooks;
	private ArrayList<Book> listLentBooks;
	private ArrayList<Book> popularBooks;
	private ArrayList<Customer> customers;
	private ArrayList<Records> records;
	private Book book1, book2, book3;
	private final static long DAILY_OVERDUE_FEE = 2;
	private LocalDate today;
	private DataController database; 
	
	public Library() {
		try {
			database = new DataController();
			today = LocalDate.now();
			this.listBooks = new ArrayList<Book>();
			this.listLentBooks = new ArrayList<Book>();
			this.customers = new ArrayList<Customer>();
			this.records = new ArrayList<Records>();
			book1 = new Book("dragon", "100", "dragon", "dragon", "", "dragon");
			book2 = new Book("dog", "200", "dog", "dog", "", "dog");
			book3 = new Book("cat", "300", "cat", "cat", "", "cat");
			book1.setLid(1);
			book2.setLid(2);
			book3.setLid(3);
			addBook(book1);
			addBook(book2);
			addBook(book3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void registerCustomer(String customerId, String name, String address, int phoneNumber) {
		Customer newCustomer = new Customer(customerId, name, address, phoneNumber);
		database.registerCustomer(newCustomer);
		//this.customers.add(newCustomer);
	}

	public Customer findCustomer(String customerId) {
		Customer customer = database.customers().fetchByCid(customerId);
		if (customer != null) {
			return customer;
		} else {
			return null;
		}
		/*
		for (Customer customer : customers) {
			if (customer != null && customer.getCustomerId().equals(customerId));
				return customer;
		}
		return null;
		*/
	}
	
	public boolean deregisterCustomer(String customerId) {
		return database.deregisterCustomer(customerId);
	}

	public boolean changeCustomerInformation(Customer customer) {
		try {
			return database.customers().modify(customer);
		} catch (Exception e) {
			return false;
		}
	}
	
	public void addBook(Book book) {
		listBooks.add(book);
	}

	public void lendBook(Customer regCustomer, Book book) {
		if (regCustomer != null && book != null) {
			LocalDate dueDate = today.plusDays(14);
			Records newRecord = new Records(regCustomer.getCustomerId(), book.getLid(), today, dueDate);
			this.records.add(0, newRecord);
			this.listLentBooks.add(book);
			this.listBooks.remove(book);
		}
	}

	public void returnBook(Book book, Records record) {
		this.listBooks.add(book);
		this.listLentBooks.remove(book);
		record.setDateReturned(today);
		record.setFine(lateReturnCharge(record));
	}

	public void sortListBooksBy(FlexibleBookComparator.Order sortingBy) {
		FlexibleBookComparator comparator = new FlexibleBookComparator();
		comparator.setSortingBy(sortingBy);
		Collections.sort(this.listBooks, comparator);

	}

	public void printPopularBooks() {
		this.setPopularBooks(new ArrayList<Book>());
		if (this.listBooks.size() > 0) {
			for (Book libBook : this.listBooks) {
				boolean libExists = false;
				if (this.getPopularBooks().size() == 0) {
					getPopularBooks().add(libBook);
				} else
					for (Book popBook : this.getPopularBooks()) {
						if (libBook.getIsbn().equals(popBook.getIsbn())) {
							libExists = true;
							break;
						}
					}
				if (libExists == false) {
					getPopularBooks().add(libBook);
				}
			}
			for (Book popBook : this.getPopularBooks()) {
				for (Book libBook : this.listBooks) {
					if (libBook.getIsbn().equals(popBook.getIsbn()) && !libBook.equals(popBook)) {
						int newLent = popBook.getLentTimes() + libBook.getLentTimes();
						popBook.setLentTimes(newLent);
					}
				}
			}
		}

		if (this.listLentBooks.size() > 0) {
			for (Book lentBook : this.listLentBooks) {
				boolean lentExists = false;
				if (this.getPopularBooks().size() == 0) {
					getPopularBooks().add(lentBook);
				} else
					for (Book popBook : this.getPopularBooks()) {
						if (lentBook.getIsbn().equals(popBook.getIsbn())) {
							lentExists = true;
							break;

						}
					}
				if (lentExists == false) {
					getPopularBooks().add(lentBook);
				}
			}
			for (Book popBook : this.getPopularBooks()) {
				for (Book lentBook : this.listLentBooks) {
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
		Collections.sort(this.getPopularBooks(), comparator);

	}

	public Book findBook(String ISBN) {
		for (Book s : this.listBooks) {
			if (s != null && s.getIsbn().equals(ISBN))
				return s;
		}
		return null;
	}

	public long exceededDays(Records record) {
		return record.getDateDue().until(today, ChronoUnit.DAYS);
	}

	public long lateReturnCharge(Records record) {
		return exceededDays(record) * DAILY_OVERDUE_FEE;
	}

	public LocalDate getDate() {
		return today;
	}

	public void advanceDays(int days) {
		today = today.plusDays(days);
	}

	public ArrayList<Records> getRecords() {
		return records;
	}

	public ArrayList<Book> getListBooks() {
		return listBooks;
	}

	public ArrayList<Book> getListLentBooks() {
		return listLentBooks;
	}

	public Records findRecord(Customer regCustomer, Book book) {
		for (Records customer : records) {
			if (customer != null && customer.getCustomerId().equals(regCustomer.getCustomerId()))
				if (customer.getLid() == book.getLid())
					return customer;
		}
		return null;
	}

	public ArrayList<Book> getPopularBooks() {
		return popularBooks;
	}

	public void setPopularBooks(ArrayList<Book> popularBooks) {
		this.popularBooks = popularBooks;
	}
}