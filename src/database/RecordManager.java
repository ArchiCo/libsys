package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Types;
import java.util.ArrayList;

import datatype.Customer;
import datatype.Record;

public class RecordManager {
	private final int AID_COL      = 1;
	private final int CID_COL      = 2;
	private final int LID_COL      = 3;
	private final int TAKEN_COL    = 4;
	private final int DUE_COL      = 5;
	private final int RETURNED_COL = 6;
	
	private String destination = "LibSys.Records";
	
	private final String SQL_INSERT = "insert into " + destination + "(cid, lid, taken, due, returned) values (?, ?, ?, ?, ?)";
	private final String SQL_DELETE_BY_AID    = "delete from " + destination + " where aid  = ?";
	private final String SQL_DELETE_BY_LID   = "delete from " + destination + " where lid = ?";
	private final String SQL_DELETE_BY_USER  = "delete from " + destination + " where cid = ?";
	private final String SQL_SELECT_ALL      = "select * from " + destination;
	private final String SQL_SELECT_BY_AID    = "select * from " + destination + " where aid = ?";
	private final String SQL_SELECT_BY_LID   = "select * from " + destination + " where lid = ?";
	private final String SQL_SELECT_BY_CID  = "select * from " + destination + " where cid = ?";
	
	private final String SQL_UPDATE = "update " + destination + " set cid = ?, lid = ?, taken = ?, due = ?, returned = ? where aid = ?";

	
	private Database db;
	
	RecordManager(Database db) throws Exception {
		if (db == null) {
			throw new Exception("[RecordsManager, Constructor]: Invalid Database object.");
		}
		this.db = db;
	}
	
	public boolean add(Record record){
		try {
			return add(pack(record));
		} catch (Exception e) {
			System.out.println("[RecordManager, add] ERROR: " + e.getMessage());
			return false;
		}
	}
	
	public boolean add(ArrayList<Record> records) throws Exception {
		if (records == null || records.isEmpty()) {
			throw new Exception ("[RecordManager, add] The records collection is invalid.");
		}
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_INSERT);
			) {

			for (Record record: records) {
				stmt.setString(1, record.getCid());
				stmt.setInt   (2, record.getLid());
				stmt.setDate  (3, Date.valueOf(record.getDateTaken()));
				stmt.setDate  (4, Date.valueOf(record.getDateDue()));
				if (record.getDateReturned() != null) {
					stmt.setDate  (5, Date.valueOf(record.getDateReturned()));
				} else {
					stmt.setNull(5, Types.NULL);
				}
				stmt.addBatch();
			}
			stmt.executeBatch();
			return true;
			
		} catch (SQLException e) {
			System.out.println("[RecordManager, add] SQL ERROR: " + e.getMessage());
			return false;
		} finally {
			
		}
	}
	
	boolean delete(int aid) throws Exception{
		return delete(new Record(aid, null, -1, null, null, null));
	}
	
	boolean delete(Record record) throws Exception{
		return delete(pack(record));
	}
	
	boolean delete(ArrayList<Record> records) throws Exception {
		if (records == null || records.isEmpty()) {
			throw new Exception ("[RecordManager, delete] The records collection is invalid.");
		}
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_DELETE_BY_AID);
				) {
				for (Record record: records) {
					stmt.setInt   (1, record.getAid());
					stmt.addBatch();
				}
				stmt.executeBatch();
				return true;
				
			} catch (SQLException e) {
				System.out.println("[RecordManager, delete] SQL ERROR: " + e.getMessage());
				return false;
			} finally {
				
			}
	}

	public ArrayList<Record> fetchAll(){

		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_SELECT_ALL)){
		  return fetch(stmt);	  
		} catch (SQLException e) {
			System.out.println("[RecordManager, fetchAll] SQL ERROR: " + e.getMessage());
			return new ArrayList<Record>();
		} finally {	
		}
	}
	
	public boolean modify(Record record) {
		try {
			return modify(pack(record));
		} catch (Exception e) {
			System.out.println("[RecordManager, modify] ERROR: " + e.getMessage());
			return false;
		}
	}
	
	boolean modify(ArrayList<Record> records) throws IOException {
		if (records == null || records.isEmpty()) {
			throw new IOException("[RecordManager, modify] The Customers collection is invalid.");
		}

		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_UPDATE)) {
			for (Record record : records) {
				stmt.setString(1, record.getCid());
				stmt.setInt   (2, record.getLid());
				stmt.setDate  (3, Date.valueOf(record.getDateTaken()));
				stmt.setDate  (4, Date.valueOf(record.getDateDue()));
				if (record.getDateReturned() != null) {
					stmt.setDate(5, Date.valueOf(record.getDateReturned()));
				} else {
					stmt.setNull(5, Types.NULL);
				}
				stmt.setInt(6, record.getAid());
				stmt.addBatch();
			}
			stmt.executeBatch();
			return true;

		} catch (SQLException e) {
			System.out.println("[RecordManager, modify] SQL ERROR: " + e.getMessage());
			return false;
		}
	}
	
	public Record fetchById(int id) {
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_SELECT_BY_AID);
			) {
		  
		  stmt.setInt(1, id); 
		  ArrayList<Record> foundRecords = fetch(stmt);
		  if (!foundRecords.isEmpty()) {
			  return foundRecords.get(0);
		  }
		} catch (SQLException e) {
			System.out.println("[RecordsManager, fetchById] SQL ERROR: " + e.getMessage());
		}
		return null;
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
	}public ArrayList<Record> fetchByUser(String username) {
		try (PreparedStatement stmt = db.getConnection().prepareStatement(SQL_SELECT_BY_CID);
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
				    int    aid          = records.getInt(AID_COL);
					String cid          = records.getString(CID_COL);
					int    lid          = records.getInt(LID_COL);
					Date   dateTaken    = records.getDate(TAKEN_COL);
					Date   dateDue      = records.getDate(DUE_COL);
					Date   dateReturned = records.getDate(RETURNED_COL);
					if (records.wasNull()) {
						fetchedRecords.add(new Record(aid, cid, lid, 
			                      dateTaken.toLocalDate(), dateDue.toLocalDate()));
					} else {
						fetchedRecords.add(new Record(aid, cid, lid, 
			                      dateTaken.toLocalDate(), dateDue.toLocalDate(), dateReturned.toLocalDate()));
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
	
	public boolean createTable() throws Exception {
		return db.createTable(destination,
				  "(aid serial primary key,"
				+ "cid text not null,"
				+ "lid int not null,"
				+ "taken date not null,"
				+ "due date not null,"
				+ "returned date)");
	}
	
	public boolean dropTable() throws Exception {
		return db.dropTable(destination);
	}
	
	
	private ArrayList<Record> pack(Record record) throws Exception{
		if (record == null) {
			throw new Exception("[RecordsManager, packer] The record is invalid.");
		}
		ArrayList<Record> records = new ArrayList<Record>();
		records.add(record);
		return records;
	}
}
