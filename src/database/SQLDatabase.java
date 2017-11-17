package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;

class SQLDatabase {
	Connection dbConnection = null;	
	String host, username, password;
	int port;
	
	SQLDatabase (String host, 
			     int    port,
			     String db,
		         String user, 
                 String pass) {
		try {
			dbConnection = DriverManager.getConnection("jdbc:mysql://" + 
												       host + ":" +
												       port + "/" + 
												       db   + "?user=" +
												       user + "&password=" +
												       pass);

		} catch (SQLException ex) {
			printSqlExceptions(ex);
		}
	}
	
	public void closeConnection() throws SQLException {
		try {
			dbConnection.close();
		} catch (SQLException ex) {
			throw ex;
		}
	}
	
	// Tests if the connection is still open or it was closed
	public boolean isConnected() throws SQLException {
		try {
			if (dbConnection.isClosed()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException ex) {
			throw ex;
		}
	}
	
	/* Testing if connection to the database is still valid
	 * and/or doesn't times out within the provided timeout
	 * period in milliseconds.
	 */
	public boolean isLive(int timeout) throws SQLException {
		try {
			return dbConnection.isValid(timeout);
		} catch (SQLException ex) {
			throw ex;
		}
	}
	
	public boolean queryInsert(String table,
							   String columns,
							   String values) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = dbConnection.createStatement();
		    return stmt.execute("INSERT INTO " + 
	    							 table   + " " + 
	    							 columns + " VALUES" +
	    							 values);
		} catch (SQLException ex) {
			printSqlExceptions(ex);
		} finally {
			releaseResources(stmt, rs);
		}
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
		    }*/
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
}