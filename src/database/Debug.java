package database;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

import database.controllers.Controller;
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
	Controller sql;
	public Debug() throws Exception {
		sql  = new Controller(new Credentials());
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
            		
            	/*case DEBUG_RECORDS_ADD_RECORD:
            		try {
            			//Record record = new Record("Test_User", "101", new LocalDate(2015, 1, 5), new LocalDate(2015, 1, 25));
						System.out.println("Adding record: " + sql.registerCustomer(customer));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		break;
            		
            	case DEBUG_CUSTOMERS_FETCH_ALL:
            		ArrayList<Record> records = sql.records().fetchByCid("Test_User");
            		
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
            		
                case DEBUG_RECORDS_DROP_TABLE:
            		try {
						System.out.println("DROP records status: " + sql.records().dropTable());
					} catch (Exception e) {
						e.printStackTrace();
					}
	                break;
            		
            	case DEBUG_RECORDS_FETCH_ALL:
            		ArrayList<Record> records = sql.records().fetchAll();
            		
            		if(records.isEmpty()) {
            			System.out.println("No Records Found");
            		} else {
            			int count = 1;
            			for (Record record : records) {
     				       System.out.println(
     				    		   "Record #" + count +  
		                           ": " + record.getId() +                
		                           ", " + record.getUsername() +               
		                           ", " + record.getLid() +
		                           ", " + record.getTakenLocalDate().toString() +
		                           ", " + record.getDueLocalDate().toString() +
		                           ", " + record.getReturnedDateString());
     				       count++;
            			}
            		}
            		break;
            		
            	case DEBUG_RECORDS_ADD_RECORD:
            		
            		try {
						System.out.println("Adding record: " + sql.records().add(new Record(0, "test_user", "200", new Date(2000,7,10), new Date(2000,8,30))));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		
            		break;*/
            	
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
            	
            	/*
            		case SQL_CONNECTION_STATUS : 
            			System.out.println("SQL Connection status: " + 
            							    sql.isConnected());
            			break;
            				   
            		case SQL_LIVE_STATUS : 
            			System.out.println("SQL Live status: " + 
            							    sql.isLive());
            			break;
            			
            		case SQL_PRINT_COLUMNS : 
            			System.out.print("Table's name: ");
            			String table = scan.nextLine();
            			sql.printColumnNames(table);
            			break;	
            			
            		case SQL_DEBUG_INSERT : 
            			System.out.print("Table's name: ");
            			String table1 = scan.nextLine();
            			System.out.print("Columns: ");
            			String columns = scan.nextLine();
            			System.out.print("Values: ");
            			String values = scan.nextLine();
            			sql.debugInsert(table1, columns, values);
            			break;
            			
            		case SQL_DEBUG_CREATE : 
	    				try {
	    					System.out.print("Table's name: ");
	            			String newTableName = scan.nextLine();
	            			System.out.print("Number of columns: ");
	            			int    newTableColumnSize = scan.nextInt();
	            			scan.nextLine();
	            			
	            			
		    				Table books = new Table(new Books().getName(), 
		    						                new Books().getColumns());	    				
		    				System.out.println(books.getSqlStructure());
			    			sql.createTable(books);
				        	} catch (Exception e) {
						    // TODO Auto-generated catch block
						    e.printStackTrace();
				    	}
            			break;
            		
            		case SQL_DEBUG_SELECT : 
            			System.out.print("Selection: ");
            			String selection = scan.nextLine();		
            			System.out.print("Table's name: ");
            			String tableSelect = scan.nextLine();
            			sql.searchQuery(selection, tableSelect);
            			break;
            		
            		case SQL_DEBUG_PRESENT: 
	    				try {
	    					System.out.print("Table's name: ");
	            			String tbl = scan.nextLine();
		    				System.out.println(sql.tableIsPresent(tbl));
			    			//sql.createTable(books.getSqlStructure());
				        	} catch (Exception e) {
						    // TODO Auto-generated catch block
						    e.printStackTrace();
				    	}
            			break;
            			
            		case SQL_DEBUG_BUILDER : 
	    				try {
		    				new Builder(sql);
				        	} catch (Exception e) {
						    // TODO Auto-generated catch block
						    e.printStackTrace();
				    	}
            			break;
            			
            		case SQL_DEBUG_CREATE_SCHEMA :
            			System.out.print("Schema to CREATE: ");
            			String newSchema = scan.nextLine();
            			sql.createSchema(newSchema);
            			break;
            			
            		case SQL_DEBUG_DROP : 
            			System.out.print("Table to DROP: ");
            			String dropTable = scan.nextLine();
            			sql.dropTable(dropTable);
            			break;	
 				   	
            		case SQL_DEBUG_FIND_ALL :
            			ArrayList<Book> books = sql.fetchAllBooks();
            			if (books == null) {
            				System.out.println("No books found.");
            			} else {
            				System.out.println("Number of items: " + books.size());
            			    for (int i = 0; i < books.size(); i++) {
            			    	Book book = books.get(i);
            				    System.out.println("Book #" + (i+1) + "\n" + book.getPrintout(book) + "\n");
            				    /*if (book.getRecords().equals(new ArrayList<Record>()) ||
            				    	book.getRecords() == null) {
            				    	System.out.println("No Records");
            				    } else {
            				       System.out.println("Records:");
            				       for (int j = 0; j < book.getRecords().size(); j++) {
            				       System.out.println("Record #" + j + ": " + 
            				                           book.getRecords().get(j).getArchiveId()  + ", " +
            				                           book.getRecords().get(j).getUsername()   + ", " +
            				                           book.getRecords().get(j).getLid()        + ", " +
            				                           book.getRecords().get(j).getTakenTime()  + ", " +
            				                           book.getRecords().get(j).getDueTime()    + ", " +
            				                           book.getRecords().get(j).getReturnedTime());
            				       }
            				    }
            			    }
            			}
            			break;
            			*/
		
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
}
