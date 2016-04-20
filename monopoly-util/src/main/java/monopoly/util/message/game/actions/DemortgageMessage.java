package monopoly.util.message.game.actions;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class DemortgageMessage implements Serializable {

	private static final long serialVersionUID = -1368624856073449644L;

	public final int senderID; // The ID of the client who sent that message.
	public final String idJuego;
	public final Object message; // Original message from a client.

	/**
	 * Create a ForwadedMessage to wrap a message sent by a client.
	 * 
	 * @param senderID
	 *            the ID number of the original sender.
	 * @param message
	 *            La propiedad que se quiere deshipotecar.
	 */
	public DemortgageMessage(int senderID, String idJuego, Object message) {
		this.senderID = senderID;
		this.idJuego = idJuego;
		this.message = message;
	}

}
