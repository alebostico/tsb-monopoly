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
public class StartGameMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	public final Object message; // Original message from a client.
	public final int senderID; // The ID of the client who sent that message.

	/**
	 * Create a ForwadedMessage to wrap a message sent by a client.
	 * 
	 * @param senderID
	 *            the ID number of the original sender.
	 * @param message
	 *            the original message.
	 */
	public StartGameMessage(int senderID, Object message) {
		this.senderID = senderID;
		this.message = message;
	}

}
