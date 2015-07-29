/**
 * 
 */
package monopoly.client.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import monopoly.client.controller.CrearJuegoController;
import monopoly.client.controller.LoginController;
import monopoly.client.controller.RegistrarmeController;
import monopoly.client.controller.TableroController;
import monopoly.client.controller.UnirmeJuegoController;
import monopoly.model.History;
import monopoly.model.Juego;
import monopoly.model.MonopolyGameStatus;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.ConstantesMensaje;
import monopoly.util.message.CreateAccountMessage;
import monopoly.util.message.CreateGameMessage;
import monopoly.util.message.LoginMessage;
import monopoly.util.message.game.HistoryGameMessage;
import monopoly.util.message.game.JoinGameMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class MonopolyClient extends GameClient {

	private Usuario usuario;

	private Juego juego;

	private List<Juego> juegosList;

	/**
	 * Connect to the hub at a specified host name and port number.
	 */
	public MonopolyClient(String hubHostName, int hubPort) throws IOException {
		super(hubHostName, hubPort);
	}

	/**
	 * Responds to a message received from the Hub. The only messages that are
	 * supported are TicTacToeGameState objects. When one is received, the
	 * newState() method in the TicTacToeWindow class is called. To avoid
	 * problems with synchronization, that method is called using
	 * SwingUtilities.invokeLater() so that it will run in the GUI event thread.
	 */
	protected void messageReceived(final Object message) {
		try {
			switch (message.getClass().getSimpleName()) {
			case ConstantesMensaje.LOGIN_MESSAGE:
				usuario = (Usuario) ((LoginMessage) message).message;
				LoginController.getInstance().iniciarSesion(usuario);
				break;

			case ConstantesMensaje.CREATE_ACCOUNT_MESSAGE:
				usuario = (Usuario) ((CreateAccountMessage) message).message;
				RegistrarmeController.getInstance().iniciarSesion(usuario);
				break;

			case ConstantesMensaje.CREATE_GAME_MESSAGE:
				juego = (Juego) ((CreateGameMessage) message).message;
				CrearJuegoController.getInstance().showCrearJuego(juego);
				break;

			case ConstantesMensaje.JOIN_GAME_MESSAGE:
				List<?> list = (List<?>) ((JoinGameMessage) message).message;
				juegosList = new ArrayList<Juego>();
				if (!list.isEmpty()) {
					for (Object obj : list) {
						juegosList.add((Juego) obj);
					}
				}
				UnirmeJuegoController.getInstance().showUnirmeJuego(juegosList);
				break;

			case ConstantesMensaje.STATUS_GAME_MESSAGE:
				determinarAccion(message);
				break;

			case ConstantesMensaje.HISTORY_GAME_MESSAGE:
				History history = (History) ((HistoryGameMessage) message).message;
				TableroController.getInstance().addHistoryGame(history);
				break;

			case ConstantesMensaje.COMPLETE_TURN_MESSAGE:
//				CompleteTurnMessage msgCompleteTurnMessage = (CompleteTurnMessage) message;
//				TableroController.getInstance().completarTurno(
//						msgCompleteTurnMessage.message,
//						msgCompleteTurnMessage.action,
//						(MonopolyGameStatus) msgCompleteTurnMessage.status);
				break;

			case ConstantesMensaje.EXCEPTION_MESSAGE:

				break;

			case "String":
				messageString(message);
				break;

			default:
				break;
			}
		} catch (Exception ex) {
			GestorLogs.registrarError(ex);
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error...");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
		}

	}

	private void messageString(Object message) {
		switch ((String) message) {

		case ConstantesMensaje.THROW_DICE_TURNS_MESSAGE:
			TableroController.getInstance().tirarDatosTurno();
			break;

		case ConstantesMensaje.THROW_DICE_ADVANCE_MESSAGE:
			TableroController.getInstance().processTirarDados(null);
			break;

		default:
			break;

		}
	}

	private void determinarAccion(Object message) {
		MonopolyGameStatus status = (MonopolyGameStatus) message;

		switch (status.getStatus()) {
		case INICIADO:
			TableroController.getInstance().actualizarEstadoJuego(status);
			break;

		case TIRAR_DADO:
		case ESPERANDO_TURNO:
		case JUGANDO:
			TableroController.getInstance().actualizarEstadoJuego(status);
			break;

		default:
			break;

		}
	}

	/**
	 * If a shutdown message is received from the Hub, the user is notified and
	 * the program ends.
	 */
	protected void serverShutdown(String message) {
		System.out
				.println("Your opponent has disconnected.\nThe game is ended.");
		System.exit(0);
	}

}
