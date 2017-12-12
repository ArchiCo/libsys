package backend;

import java.time.LocalDate;

public class Records {
	private int archieveId;
	private String customerId;
	private int lid;
	private LocalDate dateTaken;
	private LocalDate dateDue;
	private LocalDate dateReturned;
	private long fine;

	public Records(String customerId, int lid, LocalDate dateTaken, LocalDate dateDue) {
		this.customerId = customerId;
		this.lid = lid;
		this.dateTaken = dateTaken;
		this.dateDue = dateDue;
	}

	public int getArchieveId() {
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

		if (otherObject instanceof Records) {
			Records otherRecord = (Records) otherObject;
			boolean result = this.getCustomerId() == otherRecord.getCustomerId() && this.getLid() == otherRecord.getLid();
			return result;

		} else {
			return false;
		}
	}
}
