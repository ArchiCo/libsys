package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import backend.Customer;

public class CustomerManager {
	private final int CID_COL = 1;
	private final int NAME_COL = 2;
	private final int ADDRESS_COL = 3;
	private final int PHONE_COL = 4;
	
	private final int MODIFY_CID = 1;
	private final int MODIFY_NAME = 2;
	private final int MODIFY_ADDRESS = 3;
	private final int MODIFY_PHONE = 4;

	private String destination = "LibSys.Customers";

	private final String SQL_INSERT = "insert into " + destination + "(cid, name, address, phone) values (?, ?, ?, ?)";
	private final String SQL_DELETE = "delete from " + destination + " where cid = ?";
	private final String SQL_SELECT_ALL = "select * from " + destination;
	private final String SQL_SELECT = "select * from " + destination + " where cid = ?";
	private final String SQL_UPDATE = "update " + destination + " set name = ?, address = ?, phone = ? where cid = ?";
	
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
			throw new IOException("[CustomerManager, add] The Customers collection is invalid.");
		}

		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_INSERT)) {
			for (Customer Customer : Customers) {
				stmt.setString(1, Customer.getCustomerId());
				stmt.setString(2, Customer.getName());
				stmt.setString(3, Customer.getAddress());
				stmt.setInt(4, Customer.getPhoneNumber());
				stmt.addBatch();
			}
			stmt.executeBatch();
			return true;

		} catch (SQLException e) {
			System.out.println("[CustomersManager, add] SQL ERROR: " + e.getMessage());
			return false;
		}
	}
	
	public boolean modify(Customer customer) throws IOException {
		return modify(pack(customer));
	}
	
	boolean modify(ArrayList<Customer> customers) throws IOException {
		if (customers == null || customers.isEmpty()) {
			throw new IOException("[CustomerManager, modify] The Customers collection is invalid.");
		}

		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_UPDATE)) {
			for (Customer customer : customers) {
				stmt.setString(1, customer.getName());
				stmt.setString(2, customer.getAddress());
				stmt.setInt(3, customer.getPhoneNumber());
				stmt.setString(4, customer.getCustomerId());
				stmt.addBatch();
			}
			stmt.executeBatch();
			return true;

		} catch (SQLException e) {
			System.out.println("[CustomersManager, deleteById] SQL ERROR: " + e.getMessage());
			return false;
		}
	}

	boolean delete(Customer Customer) throws Exception {
		return delete(pack(Customer));
	}

	boolean delete(ArrayList<Customer> Customers) throws Exception {
		if (Customers == null || Customers.isEmpty()) {
			throw new Exception("[CustomersManager, deleteById] The Customers collection is invalid.");
		}
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_DELETE);) {
			for (Customer Customer : Customers) {
				stmt.setString(1, Customer.getCustomerId());
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

	public ArrayList<Customer> fetchAll() {

		try {
			Connection con = db.getConnection();
			PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL);
			return fetch(stmt);
		} catch (SQLException e) {
			System.out.println("[CustomersManager, fetchAll] SQL ERROR: " + e.getMessage());
			return new ArrayList<Customer>();
		} finally {
		}
	}

	public Customer fetchByCid(String cid) {
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_SELECT);) {

			stmt.setString(1, cid);
			ArrayList<Customer> fetchedCustomers = fetch(stmt);
			if (fetchedCustomers.isEmpty()) {
				return null;
			} else {
				return fetchedCustomers.get(0);
			}
		} catch (SQLException e) {
			System.out.println("[CustomersManager, fetchById] SQL ERROR: " + e.getMessage());
			return null;
		}
	}

	private ArrayList<Customer> fetch(PreparedStatement stmt) {
		try {
			ResultSet Customers = stmt.executeQuery();
			if (Customers.isBeforeFirst()) {
				ArrayList<Customer> fetchedCustomers = new ArrayList<Customer>();
				while (Customers.next()) {
					String cid = Customers.getString(CID_COL);
					String name = Customers.getString(NAME_COL);
					String address = Customers.getString(ADDRESS_COL);
					int phone = Customers.getInt(PHONE_COL);
					fetchedCustomers.add(new Customer(cid, name, address, phone));
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
		return db.createTable(destination,
				"(cid text primary key," + "name text not null," + "address text not null," + "phone int not null)");
	}

	public boolean dropTable() throws Exception {
		return db.dropTable("LibSys.customers");
	}

	private ArrayList<Customer> pack(Customer Customer) throws IOException {
		if (Customer == null) {
			throw new IOException("[CustomerManager, packer] The Customer is invalid.");
		}
		ArrayList<Customer> customers = new ArrayList<Customer>();
		customers.add(Customer);
		return customers;
	}

}
