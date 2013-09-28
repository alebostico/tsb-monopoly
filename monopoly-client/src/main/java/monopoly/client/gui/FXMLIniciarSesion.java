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
import monopoly.client.controller.LoginController;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;

/**
 * @author pablo
 * 
 */
public class FXMLIniciarSesion extends Application {

	private LoginController loginController;
	
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
		stage = primaryStage;
		gotoLogin();
		primaryStage.show();
	}

	private void gotoLogin() {
		try {
			loginController = (LoginController) replaceSceneContent("IniciarSesion.fxml");
			loginController.setApp(this);
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

	/**
	 * @return the login
	 */
	public LoginController getLogin() {
		return loginController;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(LoginController login) {
		this.loginController = login;
	}
	
	public void resultadoLogueo(boolean existe, Usuario usuario)
	{
		loginController.resultadoLogueo(existe, usuario);
	}

}
