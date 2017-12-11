package frontend.resources;

import backend.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import backend.LibraryMenu;
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

public class AddBookController implements Initializable{

	@FXML
	private Button cancelBtn;
	@FXML
	private Button addBookBtn;

	private Book newBook;
	
	
	
	
	public void closeEvent(ActionEvent event) throws IOException {
		
		    Stage stage = (Stage) cancelBtn.getScene().getWindow();
		    stage.close();
	}
	
	public void addEvent(ActionEvent event) throws IOException{
		if (event.getSource().equals(addBookBtn)) {
	    Stage stage = (Stage) addBookBtn.getScene().getWindow();
	    
	    stage.close();
		}
	}

	public void setBook(Book newBook) {
		this.newBook = newBook;
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		
	}
	

}
