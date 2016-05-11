/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import monopoly.client.connection.ConnectionController;
import monopoly.client.util.FXUtils;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.ConstantesFXML;
import monopoly.util.encriptacion.Encrypter;
import monopoly.util.encriptacion.VernamEncrypter;
import monopoly.util.exception.CampoVacioException;

/**
 * @author pablo
 * 
 */
public class LoginController extends AnchorPane implements Initializable {

	@FXML
	private TextField txtUserName;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Label lblMsgError;

	@FXML
	private Button btnLogin;

	@FXML
	private Button btnRegistrarme;

	@FXML
	private Button btnSalir;

	@FXML
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
			lblMsgError.setText(cve.getMessage());
		} catch (Exception ex) {
			lblMsgError.setText(ex.getMessage());
		}
	}

	public void validarUsuario(String userName, String password)
			throws Exception {
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

	public void iniciarSesion(final Usuario user) throws Exception {

		FutureTask<Void> taskMostrarOpciones = null;

		taskMostrarOpciones = new FutureTask<Void>(new Callable<Void>() {

			private Stage stage = null;

			@Override
			public Void call() throws Exception {

				MenuOpcionesController controller = null;
				String fxml = "";

				if (user != null) {

					GestorLogs.registrarLog("Desplegar Menú de Opciones..");

					fxml = ConstantesFXML.FXML_MENU_OPCIONES;
					stage = new Stage();
					controller = (MenuOpcionesController) FXUtils.cargarStage(
							stage, fxml, "Monopoly - Menú de Opciones", false,
							false, Modality.APPLICATION_MODAL,
							StageStyle.DECORATED);
					controller.setCurrentStage(stage);
					controller.setUsuarioLogueado(user);
					primaryStage.close();
					controller.getCurrentStage().showAndWait();
				} else {
					lblMsgError.setText("Usuario / Contraseña inválida");
				}
				return null;
			}
		});
		Platform.runLater(taskMostrarOpciones);
	}

	/**
	 * Devuelve true si todos los campos contienen caracteres, false en caso de
	 * que existan campos vacios, arrojando una excepcion
	 * 
	 * @return
	 */
	private boolean validarCampos() throws CampoVacioException {
		lblMsgError.setText("");
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
	public void processCreateAccount(ActionEvent event) {
		
		GestorLogs.registrarLog("Registrar Usuario...");
		
		Stage stage = null;
		String fxml = ConstantesFXML.FXML_REGISTRARME;
		RegistrarmeController controller = null;

		try {
			stage = new Stage();
			controller = (RegistrarmeController) FXUtils.cargarStage(
					stage, fxml, "Monopoly - Registrar Usuario", false,
					false, Modality.APPLICATION_MODAL,
					StageStyle.DECORATED);
			controller.setCurrentStage(stage);
			primaryStage.hide();
			controller.getCurrentStage().show();

		} catch (Exception ex) {
			lblMsgError.setText(ex.getMessage());
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
		return instance;
	}

}
