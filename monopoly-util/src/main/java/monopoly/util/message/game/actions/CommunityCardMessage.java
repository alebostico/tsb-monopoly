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
public class CommunityCardMessage implements Serializable
{

	private static final long serialVersionUID = 2955654835156221691L;

	public final String idJuego;
	public final Object message; // Original message from a client.

	public CommunityCardMessage(String idJuego,Object message) {
		this.idJuego = idJuego;
		this.message = message;
	}
}
