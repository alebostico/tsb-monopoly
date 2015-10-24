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
public class DoubleDiceJailMessage  implements Serializable {

	private static final long serialVersionUID = 2063660727579038325L;

	public final Object dados; // Original message from a client.
	public final String idJuego; // The ID of the client who sent that message.
	
	public DoubleDiceJailMessage(String idJuego, Object message) {
		this.idJuego = idJuego;
		this.dados = message;
	}
	
}
