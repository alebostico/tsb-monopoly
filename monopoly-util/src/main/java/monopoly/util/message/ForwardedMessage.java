package monopoly.util.message;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */

/**
 * Represents a message that was received by the Hub from one clients and that
 * is being forwarded to all clients. A ForwardedMessage includes the message
 * that was sent by a client to the Hub and the ID number of the client who sent
 * it. The default action of a Hub -- defined in the
 * messageReceived(playerID,message) method of that class -- is to wrap the
 * message in a ForwardedMessage and send the ForwardedMessage to all connected
 * client, including the client who sent the original message. When an
 * application uses a subclass of Hub, it is likely to override that behavior.
 */
public class ForwardedMessage implements Serializable {

	private static final long serialVersionUID = 3793323838998383388L;
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
	public ForwardedMessage(int senderID, Object message) {
		this.senderID = senderID;
		this.message = message;
	}
}
