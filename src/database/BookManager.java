package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import backend.Book;
import backend.Customer;

public class BookManager {
	

	private final int LID_COL              = 1;
	private final int ISBN_COL             = 2;
	private final int TITLE_COL            = 3;
	private final int GENRE_COL            = 4;
	private final int AUTHOR_COL           = 5;
	private final int PUBLISHER_COL        = 6;
	private final int SHELF_COL            = 7;
	
	private String destination = "LibSys.Books";
	
	private final String SQL_INSERT = "insert into " + destination + "(isbn, title, genre, author, publisher, shelf) values (?, ?, ?, ?, ?, ?)";
	private final String SQL_DELETE = "delete from " + destination + " where lid = ?";
	private final String SQL_SELECT = "select * from " + destination + " where lid = ?";
	private final String SQL_SELECT_ALL = "select * from " + destination;
	private final String SQL_SELECT_AVAILABLE = "select * from " + destination + " where lid not in (select lid from LibSys.Records where returned is null)";
	private final String SQL_SELECT_BORROWED = "select * from " + destination + " where lid in (select lid from LibSys.Records where returned is null)";
	private final String SQL_UPDATE = "update " + destination + " set isbn = ?, title = ?, genre = ?, author = ?, publisher = ?, shelf = ? where lid = ?";
	private final String SQL_ORDER_BY_AUTHOR = " order by author";
	private final String SQL_ORDER_ASC = " asc";
	private final String SQL_ORDER_DESC = " desc";
	
	private Database db;
	
	BookManager(Database db) throws Exception {
		if (db == null) {
			throw new Exception("[BookManager, Constructor]: Invalid Database object.");
		}
		this.db = db;
	}
	
	private ArrayList<Book> pack(Book book) throws IOException{
		if (book == null) {
			throw new IOException("[BookManager, packer] The Book is invalid.");
		}
		ArrayList<Book> books = new ArrayList<Book>();
		books.add(book);
		return books;
	}
	
	public boolean add(Book book) throws IOException {
		return add(pack(book));
	}
	
	public boolean add(ArrayList<Book> books) throws IOException {
		if (books == null || books.isEmpty()) {
			throw new IOException ("[BookManager, add] The Book collection is invalid.");
		}
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_INSERT);
			) {

			for (Book book: books) {
				stmt.setString(1, book.getIsbn());
				stmt.setString(2, book.getTitle());
				stmt.setString(3, book.getGenre());
				stmt.setString(4, book.getAuthor());
				stmt.setString(5, book.getPublisher());
				stmt.setString(6, book.getShelf());
				stmt.addBatch();
			}
			stmt.executeBatch();
			return true;
			
		} catch (SQLException e) {
			System.out.println("[BookManager, add] SQL ERROR: " + e.getMessage());
			return false;
		} finally {
			
		}
	}
	
	public boolean modify(Book book) throws IOException {
		return modify(pack(book));
	}
	
	boolean modify(ArrayList<Book> books) throws IOException {
		if (books == null || books.isEmpty()) {
			throw new IOException("[BookManager, modify] The Customers collection is invalid.");
		}

		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_UPDATE)) {
			for (Book book : books) {
				stmt.setString(1, book.getIsbn());
				stmt.setString(2, book.getTitle());
				stmt.setString(3, book.getGenre());
				stmt.setString(4, book.getAuthor());
				stmt.setString(5, book.getPublisher());
				stmt.setString(6, book.getShelf());
				stmt.setInt(7, book.getLid());
				stmt.addBatch();
			}
			stmt.executeBatch();
			return true;

		} catch (SQLException e) {
			System.out.println("[CustomersManager, deleteById] SQL ERROR: " + e.getMessage());
			return false;
		}
	}
	
	public boolean delete(int lid) throws Exception {
		return delete(new Book(lid, null, null, null, null, null, null));
	}
	
	public boolean delete(Book book) throws Exception{
		return delete(pack(book));
	}
	
	boolean delete(ArrayList<Book> books) throws Exception {
		if (books == null || books.isEmpty()) {
			throw new Exception ("[BookManager, deleteByLid] The Books collection is invalid.");
		}
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_DELETE);
				) {			
				for (Book book: books) {
					stmt.setInt(1, book.getLid());
					stmt.addBatch();
				}
				stmt.executeBatch();
				return true;
				
			} catch (SQLException e) {
				System.out.println("[BookManager, deleteByLid] SQL ERROR: " + e.getMessage());
				return false;
			} finally {
				
			}
	}

	public ArrayList<Book> fetchAllByAuthor() {
		return fetch(SQL_SELECT_ALL + SQL_ORDER_BY_AUTHOR + SQL_ORDER_ASC);
	}
	
	public ArrayList<Book> fetchAvailable() {
		return fetch(SQL_SELECT_AVAILABLE);
	}

	public ArrayList<Book> fetchBorrowed() {
		return fetch(SQL_SELECT_BORROWED);
	}
	
	public ArrayList<Book> fetchAll() {
		return fetch(SQL_SELECT_ALL);
	}
	
	public ArrayList<Book> fetch(String query){
		try (PreparedStatement stmt = db.getConnection().prepareStatement(query)){
		  return fetch(stmt);	  
		} catch (SQLException e) {
			System.out.println("[BookManager, fetch] SQL ERROR: " + e.getMessage());
			return new ArrayList<Book>();
		}
	}
	
	public Book fetchByLid(int lid) {
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_SELECT)) {
		  
		  stmt.setInt(1, lid); 
		  ArrayList<Book> fetchedBooks = fetch(stmt);
			if (fetchedBooks.isEmpty()) {
				return null;
			} else {
				return fetchedBooks.get(0);
			}  
		} catch (SQLException e) {
			System.out.println("[BookManager, fetchById] SQL ERROR: " + e.getMessage());
			return null;
		}
	}
	
	private ArrayList<Book> fetch(PreparedStatement stmt) {
		try {
			ResultSet books = stmt.executeQuery();
			if (books.isBeforeFirst()) {
				ArrayList<Book> fetchedBooks = new ArrayList<Book>();
				while (books.next()) {
				    int    lid         = books.getInt   (LID_COL);
					String isbn        = books.getString(ISBN_COL);
					String title       = books.getString(TITLE_COL);
					String genre       = books.getString(GENRE_COL);
					String author      = books.getString(AUTHOR_COL);
					String publisher   = books.getString(PUBLISHER_COL);
					String shelf       = books.getString(SHELF_COL);

					fetchedBooks.add(new Book(lid, isbn, title, genre, author, 
							                  publisher, shelf));
					}
				return fetchedBooks;
				}	
		} catch (SQLException e) {
			System.out.println("[BookManager, fetch] SQL ERROR: " + e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<Book>();
	}
	
	public boolean createTable() throws Exception {
		return db.createTable(destination,
				  "(lid serial primary key,"
				+ "isbn text not null,"
				+ "title text not null,"
				+ "genre text not null,"
				+ "author text not null,"
				+ "publisher text not null,"
				+ "shelf text not null)");
	}
	
	public boolean dropTable() throws Exception {
		return db.dropTable(destination);
	}
}
