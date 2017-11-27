package database;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.structure.Column;
import database.structure.Table;

public class SQLController {
	final int DEBUG_TIMEOUT = 5000;
	
	SQLDatabase postgresql;
	SQLController (String host, 
				   int    port,
				   String dbName,
				   String user, 
				   String pass) {
		
		postgresql = new SQLDatabase(host, port, dbName, user, pass);
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
		if (table.getSchemaName() == null) {
			createTable(table.getTableName(), structureQuery);
		} else {
			createTable(table.getSchemaName(), 
					    table.getTableName(), 
					    structureQuery);
		}
		
	}
	
	public void createTable(String schemaName, String tableName, String structure) {
		boolean status = postgresql.createTable(schemaName, tableName);
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
	
	public void debugInsert(String table, String columns, String values) {
		boolean status = postgresql.insertQueryWithSchema("LibSys", table, columns, values);
		System.out.println("Insert operation status: " + status);
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
	
	public void searchQuery(String searchItem, String tableName) {
		searchQuery(searchItem, tableName, null, null);
	}
	
	public void searchQuery(String searchItem, String tableName, 
			                   String searchParameter, String data) {
	    ResultSet rs = null;
		try {
			if (searchParameter == null || data == null) {
				rs = postgresql.outputQuery("SELECT " + searchItem + " FROM " + 
                        tableName);
			} else {
		        rs = postgresql.outputQuery("SELECT " + searchItem + " FROM " + 
			                                tableName + " WHERE " + searchParameter + 
			                                " = " + data);
			}
			
	       if (rs.getMetaData().getColumnCount() == 0) {
	    	   System.out.println("The table is empty.");
	    
	       } else {
	          ArrayList<Column> columns = new ArrayList<Column>();
	          for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
	    	      String datatype   = rs.getMetaData().getColumnTypeName(i);
	    	      String columnName = rs.getMetaData().getColumnName(i);
	    	      columns.add(new Column(columnName, datatype));
	    	      System.out.println(i + ": " + columnName + " : " + datatype);
	          }
	    }
	    
	    
	    
		} catch (Exception ex) {
			System.out.println("SQLException: " + ex.getMessage());
		    //System.out.println("SQLState: " + ex.getSQLState());
		    //System.out.println("VendorError: " + ex.getErrorCode());
		} 
	    
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
