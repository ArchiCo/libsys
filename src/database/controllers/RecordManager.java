package database.controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import datatype.Record;

public class RecordManager {
	private final int ID_COL       = 1;
	private final int CID_COL      = 3;
	private final int LID_COL      = 4;
	private final int TAKEN_COL    = 5;
	private final int DUE_COL      = 6;
	private final int RETURNED_COL = 7;
	
	private String destination = "LibSys.Records";
	
	private final String SQL_INSERT = "insert into " + destination + "(lid, cid, taken_date, due_date) values (?, ?, ?, ?)";
	private final String SQL_DELETE_BY_ID    = "delete from " + destination + " where id  = ?";
	private final String SQL_DELETE_BY_LID   = "delete from " + destination + " where lid = ?";
	private final String SQL_DELETE_BY_USER  = "delete from " + destination + " where cid = ?";
	private final String SQL_SELECT          = "select * from " + destination;
	private final String SQL_SELECT_BY_ID    = "select * from " + destination + " where id = ?";
	private final String SQL_SELECT_BY_LID   = "select * from " + destination + " where lid = ?";
	private final String SQL_SELECT_BY_USER  = "select * from " + destination + " where cid = ?";
	
	private Database db;
	
	RecordManager(Database db) throws Exception {
		if (db == null) {
			throw new Exception("[RecordsManager, Constructor]: Invalid Database object.");
		}
		this.db = db;
	}
	
	public boolean add(Record record) throws Exception {
		return add(pack(record));
	}
	
	public boolean add(ArrayList<Record> records) throws Exception {
		if (records == null || records.isEmpty()) {
			throw new Exception ("[RecordsManager, add] The records collection is invalid.");
		}
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_INSERT);
			) {
			
			db.getConnection().setAutoCommit(false);
			for (Record record: records) {
				stmt.setString(1, record.getCid());
				stmt.setInt   (2, record.getLid());
				stmt.setDate  (3, record.getTakenDate());
				stmt.setDate  (4, record.getDueDate());
				stmt.addBatch();
			}
			stmt.executeBatch();
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
			
		} catch (SQLException e) {
			System.out.println("[RecordsManager, add] SQL ERROR: " + e.getMessage());
			return false;
		} finally {
			
		}
	}
	
	boolean deleteById(Record record) throws Exception{
		return deleteById(pack(record));
	}
	
	boolean deleteById(ArrayList<Record> records) throws Exception {
		if (records == null || records.isEmpty()) {
			throw new Exception ("[RecordsManager, deleteById] The records collection is invalid.");
		}
		try (Connection con = db.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SQL_DELETE_BY_ID);
				) {
				
				con.setAutoCommit(false);
				for (Record record: records) {
					stmt.setInt   (1, record.getAid());
					stmt.addBatch();
				}
				stmt.executeBatch();
				con.commit();
				con.setAutoCommit(true);
				return true;
				
			} catch (SQLException e) {
				System.out.println("[RecordsManager, deleteById] SQL ERROR: " + e.getMessage());
				return false;
			} finally {
				
			}
	}
	
	boolean deleteByUser(Record record) throws Exception{
		return deleteByUser(pack(record));
	}
	
	boolean deleteByUser(ArrayList<Record> records) throws Exception {
		if (records == null || records.isEmpty()) {
			throw new Exception ("[RecordsManager, deleteByUser] The records collection is invalid.");
		}
		try (Connection con = db.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SQL_DELETE_BY_USER);
				) {
				
				con.setAutoCommit(false);
				for (Record record: records) {
					stmt.setString(1, record.getCid());
					stmt.addBatch();
				}
				stmt.executeBatch();
				con.commit();
				con.setAutoCommit(true);
				return true;
				
			} catch (SQLException e) {
				System.out.println("[RecordsManager, deleteByUser] SQL ERROR: " + e.getMessage());
				return false;
			} finally {
				
			}
	}
	
	boolean deleteByLid(Record record) throws Exception{
		return deleteByLid(pack(record));
	}
	
	boolean deleteByLid(ArrayList<Record> records) throws Exception {
		if (records == null || records.isEmpty()) {
			throw new Exception ("[RecordsManager, deleteByLid] The records collection is invalid.");
		}
		try (Connection con = db.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SQL_DELETE_BY_LID);
				) {
				
				con.setAutoCommit(false);
				for (Record record: records) {
					stmt.setInt(1, record.getLid());
					stmt.addBatch();
				}
				stmt.executeBatch();
				con.commit();
				con.setAutoCommit(true);
				return true;
				
			} catch (SQLException e) {
				System.out.println("[RecordsManager, deleteByLid] SQL ERROR: " + e.getMessage());
				return false;
			} finally {
				
			}
	}

	public ArrayList<Record> fetchAll(){

		try { 
			Connection con = db.getConnection();
		  PreparedStatement stmt = con.prepareStatement("select * from LibSys.records;");
		  return fetch(stmt);	  
		} catch (SQLException e) {
			System.out.println("[RecordsManager, fetchAll] SQL ERROR: " + e.getMessage());
			return new ArrayList<Record>();
		} finally {	
		}
	}
	
	public ArrayList<Record> fetchById(int id) {
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_SELECT_BY_ID);
			) {
		  
		  stmt.setInt(1, id); 
		  return fetch(stmt);	  
		} catch (SQLException e) {
			System.out.println("[RecordsManager, fetchById] SQL ERROR: " + e.getMessage());
			return new ArrayList<Record>();
		} finally {
					
		}
	}
	
	public ArrayList<Record> fetchByLid(String lid) {
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_SELECT_BY_LID);
			) {
		  
		  stmt.setString(1, lid); 
		  return fetch(stmt);	  
		} catch (SQLException e) {
			System.out.println("[RecordsManager, fetchByLid] SQL ERROR: " + e.getMessage());
			return new ArrayList<Record>();
		} finally {
					
		}
	}
	
	public ArrayList<Record> fetchByUser(String username) {
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_SELECT_BY_USER);
			) {
		  
		  stmt.setString(1, username); 
		  return fetch(stmt);	  
		} catch (SQLException e) {
			System.out.println("[RecordsManager, fetchByLid] SQL ERROR: " + e.getMessage());
			return new ArrayList<Record>();
		} finally {
					
		}
	}
	
	
	private ArrayList<Record> fetch(PreparedStatement stmt) {
		try {
			ResultSet records = stmt.executeQuery();
			if (records.isBeforeFirst()) {
				ArrayList<Record> fetchedRecords = new ArrayList<Record>();
				while (records.next()) {
				    int    aid          = records.getInt(ID_COL);
					String cid          = records.getString(CID_COL);
					int    lid          = records.getInt(LID_COL);
					Date   dateTaken    = records.getDate(TAKEN_COL);
					Date   dateDue      = records.getDate(DUE_COL);
					Date   dateReturned = records.getDate(RETURNED_COL);
					if (records.wasNull()) {
						fetchedRecords.add(new Record(aid, cid, lid, 
			                      dateTaken, dateDue));
					} else {
						fetchedRecords.add(new Record(aid, cid, lid, 
			                      dateTaken, dateDue, dateReturned));
					}
					
				}
				return fetchedRecords;
			}
		} catch (SQLException e) {
			System.out.println("[RecordsManager, fetch] SQL ERROR: " + e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<Record>();
	}
	/*
	public boolean modify(ArrayList<Record> records) {
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_SELECT_BY_USER);
			) {
		  
		  stmt.setString(1, username); 
		  return fetch(stmt);	  
		} catch (SQLException e) {
			System.out.println("[RecordsManager, fetchByLid] SQL ERROR: " + e.getMessage());
			return new ArrayList<Record>();
		} finally {
					
		}
	} */
	
	boolean modify(ArrayList<Record> records) throws Exception {
		if (records == null || records.isEmpty()) {
			throw new Exception ("[RecordsManager, modify] The records collection is invalid.");
		}
		try (Connection con = db.getConnection();
			 PreparedStatement stmt = con.prepareStatement("update LibSys.records SET returnDate");
				) {
				
				con.setAutoCommit(false);
				for (Record record: records) {
					stmt.setInt(1, record.getLid());
					stmt.addBatch();
				}
				stmt.executeBatch();
				con.commit();
				con.setAutoCommit(true);
				return true;
				
			} catch (SQLException e) {
				System.out.println("[RecordsManager, deleteByLid] SQL ERROR: " + e.getMessage());
				return false;
			} finally {
				
			}
	}

	
	public boolean createTable() throws Exception {
		return db.createTable("LibSys.records",
				  "(id serial primary key,"
				+ "created_at timestamp not null default current_timestamp,"
				+ "username text not null,"
				+ "lid text not null,"
				+ "taken_date date not null,"
				+ "due_date date not null,"
				+ "returned_date date)");
	}
	
	public boolean dropTable() throws Exception {
		return db.dropTable("LibSys.records");
	}
	
	
	private ArrayList<Record> pack(Record record) throws Exception{
		if (record == null) {
			throw new Exception("[RecordsManager, packer] The record is invalid.");
		}
		ArrayList<Record> records = new ArrayList<Record>();
		records.add(record);
		return records;
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
