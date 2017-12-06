package frontend.resources;

import java.awt.print.Book;
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

public class EditCustomerController implements Initializable{

	@FXML
	private Button cancelBtn;
	@FXML
	private Button saveBtn;
	

	
	
	
	//registerBtn going to registration
	public void closeEvent(ActionEvent event) throws IOException {
		
		//if the source of the event is the register button
		if(event.getSource().equals(cancelBtn) ) {
			// get a handle to the stage
		    Stage stage = (Stage) cancelBtn.getScene().getWindow();
		    // do what you have to do
		    stage.close();
		}
		
		else if(event.getSource().equals(saveBtn)) {
		}	
		
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	

}
