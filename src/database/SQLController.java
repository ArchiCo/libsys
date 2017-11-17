package database;

import java.sql.ResultSet;
import java.sql.SQLException;

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
		try {
			ResultSet rs = mysql.query("SELECT * FROM " + table);
			if (rs != null) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.println("Column #" + i + ":" +  
								        rs.getMetaData().getColumnName(i));
				}
				rs.close();
			} else {
				System.out.println("No result returned.");
			}
		} catch (Exception e) {
			
		}
	}
	
}
