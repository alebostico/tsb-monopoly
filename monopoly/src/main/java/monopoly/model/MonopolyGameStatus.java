/**
 * 
 */
package monopoly.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class MonopolyGameStatus implements Serializable {

	private static final long serialVersionUID = -584315517388176762L;
	
	/**
	 * Estados
	 */
	
	public static final int EMPEZAR = 0;
	
	public final List<Jugador> turnos;
	
	public int status;
	
	public Jugador currentPlayer;
	
	public MonopolyGameStatus(List<Jugador> turnos, int status, Jugador currentPlayer){
		this.turnos = turnos;
		this.status = status;
		this.currentPlayer = currentPlayer;
	}

}
