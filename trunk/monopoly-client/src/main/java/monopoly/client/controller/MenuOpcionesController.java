/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import monopoly.client.connection.ConnectionController;
import monopoly.client.util.ScreensFramework;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.ConstantesFXML;

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
	private Stage prevStage;

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
		// TODO Auto-generated method stub
		instance = this;
	}

	public void showOptionMenu(Parent root) {
		GestorLogs.registrarLog("Desplegar Menú de Opciones..");
		
		try {
			currentStage = new Stage();
			
			Scene scene = new Scene(root);
			currentStage.setScene(scene);
			currentStage.setTitle("Monopoly - Menú de Opciones");
			currentStage.centerOnScreen();
			currentStage.setResizable(false);
			prevStage.close();
			currentStage.show();
			
//			Platform.runLater(new Runnable() {
//				@Override
//				public void run() {
//					
//				}
//			});
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(ex.getMessage());
		}
	}

	@FXML
	void processNewGame(ActionEvent event) {
		GestorLogs.registrarLog("Creando nuevo Juego...");
		String fxml = ConstantesFXML.FXML_CREAR_JUEGO;
		
		try {
			Parent root;
			Stage stage = new Stage();
			FXMLLoader loader = ScreensFramework.getLoader(fxml);
			
			root = (Parent)loader.load();
			CrearJuegoController controller = (CrearJuegoController)loader.getController();
			controller.setPrevStage(currentStage);
			controller.setUsuarioLogueado(usuarioLogueado);
			controller.inicializarVariables();

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Monopoly - Nuevo Juego");
			stage.centerOnScreen();
			// prevStage.close();
			controller.setCurrentStage(stage);
			stage.show();

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(ex.getMessage());
		}
	}

	@FXML
	void processLoadGame(ActionEvent event) {

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

	@FXML
	void processJoinGame(ActionEvent event) {

	}

	/**
	 * @return the prevStage
	 */
	public Stage getPrevStage() {
		return prevStage;
	}

	/**
	 * @param prevStage
	 *            the prevStage to set
	 */
	public void setPrevStage(Stage prevStage) {
		this.prevStage = prevStage;
	}

	public static MenuOpcionesController getInstance() {
		if(instance == null)
			instance = new  MenuOpcionesController();
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

}