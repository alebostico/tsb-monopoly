/**
 * 
 */
package monopoly.client.controller;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import monopoly.client.connection.ConnectionController;
import monopoly.client.util.ScreensFramework;
import monopoly.model.Banco;
import monopoly.model.Estado.EstadoJuego;
import monopoly.model.History;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.JugadorHumano;
import monopoly.model.MonopolyGameStatus;
import monopoly.model.Usuario;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaCompania;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;
import monopoly.util.StringUtils;
import monopoly.util.constantes.ConstantesFXML;
import monopoly.util.constantes.EnumsTirarDados;

import org.controlsfx.dialog.Dialogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class TableroController extends AnchorPane implements Serializable,
		Initializable {

	private static final long serialVersionUID = 1L;

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
	private MenuButton btnAcciones;

	@FXML
	private MenuItem btnHipotecar;

	@FXML
	private MenuItem btnVender;

	@FXML
	private MenuItem btnDeshipotecar;

	@FXML
	private MenuItem btnComercializar;

	@FXML
	private MenuItem btnConstruir;

	@FXML
	private ListView<History> lvHistoryChat;
	private List<History> historyChatList;
	private ObservableList<History> oHistoryChatList;

	@FXML
	private ListView<History> lvHistory;
	private List<History> historyGameList;
	private ObservableList<History> oHistoryGameList;

	private static TableroController instance;

	@FXML
	private Stage currentStage;

	@FXML
	private Stage prevStage;

	@FXML
	private Stage preloaderStage;

	private TitledPane[] tps;

	private Juego juego;

	private Usuario usuarioLogueado;

	private MonopolyGameStatus status;

	private StringProperty clockLabelTextProperty;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		instance = this;
		historyGameList = new ArrayList<History>();
		oHistoryGameList = FXCollections.observableArrayList(historyGameList);
		historyChatList = new ArrayList<History>();
		oHistoryChatList = FXCollections.observableArrayList(historyChatList);
		accordionHistorial.setExpandedPane(tpHistory);
	}

	/**
	 * 
	 * Éste método muestra el tablero y muestra un messagebox informando al
	 * jugador que debe esperar a que se unan al juego otros oponentes.
	 * 
	 */
	public void showBoard() {
		currentStage.setFullScreen(true);
		currentStage.show();
		prevStage.close();
		currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				ConnectionController.getInstance().cerrarConexion();
			}
		});
		this.clockLabelTextProperty = lblStopwatch.textProperty();

		createDigitalClock();
		if (usuarioLogueado.equals(juego.getOwner()))
			addHistoryGame(usuarioLogueado.getUserName(), "Juego Creado.");
		else
			addHistoryGame(usuarioLogueado.getUserName(), "Te uniste al Juego.");
		esperarJugadores();
	}

	/**
	 * Método que agrega un history al panel de información que se utilizará
	 * para llevar un registro sobre jugadas o acciones que se realizan en el
	 * juego.
	 * 
	 * @param usuario
	 *            nombre que aparecerá en la primer columna informando quién fue
	 *            el que realizó el evento.
	 * @param mensaje
	 *            mensaje que se mostrará, para informar a los jugadores sobre
	 *            las acciones que se realizaró
	 * 
	 */
	private void addHistoryGame(String usuario, String mensaje) {
		History history = new History(StringUtils.getFechaActual(), usuario,
				mensaje);
		addHistoryGame(history);
	}

	/**
	 * Método que agrega un history al panel de información que se utilizará
	 * para llevar un registro sobre jugadas o acciones que se realizan en el
	 * juego.
	 * 
	 * @param history
	 *            objecto historia que contiene información sobre el usuario,
	 *            descripción del mensaje, y fecha en el que se produjó el
	 *            evento.
	 */
	public void addHistoryGame(final History history) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				oHistoryGameList = FXCollections
						.observableArrayList(historyGameList);
				lvHistory.setItems(oHistoryGameList);
			}
		});

	}

	/**
	 * 
	 * Método que agrega un mensaje de chat al panel de Chat.
	 * 
	 * @param usuario
	 * @param mensaje
	 */
	@SuppressWarnings("unused")
	private void addHistoryChat(String usuario, String mensaje) {
		History history = new History(StringUtils.getFechaActual(), usuario,
				mensaje);
		addHistoryChat(history);
	}

	/**
	 * 
	 * Método que agrega un mensaje de chat al panel de Chat.
	 * 
	 * @param history
	 *            objeto que contiene información sobre el usuario que escribió
	 *            el mensaje, el mensaje y la fecha y hora.
	 */
	public void addHistoryChat(History history) {
		historyChatList.add(history);
		oHistoryChatList = FXCollections.observableArrayList(historyChatList);
		lvHistoryChat.setItems(oHistoryChatList);
	}

	/**
	 * Método que muestra un messagebox informando que el jugador debe esperar
	 * por oponentes.
	 */
	private void esperarJugadores() {
		preloaderStage = new Stage();

		try {

			String fxml = ConstantesFXML.FXML_SPLASH;
			Parent root;

			FXMLLoader loader = ScreensFramework.getLoader(fxml);

			root = (Parent) loader.load();

			Scene scene = new Scene(root);
			preloaderStage.setScene(scene);
			preloaderStage.setTitle("Monopoly - Waiting for players");
			preloaderStage.centerOnScreen();
			preloaderStage.setResizable(false);
			preloaderStage.initModality(Modality.APPLICATION_MODAL);
			preloaderStage.initStyle(StageStyle.UNDECORATED);
			SplashController controller = (SplashController) loader
					.getController();
			controller.setController(TableroController.this);
			controller.setCurrentStage(preloaderStage);
			preloaderStage.show();

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(ex.getMessage());
		}
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
	 * Método que muestra un messagebox para que el jugador tire los dados para
	 * determinar el turno de inicio de juego.
	 */
	public void tirarDados(final EnumsTirarDados.TirarDados modo) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String fxml;
				Parent root;
				FXMLLoader loader;
				Stage tirarDadosStage;
				Scene scene;
				TirarDadosController controller;

				try {

					fxml = ConstantesFXML.FXML_TIRAR_DADOS;
					tirarDadosStage = new Stage();
					loader = ScreensFramework.getLoader(fxml);

					root = (Parent) loader.load();
					controller = (TirarDadosController) loader.getController();

					scene = new Scene(root);
					tirarDadosStage.setScene(scene);
					tirarDadosStage.setResizable(false);
					tirarDadosStage.initModality(Modality.APPLICATION_MODAL);
					tirarDadosStage.initStyle(StageStyle.UNDECORATED);
					controller.setCurrentStage(tirarDadosStage);
					tirarDadosStage.centerOnScreen();

					switch (modo) {
					case TURNO_INICIO:
						tirarDadosStage
								.setTitle("Monopoly - Tirar Dados para establecer turnos");
						SplashController.getInstance().getCurrentStage()
								.close();

						break;

					case AVANZAR_CASILLERO:
						tirarDadosStage
								.setTitle("Monopoly - Tirar Dados para avanzar de casilleros");
						break;

					default:
						break;
					}

					controller.showTirarDados(TableroController.getInstance()
							.getUsuarioLogueado().getNombre(), modo);

				} catch (Exception ex) {
					// TODO Auto-generated catch block
					GestorLogs.registrarError(ex.getMessage());
				}
			}
		});
	}

	/**
	 * Método que recibe la información sobre el orden de los turnos para
	 * empezar a jugar el juego.
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void empezarJuego() {
		try {
			showAccordionJugadores();
			displayFichas();
			TirarDadosController.getInstance().getCurrentStage().close();
		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
			Dialogs.create()
					.owner(currentStage)
					.title("Error")
					.masthead("Graficar")
					.message(
							"Se produjo un error mientras se dibujaban los graficos.")
					.showError();
		}
	}

	@SuppressWarnings("deprecation")
	public void determinarAccionEnCasillero() {
		// TODO Auto-generated method stub
		try {
			showAccordionJugadores();
			displayFichas();
			TirarDadosController.getInstance().getCurrentStage().close();
			switch (status.status) {
			default:
				break;
			}
		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
			Dialogs.create()
					.owner(currentStage)
					.title("Error")
					.masthead("Graficar")
					.message(
							"Se produjo un error mientras se dibujaban los graficos.")
					.showError();
		}
	}

	public void actualizarEstadoJuego(MonopolyGameStatus status) {
		this.status = status;

		for (History history : status.hirtoryList) {
			TableroController.getInstance().addHistoryGame(history);
		}

		if (status.status == EstadoJuego.TIRAR_DADO) {
			if (status.currentPlayer.getNombre().toLowerCase()
					.equals(usuarioLogueado.getNombre().toLowerCase())) {
				TirarDadosController.getInstance()
						.habilitarBtnAceptarTirarDados();
			} else {
				TirarDadosController.getInstance().habilitarBtnAceptarCerrar();
			}
		}
	}

	// ======================================================================================//
	// =================== Métodos para dibujar componentes en la pantalla
	// ==================//
	// ======================================================================================//

	/**
	 * Método que dibuja a los jugadores, mostrando el estado en el juego.
	 * 
	 */
	private void showAccordionJugadores() throws Exception {
		List<Jugador> turnos = status.turnos;		
		tps = new TitledPane[turnos.size() + 1];		
		String title;

		for (int i = 0; i < turnos.size(); i++) {
			title = turnos.get(i).getNombre() + " - ";
			title += StringUtils.decimalFormat.format(turnos.get(i)
					.getDinero()) + " - ";
			title += (turnos.get(i) instanceof JugadorHumano) ? "Jugador Humano"
					: "Jugador Virtual";
			tps[i] = getPaneInfoPlayer(turnos.get(i), title);
		}
		tps[turnos.size()] = getPaneInfoBanco(status.banco, "BANCO");
		
		accordionPlayers.getPanes().addAll(tps);
		accordionPlayers.setExpandedPane(tps[0]);
	}

	/**
	 * Método que dibuja las fichas en el tablero.
	 * 
	 * @throws Exception
	 */
	private void displayFichas() throws Exception {
		List<Jugador> turnos = status.turnos;
		// Tablero tablero = status.tablero;
		Image img;

		for (Jugador jugadorTurno : turnos) {
			img = new Image(
					TableroController.class.getResourceAsStream(jugadorTurno
							.getFicha().getPathImgSmall()), 25, 25, true, true);

			switch (jugadorTurno.getCasilleroActual().getNumeroCasillero()) {
			case 1:
				pCasillero01.getChildren().add(new ImageView(img));
				break;
			case 2:
				pCasillero02.getChildren().add(new ImageView(img));
				break;
			case 3:
				pCasillero03.getChildren().add(new ImageView(img));
				break;
			case 4:
				pCasillero04.getChildren().add(new ImageView(img));
				break;
			case 5:
				pCasillero05.getChildren().add(new ImageView(img));
				break;
			case 6:
				pCasillero06.getChildren().add(new ImageView(img));
				break;
			case 7:
				pCasillero07.getChildren().add(new ImageView(img));
				break;
			case 8:
				pCasillero08.getChildren().add(new ImageView(img));
				break;
			case 9:
				pCasillero01.getChildren().add(new ImageView(img));
				break;
			case 10:
				pCasillero10.getChildren().add(new ImageView(img));
				break;
			case 11:
				pCasillero11.getChildren().add(new ImageView(img));
				break;
			case 12:
				pCasillero12.getChildren().add(new ImageView(img));
				break;
			case 13:
				pCasillero13.getChildren().add(new ImageView(img));
				break;
			case 14:
				pCasillero14.getChildren().add(new ImageView(img));
				break;
			case 15:
				pCasillero15.getChildren().add(new ImageView(img));
				break;
			case 16:
				pCasillero16.getChildren().add(new ImageView(img));
				break;
			case 17:
				pCasillero17.getChildren().add(new ImageView(img));
				break;
			case 18:
				pCasillero18.getChildren().add(new ImageView(img));
				break;
			case 19:
				pCasillero19.getChildren().add(new ImageView(img));
				break;

			case 20:
				pCasillero20.getChildren().add(new ImageView(img));
				break;
			case 21:
				pCasillero21.getChildren().add(new ImageView(img));
				break;
			case 22:
				pCasillero22.getChildren().add(new ImageView(img));
				break;
			case 23:
				pCasillero23.getChildren().add(new ImageView(img));
				break;
			case 24:
				pCasillero24.getChildren().add(new ImageView(img));
				break;
			case 25:
				pCasillero25.getChildren().add(new ImageView(img));
				break;
			case 26:
				pCasillero26.getChildren().add(new ImageView(img));
				break;
			case 27:
				pCasillero27.getChildren().add(new ImageView(img));
				break;
			case 28:
				pCasillero28.getChildren().add(new ImageView(img));
				break;
			case 29:
				pCasillero29.getChildren().add(new ImageView(img));
				break;

			case 30:
				pCasillero30.getChildren().add(new ImageView(img));
				break;
			case 31:
				pCasillero31.getChildren().add(new ImageView(img));
				break;
			case 32:
				pCasillero32.getChildren().add(new ImageView(img));
				break;
			case 33:
				pCasillero33.getChildren().add(new ImageView(img));
				break;
			case 34:
				pCasillero34.getChildren().add(new ImageView(img));
				break;
			case 35:
				pCasillero35.getChildren().add(new ImageView(img));
				break;
			case 36:
				pCasillero36.getChildren().add(new ImageView(img));
				break;
			case 37:
				pCasillero37.getChildren().add(new ImageView(img));
				break;
			case 38:
				pCasillero38.getChildren().add(new ImageView(img));
				break;
			case 39:
				pCasillero39.getChildren().add(new ImageView(img));
				break;
			case 40:
				pCasillero40.getChildren().add(new ImageView(img));
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 
	 * Dibuje el TitledPane con la información actual del jugador.
	 * 
	 * @param jugador
	 * @param title
	 * @return
	 */
	private TitledPane getPaneInfoPlayer(Jugador jugador, String title)
			throws Exception {
		AnchorPane root = new AnchorPane();
		VBox vBox = new VBox();
		//HBox hbInfoJugador = new HBox();
		HBox hbPropiedades = new HBox();
		HBox hbExtra = new HBox();
		ScrollPane scroll;

		acoplarAContenedor(vBox, 0);
		root.getStyleClass().add("bg_info_panel");
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);

		//hbInfoJugador.setAlignment(Pos.CENTER);
		//hbInfoJugador.setSpacing(25);

		hbPropiedades.setAlignment(Pos.CENTER);
		hbPropiedades.setSpacing(20);

		hbExtra.setAlignment(Pos.CENTER);
		hbExtra.setSpacing(20);

		//vBox.getChildren().add(hbInfoJugador);
		vBox.getChildren().add(hbPropiedades);
		vBox.getChildren().add(hbExtra);
				
		root.getChildren().add(vBox);
		scroll = makeScrollable(root);

		VBox pImgFicha = new VBox();
		acoplarAContenedor(pImgFicha, 0);
		pImgFicha.setAlignment(Pos.CENTER);

		pImgFicha.getStyleClass().add("bg_info_ficha");
		pImgFicha.setPrefSize((double) 60, (double) 60);
		

		//pImgFicha.getChildren().add(ivFicha);

//		hbInfoJugador.getChildren().add(pImgFicha);
//		hbInfoJugador.getChildren().add(new Label(jugador.getNombre()));
//		hbInfoJugador.getChildren().add(
//				new Label(StringUtils.FORMATO_IMPORTE.format(jugador
//						.getDinero())));

//		if (status.currentPlayer.equals(jugador)) {
//			img = new Image(
//					TableroController.class
//							.getResourceAsStream("/images/dados/dice.png"),
//					40, 40, true, true);
//			ivFicha = new ImageView(img);
//
//			hbInfoJugador.getChildren().add(ivFicha);
//		}

		// ===================== HBox de propiedades =====================//

		TarjetaPropiedad propiedad;
		Banco banco = status.banco;
		String rutaImagen = "";
		String strStyle = "";
		Boolean bCrearImagen = false;
		String strToolTip = "";
		double hbWidth = 35;
		double hbHeight = 60;
		List<String[]> vTarjetas = new ArrayList<String[]>();

		GridPane gridPane1 = new GridPane();
		GridPane gridPane2 = new GridPane();

		acoplarAContenedor(gridPane1, 0);
		acoplarAContenedor(gridPane2, 0);

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
				if (propiedad instanceof TarjetaCalle) {
					strStyle = ((TarjetaCalle) (propiedad)).getColorTarjeta();
				} else {
					if (propiedad instanceof TarjetaCompania)
						strStyle = "blanco";
					else
						strStyle = "negro";
				}
				bCrearImagen = false;
				if (jugador.getTarjPropiedadList().contains(propiedad)) {
					bCrearImagen = true;
					if (!propiedad.isHipotecada())
						rutaImagen = propiedad.getPathImagenPropiedad();
					else
						rutaImagen = propiedad.getPathImagenPropiedad().replace(
								"propiedades", "dorso");
					strToolTip = showToolTipsPropiedad(propiedad);
				}
				gridPane1.add(
						crearHBoxTarjetaPropiedad(strStyle, bCrearImagen,
								rutaImagen, hbWidth, hbHeight, strToolTip),
						Integer.parseInt(vTarjeta[1]), Integer
								.parseInt(vTarjeta[2]));
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
					strStyle = ((TarjetaCalle) (propiedad)).getColorTarjeta();
				} else {
					if (propiedad instanceof TarjetaCompania)
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
				gridPane2.add(
						crearHBoxTarjetaPropiedad(strStyle, bCrearImagen,
								rutaImagen, hbWidth, hbHeight, strToolTip),
						Integer.parseInt(vTarjeta[1]), Integer
								.parseInt(vTarjeta[2]));
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

		hbExtra.getChildren().add(new ImageView(imgCasa));
		hbExtra.getChildren().add(new Label("x " + jugador.getNroCasas()));
		hbExtra.getChildren().add(new ImageView(imgHotel));
		hbExtra.getChildren().add(new Label("x " + jugador.getNroHoteles()));
		hbExtra.getChildren().add(new ImageView(imgCarcel));
		hbExtra.getChildren().add(
				new Label("x " + jugador.getTarjetaCarcelList().size()));

		TitledPane tpInfoPlayer = new TitledPane(title, scroll);
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
	 * 
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

		acoplarAContenedor(vBox, 0);
		root.getStyleClass().add("bg_info_panel");
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

		acoplarAContenedor(gridPane1, 0);
		acoplarAContenedor(gridPane2, 0);

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
							strStyle = ((TarjetaCalle) (propiedad)).getColorTarjeta();
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
										rutaImagen, hbWidth, hbHeight, strToolTip),
								Integer.parseInt(vTarjeta[1]), Integer
										.parseInt(vTarjeta[2]));
					}
				}
				
				//Tarjetas para el gridpane2
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
							strStyle = ((TarjetaCalle) (propiedad)).getColorTarjeta();
						} else {
							if (propiedad instanceof TarjetaCompania)
								strStyle = "blanco";
							else
								strStyle = "negro";
						}
						if (propiedad.getJugador() == null) {
							bCrearImagen = true;
								rutaImagen = propiedad.getPathImagenFrente();
							strToolTip = showToolTipsPropiedad(propiedad);
						}
						gridPane2.add(
								crearHBoxTarjetaPropiedad(strStyle, bCrearImagen,
										rutaImagen, hbWidth, hbHeight, strToolTip),
								Integer.parseInt(vTarjeta[1]), Integer
										.parseInt(vTarjeta[2]));
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

		TitledPane tp = new TitledPane(title, scroll);
		tp.setId("tp_banco");
		tp.setCollapsible(true);
		return tp;
	}

	private HBox crearHBoxTarjetaPropiedad(String style, boolean creaImagen,
			String rutaImagen, Double hbWidth, Double hbHeight, String toolTips) {
		HBox hBox_inner = new HBox();
		Image imgPropiedad;
		Tooltip tpImagen;

		hBox_inner.getStyleClass().add("border_" + style.toLowerCase());
		hBox_inner.setPrefSize(hbWidth, hbHeight);

		if (creaImagen) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(rutaImagen),
					hbWidth, hbHeight, false, false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(toolTips);
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(rutaImagen),
					70, 120, false, false);
			tpImagen.setGraphic(new ImageView(imgPropiedad));
			
			Tooltip.install(hBox_inner, tpImagen);			
		}
		return hBox_inner;
	}

	private void acoplarAContenedor(javafx.scene.Node node, double valor) {
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
			tooltip += "(Hipotecada)";
		tooltip += " - "
				+ StringUtils.decimalFormat.format(propiedad
						.getValorPropiedad());
		return tooltip;
	}

	private ScrollPane makeScrollable(final AnchorPane node) {
	    final ScrollPane scroll = new ScrollPane();
	    scroll.setContent(node);
	    scroll.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
	      @Override public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
	        node.setPrefWidth(bounds.getWidth());
	      }
	    });
	    return scroll;
	  }
	// ======================================================================//
	// ============================== Event Fx ==============================//
	// ======================================================================//

	@FXML
	void processMenu(ActionEvent event) {

	}

	@FXML
	void processAcciones(ActionEvent event) {

	}

	@FXML
	void processContruir(ActionEvent event) {

	}

	@FXML
	void processVender(ActionEvent event) {

	}

	@FXML
	void processHipotecar(ActionEvent event) {

	}

	@FXML
	void processDeshipotecar(ActionEvent event) {

	}

	@FXML
	void processComercializar(ActionEvent event) {

	}

	@FXML
	void processSendMessage(ActionEvent event) {

	}

	// ======================================================================//
	// ========================== Getter & Setter ===========================//
	// ======================================================================//

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

	public MonopolyGameStatus getStatus() {
		return status;
	}

}
