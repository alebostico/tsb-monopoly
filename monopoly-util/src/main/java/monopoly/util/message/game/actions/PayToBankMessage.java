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
public class PayToBankMessage implements Serializable {

	private static final long serialVersionUID = -418994357796220385L;

	public final Object message; // Original message from a client.

	public PayToBankMessage(Object message) {
		this.message = message;
	}
}
