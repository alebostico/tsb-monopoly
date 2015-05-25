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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import monopoly.model.tarjetas.TarjetaPropiedad;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class VentaPropiedadController extends AnchorPane implements
		Initializable {

	@FXML
	private Label lblPrecioPropiedad;

	@FXML
	private ImageView imgTarjetaDetalle;

	@FXML
	private ImageView imgTarjetaPropiedad;

	@FXML
	private Button btnComprar;

	@FXML
	private Button btnSubasta;

	@FXML
	private Stage currentStage;

	private TarjetaPropiedad tarjetaSelected;

	private static VentaPropiedadController instance;

	@FXML
	void processComprar(ActionEvent event) {

	}

	@FXML
	void processSubasta(ActionEvent event) {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		instance = this;
	}

	public void cargarPropiedad(TarjetaPropiedad tarjetaSelected) {
		this.tarjetaSelected = tarjetaSelected;
		Image img = new Image(
				VentaPropiedadController.class
						.getResourceAsStream(tarjetaSelected
								.getPathImagenPropiedad()));
		imgTarjetaPropiedad.setImage(img);

		img = new Image(
				VentaPropiedadController.class
						.getResourceAsStream(tarjetaSelected
								.getPathImagenFrente()));
		imgTarjetaDetalle.setImage(img);
		lblPrecioPropiedad.setText(tarjetaSelected.getValorPropiedad() + " €");
	}

	public Stage getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

	public TarjetaPropiedad getTarjetaSelected() {
		return tarjetaSelected;
	}

	public void setTarjetaSelected(TarjetaPropiedad tarjetaSelected) {
		this.tarjetaSelected = tarjetaSelected;
	}

	public static VentaPropiedadController getInstance() {
		if (instance == null)
			instance = new VentaPropiedadController();
		return instance;
	}
}
