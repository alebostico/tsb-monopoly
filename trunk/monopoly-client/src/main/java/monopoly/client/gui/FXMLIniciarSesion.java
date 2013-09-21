/**
 * 
 */
package monopoly.client.gui;

import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import monopoly.client.connection.TCPClient;
import monopoly.client.controller.LoginController;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.encriptacion.Encrypter;
import monopoly.util.encriptacion.VernamEncrypter;

/**
 * @author pablo
 * 
 */
public class FXMLIniciarSesion extends Application {

	private LoginController login;
	
	private TCPClient cliente;
	private Usuario usuarioLogeado;
	
	private Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		cliente = null;
		usuarioLogeado = null;
		stage = primaryStage;
		gotoLogin();
		primaryStage.show();
	}

	private void gotoLogin() {
		try {
			login = (LoginController) replaceSceneContent("IniciarSesion.fxml");
			login.setApp(this);
		} catch (Exception ex) {
			GestorLogs.registrarError(ex.getMessage());
		}
	}

	private Initializable replaceSceneContent(String fxml) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		InputStream in = FXMLIniciarSesion.class.getResourceAsStream(fxml);
		loader.setBuilderFactory(new JavaFXBuilderFactory());
		loader.setLocation(FXMLIniciarSesion.class.getResource(fxml));
		Parent root;
		try {
			root = (Parent) loader.load(in);
		} finally {
			in.close();
		}

		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		return (Initializable) loader.getController();
	}

	public void validarUsuario(String userName, String password){
		String passwordEnc = new String(password);
        Encrypter enc = new VernamEncrypter(passwordEnc);
        enc.code();
        passwordEnc = enc.getEncrypted();
        
        GestorLogs.registrarLog("Validando usuario: " + userName);
        Usuario usuario = new Usuario(userName, password);
        crearCliente(usuario);
        cliente.iniciarSesion(usuario);
        
    }
	
	private void crearCliente(Usuario usuario)
	{
		cliente = new TCPClient(usuario, this);
		cliente.start();
	}
	
	public void resultadoLogueo(boolean existe, Usuario usuario)
	{
		if(existe)
			login.UsuarioCorrecto();
		else
			login.usuarioIncorrecto();
			
	}
}
