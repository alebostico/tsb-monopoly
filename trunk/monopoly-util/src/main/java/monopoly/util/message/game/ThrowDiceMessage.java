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
public class ThrowDiceMessage implements Serializable  {

	private static final long serialVersionUID = -5411204058729372854L;
	
	public final Object message; // Original message from a client.
	
	public ThrowDiceMessage(Object message) {
		this.message = message;
	}
	
}
