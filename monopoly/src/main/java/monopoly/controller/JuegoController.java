/**
 * 
 */
package monopoly.controller;

import monopoly.model.Dado;
import monopoly.model.Estado;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.JugadorHumano;
import monopoly.model.MonopolyGameStatus;
import monopoly.model.Usuario;
import monopoly.model.tablero.Casillero;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 */
public class JuegoController {

	private Juego juego;

	private Estado estadoJuego;

	private int cantJugadores;

	private BancoController gestorBanco;

	private TableroController gestorTablero;

	private JugadorController gestorJugadores;

	private MonopolyGameStatus status;

	public JuegoController(Usuario creador, String nombre) {
		this.gestorBanco = new BancoController();
		this.gestorTablero = new TableroController();
		this.juego = new Juego(creador, nombre);
		this.juego.setTablero(gestorTablero.getTablero());
		this.estadoJuego = new Estado();
		this.gestorJugadores = new JugadorController();
	}

	public Juego getJuego() {
		return juego;
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	public void addPlayer(Jugador jugador) {
		this.gestorJugadores.addPlayer(jugador);

		if (this.gestorJugadores.cantJugadoresConectados() == cantJugadores) {
			estadoJuego.actualizarEstadoJuego();
			gestorJugadores.establecerTurnos();
		}
	}

	/**
	 * @return the gestorBanco
	 */
	public BancoController getGestorBanco() {
		return gestorBanco;
	}

	/**
	 * @return the gestorTablero
	 */
	public TableroController getGestorTablero() {
		return gestorTablero;
	}

	/**
	 * @return the gestorJugadores
	 */
	public JugadorController getGestorJugadores() {
		return gestorJugadores;
	}

	/**
	 * @return the cantJugadores
	 */
	public int getCantJugadores() {
		return cantJugadores;
	}

	/**
	 * @param cantJugadores
	 *            the cantJugadores to set
	 */
	public void setCantJugadores(int cantJugadores) {
		this.cantJugadores = cantJugadores;
	}

	/**
	 * @return the estadoJuego
	 */
	public Estado getEstadoJuego() {
		return estadoJuego;
	}

	/**
	 * @param estadoJuego
	 *            the estadoJuego to set
	 */
	public void setEstadoJuego(Estado estadoJuego) {
		this.estadoJuego = estadoJuego;
	}

	public void startGame(int key, Dado dados) {
		// TODO Auto-generated method stub
		JugadorHumano jugador = gestorJugadores.getJugadorHumano(key);
		jugador.setTiradaInicial(dados);
		boolean tiraronTodosDados = true;
		for (Jugador j : gestorJugadores.getListaJugadoresHumanos()) {
			if (j.getTiradaInicial() == null) {
				tiraronTodosDados = false;
				break;
			}
		}
		if (tiraronTodosDados) {
			estadoJuego.actualizarEstadoJuego();
			ordenarTurnos();
			// quien es el primero?
		}
	}

	private void ordenarTurnos() {

		this.gestorJugadores.ordenarTurnos();

		for (Jugador jug : gestorJugadores.getTurnoslist()) {
			for (Casillero casillero : gestorTablero.getTablero()
					.getCasillerosList()) {

				if (jug.getCasilleroActual().equals(casillero)) {
					casillero.addJugador(jug);
					break;
				}
			}
		}

		status = new MonopolyGameStatus(gestorJugadores.getTurnoslist(),
				gestorBanco.getBanco(), gestorTablero.getTablero(),
				MonopolyGameStatus.EMPEZAR,
				gestorJugadores.getCurrentPlayer());

		for (int key : gestorJugadores.getIdConnectionClient()) {
			PartidasController.getInstance().getMonopolyGame()
					.sendToOne(key, status);
		}

	}

}
