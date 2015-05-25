/**
 * 
 */
package monopoly.client.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class FXUtils {
	public static Object cargarStage(Stage pStage, String pFxml, String pTitle, boolean pResizable, boolean pFullScreen, Modality pModality, StageStyle pStyle){
		Scene scene = null;
		Parent root = null;
		FXMLLoader loader = null;
		Object controller = null;
		
		try {			
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
			

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(ex.getMessage());
		}
		return controller;
	}
}
