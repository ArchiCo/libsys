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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Node;

public class AddBookController implements Initializable{
//Buttons
	@FXML
	private Button cancelBtn;
	@FXML
	private Button addBookBtn;

	//Fields
	@FXML private TextField LIDField;
	@FXML private TextField IsbnField;
	@FXML private TextField titleField;
	@FXML private TextField authorField;
	@FXML private TextField genreField;
	@FXML private TextField pubdateField;
	@FXML private TextField publisherField;
	
	private Book newBook;
	private Stage alertStage;
	private boolean addBookClickedOk = false;
	private LibraryMenu librarymenu;
	
	public AddBookController(LibraryMenu librarymenu) {
		this.librarymenu = librarymenu;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void setAlertStage(Stage alertStage) {
		this.alertStage = alertStage;
	}
	public void setBook(Book newBook) {
		this.newBook = newBook;
		
	}
	public boolean isAddBookClicked() {
		return addBookClickedOk;
	}
	private void handleValidInput(Boolean validInput) {
		if(validInput == true) {
			newBook.setID(LIDField.getText());
			newBook.setIsbn(IsbnField.getText());
			newBook.setTitle(titleField.getText());
			newBook.setAuthor(authorField.getText());
			newBook.setPublisher(publisherField.getText());
			
			addBookClickedOk = true;
			librarymenu.getLibrary().addBook(newBook);
			}
		}
	public boolean validInputCheck() {
		String errorMsg = "y u do dis";
		
		if (LIDField.getText() == null|| 
				IsbnField.getText() == null ||
				titleField.getText() == null ||
				authorField.getText() == null ||
				genreField.getText() == null ||
				publisherField.getText() == null ){
			Alert alertmsg = new Alert(AlertType.ERROR);
			alertmsg.initOwner(alertStage);
			alertmsg.setTitle("y u do dis");
			alertmsg.setHeaderText("y u do dis eh man");
			alertmsg.setContentText(errorMsg);
			
			alertmsg.showAndWait();
			return false;
		}
		else {
			return true;
		}
	}
	@FXML
	public void closeEvent(ActionEvent event) throws IOException {
		    Stage stage = (Stage) cancelBtn.getScene().getWindow();
		    stage.close();
	}
	@FXML
	public void addEvent(ActionEvent event) throws IOException{
		if (event.getSource().equals(addBookBtn)) {
	    Stage stage = (Stage) addBookBtn.getScene().getWindow();
	    Boolean validInput = validInputCheck();
	    handleValidInput(validInput);
	    stage.close();
		}
	}


	


	

}
