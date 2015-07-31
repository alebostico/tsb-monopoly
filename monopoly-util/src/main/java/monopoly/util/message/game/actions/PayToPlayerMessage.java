/**
 * 
 */
package monopoly.util.message.game.actions;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 */
public class PayToPlayerMessage implements Serializable {

	private static final long serialVersionUID = -5934180664095710047L;

	public final Object jugadorAPagar;
	public final String mesasge;
	public final int monto;
	public final String idJuego;

	public PayToPlayerMessage(String message, Object jugador, int monto,
			String idJuego) {
		this.monto = monto;
		this.mesasge = message;
		this.jugadorAPagar = jugador;
		this.idJuego = idJuego;
	}

}
