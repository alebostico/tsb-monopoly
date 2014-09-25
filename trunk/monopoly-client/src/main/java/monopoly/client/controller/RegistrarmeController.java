/**
 * 
 */
package monopoly.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;
import monopoly.client.connection.ConexionController;
import monopoly.client.util.ScreensFramework;
import monopoly.message.impl.RegistrarmeMensaje;
import monopoly.model.Usuario;
import monopoly.util.ConstantesFXML;
import monopoly.util.GestorLogs;
import monopoly.util.encriptacion.Encrypter;
import monopoly.util.encriptacion.VernamEncrypter;
import monopoly.util.exception.CampoVacioException;
import monopoly.util.exception.EmailInvalidoException;
import monopoly.util.message.IMensaje;

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
	public static Stage prevStage;

	private static RegistrarmeController instance = null;

	@FXML
	void processCancel(ActionEvent event) {
		Stage stage = new Stage();
		Parent root;

		String fxml = ConstantesFXML.FXML_INICIAR_SESION;

		try {
			root = ScreensFramework.getParent(fxml);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Monopoly - Registrar Usuario");
			stage.centerOnScreen();
			prevStage.close();

			stage.show();

		} catch (IOException ex) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(ex.getMessage());
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(ex.getMessage());
		}
	}

	@FXML
	void processSave(ActionEvent event) {
		String nombre = "";
		String userName = "";
		String passwordEnc = "";
		String email = "";
		String vStr[] = null;
		Usuario usuario = null;

		if (validarCampos()) {
			Encrypter enc = new VernamEncrypter(txtPassword.getText());
			enc.code();
			passwordEnc = enc.getEncrypted();
			nombre = txtNombre.getText();
			userName = txtUserName.getText();
			email = txtEmail.getText();
			
			usuario = new Usuario(userName, passwordEnc);
			try {
				usuario.setEmail(email);
			} catch (EmailInvalidoException e) {
				// TODO Auto-generated catch block
				MessageBox.show(prevStage, e.getMessage(), "Campo inválido",
						MessageBox.ICON_WARNING | MessageBox.OK);
			}
			usuario.setNombre(nombre);
			
			vStr = new String[] { userName, passwordEnc, nombre, email };
			IMensaje msg = new RegistrarmeMensaje();
			ConexionController.THREAD_CLIENTE.enviarMensaje(msg
					.codificarMensaje(vStr));

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
			MessageBox.show(prevStage, cve.getMessage(), "Campo Obligatorio",
					MessageBox.ICON_WARNING | MessageBox.OK);
			return false;
		}
	}

	public static RegistrarmeController getInstance() {
		return instance;
	}

}
