package initializer;

import database.Debug;

public class Initializer {
	
	public static void main(String[] args) {
        try {
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            new Debug().run();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
	}

}
