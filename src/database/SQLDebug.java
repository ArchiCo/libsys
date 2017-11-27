package database;
import java.util.Scanner;

import database.structure.Table;
import database.structure.pattern.Books;

public class SQLDebug {
	
	private Credentials  cred;
	final String SQL_CONNECTION_STATUS = "1";
	final String SQL_LIVE_STATUS       = "2";
	final String SQL_PRINT_COLUMNS     = "3";
	final String SQL_DEBUG_INSERT      = "4";
	final String SQL_DEBUG_CREATE      = "5";
	final String SQL_DEBUG_DROP        = "6";
	final String SQL_DEBUG_BUILDER     = "7";
	final String SQL_DEBUG_PRESENT     = "8";
	final String DEBUG_EXIT            = "9";
	
	Scanner scan;
	SQLController sql;
	public SQLDebug() {
		cred = new Credentials();
		scan = new Scanner(System.in);
		sql  = new SQLController(cred.getHostname(), 
				                 cred.getPort(),
				                 cred.getDatabase(),
				                 cred.getUsername(),
				                 cred.getPassword());
	}
	
		public void run() {
            boolean active = true;
            do {
            	debugMenu();
            	String str = scan.nextLine();
            	switch (str) {
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
		    				Table books = new Table(new Books().getName(), 
		    						                new Books().getColumns());	    				
		    				System.out.println(books.getSqlStructure());
			    			sql.createTable(books);
				        	} catch (Exception e) {
						    // TODO Auto-generated catch block
						    e.printStackTrace();
				    	}
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
		    				//System.out.println(books.getSqlStructure());
			    			//sql.createTable(books.getSqlStructure());
				        	} catch (Exception e) {
						    // TODO Auto-generated catch block
						    e.printStackTrace();
				    	}
            			break;
            			
            		case SQL_DEBUG_DROP : 
            			System.out.print("Table to DROP: ");
            			String dropTable = scan.nextLine();
            			sql.dropTable(dropTable);
            			break;	
 				   	
            		case DEBUG_EXIT: 
            			active = false;
            			break;
            				   
            		default:
            			System.out.println("Invalid option.\n");
            			break;
            	}

            } while(active);
            
            System.out.println("Finished debugging.");
		}
		
		private void debugMenu() {
        	System.out.println("\nDEBUG:");
        	System.out.println("1. Connection status");
        	System.out.println("2. Live status");
        	System.out.println("3. Print column names");
        	System.out.println("4. Debug Insert");
        	System.out.println("5. Debug CREATE");
        	System.out.println("6. Debug DROP");
        	System.out.println("7. Debug BUILDER");
        	System.out.println("8. Debug PRESENT");
        	System.out.println("9. Exit");
        	System.out.print("\nSelect: ");
		}
}
