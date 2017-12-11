package RecycleBin;

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

public class LibSysController implements Initializable {

	@FXML
	private Button backBtn;

	
	Stage window;
	
	
	
	private void changeScreenBtn(ActionEvent event) throws IOException {
		if (event.getSource().equals(backBtn)) {
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
