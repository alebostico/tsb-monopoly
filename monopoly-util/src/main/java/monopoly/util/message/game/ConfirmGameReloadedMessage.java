package monopoly.util.message.game;

import java.io.Serializable;

/**
 * Se usa para confirmar que el cliente pudo restaurar correctamente un juego
 * guardado.
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class ConfirmGameReloadedMessage implements Serializable {

	private static final long serialVersionUID = 5289030343205856677L;

	public final Object juego;
	public final int senderID;

	/**
	 * Create a ForwadedMessage to wrap a message sent by a client.
	 * 
	 * @param senderID
	 *            the ID number of the original sender.
	 * @param juego
	 *            el juego que se quiere restaurar.
	 */
	public ConfirmGameReloadedMessage(int senderID, Object juego) {
		this.juego = juego;
		this.senderID = senderID;
	}

}
