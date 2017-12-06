package frontend;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import sun.security.jgss.LoginConfigImpl;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class MainApp extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
		/*	BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setTitle("Library System");
			*/
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
