/**
 * 
 */
package monopoly.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import monopoly.client.connection.ConnectionController;
import monopoly.client.util.ScreensFramework;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.ConstantesFXML;
import monopoly.util.encriptacion.Encrypter;
import monopoly.util.encriptacion.VernamEncrypter;
import monopoly.util.exception.CampoVacioException;
import monopoly.util.exception.EmailInvalidoException;

/**
 * @author pablo
 *
 */
@SuppressWarnings("deprecation")
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
		} catch (EmailInvalidoException eie) {
			Dialogs.create().owner(currentStage).title("Advertencia")
			.masthead("Campo Inválido").message(eie.getMessage())
			.showWarning();
		} catch (CampoVacioException cve) {
			Dialogs.create().owner(currentStage).title("Advertencia")
			.masthead("Campo Obligatorio").message(cve.getMessage())
			.showWarning();
		}
	}
	
	public void iniciarSesion(final Usuario user) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (user != null) {
					try {
						FXMLLoader loader = ScreensFramework.getLoader(ConstantesFXML.FXML_MENU_OPCIONES);
						Parent root = (Parent) loader.load();
						MenuOpcionesController controller = (MenuOpcionesController) loader
								.getController();
						controller.setPrevStage(currentStage);
						controller.setUsuarioLogueado(user);
						controller.showOptionMenu(root);

					} catch (IOException e) {
						GestorLogs.registrarException(e);
					}
				} else {
					lblMsgError.setText("Usuario / Contraseña inválida");
				}
			}
		});
	}

	private boolean validarCampos() throws CampoVacioException {
		if (txtNombre.getText().isEmpty()) {
			txtNombre.requestFocus();
			throw new CampoVacioException(
					"¡El Campo Nombre no puede estar vacio!");
		}

		if (txtUserName.getText().isEmpty()) {
			txtUserName.requestFocus();
			throw new CampoVacioException(
					"¡El Campo Usuario no puede estar vacio!");
		}

		if (txtPassword.getText().isEmpty()) {
			txtPassword.requestFocus();
			throw new CampoVacioException(
					"¡El Campo Contraseña no puede estar vacio!");
		}

		if (txtRepeatPassword.getText().isEmpty()) {
			txtRepeatPassword.requestFocus();
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
