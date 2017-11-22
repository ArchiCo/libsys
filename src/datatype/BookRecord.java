package datatype;
import java.sql.Date;

public class BookRecord {
	private String customerSsn = null;
	private Date   dateTaken,
	               dateDue,
                   dateReturned = null;
	
	BookRecord(String customerSsn, Date dateTaken, Date dateDue) {
		this.customerSsn = customerSsn;
		this.dateTaken   = dateTaken;
		this.dateDue     = dateDue;
	}
	
	public String getCustomerId()   { return  customerSsn; }
	public Date   getDateTaken()    { return    dateTaken; }
	public Date   getDateDue()      { return      dateDue; }
	public Date   getDateReturned() { return dateReturned; }
	
	public void   setCustomerId(String newId) {
		customerSsn  = newId; 
	}
	
	public void   setDateTaken(Date newDate) {
		dateTaken    = newDate;
	}

	public void   setDateDue(Date newDate) {
		dateReturned = newDate; 
	}
	public void   setDateReturned(Date newDate) {
		dateReturned = newDate; 
	}
	
}
