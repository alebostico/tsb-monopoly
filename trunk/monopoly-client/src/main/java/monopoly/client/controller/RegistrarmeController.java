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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * @author pablo
 *
 */
public class RegistrarmeController extends AnchorPane implements Initializable {

	@FXML
	private Button btnSalir;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtUserName;

	@FXML
	private Button btnGuardar;

	@FXML
	private PasswordField txtRepeatPassword;

	@FXML
	private PasswordField txtPassword;

	@FXML
	void processComparateFields(ActionEvent event) {
		
	}

	@FXML
	void processExit(ActionEvent event) {

	}

	@FXML
	void processSave(ActionEvent event) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}

}
