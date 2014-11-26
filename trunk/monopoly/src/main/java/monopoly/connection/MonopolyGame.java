/**
 * 
 */
package monopoly.connection;

import java.io.IOException;
import java.util.List;

import monopoly.controller.PartidasController;
import monopoly.controller.UsuarioController;
import monopoly.model.Estado.EstadoJuego;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.Usuario;
import monopoly.util.constantes.ConstantesMensaje;
import monopoly.util.message.CreateAccountMessage;
import monopoly.util.message.CreateGameMessage;
import monopoly.util.message.LoginMessage;
import monopoly.util.message.game.LoadGameMessage;
import monopoly.util.message.game.JoinGameMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class MonopolyGame extends GameServer {

	private Usuario usuario;
	private Juego juego;
	private List<Juego> juegosList;
	
	/**
	 * Creates a MonopolyGame listening on a specified port.
	 */
	public MonopolyGame(int port) throws IOException {
		super(port);
	}

	/**
	 * 
	 */
	public MonopolyGame() throws IOException {
		super();
	}

	/**
	 * When the second player connects, this method starts the game by sending
	 * the initial game state to the two players. At this time, the players'
	 * hands are null. The hands will be set when the first hand is dealt. This
	 * method also shuts down the Hub's ServerSocket so that no further players
	 * can connect.
	 */
	protected void playerConnected(int playerID) {

	}

	/**
	 * If a player disconnects, the game ends. This method shuts down the Hub,
	 * which will send a signal to the remaining connected player, if any, to
	 * let them know that their opponent has left the game. The client will
	 * respond by terminating that player's program.
	 */
	protected void playerDisconnected(int playerID) {
		shutdownServer();
	}

	/**
	 * This is the method that responds to messages received from the clients.
	 * It handles all of the action of the game. When a message is received,
	 * this method will make any changes to the state of the game that are
	 * triggered by the message. It will then send information about the new
	 * state to each player, and it will generally send a string to each client
	 * as a message to be displayed to that player.
	 */
	protected void messageReceived(int senderId, Object message) {
		
		switch (message.getClass().getSimpleName()) {
		case ConstantesMensaje.LOGIN_MESSAGE:
			usuario = (Usuario) ((LoginMessage) message).message;
			usuario = UsuarioController.validarUsuario(usuario.getUserName(),
					usuario.getPassword());
			sendToOne(senderId, new LoginMessage(senderId, usuario));
			break;

		case ConstantesMensaje.CREATE_ACCOUNT_MESSAGE:
			// tomar el usuario y grabarlo
			usuario = (Usuario) ((CreateAccountMessage) message).message;
			UsuarioController.saveUsuario(usuario);
			sendToOne(senderId, new CreateAccountMessage(senderId, usuario));
			break;

		case ConstantesMensaje.CREATE_GAME_MESSAGE:
			// create juego
			usuario = (Usuario) ((CreateGameMessage) message).message;
			juego = PartidasController.getInstance().crearJuego(usuario, "");
			sendToOne(senderId, new CreateGameMessage(senderId, juego));
			break;

		case ConstantesMensaje.JOIN_GAME_MESSAGE:
			// unirse al juego
			Jugador jugador = (Jugador) ((JoinGameMessage) message).message;
			PartidasController.getInstance().joinPlayerGame(jugador);
			break;
			
		case ConstantesMensaje.LOAD_GAME_MESSAGE:
			juego = (Juego) ((LoadGameMessage) message).message;
			PartidasController.getInstance().loadGame(senderId,juego);
			break;
			
		case ConstantesMensaje.START_GAME_MESSAGE:
			PartidasController.getInstance().establecerTurnoJugador(senderId, message);
			break;

		case ConstantesMensaje.DISCONNECT_MESSAGE:

			break;
			
		case "String":
			messageString(senderId, message);
			break;

		default:
			System.out.print(message.getClass().getSimpleName());
			break;
		}
	}
	
	private void messageString(int senderId, Object message){
		switch((String) message){
		case ConstantesMensaje.GET_PENDING_GAMES_MESSAGE:
			juegosList = PartidasController.getInstance().buscarJuegos(EstadoJuego.ESPERANDO_JUGADOR);
			sendToOne(senderId, new JoinGameMessage(juegosList));
			break;
			
		case ConstantesMensaje.THROW_DICE_TURNS_MESSAGE:
			
			break;
			
			default:
				break;
		
		}
	}

}
