package monopoly.util.message.game;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class BidResultMessage implements Serializable {

	private static final long serialVersionUID = 6385060950058676379L;
	public final Object comprador; // The ID of the client who sent that message.
	public final String idJuego;
	public final Object propiedad; // Original message from a client.
	public final Integer oferta; // La cantidad de casas a construir
	public final Boolean resultado;

	/**
	 * Create a ForwadedMessage to wrap a message sent by a client.
	 * 
	 * @param senderID
	 *            the ID number of the original sender.
	 * @param idJuego
	 *            El juego
	 * @param message
	 *            La propiedad que se quiere hipotecar.
	 * @param oferta
	 *            el monto que se ofrece por la propiedad
	 * @param resultado
	 *            Si el jugador acepta o no la oferta
	 */
	public BidResultMessage(Object comprador, String idJuego, Object propiedad,
			Integer oferta, boolean resultado) {
		this.comprador = comprador;
		this.idJuego = idJuego;
		this.propiedad = propiedad;
		this.oferta = oferta;
		this.resultado = resultado;
	}

}
