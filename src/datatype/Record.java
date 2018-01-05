package datatype;

import java.time.LocalDate;

public class Record {
	final String END_OF_LINE = System.lineSeparator();
	private int aid, lid;
	private String cid;
	private LocalDate dateTaken, dateDue, dateReturned;
	private long fine;

	public Record(int archiveId, String customerId, int lid, LocalDate dateTaken, LocalDate dateDue){
		this(archiveId, customerId, lid, dateTaken, dateDue, null);
	}

	public Record(String customerId, int lid, LocalDate dateTaken, LocalDate dateDue, LocalDate dateReturned){
		this(-1, customerId, lid, dateTaken, dateDue, dateReturned);
	}
	
	public Record(String customerId, int lid, LocalDate dateTaken, LocalDate dateDue){
		this(-1, customerId, lid, dateTaken, dateDue, null);
	}
	
	public Record(int archiveId, String cid, int lid, LocalDate dateTaken, LocalDate dateDue, LocalDate dateReturned) {
		this.aid = archiveId;
		this.cid = cid;
		this.lid = lid;
		this.dateTaken = dateTaken;
		this.dateDue = dateDue;
		this.dateReturned = dateReturned;
	}

	public int       getAid()          { return          aid; }
	public String    getCid()          { return          cid; }
	public int       getLid()          { return          lid; }
	public LocalDate getDateTaken()    { return    dateTaken; }	
	public LocalDate getDateDue()      { return      dateDue; }
	public LocalDate getDateReturned() { return dateReturned; }
	public long      getFine()         { return         fine; }
	
	public void setAid         (int       var) { aid          = var; }
	public void setCid         (String    var) { cid          = var; }
	public void setLid         (int       var) { lid          = var; }
	public void setDateTaken   (LocalDate var) { dateTaken    = var; }
	public void setDateDue     (LocalDate var) { dateDue      = var; }
	public void setDateReturned(LocalDate var) { dateReturned = var; }
	public void setFine        (long      var) { fine         = var; }

	public boolean equals(Object otherObject) {
		if (otherObject instanceof Record) {
			Record otherRecord = (Record) otherObject;
			boolean result = (cid == otherRecord.getCid()) && (lid == otherRecord.getLid());
			return result;

		} else {
			return false;
		}
	}
	
	public String getDatesToString() {
		String result = "";
		result += "Date taken: " + getDateTaken().toString() + END_OF_LINE +
				  "Date due: "   + getDateDue().toString()   + END_OF_LINE;	
		if (getDateReturned() != null) {
			result += "Date returned: " + getDateReturned().toString() + END_OF_LINE;	
		}
		return result;
	}
	
	public String toString() {
		String result = "";
		result += "aID: " + getAid() + END_OF_LINE +
				  "cID: " + getCid() + END_OF_LINE +
				  "lID: " + getLid() + END_OF_LINE +
				  "Date taken: " + getDateTaken().toString() + END_OF_LINE +
				  "Date due: "   + getDateDue().toString();	
		if (getDateReturned() != null) {
			result += "Date returned: " + getDateReturned().toString() + END_OF_LINE;	
		}
		return result;
	}
}
