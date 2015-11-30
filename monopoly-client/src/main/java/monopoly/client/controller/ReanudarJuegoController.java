/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import monopoly.client.connection.ConnectionController;
import monopoly.client.util.ScreensFramework;
import monopoly.model.Juego;
import monopoly.model.MonopolyGameStatus;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.ConstantesFXML;
import monopoly.util.message.game.ConfirmGameReloadedMessage;
import monopoly.util.message.game.ReloadSavedGameMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class ReanudarJuegoController extends AnchorPane implements
		Initializable {

	@FXML
	private TableColumn<JuegoSimpleProperty, String> colNombre;

	@FXML
	private TableColumn<JuegoSimpleProperty, String> colFecha;

	@FXML
	private TableColumn<JuegoSimpleProperty, String> colGuardado;

	@FXML
	private TableColumn<JuegoSimpleProperty, String> colParticipantes;

	private List<JuegoSimpleProperty> filtersJuegosList;
	private ObservableList<JuegoSimpleProperty> obsJuegosList;

	@FXML
	private TableView<JuegoSimpleProperty> tblJuegos;

	@FXML
	private Button btnUnirmeJuego;

	@FXML
	private Button btnCancelar;

	@FXML
	private Stage currentStage;

	@FXML
	private Stage prevStage;

	private List<Juego> juegosList;

	private static ReanudarJuegoController instance = null;

	private Usuario usuarioLogueado = null;

	@FXML
	private TableroController controller;

	@FXML
	void processCancel(ActionEvent event) {
		currentStage.close();
		prevStage.show();
	}

	/**
	 * Primera etapa de la carga de juego. Envía un mensaje al servidor para
	 * buscar el juego que se desea cargar
	 * 
	 * @param event
	 */
	@FXML
	void processLoadGame(ActionEvent event) {
		int senderID = ConnectionController.getInstance().getIdPlayer();
		final JuegoSimpleProperty juegoSelected = tblJuegos.getSelectionModel()
				.getSelectedItem();

		try {
			String fxml = ConstantesFXML.FXML_MOSTRAR_TABLERO;
			Parent root;
			Stage stage = new Stage();
			FXMLLoader loader = ScreensFramework.getLoader(fxml);

			root = (Parent) loader.load();
			controller = (TableroController) loader.getController();

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Monopoly - Tablero - "
					+ usuarioLogueado.getNombre());
			stage.centerOnScreen();
			controller.setUsuarioLogueado(usuarioLogueado);
			controller.setCurrentStage(stage);
			controller.setPrevStage(currentStage);
		} catch (Exception ex) {
			TableroController.getInstance().showException(ex);
			GestorLogs.registrarError(ex);
		}

		ReloadSavedGameMessage msg = new ReloadSavedGameMessage(senderID,
				juegoSelected.getJuego().getUniqueID(), null);
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
	public void finishLoadGame(Juego juego,
			MonopolyGameStatus monopolyGameStatus) {
		try {
			controller.setPrevStage(currentStage);
			controller.setJuego(juego);
			controller.restaurarJuego(monopolyGameStatus);

			// Si todo va bien, envío un mensaje de confirmación...
			int senderID = ConnectionController.getInstance().getIdPlayer();
			ConfirmGameReloadedMessage msg = new ConfirmGameReloadedMessage(
					senderID, juego);
			ConnectionController.getInstance().send(msg);
		} catch (Exception ex) {
			TableroController.getInstance().showException(ex);
			GestorLogs.registrarError(ex);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		juegosList = new ArrayList<Juego>();
		filtersJuegosList = new ArrayList<JuegoSimpleProperty>();
		obsJuegosList = FXCollections.observableArrayList(filtersJuegosList);
	}

	public void showReanudarJuego(List<Juego> juegosList) {
		this.juegosList = juegosList;

		if (filtersJuegosList == null)
			filtersJuegosList = new ArrayList<JuegoSimpleProperty>();

		for (Juego juego : this.juegosList) {
			filtersJuegosList.add(new JuegoSimpleProperty(juego));
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

	public static ReanudarJuegoController getInstance() {
		if (instance == null)
			instance = new ReanudarJuegoController();
		return instance;
	}

	public Stage getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
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

	private void configurarTabla() {
		colNombre
				.setCellValueFactory(new PropertyValueFactory<JuegoSimpleProperty, String>(
						"nombre"));

		colFecha.setCellValueFactory(new PropertyValueFactory<JuegoSimpleProperty, String>(
				"fechaCreacion"));

		colGuardado
				.setCellValueFactory(new PropertyValueFactory<JuegoSimpleProperty, String>(
						"fechaGuardado"));

		colParticipantes
				.setCellValueFactory(new PropertyValueFactory<JuegoSimpleProperty, String>(
						"participantes"));
	}

	@SuppressWarnings("unchecked")
	private void cargarTabla() {
		obsJuegosList = FXCollections.observableArrayList(filtersJuegosList);
		tblJuegos.setItems(obsJuegosList);
		tblJuegos.getColumns().setAll(colNombre, colFecha, colGuardado,
				colParticipantes);
	}

	public static class JuegoSimpleProperty {
		private final SimpleStringProperty nombre;
		private final SimpleStringProperty fechaCreacion;
		private final SimpleStringProperty fechaGuardado;
		private final SimpleStringProperty creador;
		private final SimpleIntegerProperty participantes;
		private final SimpleObjectProperty<Juego> juego;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		private JuegoSimpleProperty(Juego juego) {
			this.nombre = new SimpleStringProperty(juego.getNombreJuego());
			this.fechaCreacion = new SimpleStringProperty(
					dateFormat.format(juego.getFechaCreacion()));
			this.fechaGuardado = new SimpleStringProperty(
					dateFormat.format(juego.getFechaGuardado()));
			this.creador = new SimpleStringProperty(juego.getOwner()
					.getUserName());
			this.participantes = new SimpleIntegerProperty(
					juego.getCantJugadores());
			this.juego = new SimpleObjectProperty<Juego>(juego);
		}

		public String getNombre() {
			return nombre.get();
		}

		public String getFechaCreacion() {
			return fechaCreacion.get();
		}

		public String getFechaGuardado() {
			return fechaGuardado.get();
		}

		public String getCreador() {
			return creador.get();
		}

		public int getParticipantes() {
			return participantes.get();
		}

		public Juego getJuego() {
			return juego.get();
		}

		public void getNombre(String fNombre) {
			nombre.set(fNombre);
		}

		public void getFechaCreacion(String fFecha) {
			fechaCreacion.set(fFecha);
		}
		
		public void getFechaGuardado(String fFecha){
			fechaCreacion.set(fFecha);
		}

		public void getCreador(String fCreador) {
			creador.set(fCreador);
		}

		public void getParticipantes(int fParticipantes) {
			participantes.set(fParticipantes);
		}

		public void setJuego(Juego fJuego) {
			juego.set(fJuego);
		}

	}

}
