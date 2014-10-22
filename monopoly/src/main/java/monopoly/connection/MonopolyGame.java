/**
 * 
 */
package monopoly.connection;

import java.io.IOException;

import monopoly.controller.PartidasController;
import monopoly.controller.UsuarioController;
import monopoly.model.Juego;
import monopoly.model.Usuario;
import monopoly.util.message.CreateAccountMessage;
import monopoly.util.message.CreateGameMessage;
import monopoly.util.message.InitGameMessage;
import monopoly.util.message.LoginMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class MonopolyGame extends GameServer {

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
	protected void messageReceived(int playerID, Object message) {
		int senderId = 0;
		Usuario usuario;
		Juego juego;
		
		switch (message.getClass().getSimpleName()) {
		case "LoginMessage":
			senderId = ((LoginMessage) message).senderID;
			usuario = (Usuario) ((LoginMessage) message).message;
			usuario = UsuarioController.validarUsuario(usuario.getUserName(),
					usuario.getPassword());
			sendToOne(senderId, new LoginMessage(senderId, usuario));
			break;

		case "CreateAccountMessage":
			// tomar el usuario y grabarlo
			senderId = ((CreateAccountMessage) message).senderID;
			usuario = (Usuario) ((CreateAccountMessage) message).message;
			UsuarioController.saveUsuario(usuario);
			sendToOne(senderId, new CreateAccountMessage(senderId, usuario));
			break;

		case "CreateGameMessage":
			// create juego
			senderId = ((CreateGameMessage) message).senderID;
			usuario = (Usuario) ((CreateGameMessage) message).message;
			juego = PartidasController.getInstance().crearJuego(usuario, "");
			sendToOne(senderId, new CreateGameMessage(senderId, juego));
			break;

		case "JoinGameMessage":
			// unirse al juego
			break;
			
		case "InitGameMessage":
			senderId = ((InitGameMessage) message).senderID;
			juego = (Juego) ((InitGameMessage) message).message;
			PartidasController.getInstance().initGame(senderId,juego);
			break;

		case "DisconnectMessage":

			break;

		default:
			System.out.print(message.getClass().getSimpleName());
			break;
		}
	}

}
