/**
 * 
 */
package monopoly.controller;

import java.io.IOException;

import monopoly.connection.GameServer;
import monopoly.model.Juego;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class GameController extends GameServer {
	
	// private Juego juego;
	
	public GameController(int port) throws IOException {
		super(port);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * Cuando se conecta el ultimo jugador debería comenzar el juego
	 * 
	 * When the second player connects, this method starts the game by sending
	 * the initial game state to the two players. At this time, the players'
	 * hands are null. The hands will be set when the first hand is dealt. This
	 * method also shuts down the Hub's ServerSocket so that no further players
	 * can connect.
	 */
	protected void playerConnected(int playerID) {
		if (playerID == 2) {
			shutdownServerSocket();
			sendToAll("Ready to start the first game!");
		}
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
	    * This is the method that responds to messages received from the
	    * clients.  It handles all of the action of the game.  When a message
	    * is received, this method will make any changes to the state of
	    * the game that are triggered by the message.  It will then send
	    * information about the new state to each player, and it will
	    * generally send a string to each client as a message to be
	    * displayed to that player.
	    */
	   protected void messageReceived(int playerID, Object message) {
		   
	   }

}
