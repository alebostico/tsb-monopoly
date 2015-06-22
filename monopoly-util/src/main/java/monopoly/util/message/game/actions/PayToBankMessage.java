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
public class PayToBankMessage implements Serializable {

	private static final long serialVersionUID = -418994357796220385L;

	public final String idJuego;
	public final int monto;
	public final String mensaje;

	public PayToBankMessage(String idJuego, int monto, String mensaje) {
		this.idJuego = idJuego;
		this.monto = monto;
		this.mensaje = mensaje;
	}
}
