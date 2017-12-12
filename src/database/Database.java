package database;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.sun.rowset.JdbcRowSetImpl;

import datatype.Book;
import datatype.Customer;
import datatype.Record;

import java.sql.ResultSet;

class Database {
	private Credentials cred;
	private String recordsTable = "records", 
			       customersTable = "customers",
			       booksTable = "books",
				   schema = "LibSys";
	private Connection con;
	
	Database (Credentials newCredentials) throws SQLException {
		cred = newCredentials;
		con = DriverManager.getConnection 
                ("jdbc:postgresql://" + 
                 cred.getHostname() + ":" +
                 cred.getPort() + "/" + 
                 cred.getDatabase() + "?sslmode=require&user=" +
                 cred.getUsername() + "&password=" +
                 cred.getPassword());
	}
	
	Connection getConnection() {
		return con;
	}
	
	boolean createTable(String table, String parameters) throws Exception {
		try (PreparedStatement stmt = con.prepareStatement(
				"create table " + table + parameters)) {
			
			stmt.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			System.out.println("[Database, createTable] SQL ERROR: " + e.getMessage());
			return false;
		}
	}
	
	boolean dropTable(String table) throws Exception {
		try (PreparedStatement stmt = con.prepareStatement(
				"drop table " + table)) {
			
			stmt.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			System.out.println("[Database, dropTable] SQL ERROR: " + e.getMessage());
			return false;
		}
	}
	/*
	private boolean add(ArrayList<?> collection, String SQL_INSERT) {
		if (collection == null || collection.isEmpty()) {
			return false;
		}
		
		try (PreparedStatement stmt = con.prepareStatement(SQL_INSERT);
				) {
				
				con.setAutoCommit(false);
				
				if (collection.get(0) instanceof Book) {
					ArrayList<Book> books = (ArrayList<Book>) collection;
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
				} else if (collection.get(0) instanceof Record) {
					
				} else if (collection.get(0) instanceof Customer) {
					
				} else {
					return false;
				}
				stmt.executeBatch();
				con.commit();
				con.setAutoCommit(true);
				return true;
			} catch (SQLException e) {
				System.out.println("[BookManager, add] SQL ERROR: " + e.getMessage());
				return false;
			} finally {
				
			}
	}
	*/
	
	
/*	Connection getConnection() {
		try {Connection con = DriverManager.getConnection 
			                  ("jdbc:postgresql://" + 
	                           cred.getHostname() + ":" +
	                           cred.getPort() + "/" + 
	                           cred.getDatabase() + "?sslmode=require&user=" +
	                           cred.getUsername() + "&password=" +
	                           cred.getPassword());
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
*/
	
	String getBooksLocation()     { return schema + "." +     booksTable; }
	String getRecordsLocation()   { return schema + "." +   recordsTable; }
	String getCustomersLocation() { return schema + "." + customersTable; }
	
	
	/*
	public boolean queryUpdate() {
	return false;	
	}
	
	public ResultSet query(String query) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = dbConnection.createStatement();
		    rs = stmt.executeQuery(query);
		    return rs;
		}
		catch (SQLException ex){
		    printSqlExceptions(ex);
		}
		finally {
		    /*if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException sqlEx) { } // ignore

		        rs = null;
		    }

		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore

		        stmt = null;
		    }
		}
		return rs;	
	}
	
	private void printSqlExceptions(SQLException ex) {
		System.out.println("SQLException: " + ex.getMessage());
	    System.out.println("SQLState: " + ex.getSQLState());
	    System.out.println("VendorError: " + ex.getErrorCode());
	}
	
	private void releaseResources(Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException sqlEx) { 
				printSqlExceptions(sqlEx);
			}
			rs = null;
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException sqlEx) { 
				printSqlExceptions(sqlEx);
			} // ignore
        stmt = null;
		}
	}
	
	public boolean createSchema(String schemaName) {
		return inputQuery("CREATE SCHEMA " + schemaName);
	}
	
	public boolean createTable(String tableName, String structure) {
		return createTable(null, tableName, structure);
	}
	
	public boolean createTable(String schemaName, String tableName, String structure) {
		String createTableQuery = "CREATE TABLE ";
		if (schemaName != null) {
			createTableQuery += schemaName + ".";
		}
		createTableQuery += tableName + structure;
		return (inputQuery(createTableQuery));
	}
	
	public boolean dropTable(String schemaName, String tableName) {
		return dropTable(schemaName + "." + tableName);
	}
	
	public boolean dropTable(String tableName) {
		return inputQuery("DROP TABLE " + tableName);
	}
	
	public ResultSet getTableName(String tableName) {
		try {
			DatabaseMetaData dbm = dbConnection.getMetaData();
			ResultSet rs = dbm.getTables(null, null, tableName, null);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return null;
	}
	
	public boolean insertQueryWithSchema(String schemaName, String tableName,
                                         String columns, String values) {
        return insertQuery(schemaName + "." + tableName, columns, values);
    }

    public boolean insertQueryWithSchema(String schemaName, String tableName,
                                         String values) {
        return insertQuery(schemaName + "." + tableName, values);
    }
	
	public boolean insertQuery(String tableName, String values) {
        return inputQuery("INSERT INTO " + tableName + " VALUES " + values);
    }
    
    public boolean insertQuery(String tableName, String columns, String values) {
		return inputQuery("INSERT INTO " + tableName + " " + 
	                      columns + " VALUES " + values);
	}
    
    public boolean deleteQuery(String schemaName, String tableName,
            String deleteBy, String conditions) {
    	return deleteInQuery(schemaName+"."+tableName, deleteBy, conditions);
    }
    
    private boolean deleteInQuery(String tableName, String deleteBy, String conditions) {
    	return inputQuery("DETELE FROM " + tableName + " WHERE " + deleteBy + " IN " +
                          conditions);
    }
	
	public boolean inputQuery(String query) {
		//System.out.println("[inputQuery] Query: " + query);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = dbConnection.createStatement();
		    stmt.execute(query);
		    return true;
		} catch (SQLException ex) {
			printSqlExceptions(ex);
		} finally {
			releaseResources(stmt, rs);
		}
		return false;
	}
	
	// OUTPUT METHODS
	public void selQuer(String scope, String dbName, String tableName) throws SQLException {
		ResultSet rs = null;
		RowSet jdbcRowSet = null;
		String selectString = "select ? from ?";
		try (PreparedStatement searchData = dbConnection.prepareStatement(selectString)) {
			searchData.setString(1, scope);
			searchData.setString(2, dbName + "." + tableName);
			rs = searchData.executeQuery();
			jdbcRowSet = new JdbcRowSetImpl(rs);
		} catch (SQLException ex) {
			
		} finally {
			rs.close();
			jdbcRowSet.close();
		}
		
	}
	
	
	public ResultSet selectQuery(String searchItem, String tableName) {
		return selectQuery(searchItem, tableName, null, null);
	}
	
	public ResultSet selectQuery(String searchItem, String tableName, 
			                     String searchKey,  String searchVariable) {
		try {
		    ResultSet rs = null;
			if (searchKey == null || searchVariable == null ||
				searchKey.isEmpty() || searchVariable.isEmpty()) {
				rs = outputQuery("SELECT " + searchItem + " FROM " + 
                        tableName);
			} else {
		        rs = outputQuery("SELECT " + searchItem + " FROM " + 
			                     tableName + " WHERE " + searchKey + 
			                     " = '" + searchVariable + "'");
			}
			
			if (rs.getMetaData().getColumnCount() > 0 && 
				rs.isBeforeFirst()) {
				return rs;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
			return null;
	}
	
	public ResultSet outputQuery(String query) {
		System.out.println("[outputQuery] Query: " + query);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = dbConnection.createStatement();
		    rs = stmt.executeQuery(query);
		    return rs;
		} catch (SQLException ex) {
			printSqlExceptions(ex);
		}
		return null;
	}*/
}