/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import monopoly.client.connection.ConnectionController;
import monopoly.client.util.FXUtils;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.ConstantesFXML;
import monopoly.util.constantes.ConstantesMensaje;
import monopoly.util.message.CreateGameMessage;
import monopoly.util.message.game.GetSavedGamesMessage;

/**
 * @author pablo
 *
 */
public class MenuOpcionesController extends AnchorPane implements Initializable {

	@FXML
	private Button btnSalir;

	@FXML
	private Button btnAcercaDe;

	@FXML
	private Button btnUnirmeJuego;

	@FXML
	private Button btnReanudarJuego;

	@FXML
	private Button btnAyuda;

	@FXML
	private Button btnNuevoJuego;

	@FXML
	private Button btnOpciones;

	@FXML
	private Stage currentStage;

	private static MenuOpcionesController instance = null;

	private Usuario usuarioLogueado = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
	}

	@FXML
	void processNewGame(ActionEvent event) {
		GestorLogs.registrarLog("Creando nuevo Juego...");
		CrearJuegoController controller = null;
		String fxml = "";
		Stage stage = null;

		try {
			
			fxml = ConstantesFXML.FXML_MENU_OPCIONES;
			stage = new Stage();
			controller = (CrearJuegoController) FXUtils.cargarStage(
					stage, fxml, "Monopoly - Nuevo Juego", false,
					false, Modality.APPLICATION_MODAL,
					StageStyle.DECORATED);
			controller.setCurrentStage(stage);
			controller.setPrevStage(currentStage);
			controller.setUsuarioLogueado(usuarioLogueado);
			
			int senderId = ConnectionController.getInstance().getIdPlayer();
			ConnectionController.getInstance().send(
					new CreateGameMessage(senderId, usuarioLogueado));

		} catch (Exception ex) {
			GestorLogs.registrarException(ex);
			FXUtils.getAlert(AlertType.ERROR, "Error...", null, ex.getMessage()).showAndWait();
		}
	}

	@FXML
	void processJoinGame(ActionEvent event) {
		GestorLogs.registrarLog("Creando nuevo Juego...");
		
		UnirmeJuegoController controller = null;
		String fxml = "";
		Stage stage = null;
		
		try {
			
			fxml = ConstantesFXML.FXML_UNIRME_JUEGO;
			stage = new Stage();
			controller = (UnirmeJuegoController) FXUtils.cargarStage(
					stage, fxml, "Monopoly - Unirme a Juego", false,
					false, Modality.APPLICATION_MODAL,
					StageStyle.DECORATED);
			controller.setPrevStage(currentStage);
			controller.setUsuarioLogueado(usuarioLogueado);
			controller.setCurrentStage(stage);
			
			ConnectionController.getInstance().send(
					ConstantesMensaje.GET_PENDING_GAMES_MESSAGE);

		} catch (Exception ex) {
			GestorLogs.registrarException(ex);
			FXUtils.getAlert(AlertType.ERROR, "Error...", null, ex.getMessage()).showAndWait();
		}
	}

	@FXML
	void processLoadGame(ActionEvent event) {
		GestorLogs.registrarLog("Reanudando Juego...");
		
		ReanudarJuegoController controller;
		Stage stage = null;
		String fxml = "";

		try {
			
			fxml = ConstantesFXML.FXML_REANUDAR_JUEGO;
			stage = new Stage();
			
			controller = (ReanudarJuegoController) FXUtils.cargarStage(
					stage, fxml, "Monopoly - Reanudar juego",
					false, false, Modality.APPLICATION_MODAL,
					StageStyle.UTILITY);
			controller.setCurrentStage(stage);
			controller.setPrevStage(currentStage);
			controller.setUsuarioLogueado(usuarioLogueado);
			
			int senderId = ConnectionController.getInstance().getIdPlayer();
			ConnectionController.getInstance().send(
					new GetSavedGamesMessage(senderId, usuarioLogueado));
		} catch (Exception ex) {
			GestorLogs.registrarException(ex);
			FXUtils.getAlert(AlertType.ERROR, "Error...", null, ex.getMessage()).showAndWait();
		}

	}

	public void showJuegosPendientes() {
		
	}

	@FXML
	void processOptions(ActionEvent event) {
		
	}

	@FXML
	void processHelp(ActionEvent event) {
		
	}

	@FXML
	void processExit(ActionEvent event) {
		GestorLogs.registrarLog("Saliendo del Juego..");
		ConnectionController.getInstance().cerrarConexion();
	}

	@FXML
	void processAbout(ActionEvent event) {
		
	}

	public static MenuOpcionesController getInstance() {
		if (instance == null)
			instance = new MenuOpcionesController();
		return instance;
	}

	/**
	 * @return the usuarioLogueado
	 */
	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

	/**
	 * @param usuarioLogueado
	 *            the usuarioLogueado to set
	 */
	public void setUsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public Stage getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

}
