package frontend;

import frontend.resources.*;
import backend.*;
import java.util.Observable;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import sun.security.jgss.LoginConfigImpl;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Frontend extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/frontend/resources/Login.fxml"));
			Parent root = loader.load();
		    Scene scene = new Scene(root);
		    primaryStage.setTitle("Library System");
			
		    
		    primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
