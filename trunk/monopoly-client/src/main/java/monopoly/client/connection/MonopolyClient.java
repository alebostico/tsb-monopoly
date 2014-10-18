/**
 * 
 */
package monopoly.client.connection;

import java.io.IOException;

import monopoly.client.controller.CrearJuegoController;
import monopoly.client.controller.LoginController;
import monopoly.client.controller.RegistrarmeController;
import monopoly.model.Juego;
import monopoly.model.Usuario;
import monopoly.util.message.CreateAccountMessage;
import monopoly.util.message.CreateGameMessage;
import monopoly.util.message.LoginMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class MonopolyClient extends GameClient {

	private Usuario usuario;
	
	private Juego juego;
	
	
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
		
		switch (message.getClass().getSimpleName()) {
		case "LoginMessage":
			usuario = (Usuario) ((LoginMessage) message).message;
			LoginController.getInstance().iniciarSesion(usuario);
			break;

		case "CreateAccountMessage":
			usuario = (Usuario) ((CreateAccountMessage) message).message;
			RegistrarmeController.getInstance().iniciarSesion(usuario);
			break;
			
		case "CreateGameMessage":
			juego = (Juego) ((CreateGameMessage) message).message;
			CrearJuegoController.getInstance().showCrearJuego(juego);
			break;

		case "String":

			break;

		default:
			break;
		}

		// if (message instanceof MonopolyGameState) {
		// newState((MonopolyGameState) message);
		// }
		// if (message instanceof MonopolyGameState)
		// newState((MonopolyGameState) message);
		// else if (message instanceof String)
		// messageFromServer.setText( (String)message );
		// else if (message instanceof PokerCard[]) {
		// opponentHand = (PokerCard[])message;
		// display.repaint();
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
