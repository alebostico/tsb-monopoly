/**
 * 
 */
package monopoly.client.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import monopoly.client.controller.TableroController;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class FXUtils {
	public static Object cargarStage(Stage pStage, String pFxml, String pTitle,
			boolean pResizable, boolean pFullScreen, Modality pModality,
			StageStyle pStyle) throws Exception {
		Scene scene = null;
		Parent root = null;
		FXMLLoader loader = null;
		Object controller = null;
		Image img = null;
		Stage wStage;

		loader = ScreensFramework.getLoader(pFxml);

		root = (Parent) loader.load();
		controller = loader.getController();

		scene = new Scene(root);
		pStage.setScene(scene);
		pStage.setResizable(pResizable);
		pStage.initModality(pModality);
		pStage.initStyle(pStyle);
		pStage.setFullScreen(pFullScreen);
		pStage.centerOnScreen();
		pStage.setTitle(pTitle);
		
		// Setteo el icono.
		
		wStage = (Stage)scene.getWindow();
		img = new Image(
				TableroController.class
						.getResourceAsStream("/images/logos/monopoly3.png"));
		wStage.getIcons().add(img);

		return controller;
	}
}
