/**
 * 
 */
package monopoly.client.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert.AlertType;
import monopoly.client.controller.CrearJuegoController;
import monopoly.client.controller.HipotecarController;
import monopoly.client.controller.LoginController;
import monopoly.client.controller.ReanudarJuegoController;
import monopoly.client.controller.RegistrarmeController;
import monopoly.client.controller.TableroController;
import monopoly.client.controller.UnirmeJuegoController;
import monopoly.model.History;
import monopoly.model.Juego;
import monopoly.model.MonopolyGameStatus;
import monopoly.model.Usuario;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.ConstantesMensaje;
import monopoly.util.message.CreateAccountMessage;
import monopoly.util.message.CreateGameMessage;
import monopoly.util.message.ExceptionMessage;
import monopoly.util.message.LoginMessage;
import monopoly.util.message.game.ChatGameMessage;
import monopoly.util.message.game.GetMortgagesMessage;
import monopoly.util.message.game.GetSavedGamesMessage;
import monopoly.util.message.game.HistoryGameMessage;
import monopoly.util.message.game.JoinGameMessage;
import monopoly.util.message.game.MortgageMessage;
import monopoly.util.message.game.ReloadSavedGameMessage;
import monopoly.util.message.game.SaveGameMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class MonopolyClient extends GameClient {

	private Usuario usuario;

	private Juego juego;

	private TarjetaPropiedad propiedad;
	
	private List<Juego> juegosList;
	private List<TarjetaPropiedad> propiedadesList;

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
		List<?> list;

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

			case ConstantesMensaje.SAVE_GAME_MESSAGE:
				// La IOExceptino ES null si el juego se guardó correctamente
				IOException exception = (IOException) ((SaveGameMessage) message).exception;
				TableroController.getInstance().showJuegoGuardado(exception);
				break;

			case ConstantesMensaje.GET_SAVED_GAMES_MESSAGES:
				list = (List<?>) ((GetSavedGamesMessage) message).message;
				juegosList = new ArrayList<Juego>();
				if (list != null && !list.isEmpty()) {
					for (Object obj : list) {
						juegosList.add((Juego) obj);
					}
					ReanudarJuegoController.getInstance().showReanudarJuego(
							juegosList);
				} else {
					usuario = TableroController.getInstance()
							.getUsuarioLogueado();
					TableroController
							.getInstance()
							.showMessageBox(AlertType.INFORMATION,
									"Información", "No hay juegos",
									"No se encontró ningún juego guardado para el usuario.");
				}
				break;

			case ConstantesMensaje.MORTGAGE_MESSAGE:
				MortgageMessage hipoteca = (MortgageMessage) message;
				propiedad = (TarjetaPropiedad) hipoteca.message;
				HipotecarController.getInstance().finishMortgage(propiedad);
				break;
				
			case ConstantesMensaje.RELOAD_SAVED_GAME_MESSAGE:

				ReloadSavedGameMessage savedGame = (ReloadSavedGameMessage) message;
				this.juego = (Juego) savedGame.juego;
				MonopolyGameStatus status = (MonopolyGameStatus) savedGame.juegoStatus;
				ReanudarJuegoController.getInstance().finishLoadGame(juego,
						status);
				break;

			case ConstantesMensaje.JOIN_GAME_MESSAGE:
				list = (List<?>) ((JoinGameMessage) message).message;
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

			case ConstantesMensaje.CHAT_GAME_MESSAGE:
				History chatHistory = (History) ((ChatGameMessage) message).message;
				TableroController.getInstance().addChatHistoryGame(chatHistory);
				break;

			case ConstantesMensaje.COMPLETE_TURN_MESSAGE:
				// CompleteTurnMessage msgCompleteTurnMessage =
				// (CompleteTurnMessage) message;
				// TableroController.getInstance().completarTurno(
				// msgCompleteTurnMessage.message,
				// msgCompleteTurnMessage.action,
				// (MonopolyGameStatus) msgCompleteTurnMessage.status);
				break;

			case ConstantesMensaje.GET_MORTGAGES_MESSAGE:
				list = (List<?>) ((GetMortgagesMessage) message).message;
				propiedadesList = new ArrayList<TarjetaPropiedad>();
				if (list != null && !list.isEmpty()) {
					for (Object obj : list) {
						propiedadesList.add((TarjetaPropiedad) obj);
					}
					HipotecarController.getInstance().showHipotecar(
							propiedadesList);
				} else {
					usuario = TableroController.getInstance()
							.getUsuarioLogueado();
					TableroController.getInstance().showMessageBox(
							AlertType.INFORMATION, "Información",
							"No hay propiedades",
							"No hay ninguna propiedad que se pueda hipotecar.");
				}
				break;

			case ConstantesMensaje.EXCEPTION_MESSAGE:
				Exception ex = (Exception) ((ExceptionMessage) message).message;
				TableroController.getInstance().showException(ex);
				break;

			case "String":
				messageString(message);
				break;

			default:
				break;
			}
		} catch (Exception ex) {
			GestorLogs.registrarException(ex);
			TableroController.getInstance().showException(ex);
		}

	}

	private void messageString(Object message) {
		switch ((String) message) {

		case ConstantesMensaje.THROW_DICE_TURNS_MESSAGE:
			TableroController.getInstance().tirarDadosTurno();
			break;

		case ConstantesMensaje.THROW_DICE_ADVANCE_MESSAGE:
			TableroController.getInstance().processTirarDados(null);
			break;

		case ConstantesMensaje.DOUBLE_DICE_MESSAGE:
			TableroController.getInstance().showDadosDobles();
			break;

		default:
			break;

		}
	}

	private void determinarAccion(Object message) {
		MonopolyGameStatus status = (MonopolyGameStatus) message;

		switch (status.estadoTurno) {
		case INICIADO:
			TableroController.getInstance().actualizarEstadoJuego(status);
			break;

		case TIRAR_DADO:
		case ESPERANDO_TURNO:
		case JUGANDO:
		case DADOS_DOBLES:
		case PRESO:
		case LIBRE:
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
