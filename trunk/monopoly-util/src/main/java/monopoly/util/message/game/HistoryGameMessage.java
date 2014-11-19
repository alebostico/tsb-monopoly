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
public class HistoryGameMessage implements Serializable { 

	private static final long serialVersionUID = -7458872200667918597L;

	public final Object message; // Original message from a client.

	/**
	 * Create a ForwadedMessage to wrap a message sent by a client.
	 * 
	 * @param message
	 *            the original message.
	 */
	public HistoryGameMessage(Object message) {
		this.message = message;
	}
}
