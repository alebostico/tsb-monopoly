/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import monopoly.client.connection.ConnectionController;
import monopoly.model.Dado;
import monopoly.model.MonopolyGameStatus;
import monopoly.util.message.game.StartGameMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class TirarDadosController extends AnchorPane implements Initializable {

	@FXML
	private Label lblNombre;

	private Button btnTirarDados;

	private Button btnAceptar;

	@FXML
	private HBox hbPanel;

	@FXML
	private VBox vbPanel;

	private Stage currentStage;

	private Dado dados;
	
	private MonopolyGameStatus status;

	private static TirarDadosController instance;

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
	}

	public void showEstablecerTurno(final String username) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (lblNombre != null)
					lblNombre.setText(username);
				btnTirarDados = new Button("Tirar Dados");
				btnTirarDados.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						// devolver el valor de los dados
						TirarDadosController.getInstance()
								.tirarDadoParaTurnos();
					}
				});
				hbPanel.getChildren().add(btnTirarDados);
				currentStage.show();
			}
		});
	}

	public void tirarDadoParaTurnos() {
		String path;
		dados = new Dado();
		hbPanel.getChildren().clear();

		path = Dado.getPathImageDado(dados.getValorDado(1));
		Image img = new Image(
				TirarDadosController.class.getResourceAsStream(path), 50, 50,
				true, true);
		ImageView imgDado1 = new ImageView(img);

		path = Dado.getPathImageDado(dados.getValorDado(2));
		img = new Image(TirarDadosController.class.getResourceAsStream(path),
				50, 50, true, true);
		ImageView imgDado2 = new ImageView(img);

		hbPanel.getChildren().add(imgDado1);
		hbPanel.getChildren().add(new Label(" + "));
		hbPanel.getChildren().add(imgDado2);
		hbPanel.getChildren().add(new Label(" = "));
		hbPanel.getChildren().add(new Label(" " + dados.getSuma() + " "));

		TableroController.getInstance().addHistoryGame(TableroController.getInstance().getUsuarioLogueado().getUserName(), "El resultado de los dados para establecer su turno ha sido " + dados.getSuma());
		
		btnAceptar = new Button("Aceptar");
		btnAceptar.setDisable(true);
		btnAceptar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// devolver el valor de los dados
				TableroController.getInstance().empezarJuego(TirarDadosController.getInstance().getStatus());
			}
		});
		vbPanel.getChildren().add(btnAceptar);
		int senderId = ConnectionController.getInstance().getIdPlayer();
		Object[] vDatos = new Object[3];
		vDatos[0] = TableroController.getInstance().getJuego().getUniqueID();
		vDatos[1] = dados;
		ConnectionController.getInstance().send(new StartGameMessage(senderId, vDatos));
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
	 * @return the status
	 */
	public MonopolyGameStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(MonopolyGameStatus status) {
		this.status = status;
	}

	/**
	 * @return the instance
	 */
	public static TirarDadosController getInstance() {
		if (instance == null)
			instance = new TirarDadosController();
		return instance;
	}

	public void habilitarBotonAceptar(MonopolyGameStatus status) {
		this.status = status;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (btnAceptar != null) {
					btnAceptar.setDisable(false);
				}
			}
		});
	}
}
