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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import monopoly.client.connection.TCPClient;
import monopoly.client.gui.FXMLIniciarSesion;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.encriptacion.Encrypter;
import monopoly.util.encriptacion.VernamEncrypter;

/**
 * @author pablo
 * 
 */
public class LoginController extends AnchorPane implements Initializable {

	@FXML
    TextField userId;
	
    @FXML
    PasswordField password;
    
    @FXML
    Button login;
    
    @FXML
    Button registrarme;
    
    @FXML
    Label errorMessage;
    
	private FXMLIniciarSesion application;
	
	private TCPClient cliente;
	private Usuario usuarioLogeado;

	/**
	 * @return the application
	 */
	public FXMLIniciarSesion getApplication() {
		return application;
	}

	/**
	 * @param application the application to set
	 */
	public void setApp(FXMLIniciarSesion application) {
		this.application = application;
	}


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
	
	private void crearCliente()
	{
		cliente = new TCPClient(this);
		cliente.start();
	}
	
	public void processLogin(ActionEvent event) {
        if (application == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            errorMessage.setText("Se produjo un error al iniciar sesión");
        } else {
        	if(validarDatosIngresados())
        	{
        		validarUsuario(userId.getText(), password.getText());
        	}
        	else{
        		errorMessage.setText("¡Existen campos vacios! Complete por favor.");
        		userId.setFocusTraversable(true);
        	}
        }
    }
	
	private boolean validarDatosIngresados()
	{
		if(userId.getText().equals("")){
			return false;
		}
		if(password.getText().equals(""))
			return false;
		return true;
	}
	
	public void validarUsuario(String userName, String password){
		String passwordEnc = new String(password);
        Encrypter enc = new VernamEncrypter(passwordEnc);
        enc.code();
        passwordEnc = enc.getEncrypted();
        
        GestorLogs.registrarLog("Validando usuario: " + userName);
        usuarioLogeado = new Usuario(userName, passwordEnc);
        cliente.iniciarSesion(usuarioLogeado);
    }
	
	public void resultadoLogueo(boolean existe, Usuario usuario)
	{
		if(existe)
			errorMessage.setText("Usuario Logueado");
		else
			errorMessage.setText("Usuario / Contraseña inválida");
	}

}
