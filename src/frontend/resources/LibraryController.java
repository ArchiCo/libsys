package frontend.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import backend.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LibraryController implements Initializable{

	@FXML
	private Button logoutBtn;
	@FXML
	private Button editCustomerBtn;
	@FXML
	private Button addCustomerBtn;
	@FXML
	private Button addBookBtn;
	@FXML
	private Button editBookBtn;

	
		


	
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
				
				Parent root = FXMLLoader.load(getClass().getResource("EditCustomer.fxml"));
				Scene scene = new Scene(root);
				Stage window = new Stage ();
				window.setScene(scene);
				window.show();
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
	 
	 @FXML
		private void addBookEvent(ActionEvent event) throws IOException {
			 
			if(event.getSource().equals(addBookBtn)) {
				
				Parent root = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
				Scene scene = new Scene(root);
				Stage window = new Stage ();
				window.setScene(scene);
				window.show();
				
			}
		}
	 
	 @FXML
		private void editBookEvent(ActionEvent event) throws IOException {
			 
			if(event.getSource().equals(editBookBtn)) {
				
				Parent root = FXMLLoader.load(getClass().getResource("EditBook.fxml"));
				Scene scene = new Scene(root);
				Stage window = new Stage ();
				window.setScene(scene);
				window.show();
			}
		}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
