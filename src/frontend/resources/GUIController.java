package frontend.resources;

import frontend.MainApp;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIController {
	
	@FXML
	private Button bookBtn;
	@FXML
	private Button customerBtn;
	@FXML
	private Button registerBtn;
	
	@FXML
	private AnchorPane rootPane;
	private Stage stage;
	private Parent root;
	
	@FXML
	private Text textTarget;

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		/*if(event.getSource().equals(bookBtn)) {
		bookBtn.setOnAction(e -> 
			stage = (Stage)bookBtn.getScene().getWindow());
			root = FXMLLoader.load(getClass().getResource("BookDirectory.fxml"));
		}*/
		if(event.getSource() == (registerBtn)) {
			//textTarget.setText("chicken");
			
			registerBtn.setOnAction(e ->
			stage = (Stage)registerBtn.getScene().getWindow());
			root = FXMLLoader.load(getClass().getResource(("/frontend/resources/Registration.fxml")));
			
		}
		Scene newScene = new Scene(root);
		stage.setScene(newScene);
		stage.show();
		
			
			
		
	}
	
}
