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

	public final String idJuego;
	public final Object subastaStatus; // Original message from a client.

	public AuctionPropertyMessage(String idJuego, Object subastaStatus) {
		this.subastaStatus = subastaStatus;
		this.idJuego= idJuego;
	}
}
