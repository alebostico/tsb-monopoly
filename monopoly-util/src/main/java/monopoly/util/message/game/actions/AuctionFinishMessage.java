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
public class AuctionFinishMessage implements Serializable {

	private static final long serialVersionUID = 3977244978978063031L;

	public final String idJuego;
	public final String mensaje;

	public AuctionFinishMessage(String pIdJuego, String pMensaje) {
		idJuego = pIdJuego;
		mensaje = pMensaje;
	}

}
