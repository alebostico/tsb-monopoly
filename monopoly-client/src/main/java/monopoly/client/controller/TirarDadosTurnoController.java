/**
 * 
 */
package monopoly.client.controller;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import monopoly.client.connection.ConnectionController;
import monopoly.model.Dado;
import monopoly.model.History;
import monopoly.util.GestorLogs;
import monopoly.util.StringUtils;
import monopoly.util.message.game.StartGameMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class TirarDadosTurnoController implements Initializable, Serializable {

	private static final long serialVersionUID = 5580949166994505501L;

	@FXML
	private Label lblNombre;

	@FXML
	private HBox hbPanel;

	@FXML
	private VBox vbPanel;

	@FXML
	private Button btnTirarDados;

	@FXML
	private Stage currentStage;

	private Dado dados;

	private static TirarDadosTurnoController instance;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
	}

	@FXML
	void processTirarDados(ActionEvent event) {
		String path;
		Image img;
		ImageView imgDado1;
		ImageView imgDado2;
		History history;
		String mensaje = "";

		try {
			dados = new Dado();
			hbPanel.getChildren().clear();

			path = Dado.getPathImageDado(dados.getValorDado(1));
			img = new Image(
					TirarDadosController.class.getResourceAsStream(path), 50,
					50, true, true);
			imgDado1 = new ImageView(img);

			path = Dado.getPathImageDado(dados.getValorDado(2));
			img = new Image(
					TirarDadosController.class.getResourceAsStream(path), 50,
					50, true, true);
			imgDado2 = new ImageView(img);

			hbPanel.getChildren().add(imgDado1);
			hbPanel.getChildren().add(new Label(" + "));
			hbPanel.getChildren().add(imgDado2);
			hbPanel.getChildren().add(new Label(" = "));
			hbPanel.getChildren().add(new Label(" " + dados.getSuma() + " "));

			btnTirarDados = new Button("Aceptar");
			btnTirarDados.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					ConnectionController.getInstance().send(
							new StartGameMessage(TableroController.getInstance()
									.getJuego().getUniqueID(), dados));
					if (TirarDadosTurnoController.getInstance() != null)
						TirarDadosTurnoController.getInstance()
								.getCurrentStage().close();
				}
			});
			
			vbPanel.getChildren().add(btnTirarDados);

			mensaje = String
					.format("El resultado de los dados para establecer su turno fue %s.",
							dados.getSuma());

			history = new History(StringUtils.getFechaActual(),
					TableroController.getInstance().getUsuarioLogueado()
							.getUserName(), mensaje);
			TableroController.getInstance().addHistoryGame(history);

		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(ex.getMessage());
			alert.setTitle("Error...");
			alert.showAndWait();
		}
	}

	public void settearDatos(String username) {
		if (lblNombre != null)
			lblNombre.setText(username);
	}

	public Stage getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

	public static TirarDadosTurnoController getInstance() {
		if (instance == null)
			instance = new TirarDadosTurnoController();
		return instance;
	}

}
