package frontend.resources;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.Alert.*;

public class RegistrationController implements Initializable {

	@FXML
	private Button backBtn;
	@FXML
	private Button completeRegBtn;
	@FXML
	private TextField regID;
	@FXML
	private TextField registrationUsername;
	@FXML
	private PasswordField registrationPassw;

	
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
      //  String test = "123";
      //  regID.setPromptText(test);
        completeRegBtn.setDisable(true);
        
        regID.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                //System.out.println(t+"====="+t1);
               if(t1.equals("") || registrationUsername.equals("") || registrationPassw.getLength() < 8)
                   completeRegBtn.setDisable(true);
               else
                   completeRegBtn.setDisable(false);
            }
          });
        		
        registrationUsername.textProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                //System.out.println(t+"====="+t1);
               if(registrationUsername.getText().isEmpty()||regID.getText().equals("")) 
            		   //|| registrationPassw.getLength()<8)
                   completeRegBtn.setDisable(true);
               else
                   completeRegBtn.setDisable(false);
            }
          });
          
  
    registrationPassw.textProperty().addListener(new ChangeListener<String>(){

       @Override
        public void changed(ObservableValue<? extends String> ov, String t, String t1) {
            //System.out.println(t+"====="+t1);
           if(registrationPassw.getLength()<8 || registrationUsername.getText().isEmpty() || regID.getText().isEmpty())
               completeRegBtn.setDisable(true);
           else
               completeRegBtn.setDisable(false);
       		}
       });
       
    }

	@FXML
	private void handleRegistrationAction(Event event) throws IOException {
		 if (event.getSource().equals(completeRegBtn)) {
			 	//if ID and username are not empty, registration is ok
			 if (regID.getText() != null && !regID.getText().isEmpty() && registrationUsername.getText() != null &&
					 !registrationUsername.getText().isEmpty()) {
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
	//}
	
	 @FXML
	 private void handleButtonAction(ActionEvent event) throws IOException {

		 if (event.getSource().equals(backBtn)) {
				Parent loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
				Scene loginScene = new Scene(loginParent);
				Stage window = (Stage) backBtn.getScene().getWindow();
				window.setScene(loginScene);
				window.show();
			}
		 }
	  
}
