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

public class LibraryController implements Initializable{

	@FXML
	private Button logoutBtn;
	@FXML
	private Button editCustomerBtn;
	@FXML
	private Button addCustomerBtn;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	
	 @FXML
	 private void handleButtonAction(ActionEvent event) throws IOException {
	}
	 @FXML
	private void logoutEvent(ActionEvent event) throws IOException {
		if(event.getSource().equals(logoutBtn) ) {
			Stage window;
			Parent registerParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene registerScene = new Scene(registerParent);
			window = (Stage) logoutBtn.getScene().getWindow();
			window.setScene(registerScene);
			window.show();
		}

	}

	 @FXML
	private void editCustomerEvent(ActionEvent event) throws IOException {
		 
		 
		if(event.getSource().equals(editCustomerBtn) ) {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Customer edit");
			dialog.setHeaderText("Edit the customer data");
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			TextField ID=new TextField();
			ID.setPromptText("ID");
			TextField username = new TextField();
			username.setPromptText("Username");
			TextField phone=new TextField();
			phone.setPromptText("Phone");
			TextField address= new TextField();
			address.setPromptText("Address");
			grid.add(new Label("ID:"), 0, 0);
			grid.add(ID, 1, 0);
			grid.add(new Label("Username:"), 0, 1);
			grid.add(username, 1, 1);
			grid.add(new Label("Phone"), 0, 2);
			grid.add(phone, 1, 2);
			grid.add(new Label("Address"),0,3);
			grid.add(address, 1, 3);
			dialog.getDialogPane().setContent(grid);
			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();

			if (!ID.getText().isEmpty() && !username.getText().isEmpty() && !phone.getText().isEmpty() && !address.getText().isEmpty()){
			if(result.isPresent()) {
			System.out.println("Your name: " + username.getText());
		    System.out.println("Your ID: "+ ID.getText());
		    System.out.println("Your address: "+ address.getText());
			System.out.println("Your phone number: " +phone.getText());}
			}
		}
	}

	 @FXML
		private void addCustomerEvent(ActionEvent event) throws IOException {
			 
			 
			if(event.getSource().equals(addCustomerBtn) ) {
				
				Parent root = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
				Scene scene = new Scene(root);
				Stage window = new Stage ();
				window.setScene(scene);
				window.show();
			}
		}
}
