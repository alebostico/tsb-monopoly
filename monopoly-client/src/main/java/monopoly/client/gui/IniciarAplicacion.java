/**
 * 
 */
package monopoly.client.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import monopoly.client.controller.LoginController;
import monopoly.client.util.FXUtils;
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
			loginController = (LoginController) FXUtils
					.cargarStage(primaryStage, fxml,
							"Monopoly - Iniciar Sesión", false,
							false, null,
							StageStyle.DECORATED);

			loginController.setPrimaryStage(primaryStage);
			primaryStage.show();

		} catch (Exception ex) {
			GestorLogs.registrarError(ex.getMessage());
		}
	}
}
