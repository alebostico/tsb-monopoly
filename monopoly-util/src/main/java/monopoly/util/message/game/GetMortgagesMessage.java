package monopoly.util.message.game;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class GetMortgagesMessage implements Serializable {

	private static final long serialVersionUID = 3555249207080068084L;

	public final int senderID; // The ID of the client who sent that message.
	public final Object message; // Original message from a client.

	/**
	 * Create a ForwadedMessage to wrap a message sent by a client.
	 * 
	 * @param senderID
	 *            the ID number of the original sender.
	 * @param message
	 *            El Jugador del cual se quieren mostrar las propiedades.
	 */
	public GetMortgagesMessage(int senderID, Object message) {
		this.senderID = senderID;
		this.message = message;
	}

}
