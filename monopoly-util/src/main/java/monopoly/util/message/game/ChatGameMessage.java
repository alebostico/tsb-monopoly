package monopoly.util.message.game;

import java.io.Serializable;

public class ChatGameMessage implements Serializable {

	private static final long serialVersionUID = -7055776287835821196L;
	
	public final String idJuego;
	public final Object message; // Original message from a client.
	
	/**
	 * Create a ForwadedMessage to wrap a message sent by a client.
	 * 
	 * @param message
	 *            the original message.
	 */
	public ChatGameMessage(String idJuego, Object message) {
		this.idJuego = idJuego;
		this.message = message;
	}

}
