/**
 * 
 */
package monopoly.client.controller;

import java.io.Serializable;
import java.net.URL;
import java.text.DateFormat;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import monopoly.client.connection.ConnectionController;
import monopoly.client.util.ScreensFramework;
import monopoly.model.History;
import monopoly.model.Juego;
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
	private Button btnMenu;

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

	private void addHistoryGame(String usuario, String mensaje) {
		Calendar calendar = GregorianCalendar.getInstance();
		History history = new History(dateFormat.format(calendar.getTime()),
				usuario, mensaje);
		historyGameList.add(history);
		oHistoryGameList = FXCollections.observableArrayList(historyGameList);
		lvHistory.setItems(oHistoryGameList);
	}

	@SuppressWarnings("unused")
	private void addHistoryChat(String usuario, String mensaje) {
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

	public void startGame() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {

					String fxml = ConstantesFXML.FXML_TIRAR_DADOS;
					Parent root;

					Stage tirarDadosStage = new Stage();
					FXMLLoader loader = ScreensFramework.getLoader(fxml);

					root = (Parent) loader.load();

					Scene scene = new Scene(root);
					tirarDadosStage.setScene(scene);
					tirarDadosStage
							.setTitle("Monopoly - Tirar Dados para establecer turnos");
					tirarDadosStage.centerOnScreen();
					tirarDadosStage.setResizable(false);
					tirarDadosStage.initModality(Modality.APPLICATION_MODAL);
					tirarDadosStage.initStyle(StageStyle.UNDECORATED);

					SplashController.getInstance().getCurrentStage().close();
					tirarDadosStage.show();

				} catch (Exception ex) {
					// TODO Auto-generated catch block
					GestorLogs.registrarError(ex.getMessage());
				}
			}
		});
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
