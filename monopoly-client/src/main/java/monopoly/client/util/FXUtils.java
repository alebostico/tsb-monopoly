/**
 * 
 */
package monopoly.client.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import monopoly.client.controller.TableroController;
import monopoly.util.GestorLogs;

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
		if (pModality != null)
			pStage.initModality(pModality);
		pStage.initStyle(pStyle);
		pStage.setFullScreen(pFullScreen);
		pStage.centerOnScreen();
		pStage.setTitle(pTitle);

		// Setteo el icono.

		wStage = (Stage) scene.getWindow();
		img = new Image(
				TableroController.class
						.getResourceAsStream("/images/logos/monopoly3.png"));
		wStage.getIcons().add(img);

		return controller;
	}

	public static Alert getAlert(AlertType type, String title, String headerText,
			String message) {

		Alert alert = null;
		ButtonType buttonAceptar = null;
		DialogPane dialogPane;
		Image img;
		Stage stage;

		try {
			alert = new Alert(type);

			alert.setTitle(title);
			alert.setHeaderText(headerText);
			alert.setContentText(message);
			buttonAceptar = new ButtonType("Aceptar", ButtonData.OK_DONE);
			alert.getButtonTypes().setAll(buttonAceptar);

			dialogPane = alert.getDialogPane();

			dialogPane.getStylesheets().add(
					TableroController.class.getResource("/css/Dialog.css").toExternalForm());
			dialogPane.getStyleClass().add("dialog");

			alert.getDialogPane()
					.getChildren()
					.stream()
					.filter(node -> node instanceof Label)
					.forEach(
							node -> ((Label) node)
									.setMinHeight(Region.USE_PREF_SIZE));

			// Seteo el icono de la cabecera.
			stage = (Stage) alert.getDialogPane().getScene().getWindow();
			img = new Image(
					TableroController.class
							.getResourceAsStream("/images/logos/monopoly3.png"));
			stage.getIcons().add(img);

			if (type == AlertType.INFORMATION) {
				// Set the icon (must be included in the project).
				img = new Image(
						TableroController.class
								.getResource("/images/iconos/monopoly5.png")
								.toString(), 48, 48, true, true);
				alert.setGraphic(new ImageView(img));
			}

		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
		}
		return alert;
	}
	
	public static boolean getAlertSioNo(String title, String headerText, String message){
		Alert alert;
		Optional<ButtonType> result = null;

		try {
			ButtonType buttonYes;
			ButtonType buttonNo;

			buttonYes = new ButtonType("Si", ButtonData.YES);
			buttonNo = new ButtonType("No", ButtonData.NO);

			alert = getAlert(
					AlertType.CONFIRMATION,
					title,
					headerText,
					message
					);
			alert.getButtonTypes().addAll(new ArrayList<ButtonType>(Arrays
							.asList(buttonYes, buttonNo)));

			result = alert.showAndWait();
			if(result.get() == buttonYes)
				return true;
		}
		catch (Exception ex)
		{
			GestorLogs.registrarError(ex);
		}
		return false;
	}

}
