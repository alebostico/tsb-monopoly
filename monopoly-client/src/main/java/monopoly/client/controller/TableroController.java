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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
import monopoly.model.History;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.JugadorHumano;
import monopoly.model.MonopolyGameStatus;
import monopoly.model.Usuario;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;
import monopoly.util.StringUtils;
import monopoly.util.constantes.ConstantesFXML;

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
	public void addHistoryGame(History history) {
		historyGameList.add(history);
		oHistoryGameList = FXCollections.observableArrayList(historyGameList);
		lvHistory.setItems(oHistoryGameList);
	}

	/**
	 * 
	 * Método que agrega un mensaje de chat
	 * al panel de Chat.
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
	 * Método que agrega un mensaje de chat
	 * al panel de Chat.
	 * 
	 * @param history
	 * 			objeto que contiene información
	 * 			sobre el usuario que escribió el mensaje,
	 * 			el mensaje y la fecha y hora.
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
	public void tirarDadosParaTurno() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {

					String fxml = ConstantesFXML.FXML_TIRAR_DADOS;
					Parent root;
					TirarDadosController controller;

					Stage tirarDadosStage = new Stage();
					FXMLLoader loader = ScreensFramework.getLoader(fxml);

					root = (Parent) loader.load();
					controller = (TirarDadosController) loader.getController();

					Scene scene = new Scene(root);
					tirarDadosStage.setScene(scene);
					tirarDadosStage
							.setTitle("Monopoly - Tirar Dados para establecer turnos");
					tirarDadosStage.centerOnScreen();
					tirarDadosStage.setResizable(false);
					tirarDadosStage.initModality(Modality.APPLICATION_MODAL);
					tirarDadosStage.initStyle(StageStyle.UNDECORATED);

					SplashController.getInstance().getCurrentStage().close();
					controller.setCurrentStage(tirarDadosStage);

					controller.showEstablecerTurno(TableroController
							.getInstance().getUsuarioLogueado().getNombre());

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
	 * @param status
	 *            objeto que contiene información sobre los turnos, estado del
	 *            banco y jugador actual.
	 */
	public void empezarJuego(MonopolyGameStatus status) {
		this.status = status;
		showAccordionJugadores();
		displayFichas();
		TirarDadosController.getInstance().getCurrentStage().close();
	}

	/**
	 * Método que dibuja a los jugadores, mostrando el estado en el juego.
	 * 
	 */
	private void showAccordionJugadores() {
		List<Jugador> turnos = status.turnos;
		tps = new TitledPane[turnos.size() + 1];
		String title;

		for (int i = 0; i < turnos.size(); i++) {
			title = turnos.get(i).getNombre() + " - ";
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
	 */
	private void displayFichas() {
		List<Jugador> turnos = status.turnos;
		// Tablero tablero = status.tablero;
		Image img;

		for (Jugador j : turnos) {
			switch (j.getCasilleroActual().getNumeroCasillero()) {
			case 1:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero01.getChildren().add(new ImageView(img));
				break;

			case 2:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero02.getChildren().add(new ImageView(img));
				break;

			case 3:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero03.getChildren().add(new ImageView(img));
				break;

			case 4:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero04.getChildren().add(new ImageView(img));
				break;

			case 5:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero05.getChildren().add(new ImageView(img));
				break;

			case 6:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero06.getChildren().add(new ImageView(img));
				break;

			case 7:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero07.getChildren().add(new ImageView(img));
				break;

			case 8:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero08.getChildren().add(new ImageView(img));
				break;

			case 9:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero01.getChildren().add(new ImageView(img));
				break;

			case 10:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero10.getChildren().add(new ImageView(img));
				break;

			case 11:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero11.getChildren().add(new ImageView(img));
				break;

			case 12:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero12.getChildren().add(new ImageView(img));
				break;
			case 13:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero13.getChildren().add(new ImageView(img));
				break;
			case 14:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero14.getChildren().add(new ImageView(img));
				break;
			case 15:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero15.getChildren().add(new ImageView(img));
				break;
			case 16:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero16.getChildren().add(new ImageView(img));
				break;
			case 17:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero17.getChildren().add(new ImageView(img));
				break;
			case 18:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero18.getChildren().add(new ImageView(img));
				break;
			case 19:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero19.getChildren().add(new ImageView(img));
				break;

			case 20:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero20.getChildren().add(new ImageView(img));
				break;
			case 21:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero21.getChildren().add(new ImageView(img));
				break;
			case 22:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero22.getChildren().add(new ImageView(img));
				break;
			case 23:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero23.getChildren().add(new ImageView(img));
				break;
			case 24:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero24.getChildren().add(new ImageView(img));
				break;
			case 25:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero25.getChildren().add(new ImageView(img));
				break;
			case 26:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero26.getChildren().add(new ImageView(img));
				break;
			case 27:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero27.getChildren().add(new ImageView(img));
				break;
			case 28:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero28.getChildren().add(new ImageView(img));
				break;
			case 29:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero29.getChildren().add(new ImageView(img));
				break;

			case 30:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero30.getChildren().add(new ImageView(img));
				break;
			case 31:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero31.getChildren().add(new ImageView(img));
				break;
			case 32:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero32.getChildren().add(new ImageView(img));
				break;
			case 33:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero33.getChildren().add(new ImageView(img));
				break;
			case 34:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero34.getChildren().add(new ImageView(img));
				break;
			case 35:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero35.getChildren().add(new ImageView(img));
				break;
			case 36:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero36.getChildren().add(new ImageView(img));
				break;
			case 37:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero37.getChildren().add(new ImageView(img));
				break;
			case 38:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero38.getChildren().add(new ImageView(img));
				break;
			case 39:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero39.getChildren().add(new ImageView(img));
				break;
			case 40:
				img = new Image(TableroController.class.getResourceAsStream(j
						.getFicha().getPathImgSmall()), 25, 25, true, true);
				pCasillero40.getChildren().add(new ImageView(img));
				break;

			default:
				break;
			}
		}
	}

	/**
	 * 
	 * Dibuje el TitledPane con la información
	 * actual del jugador.
	 * 
	 * @param jugador
	 * @param title
	 * @return
	 */
	private TitledPane getPaneInfoPlayer(Jugador jugador, String title) {
		AnchorPane root = new AnchorPane();
		VBox vBox = new VBox();
		HBox hbInfoJugador = new HBox();
		HBox hbPropiedades = new HBox();
		HBox hbExtra = new HBox();

		AnchorPane.setLeftAnchor(vBox, (double) 0);
		AnchorPane.setRightAnchor(vBox, (double) 0);
		AnchorPane.setTopAnchor(vBox, (double) 0);
		AnchorPane.setBottomAnchor(vBox, (double) 0);
		root.getStyleClass().add("bg_info_panel");
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);

		hbInfoJugador.setAlignment(Pos.CENTER);
		hbInfoJugador.setSpacing(25);

		hbPropiedades.setAlignment(Pos.CENTER);
		hbPropiedades.setSpacing(20);

		hbExtra.setAlignment(Pos.CENTER);
		hbExtra.setSpacing(20);

		vBox.getChildren().add(hbInfoJugador);
		vBox.getChildren().add(hbPropiedades);
		vBox.getChildren().add(hbExtra);
		root.getChildren().add(vBox);

		VBox pImgFicha = new VBox();
		AnchorPane.setLeftAnchor(pImgFicha, (double) 0);
		AnchorPane.setRightAnchor(pImgFicha, (double) 0);
		AnchorPane.setTopAnchor(pImgFicha, (double) 0);
		AnchorPane.setBottomAnchor(pImgFicha, (double) 0);
		pImgFicha.setAlignment(Pos.CENTER);

		pImgFicha.getStyleClass().add("bg_info_ficha");
		pImgFicha.setPrefSize((double) 60, (double) 60);
		Image img = new Image(
				TableroController.class.getResourceAsStream(jugador.getFicha()
						.getPathImgBig()), 40, 40, true, true);
		ImageView ivFicha = new ImageView(img);

		pImgFicha.getChildren().add(ivFicha);

		hbInfoJugador.getChildren().add(pImgFicha);
		hbInfoJugador.getChildren().add(new Label(jugador.getNombre()));
		hbInfoJugador.getChildren().add(
				new Label(StringUtils.FORMATO_IMPORTE.format(jugador
						.getDinero())));

		if (status.currentPlayer.getNombre().equals(jugador.getNombre())) {
			img = new Image(
					TableroController.class
							.getResourceAsStream("/images/dados/dice.png"),
					40, 40, true, true);
			ivFicha = new ImageView(img);

			hbInfoJugador.getChildren().add(ivFicha);
		}

		// ===================== HBox de propiedades =====================//

		TarjetaPropiedad propiedad;
		Image imgPropiedad;
		HBox hBox_inner;
		Tooltip tpImagen;
		double hbWidth = 35;
		double hbHeight = 60;

		GridPane gridPane1 = new GridPane();
		GridPane gridPane2 = new GridPane();

		AnchorPane.setLeftAnchor(gridPane1, (double) 0);
		AnchorPane.setRightAnchor(gridPane1, (double) 0);
		AnchorPane.setTopAnchor(gridPane1, (double) 0);
		AnchorPane.setBottomAnchor(gridPane1, (double) 0);

		AnchorPane.setLeftAnchor(gridPane2, (double) 0);
		AnchorPane.setRightAnchor(gridPane2, (double) 0);
		AnchorPane.setTopAnchor(gridPane2, (double) 0);
		AnchorPane.setBottomAnchor(gridPane2, (double) 0);

		gridPane1.setHgap(5);
		gridPane1.setVgap(10);

		gridPane2.setHgap(5);
		gridPane2.setVgap(10);

		// ----- tarjeta02 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_marron");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta02");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane1.add(hBox_inner, 0, 0);

		// ----- tarjeta04 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_marron");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta04");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane1.add(hBox_inner, 1, 0);

		// ----- tarjeta07 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_celeste");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta07");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane1.add(hBox_inner, 0, 1);

		// ----- tarjeta09 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_celeste");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta09");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane1.add(hBox_inner, 1, 1);

		// ----- tarjeta10 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_celeste");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta10");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane1.add(hBox_inner, 2, 1);

		// ----- tarjeta12 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_fuczia");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta12");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane1.add(hBox_inner, 0, 2);

		// ----- tarjeta14 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_fuczia");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta14");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane1.add(hBox_inner, 1, 2);

		// ----- tarjeta15 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_fuczia");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta15");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane1.add(hBox_inner, 2, 2);

		// ----- tarjeta17 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_naranja");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta17");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane1.add(hBox_inner, 0, 3);

		// ----- tarjeta19 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_naranja");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta19");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane1.add(hBox_inner, 1, 3);

		// ----- tarjeta20 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_naranja");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta20");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane1.add(hBox_inner, 2, 3);

		// ----- tarjeta22 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_rojo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta22");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane1.add(hBox_inner, 0, 4);

		// ----- tarjeta24 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_rojo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta24");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane1.add(hBox_inner, 1, 4);

		// ----- tarjeta25 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_rojo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta25");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane1.add(hBox_inner, 2, 4);

		// ----- tarjeta27 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_amarillo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta27");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane2.add(hBox_inner, 0, 0);

		// ----- tarjeta28 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_amarillo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta28");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane2.add(hBox_inner, 1, 0);

		// ----- tarjeta30 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_amarillo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta30");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane2.add(hBox_inner, 2, 0);

		// ----- tarjeta32 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_verde");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta32");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane2.add(hBox_inner, 0, 1);

		// ----- tarjeta33 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_verde");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta33");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane2.add(hBox_inner, 1, 1);

		// ----- tarjeta35 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_verde");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta35");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane2.add(hBox_inner, 2, 1);

		// ----- tarjeta38 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_azul");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta38");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane2.add(hBox_inner, 0, 2);

		// ----- tarjeta40 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_azul");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta40");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane2.add(hBox_inner, 1, 2);

		// ----- tarjeta13 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_blanco");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta13");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane2.add(hBox_inner, 0, 3);

		// ----- tarjeta29 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_blanco");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta29");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane2.add(hBox_inner, 1, 3);

		// ----- tarjeta06 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_negro");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta06");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane2.add(hBox_inner, 0, 4);

		// ----- tarjeta16 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_negro");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta16");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane2.add(hBox_inner, 1, 4);

		// ----- tarjeta26 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_negro");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta26");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane2.add(hBox_inner, 2, 4);

		// ----- tarjeta36 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_negro");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta36");
		if (jugador.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
		}
		gridPane2.add(hBox_inner, 3, 4);

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

		TitledPane tp = new TitledPane(title, root);
		tp.setId("tp_" + jugador.getNombre());
		tp.setCollapsible(true);
		return tp;
	}

	/**
	 * 
	 * Dibuja el TitledPan con la información
	 * actual del banco.
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

		AnchorPane.setLeftAnchor(vBox, (double) 0);
		AnchorPane.setRightAnchor(vBox, (double) 0);
		AnchorPane.setTopAnchor(vBox, (double) 0);
		AnchorPane.setBottomAnchor(vBox, (double) 0);
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

		// ===================== HBox de propiedades =====================//

		TarjetaPropiedad propiedad;
		Image imgPropiedad;
		HBox hBox_inner;
		Tooltip tpImagen;
		double hbWidth = 35;
		double hbHeight = 60;

		GridPane gridPane1 = new GridPane();
		GridPane gridPane2 = new GridPane();

		AnchorPane.setLeftAnchor(gridPane1, (double) 0);
		AnchorPane.setRightAnchor(gridPane1, (double) 0);
		AnchorPane.setTopAnchor(gridPane1, (double) 0);
		AnchorPane.setBottomAnchor(gridPane1, (double) 0);

		AnchorPane.setLeftAnchor(gridPane2, (double) 0);
		AnchorPane.setRightAnchor(gridPane2, (double) 0);
		AnchorPane.setTopAnchor(gridPane2, (double) 0);
		AnchorPane.setBottomAnchor(gridPane2, (double) 0);

		gridPane1.setHgap(5);
		gridPane1.setVgap(10);

		gridPane2.setHgap(5);
		gridPane2.setVgap(10);

		// ----- tarjeta02 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_marron");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta02");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane1.add(hBox_inner, 0, 0);

		// ----- tarjeta04 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_marron");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta04");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane1.add(hBox_inner, 1, 0);

		// ----- tarjeta07 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_celeste");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta07");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane1.add(hBox_inner, 0, 1);

		// ----- tarjeta09 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_celeste");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta09");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane1.add(hBox_inner, 1, 1);

		// ----- tarjeta10 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_celeste");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta10");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane1.add(hBox_inner, 2, 1);

		// ----- tarjeta12 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_fuczia");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta12");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane1.add(hBox_inner, 0, 2);

		// ----- tarjeta14 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_fuczia");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta14");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane1.add(hBox_inner, 1, 2);

		// ----- tarjeta15 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_fuczia");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta15");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane1.add(hBox_inner, 2, 2);

		// ----- tarjeta17 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_naranja");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta17");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane1.add(hBox_inner, 0, 3);

		// ----- tarjeta19 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_naranja");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta19");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane1.add(hBox_inner, 1, 3);

		// ----- tarjeta20 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_naranja");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta20");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane1.add(hBox_inner, 2, 3);

		// ----- tarjeta22 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_rojo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta22");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane1.add(hBox_inner, 0, 4);

		// ----- tarjeta24 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_rojo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta24");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane1.add(hBox_inner, 1, 4);

		// ----- tarjeta25 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_rojo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta25");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane1.add(hBox_inner, 2, 4);

		// ----- tarjeta27 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_amarillo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta27");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane2.add(hBox_inner, 0, 0);

		// ----- tarjeta28 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_amarillo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta28");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane2.add(hBox_inner, 1, 0);

		// ----- tarjeta30 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_amarillo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta30");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane2.add(hBox_inner, 2, 0);

		// ----- tarjeta32 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_verde");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta32");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane2.add(hBox_inner, 0, 1);

		// ----- tarjeta33 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_verde");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta33");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane2.add(hBox_inner, 1, 1);

		// ----- tarjeta35 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_verde");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta35");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane2.add(hBox_inner, 2, 1);

		// ----- tarjeta38 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_azul");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta38");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane2.add(hBox_inner, 0, 2);

		// ----- tarjeta40 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_azul");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta40");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane2.add(hBox_inner, 1, 2);

		// ----- tarjeta13 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_blanco");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta13");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane2.add(hBox_inner, 0, 3);

		// ----- tarjeta29 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_blanco");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta29");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane2.add(hBox_inner, 1, 3);

		// ----- tarjeta06 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_negro");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta06");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane2.add(hBox_inner, 0, 4);

		// ----- tarjeta16 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_negro");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta16");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane2.add(hBox_inner, 1, 4);

		// ----- tarjeta26 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_negro");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta26");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane2.add(hBox_inner, 2, 4);

		// ----- tarjeta36 --------//
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_negro");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		propiedad = juego.getTarjetasPropiedad("tarjeta36");
		if (banco.getTarjPropiedadList().contains(propiedad)) {
			imgPropiedad = new Image(
					TableroController.class.getResourceAsStream(propiedad
							.getNombreImagen()), hbWidth, hbHeight, false,
					false);
			tpImagen = new Tooltip(showToolTipsPropiedad(propiedad));
			Tooltip.install(hBox_inner, tpImagen);
			hBox_inner.getChildren().add(new ImageView(imgPropiedad));
		}
		gridPane2.add(hBox_inner, 3, 4);

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

		TitledPane tp = new TitledPane(title, root);
		tp.setId("tp_banco");
		tp.setCollapsible(true);
		return tp;
	}

	/**
	 * Método para agregar un tooltips a la imagen
	 * de la propiedad con información sobre la misma.
	 * 
	 * @param propiedad
	 * @return
	 */
	private String showToolTipsPropiedad(TarjetaPropiedad propiedad) {
		return propiedad.getNombre()
				+ " - "
				+ StringUtils.FORMATO_IMPORTE.format(propiedad
						.getValorPropiedad());
	}

	
	
	//============================= Event Fx ==============================//
	
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
    
    //============================== Getter & Setter ===========================//

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

}
