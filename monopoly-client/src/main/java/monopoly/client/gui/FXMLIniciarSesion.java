/**
 * 
 */
package monopoly.client.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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

	public void validarUsuario(final boolean existe, Usuario usuario) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (existe) {
					Stage stage = new Stage();
					stage.setTitle("Opciones");
					Pane myPane;
					try {
						myPane = (Pane) ScreensFramework
								.getFXMLLoader("/fxml/MenuOpciones.fxml");

						OptionMenuController controller = (OptionMenuController) ScreensFramework
								.replaceSceneContent("/fxml/MenuOpciones.fxml",
										primaryStage); 
						controller.setPrevStage(primaryStage);
						Scene myScene = new Scene(myPane);
						primaryStage.setScene(myScene);
						primaryStage.show();
					} catch (IOException ex) {
						// TODO Auto-generated catch block
						GestorLogs.registrarError(ex.getMessage());
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						GestorLogs.registrarError(ex.getMessage());
					}
				} else {
					loginController.getErrorMessage().setText(
							"Usuario / Contraseña inválida");
				}
			}
		});
	}
}
