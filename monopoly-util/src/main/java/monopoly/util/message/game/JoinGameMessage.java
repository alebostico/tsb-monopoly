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
public class JoinGameMessage implements Serializable {

	private static final long serialVersionUID = -7439839818328698491L;
	public final Object message; // Original message from a client.

	/**
	 * Create a ForwadedMessage to wrap a message sent by a client.
	 * 
	 * @param message
	 *            the original message.
	 */
	public JoinGameMessage(Object message) {
		this.message = message;
	}

}
