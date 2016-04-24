/**
 * 
 */
package monopoly.client.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import monopoly.client.connection.ConnectionController;
import monopoly.client.controller.TirarDadosController.TipoTiradaEnum;
import monopoly.client.util.FXUtils;
import monopoly.model.AccionEnCasillero;
import monopoly.model.Banco;
import monopoly.model.Deuda;
import monopoly.model.History;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.JugadorHumano;
import monopoly.model.JugadorVirtual;
import monopoly.model.MonopolyGameStatus;
import monopoly.model.SubastaStatus;
import monopoly.model.Usuario;
import monopoly.model.tablero.Casillero;
import monopoly.model.tablero.CasilleroCalle;
import monopoly.model.tablero.CasilleroCompania;
import monopoly.model.tablero.CasilleroEstacion;
import monopoly.model.tarjetas.Tarjeta;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaCompania;
import monopoly.model.tarjetas.TarjetaComunidad;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.model.tarjetas.TarjetaSuerte;
import monopoly.util.GestorLogs;
import monopoly.util.StringUtils;
import monopoly.util.constantes.ConstantesFXML;
import monopoly.util.constantes.EnumEstadoSubasta;
import monopoly.util.constantes.EnumSalidaCarcel;
import monopoly.util.constantes.EnumsTipoImpuesto;
import monopoly.util.exception.CondicionInvalidaException;
import monopoly.util.message.DisconnectPlayerMessage;
import monopoly.util.message.game.BankruptcyMessage;
import monopoly.util.message.game.ChatGameMessage;
import monopoly.util.message.game.CompleteTurnMessage;
import monopoly.util.message.game.SaveGameMessage;
import monopoly.util.message.game.actions.AuctionDecideMessage;
import monopoly.util.message.game.actions.BidForPropertyMessage;
import monopoly.util.message.game.actions.BidResultMessage;
import monopoly.util.message.game.actions.BuildMessage;
import monopoly.util.message.game.actions.DemortgageMessage;
import monopoly.util.message.game.actions.GoToJailMessage;
import monopoly.util.message.game.actions.MortgageMessage;
import monopoly.util.message.game.actions.PayRentMessage;
import monopoly.util.message.game.actions.PayToBankMessage;
import monopoly.util.message.game.actions.PayToLeaveJailMessage;
import monopoly.util.message.game.actions.SuperTaxMessage;
import monopoly.util.message.game.actions.UnbuildMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class TableroController extends AnchorPane implements Serializable,
		Initializable {

	private static final long serialVersionUID = 2964193640386734389L;

	@FXML
	private BorderPane pTablero;

	@FXML
	private TilePane pCasillero01;

	@FXML
	private TilePane pCasillero02;

	@FXML
	private TilePane pCasillero03;

	@FXML
	private TilePane pCasillero04;

	@FXML
	private TilePane pCasillero05;

	@FXML
	private TilePane pCasillero06;

	@FXML
	private TilePane pCasillero07;

	@FXML
	private TilePane pCasillero08;

	@FXML
	private TilePane pCasillero09;

	@FXML
	private TilePane pCasillero10;

	@FXML
	private TilePane pCasillero11;

	@FXML
	private TilePane pCasillero12;

	@FXML
	private TilePane pCasillero13;

	@FXML
	private TilePane pCasillero14;

	@FXML
	private TilePane pCasillero15;

	@FXML
	private TilePane pCasillero16;

	@FXML
	private TilePane pCasillero17;

	@FXML
	private TilePane pCasillero18;

	@FXML
	private TilePane pCasillero19;

	@FXML
	private TilePane pCasillero20;

	@FXML
	private TilePane pCasillero21;

	@FXML
	private TilePane pCasillero22;

	@FXML
	private TilePane pCasillero23;

	@FXML
	private TilePane pCasillero24;

	@FXML
	private TilePane pCasillero25;

	@FXML
	private TilePane pCasillero26;

	@FXML
	private TilePane pCasillero27;

	@FXML
	private TilePane pCasillero28;

	@FXML
	private TilePane pCasillero29;

	@FXML
	private TilePane pCasillero30;

	@FXML
	private TilePane pCasillero31;

	@FXML
	private TilePane pCasillero32;

	@FXML
	private TilePane pCasillero33;

	@FXML
	private TilePane pCasillero34;

	@FXML
	private TilePane pCasillero35;

	@FXML
	private TilePane pCasillero36;

	@FXML
	private TilePane pCasillero37;

	@FXML
	private TilePane pCasillero38;

	@FXML
	private TilePane pCasillero39;

	@FXML
	private TilePane pCasillero40;

	@FXML
	private TextArea txtMessageChat;

	@FXML
	private Accordion accordionPlayers;

	@FXML
	private Accordion accordionHistorial;

	@FXML
	private TitledPane tpHistory;

	@FXML
	private TitledPane tpChat;

	@FXML
	private Label lblStopwatch;

	@FXML
	private MenuButton btnMenu;

	@FXML
	private MenuItem btnBancarrota;

	@FXML
	private ListView<History> lvHistoryChat;
	private static List<History> historyChatList;
	private ObservableList<History> oHistoryChatList;

	@FXML
	private ListView<History> lvHistoryGame;
	private static List<History> historyGameList;
	private ObservableList<History> oHistoryGameList;

	@FXML
	private HBox hboxTurnoDados;

	@FXML
	private ImageView imgDados;

	@FXML
	private Label lblTurnoJugador;

	@FXML
	private Button btnFinalizarTurno;

	@FXML
	private Button btnTirarDados;

	private static TableroController instance;

	@FXML
	private Stage currentStage;

	@FXML
	private Stage prevStage;

	@FXML
	private Stage preloaderStage;

	@FXML
	private TitledPane[] tps;

	private Juego juego;

	private Usuario usuarioLogueado;

	private static MonopolyGameStatus estadoActual;

	private StringProperty clockLabelTextProperty;

	private Deuda deudaPendiente;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;

		historyGameList = new ArrayList<History>();
		historyChatList = new ArrayList<History>();
		oHistoryGameList = FXCollections.observableArrayList(historyGameList);
		oHistoryChatList = FXCollections.observableArrayList(historyChatList);
		accordionHistorial.setExpandedPane(tpHistory);

		txtMessageChat.setOnKeyPressed(new ChatEventHandler());

	}

	/**
	 * 
	 * Éste método muestra el tablero y muestra un messagebox
	 * informando al jugador que debe esperar a que se unan al juego otros
	 * oponentes.
	 * 
	 */
	public void showTableroDeJuego() throws Exception {
		loadStage();
		this.clockLabelTextProperty = lblStopwatch.textProperty();

		createDigitalClock();
		if (usuarioLogueado.equals(juego.getOwner()))
			addHistoryGame(usuarioLogueado.getUserName(), "Creó el juego.");
		else
			addHistoryGame(usuarioLogueado.getUserName(), "Sé unió al Juego.");

		esperarJugadores();
	}
	
	/**
	 * 
	 * Éste método muestra el tablero y muestra un messagebox informando al
	 * jugador que debe esperar a que se unan al juego otros oponentes.
	 * 
	 */
	public void restaurarJuego(MonopolyGameStatus monopolyGameStatus)
			throws Exception {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					loadStage();
					clockLabelTextProperty = lblStopwatch.textProperty();
					createDigitalClock();
				} catch (Exception ex) {
					GestorLogs.registrarError(ex);
					showNoFutureMessageBox(AlertType.ERROR, "Error..",
							"Error al restaurar el juego", ex.getMessage());
				}
			}
		});

		this.actualizarEstadoJuego(monopolyGameStatus);
	}
	
	/**
	 * Envía al servidor un mensaje para guardar en juego en un archivo para
	 * continuarlo más adelante
	 * 
	 */
	private void guardarJuego() {

		SaveGameMessage saveMessage = new SaveGameMessage(getJuego()
				.getUniqueID(), null);
		ConnectionController.getInstance().send(saveMessage);
	}

	
	/**
	 * Oculta el Menú de Opciones y muestra el tablero.
	 * 
	 * @return
	 */
	private Stage loadStage() {
		// currentStage.setFullScreen(true);
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		currentStage.setX(bounds.getMinX());
		currentStage.setY(bounds.getMinY());
		currentStage.setWidth(bounds.getWidth());
		currentStage.setHeight(bounds.getHeight());
		currentStage.show();
		prevStage.close(); // cierra la ventana de restauración
		MenuOpcionesController.getInstance().getCurrentStage().hide(); // oculta
																		// el
																		// menú
		currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				cerrar(false);
				we.consume();
			}
		});

		return currentStage;
	}
	
	/**
	 * Inicializa el reloj del tablero.
	 */
	public void createDigitalClock() {
		final Timeline timeline = new Timeline();

		timeline.setCycleCount(Timeline.INDEFINITE);
		final KeyFrame kf = new KeyFrame(Duration.seconds(1),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						clockLabelTextProperty.setValue(StringUtils
								.getFechaActual());
					}
				});
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	
	/**
	 * Método que muestra un messagebox informando que el jugador debe esperar
	 * por oponentes.
	 */
	private void esperarJugadores() {
		preloaderStage = new Stage();
		SplashController controller;

		try {

			String fxml = ConstantesFXML.FXML_SPLASH;

			preloaderStage = new Stage();
			controller = (SplashController) FXUtils.cargarStage(preloaderStage,
					fxml, "Monopoly - Esperando por jugadores", false, false,
					Modality.APPLICATION_MODAL, StageStyle.UNDECORATED);

			controller.setController(TableroController.this);
			controller.setCurrentStage(preloaderStage);
			preloaderStage.show();

		} catch (Exception ex) {
			GestorLogs.registrarException(ex);
			showNoFutureMessageBox(AlertType.ERROR, "Error...", null,
					ex.getMessage());
		}
	}
	
	/**
	 * Cierra la ventana del tablero y se desconecta
	 * 
	 * @param force
	 *            Especifica si se fuerza el cierre.
	 *            <ul>
	 *            <li>{@code false}: muestra un mensaje preguntando al usuario
	 *            si realmente desea salir del juego</li>
	 *            <li>{@code true}: sale del juego sin preguntarle al usuario</li>
	 *            </ul>
	 */
	private void cerrar(boolean force) {
		boolean answer = false;
		if (!force) {
			answer = showYesNoMsgBox("Abandonar juego", null,
					"¿Está seguro que desea abandonar el juego? Se perderá el progreso del juego.");
		}
		if (force || answer) {
			ConnectionController.getInstance().send(
					new DisconnectPlayerMessage(getJuego().getUniqueID(),
							usuarioLogueado.getUserName()));
			GestorLogs.registrarLog("Saliendo de monopolio...");
			ConnectionController.getInstance().cerrarConexion();
			currentStage.close();
			Platform.exit();
			System.exit(0);
		}
	}


	
	/**
	 * Agrega un history al panel de información que se utilizará para llevar un
	 * registro sobre jugadas o acciones que se realizan en el juego.
	 * 
	 * @param usuario
	 *            nombre que aparecerá en la primer columna informando quién fue
	 *            el que realizó el evento.
	 * @param mensaje
	 *            mensaje que se mostrará, para informar a los jugadores sobre
	 *            las acciones que se realizaró
	 * 
	 */
	private void addHistoryGame(String usuario, String mensaje)
			throws Exception {
		History history = new History(StringUtils.getFechaActual(), usuario,
				mensaje);
		addHistoryGame(history);
	}

	/**
	 * Agrega una history al panel de información que se utilizará para llevar
	 * un registro sobre jugadas o acciones que se realizan en el juego.
	 * 
	 * @param history
	 *            objeto historia que contiene información sobre el usuario,
	 *            descripción del mensaje, y fecha en el que se produjó el
	 *            evento.
	 */
	public void addHistoryGame(final History history) throws Exception {
		FutureTask<Void> taskAddHistory = null;
		taskAddHistory = new FutureTask<Void>(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				historyGameList.add(history);

				oHistoryGameList = FXCollections
						.observableArrayList(historyGameList);

				if (lvHistoryGame != null) {
					lvHistoryGame.getItems().clear();
					lvHistoryGame.setItems(oHistoryGameList);
					lvHistoryGame.setCellFactory(new HistoryCallback());
					lvHistoryGame.scrollTo(lvHistoryGame.getItems().size() - 1);
				}
				return null;
			}
		});
		Platform.runLater(taskAddHistory);
	}

	/**
	 * Agrega una historia al juego.
	 * 
	 * @param chatHistory
	 */
	public void addChatHistoryGame(final History chatHistory) throws Exception {

		FutureTask<Void> taskAddHistory = null;
		taskAddHistory = new FutureTask<Void>(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				historyChatList.add(chatHistory);

				oHistoryChatList = FXCollections
						.observableArrayList(historyChatList);

				if (lvHistoryChat != null) {
					lvHistoryChat.getItems().clear();
					lvHistoryChat.setItems(oHistoryChatList);
					lvHistoryChat.setCellFactory(new HistoryCallback());
					lvHistoryChat.scrollTo(lvHistoryChat.getItems().size() - 1);
				}
				return null;
			}
		});
		Platform.runLater(taskAddHistory);
	}

	/**
	 * Agrega historias a la subasta.
	 * 
	 * @param historyList
	 */
	public void addHistorySubasta(final List<History> historyList)
			throws Exception {
		for (History history : historyList) {
			SubastaController.getInstance().agregarHistoriaDeSubasta(history);
		}
	}

	/**
	 * Envía un mensaje a todos los jugadores.
	 * 
	 */
	public void sendChatMessage() {
		Usuario usuario = usuarioLogueado;
		String mensaje = this.txtMessageChat.getText();

		History history = new History(StringUtils.getFechaActual(),
				usuario.getUserName(), mensaje);

		ChatGameMessage chatMessage = new ChatGameMessage(getJuego()
				.getUniqueID(), history);

		this.txtMessageChat.setText("");

		ConnectionController.getInstance().send(chatMessage);

	}

	/**
	 * Método que muestra un messagebox informando que el jugador debe esperar
	 * por oponentes.
	 */
	private void esperarRespuestaOferta() {
		preloaderStage = new Stage();
		SplashController controller;

		try {
			String fxml = ConstantesFXML.FXML_SPLASH;

			preloaderStage = new Stage();
			controller = (SplashController) FXUtils.cargarStage(preloaderStage,
					fxml, "Monopoly - Esperando respuesta", false, false,
					Modality.APPLICATION_MODAL, StageStyle.UNDECORATED);

			controller.setController(TableroController.this);
			controller.setCurrentStage(preloaderStage);
			preloaderStage.show();
			controller.setLblMensaje("Esperando respuesta del jugador...");
			controller.setController(TableroController.this);
			controller.setCurrentStage(preloaderStage);
			preloaderStage.show();

		} catch (Exception ex) {
			GestorLogs.registrarException(ex);
			showNoFutureMessageBox(AlertType.ERROR, "Error...", null,
					ex.getMessage());
		}
	}
	
	/**
	 * Realiza las diferentes acciones que se puede realizar en el juego en base
	 * al casillero al cual avanzó en el caso de que haya sido su turno. Si no
	 * lo fue informa al usuario las direntes estrategias realizada por los
	 * contrincantes.
	 * 
	 * @param estadoTurno
	 *            Toda las información del juego.
	 */
	public void actualizarEstadoJuego(MonopolyGameStatus monopolyGameStatus) {

		estadoActual = monopolyGameStatus;

		try {

			// Cargo la Historia del juego
			for (History history : estadoActual.hirtoryList) {
				addHistoryGame(history);
			}

			// Actualizo la información en el tablero.
			actualizarTurnoJugador();
			actualizarGraficoEnElTablero();

			switch (estadoActual.estadoTurno) {
			/*
			 * opción cuando al jugador le toca tirar el dado.
			 */
			case TIRAR_DADO:
				mostrarTirarDados(true);
				showMessageBox(AlertType.INFORMATION, "Turno de juego...",
						null, "Es tu turno para jugar", null);
				break;

			case ACTUALIZANDO_ESTADO:
				break;

			case JUGANDO:
				mostrarTirarDados(false);
				realizarAccionEnCasillero();
				break;

			case ESPERANDO_TURNO:
				mostrarTirarDados(false);
				break;

			case DADOS_DOBLES:
				showDadosDobles();
				break;

			case PRESO:
				showOpcionesCarcel();
				break;

			case LIBRE:
				mostrarTirarDados(true);
				showMessageBox(AlertType.INFORMATION, "Turno de juego...",
						"Libre de la Cárcel.", "Continua jugando.", null);
				break;
			default:
				throw new CondicionInvalidaException("El estado de Turno "
						+ estadoActual.estadoTurno + " es inválido.");
			}
		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
			showException(ex, estadoActual.estadoTurno.name());
		}
	}
	
	/**
	 * Muestra la pantalla para tirar dados.
	 * 
	 */
	public void tirarDadosTurno() {
		Platform.runLater(new Runnable() {
			private Stage tirarDadosStage;

			@Override
			public void run() {
				String fxml;
				TirarDadosController controller;

				try {
					fxml = ConstantesFXML.FXML_TIRAR_DADOS;
					tirarDadosStage = new Stage();
					// controller = (TirarDadosTurnoController) FXUtils
					controller = (TirarDadosController) FXUtils.cargarStage(
							tirarDadosStage, fxml,
							"Monopoly - Tirar Dados para turnos", false, false,
							Modality.APPLICATION_MODAL, StageStyle.UNDECORATED);
					controller.setCurrentStage(tirarDadosStage);
					controller.settearDatos(usuarioLogueado.getNombre());
					controller.setTipoTirada(TipoTiradaEnum.TIRAR_TURNO);
					SplashController.getInstance().getCurrentStage().close();
					tirarDadosStage.showAndWait();
				} catch (Exception ex) {
					GestorLogs.registrarException(ex);
					showException(ex, "tirarDadosTurno...", null);
				}
			}
		});
	}

	/**
	 * Determinar que acción realizar.
	 * 
	 * @throws Exception
	 */
	private void realizarAccionEnCasillero() throws Exception {

		AccionEnCasillero accionCasillero = estadoActual.accionCasillero;
		Casillero casilleroActual = estadoActual.currentPlayer
				.getCasilleroActual();
		Jugador jugadorActual = estadoActual.currentPlayer;
		Tarjeta tarjetaSelected = estadoActual.tarjeta;
		List<Jugador> turnosList = estadoActual.turnos;
		String mensaje = estadoActual.getMensajeAux();
		int monto = estadoActual.getMonto();

		try {
			switch (accionCasillero.getAccion()) {

			case DESCANSO:
				showMessageBox(AlertType.INFORMATION, "Descanso...", null,
						mensaje, null);
				finalizarTurno();
				break;

			case DISPONIBLE_PARA_VENDER:
				disponibleParaLaVenta(jugadorActual, casilleroActual, mensaje);
				break;

			case TARJETA_SUERTE:
				showTarjetaSuerte((TarjetaSuerte) tarjetaSelected);
				break;

			case TARJETA_COMUNIDAD:
				showTarjetaComunidad((TarjetaComunidad) tarjetaSelected);
				break;

			case HIPOTECADA:
				showMessageBox(AlertType.INFORMATION,
						"Propiedad hipotecada...",
						"La Propiedad se encuentra hipotecada.", mensaje, null);
				finalizarTurno();
				break;

			case IMPUESTO_DE_LUJO:
				showImpuestoDeLujo(jugadorActual, mensaje, monto);
				break;

			case IMPUESTO_SOBRE_CAPITAL:
				showImpuestoSobreElCapital(jugadorActual, mensaje, monto);
				break;

			case MI_PROPIEDAD:
				showMessageBox(AlertType.INFORMATION, "Propiedad...", null,
						mensaje, null);
				finalizarTurno();
				break;

			case PAGAR_ALQUILER:
				pagarAlquiler(jugadorActual, casilleroActual, turnosList,
						mensaje, monto);
				break;

			case IR_A_LA_CARCEL:
				irALaCarcel(mensaje);
				break;

			default:
				showMessageBox(AlertType.ERROR, "Acción inválida",
						"Se Produjo un error.", String.format(
								"La acción %s no es una acción válida.",
								accionCasillero), null);
				break;
			}
		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
			showException(ex,
					"Se ha producido un error al realizar el movimiento en el casillero.");
		}
	}
	
	/**
	 * Muestra un mensaje con información sobre la propiedad que se encuentra en
	 * venta.
	 * 
	 * @param jugadorActual
	 * @param casilleroActual
	 * @param mensaje
	 */
	private void disponibleParaLaVenta(Jugador jugadorActual,
			Casillero casilleroActual, String mensaje) {

		try {
			showMessageBox(AlertType.INFORMATION,
					"Compra de propiedad dispobible...",
					casilleroActual.getNombreCasillero(), mensaje, null);

			switch (casilleroActual.getTipoCasillero()) {
			case C_CALLE:
				showVentaPropiedad(
						((CasilleroCalle) casilleroActual).getTarjetaCalle(),
						jugadorActual);
				break;
			case C_COMPANIA:
				showVentaPropiedad(
						((CasilleroCompania) casilleroActual)
								.getTarjetaCompania(),
						jugadorActual);
				break;

			case C_ESTACION:
				showVentaPropiedad(
						((CasilleroEstacion) casilleroActual)
								.getTarjetaEstacion(),
						jugadorActual);
				break;
			default:
				throw new CondicionInvalidaException(
						"Tipo de casillero inválido.");
			}
		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
			showException(ex, "disponibleParaLaVenta");
		}
	}
	


	/**
	 * Muestra la pantalla para vender un propiedad.
	 * 
	 * @param tarjetaPropiedad
	 * @param jugador
	 */
	private void showVentaPropiedad(final TarjetaPropiedad tarjetaPropiedad,
			final Jugador jugador) {
		Platform.runLater(new Runnable() {
			private Stage ventaPropiedadStage = null;
			private TarjetaPropiedad tarjeta = tarjetaPropiedad;
			private JugadorHumano jugadorComprador = (JugadorHumano) jugador;

			@Override
			public void run() {
				String fxml;
				VentaPropiedadController controller;

				try {

					fxml = ConstantesFXML.FXML_VENTA_PROPIEDAD;
					ventaPropiedadStage = new Stage();
					controller = (VentaPropiedadController) FXUtils
							.cargarStage(ventaPropiedadStage, fxml,
									"Monopoly - Comprar Propiedad", false,
									false, Modality.APPLICATION_MODAL,
									StageStyle.TRANSPARENT);
					controller.setCurrentStage(ventaPropiedadStage);
					controller.cargarPropiedad(tarjeta);
					controller.setJugadorComprador(jugadorComprador);
					controller.setIdJuego(juego.getUniqueID());
					ventaPropiedadStage.show();
				} catch (Exception ex) {
					GestorLogs.registrarException(ex);
					showException(ex, "showVentaPropiedad...", null);
				}
			}
		});
	}

	/**
	 * Muestra el objetivo de la Tarjeta Suerte.
	 * 
	 * @param tarjetaSuerte
	 */
	private void showTarjetaSuerte(final TarjetaSuerte tarjetaSuerte) {
		Platform.runLater(new Runnable() {
			private Stage TarjetaSuerteStage = null;
			private TarjetaSuerte tarjeta = tarjetaSuerte;

			@Override
			public void run() {
				String fxml;
				TarjetaSuerteController controller;

				try {

					fxml = ConstantesFXML.FXML_TARJETA_SUERTE;
					TarjetaSuerteStage = new Stage();
					controller = (TarjetaSuerteController) FXUtils.cargarStage(
							TarjetaSuerteStage, fxml,
							"Monopoly - Tarjeta Suerte", false, false,
							Modality.APPLICATION_MODAL, StageStyle.TRANSPARENT);
					controller.setCurrentStage(TarjetaSuerteStage);
					controller.mostrarTarjeta(tarjeta);
					controller.setIdJuego(getJuego().getUniqueID());
					TarjetaSuerteStage.show();
				} catch (Exception ex) {
					GestorLogs.registrarError(ex);
					showException(ex, "showTarjetaSuerte...", null);
				}
			}
		});
	}
	
	/**
	 * Muestra el objetivo de la Tarjeta Comunidad.
	 * 
	 * @param tarjetaComunidad
	 */
	private void showTarjetaComunidad(final TarjetaComunidad tarjetaComunidad) {
		Platform.runLater(new Runnable() {
			private Stage tarjetaComunidadStage = null;
			private TarjetaComunidad tarjeta = tarjetaComunidad;

			@Override
			public void run() {
				String fxml;
				TarjetaComunidadController controller;

				try {

					fxml = ConstantesFXML.FXML_TARJETA_COMUNIDAD;
					tarjetaComunidadStage = new Stage();
					controller = (TarjetaComunidadController) FXUtils
							.cargarStage(tarjetaComunidadStage, fxml,
									"Monopoly - Tarjeta Comunidad", false,
									false, Modality.APPLICATION_MODAL,
									StageStyle.TRANSPARENT);
					controller.setCurrentStage(tarjetaComunidadStage);
					controller.setIdJuego(getJuego().getUniqueID());
					controller.mostrarTarjeta(tarjeta);
					tarjetaComunidadStage.show();
				} catch (Exception ex) {
					GestorLogs.registrarError(ex);
					showException(ex, "showTarjetaComunidad...", null);
				}
			}
		});
	}
	

	/**
	 * Muestra un mensaje para pagar el impuesto de lujo.
	 * 
	 * @param jugadorActual
	 * @param mensaje
	 * @param monto
	 */
	private void showImpuestoDeLujo(final Jugador jugadorActual,
			final String mensaje, final int monto) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				PayToBankMessage msgPayToBank;
				String msgSinDinero;
				String idJuego;
				String mensajeAux;
				Alert alert;
				DialogPane dialogPane;
				Image img;

				try {
					idJuego = juego.getUniqueID();
					msgSinDinero = "No cuentas con suficiente dinero para pagar %s. Vende hoteles, casas o hipoteca propiedades para continuar con el juego.";

					alert = getAlert(AlertType.INFORMATION,
							"Impuesto de lujo...", "Debes pagar el impuesto.",
							mensaje, null);
					dialogPane = alert.getDialogPane();

					img = new Image(
							TableroController.class
									.getResourceAsStream("/images/logos/luxury_tax.gif"),
							48, 48, true, true);

					dialogPane.setGraphic(new ImageView(img));

					alert.showAndWait();

					if (jugadorActual.getDinero() >= monto) {
						mensajeAux = String.format(
								"Ha pagado al banco %s de impuesto de lujo.",
								StringUtils.formatearAMoneda(100));
						msgPayToBank = new PayToBankMessage(idJuego, 100,
								mensajeAux);
						ConnectionController.getInstance().send(msgPayToBank);
					} else {
						registrarDeuda(monto);
						showMessageBox(AlertType.WARNING,
								"Impuesto de lujo...",
								"Debes pagar el impuesto.",
								String.format(msgSinDinero, "el impuesto"),
								null);
					}
				} catch (Exception ex) {
					GestorLogs.registrarError(ex);
					showException(ex, "showImpuestoDeLujo...", null);
				}
			}
		});
	}

	/**
	 * Muestra un mensaje sobre el impuesto sobre el capital.
	 * 
	 * @param jugadorActual
	 * @param mensaje
	 * @param monto
	 */
	private void showImpuestoSobreElCapital(final Jugador jugadorActual,
			final String mensaje, final int monto) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				String msgSinDinero;
				String idJuego;
				Alert alert;
				DialogPane dialogPane;
				Image img;

				try {
					idJuego = juego.getUniqueID();
					msgSinDinero = "No cuentas con suficiente dinero para pagar %s. Vende hoteles, casas o hipoteca propiedades para continuar con el juego.";

					ButtonType buttonPorcentaje;
					ButtonType buttonMonto;
					Optional<ButtonType> result;
					SuperTaxMessage msgSuperTax = null;

					buttonPorcentaje = new ButtonType("Pagar 10%");
					buttonMonto = new ButtonType("Pagar "
							+ StringUtils.formatearAMoneda(200));

					alert = getAlert(AlertType.CONFIRMATION,
							"Impuesto sobre el capital...",
							"Debes pagar el impuesto.", mensaje,
							Arrays.asList(buttonPorcentaje, buttonMonto));

					//
					dialogPane = alert.getDialogPane();
					img = new Image(
							TableroController.class
									.getResourceAsStream("/images/logos/impuestoalcapital.png"),
							48, 48, true, true);
					dialogPane.setGraphic(new ImageView(img));

					result = alert.showAndWait();

					if (result.get() == buttonPorcentaje) {
						msgSuperTax = new SuperTaxMessage(juego.getUniqueID(),
								EnumsTipoImpuesto.TIPO_IMPUESTO_PORCENTAJE);
					} else {
						msgSuperTax = new SuperTaxMessage(idJuego,
								EnumsTipoImpuesto.TIPO_IMPUESTO_MONTO);

						if (jugadorActual.getDinero() < 200) {
							registrarDeuda(monto);
							showMessageBox(AlertType.WARNING,
									"Impuesto de sobre el capital...",
									"Debes pagar el impuesto.",
									String.format(msgSinDinero, "el impuesto"),
									null);
							return;
						}
					}
					ConnectionController.getInstance().send(msgSuperTax);

				} catch (Exception ex) {
					GestorLogs.registrarError(ex);
					showException(ex, "showImpuestoSobreElCapital...", null);
				}

			}
		});
	}
	

	/**
	 * Muestra un mensaje informando cuando debe pagar al alquiler al jugador
	 * propietario.
	 * 
	 * @param jugadorActual
	 * @param casilleroActual
	 * @param turnosList
	 * @param mensaje
	 * @param monto
	 */
	private void pagarAlquiler(final Jugador jugadorActual,
			final Casillero casilleroActual, final List<Jugador> turnosList,
			final String mensaje, final int monto) {

		PayRentMessage msgPayRent;
		String msgSinDinero;
		String idJuego;
		try {
			idJuego = juego.getUniqueID();
			msgSinDinero = "No cuentas con suficiente dinero para pagar %s. Vende hoteles, casas o hipoteca propiedades para continuar con el juego.";

			showMessageBox(AlertType.INFORMATION, "Pagar...",
					"Pagar alquiler.", mensaje, null);
			if (jugadorActual.getDinero() >= monto) {
				msgPayRent = new PayRentMessage(idJuego,
						casilleroActual.getNumeroCasillero());
				ConnectionController.getInstance().send(msgPayRent);
			} else {
				registrarDeuda(monto);
				showMessageBox(AlertType.WARNING, "Alquiler...",
						"Debes pagar el alquiler.",
						String.format(msgSinDinero, "el alquiler"), null);
			}
		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
			showException(ex, "pagarAlquiler");
		}
	}


	/**
	 * Muestra un mensaje informando que el jugador irá a la cárcel.
	 * 
	 * @param mensaje
	 */
	private void irALaCarcel(final String mensaje) {
		try {
			GoToJailMessage msgGoToJailMessage;
			showMessageBox(AlertType.INFORMATION, "Marche preso...", null,
					mensaje, "/images/logos/in_prision.png");
			// enviar mensaje;
			msgGoToJailMessage = new GoToJailMessage(juego.getUniqueID());
			ConnectionController.getInstance().send(msgGoToJailMessage);
		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
			showException(ex, "irALaCarcel...");
		}
	}


	/**
	 * Muestra un mensaje con el resultado del guardado del juego.
	 * 
	 * @param exception
	 *            Si el juego se guardó, {@code exception} es <code>null</code>.
	 *            Si hubo algún error, se pasa la excepción que se generó.
	 */
	public void showJuegoGuardado(IOException exception) {
		AlertType alertType;
		String msgHeader;
		String msgGuardado;

		if (exception == null) {
			alertType = AlertType.INFORMATION;
			msgHeader = "Juego guardado";
			msgGuardado = "El juego se guardó correctamente";
		} else {
			alertType = AlertType.ERROR;
			msgHeader = "El juego no se pudo guardar";
			msgGuardado = exception.getMessage();
		}

		showMessageBox(alertType, "Estado de Juego", msgHeader, msgGuardado,
				null);

		// Una vez que le informamos al usuario que el juego se guardó
		// correctamente, cerramos el juego
		if (exception == null)
			this.cerrar(true);
	}

	/**
	 * Muestra las opción que tiene el jugador para salir de la cárcel. Pueden
	 * ser:
	 * <ol>
	 * <li>Pagar un monto fijo</li>
	 * <li>Tirar dados dobles</li>
	 * <li>Utilizar una tarjeta de la cárcel</li>
	 * </ol>
	 */
	private void showOpcionesCarcel() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				String msgSinDinero;
				String idJuego;
				Alert alert;
				Jugador jugadorActual;
				PayToLeaveJailMessage msgPayToLeaveJail;

				try {
					idJuego = juego.getUniqueID();
					jugadorActual = estadoActual.currentPlayer;
					msgSinDinero = "No cuentas con suficiente dinero para pagar %s. Vende hoteles, casas o hipoteca propiedades para continuar con el juego.";
					boolean tercerTurno = jugadorActual
							.getCantidadTurnosCarcel() >= 3;

					ButtonType buttonPagar;
					ButtonType buttonUsarTarjeta;
					ButtonType buttonTirarDados;
					List<ButtonType> buttons;
					Optional<ButtonType> result;
					String contentText = "";

					buttonPagar = new ButtonType(String.format("Pagar %s",
							StringUtils.formatearAMoneda(50)));
					buttonUsarTarjeta = new ButtonType("Usar Tarjeta");

					if (tercerTurno)
						buttonTirarDados = new ButtonType("Tirar dados");
					else
						buttonTirarDados = new ButtonType("Sacar dados dobles");

					buttons = new ArrayList<ButtonType>();

					if (jugadorActual.getTarjetaCarcelList().size() > 0) {
						buttons.add(buttonUsarTarjeta);
					}

					if (!tercerTurno) {
						buttons.add(buttonPagar);
					}

					buttons.add(buttonTirarDados);

					alert = getAlert(AlertType.CONFIRMATION, "Comisaria",
							"Estás en la cárcel, debes salir.", contentText,
							buttons);

					DialogPane dialog = alert.getDialogPane();

					Image img = new Image(
							TableroController.class
									.getResourceAsStream("/images/logos/in_prision.png"),
							48, 48, true, true);
					dialog.setGraphic(new ImageView(img));

					result = alert.showAndWait();

					if (result.get() == buttonTirarDados) {
						// bloquearAcciones(false);
						mostrarTirarDados(true);
					} else if (result.get() == buttonUsarTarjeta) {
						msgPayToLeaveJail = new PayToLeaveJailMessage(idJuego,
								EnumSalidaCarcel.USAR_TARJETA);
						ConnectionController.getInstance().send(
								msgPayToLeaveJail);
					} else {

						if (jugadorActual.getDinero() < 50) {
							registrarDeuda(50);
							showMessageBox(AlertType.WARNING, "Comisaria",
									"Debes pagar para salir de la cárcel.",
									String.format(msgSinDinero,
											"la salida de la cárcel"), null);
							return;
						} else {
							msgPayToLeaveJail = new PayToLeaveJailMessage(
									idJuego, EnumSalidaCarcel.PAGAR);
							ConnectionController.getInstance().send(
									msgPayToLeaveJail);
						}
					}

				} catch (Exception ex) {
					GestorLogs.registrarError(ex);
					showException(ex, "irALaCarcel...", null);
				}

			}
		});
	}

	/**
	 * Muesta un mensaje informando que se repite el turno por haber sacado
	 * dados dobles.
	 * 
	 */
	public void showDadosDobles() {
		try {
			mostrarTirarDados(true);
			showMessageBox(AlertType.INFORMATION, "Turno de juego...",
					"Dados dobles.", "Es tu turno nuevamente.",
					"/images/logos/dice.png");
		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
			showException(ex, "showDadosDobles...");
		}
	}

	/**
	 * Método para mostrar un mensaje en la pantalla que requiere de una
	 * respuesta Numérica entre {@code min} y {@code max}. <code>
	 * -----------------------------------------------------
	 * |   title                                           | 
	 * |===================================================|
	 * | headerText                                        |
	 * |---------------------------------------------------|
	 * | desc line1......................................  |
	 * | desc line2......................................  |
	 * | message                                           |
	 * |  ----------                                       |
	 * |  |SPINNER |  msgSpinner: xxx €                    |
	 * |  ----------                                       |
	 * -----------------------------------------------------
	 * </code>
	 * 
	 * @param title
	 *            El título del mensaje
	 * @param headerText
	 *            El encabezado del mensaje
	 * @param desc
	 *            Una descripción con una explicación
	 * @param message
	 *            El mensaje a mostrar
	 * @param msgSpinner
	 *            Un texto que va a la derecha del spinner
	 * @param minValue
	 *            El valor mínimo aceptado por el spinner
	 * @param maxValue
	 *            El valor máximo aceptado por el spinner
	 * @param defaultValue
	 *            El valor por defecto en el spinner
	 * @param precioCasa
	 *            El precio de cada casa que se quiere vender/comprar
	 * @return El valor seleccionado por el usuario o -1 si presionó "Cancelar"
	 */
	public int showConstruccionesMsgBox(String title, String headerText,
			String desc, String message, String msgSpinner, int minValue,
			int maxValue, int defaultValue, int precioCasa) {

		Alert alert;
		Optional<ButtonType> result = null;

		Spinner<Integer> spinner = new Spinner<Integer>(minValue, maxValue,
				defaultValue);
		Label lblText = new Label(message);
		Label lblDesc = new Label(desc);
		Label lblCost = new Label(StringUtils.formatearAMoneda(defaultValue
				* precioCasa));

		spinner.setEditable(false); // solo se puede editar usando los botones
		spinner.setVisible(true);
		spinner.setMinWidth(80);
		spinner.setPrefWidth(80);

		spinner.getValueFactory()
				.valueProperty()
				.addListener(
						(obs, oldValue, newValue) -> lblCost
								.setText(StringUtils.formatearAMoneda(newValue
										* precioCasa)));

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));

		grid.add(lblDesc, 0, 0);
		grid.add(lblText, 0, 2);
		grid.add(new HBox(new Label("   "), spinner, new Label("  "
				+ msgSpinner + ": "), lblCost), 0, 3);

		try {
			ButtonType buttonOk;
			ButtonType buttonCancel;

			buttonOk = new ButtonType("Aceptar", ButtonData.OK_DONE);
			buttonCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

			alert = getAlert(
					AlertType.CONFIRMATION,
					title,
					headerText,
					message,
					new ArrayList<ButtonType>(Arrays.asList(buttonOk,
							buttonCancel)));

			alert.getDialogPane().setContent(grid);

			result = alert.showAndWait();
		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
		}

		if (result.get().getButtonData() == ButtonData.CANCEL_CLOSE)
			return -1;

		return spinner.getValue();

	}

	/**
	 * Método para mostrar un mensaje en la pantalla que requiere de una
	 * respuesta Numérica entre {@code 0} y {@code Integer.MAX_VALUE}. <code>
	 * -----------------------------------------------------
	 * |   title                                           | 
	 * |===================================================|
	 * | headerText                                        |
	 * |---------------------------------------------------|
	 * | desc line1......................................  |
	 * | desc line2......................................  |
	 * | message                                           |
	 * |  ----------                                       |
	 * |  |SPINNER |  €                                    |
	 * |  ----------                                       |
	 * -----------------------------------------------------
	 * </code>
	 * 
	 * @param title
	 *            El título del mensaje
	 * @param headerText
	 *            El encabezado del mensaje
	 * @param desc
	 *            Una descripción con una explicación
	 * @param message
	 *            El mensaje a mostrar
	 * @param valorPropiedad
	 *            El valor de de la propiedad que se oferta
	 * @return El valor seleccionado por el usuario o -1 si presionó "Cancelar"
	 */
	public int showPropiedadesMsgBox(String title, String headerText,
			String desc, String message, String msgSpinner, int valorPropiedad) {

		Alert alert;
		Optional<ButtonType> result = null;

		// normal setup of spinner
		// Spinner<Integer> spinner = new Spinner<Integer>(0, Integer.MAX_VALUE,
		// valorPropiedad);
		SpinnerValueFactory<Integer> factory = new IntegerSpinnerValueFactory(
				0, Integer.MAX_VALUE, valorPropiedad);
		Spinner<Integer> spinner = new Spinner<Integer>(factory);
		spinner.setEditable(true);

		/* **************************
		 * Creamos un Formatter y se lo asignamos al spinner para que se
		 * actualice el valor del monto ofrecido cuando lo escribimos a mano
		 */
		// hook in a formatter with the same properties as the factory
		TextFormatter<Integer> formatter = new TextFormatter<Integer>(
				factory.getConverter(), factory.getValue());
		spinner.getEditor().setTextFormatter(formatter);
		// bidi-bind the values
		factory.valueProperty().bindBidirectional(formatter.valueProperty());

		Label lblText = new Label(message);
		Label lblDesc = new Label(desc);

		spinner.setEditable(true);
		spinner.setVisible(true);
		spinner.setMinWidth(80);
		spinner.setPrefWidth(80);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));

		grid.add(lblDesc, 0, 0);
		grid.add(lblText, 0, 1);
		grid.add(new HBox(spinner, new Label(" € ")), 0, 2);

		try {
			ButtonType buttonOk;
			ButtonType buttonCancel;

			buttonOk = new ButtonType("Aceptar", ButtonData.OK_DONE);
			buttonCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

			alert = getAlert(
					AlertType.CONFIRMATION,
					title,
					headerText,
					message,
					new ArrayList<ButtonType>(Arrays.asList(buttonOk,
							buttonCancel)));

			alert.getDialogPane().setContent(grid);

			result = alert.showAndWait();
		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
		}

		if (result.get().getButtonData() == ButtonData.CANCEL_CLOSE)
			return -1;

		return spinner.getValue();

	}

	public void showWinMessage() {
		this.showMessageBox(
				AlertType.INFORMATION,
				"Ganaste",
				"¡¡¡FELICITACIONES!!!",
				"Ganaste el juego porque todos los jugadores se declararon en bancarrota",
				"/images/logos/winner.jpg");

		this.cerrar(true);
	}

	public void actualizarSubasta(SubastaStatus statusSubasta) throws Exception {

		if (statusSubasta.estado == EnumEstadoSubasta.INICIADA) {
			Platform.runLater(new Runnable() {
				private Stage subastaStage = null;

				@Override
				public void run() {

					String fxml;
					SubastaController controller;

					try {

						if (!getMyPlayer().getNombre().equals(
								statusSubasta.jugadorActual.getNombre())) {
							fxml = ConstantesFXML.FXML_SUBASTA;
							subastaStage = new Stage();
							controller = (SubastaController) FXUtils
									.cargarStage(subastaStage, fxml,
											"Monopoly - Subasta", false, false,
											Modality.APPLICATION_MODAL,
											StageStyle.DECORATED);
							controller
									.setTarjetaSubasta(statusSubasta.propiedadSubastada);
							controller.setCurrentStage(subastaStage);
							controller.setJugador(getMyPlayer());
							controller.setIdJuego(juego.getUniqueID());
							controller.setEstadoSubasta(statusSubasta.estado);
							controller.cargarImagenes();
							controller.bloquearBotones(true);
							controller
									.agregarHistoriaDeSubasta(statusSubasta.historyList);
							subastaStage.show();

							if (VentaPropiedadController.getInstance()
									.getCurrentStage() != null)
								VentaPropiedadController.getInstance()
										.getCurrentStage().close();
						}
					} catch (Exception ex) {
						GestorLogs.registrarException(ex);
					}
				}
			});

		} else {
			if (statusSubasta.estado == EnumEstadoSubasta.FINALIZADA) {

				FutureTask<Void> taskAddHistory = null;
				taskAddHistory = new FutureTask<Void>(new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						Alert alert = null;
						if (SubastaController.getInstance().getCurrentStage() != null)
							SubastaController.getInstance().getCurrentStage()
									.close();
						alert = getAlert(
								AlertType.INFORMATION,
								"Subasta Finalizada",
								String.format("Subastar %s", SubastaController
										.getInstance().getTarjetaSubasta()
										.getNombre()),
								statusSubasta.getMensaje(), null);

						if (statusSubasta.jugadorActual.equals(getMyPlayer())) {
							alert.setContentText(alert.getContentText()
									+ ". Finalizó su turno.");
							alert.showAndWait();
							finalizarTurno();
						} else
							alert.show();

						return null;
					}
				});
				Platform.runLater(taskAddHistory);
				taskAddHistory.get();
			} else {
				SubastaController.getInstance()
						.actualizarSubasta(statusSubasta);
			}
		}
	}


	public void decidirSubasta(String mensaje, int monto,
			TarjetaPropiedad propiedad, Jugador jugadorInicial)
			throws Exception {

		FutureTask<Void> taskMessage = null;

		taskMessage = new FutureTask<Void>(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				AuctionDecideMessage msgDecide = null;
				boolean bAceptaSubasta;
				bAceptaSubasta = showYesNoMsgBox("Subasta - Monopoly",
						"Subasta de la propiedad " + propiedad.getNombre(),
						mensaje);
				msgDecide = new AuctionDecideMessage(juego.getUniqueID(),
						monto, propiedad, bAceptaSubasta, jugadorInicial);
				ConnectionController.getInstance().send(msgDecide);

				return null;

			}
		});
		Platform.runLater(taskMessage);
		taskMessage.get();
	}
	
	/**
	 * 
	 * 
	 * @param mensaje
	 */
	public void finalizarSubasta(final String mensaje) throws Exception {
		FutureTask<Void> taskMessage = null;

		taskMessage = new FutureTask<Void>(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				Alert alert;
				alert = getAlert(
						AlertType.INFORMATION,
						"Subasta Finalizada",
						String.format("Subastar %s", SubastaController
								.getInstance().getTarjetaSubasta().getNombre()),
						mensaje, null);
				if (SubastaController.getInstance().getCurrentStage() != null)
					SubastaController.getInstance().getCurrentStage().close();
				VentaPropiedadController.getInstance().getCurrentStage()
						.close();

				alert.showAndWait();

				finalizarTurno();

				return null;
			}
		});
		Platform.runLater(taskMessage);
		taskMessage.get();
	}

	/**
	 * Muestra un mensaje que informa si la propiedad se hipotecó correctamente
	 * o hubo algún error
	 * 
	 * @param propiedad
	 *            La propiedad que se hipoteca.
	 */
	public void finishMortgage(TarjetaPropiedad propiedad) throws Exception {

		if (propiedad != null && propiedad.isHipotecada()) {
			TableroController.getInstance().showMessageBox(
					AlertType.INFORMATION,
					"Información",
					"Propiedad hipotecada",
					String.format("La propiedad %s se hipotecó por %s",
							propiedad.getNombre(), StringUtils
									.formatearAMoneda(propiedad
											.getValorHipotecario())), null);
		} else {
			TableroController.getInstance().showMessageBox(
					AlertType.ERROR,
					"Error",
					"Error de hipoteca",
					String.format("La propiedad %s no se pudo hipotecar",
							propiedad.getNombre()), null);
		}
	}

	/**
	 * Muestra un mensaje que informa si la propiedad se deshipotecó
	 * correctamente o hubo algún error
	 * 
	 * @param propiedad
	 *            La propiedad que se hipoteca.
	 */
	public void finishDemortgage(TarjetaPropiedad propiedad) throws Exception {

		if (propiedad != null && !propiedad.isHipotecada()) {
			TableroController.getInstance().showMessageBox(
					AlertType.INFORMATION,
					"Información",
					"Propiedad deshipotecada",
					String.format("La propiedad %s se deshipotecó por %s",
							propiedad.getNombre(), StringUtils
									.formatearAMoneda(propiedad
											.getValorDeshipotecario())), null);
		} else {
			TableroController.getInstance().showMessageBox(
					AlertType.ERROR,
					"Error",
					"Error de deshipoteca",
					String.format("La propiedad %s no se pudo deshipotecar",
							propiedad.getNombre()), null);
		}
	}

	/**
	 * Muestra un mensaje que informa si se pudieron construir los edificios
	 * 
	 * @param calle
	 *            La calle del color donde se construyó.
	 * @param monto
	 *            La propiedad que se hipoteca.
	 */
	public void finishBuild(TarjetaCalle calle, int monto) throws Exception {

		if (monto > 0) {
			TableroController
					.getInstance()
					.showMessageBox(
							AlertType.INFORMATION,
							"Información",
							"Calle construida",
							String.format(
									"Se construyó sobre el color %s con un costo de %s",
									calle.getColor(),
									StringUtils.formatearAMoneda(monto)), null);
		} else {
			TableroController.getInstance().showMessageBox(
					AlertType.ERROR,
					"Error",
					"Error en la construcción",
					String.format("No se pudo construir sobre el color %s",
							calle.getColor()), null);
		}
	}

	/**
	 * Muestra un mensaje que informa si se pudieron construir los edificios
	 * 
	 * @param calle
	 *            La calle del color donde se construyó.
	 * @param monto
	 *            La propiedad que se hipoteca.
	 */
	public void finishUnbuild(TarjetaCalle calle, int monto) throws Exception {

		if (monto > 0) {
			TableroController
					.getInstance()
					.showMessageBox(
							AlertType.INFORMATION,
							"Información",
							"Edificios vendidos",
							String.format(
									"Se vendieron edificios en el color %s con un beneficio de %s",
									calle.getColor(),
									StringUtils.formatearAMoneda(monto)), null);
		} else {
			TableroController.getInstance().showMessageBox(
					AlertType.ERROR,
					"Error",
					"Error en la venta",
					String.format(
							"No se pudieron vender edificios del color %s",
							calle.getColor()), null);
		}
	}

	/**
	 * Muestra un MessageBox informando que un jugador se fué del juego debido a
	 * que cayó en bancarrota.
	 * 
	 * @param mensaje
	 *            El mensaje que se va a mostrar
	 */
	public void informarBancarrota(String mensaje) {
		showMessageBox(AlertType.INFORMATION, "Bancarrota",
				"Un jugador se retiró", mensaje, "/images/logos/bancarrota.png");
	}

	/**
	 * Muestra un mensaje para consultarle al jugador si desea venderle una
	 * propiedad a otro jugador
	 * 
	 * @param propiedad
	 *            La propiedad por la que ofertó
	 * @param oferente
	 *            El jugador que realizó la oferta
	 * @param monto
	 *            El monto que ofrece
	 */
	public void ofrecerPorPropiedad(TarjetaPropiedad propiedad,
			Jugador oferente, int monto) throws Exception {

		Platform.runLater(new Runnable() {
			String message;
			boolean respuesta;

			@Override
			public void run() {
				message = String
						.format("El jugador %s realizó una oferta de %s por la propiedad %s.\n"
								+ "¿Desea aceptar la oferta y venderle la propiedad?",
								oferente.getNombre(),
								StringUtils.formatearAMoneda(monto),
								propiedad.getNombre());

				respuesta = showYesNoMsgBox("Oferta recibida",
						"Ha recibido una oferta", message);

				BidResultMessage bidMessage = new BidResultMessage(
						(JugadorHumano) oferente, juego.getUniqueID(),
						propiedad, monto, respuesta);
				ConnectionController.getInstance().send(bidMessage);
			}
		});

	}

	/**
	 * Muestra un mensaje que le informa al jugador si la oferta fue aceptada o
	 * rechazada.
	 * 
	 * @param propiedad
	 *            La propiedad por la que ofertó
	 * @param monto
	 *            El monto que ofrece
	 * @param resultado
	 *            {@code true} si la oferta fue aceptada
	 */
	public void finalizarOfertaPropiedad(TarjetaPropiedad propiedad, int monto,
			boolean resultado) throws Exception {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				SplashController.getInstance().getCurrentStage().close();
			}
		});

		String titulo;
		String mensaje;

		if (resultado) {
			titulo = "Oferta aceptada";
			mensaje = String.format("Adquiriste la propiedad %s por %s",
					propiedad.getNombre(), StringUtils.formatearAMoneda(monto));
		} else {
			titulo = "Oferta rechazada";
			mensaje = String.format("El jugador %s no aceptó tu oferta de %s\n"
					+ "por la propiedad %s",
					propiedad.getJugador().getNombre(),
					StringUtils.formatearAMoneda(monto), propiedad.getNombre());
		}

		showMessageBox(AlertType.INFORMATION, "Comprar propiedad", titulo,
				mensaje, null);
	}
	

	/**
	 * Cuando no posee dinero sufiente para realizar una acción registra la
	 * deuda hasta completar la acción.
	 * 
	 * @param pMonto
	 *            Monto de la deuda.
	 * 
	 * @throws Exception
	 */
	private void registrarDeuda(int pMonto) throws Exception {
		// bloquearAcciones(false);
		mostrarTirarDados(false);
		mostrarFinalizarTurno(true);
		deudaPendiente = new Deuda(pMonto);

	}


	/**
	 * Finaliza el turno actual para continuar el juego.
	 * 
	 * @throws Exception
	 */
	private void finalizarTurno() throws Exception {

		CompleteTurnMessage msg = new CompleteTurnMessage(getJuego()
				.getUniqueID(), null, null);
		ConnectionController.getInstance().send(msg);
	}

	/**
	 * Método para mostrar un mensaje en la pantalla. Implementa FutureTask.
	 * 
	 * @param type
	 *            El tipo de mensaje. Es del tipo
	 *            {@link javafx.scene.control.Alert.AlertType}
	 * @param title
	 *            El título del mensaje
	 * @param headerText
	 *            El encabezado del mensaje
	 * @param message
	 *            El mensaje a mostrar
	 */
	public void showMessageBox(final AlertType type, final String title,
			final String headerText, final String message,
			final String urlGraphic) {
		FutureTask<Void> taskMessage = null;
		try {

			taskMessage = new FutureTask<Void>(new Callable<Void>() {

				@Override
				public Void call() throws Exception {

					final Alert alert = getAlert(type, title, headerText,
							message, null);

					// Setteo la Imagen si es necesario
					if (!StringUtils.IsNullOrEmpty(urlGraphic)) {
						Image img = new Image(this.getClass()
								.getResource(urlGraphic).toString(), 48, 48,
								true, true);
						alert.setGraphic(new ImageView(img));
					}

					alert.showAndWait();
					return null;

				}
			});
			Platform.runLater(taskMessage);
			taskMessage.get();

		} catch (Exception ex) {
			GestorLogs.registrarException(ex);
		}
	}

	/**
	 * Método para mostrar un mensaje en la pantalla dentro de una interfaz
	 * JavaFx (No genera el FutureTask).
	 * 
	 * @param type
	 *            El tipo de mensaje. Es del tipo
	 *            {@link javafx.scene.control.Alert.AlertType}
	 * @param title
	 *            El título del mensaje
	 * @param headerText
	 *            El encabezado del mensaje
	 * @param message
	 *            El mensaje a mostrar
	 */
	public void showNoFutureMessageBox(AlertType type, String title,
			String headerText, String message) {
		Alert alert = getAlert(type, title, headerText, message, null);
		alert.showAndWait();
	}

	/**
	 * Método para mostrar un mensaje en la pantalla que requiere de una
	 * respuesta SI/NO
	 * 
	 * @param title
	 *            El título del mensaje
	 * @param headerText
	 *            El encabezado del mensaje
	 * @param message
	 *            El mensaje a mostrar
	 * @return <strong>{@code true}</strong> si el usuario respondió
	 *         <strong>SI</strong>. <strong>{@code false}</strong> si respondió
	 *         <strong>NO</strong>.
	 */
	public boolean showYesNoMsgBox(String title, String headerText,
			String message) {

		Alert alert;
		Optional<ButtonType> result = null;

		try {
			ButtonType buttonYes;
			ButtonType buttonNo;

			buttonYes = new ButtonType("Si", ButtonData.YES);
			buttonNo = new ButtonType("No", ButtonData.NO);

			alert = getAlert(
					AlertType.CONFIRMATION,
					title,
					headerText,
					message,
					new ArrayList<ButtonType>(Arrays
							.asList(buttonYes, buttonNo)));

			result = alert.showAndWait();
		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
		}
		return (result.get().getButtonData() == ButtonData.YES);
	}

	/**
	 * Método para mostrar un mensaje en la pantalla que requiere de una
	 * respuesta SI/NO para usar cuando no se ejecuta desde una ventana JavaFX
	 * (Mete el mensaje dentro de un FutureTask).
	 * 
	 * @param title
	 *            El título del mensaje
	 * @param headerText
	 *            El encabezado del mensaje
	 * @param message
	 *            El mensaje a mostrar
	 * @return <strong>{@code true}</strong> si el usuario respondió
	 *         <strong>SI</strong>. <strong>{@code false}</strong> si respondió
	 *         <strong>NO</strong>.
	 */
	public boolean showFutureYesNoMsgBox(String title, String headerText,
			String message) {
		Boolean result = new Boolean(false);
		FutureTask<Boolean> taskMessage = null;

		try {

			taskMessage = new FutureTask<Boolean>(new Callable<Boolean>() {

				Alert alert;
				Optional<ButtonType> result = null;

				@Override
				public Boolean call() throws Exception {

					ButtonType buttonYes;
					ButtonType buttonNo;

					buttonYes = new ButtonType("Si", ButtonData.YES);
					buttonNo = new ButtonType("No", ButtonData.NO);

					alert = getAlert(
							AlertType.CONFIRMATION,
							title,
							headerText,
							message,
							new ArrayList<ButtonType>(Arrays.asList(buttonYes,
									buttonNo)));

					result = alert.showAndWait();
					return new Boolean(
							result.get().getButtonData() == ButtonData.YES);
				}
			});

			Platform.runLater(taskMessage);
			result = taskMessage.get();
		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
		}
		return (result.booleanValue());
	}

	/**
	 * Muestra un mensaje de error.
	 * 
	 * @param exception
	 *            La {@code Exception} con el error.
	 */
	public void showException(Exception exception, String claseMensaje) {
		String msgGuardado;

		try {

			msgGuardado = "Se ha producido un error desconocido";

			if (exception != null)
				msgGuardado = exception.getMessage();

			showMessageBox(AlertType.ERROR, "Error", claseMensaje, msgGuardado,
					null);
		} catch (Exception ex) {
			GestorLogs.registrarException(ex);
		}

	}

	/**
	 * Muestra un mensaje de error.
	 * 
	 * @param exception
	 *            La {@code Exception} con el error.
	 */
	private void showException(Exception exception, String title, String header) {
		String msgGuardado;

		try {

			msgGuardado = "Se ha producido un error desconocido";

			if (exception != null)
				msgGuardado = exception.getMessage();

			showNoFutureMessageBox(AlertType.ERROR, title, header, msgGuardado);
		} catch (Exception ex) {
			GestorLogs.registrarException(ex);
		}

	}

	private Alert getAlert(AlertType type, String title, String headerText,
			String message, List<ButtonType> botones) {

		Alert alert = null;
		ButtonType buttonAceptar = null;
		DialogPane dialogPane;
		Image img;
		Stage stage;

		try {
			alert = new Alert(type);

			alert.setTitle(title);
			alert.setHeaderText(headerText);
			alert.setContentText(message);
			if (botones != null && botones.size() > 0) {
				alert.getButtonTypes().setAll(botones);
			} else {
				buttonAceptar = new ButtonType("Aceptar", ButtonData.OK_DONE);
				alert.getButtonTypes().setAll(buttonAceptar);
			}

			dialogPane = alert.getDialogPane();

			dialogPane.getStylesheets().add(
					getClass().getResource("/css/Dialog.css").toExternalForm());
			dialogPane.getStyleClass().add("dialog");

			/*
			 * workaround para el problema del tamaño de labels:
			 * http://stackoverflow.com/a/33905734
			 */
			alert.getDialogPane()
					.getChildren()
					.stream()
					.filter(node -> node instanceof Label)
					.forEach(
							node -> ((Label) node)
									.setMinHeight(Region.USE_PREF_SIZE));

			// Seteo el icono de la cabecera.
			stage = (Stage) alert.getDialogPane().getScene().getWindow();
			img = new Image(
					TableroController.class
							.getResourceAsStream("/images/logos/monopoly3.png"));
			stage.getIcons().add(img);

			if (type == AlertType.INFORMATION) {
				// Set the icon (must be included in the project).
				img = new Image(
						this.getClass()
								.getResource("/images/iconos/monopoly5.png")
								.toString(), 48, 48, true, true);
				alert.setGraphic(new ImageView(img));
			}

		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
		}
		return alert;
	}

	// =======================================================================//
	// ======== Clases para las acciones posibles en las propiedades =========//
	// =======================================================================//

	/**
	 * Clase para la acción de "Hipotecar" del {@code ContextMenu}
	 * 
	 * @author Bostico Alejandro
	 * @author Moreno Pablo
	 */
	private class EventHipotecar implements EventHandler<ActionEvent> {

		final private TarjetaPropiedad propiedad;

		public EventHipotecar(TarjetaPropiedad propiedad) {
			super();
			this.propiedad = propiedad;
		}

		@Override
		public void handle(ActionEvent event) {
			boolean answer = showYesNoMsgBox("Hipotecar propiedad",
					"Confirmar hipoteca", String.format(
							"¿Desea hipotecar la propiedad %s por %s?",
							propiedad.getNombre(), StringUtils
									.formatearAMoneda(propiedad
											.getValorHipotecario())));

			if (!answer)
				return;

			int senderID = ConnectionController.getInstance().getIdPlayer();
			String idJuego = getJuego().getUniqueID();

			MortgageMessage msg = new MortgageMessage(senderID, idJuego,
					propiedad);
			ConnectionController.getInstance().send(msg);

		}

	}

	/**
	 * Clase para la acción de "Deshipotecar" del {@code ContextMenu}
	 * 
	 * @author Bostico Alejandro
	 * @author Moreno Pablo
	 */
	private class EventDeshipotecar implements EventHandler<ActionEvent> {

		final private TarjetaPropiedad propiedad;

		public EventDeshipotecar(TarjetaPropiedad propiedad) {
			super();
			this.propiedad = propiedad;
		}

		@Override
		public void handle(ActionEvent event) {
			boolean answer = showYesNoMsgBox("Deshipotecar propiedad",
					"Confirmar deshipoteca", String.format(
							"¿Desea deshipotecar la propiedad %s por %s?",
							propiedad.getNombre(), StringUtils
									.formatearAMoneda(propiedad
											.getValorDeshipotecario())));

			if (!answer)
				return;

			int senderID = ConnectionController.getInstance().getIdPlayer();
			String idJuego = getJuego().getUniqueID();

			DemortgageMessage msg = new DemortgageMessage(senderID, idJuego,
					propiedad);
			ConnectionController.getInstance().send(msg);

		}

	}

	/**
	 * Clase para la acción de "Construir" del {@code ContextMenu}
	 * 
	 * @author Bostico Alejandro
	 * @author Moreno Pablo
	 */
	private class EventConstruir implements EventHandler<ActionEvent> {

		final private TarjetaPropiedad propiedad;

		public EventConstruir(TarjetaPropiedad propiedad) {
			super();
			this.propiedad = propiedad;
		}

		@Override
		public void handle(ActionEvent event) {
			int maxHouse = 15;
			TarjetaCalle tarjeta;

			if (propiedad.isPropiedadCalle()) {
				tarjeta = (TarjetaCalle) propiedad;
				maxHouse = tarjeta.casasParaCompletar();
			} else {
				showMessageBox(AlertType.ERROR, "Error",
						"No se puede construir",
						"Solo se pueden construír edificios sobre calles.",
						null);
				return;
			}

			String descripcion = "Seleccione la cantidad de casas que desea construír.\n"
					+ "Se construirán automáticamente y en orden de forma tal que ninguna\n"
					+ "calle tenga más de 1 casa de diferencia con otra. Si se seleccionan\n"
					+ "5 construcciones, se construirá un hotel en lugar de casas.\n\n";
			int answer = showConstruccionesMsgBox("Construir edificios",
					"Ingresar cantidad", descripcion,
					"¿Cuantos edificios desea construír en total?",
					"Costo total", 1, maxHouse, maxHouse,
					tarjeta.getPrecioCadaCasa());

			// Si "answer = -1" es porque presionó "Cancelar"
			// En ese caso salimos sin hacer nada...
			if (answer < 1)
				return;

			int senderID = ConnectionController.getInstance().getIdPlayer();
			String idJuego = getJuego().getUniqueID();

			BuildMessage msg = new BuildMessage(senderID, idJuego, tarjeta,
					answer);
			ConnectionController.getInstance().send(msg);

		}

	}

	/**
	 * Clase para la acción de "Vender construcciones" del {@code ContextMenu}
	 * 
	 * @author Bostico Alejandro
	 * @author Moreno Pablo
	 */
	private class EventDesconstruir implements EventHandler<ActionEvent> {

		final private TarjetaPropiedad propiedad;

		public EventDesconstruir(TarjetaPropiedad propiedad) {
			super();
			this.propiedad = propiedad;
		}

		@Override
		public void handle(ActionEvent event) {
			int maxHouse = 15;
			TarjetaCalle tarjeta;

			if (propiedad.isPropiedadCalle()) {
				tarjeta = (TarjetaCalle) propiedad;
				maxHouse = (tarjeta.getEnumColor().getCantMonopoly() * 5)
						- tarjeta.casasParaCompletar();
			} else {
				showMessageBox(AlertType.ERROR, "Error",
						"No se pueden vender construcciones",
						"Solo se pueden vender construcciones de calles.", null);
				return;
			}

			String descripcion = "Seleccione la cantidad de casas que desea vender.\n"
					+ "Se venderan automáticamente y en orden de forma tal que ninguna\n"
					+ "calle tenga más de 1 casa de diferencia con otra.\n\n";
			int answer = showConstruccionesMsgBox("Vender edificios",
					"Ingresar cantidad", descripcion,
					"¿Cuantos edificios desea vender?", "Beneficio total", 1,
					maxHouse, maxHouse, tarjeta.getPrecioVentaCadaCasa());

			// Si "answer = -1" es porque presionó "Cancelar"
			// En ese caso salimos sin hacer nada...
			if (answer < 1)
				return;

			int senderID = ConnectionController.getInstance().getIdPlayer();
			String idJuego = getJuego().getUniqueID();

			UnbuildMessage msg = new UnbuildMessage(senderID, idJuego, tarjeta,
					answer);
			ConnectionController.getInstance().send(msg);

		}

	}

	/**
	 * Clase para la acción de "Vender construcciones" del {@code ContextMenu}
	 * 
	 * @author Bostico Alejandro
	 * @author Moreno Pablo
	 */
	private class EventComprarPropiedad implements EventHandler<ActionEvent> {

		final private TarjetaPropiedad propiedad;

		public EventComprarPropiedad(TarjetaPropiedad propiedad) {
			super();
			this.propiedad = propiedad;
		}

		@Override
		public void handle(ActionEvent event) {

			StringBuffer descripcion = new StringBuffer();
			descripcion
					.append("Puede realizar una oferta monetaria por la propiedad.\n");
			descripcion
					.append("En el caso de que el dueño la acepte, le deberá pagar el\n");
			descripcion.append("monto ofertado y la propiedad será suya.\n\n");

			descripcion.append("Nombre de la propiedad: "
					+ propiedad.getNombre() + "\n");

			descripcion.append("Actual dueño: "
					+ propiedad.getJugador().getNombre() + "\n");

			descripcion.append("Valor de la propiedad: "
					+ StringUtils.formatearAMoneda(propiedad
							.getValorPropiedad()) + "\n");

			descripcion.append("Valor hipotecario: "
					+ StringUtils.formatearAMoneda(propiedad
							.getValorHipotecario()) + "\n");

			if (propiedad.isHipotecada()) {
				descripcion
						.append("La propiedad está hipotecada y debés pagar ");
				descripcion.append(StringUtils.formatearAMoneda(propiedad
						.getValorDeshipotecario()));
				descripcion.append("\npara deshipotecarla luego de comprarla.");
			} else {
				descripcion.append("La propiedad NO está hipotecada.");
			}

			int answer = showPropiedadesMsgBox("Hacer una oferta",
					"Ingresar cantidad", descripcion.toString(),
					"¿Cuando dinero desea ofertar por la propiedad?",
					"Beneficio total", propiedad.getValorPropiedad());

			// Si "answer = -1" es porque presionó "Cancelar"
			// En ese caso salimos sin hacer nada...
			if (answer < 1)
				return;

			String idJuego = getJuego().getUniqueID();

			BidForPropertyMessage msg = new BidForPropertyMessage(
					TableroController.this.getMyPlayer(), idJuego, propiedad,
					answer);
			ConnectionController.getInstance().send(msg);

			esperarRespuestaOferta();
		}
	}

	/**
	 * Clase para hacer salto de linea cuando se presiona enter.
	 * 
	 * 
	 * @author Bostico Alejandro
	 * @author Moreno Pablo
	 *
	 */
	private class ChatEventHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent keyEvent) {
			if (keyEvent.getCode() == KeyCode.ENTER) {
				if (txtMessageChat.getText().trim().length() == 0) {
					keyEvent.consume();
				} else {
					if (keyEvent.isAltDown() || keyEvent.isControlDown()
							|| keyEvent.isShiftDown()) {
						txtMessageChat.appendText("\n");
					} else {
						sendChatMessage();
						keyEvent.consume();
					}
				}
			}
		}

	}

	private class HistoryCallback implements
			Callback<ListView<History>, javafx.scene.control.ListCell<History>> {
		@Override
		public ListCell<History> call(ListView<History> listView) {
			return new HistoryListCell();
		}
	}

	private class HistoryListCell extends ListCell<History> {

		@Override
		protected void updateItem(History item, boolean bln) {
			super.updateItem(item, bln);
			if (item != null) {
				Text txtHistory = new Text(item.toString());

				Color fillColor = determinarColor(item.getUsuario());

				txtHistory.setFill(fillColor);
				setGraphic(txtHistory);
			}
		}
	}

	// =======================================================================//
	// =========== Métodos para dibujar componentes en la pantalla ===========//
	// =======================================================================//

	/**
	 * Actualiza la gráfica en el tablero en base al estado del juego.
	 * 
	 * @throws Exception
	 */
	private void actualizarGraficoEnElTablero() throws Exception {
		displayFichas(estadoActual.turnos);
		displayCasasYHoteles(estadoActual.tablero.getCasillerosList());
		showAccordionJugadores(estadoActual.turnos, estadoActual.banco);
	}

	/**
	 * Dibuja a los jugadores, mostrando el estado en el juego.
	 * 
	 */
	private void showAccordionJugadores(List<Jugador> turnosList, Banco banco)
			throws Exception {
		tps = new TitledPane[turnosList.size() + 1];
		String title;

		for (int i = 0; i < turnosList.size(); i++) {
			title = turnosList.get(i).getNombre() + " - ";
			title += StringUtils
					.formatearAMoneda(turnosList.get(i).getDinero()) + " - ";
			if (turnosList.get(i).isHumano())
				title += "Jugador Humano";
			else {
				title += "Jugador Virtual ("
						+ ((JugadorVirtual) turnosList.get(i)).getTipoJugador()
								.getNombreTipo() + ")";
			}
			tps[i] = getPaneInfoPlayer(turnosList.get(i), title, banco);
		}
		tps[turnosList.size()] = getPaneInfoBanco(banco, "BANCO");

		final TitledPane[] tpJugadores = tps;
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				accordionPlayers.getPanes().clear();
				accordionPlayers.getPanes().addAll(tpJugadores);
				accordionPlayers.setExpandedPane(tpJugadores[0]);
			}
		});

	}

	/**
	 * Dibuja las fichas en el tablero.
	 * 
	 * @throws Exception
	 */
	private void displayFichas(List<Jugador> turnosList) throws Exception {
		limpiarCasilleros();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				TilePane tpCasilleroSelected = null;
				Casillero casilleroActual;
				try {
					for (Jugador jugadorTurno : turnosList) {
						casilleroActual = jugadorTurno.getCasilleroActual();
						final Image img = new Image(TableroController.class
								.getResourceAsStream(jugadorTurno.getFicha()
										.getPathImgSmall()), 25, 25, true, true);

						tpCasilleroSelected = (TilePane) pTablero
								.lookup("#pCasillero"
										+ String.format("%02d", casilleroActual
												.getNumeroCasillero()));
						if (tpCasilleroSelected != null) {
							tpCasilleroSelected.getChildren().add(
									new ImageView(img));
						} else {
							throw new CondicionInvalidaException(String.format(
									"Casillero inválido: %s", jugadorTurno
											.getCasilleroActual()
											.getNumeroCasillero()));
						}
					}
				} catch (Exception ex) {
					GestorLogs.registrarException(ex);
				}
			}
		});
	}

	/**
	 * Dibuja casas y hoteles en las propiedades.
	 * 
	 * @param casilleros
	 */
	private void displayCasasYHoteles(Casillero[] casilleros) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Image imgCasa;
				TilePane tpCasilleroSelected = null;

				try {
					for (Casillero casillero : casilleros) {
						if (casillero.isCasilleroCalle()
								&& ((CasilleroCalle) casillero).getNroCasas() > 0) {

							tpCasilleroSelected = (TilePane) pTablero
									.lookup("#pCasillero"
											+ String.format("%02d", casillero
													.getNumeroCasillero()));
							if (tpCasilleroSelected != null) {

								switch (((CasilleroCalle) casillero)
										.getNroCasas()) {
								case 1:
									imgCasa = new Image(
											TableroController.class
													.getResourceAsStream("/images/fichas/CasaS01.png"),
											18, 18, false, false);
									tpCasilleroSelected.getChildren().add(
											new ImageView(imgCasa));
									break;
								case 2:
									imgCasa = new Image(
											TableroController.class
													.getResourceAsStream("/images/fichas/CasaS02.png"),
											32, 18, false, false);
									tpCasilleroSelected.getChildren().add(
											new ImageView(imgCasa));
									break;
								case 3:
									imgCasa = new Image(
											TableroController.class
													.getResourceAsStream("/images/fichas/CasaS03.png"),
											40, 18, false, false);
									tpCasilleroSelected.getChildren().add(
											new ImageView(imgCasa));
									break;
								case 4:
									imgCasa = new Image(
											TableroController.class
													.getResourceAsStream("/images/fichas/CasaS04.png"),
											50, 18, false, false);
									tpCasilleroSelected.getChildren().add(
											new ImageView(imgCasa));
									break;
								case 5:
									imgCasa = new Image(
											TableroController.class
													.getResourceAsStream("/images/fichas/CasaS05.png"),
											30, 24, false, false);
									tpCasilleroSelected.getChildren().add(
											new ImageView(imgCasa));
									break;
								default:

									break;
								}

							} else {
								throw new CondicionInvalidaException(String
										.format("Casillero inválido: %s",
												casillero.getNumeroCasillero()));
							}

						}
					}
				} catch (Exception ex) {
					GestorLogs.registrarException(ex);
				}
			}
		});
	}

	/**
	 * Muestra la pantalla para tirar los dados.
	 * 
	 * @param pbMostrar
	 * @throws Exception
	 */
	private void mostrarTirarDados(final boolean pbMostrar) throws Exception {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				btnTirarDados.setVisible(pbMostrar);
			}
		});
	}

	/**
	 * Habilita el botón para finalizar el turno.
	 * 
	 * @param pbMostrar
	 * @throws Exception
	 */

	private void mostrarFinalizarTurno(final boolean pbMostrar)
			throws Exception {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				btnFinalizarTurno.setVisible(pbMostrar);
			}
		});
	}
	
	/**
	 * Actualiza en el tablero el jugador del turno actual.
	 * 
	 */
	private void actualizarTurnoJugador() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				lblTurnoJugador.setText("Turno de "
						+ estadoActual.currentPlayer.getNombre());
			}
		});
	}

	/**
	 * 
	 * Dibuja el TitledPane con la información actual del jugador.
	 * 
	 * @param jugador
	 * @param title
	 * @return
	 */
	private TitledPane getPaneInfoPlayer(Jugador jugador, String title,
			Banco banco) throws Exception {

		AnchorPane root = new AnchorPane();
		VBox vBox = new VBox();
		VBox pImgFicha = new VBox();
		HBox hbPropiedades = new HBox();
		HBox hbExtra = new HBox();
		ScrollPane scroll;

		acoplarAContenedor(vBox);
		root.getStyleClass().add("bg_info_panel");
		root.setPadding(new Insets(10));
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);

		hbPropiedades.setAlignment(Pos.CENTER);
		hbPropiedades.setSpacing(20);

		hbExtra.setAlignment(Pos.CENTER);
		hbExtra.setSpacing(20);

		vBox.getChildren().add(hbPropiedades);
		vBox.getChildren().add(hbExtra);

		root.getChildren().add(vBox);
		scroll = makeScrollable(root);

		acoplarAContenedor(pImgFicha);
		pImgFicha.setAlignment(Pos.CENTER);
		pImgFicha.getStyleClass().add("bg_info_ficha");
		pImgFicha.setPrefSize((double) 60, (double) 60);

		// ===================== HBox de propiedades =====================//

		TarjetaPropiedad propiedad;
		String rutaImagen = "";
		String strStyle = "";
		Boolean bCrearImagen = false;
		String strToolTip = "";
		double hbWidth = 35;
		double hbHeight = 60;
		List<String[]> vTarjetas = new ArrayList<String[]>();

		GridPane gridPane1 = new GridPane();
		GridPane gridPane2 = new GridPane();

		acoplarAContenedor(gridPane1);
		acoplarAContenedor(gridPane2);

		gridPane1.setHgap(5);
		gridPane1.setVgap(10);

		gridPane2.setHgap(5);
		gridPane2.setVgap(10);

		// Tarjetas para el gridpane1
		vTarjetas.add(new String[] { "tarjeta02", "0", "0" });
		vTarjetas.add(new String[] { "tarjeta04", "1", "0" });
		vTarjetas.add(new String[] { "tarjeta07", "0", "1", });
		vTarjetas.add(new String[] { "tarjeta09", "1", "1", });
		vTarjetas.add(new String[] { "tarjeta10", "2", "1", });
		vTarjetas.add(new String[] { "tarjeta12", "0", "2", });
		vTarjetas.add(new String[] { "tarjeta14", "1", "2", });
		vTarjetas.add(new String[] { "tarjeta15", "2", "2", });
		vTarjetas.add(new String[] { "tarjeta17", "0", "3", });
		vTarjetas.add(new String[] { "tarjeta19", "1", "3", });
		vTarjetas.add(new String[] { "tarjeta20", "2", "3", });
		vTarjetas.add(new String[] { "tarjeta22", "0", "4", });
		vTarjetas.add(new String[] { "tarjeta24", "1", "4", });
		vTarjetas.add(new String[] { "tarjeta25", "2", "4", });

		for (String[] vTarjeta : vTarjetas) {
			propiedad = banco.getTarjetaPropiedad(vTarjeta[0]);
			if (propiedad != null) {
				if (propiedad.isPropiedadCalle()) {
					strStyle = ((TarjetaCalle) (propiedad)).getColor();
				} else {
					if (propiedad.isPropiedadCompania())
						strStyle = "blanco";
					else
						strStyle = "negro";
				}

				bCrearImagen = false;
				if (jugador.getTarjPropiedadList().contains(propiedad)) {
					bCrearImagen = true;
					if (!propiedad.isHipotecada())
						rutaImagen = propiedad.getPathImagenFrente();
					else
						rutaImagen = propiedad.getPathImagenDorso();
					strToolTip = showToolTipsPropiedad(propiedad);
				}
				final HBox hbPropiedad = crearHBoxTarjetaPropiedad(strStyle,
						bCrearImagen, rutaImagen, hbWidth, hbHeight,
						strToolTip, propiedad);

				gridPane1.add(hbPropiedad, Integer.parseInt(vTarjeta[1]),
						Integer.parseInt(vTarjeta[2]));
			}
		}

		// Tarjetas para el gridpane2
		vTarjetas = new ArrayList<String[]>();
		vTarjetas.add(new String[] { "tarjeta27", "0", "0", });
		vTarjetas.add(new String[] { "tarjeta28", "1", "0", });
		vTarjetas.add(new String[] { "tarjeta30", "2", "0", });
		vTarjetas.add(new String[] { "tarjeta32", "0", "1", });
		vTarjetas.add(new String[] { "tarjeta33", "1", "1", });
		vTarjetas.add(new String[] { "tarjeta35", "2", "1", });
		vTarjetas.add(new String[] { "tarjeta38", "0", "2", });
		vTarjetas.add(new String[] { "tarjeta40", "1", "2", });
		vTarjetas.add(new String[] { "tarjeta13", "0", "3", });
		vTarjetas.add(new String[] { "tarjeta29", "1", "3", });
		vTarjetas.add(new String[] { "tarjeta06", "0", "4", });
		vTarjetas.add(new String[] { "tarjeta16", "1", "4", });
		vTarjetas.add(new String[] { "tarjeta26", "2", "4", });
		vTarjetas.add(new String[] { "tarjeta36", "3", "4", });

		for (String[] vTarjeta : vTarjetas) {
			propiedad = banco.getTarjetaPropiedad(vTarjeta[0]);
			if (propiedad != null) {
				if (propiedad.isPropiedadCalle()) {
					strStyle = ((TarjetaCalle) (propiedad)).getColor();
				} else {
					if (propiedad.isPropiedadCompania())
						strStyle = "blanco";
					else
						strStyle = "negro";
				}
				bCrearImagen = false;
				if (jugador.getTarjPropiedadList().contains(propiedad)) {
					bCrearImagen = true;
					if (!propiedad.isHipotecada())
						rutaImagen = propiedad.getPathImagenFrente();
					else
						rutaImagen = propiedad.getPathImagenDorso();
					strToolTip = showToolTipsPropiedad(propiedad);
				}

				final HBox hbPropiedad = crearHBoxTarjetaPropiedad(strStyle,
						bCrearImagen, rutaImagen, hbWidth, hbHeight,
						strToolTip, propiedad);

				gridPane2.add(hbPropiedad, Integer.parseInt(vTarjeta[1]),
						Integer.parseInt(vTarjeta[2]));
			}
		}

		hbPropiedades.getChildren().add(gridPane1);
		hbPropiedades.getChildren().add(gridPane2);

		// ===================== Area extra =========================//

		Image imgCasa;
		Image imgHotel;
		Image imgCarcel;

		imgCasa = new Image(
				TableroController.class
						.getResourceAsStream("/images/fichas/Casa01.png"),
				30, 30, false, false);
		imgHotel = new Image(
				TableroController.class
						.getResourceAsStream("/images/fichas/Casa05.png"),
				30, 30, false, false);

		imgCarcel = new Image(
				TableroController.class
						.getResourceAsStream("/images/tarjetas/Carcel.jpg"),
				30, 30, false, false);

		Label lblDescripcion;

		hbExtra.getChildren().add(new ImageView(imgCasa));
		lblDescripcion = new Label("x " + jugador.getNroCasas());
		lblDescripcion.setStyle("-fx-text-fill: white;");
		hbExtra.getChildren().add(lblDescripcion);

		hbExtra.getChildren().add(new ImageView(imgHotel));
		lblDescripcion = new Label("x " + jugador.getNroHoteles());
		lblDescripcion.setStyle("-fx-text-fill: white;");
		hbExtra.getChildren().add(lblDescripcion);
		hbExtra.getChildren().add(new ImageView(imgCarcel));
		lblDescripcion = new Label("x " + jugador.getTarjetaCarcelList().size());
		lblDescripcion.setStyle("-fx-text-fill: white;");
		hbExtra.getChildren().add(lblDescripcion);

		TitledPane tpInfoPlayer = new TitledPane(title, scroll);
		tpInfoPlayer.setStyle("-fx-text-fill: white;");
		tpInfoPlayer.setId("tp_" + jugador.getNombre());
		tpInfoPlayer.setCollapsible(true);

		Image img = new Image(
				TableroController.class.getResourceAsStream(jugador.getFicha()
						.getPathImgSmall()), 25d, 25d, true, true);
		ImageView ivFicha = new ImageView(img);
		tpInfoPlayer.setGraphic(ivFicha);
		return tpInfoPlayer;
	}

	/**
	 * Dibuja el TitledPan con la información actual del banco.
	 * 
	 * @param banco
	 * @param title
	 * @return
	 */
	private TitledPane getPaneInfoBanco(Banco banco, String title) {
		AnchorPane root = new AnchorPane();
		VBox vBox = new VBox();
		HBox hbPropiedades = new HBox();
		HBox hbExtra = new HBox();
		ScrollPane scroll;

		root.getStyleClass().add("bg_info_panel");
		root.setPadding(new Insets(10));
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);

		hbPropiedades.setAlignment(Pos.CENTER);
		hbPropiedades.setSpacing(20);

		hbExtra.setAlignment(Pos.CENTER);
		hbExtra.setSpacing(20);

		acoplarAContenedor(vBox);
		vBox.getChildren().add(hbPropiedades);
		vBox.getChildren().add(hbExtra);

		root.getChildren().add(vBox);
		scroll = makeScrollable(root);
		scroll.autosize();

		// ===================== HBox de propiedades =====================//

		TarjetaPropiedad propiedad;
		String rutaImagen = "";
		String strStyle = "";
		Boolean bCrearImagen = false;
		String strToolTip = "";
		double hbWidth = 35;
		double hbHeight = 60;

		GridPane gridPane1 = new GridPane();
		GridPane gridPane2 = new GridPane();

		acoplarAContenedor(gridPane1);
		acoplarAContenedor(gridPane2);

		gridPane1.setHgap(5);
		gridPane1.setVgap(10);

		gridPane2.setHgap(5);
		gridPane2.setVgap(10);

		List<String[]> vTarjetas = new ArrayList<String[]>();

		// Tarjetas para el gridpane1
		vTarjetas.add(new String[] { "tarjeta02", "0", "0" });
		vTarjetas.add(new String[] { "tarjeta04", "1", "0" });
		vTarjetas.add(new String[] { "tarjeta07", "0", "1", });
		vTarjetas.add(new String[] { "tarjeta09", "1", "1", });
		vTarjetas.add(new String[] { "tarjeta10", "2", "1", });
		vTarjetas.add(new String[] { "tarjeta12", "0", "2", });
		vTarjetas.add(new String[] { "tarjeta14", "1", "2", });
		vTarjetas.add(new String[] { "tarjeta15", "2", "2", });
		vTarjetas.add(new String[] { "tarjeta17", "0", "3", });
		vTarjetas.add(new String[] { "tarjeta19", "1", "3", });
		vTarjetas.add(new String[] { "tarjeta20", "2", "3", });
		vTarjetas.add(new String[] { "tarjeta22", "0", "4", });
		vTarjetas.add(new String[] { "tarjeta24", "1", "4", });
		vTarjetas.add(new String[] { "tarjeta25", "2", "4", });

		for (String[] vTarjeta : vTarjetas) {
			propiedad = banco.getTarjetaPropiedad(vTarjeta[0]);
			if (propiedad != null) {
				if (propiedad instanceof TarjetaCalle) {
					strStyle = ((TarjetaCalle) (propiedad)).getColor();
				} else {
					if (propiedad instanceof TarjetaCompania)
						strStyle = "blanco";
					else
						strStyle = "negro";
				}
				bCrearImagen = false;
				if (propiedad.getJugador() == null) {
					bCrearImagen = true;
					rutaImagen = propiedad.getPathImagenFrente();
					strToolTip = showToolTipsPropiedad(propiedad);
				}
				gridPane1.add(
						crearHBoxTarjetaPropiedad(strStyle, bCrearImagen,
								rutaImagen, hbWidth, hbHeight, strToolTip,
								propiedad), Integer.parseInt(vTarjeta[1]),
						Integer.parseInt(vTarjeta[2]));
			}
		}

		// Tarjetas para el gridpane2
		vTarjetas = new ArrayList<String[]>();
		vTarjetas.add(new String[] { "tarjeta27", "0", "0", });
		vTarjetas.add(new String[] { "tarjeta28", "1", "0", });
		vTarjetas.add(new String[] { "tarjeta30", "2", "0", });
		vTarjetas.add(new String[] { "tarjeta32", "0", "1", });
		vTarjetas.add(new String[] { "tarjeta33", "1", "1", });
		vTarjetas.add(new String[] { "tarjeta35", "2", "1", });
		vTarjetas.add(new String[] { "tarjeta38", "0", "2", });
		vTarjetas.add(new String[] { "tarjeta40", "1", "2", });
		vTarjetas.add(new String[] { "tarjeta13", "0", "3", });
		vTarjetas.add(new String[] { "tarjeta29", "1", "3", });
		vTarjetas.add(new String[] { "tarjeta06", "0", "4", });
		vTarjetas.add(new String[] { "tarjeta16", "1", "4", });
		vTarjetas.add(new String[] { "tarjeta26", "2", "4", });
		vTarjetas.add(new String[] { "tarjeta36", "3", "4", });

		for (String[] vTarjeta : vTarjetas) {
			propiedad = banco.getTarjetaPropiedad(vTarjeta[0]);
			if (propiedad != null) {
				if (propiedad instanceof TarjetaCalle) {
					strStyle = ((TarjetaCalle) (propiedad)).getColor();
				} else {
					if (propiedad instanceof TarjetaCompania)
						strStyle = "blanco";
					else
						strStyle = "negro";
				}
				bCrearImagen = false;
				if (propiedad.getJugador() == null) {
					bCrearImagen = true;
					rutaImagen = propiedad.getPathImagenFrente();
					strToolTip = showToolTipsPropiedad(propiedad);
				}
				gridPane2.add(
						crearHBoxTarjetaPropiedad(strStyle, bCrearImagen,
								rutaImagen, hbWidth, hbHeight, strToolTip,
								propiedad), Integer.parseInt(vTarjeta[1]),
						Integer.parseInt(vTarjeta[2]));
			}
		}

		hbPropiedades.getChildren().add(gridPane1);
		hbPropiedades.getChildren().add(gridPane2);

		// ===================== Area extra =========================//

		Image imgCasa;
		Image imgHotel;

		imgCasa = new Image(
				TableroController.class
						.getResourceAsStream("/images/fichas/Casa01.png"),
				30, 30, false, false);
		imgHotel = new Image(
				TableroController.class
						.getResourceAsStream("/images/fichas/Casa05.png"),
				30, 30, false, false);

		hbExtra.getChildren().add(new ImageView(imgCasa));
		hbExtra.getChildren().add(new Label("x " + banco.getNroCasas()));
		hbExtra.getChildren().add(new ImageView(imgHotel));
		hbExtra.getChildren().add(new Label("x " + banco.getNroHoteles()));

		TitledPane tpBanco = new TitledPane(title, scroll);
		tpBanco.setId("tp_banco");
		tpBanco.setStyle("-fx-text-fill: white;");
		tpBanco.setCollapsible(true);
		return tpBanco;
	}

	/**
	 * Dibuja el tooltips sobre la propiedad y settea el estilo.
	 * 
	 * @param style
	 *            de la propiedad.
	 * @param creaImagen
	 *            si el jugador posee la propiedad.
	 * @param rutaImagen
	 *            ruta de la imágen.
	 * @param hbWidth
	 *            ancho de la imágen.
	 * @param hbHeight
	 *            alto de la imágen.
	 * @param toolTips
	 *            de la propiedad.
	 * 
	 * @return
	 */
	private HBox crearHBoxTarjetaPropiedad(final String style,
			final boolean creaImagen, final String rutaImagen,
			final Double hbWidth, final Double hbHeight, final String toolTips,
			final TarjetaPropiedad propiedad) {
		final HBox hBox_inner = new HBox();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				Image imgPropiedad;
				Tooltip tpImagen;

				hBox_inner.getStyleClass().add("border_" + style.toLowerCase());
				hBox_inner.setPrefSize(hbWidth, hbHeight);

				if (creaImagen) {
					imgPropiedad = new Image(TableroController.class
							.getResourceAsStream(rutaImagen), hbWidth,
							hbHeight, false, false);
					hBox_inner.getChildren().add(new ImageView(imgPropiedad));
					tpImagen = new Tooltip(toolTips);
					tpImagen.setContentDisplay(ContentDisplay.TOP);
					imgPropiedad = new Image(TableroController.class
							.getResourceAsStream(rutaImagen), 250, 284, false,
							false);
					tpImagen.setGraphic(new ImageView(imgPropiedad));
					tpImagen.setAutoHide(false);
					Tooltip.install(hBox_inner, tpImagen);

					if (propiedad.getJugador() != null) {
						final ContextMenu contextMenu = new ContextMenu();
						if (propiedad.getJugador().equals(getMyPlayer())) {
							MenuItem btnHipo = new MenuItem("Hipotecar");
							MenuItem btnDes = new MenuItem("Deshipotecar");
							MenuItem btnCon = new MenuItem("Construir");
							MenuItem btnVCon = new MenuItem(
									"Vender construcciones");
							MenuItem btnVProp = new MenuItem("Vender propiedad");

							contextMenu.getItems().addAll(btnHipo, btnDes,
									btnCon, btnVCon, btnVProp);

							// Seteamos el estado de los botones...
							if (!propiedad.isHipotecable())
								btnHipo.setDisable(true);
							else
								btnHipo.setOnAction(new EventHipotecar(
										propiedad));

							if (!propiedad.isDeshipotecable())
								btnDes.setDisable(true);
							else
								btnDes.setOnAction(new EventDeshipotecar(
										propiedad));

							if (propiedad.isPropiedadCalle()) {
								if (!((TarjetaCalle) propiedad).isContruible())
									btnCon.setDisable(true);
								else
									btnCon.setOnAction(new EventConstruir(
											propiedad));
							} else {
								btnCon.setVisible(false);
							}

							if (propiedad.isPropiedadCalle()) {
								if (!((TarjetaCalle) propiedad)
										.isDescontruible())
									btnVCon.setDisable(true);
								else
									btnVCon.setOnAction(new EventDesconstruir(
											propiedad));
							} else {
								btnVCon.setVisible(false);
							}

							if (btnVCon.isVisible() && !btnVCon.isDisable())
								btnVProp.setDisable(true);

						} else {
							MenuItem btnOferta = new MenuItem(
									"Comprar propiedad");

							btnOferta.setOnAction(new EventComprarPropiedad(
									propiedad));

							contextMenu.getItems().addAll(btnOferta);

						}
						hBox_inner.setOnMouseClicked(event -> {
							if (event.getButton() == MouseButton.SECONDARY) {
								contextMenu.show(hBox_inner,
										event.getScreenX(), event.getScreenY());
							}
						});
					}
				}
			}
		});
		return hBox_inner;
	}

	/**
	 * Acopla al contenedor un panel.
	 * 
	 * @param node
	 * @param valor
	 */
	private void acoplarAContenedor(javafx.scene.Node node) {
		AnchorPane.setLeftAnchor(node, (double) 0);
		AnchorPane.setRightAnchor(node, (double) 0);
		AnchorPane.setTopAnchor(node, (double) 0);
		AnchorPane.setBottomAnchor(node, (double) 0);
	}

	/**
	 * Método para agregar un tooltips a la imagen de la propiedad con
	 * información sobre la misma.
	 * 
	 * @param propiedad
	 * @return
	 */
	private String showToolTipsPropiedad(TarjetaPropiedad propiedad) {
		String tooltip = propiedad.getNombre();
		if (propiedad.isHipotecada())
			tooltip += " (Hipotecada)";
		tooltip += " - "
				+ StringUtils.formatearAMoneda(propiedad.getValorPropiedad());
		return tooltip;
	}

	/**
	 * Crea la barra de desplazamiento a un componente.
	 * 
	 * @param node
	 *            componente para el cual se agregará la barra de
	 *            desplazamiento.
	 * @return el objeto scrolleable.
	 */
	private ScrollPane makeScrollable(final AnchorPane node) {
		final ScrollPane scroll = new ScrollPane();
		scroll.setContent(node);
		scroll.viewportBoundsProperty().addListener(
				new ChangeListener<Bounds>() {
					@Override
					public void changed(ObservableValue<? extends Bounds> ov,
							Bounds oldBounds, Bounds bounds) {
						node.setPrefWidth(bounds.getWidth());
					}
				});
		return scroll;
	}

	/**
	 * Devuelve un color de acuerdo al usuario logueado y al que se envía.
	 * 
	 * @param nombreUsuario
	 *            El usuario que inicia el evento.
	 * @return El color que corresponde (o {@code Color.RED} si no se encuentra
	 *         el usuario)
	 */
	private javafx.scene.paint.Color determinarColor(String nombreUsuario) {
		Color fillColor = Color.RED;
		String jugador = TableroController.this.usuarioLogueado.getUserName();
		if (jugador != null) {
			if (jugador.equals(nombreUsuario))
				fillColor = Color.DARKGREEN;
			else
				fillColor = Color.DARKBLUE;
		}
		return fillColor;
	}

	/**
	 * Borra todos los elementos que estan dibujados en todos los casillero del
	 * tablero.
	 */
	private void limpiarCasilleros() throws Exception {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				pCasillero01.getChildren().clear();
				pCasillero02.getChildren().clear();
				pCasillero03.getChildren().clear();
				pCasillero04.getChildren().clear();
				pCasillero05.getChildren().clear();
				pCasillero06.getChildren().clear();
				pCasillero07.getChildren().clear();
				pCasillero08.getChildren().clear();
				pCasillero09.getChildren().clear();
				pCasillero10.getChildren().clear();
				pCasillero11.getChildren().clear();
				pCasillero12.getChildren().clear();
				pCasillero13.getChildren().clear();
				pCasillero14.getChildren().clear();
				pCasillero15.getChildren().clear();
				pCasillero16.getChildren().clear();
				pCasillero17.getChildren().clear();
				pCasillero18.getChildren().clear();
				pCasillero19.getChildren().clear();
				pCasillero20.getChildren().clear();
				pCasillero21.getChildren().clear();
				pCasillero22.getChildren().clear();
				pCasillero23.getChildren().clear();
				pCasillero24.getChildren().clear();
				pCasillero25.getChildren().clear();
				pCasillero26.getChildren().clear();
				pCasillero27.getChildren().clear();
				pCasillero28.getChildren().clear();
				pCasillero29.getChildren().clear();
				pCasillero30.getChildren().clear();
				pCasillero31.getChildren().clear();
				pCasillero32.getChildren().clear();
				pCasillero33.getChildren().clear();
				pCasillero34.getChildren().clear();
				pCasillero35.getChildren().clear();
				pCasillero36.getChildren().clear();
				pCasillero37.getChildren().clear();
				pCasillero38.getChildren().clear();
				pCasillero39.getChildren().clear();
				pCasillero40.getChildren().clear();
			}
		});
	}

	// ======================================================================//
	// ============================== Event Fx ==============================//
	// ======================================================================//

	/**
	 * Método que muestra un messagebox para que el jugador tire los dados para
	 * determinar el turno de inicio de juego.
	 * 
	 * @param event
	 */
	@FXML
	public void processTirarDados(ActionEvent event) {

		Platform.runLater(new Runnable() {
			private Stage tirarDadosStage;

			@Override
			public void run() {
				String fxml;
				String title;
				TirarDadosController controller;

				try {
					fxml = ConstantesFXML.FXML_TIRAR_DADOS;
					tirarDadosStage = new Stage();
					title = estadoActual.currentPlayer.estaPreso() ? "Monopoly - Tirar Dados dobles."
							: "Monopoly - Tirar Dados avance de casilleros";
					controller = (TirarDadosController) FXUtils.cargarStage(
							tirarDadosStage, fxml, title, false, false,
							Modality.APPLICATION_MODAL, StageStyle.DECORATED);
					controller.setCurrentStage(tirarDadosStage);
					controller.settearDatos(usuarioLogueado.getNombre());
					if (estadoActual.currentPlayer.estaPreso())
						controller.setTipoTirada(TipoTiradaEnum.TIRAR_CARCEL);
					else
						controller.setTipoTirada(TipoTiradaEnum.TIRAR_AVANCE);
					tirarDadosStage.showAndWait();

				} catch (Exception ex) {
					GestorLogs.registrarException(ex);
					showMessageBox(AlertType.ERROR, "Error...", null,
							ex.getMessage(), null);
				}
			}
		});
	}

	@FXML
	void processMenu(ActionEvent event) {

	}

	@FXML
	void processSendMessage(ActionEvent event) {
		if (txtMessageChat.getText().trim().length() > 0) {
			sendChatMessage();
		}
	}

	@FXML
	void processfinalizarTurno(ActionEvent event) {
		try {
			finalizarTurno();
		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
			showMessageBox(AlertType.ERROR, "Error...", null, ex.getMessage(),
					null);
		}
	}

	@FXML
	void processBancarrota(ActionEvent event) {

		boolean answer = false;
		answer = showYesNoMsgBox("Abandonar juego", null,
				"¿Está seguro que desea declararse en bancarrota y abandonar el juego?");

		if (answer) {
			BankruptcyMessage bancarrota = new BankruptcyMessage(getJuego()
					.getUniqueID(), getPlayer(getUsuarioLogueado()).getNombre());

			ConnectionController.getInstance().send(bancarrota);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				GestorLogs.registrarException(e);
				e.printStackTrace();
			}

			this.cerrar(true);
		}
	}

	/**
	 * Evento ejecutado cuando se preciona el botón Guardar.
	 * 
	 * @param event
	 */
	@FXML
	void processGuardar(ActionEvent event) {
		if (cantJugadoresHumanos() == 1)
			this.guardarJuego();
		else
			showNoFutureMessageBox(AlertType.ERROR, "No se puede guardar",
					null,
					"No se puede guardar el juego cuando hay mas de un jugador humano.");

		// this.cerrar(true);
		/*
		 * Cierro la ventana cuando llega la confirmación de que el juego se
		 * guardó sin problemas
		 */
	}

	/**
	 * Evento ejecutado cuando se preciona abandonar/salir del juego.
	 * 
	 * @param event
	 */
	@FXML
	void processAbandonar(ActionEvent event) {
		this.cerrar(false);
	}

	// ======================================================================//
	// ========================== Getter & Setter ===========================//
	// ======================================================================//

	/**
	 * Instancia del tablero.
	 * 
	 * @return
	 */
	public static TableroController getInstance() {
		if (instance == null)
			instance = new TableroController();
		return instance;
	}

	public Stage getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

	public Stage getPrevStage() {
		return prevStage;
	}

	public void setPrevStage(Stage prevStage) {
		this.prevStage = prevStage;
	}

	public Juego getJuego() {
		return juego;
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public Deuda getDeudaPendiente() {
		return deudaPendiente;
	}

	public void setDeudaPendiente(Deuda deudaPendiente) {
		this.deudaPendiente = deudaPendiente;
	}

	/**
	 * Devuelve la cantidad de jugadores humanos del juego
	 * 
	 * @return La cantidad de jugadores humanos que están jugando
	 */
	private int cantJugadoresHumanos() {
		int contador = 0;
		for (Jugador jugador : estadoActual.turnos) {
			if (jugador.isHumano())
				contador++;
		}
		return contador;
	}

	/**
	 * Devuelve el {@code JugadorHumano} que pertenece al {@code Usuario}
	 * 
	 * @param usuario
	 *            El usuario del cual se quiere conocer el Jugador
	 * @return El jugador
	 */
	public JugadorHumano getPlayer(Usuario usuario) {

		for (Jugador jugador : estadoActual.turnos) {
			if (jugador.isHumano()) {
				if (((JugadorHumano) jugador).getUsuario().equals(usuario))
					return (JugadorHumano) jugador;
			}
		}
		return null;
	}

	/**
	 * Devuelve el Jugador del usuario logueado
	 * 
	 * @return El Jugador del usuario logueado
	 */
	public JugadorHumano getMyPlayer() {
		return getPlayer(usuarioLogueado);
	}

}
