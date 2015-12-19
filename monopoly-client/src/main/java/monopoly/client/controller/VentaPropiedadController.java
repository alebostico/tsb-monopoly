package monopoly.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import monopoly.client.connection.ConnectionController;
import monopoly.client.util.FXUtils;
import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;
import monopoly.util.StringUtils;
import monopoly.util.constantes.ConstantesFXML;
import monopoly.util.message.game.actions.BuyPropertyMessage;

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

	private Jugador jugadorComprador;

	private static VentaPropiedadController instance;

	@FXML
	void processComprar(ActionEvent event) {
		try {
			if (jugadorComprador.getDinero() >= tarjetaSelected
					.getValorPropiedad()) {
				BuyPropertyMessage msg = new BuyPropertyMessage(
						jugadorComprador.getJuego().getUniqueID(),
						tarjetaSelected);
				ConnectionController.getInstance().send(msg);
				currentStage.close();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Comprar Propiedad "
						+ tarjetaSelected.getNombre());
				alert.setHeaderText("Dinero insufiente para pagar propiedad.");
				alert.setContentText(String
						.format("No dispone de suficiente dinero para comprar la propiedad %s. Debe subastar la propiedad.",
								tarjetaSelected.getNombre()));
				alert.showAndWait();
			}
		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
			GestorLogs.registrarError(ex);
		}
	}

	@FXML
	void processSubasta(ActionEvent event) {
		javafx.application.Platform.runLater(new Runnable() {
			private Stage subastaStage = null;

			@Override
			public void run() {
				String fxml;
				SubastaController controller;

				try {

					fxml = ConstantesFXML.FXML_SUBASTA;
					subastaStage = new Stage();
					controller = (SubastaController) FXUtils.cargarStage(
							subastaStage, fxml, "Monopoly - Subasta", false,
							false, Modality.APPLICATION_MODAL,
							StageStyle.DECORATED);
					controller.setTarjetaSubasta(tarjetaSelected);
					subastaStage.show();
					//finalizarTurno();
					if (VentaPropiedadController.getInstance() != null)
						VentaPropiedadController.getInstance()
								.getCurrentStage().close();

				} catch (Exception e) {
					GestorLogs.registrarError(e);
				}
			}
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
		lblPrecioPropiedad.setText(StringUtils.formatearAMoneda(tarjetaSelected
				.getValorPropiedad()));
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

	public Jugador getJugadorComprador() {
		return jugadorComprador;
	}

	public void setJugadorComprador(Jugador jugadorComprador) {
		this.jugadorComprador = jugadorComprador;
	}

	public static VentaPropiedadController getInstance() {
		if (instance == null)
			instance = new VentaPropiedadController();
		return instance;
	}
}
