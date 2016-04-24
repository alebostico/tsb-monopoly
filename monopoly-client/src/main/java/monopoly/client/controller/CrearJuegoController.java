/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import monopoly.client.util.FXUtils;
import monopoly.model.Juego;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.ConstantesFXML;
import monopoly.util.exception.CampoVacioException;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class CrearJuegoController extends AnchorPane implements Initializable {

	@FXML
	private TextField txtUserName;

	@FXML
	private TextField txtNombreJuego;

	@FXML
	private TextField txtFechaCreacion;

	@FXML
	private TextField txtIdJuego;

	@FXML
	private Button btnCrearJuego;

	@FXML
	private Button btnCancelar;

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
		instance = this;
	}

	public void showCrearJuego(Juego juego) {
		this.nuevoJuego = juego;
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

				if (nuevoJuego != null) {
					txtFechaCreacion.setText(df.format(nuevoJuego
							.getFechaCreacion()));
					txtIdJuego.setText(nuevoJuego.getUniqueID());
				}

				if (usuarioLogueado != null)
					txtUserName.setText(usuarioLogueado.getUserName());
				currentStage.show();
			}
		});
	}

	@FXML
	void processCancel(ActionEvent event) {
		currentStage.close();
		prevStage.show();
	}

	@FXML
	void processCreateGame(ActionEvent event) {
		String fxml ;
		Stage stageCrearJugadores;
		CrearJugadoresController controller;
		try {
			if (validarCamposVacios()) {
				
				nuevoJuego.setNombreJuego(txtNombreJuego.getText());
				
				stageCrearJugadores = new Stage();
				fxml = ConstantesFXML.FXML_CREAR_JUGADORES;
				
				controller = (CrearJugadoresController) FXUtils
						.cargarStage(stageCrearJugadores, fxml,
								"Monopoly - Nuevo Juego",
								false, false, Modality.APPLICATION_MODAL,
								StageStyle.UTILITY);

				controller.setPrevStage(prevStage);
				controller.setJuego(nuevoJuego);
				controller.setCurrentStage(stageCrearJugadores);
				controller.inicializarVariables();
				currentStage.close();
				stageCrearJugadores.show();
			}
		} catch (CampoVacioException cve) {
			FXUtils.getAlert(AlertType.WARNING, "Advertencia...", "Campo Obligatorio", cve.getMessage()).showAndWait();
		} catch (Exception ex) {
			GestorLogs.registrarException(ex);
			FXUtils.getAlert(AlertType.ERROR, "Error...", null, ex.getMessage()).showAndWait();
		}
	}

	private boolean validarCamposVacios() throws CampoVacioException {
		if (txtNombreJuego.getText().isEmpty()) {
			txtNombreJuego.requestFocus();
			throw new CampoVacioException(
					"El Campo Nombre no puedo estar vacio.");
		}
		return true;
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

	/**
	 * @return the currentStage
	 */
	public Stage getCurrentStage() {
		return currentStage;
	}

	/**
	 * @param currentStage
	 *            the currentStage to set
	 */
	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

}
