package monopoly.util.message.game.actions;

import java.io.Serializable;


/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class BidForPropertyMessage implements Serializable {

	private static final long serialVersionUID = -2087277759739007900L;
	public final Object comprador; // The ID of the client who sent that message.
	public final String idJuego;
	public final Object propiedad; // Original message from a client.
	public final Integer oferta; // La cantidad de casas a construir

	/**
	 * Create a ForwadedMessage to wrap a message sent by a client.
	 * 
	 * @param senderID
	 *            the ID number of the original sender.
	 * @param idJuego
	 *            El juego
	 * @param oferta
	 *            el monto que se ofrece por la propiedad
	 * @param message
	 *            La propiedad que se quiere hipotecar.
	 */
	public BidForPropertyMessage(Object dueno, String idJuego, Object propiedad,
			Integer oferta) {
		this.comprador = dueno;
		this.idJuego = idJuego;
		this.propiedad = propiedad;
		this.oferta = oferta;
	}

}
