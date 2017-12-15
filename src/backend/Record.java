package backend;

import java.time.LocalDate;

public class Record {
	final String END_OF_LINE = System.lineSeparator();
	private int archieveId;
	private String customerId;
	private int lid;
	private LocalDate dateTaken;
	private LocalDate dateDue;
	private LocalDate dateReturned;
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
	
	public Record(int archiveId, String customerId, int lid, LocalDate dateTaken, LocalDate dateDue, LocalDate dateReturned) {
		this.archieveId = archiveId;
		this.customerId = customerId;
		this.lid = lid;
		this.dateTaken = dateTaken;
		this.dateDue = dateDue;
		this.dateReturned = dateReturned;
	}

	public int getArchiveId() {
		return archieveId;
	}

	public void setArchieveId(int archieveId) {
		this.archieveId = archieveId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setLibraryID(String newCustomerId) {
		this.customerId = newCustomerId;
	}

	public int getLid() {
		return lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public LocalDate getDateTaken() {
		return dateTaken;
	}

	public void setDateTaken(LocalDate dateTaken) {
		this.dateTaken = dateTaken;
	}

	public LocalDate getDateDue() {
		return dateDue;
	}

	public void setDateDue(LocalDate dateDue) {
		this.dateDue = dateDue;
	}

	public LocalDate getDateReturned() {
		return dateReturned;
	}

	public void setDateReturned(LocalDate dateReturned) {
		this.dateReturned = dateReturned;
	}

	public long getFine() {
		return fine;
	}

	public void setFine(long fine) {
		this.fine = fine;
	}

	public boolean equals(Object otherObject) {

		if (otherObject instanceof Record) {
			Record otherRecord = (Record) otherObject;
			boolean result = this.getCustomerId() == otherRecord.getCustomerId() && this.getLid() == otherRecord.getLid();
			return result;

		} else {
			return false;
		}
	}
	
	public String getDatesToString() {
		String result = "Date taken: " + getDateTaken().toString() + END_OF_LINE;
		result += "Date due: "   + getDateDue().toString() + END_OF_LINE;	
		if (getDateReturned() != null) {
			result += "Date returned: "   + getDateReturned().toString() + END_OF_LINE;	
		}
		return result;
	}
	
	public String toString() {
		String result = "aID: " + getArchiveId() + END_OF_LINE;
		result += "cID: " + getCustomerId() + END_OF_LINE;
		result += "lID: " + getLid() + END_OF_LINE;
		result += "Date taken: " + getDateTaken().toString() + END_OF_LINE;
		result += "Date due: "   + getDateDue().toString() + END_OF_LINE;	
		if (getDateReturned() != null) {
			result += "Date returned: "   + getDateReturned().toString() + END_OF_LINE;	
		}
		return result;
	}
}
