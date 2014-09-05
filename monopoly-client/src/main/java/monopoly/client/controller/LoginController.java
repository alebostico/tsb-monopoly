/**
 * 
 */
package monopoly.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import monopoly.client.connection.TCPClient;
import monopoly.client.gui.FXMLIniciarSesion;
import monopoly.client.util.ScreensFramework;
import monopoly.message.impl.LoginMensaje;
import monopoly.util.GestorLogs;
import monopoly.util.encriptacion.Encrypter;
import monopoly.util.encriptacion.VernamEncrypter;
import monopoly.util.message.IMensaje;

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
		
		IMensaje mensaje = (IMensaje) new LoginMensaje();
		
		cliente.enviarMensaje(mensaje.codificarMensaje(new String[] {userName, passwordEnc}));

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
	
	public void processRegister(ActionEvent event)
	{
		Parent root;
		RegistrarmeController controller;
		String fxml = "/fxml/Registrarme.fxml";
		
		if (APPLICATION == null) {
			// We are running in isolated FXML, possibly in Scene Builder.
			// NO-OP.
			errorMessage.setText("Se produjo un error al iniciar sesión");
		} else {
			try {
				root = ScreensFramework.getParent(fxml);
				
				controller = (RegistrarmeController) ScreensFramework
						.getController(fxml);
				
				APPLICATION.getPrimaryStage().setScene(new Scene(root));
				APPLICATION.getPrimaryStage().centerOnScreen();
				APPLICATION.getPrimaryStage().setTitle("Monopoly - Registrar Usuario");
				APPLICATION.getPrimaryStage().show();

			} catch (IOException ex) {
				// TODO Auto-generated catch block
				GestorLogs.registrarError(ex.getMessage());
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				GestorLogs.registrarError(ex.getMessage());
			}
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
