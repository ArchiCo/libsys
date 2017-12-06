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
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.Alert.*;
import javafx.scene.control.ButtonBar.ButtonData;

public class EditBookController implements Initializable{

	@FXML
	private Button cancelBtn;
	@FXML
	private Button saveBtn;
	
	

	
	
	
	//registerBtn going to registration
	public void closeEvent(ActionEvent event) throws IOException {
		
		//if the source of the event is the register button
					// get a handle to the stage
		    Stage stage = (Stage) cancelBtn.getScene().getWindow();
		    // do what you have to do
            stage.close();
	}

    public void saveChanges(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	

}
