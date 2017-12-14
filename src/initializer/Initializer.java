package initializer;

import backend.LibraryMenu;
import javafx.application.Application;

public class Initializer {
	
	public static void main(String[] args) {
		try {
			Application.launch(LibraryMenu.class, args);
		//Class.forName("com.mysql.jdbc.Driver").newInstance();
		//	LibraryMenu program = new LibraryMenu();
		//	program.run();
			//new Debug().run();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
