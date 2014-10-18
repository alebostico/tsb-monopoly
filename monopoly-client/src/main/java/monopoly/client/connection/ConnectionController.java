/**
 * 
 */
package monopoly.client.connection;

import java.io.IOException;

import monopoly.client.controller.LoginController;
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

	public void iniciarConexion() {
		try {
			if (connection == null) {
				connection = new MonopolyClient(ServerIp, ServerPort);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(e.getMessage());
		}
		idPlayer = connection.getID();
	}

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
	 * This class defines the client object that handles communication with the
	 * Hub.
	 */
	private class MonopolyClient extends GameClient {

		/**
		 * Connect to the hub at a specified host name and port number.
		 */
		public MonopolyClient(String hubHostName, int hubPort)
				throws IOException {
			super(hubHostName, hubPort);
		}

		/**
		 * Responds to a message received from the Hub. The only messages that
		 * are supported are TicTacToeGameState objects. When one is received,
		 * the newState() method in the TicTacToeWindow class is called. To
		 * avoid problems with synchronization, that method is called using
		 * SwingUtilities.invokeLater() so that it will run in the GUI event
		 * thread.
		 */
		protected void messageReceived(final Object message) {
			if (message instanceof LoginMessage) {
				Usuario usuario = (Usuario) ((LoginMessage) message).message;
				LoginController.getInstance().iniciarSesion(usuario);
			}

			if (message instanceof MonopolyGameState) {
				newState((MonopolyGameState) message);
			}
			if (message instanceof MonopolyGameState)
				newState((MonopolyGameState) message);
			// else if (message instanceof String)
			// messageFromServer.setText( (String)message );
			// else if (message instanceof PokerCard[]) {
			// opponentHand = (PokerCard[])message;
			// display.repaint();
			// }
		}

		/**
		 * If a shutdown message is received from the Hub, the user is notified
		 * and the program ends.
		 */
		protected void serverShutdown(String message) {
			System.out
					.println("Your opponent has disconnected.\nThe game is ended.");
			System.exit(0);
		}

	}

	/**
	 * This method is called when a new game state is received from the hub. It
	 * stores the new state in the instance variable that represents the game
	 * state and updates the user interface to reflect the state. Note that this
	 * method is called on the GUI event thread (using
	 * SwingUtilitites.invokeLater()) to avoid synchronization problems.
	 * (Synchronization is an issue when a method that manipulates the GUI is
	 * called from a thread other than the GUI event thread. In this problem,
	 * there is also the problem that a message can actually be received before
	 * the constructor has completed, which would lead to errors in this method
	 * from uninitialized variables, if SwingUtilities.invokeLater() were not
	 * used.)
	 */
	private void newState(MonopolyGameState state) {
		this.state = state;

		// Set the name of callButton to "CALL" during the second round of
		// betting and to "SEE" at other times.

		// if (state.status == MonopolyGameState.RAISE_CALL_OR_FOLD_ROUND_2)
		// callButton.setText("CALL");
		// else
		// callButton.setText("SEE");

		// When it's time for this player to make a bet, enable the betInput
		// text field,
		// set its content to be the minimum possible bet plus $10, and select
		// the
		// text input so that the user can simply type the bet and press return.
		// The
		// betInput is not editable except when its time for the user to place a
		// bet.
		// Once the user places the bet (or sees, calls, passes, or folds), the
		// betInput is once again made empty and uneditable.

		// if (state.status == PokerGameState.RAISE_SEE_OR_FOLD_ROUND_1
		// || state.status == PokerGameState.RAISE_CALL_OR_FOLD_ROUND_2
		// || state.status == PokerGameState.BET_OR_FOLD) {
		// if (!betInput.isEditable()) {
		// int suggestedBet;
		// if (state.status == PokerGameState.BET_OR_FOLD)
		// suggestedBet = 10;
		// else
		// suggestedBet = state.amountToSee + 10;
		// betInput.setText("" + suggestedBet);
		// betInput.setEditable(true);
		// betInput.selectAll();
		// betInput.requestFocus();
		// }
		// }

		// Show the current amounts of the user's money, the opponent's money,
		// and the pot.

		// money.setText("You have $ " + state.money);
		// opponentsMoney.setText("Your opponent has $ " + state.opponentMoney);
		// if (state.status != PokerGameState.DEAL
		// && state.status != PokerGameState.WAIT_FOR_DEAL)
		// opponentHand = null;
		// pot.setText("Pot:  $ " + state.pot);
		//
		// // If it's time for the user to select cards to be discarded, create
		// the
		// // discard array that will record which cards have been selected to
		// be
		// // discarded.
		//
		// if (state.status == PokerGameState.DRAW && discard == null) {
		// discard = new boolean[5];
		// }

		// Set the JLable, message, to show instructions to the user that are
		// appropriate for the state.

		switch (state.status) {
		// case PokerGameState.DEAL:
		// message.setText("Click the DEAL button to start the game.");
		// break;
		// case PokerGameState.DRAW:
		// message.setText("Click the cards you want to discard, then click DRAW.");
		// break;
		// case PokerGameState.BET_OR_FOLD:
		// message.setText("Place your BET, PASS, or FOLD.");
		// break;
		// case PokerGameState.RAISE_SEE_OR_FOLD_ROUND_1:
		// message.setText("Place your BET, SEE, or FOLD.");
		// break;
		// case PokerGameState.RAISE_CALL_OR_FOLD_ROUND_2:
		// message.setText("Place your BET, CALL, or FOLD.");
		// break;
		// case PokerGameState.WAIT_FOR_BET:
		// message.setText("Waiting for opponent to bet.");
		// break;
		// case PokerGameState.WAIT_FOR_DEAL:
		// message.setText("Waiting for opponent to deal.");
		// break;
		// case PokerGameState.WAIT_FOR_DRAW:
		// message.setText("Waiting for opponent to draw.");
		// break;
		default:
			break;
		}
	}

	// public static TCPClient THREAD_CLIENTE;
	//
	// public static void iniciarConexion() {
	// THREAD_CLIENTE = new TCPClient();
	// THREAD_CLIENTE.start();
	// }
	//
	// public static void cerrarConexion() {
	// THREAD_CLIENTE.detenerHilo();
	// }

	public static ConnectionController getInstance() {
		if (instance == null)
			instance = new ConnectionController();
		return instance;
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

	/**
	 * @return the connection
	 */
	public MonopolyClient getConnection() {
		return connection;
	}

	/**
	 * @param connection
	 *            the connection to set
	 */
	public void setConnection(MonopolyClient connection) {
		this.connection = connection;
	}

}
