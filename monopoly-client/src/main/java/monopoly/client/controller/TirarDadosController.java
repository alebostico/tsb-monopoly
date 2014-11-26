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
import monopoly.model.History;
import monopoly.util.StringUtils;
import monopoly.util.constantes.EnumsTirarDados;
import monopoly.util.message.game.AdvanceInBoardMessage;
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

	public void showTirarDados(final String username,
			final EnumsTirarDados.TirarDados modo) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (lblNombre != null)
					lblNombre.setText(username);
				btnTirarDados = new Button("Tirar Dados");
				switch (modo) {
				case TURNO_INICIO:
					btnTirarDados.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							// devolver el valor de los dados
							TirarDadosController.getInstance()
									.tirarDados(modo);
						}
					});
					break;
				case AVANZAR_CASILLERO:
					btnTirarDados.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							// avanzar de casillero
							
						}
					});
					break;
				}
				hbPanel.getChildren().add(btnTirarDados);
				currentStage.show();
			}
		});
	}

	public void tirarDados(final EnumsTirarDados.TirarDados modo) {
		String path;
		Image img;
		ImageView imgDado1;
		ImageView imgDado2;
		History history;
		int senderId;
		String mensaje = "";
		
		dados = new Dado();
		hbPanel.getChildren().clear();

		path = Dado.getPathImageDado(dados.getValorDado(1));
		img = new Image(
				TirarDadosController.class.getResourceAsStream(path), 50, 50,
				true, true);
		imgDado1 = new ImageView(img);

		path = Dado.getPathImageDado(dados.getValorDado(2));
		img = new Image(TirarDadosController.class.getResourceAsStream(path),
				50, 50, true, true);
		imgDado2 = new ImageView(img);

		hbPanel.getChildren().add(imgDado1);
		hbPanel.getChildren().add(new Label(" + "));
		hbPanel.getChildren().add(imgDado2);
		hbPanel.getChildren().add(new Label(" = "));
		hbPanel.getChildren().add(new Label(" " + dados.getSuma() + " "));

		senderId = ConnectionController.getInstance().getIdPlayer();
		btnAceptar = new Button("Aceptar");
		btnAceptar.setDisable(true);
		
		switch(modo)
		{
		case TURNO_INICIO:
			mensaje = String.format("El resultado de los dados para establecer su turno fue %s.", dados.getSuma());
			
			btnAceptar.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					TableroController.getInstance().empezarJuego();
				}
			});
			
			ConnectionController.getInstance().send(
					new StartGameMessage(senderId, TableroController.getInstance().getJuego().getUniqueID(), dados));
			
			break;
			
		case AVANZAR_CASILLERO:
			mensaje = String.format("El resultado de los dados para avanzar de casillero fue %s.", dados.getSuma());
			btnAceptar.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					TableroController.getInstance().determinarAccionEnCasillero();
				}
			});
			ConnectionController.getInstance().send(
					new AdvanceInBoardMessage(senderId, dados));
			
			break;
		}
		
		history = new History(StringUtils.getFechaActual(),
				TableroController.getInstance().getUsuarioLogueado()
						.getUserName(),mensaje);
		TableroController.getInstance().addHistoryGame(history);
		
		vbPanel.getChildren().add(btnAceptar);
		
	}

	public void habilitarBotonAceptar() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (btnAceptar != null) {
					btnAceptar.setDisable(false);
				}
			}
		});
	}

	public Stage getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

	public static TirarDadosController getInstance() {
		if (instance == null)
			instance = new TirarDadosController();
		return instance;
	}

}
