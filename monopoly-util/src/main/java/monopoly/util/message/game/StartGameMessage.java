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

	private static final long serialVersionUID = -1778670727756818850L;

	public final Object message; // Original message from a client.
	public final String UniqueIdJuego; // The ID of the client who sent that message.

	/**
	 * Create a ForwadedMessage to wrap a message sent by a client.
	 * 
	 */
	public StartGameMessage(String UniqueIdJuego, Object dados) {
		this.UniqueIdJuego = UniqueIdJuego;		
		this.message = dados;
	}

}
