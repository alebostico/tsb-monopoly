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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import monopoly.client.util.ScreensFramework;
import monopoly.model.Juego;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.ConstantesFXML;

import org.controlsfx.dialog.Dialogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
@SuppressWarnings("deprecation")
public class UnirmeJuegoController extends AnchorPane implements Initializable {

    @FXML
    private TableView<JuegoSimpleProperty> tblJuegos;
    private List<Juego> juegosList;
    private List<JuegoSimpleProperty> filtersJuegosList;
    private ObservableList<JuegoSimpleProperty> obsJuegosList;

    @FXML
    private TextField txtUserName;

    @FXML
    private TableColumn<JuegoSimpleProperty, String> colNombre;
    
	@FXML
    private TableColumn<JuegoSimpleProperty, String> colFecha;

    @FXML
    private TableColumn<JuegoSimpleProperty, String> colCreador;
    
    @FXML
    private TableColumn<JuegoSimpleProperty, String> colParticipantes;
    
    @FXML
    private Button btnUnirmeJuego;

    @FXML
    private TextField txtNombreJuego;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtFechaHasta;

    @FXML
    private TextField txtFechaDesde;

    @FXML
    private Button btnBuscar;
    
    @FXML
	private Stage currentStage;

	@FXML
	private Stage prevStage;

	private Usuario usuarioLogueado = null;
	
	private Juego juegoSelected;

	private static UnirmeJuegoController instance;
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		instance = this;
		juegosList = new ArrayList<Juego>();
		filtersJuegosList= new ArrayList<JuegoSimpleProperty>();
		obsJuegosList = FXCollections.observableArrayList(filtersJuegosList);
	}
	
	public void showUnirmeJuego(List<Juego> juegosList) {
		this.juegosList = juegosList;
		if(filtersJuegosList == null)
			filtersJuegosList = new ArrayList<JuegoSimpleProperty>();
		for(Juego juego : this.juegosList){
			filtersJuegosList.add(new JuegoSimpleProperty(juego));
		}
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				configurarTabla();
				cargarTabla();
				currentStage.show();
			}
		});
	}
	
	private void configurarTabla(){
		// Columna Nombre
		colNombre = new TableColumn<>(
				"Juego");
		colNombre
				.setCellValueFactory(new PropertyValueFactory<JuegoSimpleProperty, String>(
						"nombre"));
		
		// Column Fecha
		colFecha = new TableColumn<>(
				"Fecha");
		colFecha
				.setCellValueFactory(new PropertyValueFactory<JuegoSimpleProperty, String>(
						"fecha"));
		
		// Columna Creador
		colCreador = new TableColumn<>(
				"Creador");
		colCreador
				.setCellValueFactory(new PropertyValueFactory<JuegoSimpleProperty, String>(
						"creador"));
		
		// Columna Participantes
		colParticipantes = new TableColumn<>(
				"Participantes");
		colParticipantes
				.setCellValueFactory(new PropertyValueFactory<JuegoSimpleProperty, String>(
						"participantes"));
		
		colNombre.prefWidthProperty().bind(tblJuegos.widthProperty().multiply(0.30));
		colFecha.prefWidthProperty().bind(tblJuegos.widthProperty().multiply(0.15));
		colCreador.prefWidthProperty().bind(tblJuegos.widthProperty().multiply(0.25));
		colParticipantes.prefWidthProperty().bind(tblJuegos.widthProperty().multiply(0.30));
		
	}
	
	@SuppressWarnings("unchecked")
	private void cargarTabla(){
		obsJuegosList = FXCollections
				.observableArrayList(filtersJuegosList);
		tblJuegos.setItems(obsJuegosList);
		tblJuegos.getColumns().setAll(
				colNombre, colFecha, colCreador,
				colParticipantes);
	}
	
	@FXML
    void processJoinGame(ActionEvent event) {
    	final JuegoSimpleProperty juegoSelected = tblJuegos.getSelectionModel().getSelectedItem();
    	String fxml = ConstantesFXML.FXML_UNIR_JUGADOR;
		Parent root;
		Stage stage = new Stage();
		FXMLLoader loader = null;
		try {

				loader = ScreensFramework.getLoader(fxml);

				root = (Parent) loader.load();
				UnirJugadorController controller = (UnirJugadorController) loader
						.getController();

				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setTitle("Monopoly - Unirme a Juego");
				stage.centerOnScreen();
				controller.setPrevStage(currentStage);
				controller.setCurrentStage(stage);
				controller.setJuegoSelected(juegoSelected.getJuego());
				controller.setUsuarioLogueado(usuarioLogueado);
				stage.show();
		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
			Dialogs.create().owner(currentStage).title("Error")
			.masthead("Error mediante una excepción").message(ex.getMessage())
			.showError();
		}
    	
    }

    @FXML
    void processCancel(ActionEvent event) {

    }

    @FXML
    void processSearch(ActionEvent event) {

    }
    
	/**
	 * @return the currentStage
	 */
	public Stage getCurrentStage() {
		return currentStage;
	}
	
	/**
	 * @param currentStage the currentStage to set
	 */
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
	 * @param prevStage the prevStage to set
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
	 * @param usuarioLogueado the usuarioLogueado to set
	 */
	public void setUsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}
	
	/**
	 * @return the juegoUnido
	 */
	public Juego getJuegoSelected() {
		return juegoSelected;
	}
	
	/**
	 * @param juegoUnido the juegoUnido to set
	 */
	public void setJuegoSelected(Juego juegoUnido) {
		this.juegoSelected = juegoUnido;
	}
	/**
	 * @return the instance
	 */
	public static UnirmeJuegoController getInstance() {
		if(instance == null)
			instance = new UnirmeJuegoController();
		return instance;
	}
    
	public static class JuegoSimpleProperty{
		private final SimpleStringProperty nombre;
		private final SimpleStringProperty fecha;
		private final SimpleStringProperty creador;
		private final SimpleStringProperty participantes;
		private final SimpleObjectProperty<Juego> juego;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		private JuegoSimpleProperty(Juego juego) {
			String aux = juego.getJugadoresList().size() + " de " + juego.getCantJugadores();
			this.nombre = new SimpleStringProperty(juego.getNombreJuego());
			this.fecha = new SimpleStringProperty(dateFormat.format(juego.getFechaCreacion()));
			this.creador = new SimpleStringProperty(juego.getOwner().getUserName());
			this.participantes = new SimpleStringProperty(aux);
			this.juego = new SimpleObjectProperty<Juego>(juego);
		}

		public String getNombre() {
			return nombre.get();
		}
		
		public String getFecha() {
			return fecha.get();
		}

		public String getCreador() {
			return creador.get();
		}

		public String getParticipantes() {
			return participantes.get();
		}
		
		public Juego getJuego() {
			return juego.get();
		}
		
		public void getNombre(String fNombre) {
			nombre.set(fNombre);
		}
		
		public void getFecha(String fFecha) {
			fecha.set(fFecha);
		}

		public void getCreador(String fCreador) {
			creador.set(fCreador);
		}

		public void getParticipantes(String fParticipantes) {
			participantes.set(fParticipantes);
		}
		
		public void setJuego(Juego fJuego)
		{
			juego.set(fJuego);
		}
		
	}

}
