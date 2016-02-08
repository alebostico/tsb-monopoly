package monopoly.util.message.game;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class MortgageMessage implements Serializable {

	private static final long serialVersionUID = 7732230402922511921L;

	public final int senderID; // The ID of the client who sent that message.
	public final String idJuego;
	public final Object message; // Original message from a client.

	/**
	 * Create a ForwadedMessage to wrap a message sent by a client.
	 * 
	 * @param senderID
	 *            the ID number of the original sender.
	 * @param message
	 *            La propiedad que se quiere hipotecar.
	 */
	public MortgageMessage(int senderID, String idJuego, Object message) {
		this.senderID = senderID;
		this.idJuego = idJuego;
		this.message = message;
	}

}
