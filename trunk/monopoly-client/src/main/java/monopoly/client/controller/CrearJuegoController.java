/**
 * 
 */
package monopoly.client.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import monopoly.client.util.ScreensFramework;
import monopoly.model.Juego;
import monopoly.model.Usuario;
import monopoly.util.ConstantesFXML;
import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class CrearJuegoController extends AnchorPane implements Initializable {

	@FXML
	private Stage currentStage;

	@FXML
	private Stage prevStage;

	private Usuario usuarioLogueado = null;

	private Juego nuevoJuego = null;

	private static CrearJuegoController instance;

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

	public void inicializarVariables() {
		nuevoJuego = new Juego(usuarioLogueado, "");

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		if (nuevoJuego != null) {
			txtFechaCreacion.setText(df.format(nuevoJuego.getFechaCreacion()));
			txtIdJuego.setText(nuevoJuego.getUniqueID());
		}

		if (usuarioLogueado != null)
			txtUserName.setText(usuarioLogueado.getUserName());
	}

	@FXML
	private TextField txtUserName;

	@FXML
	private Button btnCrearJuego;

	@FXML
	private TextField txtNombreJuego;

	@FXML
	private TextField txtFechaCreacion;

	@FXML
	private Button btnCancelar;

	@FXML
	private TextField txtIdJuego;

	@FXML
	void processCancel(ActionEvent event) {

	}

	@FXML
	void processCreateGame(ActionEvent event) {

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

	public static CrearJuegoController getInstance() {
		return instance;
	}

	public void showMe(Usuario usuario) {
		GestorLogs.registrarLog("Creando nuevo Juego...");

		Parent root;
		Stage stage = new Stage();

		String fxml = ConstantesFXML.FXML_CREAR_JUEGO;

		try {
			root = ScreensFramework.getParent(fxml);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Monopoly - Nuevo Juego");
			stage.centerOnScreen();
			// prevStage.close();

			stage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(e.getMessage());
			;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(e.getMessage());
			;
		}

	}

}
