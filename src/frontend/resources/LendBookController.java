package frontend.resources;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.java.swing.plaf.windows.resources.windows;

import backend.Library;
import datatype.Book;
import datatype.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Stage;
import javafx.util.Callback;

public class LendBookController implements Initializable{

	@FXML private TableView<Customer> lendCustomerTable;
	@FXML private TableColumn<Customer, String> lendCustomerIDCol;
	@FXML private TableColumn<Customer, String> lendCustomerNameCol;
	
	@FXML private Button backBtn;
	@FXML private Button lendBtn;
	
	private Customer chosenCustomer;
	private Book chosenBook;
	
	private LibraryController librarycontroller;
	private Library	library;
//	private LibraryMenu libraryMenu;
	
	private ObservableList<Book> basket;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.library = new Library();
//		this.libraryMenu = new LibraryMenu();
		this.librarycontroller = new LibraryController();
		this.basket = FXCollections.observableArrayList();
		
		SortedList<Customer> sortedCustomers = new SortedList<>(librarycontroller.getObsCustomers(library.getCustomersList()));
	
		
		lendCustomerIDCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Customer, String> param) {
				Customer customer = param.getValue();
				SimpleStringProperty convertedCstId = librarycontroller.getStringProperty(customer.getCid());
				return convertedCstId;
			}
		});
		lendCustomerNameCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Customer, String> param) {
				Customer customer = param.getValue();
				SimpleStringProperty convertedCstName = librarycontroller.getStringProperty(customer.getName());
				return convertedCstName;
			}
		});
		
		lendCustomerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {

			@Override
			public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
				chosenCustomer =  lendCustomerTable.getSelectionModel().getSelectedItem();
			}
		});
	lendCustomerTable.setItems(sortedCustomers);
	
	}
	
	@FXML
	public void handleBackButton(ActionEvent event) {
		Stage window = (Stage)this.backBtn.getScene().getWindow();
		window.close();
	}	
	
	@FXML
	public void lendBookEvent() {
		Alert confirmedLend = new Alert(AlertType.CONFIRMATION);
		confirmedLend.setTitle("Confirm Lend Book");
		confirmedLend.setHeaderText("Lend Selected Book");
		confirmedLend.setContentText("Are you sure you want to lend this book to the selected customer?");
		Optional <ButtonType> result = confirmedLend.showAndWait();
		if (result.get() == ButtonType.OK ) {
			Alert confirmation = new Alert(AlertType.INFORMATION);
			confirmation.setTitle("Book(s) Lent");
			confirmation.setHeaderText(null);
			confirmation.setContentText("Please return the book(s) in 2 weeks. " + "\n" 
					+ "Due Date: " + library.getDate().plusDays(14));
			for(Book book : basket) {
				library.lendBook(chosenCustomer.getCid(), book);
				System.out.println(book);
				confirmation.showAndWait();
			}
			System.out.println(chosenCustomer);
			Stage window = 	(Stage) lendBtn.getScene().getWindow();
			basket.clear();
			window.close();
		}
		else {
			Stage window = (Stage) lendBtn.getScene().getWindow();
			window.close();
		}

	}
	public void setChosenBook(Book chosenBook) {
		this.chosenBook = chosenBook;
	}
	public Book getChosenBook() {
		return this.chosenBook;
	}
	/*public void setLibraryMenu(LibraryMenu libraryMenu) {
		this.libraryMenu = libraryMenu;
	}*/
	public void setLibraryController(LibraryController libraryController) {
		this.librarycontroller = libraryController;
	}
	public void setLibrary(Library library) {
		this.library = library;
	}
	public void setBasket(ObservableList<Book> basket) {
		this.basket = basket;
	}


}
