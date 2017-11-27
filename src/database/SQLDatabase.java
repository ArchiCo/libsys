package database;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
			/*dbConnection = DriverManager.getConnection("jdbc:mysql://" + 
												       host + ":" +
												       port + "/" + 
												       db   + "?useSSL=true&user=" +
												       user + "&password=" +
												       pass);*/
			
			dbConnection = DriverManager.getConnection
					("jdbc:postgresql://" + 
				     host + ":" +
				     port + "/" + 
				     db   + "?sslmode=require&user=" +
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
	public boolean queryUpdate() {
	return false;	
	}
	
	public ResultSet selectQuery(String selection, String table) {
		return selectQuery(selection, table, null);
	}
	
	public ResultSet selectQuery(String selection, String table, String condition) {
		try {
			String query = "SELECT " + selection + 
					       " FROM "  + table;
			if (!condition.isEmpty()  && 
			    !condition.equals("") &&
			    !condition.equals(null)) {
				
				query += " WHERE " + condition;
			}
			ResultSet result = dbConnection.createStatement()
					                       .executeQuery(query);
			return result;
		} catch (SQLException ex) {
			printSqlExceptions(ex);
		}
		return null;
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
	
	public boolean createTable(String tableName, String structure) {
		return createTable(null, tableName, structure);
	}
	
	public boolean createTable(String schemaName, 
			                   String tableName, 
			                   String structure) {
		String createTableQuery = "CREATE TABLE ";
		if (schemaName == null) {
			createTableQuery += tableName;
		} else {
			createTableQuery += schemaName + "." + tableName;
		}
		
		createTableQuery += structure;
		
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
	
	public boolean insertQuery(String tableName, String columns, String values) {
		return inputQuery("INSERT INTO " + tableName + " " + 
	                      columns + " VALUES" + values);
	}
	
	public boolean insertQuery(String tableName, String values) {
        return inputQuery("INSERT INTO " + tableName + " VALUES" + values);
    }
	
	public boolean inputQuery(String query) {
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
	
	public ResultSet outputQuery(String query) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = dbConnection.createStatement();
		    rs = stmt.executeQuery(query);
		    return rs;
		} catch (SQLException ex) {
			printSqlExceptions(ex);
		} finally {
			//releaseResources(stmt, rs);
		}
		return null;
	}
	
}