/**
 * 
 */
package monopoly.message.impl;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class MonopolyGameState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------------
	// The eight following constants are the possible values of
	// status. The status is the basic information that tells
	// a player what it should be doing at a given time.

	
	// The player must click "DEAL" to start the game.
	public final static int DEAL = 0; 
	
	public final static int INICIAR_APLICACION = 1;
	
	// The player must make the first bet in a betting round, or fold.
	public final static int BET_OR_FOLD = 1; 
	public final static int RAISE_SEE_OR_FOLD_ROUND_1 = 2; // During first round
															// of betting,
															// player must
															// respond
															// to pponent's bet
															// by raising or
															// matching the bet,
															// or folding.
	public final static int RAISE_CALL_OR_FOLD_ROUND_2 = 3; // During second
															// round of betting,
															// player must
															// respond
															// to pponent's bet
															// by raising or
															// matching the bet,
															// or folding.
	public final static int DRAW = 4; // The player must select cards to
										// discard, and click "DRAW".

	public final static int WAIT_FOR_DEAL = 5; // Wait for opposing player to
												// start the game.
	public final static int WAIT_FOR_BET = 6; // Wait for opposing player to BET
												// (or fold).
	public final static int WAIT_FOR_DRAW = 7; // Wait for opposing player to
												// discard cards.

	// -------------------------------------------------------------

	public int status; // Game status; one of the constants defined in this
						// class.

	public MonopolyGameState(int status)
	{
		this.status = status;
	}

}
