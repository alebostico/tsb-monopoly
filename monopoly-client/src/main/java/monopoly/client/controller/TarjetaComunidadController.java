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
import monopoly.model.tarjetas.TarjetaComunidad;

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
		// enviar datos a servidor para procesar tarjeta.
				currentStage.close();
	}
	public void mostrarTarjeta(TarjetaComunidad tarjeta){
		this.tarjetaSeleccionada =  tarjeta;
		if(lblMensaje != null){
			lblMensaje.setText(tarjetaSeleccionada.getObjetivo());
		}
	}

	public TarjetaComunidad getTarjetaSeleccionada() {
		return tarjetaSeleccionada;
	}

	public void setTarjetaSeleccionada(TarjetaComunidad tarjetaSeleccionada) {
		this.tarjetaSeleccionada = tarjetaSeleccionada;
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
