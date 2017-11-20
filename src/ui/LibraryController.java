package ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LibraryController {
	
	@FXML
	private Button bookBtn;
	@FXML
	private Button customerBtn;
	
	@FXML
	private AnchorPane rootPane;
	private Stage stage;
	private Parent root;

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		if(event.getSource().equals(bookBtn)) {
		bookBtn.setOnAction(e -> 
			stage = (Stage)bookBtn.getScene().getWindow());
			root = FXMLLoader.load(getClass().getResource("BookDirectory.fxml"));
		}
		
		
			
			
		
	}
	
}
