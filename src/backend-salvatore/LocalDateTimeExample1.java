package backend;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone; 
	
	public class LocalDateTimeExample1 {  
	    public static void main(String[] args) {  
	    	int borrowDays = 30;
	    	TimeZone timeZone = TimeZone.getTimeZone("UTC+1");
	    	Calendar calendar = Calendar.getInstance(timeZone);
	    	SimpleDateFormat simpleDateFormat = 
	    	       new SimpleDateFormat("EE dd MMM yyyy", Locale.ENGLISH);
	    	simpleDateFormat.setTimeZone(timeZone);

	    	System.out.println("Today's date is: " + simpleDateFormat.format(calendar.getTime()));
	    	calendar.add(Calendar.DATE, borrowDays);
	    	System.out.println("Please return the book: " + simpleDateFormat.format(calendar.getTime()) );
	    }  
	}  
