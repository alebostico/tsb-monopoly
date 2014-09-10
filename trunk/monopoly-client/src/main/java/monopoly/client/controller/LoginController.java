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
import monopoly.message.impl.LoginMensaje;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.encriptacion.Encrypter;
import monopoly.util.encriptacion.VernamEncrypter;
import monopoly.util.exception.CampoVacioException;
import monopoly.util.message.IMensaje;

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
	private Button login;

	@FXML
	private Button registrarme;

	@FXML
	private Button salir;

	@FXML
	private Label lblMsgError;

	private Stage primaryStage;

	private IMensaje mensaje;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
//		Parent root = (Parent) primaryStage.getScene().getRoot();
//		root.setOnKeyPressed(new EventHandler<KeyEvent>() {
//	        @Override
//	        public void handle(KeyEvent e) {
//	            if (e.getCode().equals(KeyCode.ENTER)) {
//	            	processLogin(null);
//	            }
//	        }
//	    });
		lblMsgError.setText("");
	}

	public void processLogin(ActionEvent event) {
		String userName = "";
		String password = "";
		if (validarCampos()) {
			userName = txtUserName.getText();
			password = txtPassword.getText();
			validarUsuarioEnServidor(userName, password);
		}
	}

	/**
	 * Devuelve true si todos los campos contienen caracteres, false en caso de
	 * que existan campos vacios, arrojando una excepcion
	 * 
	 * @return
	 */
	private boolean validarCampos() {
		try {
			if (txtUserName.getText().equals("")) {
				txtUserName.setFocusTraversable(true);
				throw new CampoVacioException(
						"¡El Campo Usuario no puede estar vacio!");
			}
			if (txtPassword.getText().equals("")) {
				txtPassword.setFocusTraversable(true);
				throw new CampoVacioException(
						"¡El Campo Contraseña no puede estar vacio!");
			}
			return true;
		} catch (CampoVacioException cve) {
			MessageBox.show(primaryStage, cve.getMessage(),
					"Campos Obligatorios", MessageBox.ICON_WARNING
							| MessageBox.OK);
			return false;
		}
	}

	public void validarUsuarioEnServidor(String userName, String password) {
		String passwordEnc = new String(password);
		Encrypter enc = new VernamEncrypter(passwordEnc);

		enc.code();
		passwordEnc = enc.getEncrypted();

		GestorLogs
				.registrarLog("Enviando mensaje al servidor para validar el usuario: "
						+ userName);

		mensaje = (IMensaje) new LoginMensaje();

		ConexionController.THREAD_CLIENTE.enviarMensaje(mensaje
				.codificarMensaje(new String[] { userName, passwordEnc }));

	}

	public void evaluarResultadoLogueo(final boolean existe, final Usuario user) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (existe)
					showOptionMenu(user);
				else {
					lblMsgError.setText("Usuario / Contraseña inválida");
				}
			}
		});
	}

	@FXML
	public void processExit(ActionEvent event) {
		ConexionController.THREAD_CLIENTE.detenerHilo();
		System.exit(0);
	}

	@FXML
	public void processRegister(ActionEvent event) {
		Parent root;
		String fxml = "/fxml/Registrarme.fxml";

		try {
			root = ScreensFramework.getParent(fxml);
			RegistrarmeController controller = (RegistrarmeController) ScreensFramework
					.getController(fxml);
			controller.setPrevStage(primaryStage);
			primaryStage.setScene(new Scene(root));
			primaryStage.centerOnScreen();
			primaryStage.setTitle(
					"Monopoly - Registrar Usuario");
			primaryStage.show();

		} catch (IOException ex) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(ex.getMessage());
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(ex.getMessage());
		}
	}

	public void showOptionMenu(Usuario usuario) {
		Parent root;
		MenuOpcionesController controller;
		String fxml = "/fxml/MenuOpciones.fxml";

		try {
			root = ScreensFramework.getParent(fxml);

			controller = (MenuOpcionesController) ScreensFramework
					.getController(fxml);
			controller.setPrevStage(primaryStage);

			primaryStage.setScene(new Scene(root));
			primaryStage.centerOnScreen();
			primaryStage.setTitle("Monopoly - Menú de Opciones");
			primaryStage.show();

		} catch (IOException ex) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(ex.getMessage());
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

}
