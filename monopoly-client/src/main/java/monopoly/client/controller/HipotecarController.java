package monopoly.client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import monopoly.client.connection.ConnectionController;
import monopoly.model.Usuario;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.message.game.MortgageMessage;

public class HipotecarController extends AnchorPane implements Initializable {

	@FXML
	private TableColumn<PropiedadSimpleProperty, Integer> colPrecio;

	@FXML
	private TableView<PropiedadSimpleProperty> tblPropiedades;

	@FXML
	private Button btnHipotecar;

	@FXML
	private TableColumn<PropiedadSimpleProperty, Integer> colHipoteca;

	@FXML
	private Button btnCancelar;

	@FXML
	private TableColumn<PropiedadSimpleProperty, String> colNombre;

	@FXML
	private TableColumn<PropiedadSimpleProperty, String> colColor;

	@FXML
	private Stage currentStage;

	@FXML
	private Stage prevStage;
	private static HipotecarController instance = null;

	private List<TarjetaPropiedad> propiedadesList;

	private List<PropiedadSimpleProperty> filtersPropiedadesList;
	private ObservableList<PropiedadSimpleProperty> obsPropiedadesList;

	private Usuario usuarioLogueado = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		propiedadesList = new ArrayList<TarjetaPropiedad>();
		filtersPropiedadesList = new ArrayList<PropiedadSimpleProperty>();
		obsPropiedadesList = FXCollections
				.observableArrayList(filtersPropiedadesList);
	}

	public void showHipotecar(List<TarjetaPropiedad> propiedadesList) {
		this.propiedadesList = propiedadesList;

		if (filtersPropiedadesList == null)
			filtersPropiedadesList = new ArrayList<PropiedadSimpleProperty>();

		for (TarjetaPropiedad propiedad : this.propiedadesList) {
			filtersPropiedadesList.add(new PropiedadSimpleProperty(propiedad));
		}

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				configurarTabla();
				cargarTabla();
				currentStage.show();
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void cargarTabla() {
		obsPropiedadesList = FXCollections
				.observableArrayList(filtersPropiedadesList);
		tblPropiedades.setItems(obsPropiedadesList);
		tblPropiedades.getColumns().setAll(colNombre, colColor, colPrecio,
				colHipoteca);
	}

	@FXML
	void processCancel(ActionEvent event) {
		currentStage.close();
		prevStage.show();
	}

	@FXML
	void processHipotecar(ActionEvent event) {
		int senderID = ConnectionController.getInstance().getIdPlayer();
		String idJuego = TableroController.getInstance().getJuego()
				.getUniqueID();
		final PropiedadSimpleProperty propiedadSelected = tblPropiedades
				.getSelectionModel().getSelectedItem();

		MortgageMessage msg = new MortgageMessage(senderID, idJuego,
				propiedadSelected.getTarjetaPropiedad());
		ConnectionController.getInstance().send(msg);
	}

	/**
	 * Segunda etapa en la carga del juego. Cuando el server devuelve el juego,
	 * se carga el tablero con el juego.
	 * 
	 * @param juego
	 *            El juego que se quiere cargar. Viene desde el servidor
	 *            deserializado.
	 */
	@FXML
	public void finishMortgage(TarjetaPropiedad propiedad) {

		if (propiedad != null && propiedad.isHipotecada()) {
			TableroController.getInstance().showMessageBox(
					AlertType.INFORMATION,
					"Información",
					"Propiedad hipotecada",
					String.format("La propiedad %s se hipotecó por %s",
							propiedad.getNombrePropiedad(),
							propiedad.getValorHipotecario()));
		} else {
			TableroController.getInstance().showMessageBox(
					AlertType.ERROR,
					"Error",
					"Error de hipoteca",
					String.format("La propiedad %s no se pudo hipotecar",
							propiedad.getNombrePropiedad()));
		}

		currentStage.close();
	}

	public static HipotecarController getInstance() {
		if (instance == null)
			instance = new HipotecarController();
		return instance;
	}

	public Stage getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
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

	private void configurarTabla() {
		colNombre
				.setCellValueFactory(new PropertyValueFactory<PropiedadSimpleProperty, String>(
						"nombre"));

		colColor.setCellValueFactory(new PropertyValueFactory<PropiedadSimpleProperty, String>(
				"color"));

		colPrecio
				.setCellValueFactory(new PropertyValueFactory<PropiedadSimpleProperty, Integer>(
						"precioPropiedad"));

		colHipoteca
				.setCellValueFactory(new PropertyValueFactory<PropiedadSimpleProperty, Integer>(
						"precioHipoteca"));
	}

	public static class PropiedadSimpleProperty {
		private final SimpleStringProperty nombre;
		private final SimpleStringProperty color;
		private final SimpleIntegerProperty precioPropiedad;
		private final SimpleIntegerProperty precioHipoteca;
		private final SimpleObjectProperty<TarjetaPropiedad> tarjetaPropiedad;

		private PropiedadSimpleProperty(TarjetaPropiedad tarjeta) {
			this.nombre = new SimpleStringProperty(tarjeta.getNombre());

			if (tarjeta.isPropiedadEstacion())
				this.color = new SimpleStringProperty("<ESTACIÓN>");
			else if (tarjeta.isPropiedadCompania())
				this.color = new SimpleStringProperty("<COMPAÑÍA>");
			else
				this.color = new SimpleStringProperty(((TarjetaCalle) tarjeta)
						.getEnumColor().getColorCant());

			this.precioPropiedad = new SimpleIntegerProperty(
					tarjeta.getValorPropiedad());
			this.precioHipoteca = new SimpleIntegerProperty(
					tarjeta.getValorHipotecario());
			this.tarjetaPropiedad = new SimpleObjectProperty<TarjetaPropiedad>(
					tarjeta);
		}

		/**
		 * 
		 * @return El nombre de la propiedad
		 */
		public String getNombre() {
			return nombre.get();
		}

		/**
		 * 
		 * @return El color de la propiedad
		 */
		public String getColor() {
			return color.get();
		}

		/**
		 * @return El precio de la propiedad
		 */
		public Integer getPrecioPropiedad() {
			return precioPropiedad.get();
		}

		/**
		 * @return El precio de hipotecar la propiedad
		 */
		public Integer getPrecioHipoteca() {
			return precioHipoteca.get();
		}

		/**
		 * @return La tarjeta de la propiedad
		 */
		public TarjetaPropiedad getTarjetaPropiedad() {
			return tarjetaPropiedad.get();
		}
	}
}
