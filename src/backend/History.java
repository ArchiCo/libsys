package backend;

public class History {
	private Customer customer;
	private Book book;
	private Record record;

	public History(Customer customer, Book book, Record record) {
		this.customer = customer;
		this.book = book;
		this.record = record;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public Book getBook() {
		return book;
	}
	
	public Record getRecord() {
		return record;
	}
}
