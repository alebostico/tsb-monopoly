package monopoly.util.message.game;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class UnbuildMessage implements Serializable {

	private static final long serialVersionUID = 103904810959880819L;
	public final int senderID; // The ID of the client who sent that message.
	public final String idJuego;
	public final Object message; // Original message from a client.
	public final Integer cantidad; // La cantidad de casas a construir

	/**
	 * Create a ForwadedMessage to wrap a message sent by a client.
	 * 
	 * @param senderID
	 *            the ID number of the original sender.
	 * @param message
	 *            La propiedad que se quiere hipotecar.
	 */
	public UnbuildMessage(int senderID, String idJuego, Object message,
			Integer cantidad) {
		this.senderID = senderID;
		this.idJuego = idJuego;
		this.message = message;
		this.cantidad = cantidad;
	}

}
