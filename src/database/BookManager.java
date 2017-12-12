package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import datatype.Book;
import datatype.Customer;
import datatype.Record;

public class BookManager {
	
	/*int    lid    = Integer.parseInt(books.getString(Books.BOOKS_LID_COLUMN));
	 String isbn   = books.getString(Books.BOOKS_ISBN_COLUMN);
    String title  = books.getString(Books.BOOKS_TITLE_COLUMN);
    String genre  = books.getString(Books.BOOKS_GENRE_COLUMN);
    String author = books.getString(Books.BOOKS_AUTHOR_COLUMN);
    String publisher = books.getString(Books.BOOKS_PUBLISHER_COLUMN);
	 Date   publicationDate = books.getDate(Books.BOOKS_PUBLICATION_DATE_COLUMN);
	 String shelf = books.getString(Books.BOOKS_SHELF_COLUMN);	*/
	
	private final int LID_COL              = 1;
	private final int ISBN_COL             = 2;
	private final int TITLE_COL            = 3;
	private final int GENRE_COL            = 4;
	private final int AUTHOR_COL           = 5;
	private final int PUBLISHER_COL        = 6;
	private final int PUBLICATION_DATE_COL = 7;
	private final int SHELF_COL            = 8;
	
	private String destination = "LibSys.Books";
	
	private final String SQL_INSERT = "insert into " + destination + "(isbn, title, genre, author, publisher, publication_date, shelf) values (?, ?, ?, ?, ?, ?, ?)";
	private final String SQL_DELETE = "delete from " + destination + " where lid = ?";
	private final String SQL_SELECT = "select * from " + destination + " where lid = ?";
	
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
			throw new IOException ("[CustomerManager, add] The Customers collection is invalid.");
		}
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_INSERT);
			) {
			
			db.getConnection().setAutoCommit(false);
			for (Book book: books) {
				stmt.setString(1, book.getIsbn());
				stmt.setString(2, book.getTitle());
				stmt.setString(3, book.getGenre());
				stmt.setString(4, book.getAuthor());
				stmt.setString(5, book.getPublisher());
				stmt.setDate  (6, book.getPublicationDate());
				stmt.setString(7, book.getShelf());
				stmt.addBatch();
			}
			stmt.executeBatch();
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
			
		} catch (SQLException e) {
			System.out.println("[BookManager, add] SQL ERROR: " + e.getMessage());
			return false;
		} finally {
			
		}
	}
	
	boolean deleteByLid(Book book) throws Exception{
		return deleteByLid(pack(book));
	}
	
	boolean deleteByLid(ArrayList<Book> books) throws Exception {
		if (books == null || books.isEmpty()) {
			throw new Exception ("[BookManager, deleteByLid] The Books collection is invalid.");
		}
		try (Connection con = db.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SQL_DELETE);
				) {
				
				con.setAutoCommit(false);
				for (Book book: books) {
					stmt.setInt(1, book.getLid());
					stmt.addBatch();
				}
				stmt.executeBatch();
				con.commit();
				con.setAutoCommit(true);
				return true;
				
			} catch (SQLException e) {
				System.out.println("[BookManager, deleteByLid] SQL ERROR: " + e.getMessage());
				return false;
			} finally {
				
			}
	}

	public ArrayList<Book> fetchAll(){

		try { 
			Connection con = db.getConnection();
		  PreparedStatement stmt = con.prepareStatement("select * from LibSys.Customers;");
		  return fetch(stmt);	  
		} catch (SQLException e) {
			System.out.println("[BookManager, fetchAll] SQL ERROR: " + e.getMessage());
			return new ArrayList<Book>();
		} finally {	
		}
	}
	
	public ArrayList<Book> fetch(int lid) {
		try (Connection con = db.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SQL_SELECT);
			) {
		  
		  stmt.setInt(1, lid); 
		  return fetch(stmt);	  
		} catch (SQLException e) {
			System.out.println("[CustomersManager, fetchById] SQL ERROR: " + e.getMessage());
			return new ArrayList<Book>();
		} finally {
					
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
					Date   publication = books.getDate  (PUBLICATION_DATE_COL);
					String shelf       = books.getString(SHELF_COL);

					fetchedBooks.add(new Book(lid, isbn, title, genre, author, 
							                  publisher, publication,  shelf));
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
		return db.createTable("LibSys.books",
				  "(lid serial primary key,"
				+ "isbn text not null,"
				+ "title text not null,"
				+ "genre text not null,"
				+ "author text not null,"
				+ "publisher text not null,"
				+ "publication_date date not null,"
				+ "shelf text not null)");
	}
	
	public boolean dropTable() throws Exception {
		return db.dropTable("LibSys.books");
	}
	
/*		
		String query = "";
		for (int i = 0; i  < newBooks.size(); i++) {
			query += "('"  + newBooks.get(i).getLid()  + 
					 "','" + newBooks.get(i).getIsbn() + 
					 "','" + newBooks.get(i).getTitle() +
					 "','" + newBooks.get(i).getGenre() +
					 "','" + newBooks.get(i).getAuthor() +
					 "','" + newBooks.get(i).getPublisher() +
					 "','" + newBooks.get(i).getPublicationDateTxt() +
					 "')";
			if (i < newBooks.size()-1) {
				query += ",";
			}
		}
		//return postgresql.insertQueryWithSchema(schema, "books", query);
		return false;
	}	
	
	public void save(List<Entity> entities) throws SQLException {
	    try (
	        Connection connection = database.getConnection();
	        PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
	    ) {
	        int i = 0;

	        for (Entity entity : entities) {
	            statement.setString(1, entity.getSomeProperty());
	            // ...

	            statement.addBatch();
	            i++;

	            if (i % 1000 == 0 || i == entities.size()) {
	                statement.executeBatch(); // Execute every 1000 items.
	            }
	        }
	    }
	}
*/	
}
