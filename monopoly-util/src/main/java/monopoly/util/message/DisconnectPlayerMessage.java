package monopoly.util.message;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */

/**
 * A DisconnectMessage is sent from a Client to the Hub when that client wants
 * to disconnect. A DisconnectMessage is also sent from the Hub to each client
 * just before it shuts down normally. DisconnectMessages are for internal use
 * in the netgame.common package and are not used directly by users of the
 * package.
 */
public final class DisconnectPlayerMessage implements Serializable {

	private static final long serialVersionUID = -1137038451401065730L;
	
	/**
	 * The message associated with the disconnect. When the Hub sends
	 * disconnects because it is shutting down, the message is "*shutdown*".
	 */
	final public String juegoId;
	final public String nombreJugador;

	/**
	 * Creates a DisconnectMessage containing a given String, which is meant to
	 * describe the reason for the disconnection.
	 */
	public DisconnectPlayerMessage(String message, String nombreJugador) {
		this.juegoId = message;
		this.nombreJugador = nombreJugador;
	}

}
