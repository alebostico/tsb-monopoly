/**
 * 
 */
package monopoly.client.controller;

import java.io.Serializable;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import monopoly.client.connection.ConnectionController;
import monopoly.client.util.ScreensFramework;
import monopoly.model.History;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.JugadorHumano;
import monopoly.model.MonopolyGameStatus;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
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
	private Button btnHipotecar;

	@FXML
	private ListView<History> lvHistory;

	private List<History> historyGameList;
	private ObservableList<History> oHistoryGameList;

	@FXML
	private Pane pCasillero10;

	@FXML
	private Pane pCasillero12;

	@FXML
	private Pane pCasillero11;

	@FXML
	private Pane pCasillero02;

	@FXML
	private Pane pCasillero05;

	@FXML
	private Pane pCasillero04;

	@FXML
	private Pane pCasillero07;

	@FXML
	private Pane pCasillero06;

	@FXML
	private Pane pCasillero09;

	@FXML
	private Pane pCasillero08;

	@FXML
	private Button btnComerciar;

	@FXML
	private MenuButton btnMenu;

	@FXML
	private ListView<History> lvHistoryChat;

	private List<History> historyChatList;
	private ObservableList<History> oHistoryChatList;

	@FXML
	private Pane pCasillero40;

	@FXML
	private Pane pCasillero01;

	@FXML
	private TextArea txtMessageChat;

	@FXML
	private Pane pCasillero36;

	@FXML
	private Pane pCasillero35;

	@FXML
	private Pane pCasillero38;

	@FXML
	private Pane pCasillero37;

	@FXML
	private Pane pCasillero39;

	@FXML
	private Accordion accordionPlayers;

	@FXML
	private Accordion accordionHistorial;

	@FXML
	private Button btnDesconstruir;

	@FXML
	private Button btnDeshipotecar;

	@FXML
	private Pane pCasillero30;

	@FXML
	private Pane pCasillero32;

	@FXML
	private Pane pCasillero31;

	@FXML
	private Pane pCasillero34;

	@FXML
	private Pane pCasillero33;

	@FXML
	private Pane pCasillero25;

	@FXML
	private Pane pCasillero24;

	@FXML
	private Pane pCasillero27;

	@FXML
	private Pane pCasillero26;

	@FXML
	private Pane pCasillero29;

	@FXML
	private Pane pCasillero28;

	@FXML
	private Button btnSendMessage;

	@FXML
	private Pane pCasillero21;

	@FXML
	private Button btnConstruir;

	@FXML
	private Pane pCasillero20;

	@FXML
	private Pane pCasillero23;

	@FXML
	private Pane pCasillero22;

	@FXML
	private Pane pCasillero14;

	@FXML
	private Pane pCasillero13;

	@FXML
	private Pane pCasillero16;

	@FXML
	private Pane pCasillero15;

	@FXML
	private Pane pCasillero18;

	@FXML
	private Pane pCasillero17;

	@FXML
	private Pane pCasillero19;

	@FXML
	private TitledPane tpHistory;

	@FXML
	private TitledPane tpChat;

	@FXML
	private Label lblStopwatch;

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

	private StringProperty clockLabelTextProperty;

	private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

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

	@FXML
	void processSendMessage(ActionEvent event) {

	}

	@FXML
	void processMenu(ActionEvent event) {

	}

	@FXML
	void processContruir(ActionEvent event) {

	}

	@FXML
	void processDesconstruir(ActionEvent event) {

	}

	@FXML
	void processHipotecar(ActionEvent event) {

	}

	@FXML
	void processDeshipotecar(ActionEvent event) {

	}

	@FXML
	void processComerciar(ActionEvent event) {

	}

	public void showBoard() {
		currentStage.show();
		prevStage.close();
		currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				ConnectionController.getInstance().cerrarConexion();
			}
		});
		this.clockLabelTextProperty = lblStopwatch.textProperty();

		createDigitalClock();
		addHistoryGame(usuarioLogueado.getUserName(), "Juego Creado");
		esperarJugadores();

	}

	public void addHistoryGame(String usuario, String mensaje) {
		Calendar calendar = GregorianCalendar.getInstance();
		History history = new History(dateFormat.format(calendar.getTime()),
				usuario, mensaje);
		historyGameList.add(history);
		oHistoryGameList = FXCollections.observableArrayList(historyGameList);
		lvHistory.setItems(oHistoryGameList);
	}

	public void addHistoryChat(String usuario, String mensaje) {
		Calendar calendar = GregorianCalendar.getInstance();
		History history = new History(dateFormat.format(calendar.getTime()),
				usuario, mensaje);
		historyChatList.add(history);
		oHistoryChatList = FXCollections.observableArrayList(historyChatList);
		lvHistoryChat.setItems(oHistoryChatList);
	}

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

	public void createDigitalClock() {
		final Timeline timeline = new Timeline();

		timeline.setCycleCount(Timeline.INDEFINITE);
		final KeyFrame kf = new KeyFrame(Duration.seconds(1),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						Calendar calendar = GregorianCalendar.getInstance();
						clockLabelTextProperty.setValue(dateFormat
								.format(calendar.getTime()));
					}
				});
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

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

	public void empezarJuego(MonopolyGameStatus status) {
		showAccordionJugadores(status.turnos);
		displayFichasInicio(status.turnos);
		TirarDadosController.getInstance().getCurrentStage().close();
	}

	private void showAccordionJugadores(List<Jugador> turnos) {
		tps = new TitledPane[turnos.size() + 1];
		String title;

		for (int i = 0; i < turnos.size(); i++) {
			title = turnos.get(i).getNombre() + " - ";
			title += (turnos.get(i) instanceof JugadorHumano) ? "Jugador Humano"
					: "Jugador Virtual";
			tps[i] = getPaneInfoPlayer(turnos.get(i), title);
		}
		tps[turnos.size()] = new TitledPane("Banco", new Pane());

		accordionPlayers.getPanes().addAll(tps);
		accordionPlayers.setExpandedPane(tps[0]);
	}

	private void displayFichasInicio(List<Jugador> turnos) {
		GridPane grid = new GridPane();
		grid.setHgap(5);
		grid.setVgap(5);

		Image img;

		int row = 0;
		int col = 0;
		for (int i = 0; i < turnos.size(); i++) {
			img = new Image(TableroController.class.getResourceAsStream(turnos
					.get(i).getFicha().getPathImgSmall()), 25, 25, true, true);
			grid.add(new ImageView(img), col, row);

			col++;

			if (col > 1) {
				row++;
				col = 0;
			}
		}
		AnchorPane.setLeftAnchor(grid, (double) 0);
		AnchorPane.setRightAnchor(grid, (double) 0);
		AnchorPane.setTopAnchor(grid, (double) 0);
		AnchorPane.setBottomAnchor(grid, (double) 0);

		pCasillero01.getChildren().add(grid);
	}

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
		vBox.getStyleClass().add("bg_info_panel");
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);

		hbInfoJugador.setAlignment(Pos.CENTER);
		hbInfoJugador.setSpacing(20);

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
		pImgFicha.setMinSize((double) 75, (double) 75);
		pImgFicha.setMaxSize((double) 75, (double) 75);
		Image img = new Image(
				TableroController.class.getResourceAsStream(jugador.getFicha()
						.getPathImgBig()), 40, 40, true, true);
		ImageView ivFicha = new ImageView(img);

		pImgFicha.getChildren().add(ivFicha);

		NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();

		hbInfoJugador.getChildren().add(pImgFicha);
		hbInfoJugador.getChildren().add(new Label(jugador.getNombre()));
		hbInfoJugador.getChildren().add(
				new Label(formatoImporte.format(jugador.getDinero())));

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

		// Image bgPropiedad = new Image(
		// TableroController.class
		// .getResourceAsStream("/images/background/bg_propiedad.png"),
		// 40, 60, false, false);
		
		double hbWidth = 35;
		double hbHeight = 60;

		HBox hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_marron");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane1.add(hBox_inner, 0, 0);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_marron");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane1.add(hBox_inner, 1, 0);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_celeste");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane1.add(hBox_inner, 0, 1);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_celeste");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane1.add(hBox_inner, 1, 1);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_celeste");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane1.add(hBox_inner, 2, 1);
		
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_fuczia");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane1.add(hBox_inner, 0, 2);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_fuczia");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane1.add(hBox_inner, 1, 2);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_fuczia");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane1.add(hBox_inner, 2, 2);
		
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_naranja");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane1.add(hBox_inner, 0, 3);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_naranja");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane1.add(hBox_inner, 1, 3);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_naranja");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane1.add(hBox_inner, 2, 3);
		
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_rojo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane1.add(hBox_inner, 0, 4);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_rojo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane1.add(hBox_inner, 1, 4);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_rojo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane1.add(hBox_inner, 2, 4);
		
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_amarillo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane2.add(hBox_inner, 0, 0);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_amarillo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane2.add(hBox_inner, 1, 0);
		
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_amarillo");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane2.add(hBox_inner, 2, 0);
		
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_verde");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane2.add(hBox_inner, 0, 1);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_verde");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane2.add(hBox_inner, 1, 1);
		
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_verde");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane2.add(hBox_inner, 2, 1);
		
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_azul");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane2.add(hBox_inner, 0, 2);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_azul");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane2.add(hBox_inner, 1, 2);
		
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_blanco");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane2.add(hBox_inner, 0, 3);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_blanco");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane2.add(hBox_inner, 1, 3);
		
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_negro");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane2.add(hBox_inner, 0, 4);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_negro");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane2.add(hBox_inner, 1, 4);
		
		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_negro");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane2.add(hBox_inner, 2, 4);

		hBox_inner = new HBox();
		hBox_inner.getStyleClass().add("border_negro");
		hBox_inner.setPrefSize(hbWidth, hbHeight);
		gridPane2.add(hBox_inner, 3, 4);

		hbPropiedades.getChildren().add(gridPane1);
		hbPropiedades.getChildren().add(gridPane2);

		TitledPane tp = new TitledPane(title, root);
		tp.setId("tp_" + jugador.getNombre());
		return tp;
	}

	/**
	 * @return the currentStage
	 */
	public Stage getCurrentStage() {
		return currentStage;
	}

	/**
	 * @param currentStage
	 *            the currentStage to set
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
	 * @param prevStage
	 *            the prevStage to set
	 */
	public void setPrevStage(Stage prevStage) {
		this.prevStage = prevStage;
	}

	public static TableroController getInstance() {
		if (instance == null)
			instance = new TableroController();
		return instance;
	}

	/**
	 * @return the juego
	 */
	public Juego getJuego() {
		return juego;
	}

	/**
	 * @param juego
	 *            the juego to set
	 */
	public void setJuego(Juego juego) {
		this.juego = juego;
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

}
