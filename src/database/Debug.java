/*package database;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

import database.structure.Table;
import database.structure.pattern.Books;
import datatype.Book;
import datatype.Customer;
import datatype.Record;

public class Debug {
	final String DEBUG_BUILDER              = "1";
	
	final String DEBUG_CUSTOMERS_CREATE_TABLE = "2";
	final String DEBUG_CUSTOMERS_ADD_RECORD   = "3";
	final String DEBUG_CUSTOMERS_FETCH_ALL    = "4";
	final String DEBUG_CUSTOMERS_DELETE       = "5";
	final String DEBUG_CUSTOMERS_DROP_TABLE   = "6";

	final String DEBUG_RECORDS_CREATE_TABLE   = "7";
	final String DEBUG_RECORDS_ADD_RECORD     = "8";
	final String DEBUG_RECORDS_FETCH_ALL      = "9";
	final String DEBUG_RECORDS_DELETE_TABLE   = "10";
	final String DEBUG_RECORDS_DROP_TABLE     = "11";

	final String DEBUG_BOOKS_CREATE_TABLE     = "12";
	final String DEBUG_BOOKS_ADD_RECORD       = "13";
	final String DEBUG_BOOKS_FETCH_ALL        = "14";
	final String DEBUG_BOOKS_DELETE_TABLE     = "15";
	final String DEBUG_BOOKS_DROP_TABLE       = "16";
	
	final String DEBUG_EXIT                   = "17";
	
	Scanner scan  = new Scanner(System.in);
	DataController sql;
	public Debug() throws Exception {
		sql  = new DataController(new Credentials());
	}
	
		public void run() {
            boolean active = true;
            do {
            	debugMenu();
            	String option = scan.nextLine();
            	switch (option) {
            	
            	case DEBUG_BUILDER:
            		
            		break;
            		
            	case DEBUG_CUSTOMERS_CREATE_TABLE:
            		try {
						System.out.println("CREATE customers status: " + sql.customers().createTable());
					} catch (Exception e) {
						e.printStackTrace();
					}
            		break;
            	case DEBUG_CUSTOMERS_ADD_RECORD:
            		try {
            			Customer customer = new Customer("Test_User", "Pass", "SSN12345", "Name", "Surname", "Address", "070100");
						System.out.println("Adding record: " + sql.registerCustomer(customer));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		break;
            	case DEBUG_CUSTOMERS_FETCH_ALL:
            		ArrayList<Customer> customers = sql.customers().fetchByCid("Test_User");
            		
            		if(customers.isEmpty()) {
            			System.out.println("No Records Found");
            		} else {
            			int count = 1;
            			for (Customer customer : customers) {
     				       System.out.println(
     				    		   "Customer #" + count +  
		                           ": " + customer.getCid() +                
		                           ", " + customer.getPassword() +               
		                           ", " + customer.getSsn() +
		                           ", " + customer.getName() +
		                           ", " + customer.getSurname() +
		                           ", " + customer.getAddress() +
		                           ", " + customer.getPhone());
     				       count++;
            			}
            		}
            		break;
            	case DEBUG_CUSTOMERS_DELETE:
            		try {
                		Customer customer = new Customer("Test_User", "Pass", "SSN12345", "Name", "Surname", "Address", "070100");
						System.out.println("Delecting customer: " + sql.deregisterCustomer(customer));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		break;
            		
            	case DEBUG_CUSTOMERS_DROP_TABLE:
            		try {
						System.out.println("DROP customers status: " + sql.customers().dropTable());
					} catch (Exception e) {
						e.printStackTrace();
					}
            		break;
            	
            	
                case DEBUG_RECORDS_CREATE_TABLE:
            		try {
						System.out.println("CREATE records status: " + sql.records().createTable());
					} catch (Exception e) {
						e.printStackTrace();
					}
            		break;
            		
        		case DEBUG_EXIT: 
                    System.out.println("Finished debugging.");
        			System.exit(0);
        			break;
        				   
        		default:
        			System.out.println("Invalid option.\n");
        			break;
        	}

        } while(true);
	}
           
		
		private void debugMenu() {
        	System.out.println("\nDEBUG:");
        	System.out.println(" 1. Debug Builder\n");
        	
        	System.out.println("CUSTOMERS:");
        	System.out.println(" 2. Create");
        	System.out.println(" 3. Add");
           	System.out.println(" 4. Fetch");
           	System.out.println(" 5. Delete");
        	System.out.println(" 6. Drop\n");

        	System.out.println("RECORDS:");
        	System.out.println(" 7. Create");
        	System.out.println(" 8. Add");
           	System.out.println(" 9. Fetch");
           	System.out.println("10. Delete");
        	System.out.println("11. Drop\n");
        	
        	System.out.println("BOOKS:");
        	System.out.println("12. Create");
        	System.out.println("13. Add");
           	System.out.println("14. Fetch");
           	System.out.println("15. Delete");
        	System.out.println("16. Drop\n");        	
        	
        	System.out.println("17. Exit");
        	System.out.print("\nSelect: ");
		}
}*/
