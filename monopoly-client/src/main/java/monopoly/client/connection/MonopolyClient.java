/**
 * 
 */
package monopoly.client.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert.AlertType;
import monopoly.client.controller.CrearJuegoController;
import monopoly.client.controller.LoginController;
import monopoly.client.controller.ReanudarJuegoController;
import monopoly.client.controller.RegistrarmeController;
import monopoly.client.controller.TableroController;
import monopoly.client.controller.UnirmeJuegoController;
import monopoly.model.History;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.JugadorHumano;
import monopoly.model.MonopolyGameStatus;
import monopoly.model.SubastaStatus;
import monopoly.model.Usuario;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.ConstantesMensaje;
import monopoly.util.exception.SinDineroException;
import monopoly.util.exception.UsuarioExistenteException;
import monopoly.util.message.CreateAccountMessage;
import monopoly.util.message.CreateGameMessage;
import monopoly.util.message.ExceptionMessage;
import monopoly.util.message.LoginMessage;
import monopoly.util.message.game.BankruptcyMessage;
import monopoly.util.message.game.ChatGameMessage;
import monopoly.util.message.game.GetSavedGamesMessage;
import monopoly.util.message.game.HistoryGameMessage;
import monopoly.util.message.game.JoinGameMessage;
import monopoly.util.message.game.ReloadSavedGameMessage;
import monopoly.util.message.game.SaveGameMessage;
import monopoly.util.message.game.actions.AuctionDecideMessage;
import monopoly.util.message.game.actions.AuctionFinishMessage;
import monopoly.util.message.game.actions.AuctionNotifyMessage;
import monopoly.util.message.game.actions.AuctionPropertyMessage;
import monopoly.util.message.game.actions.BidForPropertyMessage;
import monopoly.util.message.game.actions.BidResultMessage;
import monopoly.util.message.game.actions.BuildMessage;
import monopoly.util.message.game.actions.DemortgageMessage;
import monopoly.util.message.game.actions.MortgageMessage;
import monopoly.util.message.game.actions.UnbuildMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class MonopolyClient extends GameClient {

	private Usuario usuario;

	private Juego juego;

	private History history;

	private TarjetaPropiedad propiedad;

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
		List<?> list;
		List<History> historyList;
		String mensaje;
		MonopolyGameStatus status;
		SubastaStatus subastaStatus;
		AuctionDecideMessage msgAuctionDecide;
		ExceptionMessage exceptionMessage;
		TarjetaCalle tarjetaCalle;

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

			case ConstantesMensaje.BANKRUPTCY_MESSAGE:
				mensaje = ((BankruptcyMessage) message).message;
				TableroController.getInstance().informarBancarrota(mensaje);
				break;

			case ConstantesMensaje.SAVE_GAME_MESSAGE:
				// La IOException ES null si el juego se guardó correctamente
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
							.showMessageBox(
									AlertType.INFORMATION,
									"Información",
									"No hay juegos",
									"No se encontró ningún juego guardado para el usuario.",
									null);
				}
				break;

			case ConstantesMensaje.RELOAD_SAVED_GAME_MESSAGE:

				ReloadSavedGameMessage savedGame = (ReloadSavedGameMessage) message;
				this.juego = (Juego) savedGame.juego;
				status = (MonopolyGameStatus) savedGame.juegoStatus;
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
				history = (History) ((HistoryGameMessage) message).history;
				TableroController.getInstance().addHistoryGame(history);
				break;

			case ConstantesMensaje.CHAT_GAME_MESSAGE:
				history = (History) ((ChatGameMessage) message).message;
				TableroController.getInstance().addChatHistoryGame(history);
				break;

			case ConstantesMensaje.COMPLETE_TURN_MESSAGE:
				// CompleteTurnMessage msgCompleteTurnMessage =
				// (CompleteTurnMessage) message;
				// TableroController.getInstance().completarTurno(
				// msgCompleteTurnMessage.message,
				// msgCompleteTurnMessage.action,
				// (MonopolyGameStatus) msgCompleteTurnMessage.status);
				break;

			case ConstantesMensaje.AUCTION_PROPERTY_MESSAGE:
				subastaStatus = (SubastaStatus) ((AuctionPropertyMessage) message).subastaStatus;
				TableroController.getInstance()
						.actualizarSubasta(subastaStatus);
				break;

			case ConstantesMensaje.AUCTION_NOTIFY_MESSAGE:
				list = (List<?>) ((AuctionNotifyMessage) message).historyList;

				if (list.isEmpty())
					return;

				historyList = new ArrayList<History>();
				for (Object history : list) {
					historyList.add((History) history);
				}
				TableroController.getInstance().addHistorySubasta(historyList);
				break;

			case ConstantesMensaje.AUCTION_FINISH_MESSAGE:
				mensaje = ((AuctionFinishMessage) message).mensaje;
				TableroController.getInstance().finalizarSubasta(mensaje);
				break;

			case ConstantesMensaje.AUCTION_DECIDE_MESSAGE:
				msgAuctionDecide = (AuctionDecideMessage) message;
				TableroController.getInstance().decidirSubasta(
						msgAuctionDecide.mensaje, msgAuctionDecide.monto,
						(TarjetaPropiedad) msgAuctionDecide.propiedad,
						(Jugador) msgAuctionDecide.jugadorInicial);

				break;

			case ConstantesMensaje.MORTGAGE_MESSAGE:
				MortgageMessage hipoteca = (MortgageMessage) message;
				propiedad = (TarjetaPropiedad) hipoteca.message;
				TableroController.getInstance().finishMortgage(propiedad);
				break;

			case ConstantesMensaje.DEMORTGAGE_MESSAGE:
				DemortgageMessage deshipoteca = (DemortgageMessage) message;
				propiedad = (TarjetaPropiedad) deshipoteca.message;
				TableroController.getInstance().finishDemortgage(propiedad);
				break;

			case ConstantesMensaje.BUILD_MESSAGE:
				BuildMessage construccion = (BuildMessage) message;
				tarjetaCalle = (TarjetaCalle) construccion.message;
				TableroController.getInstance().finishBuild(tarjetaCalle,
						construccion.cantidad);
				break;

			case ConstantesMensaje.UNBUILD_MESSAGE:
				UnbuildMessage vender = (UnbuildMessage) message;
				tarjetaCalle = (TarjetaCalle) vender.message;
				TableroController.getInstance().finishUnbuild(tarjetaCalle,
						vender.cantidad);
				break;

			case ConstantesMensaje.BID_FOR_PROPERTY_MESSAGE:
				BidForPropertyMessage bidMsg = (BidForPropertyMessage) message;
				TableroController.getInstance().ofrecerPorPropiedad(
						(TarjetaPropiedad) bidMsg.propiedad,
						(JugadorHumano) bidMsg.comprador,
						bidMsg.oferta.intValue());
				break;

			case ConstantesMensaje.BID_RESULT_MESSAGE:
				BidResultMessage bidResult = (BidResultMessage) message;
				TableroController.getInstance().finalizarOfertaPropiedad(
						(TarjetaPropiedad) bidResult.propiedad,
						bidResult.oferta.intValue(),
						bidResult.resultado.booleanValue());
				break;

			case ConstantesMensaje.EXCEPTION_MESSAGE:
				exceptionMessage = (ExceptionMessage) message;
				mensaje = exceptionMessage.message.getClass().getSimpleName();
				
				switch (mensaje) {
				case "SinDineroException":
					SinDineroException sdex = (SinDineroException) exceptionMessage.message;
					TableroController.getInstance()
							.showSinDineroException(sdex);
					break;
					
				case "UsuarioExistenteException":
					UsuarioExistenteException uee = (UsuarioExistenteException) exceptionMessage.message;
					RegistrarmeController.getInstance().showMensaje(uee.getMessage());
					break;

				default:
					TableroController.getInstance().showException(
							exceptionMessage.message, mensaje);
					break;
				}
				break;

			case "String":
				messageString(message);
				break;

			default:
				break;
			}
		} catch (Exception ex) {
			GestorLogs.registrarException(ex);
			mensaje = message.getClass().getSimpleName();
			TableroController.getInstance().showException(ex, mensaje);
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

		case ConstantesMensaje.WIN_MESSAGE:
			TableroController.getInstance().showWinMessage();
			break;

		default:
			break;

		}
	}

	private void determinarAccion(Object message) {
		MonopolyGameStatus status = (MonopolyGameStatus) message;

		TableroController.getInstance().actualizarEstadoJuego(status);

		// switch (status.estadoTurno) {
		// case INICIADO:
		// TableroController.getInstance().actualizarEstadoJuego(status);
		// break;
		//
		// case ACTUALIZANDO_ESTADO:
		// case TIRAR_DADO:
		// case ESPERANDO_TURNO:
		// case JUGANDO:
		// case DADOS_DOBLES:
		// case PRESO:
		// case LIBRE:
		// TableroController.getInstance().actualizarEstadoJuego(status);
		// break;
		//
		// default:
		// break;
		//
		// }
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
