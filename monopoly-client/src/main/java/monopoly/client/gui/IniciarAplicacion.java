/**
 * 
 */
package monopoly.client.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import monopoly.client.controller.LoginController;
import monopoly.client.util.ScreensFramework;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.ConstantesFXML;

/**
 * 
 * @author pablo
 *
 */
public class IniciarAplicacion extends Application {

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
		gotoLogin(stage);
	}

	private void gotoLogin(Stage primaryStage) {
		String fxml = ConstantesFXML.FXML_INICIAR_SESION;
		LoginController loginController;
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
}
