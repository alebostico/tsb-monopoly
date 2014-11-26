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
public class AdvanceInBoardMessage implements Serializable{

	private static final long serialVersionUID = 3160318744864280110L;
	
	public final Object message; // Original message from a client.
	public final int senderID; // The ID of the client who sent that message.
	
	public AdvanceInBoardMessage(int senderID, Object message) {
		this.senderID = senderID;
		this.message = message;
	}
	
}
