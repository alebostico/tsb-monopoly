/**
 * 
 */
package monopoly.model;

import java.io.Serializable;
import java.util.List;

import monopoly.model.Estado.EstadoJuego;
import monopoly.model.tablero.Tablero;

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
	public static final int TURNO_DADO = 1;
	
	public final List<Jugador> turnos;
	
	public final EstadoJuego status;
	
	public final Banco banco;
	
	public final Tablero tablero;
	
	public Jugador currentPlayer;
	
	public final List<History> hirtoryList;
	
	public MonopolyGameStatus(List<Jugador> turnos, Banco banco, Tablero tablero, EstadoJuego status, Jugador currentPlayer, List<History> hirtoryList){
		this.turnos = turnos;
		this.status = status;
		this.currentPlayer = currentPlayer;
		this.banco = banco;
		this.tablero = tablero;
		this.hirtoryList = hirtoryList;
	}

}
