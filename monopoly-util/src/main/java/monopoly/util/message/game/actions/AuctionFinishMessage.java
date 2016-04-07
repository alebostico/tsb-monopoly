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
	public final int monto;
	public final Object tarjeta;

	public AuctionFinishMessage(String pIdJuego, String pMensaje) {
		idJuego = pIdJuego;
		mensaje = pMensaje;
		monto = 0;
		tarjeta = null;
	}
	
	public AuctionFinishMessage(String pIdJuego, int pMonto, Object pTarjeta, String pMensaje) {
		idJuego = pIdJuego;
		mensaje = pMensaje;
		monto = pMonto;
		tarjeta = pTarjeta;
	}

}
