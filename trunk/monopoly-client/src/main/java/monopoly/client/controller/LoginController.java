/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * @author pablo
 * 
 */
public class LoginController extends AnchorPane implements Initializable {

	@FXML
    TextField userId;
	
    @FXML
    PasswordField password;
    
    @FXML
    Button login;
    
    @FXML
    Button registrarme;
    
    @FXML
    Label errorMessage;
    
//	private Main application;
//
//	public void setApp(Main application) {
//		this.application = application;
//	}
//
	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		errorMessage.setText("");
	}
	
	public void processLogin(ActionEvent event) {
//        if (application == null){
//            // We are running in isolated FXML, possibly in Scene Builder.
//            // NO-OP.
//            errorMessage.setText("Hello " + username.getText());
//        } else {
//            if (!application.validarUsuario(username.getText(), password.getText())){
//                errorMessage.setText("Usuario / Contraseña inválida");
//            }
//        }
    }

}
