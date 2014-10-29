/**
 * 
 */
package monopoly.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

import org.controlsfx.dialog.Dialogs;

/**
 * @author pablo
 * 
 */
@SuppressWarnings("deprecation")
public class LoginController extends AnchorPane implements Initializable {

	@FXML
	private Button btnSalir;

	@FXML
	private Button btnConfig;

	@FXML
	private TextField txtUserName;

	@FXML
	private Button btnRegistrarme;

	@FXML
	private Button btnLohgin;

	@FXML
	private Label lblMsgError;

	@FXML
	private PasswordField txtPassword;

	private Stage primaryStage;

	private static LoginController instance = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		instance = this;
		lblMsgError.setText("");
	}

	public void processLogin(ActionEvent event) {
		String userName = "";
		String password = "";
		try {
			if (validarCampos()) {
				userName = txtUserName.getText();
				password = txtPassword.getText();
				validarUsuario(userName, password);
			}
		} catch (CampoVacioException cve) {
			Dialogs.create().owner(primaryStage).title("Advertencia")
					.masthead("Campo Obligatorio").message(cve.getMessage())
					.showWarning();
		}
	}

	public void validarUsuario(String userName, String password) {
		String passwordEnc = new String(password);
		Encrypter enc = new VernamEncrypter(passwordEnc);

		enc.code();
		passwordEnc = enc.getEncrypted();

		GestorLogs
				.registrarLog("Enviando mensaje al servidor para validar el usuario: "
						+ userName);

		Usuario usuario = new Usuario(userName);
		usuario.setPassword(passwordEnc);

		ConnectionController.getInstance().iniciarConexionToLogin(usuario);
	}

	public void iniciarSesion(final Usuario user) {
		final String fxml = ConstantesFXML.FXML_MENU_OPCIONES;

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (user != null) {
					try {
						FXMLLoader loader = ScreensFramework.getLoader(fxml);
						Parent root = (Parent) loader.load();
						MenuOpcionesController controller = (MenuOpcionesController) loader
								.getController();
						controller.setPrevStage(primaryStage);
						controller.setUsuarioLogueado(user);
						controller.showOptionMenu(root);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						GestorLogs.registrarError(e.getMessage());
					}
				} else {
					lblMsgError.setText("Usuario / Contraseña inválida");
				}
			}
		});
	}

	/**
	 * Devuelve true si todos los campos contienen caracteres, false en caso de
	 * que existan campos vacios, arrojando una excepcion
	 * 
	 * @return
	 */
	private boolean validarCampos() throws CampoVacioException {
		if (txtUserName.getText().equals("")) {
			txtUserName.requestFocus();
			throw new CampoVacioException(
					"¡El Campo Usuario no puede estar vacio!");
		}
		if (txtPassword.getText().equals("")) {
			txtPassword.requestFocus();
			throw new CampoVacioException(
					"¡El Campo Contraseña no puede estar vacio!");
		}
		return true;
	}

	@FXML
	public void processExit(ActionEvent event) {
		GestorLogs.registrarLog("Saliendo del Juego..");
		ConnectionController.getInstance().cerrarConexion();
	}

	@FXML
	void processConfig(ActionEvent event) {

	}

	@FXML
	public void processCreateAccount(ActionEvent event) {
		GestorLogs.registrarLog("Registrar Juego...");
		Parent root;
		String fxml = ConstantesFXML.FXML_REGISTRARME;

		try {
			Stage stage = new Stage();
			FXMLLoader loader = ScreensFramework.getLoader(fxml);

			root = (Parent) loader.load();
			RegistrarmeController controller = (RegistrarmeController) loader
					.getController();
			controller.setPrevStage(primaryStage);

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Monopoly - Registrar Usuario");
			stage.centerOnScreen();
			primaryStage.hide();
			controller.setCurrentStage(stage);
			stage.show();

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(ex.getMessage());
		}
	}

	/**
	 * @return the stage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * @param stage
	 *            the stage to set
	 */
	public void setPrimaryStage(Stage stage) {
		this.primaryStage = stage;
	}

	public static LoginController getInstance() {
		// yeah I know, NULL check and synchronize are missing...
		// the getInstance() method is seldom used - usually only for
		// message passing between stages
		return instance;
	}

}
