package frontend.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LoginController implements Initializable{

	@FXML
	private Button loginBtn;
	@FXML
	private Button registerBtn;
	

	
	
	
	//registerBtn going to registration
	public void changeScreenBtn(ActionEvent event) throws IOException {
		
		//if the source of the event is the register button
		if(event.getSource().equals(registerBtn) ) {
			Stage window;
			//load the registration scene into a parent
		Parent registerParent = FXMLLoader.load(getClass().getResource("Registration.fxml"));
		//make a new scene with the loaded fxml
		Scene registerScene = new Scene(registerParent);
		//get stage info
		window = (Stage) registerBtn.getScene().getWindow();
		//set scene to the register scene with the stage of the register button, which is the same
		window.setScene(registerScene);
		window.show();
		}
		
		else if(event.getSource().equals(loginBtn)) {
			
			//loadWindow("Library.fxml", "Book Directory");
			 Stage window;
			Parent bookParent = FXMLLoader.load(getClass().getResource("Library.fxml"));
			Scene bookScene = new Scene(bookParent);
			
			window = (Stage) (loginBtn.getScene().getWindow());
			window.setScene(bookScene);
			window.show();
			
		}	
		
	}
	
	public void loadWindow(String location, String title) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource(location));
		Stage newStage = new Stage();
		newStage.setTitle(title);
		newStage.setScene(new Scene(root));
		newStage.show();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	

}
