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

	public final List<Jugador> turnos;

	public final EstadoJuego estadoTurno;

	public final  AccionEnCasillero accionCasillero;

	public final Banco banco;

	public final Tablero tablero;

	public final Jugador currentPlayer;

	public final List<History> hirtoryList;
	
	public final Tarjeta tarjeta;

	public MonopolyGameStatus(List<Jugador> _turnos, Banco _banco,
			Tablero _tablero, EstadoJuego _status, AccionEnCasillero _accion,
			Jugador _currentPlayer, List<History> _hirtoryList, Tarjeta _tarjeta) {
		this.turnos = _turnos;
		this.estadoTurno = _status;
		this.accionCasillero = _accion;
		this.currentPlayer = _currentPlayer;
		this.banco = _banco;
		this.tablero = _tablero;
		this.hirtoryList = _hirtoryList;
		this.tarjeta = _tarjeta;
	}

}
