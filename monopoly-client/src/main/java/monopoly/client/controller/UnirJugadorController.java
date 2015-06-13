/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import monopoly.client.connection.ConnectionController;
import monopoly.client.util.ScreensFramework;
import monopoly.model.Ficha;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.JugadorHumano;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.ConstantesFXML;
import monopoly.util.message.game.JoinGameMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class UnirJugadorController extends AnchorPane implements Initializable {

	@FXML
	private ImageView imgBack;

	@FXML
	private Button btnAceptar;

	@FXML
	private ImageView imgFicha;

	@FXML
	private ImageView imgNext;

	@FXML
	private Button btnCancelar;

	@FXML
	private Stage currentStage;

	@FXML
	private Stage prevStage;

	private static UnirJugadorController instance;

	private Usuario usuarioLogueado;

	private Juego juegoSelected;

	private Ficha fichaPlayer;

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
		settearActions();
	}

	@FXML
	void processCancel(ActionEvent event) {
		prevStage.show();
		currentStage.close();
	}

	@FXML
	void processJoinPlayer(ActionEvent event) {
		int senderID = ConnectionController.getInstance().getIdPlayer();
		Jugador jugador = new  JugadorHumano(usuarioLogueado.getUserName(),
											fichaPlayer, juegoSelected, null, usuarioLogueado, senderID);
		
		String fxml = ConstantesFXML.FXML_MOSTRAR_TABLERO;

		try {
			Parent root;
			Stage stage = new Stage();
			FXMLLoader loader = ScreensFramework.getLoader(fxml);

			root = (Parent) loader.load();
			TableroController controller = (TableroController) loader
					.getController();
			controller.setPrevStage(currentStage);
			controller.setJuego(juegoSelected);
			controller.setUsuarioLogueado(usuarioLogueado);

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Monopoly - Tablero");
			stage.centerOnScreen();
			controller.setCurrentStage(stage);
			controller.showTableroDeJuego();
			
			JoinGameMessage msg = new JoinGameMessage(jugador);
			ConnectionController.getInstance().send(msg);
			
			prevStage.close();
			currentStage.close();

		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
		}
	}

	private void settearActions() {
		// Método para mostrar la siguiente imagen del usuario creador del juego
		imgNext.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int indexOf = juegoSelected.getFichasPlayerList().indexOf(
						fichaPlayer);
				indexOf++;
				if (indexOf <= juegoSelected.getFichasPlayerList().size() - 1) {
					for (; indexOf < juegoSelected.getFichasPlayerList().size(); indexOf++) {
						if (!juegoSelected.getFichasPlayerList().get(indexOf)
								.isSelected()) {
							fichaPlayer.setSelected(false);
							fichaPlayer = juegoSelected.getFichasPlayerList()
									.get(indexOf);
							fichaPlayer.setSelected(true);

							Image img = new Image(
									CrearJugadoresController.class
											.getResourceAsStream(fichaPlayer
													.getPathImgBig()), 90, 87,
									true, true);
							imgFicha.setImage(img);
							imgFicha.setId("ficha_" + fichaPlayer.getIdFicha());
							return;
						}
					}
				} else
					return;
			}
		});

		// Método para mostrar la imagen anterior del usuario creador del juego
		imgBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int indexOf = juegoSelected.getFichasPlayerList().indexOf(
						fichaPlayer);
				indexOf--;
				for (; indexOf >= 0; indexOf--) {
					if (!juegoSelected.getFichasPlayerList().get(indexOf)
							.isSelected()) {
						fichaPlayer.setSelected(false);
						fichaPlayer = juegoSelected.getFichasPlayerList().get(
								indexOf - 1);
						fichaPlayer.setSelected(true);

						Image img = new Image(CrearJugadoresController.class
								.getResourceAsStream(fichaPlayer
										.getPathImgBig()), 90, 87, true, true);
						imgFicha.setImage(img);
						imgFicha.setId("ficha_" + fichaPlayer.getIdFicha());
					}
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

	public Stage getPrevStage() {
		return prevStage;
	}

	public void setPrevStage(Stage prevStage) {
		this.prevStage = prevStage;
	}

	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public Juego getJuegoSelected() {
		return juegoSelected;
	}

	public void setJuegoSelected(Juego juegoSelected) {
		this.juegoSelected = juegoSelected;
		for (Ficha ficha : juegoSelected.getFichasPlayerList()) {
			if (!ficha.isSelected()) {
				fichaPlayer = ficha;
				fichaPlayer.setSelected(true);

				Image img = new Image(
						CrearJugadoresController.class.getResourceAsStream(fichaPlayer
								.getPathImgBig()), 90, 87, true, true);
				imgFicha.setImage(img);
				imgFicha.setId("ficha_" + fichaPlayer.getIdFicha());
				break;
			}
		}
	}

	public static UnirJugadorController getInstance() {
		if (instance == null)
			instance = new UnirJugadorController();
		return instance;
	}

}
