package monopoly.util.message;


import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */

/**
 * The Hub sends a StatusMessage to all connected clients when a player connects
 * or disconnects. When a player connects, that player receives the status
 * message caused by their connecting. When a player disconnects, that player
 * does not receive a copy of the status message that is sent. StatusMessages
 * are from internal use in the netgame.common package and users of this package
 * do not have to deal with them. This package private class is only used
 * internally in the netgame.common package. Users of the package will not see
 * these messages; instead, the Client's playerConnected() or
 * playerDisconnected() method will be called.
 */
public final class _StatusMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The ID number of the player who has connected or disconnected.
	 */
	public final int playerID;

	/**
	 * True if the player has just connected; false if the player has just
	 * disconnected.
	 */
	public final boolean connecting;

	/**
	 * The list of players after the change has been made.
	 */
	public final int[] players;

	public _StatusMessage(int playerID, boolean connecting, int[] players) {
		this.playerID = playerID;
		this.connecting = connecting;
		this.players = players;
	}

}