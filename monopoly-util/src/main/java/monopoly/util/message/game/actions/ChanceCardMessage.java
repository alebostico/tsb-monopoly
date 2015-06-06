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
public class ChanceCardMessage implements Serializable{

	private static final long serialVersionUID = -2181285037127988288L;

	public final String idJuego;
	public final Object message; // Original message from a client.

	public ChanceCardMessage(String idJuego, Object message) {
		this.idJuego = idJuego;
		this.message = message;
	}
}
