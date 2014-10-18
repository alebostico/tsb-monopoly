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
import monopoly.client.connection.ConnectionController;
import monopoly.model.Usuario;
import monopoly.util.encriptacion.Encrypter;
import monopoly.util.encriptacion.VernamEncrypter;
import monopoly.util.exception.CampoVacioException;
import monopoly.util.exception.EmailInvalidoException;

/**
 * @author pablo
 *
 */
public class RegistrarmeController extends AnchorPane implements Initializable {

	@FXML
	private Button btnCancelar;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtUserName;

	@FXML
	private TextField txtEmail;

	@FXML
	private Button btnGuardar;

	@FXML
	private PasswordField txtRepeatPassword;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Label lblMsgError;

	@FXML
	public Stage prevStage;

	@FXML
	private Stage currentStage;

	private static RegistrarmeController instance = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		instance = this;
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

	@FXML
	void processCancel(ActionEvent event) {
		prevStage.show();
		currentStage.close();
	}

	@FXML
	void processSave(ActionEvent event) {
		String nombre = "";
		String userName = "";
		String passwordEnc = "";
		String email = "";
		Usuario usuario = null;

		try {
			if (validarCampos()) {
				Encrypter enc = new VernamEncrypter(txtPassword.getText());
				enc.code();
				passwordEnc = enc.getEncrypted();
				nombre = txtNombre.getText();
				userName = txtUserName.getText();
				email = txtEmail.getText();

				usuario = new Usuario(userName, passwordEnc);
				usuario.setEmail(email);

				usuario.setNombre(nombre);

				ConnectionController.getInstance().iniciarConexionToAccount(usuario);
			}
		} catch (EmailInvalidoException e) {
			// TODO Auto-generated catch block
			MessageBox.show(prevStage, e.getMessage(), "Campo inválido",
					MessageBox.ICON_WARNING | MessageBox.OK);
		} catch (CampoVacioException cve) {
			MessageBox.show(prevStage, cve.getMessage(), "Campo Obligatorio",
					MessageBox.ICON_WARNING | MessageBox.OK);
		}
	}

	private boolean validarCampos() throws CampoVacioException {
		if (txtNombre.getText().isEmpty()) {
			txtNombre.setFocusTraversable(true);
			throw new CampoVacioException(
					"¡El Campo Nombre no puede estar vacio!");
		}

		if (txtUserName.getText().isEmpty()) {
			txtUserName.setFocusTraversable(true);
			throw new CampoVacioException(
					"¡El Campo Usuario no puede estar vacio!");
		}

		if (txtPassword.getText().isEmpty()) {
			txtPassword.setFocusTraversable(true);
			throw new CampoVacioException(
					"¡El Campo Contraseña no puede estar vacio!");
		}

		if (txtRepeatPassword.getText().isEmpty()) {
			txtRepeatPassword.setFocusTraversable(true);
			throw new CampoVacioException(
					"¡El Campo Confirmar Contraseña no puede estar vacio!");
		}
		return true;
	}

	public static RegistrarmeController getInstance() {
		if (instance == null)
			instance = new RegistrarmeController();
		return instance;
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

	/**
	 * @return the currentStage
	 */
	public Stage getCurrentStage() {
		return currentStage;
	}

	/**
	 * @param currentStage
	 *            the currentStage to set
	 */
	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

}
