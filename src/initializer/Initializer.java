package initializer;

import database.SQLDebug;

public class Initializer {
	
	public static void main(String[] args) {
        try {
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            new SQLDebug().run();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
	}

}
