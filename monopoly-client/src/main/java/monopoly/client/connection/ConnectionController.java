/**
 * 
 */
package monopoly.client.connection;

import java.io.IOException;

import monopoly.message.impl.MonopolyGameState;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.LectorXML;
import monopoly.util.message.CreateAccountMessage;
import monopoly.util.message.LoginMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class ConnectionController {

	/**
	 * The state of the game. This state is a copy of the official state, which
	 * is stored on the server. When the state changes, the state is sent as a
	 * message to this window. (It is actually sent to the TicTacToeClient
	 * object that represents the connection to the server.) When that happens,
	 * the state that was received in the message replaces the value of this
	 * variable, and the board and UI is updated to reflect the changed state.
	 * This is done in the newState() method, which is called by the
	 * TicTacToeClient object.
	 */
	private MonopolyGameState state;

	private int idPlayer; // The ID number that identifies the player using this
							// window.

	private MonopolyClient connection; // The Client object for sending and
										// receiving
										// network messages.

	private String ServerIp = LectorXML.getIpServidor();

	private int ServerPort = LectorXML.getPuertoDeConexion();

	private static ConnectionController instance = null;

	public void iniciarConexionToLogin(Usuario usuario) {
		try {
			if (connection == null) {
				connection = new MonopolyClient(ServerIp, ServerPort);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(e.getMessage());
		}
		idPlayer = connection.getID();
		send(new LoginMessage(idPlayer, usuario));
	}
	
	public void iniciarConexionToAccount(Usuario usuario) {
		try {
			if (connection == null) {
				connection = new MonopolyClient(ServerIp, ServerPort);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(e.getMessage());
		}
		idPlayer = connection.getID();
		send(new CreateAccountMessage(idPlayer, usuario));
	}

	public void cerrarConexion() {
		if (connection != null) {
			connection.disconnect();
			try { // time for the disconnect message to be sent.
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
		System.exit(0);
	}

	public void send(Object message) {
		if (connection != null)
			connection.send(message);
	}

	/**
	 * @return the myID
	 */
	public int getIdPlayer() {
		return idPlayer;
	}

	/**
	 * @param myID
	 *            the myID to set
	 */
	public void setIdPlayer(int myID) {
		this.idPlayer = myID;
	}

	/**
	 * @return the state
	 */
	public MonopolyGameState getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(MonopolyGameState state) {
		this.state = state;
	}
	
	public static ConnectionController getInstance() {
		if (instance == null)
			instance = new ConnectionController();
		return instance;
	}

}
