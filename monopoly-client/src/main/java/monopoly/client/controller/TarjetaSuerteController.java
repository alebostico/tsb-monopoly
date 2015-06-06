/**
 * 
 */
package monopoly.client.controller;

import java.io.Serializable;
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
import monopoly.model.tarjetas.TarjetaSuerte;
import monopoly.util.message.game.actions.ChanceCardMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class TarjetaSuerteController extends AnchorPane implements
		Serializable, Initializable {

	private static final long serialVersionUID = -2714033349851758167L;

	@FXML
    private Label lblMensaje;

    @FXML
    private Button btnAceptar;
    
    @FXML
	private Stage currentStage;
    
    private TarjetaSuerte tarjetaSeleccionada;
    
    private String idJuego;
    
    private static TarjetaSuerteController instance;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		instance= this;
	}
	
	@FXML
    void processAceptar(ActionEvent event) {
		// enviar datos a servidor para procesar tarjeta.
		ChanceCardMessage msg = new ChanceCardMessage(idJuego, tarjetaSeleccionada);
		ConnectionController.getInstance().send(msg);
		currentStage.close();
    }
	
	public void mostrarTarjeta(TarjetaSuerte tarjeta){
		this.tarjetaSeleccionada = tarjeta;
		if(lblMensaje != null){
			lblMensaje.setText(tarjetaSeleccionada.getObjetivo());
		}
	}

	public Stage getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

	public TarjetaSuerte getTarjetaSeleccionada() {
		return tarjetaSeleccionada;
	}

	public void setTarjetaSeleccionada(TarjetaSuerte tarjetaSeleccionada) {
		this.tarjetaSeleccionada = tarjetaSeleccionada;
	}
	
	public String getIdJuego() {
		return idJuego;
	}

	public void setIdJuego(String idJuego) {
		this.idJuego = idJuego;
	}

	public static TarjetaSuerteController getInstance() {
		if (instance == null)
			instance = new TarjetaSuerteController();
		return instance;
	}
}
