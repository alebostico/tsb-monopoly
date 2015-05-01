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
	 */
	public StartGameMessage(int senderID, String UniqueIdJuego, Object dados) {
		Object[] vDatos = new Object[2];
		vDatos[0] = UniqueIdJuego;
		vDatos[1] = dados;
		this.senderID = senderID;		
		this.message = vDatos;
	}

}
