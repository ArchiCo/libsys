package database;
import java.util.Scanner;

public class SQLDebug {
	final String SQL_CONNECTION_STATUS = "1";
	final String SQL_LIVE_STATUS       = "2";
	final String SQL_PRINT_COLUMNS     = "3";
	final String SQL_DEBUG_INSERT      = "4";
	final String SQL_DEBUG_SELECT      = "5";
	final String DEBUG_EXIT            = "6";
	
	Scanner scan;
	SQLController sql;
	public SQLDebug() {
		scan =  new Scanner(System.in);
		/*sql = new SQLController("leia.skip.chalmers.se",
                  				3306,
                  				"team10",
                  				"teamten",
                				"tC7FuKsZYM9M9Xwy");*/
		sql = new SQLController("ec2-184-72-243-166.compute-1.amazonaws.com",
			                    5432,
			                    "dbeqbssbap89ck",
			                    "epoxusywcguize",
		                        "9f5ce8aee62b66e69b388c89446c87518822090137cc57e861478f2ffea2376a");
		
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
            			
            		case SQL_DEBUG_SELECT : 
            			System.out.print("Table's name: ");
            			String tableSelect = scan.nextLine();
            			System.out.print("Selection: ");
            			String selection = scan.nextLine();
            			//sql.debugPrint(selection, tableSelect);
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
        	System.out.println("5. Debug Select");
        	System.out.println("6. Exit");
        	System.out.print("\nSelect: ");
		}
}
