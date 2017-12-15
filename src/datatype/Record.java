package datatype;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Record {
	private int       aid, lid;
	private String    cid = null;
	private LocalDate dateTaken,
	                  dateDue,
                      dateReturned;
	private DateTimeFormatter standardFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
	
	public Record(String cid, int lid, Date dateTaken, Date dateDue) {
		this(0, cid, lid, dateTaken, dateDue);
	}
	
	public Record(int aid, String cid, int lid, Date dateTaken, Date dateDue) {
		this(aid, cid, lid, dateTaken, dateDue, null);
	}
	
	public Record(int aid, String cid, int lid, Date dateTaken, Date dateDue, Date dateReturned) {
		this(aid, cid, lid, dateTaken.toLocalDate(), dateDue.toLocalDate(), dateReturned.toLocalDate());
	}

	public Record(String cid, int lid, LocalDate dateTaken, LocalDate dateDue) {
		this(0, cid, lid, dateTaken, dateDue);
	}

	public Record(int aid, String cid, int lid, LocalDate dateTaken, LocalDate dateDue) {
		this(aid, cid, lid, dateTaken, dateDue, null);
	}
	
	public Record(int aid, String cid,  int lid, LocalDate dateTaken, LocalDate dateDue, LocalDate dateReturned) {
		this.aid          = aid;
		this.cid          = cid;
		this.lid          = lid;
		this.dateTaken    = dateTaken;
		this.dateDue      = dateDue;
		this.dateReturned = dateReturned;
	}
	
	public int       getAid() { return aid; }
	public int       getLid() { return lid; }
	public String 	 getCid() { return cid; }
	
	public LocalDate getTakenLocalDate()    { return    dateTaken; }
	public LocalDate getDueLocalDate()      { return      dateDue; }
	public LocalDate getReturnedLocalDate() { return dateReturned; }
	public Date      getTakenDate()         { return getConvertedDate(dateTaken); }
	public Date      getDueDate()           { return getConvertedDate(dateDue); }
	public Date      getReturnedDate()      { return getConvertedDate(dateReturned); }

	
	public Date getConvertedDate(LocalDate date) {
		if (date != null) {
			return Date.valueOf(date);
		} else {
			return null;
		}
	}
	
	public String getTakenDateString()    { return dateTaken   .format(standardFormatter); }
	public String getDueDateString()      { return dateDue     .format(standardFormatter); }
	public String getReturnedDateString() { 
		if (dateReturned == null) {
			return "N/A";
		}
		return dateReturned.format(standardFormatter); 
	}

	
	public int getTakenDay()      { return dateTaken   .getDayOfMonth(); }
	public int getDueDay()        { return dateDue     .getDayOfMonth(); }
	public int getReturnedDay()   { return dateReturned.getDayOfMonth(); }
	public int getTakenMonth()    { return dateTaken   .getMonthValue(); }
	public int getDueMonth()      { return dateDue     .getMonthValue(); }
	public int getReturnedMonth() { return dateReturned.getMonthValue(); }
	public int getTakenYear()     { return dateTaken   .getYear(); }
	public int getDueYear()       { return dateDue     .getYear(); }
	public int getReturnedYear()  { return dateReturned.getYear(); }
	
	
public void setTakenDate(int year, int month, int day) { setDate(dateTaken, year, month, day); }
public void setDueDate(int year, int month, int day) { setDate(dateDue, year, month, day); }
public void setReturnedDate(int year, int month, int day) { setDate(dateReturned, year, month, day); }

public void setTakenDate(Date date) { setDate(dateTaken, date); }
public void setDueDate(Date date) { setDate(dateTaken, date); }
public void setReturnedDate(Date date) { setDate(dateTaken, date); }


public void setTakenDate(LocalDate date) { setDate(dateTaken, date); }
public void setDueDate(LocalDate date) { setDate(dateDue, date); }
public void setReturnedDate(LocalDate date) { setDate(dateReturned, date); }
	

	public void setDate(LocalDate localDate, LocalDate newLocalDate) { localDate = newLocalDate; }
	public void setDate(LocalDate localDate, Date date) { localDate = date.toLocalDate();}
	public void setDate(LocalDate localDate, int year, int month, int day) {
		localDate = LocalDate.of(year, month, day);
	}
	
}
