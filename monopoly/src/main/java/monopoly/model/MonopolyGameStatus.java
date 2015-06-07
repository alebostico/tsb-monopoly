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

	private List<Jugador> turnos;

	private EstadoJuego status;

	private AccionEnCasillero accionCasillero;

	private Banco banco;

	private Tablero tablero;

	private Jugador currentPlayer;

	private List<History> hirtoryList;
	
	private Tarjeta tarjeta;

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

	public void setTurnos(List<Jugador> turnos) {
		this.turnos = turnos;
	}

	public EstadoJuego getStatus() {
		return status;
	}

	public void setStatus(EstadoJuego status) {
		this.status = status;
	}

	public AccionEnCasillero getAccionCasillero() {
		return accionCasillero;
	}

	public void setAccionCasillero(AccionEnCasillero accionCasillero) {
		this.accionCasillero = accionCasillero;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Tablero getTablero() {
		return tablero;
	}

	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	public Jugador getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Jugador currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public List<History> getHirtoryList() {
		return hirtoryList;
	}

	public void setHirtoryList(List<History> hirtoryList) {
		this.hirtoryList = hirtoryList;
	}

	public Tarjeta getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
	}

}
