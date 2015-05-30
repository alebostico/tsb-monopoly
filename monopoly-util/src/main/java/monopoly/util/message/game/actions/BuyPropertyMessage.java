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
public class BuyPropertyMessage implements Serializable {

	private static final long serialVersionUID = -2491628976562730663L;

	public final String idJuego;
	public final Object message; // Original message from a client.

	public BuyPropertyMessage(String idJuego, Object message) {
		this.idJuego = idJuego;
		this.message = message;
	}
}
