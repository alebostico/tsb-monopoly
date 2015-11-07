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
public class PayToLeaveJailMessage implements Serializable {
	
	private static final long serialVersionUID = -8223997129551822857L;
	
	public final Object message; // Original message from a client.
	public final String idJuego; // The ID of the client who sent that message.
	
	public PayToLeaveJailMessage(String idJuego, Object message) {
		this.idJuego = idJuego;
		this.message = message;
	}

}
