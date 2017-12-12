package database.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import datatype.Customer;

public class CustomerManager {
	private final int CID_COL     = 1;
	private final int PASS_COL    = 3;
	private final int SSN_COL     = 4;
	private final int NAME_COL    = 5;
	private final int SURNAME_COL = 6;
	private final int ADDRESS_COL = 7;
	private final int PHONE_COL   = 8;
	
	private String destination = "LibSys.Customers";
	
	
	private final String SQL_INSERT    = "insert into " + destination + "(cid, password, ssn, name, surname, address, phone) values (?, ?, ?, ?, ?, ?, ?)";
	private final String SQL_DELETE    = "delete from " + destination + " where cid = ?";
	private final String SQL_SELECT          = "select * from " + destination;
	private final String SQL_SELECT_BY_CID    = "select * from " + destination + " where cid = ?";
	
	private Database db;
	
	CustomerManager(Database db) throws Exception {
		if (db == null) {
			throw new Exception("[CustomersManager, Constructor]: Invalid Database object.");
		}
		this.db = db;
	}
	
	public boolean add(Customer Customer) throws IOException {
		return add(pack(Customer));
	}
	
	public boolean add(ArrayList<Customer> Customers) throws IOException {
		if (Customers == null || Customers.isEmpty()) {
			throw new IOException ("[CustomerManager, add] The Customers collection is invalid.");
		}
		
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_INSERT)) {
			for (Customer Customer: Customers) {
				stmt.setString(1, Customer.getCid());
				stmt.setString(2, Customer.getPassword());
				stmt.setString(3, Customer.getSsn());
				stmt.setString(4, Customer.getName());
				stmt.setString(5, Customer.getSurname());
				stmt.setString(6, Customer.getAddress());
				stmt.setString(7, Customer.getPhone());
				stmt.addBatch();
			}
			stmt.executeBatch();
			return true;
			
		} catch (SQLException e) {
			System.out.println("[CustomersManager, add] SQL ERROR: " + e.getMessage());
			return false;
		} finally {
			
		}
	}
	
	boolean delete(Customer Customer) throws Exception{
		return delete(pack(Customer));
	}
	
	boolean delete(ArrayList<Customer> Customers) throws Exception {
		if (Customers == null || Customers.isEmpty()) {
			throw new Exception ("[CustomersManager, deleteById] The Customers collection is invalid.");
		}
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_DELETE);
				) {
				for (Customer Customer: Customers) {
					stmt.setString(1, Customer.getCid());
					stmt.addBatch();
				}
				stmt.executeBatch();
				return true;
				
			} catch (SQLException e) {
				System.out.println("[CustomersManager, deleteById] SQL ERROR: " + e.getMessage());
				return false;
			} finally {
				
			}
	}

	public ArrayList<Customer> fetchAll(){

		try { 
			Connection con = db.getConnection();
		  PreparedStatement stmt = con.prepareStatement("select * from LibSys.Customers;");
		  return fetch(stmt);	  
		} catch (SQLException e) {
			System.out.println("[CustomersManager, fetchAll] SQL ERROR: " + e.getMessage());
			return new ArrayList<Customer>();
		} finally {	
		}
	}
	
	public ArrayList<Customer> fetchByCid(String cid) {
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_SELECT_BY_CID);
			) {
		  
		  stmt.setString(1, cid); 
		  return fetch(stmt);	  
		} catch (SQLException e) {
			System.out.println("[CustomersManager, fetchById] SQL ERROR: " + e.getMessage());
			return new ArrayList<Customer>();
		} finally {
					
		}
	}
	
	private ArrayList<Customer> fetch(PreparedStatement stmt) {
		try {
			ResultSet Customers = stmt.executeQuery();
			if (Customers.isBeforeFirst()) {
				ArrayList<Customer> fetchedCustomers = new ArrayList<Customer>();
				while (Customers.next()) {
				    String cid      = Customers.getString(CID_COL);
					String password = Customers.getString(PASS_COL);
					String ssn      = Customers.getString(SSN_COL);
					String name     = Customers.getString(NAME_COL);
					String surname  = Customers.getString(SURNAME_COL);
					String address  = Customers.getString(ADDRESS_COL);
					String phone    = Customers.getString(PHONE_COL);

					fetchedCustomers.add(new Customer(cid, password, ssn, 
			                             name, surname, address, phone));
					}
				return fetchedCustomers;
				}	
		} catch (SQLException e) {
			System.out.println("[CustomerManager, fetch] SQL ERROR: " + e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<Customer>();
	}
	
	public boolean createTable() throws Exception {
		return db.createTable("LibSys.customers",
				  "(cid text primary key,"
				+ "created_at timestamp not null default current_timestamp,"
				+ "password text not null,"
				+ "ssn text not null,"
				+ "name text not null,"
				+ "surname text not null,"
				+ "address text not null,"
				+ "phone text)");
	}
	
	public boolean dropTable() throws Exception {
		return db.dropTable("LibSys.customers");
	}
	
	
	private ArrayList<Customer> pack(Customer Customer) throws IOException{
		if (Customer == null) {
			throw new IOException("[CustomerManager, packer] The Customer is invalid.");
		}
		ArrayList<Customer> customers = new ArrayList<Customer>();
		customers.add(Customer);
		return customers;
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
