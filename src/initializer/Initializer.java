package initializer;

import backend.LibraryMenu;

public class Initializer {
	
	public static void main(String[] args) {
		try {
		//Class.forName("com.mysql.jdbc.Driver").newInstance();
			LibraryMenu program = new LibraryMenu();
			program.run();
			//new Debug().run();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
