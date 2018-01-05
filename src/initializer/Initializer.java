package initializer;

import backend.Library;
//import backend.LibraryMenu;
import javafx.application.Application;

public class Initializer {
	
	public static void main(String[] args) {
		try {
			Application.launch(Library.class, args);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
