package frontend.resources;
import java.util.Arrays;
import java.util.List;
import backend.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Node;

public class AddBookController implements Initializable{

	@FXML
	private Button cancelBtn;
	@FXML
	private Button addBookBtn;
	

	public LibraryController liController = new LibraryController();
	
	//cancel button
	public void closeEvent(ActionEvent event) throws IOException {
		
		//if the source of the event is the register button
			// get a handle to the stage
		    Stage stage = (Stage) cancelBtn.getScene().getWindow();
		    // do what you have to do
		    stage.close();
	}
	
	//add book button
	public void addEvent(ActionEvent event) throws IOException{
		if (event.getSource().equals(addBookBtn)) {
			liController.books.add(new Book("asd", "a","a","a","a","a"));
			
			liController.refresh_table(liController.bookTable);

	    Stage stage = (Stage) addBookBtn.getScene().getWindow();
	    stage.close();
		}
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	

}
