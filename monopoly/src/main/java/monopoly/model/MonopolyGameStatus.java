/**
 * 
 */
package monopoly.model;

import java.io.Serializable;
import java.util.List;

import monopoly.model.Estado.EstadoJuego;
import monopoly.model.tablero.Tablero;
import monopoly.model.tarjetas.Tarjeta;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class MonopolyGameStatus implements Serializable {

	private static final long serialVersionUID = -584315517388176762L;

	private final List<Jugador> turnos;

	private final EstadoJuego status;

	private final  AccionEnCasillero accionCasillero;

	private  final Banco banco;

	private  final Tablero tablero;

	private  final Jugador currentPlayer;

	private  final List<History> hirtoryList;
	
	private  final Tarjeta tarjeta;

	public MonopolyGameStatus(List<Jugador> turnos, Banco banco,
			Tablero tablero, EstadoJuego status, AccionEnCasillero accion,
			Jugador currentPlayer, List<History> hirtoryList, Tarjeta tarjeta) {
		this.turnos = turnos;
		this.status = status;
		this.accionCasillero = accion;
		this.currentPlayer = currentPlayer;
		this.banco = banco;
		this.tablero = tablero;
		this.hirtoryList = hirtoryList;
		this.tarjeta = tarjeta;
	}

	public List<Jugador> getTurnos() {
		return turnos;
	}

	public EstadoJuego getStatus() {
		return status;
	}

	public AccionEnCasillero getAccionCasillero() {
		return accionCasillero;
	}

	public Banco getBanco() {
		return banco;
	}

	public Tablero getTablero() {
		return tablero;
	}

	public Jugador getCurrentPlayer() {
		return currentPlayer;
	}

	public List<History> getHirtoryList() {
		return hirtoryList;
	}

	public Tarjeta getTarjeta() {
		return tarjeta;
	}
}
