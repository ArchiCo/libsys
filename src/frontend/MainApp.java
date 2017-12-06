package frontend;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {
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
