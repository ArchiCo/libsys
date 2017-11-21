package frontend.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.corba.se.impl.orbutil.graph.Node;
import com.sun.java.swing.plaf.windows.resources.windows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginController implements Initializable{

	@FXML
	private Button registerBtn;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void changeScreenBtn(ActionEvent event) throws IOException {
		if(event.getSource().equals(registerBtn) ) {
		Parent registerParent = FXMLLoader.load(getClass().getResource("Registration.fxml"));
		Scene registerScene = new Scene(registerParent);
		
		//get stage info
		Stage window = (Stage) registerBtn.getScene().getWindow();
		window.setScene(registerScene);
		window.show();
		}
	}

}
