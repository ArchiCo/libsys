//package frontend.resources;
//
//import java.awt.print.Book;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ResourceBundle;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.*;
//import javafx.stage.Stage;
//import javafx.scene.Node;
//
//public class LogoutController implements Initializable{
//
//	@FXML
//	private Button logoutBtn;
//	
//
//	
//	
//	
//	public void changeScreenBtn(ActionEvent event) throws IOException {
//		if(event.getSource().equals(logoutBtn) ) {
//			Stage window;
//			//load the registration scene into a parent
//			Parent registerParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
//			//make a new scene with the loaded FXML
//			Scene registerScene = new Scene(registerParent);
//			//get stage info
//			window = (Stage) logoutBtn.getScene().getWindow();
//			//set scene to the register scene with the stage of the register button, which is the same
//			window.setScene(registerScene);
//			window.show();
//		}
//
//	}
//	
////	public void loadWindow(String location, String title) throws IOException {
////		
////		Parent root = FXMLLoader.load(getClass().getResource(location));
////		Stage newStage = new Stage();
////		newStage.setTitle(title);
////		newStage.setScene(new Scene(root));
////		newStage.show();
////	}
//
//
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//		
//		
//	}
//	
//
//}
