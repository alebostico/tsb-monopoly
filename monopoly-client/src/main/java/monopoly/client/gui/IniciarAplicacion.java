/**
 * 
 */
package monopoly.client.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import monopoly.client.connection.ConexionController;
import monopoly.client.controller.LoginController;
import monopoly.client.util.ScreensFramework;
import monopoly.util.GestorLogs;

/**
 * 
 * @author pablo
 *
 */
public class IniciarAplicacion extends Application {

	private Stage primaryStage;
	
	private LoginController loginController;

	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws IOException {
		// TODO Auto-generated method stub
		primaryStage = stage;
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						ConexionController.iniciarConexion();
						gotoLogin();
					}
				});
			}
		}).start();
	}

	private void gotoLogin() {
		String fxml = "/fxml/IniciarSesion.fxml";
		try {
			loginController = (LoginController) ScreensFramework
					.replaceSceneContent(fxml, primaryStage);
			
			loginController.setPrimaryStage(primaryStage);
			primaryStage.setTitle("Monopoly - Iniciar Sesión");
			primaryStage.show();

		} catch (Exception ex) {
			GestorLogs.registrarError(ex.getMessage());
		}
	}

	/**
	 * @return the primaryStage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * @param primaryStage the primaryStage to set
	 */
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/**
	 * @return the loginController
	 */
	public LoginController getLoginController() {
		return loginController;
	}

	/**
	 * @param loginController the loginController to set
	 */
	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}
	
	
}
