/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;
import monopoly.model.Usuario;
import monopoly.util.encriptacion.Encrypter;
import monopoly.util.encriptacion.VernamEncrypter;
import monopoly.util.exception.CampoVacioException;

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
	private Label lblMsgError;

	@FXML
	private Stage prevStage;

	@FXML
	void processExit(ActionEvent event) {

	}

	@FXML
	void processSave(ActionEvent event) {
		String nombre="";
		String userName="";
		String passwordEnc="";
		String email ="";
		Usuario usuario = null;
		
		if (validarCampos()) {
			Encrypter enc = new VernamEncrypter(txtPassword.getText());
			enc.code();
			passwordEnc = enc.getEncrypted();
			usuario = new Usuario(userName, passwordEnc);
			usuario.setEmail(email);
			usuario.setNombre(nombre);
			
		}
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

		txtRepeatPassword.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> observable,
							Boolean oldValue, Boolean newValue) {
						if (newValue.booleanValue()) {
							System.out.println("textfield focused");
						} else {
							System.out.println("textfield is not focused");
							if (!txtPassword.getText().equals(
									txtRepeatPassword.getText())) {
								lblMsgError.setVisible(true);
								txtRepeatPassword.focusedProperty();
							} else {
								lblMsgError.setVisible(false);
							}
						}
					}
				});
	}

	private boolean validarCampos() {
		try {
			if (txtNombre.getText().isEmpty())
				throw new CampoVacioException(
						"¡El Campo Nombre no puede estar vacio!");

			if (txtUserName.getText().isEmpty())
				throw new CampoVacioException(
						"¡El Campo Usuario no puede estar vacio!");

			if (txtPassword.getText().isEmpty())
				throw new CampoVacioException(
						"¡El Campo Contraseña no puede estar vacio!");

			if (txtRepeatPassword.getText().isEmpty())
				throw new CampoVacioException(
						"¡El Campo Confirmar Contraseña no puede estar vacio!");
			return true;
		} catch (CampoVacioException cve) {
			MessageBox.show(prevStage, cve.getMessage(), "Campos Obligatorios",
					MessageBox.ICON_WARNING | MessageBox.OK);
			return false;
		}
	}

	/**
	 * @return the prevStage
	 */
	public Stage getPrevStage() {
		return prevStage;
	}

	/**
	 * @param prevStage
	 *            the prevStage to set
	 */
	public void setPrevStage(Stage prevStage) {
		this.prevStage = prevStage;
	}

}
