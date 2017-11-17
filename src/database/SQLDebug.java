package database;
import java.util.Scanner;

public class SQLDebug {
	final String SQL_CONNECTION_STATUS	= "1";
	final String SQL_LIVE_STATUS		= "2";
	final String SQL_PRINT_COLUMNS		= "3";
	final String DEBUG_EXIT 			= "4";
	
	Scanner scan;
	SQLController sql;
	public SQLDebug() {
		scan =  new Scanner(System.in);
		sql = new SQLController("129.16.155.35",
                  				3306,
                  				"team10",
                  				"teamten",
                				"tC7FuKsZYM9M9Xwy");
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
        	System.out.println("4. Exit");
        	System.out.print("\nSelect: ");
		}
}
