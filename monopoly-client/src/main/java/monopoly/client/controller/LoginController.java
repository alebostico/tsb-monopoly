/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import monopoly.client.connection.TCPClient;
import monopoly.client.gui.FXMLIniciarSesion;
import monopoly.model.Usuario;
import monopoly.util.ConstantesMensaje;
import monopoly.util.GestorLogs;
import monopoly.util.encriptacion.Encrypter;
import monopoly.util.encriptacion.VernamEncrypter;

/**
 * @author pablo
 * 
 */
public class LoginController extends AnchorPane implements Initializable {

	@FXML
	private TextField userId;

	@FXML
	private PasswordField password;

	@FXML
	private Button login;

	@FXML
	private Button registrarme;

	@FXML
	private Button salir;

	@FXML
	private Label errorMessage;

	public static FXMLIniciarSesion APPLICATION;

	private TCPClient cliente;
	private Process p;

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
		crearCliente();
	}

	public void destroy() {
		if (p != null) {
			p.destroy();
		}
	}

	private void crearCliente() {
		cliente = new TCPClient(this);
		cliente.start();
	}

	public void processLogin(ActionEvent event) {
		if (APPLICATION == null) {
			// We are running in isolated FXML, possibly in Scene Builder.
			// NO-OP.
			errorMessage.setText("Se produjo un error al iniciar sesión");
		} else {
			if (validarDatosIngresados()) {

				validarUsuario(userId.getText(), password.getText());
			} else {
				errorMessage
						.setText("¡Existen campos vacios! Complete por favor.");
				userId.setFocusTraversable(true);
			}
		}
	}

	private boolean validarDatosIngresados() {
		if (userId.getText().equals("")) {
			return false;
		}
		if (password.getText().equals(""))
			return false;
		return true;
	}

	public void validarUsuario(String userName, String password) {
		String passwordEnc = new String(password);
		Encrypter enc = new VernamEncrypter(passwordEnc);
		enc.code();
		passwordEnc = enc.getEncrypted();

		GestorLogs.registrarLog("Validando usuario: " + userName);
		cliente.enviarMensaje(MensajesController.codificarMensaje(new String[] {
				ConstantesMensaje.LOGIN, userName, passwordEnc }));

	}

	public static void validarUsuario(ArrayList<String> cadena) {
		boolean existe = false;

		/**
		 * [posicion - parametro] 0 - TipoMensaje ; 1 - existe ; 2 - idUsuario ;
		 * 3 - userName 4 - pass ; 5 - nombre ; 6 - email
		 **/
		existe = Boolean.parseBoolean(cadena.get(1));
		Usuario user = new Usuario(cadena.get(3));
		user.setIdUsuario(Integer.parseInt(cadena.get(2)));
		user.setPassword(cadena.get(4));
		user.setNombre(cadena.get(5));
		user.setEmail(cadena.get(6));
		APPLICATION.validarUsuario(existe, user);
	}

	public void processExit(ActionEvent event) {
		if (APPLICATION == null) {
			// We are running in isolated FXML, possibly in Scene Builder.
			// NO-OP.
			errorMessage.setText("Se produjo un error al iniciar sesión");
		} else {
			cliente.detenerHilo();
			System.exit(0);
		}
	}

	/**
	 * @return the errorMessage
	 */
	public Label getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(Label errorMessage) {
		this.errorMessage = errorMessage;
	}

}
