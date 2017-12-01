package frontend.resources;

import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.Alert.*;

public class RegistrationController implements Initializable{

	@FXML
	private Button backBtn;
	@FXML
	private Button completeRegBtn;
	@FXML
	private TextField regID= new TextField(null);
	@FXML
	private TextField registrationUsername= new TextField(null);
	@FXML
	private PasswordField registrationPassw = new PasswordField();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}

	
	 @FXML
	 private void handleButtonAction(ActionEvent event) throws IOException {

	 
		 if (event.getSource().equals(backBtn)) {
				Parent loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
				Scene loginScene = new Scene(loginParent);
				Stage window = (Stage) backBtn.getScene().getWindow();
				window.setScene(loginScene);
				window.show();
			}
		 else if (event.getSource().equals(completeRegBtn)) {
			 	//if ID and username are not empty, registration is ok
			 if (regID.getText()!=null && !regID.getText().isEmpty() && registrationUsername.getText()!=null && !registrationUsername.getText().isEmpty()) {
				//if the PW is at least 8 chars
				if (!registrationPassw.getText().isEmpty() && registrationPassw.getText().length()>=8) {
					Alert regOK = new Alert(AlertType.INFORMATION);
					regOK.setTitle("Information");
					regOK.setHeaderText("Look, an Information Dialog");
					regOK.setContentText("Successfully registered");
					regOK.showAndWait();
				}
			 }
	
}
	  
	 
	}
}
