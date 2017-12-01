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
import javafx.stage.Stage;

public class AddBookController implements Initializable {

	@FXML
	private Button cancelBtn;
	@FXML
	private Button addbookBtn;

	
	Stage window;
	
	
	
	private void addBook(ActionEvent event) throws IOException {
		if (event.getSource().equals(addbookBtn)) {
			Parent loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene loginScene = new Scene(loginParent);
			window = (Stage) backBtn.getScene().getWindow();
			window.setScene(loginScene);
			
		}
		window.show();
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
