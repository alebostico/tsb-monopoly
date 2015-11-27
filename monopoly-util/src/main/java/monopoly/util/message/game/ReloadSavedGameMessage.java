/**
 * 
 */
package monopoly.util.message.game;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class ReloadSavedGameMessage implements Serializable {

	private static final long serialVersionUID = 5062728760709246684L;


	public final Object juego; // Original message from a client.
	public final Object juegoStatus; // El Status del juego
	public final int senderID; // The ID of the client who sent that message.

	/**
	 * Create a ForwadedMessage to wrap a message sent by a client.
	 * 
	 * @param senderID
	 *            the ID number of the original sender.
	 * @param juego
	 *            el juego que se quiere restaurar.
	 */
	public ReloadSavedGameMessage(int senderID, Object juego, Object gameStatus) {
		this.senderID = senderID;
		this.juego = juego;
		this.juegoStatus = gameStatus;
	}

}
