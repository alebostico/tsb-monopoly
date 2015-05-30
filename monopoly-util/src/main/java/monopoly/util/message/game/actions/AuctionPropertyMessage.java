/**
 * 
 */
package monopoly.util.message.game.actions;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class AuctionPropertyMessage implements Serializable {

	private static final long serialVersionUID = -81024719452379087L;

	public final Object message; // Original message from a client.

	public AuctionPropertyMessage(Object message) {
		this.message = message;
	}
}
