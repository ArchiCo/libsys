package frontend.resources;

import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.Alert.*;

public class RegistrationController implements Initializable{

	@FXML
	private Button backBtn;
	@FXML
	private Button completeRegBtn;

	@FXML
	private TextField regID= new TextField(null);
	@FXML
	private TextField registrationUsername= new TextField(null);
	@FXML
	private PasswordField registrationPassw = new PasswordField();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
            completeRegBtn.setDisable(true);

            regID.textProperty().addListener(new ChangeListener<String>() {


                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    //System.out.println(t+"====="+t1);
                    if(t1.equals("") || registrationUsername.equals("") || registrationPassw.getLength()<8)
                        completeRegBtn.setDisable(true);
                    else
                        completeRegBtn.setDisable(false);
                }
            });

            registrationUsername.textProperty().addListener(new ChangeListener<String>(){

                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    //System.out.println(t+"====="+t1);
                    if(registrationUsername.getText().isEmpty()||regID.getText().equals("") || registrationPassw.getLength()<8)
                        completeRegBtn.setDisable(true);
                    else
                        completeRegBtn.setDisable(false);
                }
            });


            registrationPassw.textProperty().addListener(new ChangeListener<String>(){

                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    //System.out.println(t+"====="+t1);
                    if(registrationPassw.getLength()<8 || registrationUsername.getText().isEmpty() || regID.getText().isEmpty())
                        completeRegBtn.setDisable(true);
                    else
                        completeRegBtn.setDisable(false);
                }
            });
	}






	 @FXML
	 private void handleButtonAction(ActionEvent event) throws IOException {
         completeRegBtn.disableProperty().bind(registrationPassw.lengthProperty().greaterThanOrEqualTo(8).and(registrationUsername.textProperty().isEmpty()));



         if (event.getSource().equals(backBtn)) {
                 Parent loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
                 Scene loginScene = new Scene(loginParent);
                 Stage window = (Stage) backBtn.getScene().getWindow();
                 window.setScene(loginScene);
                 window.show();
             }
             else if (event.getSource().equals(completeRegBtn)) {
                 //if ID and username are not empty, registration is ok

                 Alert regOK = new Alert(AlertType.INFORMATION);
                 regOK.setTitle("Information");
                 regOK.setHeaderText("Look, an Information Dialog");
                 regOK.setContentText("Successfully registered");
                 regOK.showAndWait();

             }


     }

}
