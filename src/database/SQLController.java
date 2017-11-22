package database;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLController {
	final int DEBUG_TIMEOUT = 5000;
	
	SQLDatabase mysql;
	SQLController (String host, 
				   int    port,
				   String dbName,
				   String user, 
				   String pass) {
		
		mysql = new SQLDatabase(host, port, dbName, user, pass);
	}
	
	public boolean isConnected() {
		try {
			return mysql.isConnected();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isLive() {
		
		try {
			return mysql.isLive(DEBUG_TIMEOUT);
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
			ResultSet rs = mysql.selectQuery("*", table);
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
	
	public void debugInsert(String table, String columns, String values) {
		boolean status = mysql.queryInsert(table, columns, values);
		System.out.println("Insert operation status: " + status);
	}
	
	/*public void debugPrint(String selection, String table) {
		ResultSet rs = mysql.selectQuery(selection, table);
		ArrayList<Array> arr;
		for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
			arr.add(rs.getArray(i));
		}
		for (int i = 0; i < arr.size(); i++)
			for (int j = 0; j < arr.get(i).; j++) {
				System.out.println(arr.get(i)[j]);
			}
		
		//System.out.println("Insert operation status: " + status);
	}*/
	
}
