/**
 * 
 */
package monopoly.client.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import monopoly.client.controller.LoginController;
import monopoly.client.controller.OptionMenuController;
import monopoly.client.util.ScreensFramework;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;

/**
 * 
 * @author pablo
 *
 */
public class FXMLIniciarSesion extends Application {

	private LoginController loginController;

	private Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(final Stage stage) throws IOException {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						primaryStage = stage;
						gotoLogin();
						primaryStage.setTitle("Monopoly - Iniciar Sesión");
						primaryStage.show();
					}
				});
			}
		}).start();
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		loginController.destroy();
	}

	private void gotoLogin() {
		try {
			loginController = (LoginController) ScreensFramework
					.replaceSceneContent("/fxml/IniciarSesion.fxml",
							primaryStage);
			LoginController.APPLICATION = this;

		} catch (Exception ex) {
			GestorLogs.registrarError(ex.getMessage());
		}
	}

	/**
	 * @return the login
	 */
	public LoginController getLogin() {
		return loginController;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(LoginController login) {
		this.loginController = login;
	}

	public void validarUsuario(final boolean existe, final Usuario usuario) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (existe)
					showOptionMenu(usuario);
				
				else {
					loginController.getErrorMessage().setText(
							"Usuario / Contraseña inválida");
				}
			}
		});
	}

	private void showOptionMenu(Usuario usuario) {
		Parent root;
		OptionMenuController controller;
		String fxml = "/fxml/MenuOpciones.fxml";

		try {
			root = ScreensFramework.getParent(fxml);
			
			controller = (OptionMenuController) ScreensFramework
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
	
	
}
