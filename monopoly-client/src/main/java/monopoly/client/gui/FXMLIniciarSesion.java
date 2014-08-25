/**
 * 
 */
package monopoly.client.gui;

import java.io.IOException;
import java.io.InputStream;

import javafx.application.Application;
import javafx.application.Platform;
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
 * 
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
	public void start(final Stage primaryStage) throws IOException {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						stage = primaryStage;
						gotoLogin();
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
			loginController = (LoginController) replaceSceneContent("/fxml/IniciarSesion.fxml");
			LoginController.APPLICATION = this;

		} catch (Exception ex) {
			GestorLogs.registrarError(ex.getMessage());
		}
	}

	private Initializable replaceSceneContent(String fxml) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml), null, new JavaFXBuilderFactory());
		InputStream in = getClass().getResourceAsStream(fxml); //FXMLIniciarSesion.class.getResourceAsStream(fxml);
		
		Parent root;
		try {
			root = (Parent) fxmlLoader.load(in);
		} finally {
			in.close();
		}

		Scene scene = new Scene(root);
		stage.setScene(scene);

		return (Initializable) fxmlLoader.getController();
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

	public void validarUsuario(final boolean existe, Usuario usuario) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (existe) {
					loginController.getErrorMessage().setText(
							"Usuario Logueado");
				} else {
					loginController.getErrorMessage().setText(
							"Usuario / Contraseña inválida");
				}
			}
		});
	}
}
