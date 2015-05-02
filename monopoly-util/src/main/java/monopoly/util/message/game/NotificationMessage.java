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
public class NotificationMessage implements Serializable {

	private static final long serialVersionUID = 3607793414927789216L;

	public final Object message; // Original message from a client.
	public final int senderID; // The ID of the client who sent that message.
	
	public NotificationMessage(int senderID, Object historyList) {
		this.senderID = senderID;		
		this.message = historyList; // List<HistoryGameMessage>
	}
}
