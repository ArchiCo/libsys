package database;
/*
import java.sql.Array;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import database.controllers.Database;
import database.structure.Column;
import database.structure.Table;
import database.structure.pattern.Books;
import datatype.Book;
import datatype.Record;

public class SQLController {
	final int DEBUG_TIMEOUT = 5000;
	public String schema  = "LibSys";

	Database postgresql;
	SQLController (String host, 
				   int    port,
				   String dbName,
				   String user, 
				   String pass) {
		
		postgresql = new Database(host, port, dbName, user, pass);
	}
	
	public String getSchema() {
		return schema;
	}
	
	public boolean isConnected() {
		try {
			return postgresql.isConnected();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isLive() {
		
		try {
			return postgresql.isLive(DEBUG_TIMEOUT);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void printColumnNames(String table) {
			List<String> columns = returnColumns(table);
			if (columns != null) {
				for (String columnName : columns) {
					System.out.println(columnName);
				}
			} else {
				System.out.println("No result returned.");
			}
	}
	
	
	public List<String> returnColumns(String table) {
		try {
			ResultSet rs = postgresql.query("SELECT * FROM " + table);
			if (rs != null) {
				List<String> columns = new ArrayList<String>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					columns.add(rs.getMetaData().getColumnName(i));
				}
				rs.close();
				return columns;
			} else {
				System.out.println("No result returned.");
			}
		} catch (Exception e) {
			
		}
		return null;
	}
	
	public void createSchema(String schemaName) {
		boolean status = postgresql.createSchema(schemaName);
		System.out.println("Schema creation status: " + status);
	}
	
	public void createTable(Table table) {
		String structureQuery = "(";
		String primaryKeyQuery = "PRIMARY KEY(";
		int keyCount = 0;
		for (int i = 0; i < table.getColumnSize(); i++) {
			if (table.isPrimaryKey(i)) {
				if (keyCount > 0 ) {
					primaryKeyQuery += ", ";
				}
				primaryKeyQuery += table.getColumnName(i);
				keyCount++;
			}
			structureQuery += table.getColumnName(i) + " " +
					          table.getColumnType(i) + ", ";
		}
		structureQuery += primaryKeyQuery + "));";
		if (table.getSchemaName() != null || !table.getSchemaName().isEmpty()) {
			createTable(table.getSchemaName(), 
					    table.getTableName(), 
					    structureQuery);
		} else {
			createTable(table.getTableName(), structureQuery);
		}
		
	}
	
	public void createTable(String schemaName, String tableName, String structure) {
		boolean status = postgresql.createTable(schemaName, tableName, structure);
		System.out.println("Table creation status: " + status);
	}
	
	public void createTable(String tableName, String structure) {
		boolean status = postgresql.createTable(tableName, structure);
		System.out.println("Table creation status: " + status);
	}
	
	public void dropTable(String schemaName, String tableName) {
		boolean status = postgresql.dropTable(schemaName, tableName);
		System.out.println("Table dropping status: " + status);
	}	
	
	public void dropTable(String tableName) {
		boolean status = postgresql.dropTable(tableName);
		System.out.println("Table dropping status: " + status);
	}
	
	public boolean tableIsPresent(String tableName) {
		try {
			ResultSet rs = postgresql.getTableName(tableName.toLowerCase());
			if (rs.next()) {
			      return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return false;
	}
	
	public boolean addBook(Book newBook) {
		ArrayList<Book> newBooks = new ArrayList<Book>();
		newBooks.add(newBook);
		return addBooks(newBooks);
	}
	
	public boolean addBooks(ArrayList<Book> newBooks) {
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
		return postgresql.insertQueryWithSchema(schema, "books", query);
	}
	
	public boolean removeBook(Book oldBook) {
		ArrayList<Book> oldBooks = new ArrayList<Book>();
		return oldBooks.add(oldBook);
	}
	
	public boolean removeBooks(ArrayList<Book> oldBooks) {
		
		try {
			ResultSet books = postgresql.deleteQuery(schema, "books", deleteBy, conditions);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
	public void debugInsert(String table, String columns, String values) {
		boolean status = postgresql.insertQueryWithSchema("LibSys", table, columns, values);
		//System.out.println("Insert operation status: " + status);
	}
}*/