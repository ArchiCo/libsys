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

public class RegistrationController implements Initializable{

	@FXML
	private Button backBtn;
	@FXML
	private Button registerBtnForreal;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}

	public void backToLogin(ActionEvent event) throws IOException {
		if (event.getSource().equals(backBtn)) {
			Parent loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene loginScene = new Scene(loginParent);
			Stage window = (Stage) backBtn.getScene().getWindow();
			window.setScene(loginScene);
			window.show();
		}
	}
}
