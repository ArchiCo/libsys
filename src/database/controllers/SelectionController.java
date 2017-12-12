package database.controllers;
/*
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.structure.pattern.Books;
import datatype.Book;
import datatype.Record;

public class SelectionController {
	
	private Database db;
	SQLController controller;
	
	SelectionController(Database db, SQLController controller) {
		this.db = db;
	}
	
	public ArrayList<Book> fetchAllBooks() {
		return fetchBooks("*", DEFAULT_SCHEMA + "." + BOOKS_TABLE);
	}
	
	public ArrayList<Book> fetchBooks(String searchItem, String searchDestination) {
		return fetchBooks(searchItem, searchDestination, null, null);
	}
	
	
	public ArrayList<Book> fetchBooks(String searchItem, String searchDestination, 
			                          String searchKey,  String searchCondition) {
		try {
		   ResultSet books = db.selectQuery(searchItem, searchDestination, 
					           searchKey,  searchCondition);
		   if (books != null) {
		      ArrayList<Book> fetchedBooks = new ArrayList<Book>();			
			  while (books.next()) {
			     int    lid    = Integer.parseInt(books.getString(Books.BOOKS_LID_COLUMN));
				 String isbn   = books.getString(Books.BOOKS_ISBN_COLUMN);
	             String title  = books.getString(Books.BOOKS_TITLE_COLUMN);
	             String genre  = books.getString(Books.BOOKS_GENRE_COLUMN);
	             String author = books.getString(Books.BOOKS_AUTHOR_COLUMN);
	             String publisher = books.getString(Books.BOOKS_PUBLISHER_COLUMN);
				 Date   publicationDate = books.getDate(Books.BOOKS_PUBLICATION_DATE_COLUMN);
				 String shelf = books.getString(Books.BOOKS_SHELF_COLUMN);				    
				 ArrayList<Record> records = null; //7findRecord("lid", Integer.toString(lid));
				 fetchedBooks.add(new Book(lid, isbn, title, genre, author, publisher, 
							               publicationDate, shelf, records));
			  }
		      return fetchedBooks;
	       }
	    } catch (SQLException e) {
            e.printStackTrace();
	    }
	    return null;
	}

	public Record fetchRecord(String parameter, String condition) {
		ArrayList<Record> fetchedResult = null;
		fetchedResult = fetchRecords(parameter, condition);
		if (fetchedResult == null) {
			return null;
		} else {
			return fetchedResult.get(0);
		}
	}
	
	public ArrayList<Record> fetchRecords(String parameter, String condition) {
		try {
			ResultSet records = db.selectQuery("*", DEFAULT_SCHEMA + "." + "records", parameter, condition);
			if (records != null) {
				ArrayList<Record> fetchedRecords = new ArrayList<Record>();
				while (records.next()) {
				    int    archiveId    = Integer.parseInt(records.getString(RECORDS_ARCHIVE_ID_COLUMN));
					String username     = records.getString(RECORDS_USERNAME_COLUMN);
					int    lid          = Integer.parseInt(records.getString(RECORDS_LID_COLUMN));
					Date   dateTaken    = records.getDate(RECORDS_DATE_TAKEN_COLUMN);
					Date   dateDue      = records.getDate(RECORDS_DATE_DUE_COLUMN);
					Date   dateReturned = records.getDate(RECORDS_DATE_RETURNED_COLUMN);
					fetchedRecords.add(new Record(archiveId, username, lid, 
							                      dateTaken, dateDue , dateReturned));
				}
				return fetchedRecords;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}*/
