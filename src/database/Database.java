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
	
	String getBooksLocation()     { return schema + "." +     booksTable; }
	String getRecordsLocation()   { return schema + "." +   recordsTable; }
	String getCustomersLocation() { return schema + "." + customersTable; }
	
}