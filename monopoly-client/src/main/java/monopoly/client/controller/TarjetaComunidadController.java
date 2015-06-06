/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import monopoly.client.connection.ConnectionController;
import monopoly.model.tarjetas.TarjetaComunidad;
import monopoly.util.message.game.actions.CommunityCardMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class TarjetaComunidadController extends AnchorPane implements
		Initializable {

	@FXML
	private Stage currentStage;

	@FXML
	private Label lblMensaje;

	@FXML
	private Button btnAceptar;

	private TarjetaComunidad tarjetaSeleccionada;

	private String idJuego;

	private static TarjetaComunidadController instance;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		instance = this;
	}

	@FXML
	private void processAceptar(ActionEvent event) {
		CommunityCardMessage msg = new CommunityCardMessage(idJuego,
				tarjetaSeleccionada);
		ConnectionController.getInstance().send(msg);
		currentStage.close();
	}

	public void mostrarTarjeta(TarjetaComunidad tarjeta) {
		this.tarjetaSeleccionada = tarjeta;
		if (lblMensaje != null) {
			lblMensaje.setText(tarjetaSeleccionada.getObjetivo());
		}
	}

	public TarjetaComunidad getTarjetaSeleccionada() {
		return tarjetaSeleccionada;
	}

	public void setTarjetaSeleccionada(TarjetaComunidad tarjetaSeleccionada) {
		this.tarjetaSeleccionada = tarjetaSeleccionada;
	}

	public String getIdJuego() {
		return idJuego;
	}

	public void setIdJuego(String idJuego) {
		this.idJuego = idJuego;
	}

	public Stage getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

	public static TarjetaComunidadController getInstance() {
		if (instance == null)
			instance = new TarjetaComunidadController();
		return instance;
	}
}
